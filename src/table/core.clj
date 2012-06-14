(ns table.core
  (:use [clojure.string :only [join replace-first]] ))

(defn render-rows [table]
  (let [
    fields (if (map? (first table)) (distinct (vec (flatten (map keys table)))) (first table))
    headers (map #(if (keyword? %) (name %) (str %)) fields)
    rows (if (map? (first table))
           (map #(map (fn [k] (get % k "")) fields) table) (rest table))
    rows (map vec rows)
    widths (map-indexed
             (fn [idx header]
               (apply max (count header) (map #(count (str (nth % idx))) rows)))
             headers)
    fmt-row (fn [row]
              (map-indexed
                (fn [idx val] (format (str "%-" (nth widths idx) "s") val))
                row))
    wrap-row (fn [row beg mid end] (str beg (join mid row) end))
    headers (fmt-row headers)
    border  (wrap-row
              (map #(apply str (repeat (.length (str %)) "-")) headers)
              "+-" "-+-" "-+")
    header [ border (wrap-row headers "| " " | " " |")]]

    (concat
      header
      [border]
      (map #(wrap-row (fmt-row %) "| " " | " " |") rows)
      [border])))

(defn table-str [& args]
  (apply str (join "\n" (apply render-rows args))))

(defn table [& args]
  (print (apply table-str args)))
