(ns table.core
  (:use [clojure.string :only [join replace-first]] ))

(defn render-rows [table]
  (let [
    fields (if (map? (first table)) (vec (keys (first table))) (first table))
    headers (if (map? (first table)) (map #(replace-first % #"^:" "") fields) (identity fields))
    border  (str "+-"
              (join "-+-"
                (map #(apply str (repeat (.length (str %)) "-"))
                  headers))
              "-+")
    header [
      border
      (str "| " (join " | " headers) " |")]
    rows (if (map? (first table))
           (map (apply juxt fields) table) (rest table))
        ]

    (concat
      header
      [border]
      (for [tr rows]
        (str "| " (join " | " tr) " |"))
      [border])))

(defn table-str [& args]
  (apply str (join "\n" (apply render-rows args))))

(defn table [& args]
  (print (apply table-str args)))

