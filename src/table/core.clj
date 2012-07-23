(ns table.core
  (:require table.width)
  (:use [clojure.string :only [join]] ))

(declare style-for format-cell render-rows-with-fields escape-newline render-rows table-str)

(def ^:dynamic *style* :plain)
(def ^:private walls ["| " " | " " |"])
(def ^:private styles
  {
   :plain {:top ["+-" "-+-" "-+"], :middle ["+-" "-+-" "-+"] :bottom ["+-" "-+-" "-+"]
           :dash "-" :header-walls walls :body-walls walls }
   :org {:top ["|-" "-+-" "-|"], :middle ["|-" "-+-" "-|"] :bottom ["|-" "-+-" "-|"]
         :dash "-" :header-walls walls :body-walls walls }
   :unicode {:top ["┌─" "─┬─" "─┐"] :middle ["├─" "─┼─" "─┤"] :bottom ["└─" "─┴─" "─┘"]
             :dash "─" :header-walls ["│ " " │ " " │"] :body-walls ["│ " " ╎ " " │"] }
   :github-markdown {:top ["" "" ""] :middle ["|-" " | " "-|"] :bottom ["" "" ""]
                     :top-dash "" :dash "-" :bottom-dash "" :header-walls walls :body-walls walls }
   })

(defn table
   "Generates an ascii table for almost any input that fits in your terminal.
   Multiple table styles are supported.

   Options:

   * :sort   When set to true sorts table by first column. Default is false.
   * :style  Sets table style. Available styles are :plain, :org, :unicode and
             :github-markdown. Default is :plain."
  [& args]
  (println (apply table-str args)))

(defn table-str
  "Same options as table but returns table as a string"
  [ args & {:keys [style] :or {style :plain} :as options}]
  (binding [*style* style]
    (apply str (join "\n" (render-rows args (if (map? options) options {}))))))

; generates a vec of formatted string rows given almost any input
(defn- render-rows [table options]
   (let [
    top-level-vec (not (coll? (first table)))
    fields (cond
             top-level-vec [:value]
             (map? (first table)) (distinct (vec (flatten (map keys table))))
             (map? table) [:key :value]
             :else (first table))
    ; rows are converted to a vec of vecs containing string cell values
    rows (cond
           top-level-vec (map #(vector %) table)
           (map? (first table)) (map #(map (fn [k] (get % k)) fields) table)
           (map? table) table
           :else (rest table))
    rows (map (fn [row] (map #(if (nil? %) "" (str %)) row)) rows)
    rows (if (options :sort) (sort-by first rows) rows)
    rows (->> rows (map vec) (map (fn [row] (map escape-newline row))))]
  (render-rows-with-fields rows fields options)))

(defn- render-rows-with-fields [rows fields options]
  (let [
    headers (map #(if (keyword? %) (name %) (str %)) fields)
    widths (table.width/get-widths (cons headers rows))
    fmt-row (fn [row]
              (map-indexed
                (fn [idx string] (format-cell string (nth widths idx)))
                row))
    wrap-row (fn [row strings] (let [[beg mid end] strings] (str beg (join mid row) end)))
    headers (fmt-row headers)
    border-for (fn [section dash]
                 (let [dash-key (if (style-for dash) dash :dash)]
                 (wrap-row
                   (map #(apply str (repeat
                                      (.length (str %))(style-for dash-key))) headers)
                   (style-for section))))
    header (wrap-row headers (style-for :header-walls))
    body (map #(wrap-row (fmt-row %) (style-for :body-walls)) rows) ]

    (concat [(border-for :top :top-dash) header (border-for :middle :dash)]
            body [( border-for :bottom :bottom-dash)])))

(defn- escape-newline [string]
  (clojure.string/replace string (str \newline) (char-escape-string \newline)))

(defn- style-for [k] (k (styles *style*)))

(defn format-cell [string width]
  (if (zero? width)
    ""
    (format
      (str "%-" width "." width "s")
      (if (> (count string) width)
        (str (.substring string 0 (- width 3)) "...")
        string))))
