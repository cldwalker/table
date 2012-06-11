(ns table.core
  (:use [clojure.string :only [join replace-first]] ))

(defn render-rows [table]
  (let [
    fields (if (map? (first table)) (vec (keys (first table))) (first table))
    headers (if (map? (first table)) (map #(replace-first % #"^:" "") fields) fields)
    rows (if (map? (first table))
           (map (apply juxt fields) table) (rest table))
    widths (map-indexed
             (fn [idx header]
               (apply max (count header) (map #(count (str (nth % idx))) rows)))
             headers)
    fmts (map #(str "%-" % "s") widths)
    fmt-row (fn [row]
              (map-indexed (fn [idx val] (format (nth fmts idx) val)) row))
    headers (fmt-row headers)
    border  (str "+-"
              (join "-+-"
                (map-indexed
                  (fn [idx header]
                    (apply str (repeat (nth widths idx) "-")))
                  headers))
              "-+")
    header [ border
      (str "| " (join " | " headers) " |")]
]
    (concat
      header
      [border]
      (for [tr rows]
        (str "| " (join " | " (fmt-row tr)) " |"))
      [border])))

(defn table-str [& args]
  (apply str (join "\n" (apply render-rows args))))

(defn table [& args]
  (print (apply table-str args)))
