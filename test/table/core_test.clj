(ns table.core-test
  (:use clojure.test
        table.core))

(deftest test-table
  (is (with-out-str (table [["1" "2"] ["3" "4"]]))
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ")) 

(deftest test-table-array
  (is (table-str [["1" "2"] ["3" "4"]])
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      "))

;(table [["1" "2"]["3" "4"]])
