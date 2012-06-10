(ns table.core
  (:use [clojure.string :only [join]] ))

(defn render-rows [table]
  (let [
    border
     (str "+-"
       (join "-+-"
         (map #(apply str (repeat (.length %) "-"))
           (first table)))
                "-+")
    header [
      border
      (str "| " (join " | " (first table)) " |")]]

    (concat
      header
      [border]
      (for [tr (rest table)]
        (str "| " (join " | " tr) " |"))
      [border])))

(defn table-str [& args]
  (apply str (join "\n" (apply render-rows args))))

(defn table [& args]
  (print (apply table-str args)))

