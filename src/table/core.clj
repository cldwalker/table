(ns table.core
  (:use [clojure.string :only [join]]
        [table.core]))

(defn render [table]
  (let [spacer (str "+-"
                    (join "-+-"
                          (map #(apply str (repeat (.length %) "-"))
                               (first table)))
                    "-+")]
    (concat [spacer
             (str "| " (join " | " (first table)) " |")
             spacer]
            (for [tr (rest table)]
              (str "| " (join " | " tr) " |"))
            [spacer])))

(defn table [& args]
  (apply str (join "\n" (apply render args))))

