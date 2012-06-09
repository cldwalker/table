(ns table.core-test
  (:use clojure.test
        table.core))

(deftest test-table
  (let [t (table [["1" "2"]["3" "4"]])]
    (is (.contains t "| 1 | 2 |"))
    (is (.contains t "| 3 | 4 |"))
    (is (.contains t "+---+---+"))))


(print (table [["1" "2"]["3" "4"]]))

; TODO hashes
#_(deftest test-table
  (let [rendered (render [{:1 3 :2 4}])]
    (is (.contains rendered "| 1 | 2 |"))
    (is (.contains rendered "| 3 | 4 |"))
    (is (.contains rendered "|---+---|"))))
