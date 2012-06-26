(ns table.core
  (:use [clojure.string :only [join replace-first]] ))

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

; generates a vec of formatted string rows given almost any input
(defn- render-rows [table]
  (let [
    fields (cond
             (map? (first table)) (distinct (vec (flatten (map keys table))))
             (map? table) [:key :value]
             :else (first table))
    headers (map #(if (keyword? %) (name %) (str %)) fields)
    ; rows are converted to a vec of vecs containing string cell values
    rows (cond
           (map? (first table)) (map #(map (fn [k] (get % k)) fields) table)
           (map? table) table
           :else (rest table))
    rows (map (fn [row] (map #(if (nil? %) "" (str %)) row)) rows)
    rows (map vec rows)
    widths (map-indexed
             (fn [idx header]
               (apply max (count header) (map #(count (str (nth % idx))) rows)))
             headers)
    fmt-row (fn [row]
              (map-indexed
                (fn [idx val] (format (str "%-" (nth widths idx) "s") val))
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

(defn table-str [ args & {:keys [style] :or {style :plain}}]
  (binding [*style* style] (apply str (join "\n" (render-rows args)))))

(defn table [& args]
  (println (apply table-str args)))
