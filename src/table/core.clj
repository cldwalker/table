(ns table.core
  (:use [clojure.string :only [join]] ))

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

(defn- style-for [k] (k (styles *style*)))

(defn- render-rows-with-fields [rows fields options]
  (let [
    headers (map #(if (keyword? %) (name %) (str %)) fields)
    widths (map-indexed
             (fn [idx header]
               (apply max (count header) (map #(count (str (nth % idx))) rows)))
             headers)
    fmt-row (fn [row]
              (map-indexed
                (fn [idx val] (let [num (nth widths idx)]
                  (if (zero? num) "" (format (str "%-" num "s") val))))
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


(defn table-str [ args & {:keys [style] :or {style :plain} :as options}]
  (binding [*style* style]
    (apply str (join "\n" (render-rows args (if (map? options) options {}))))))

(defn table [& args]
  (println (apply table-str args)))
