(ns table.core-test
  (:require clojure.string)
  (:use clojure.test
        table.core))

(defn unindent [string]
  (clojure.string/replace (clojure.string/trim string) #"\n\s*" "\n"))

(deftest test-table
  (is (=
    (unindent
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ")
    (with-out-str (table [["1" "2"] ["3" "4"]])))))

(deftest test-table-array
  (is (=
    (unindent
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ")
    (table-str [["1" "2"] ["3" "4"]]))))

(deftest test-table-map
  (is (=
    (unindent
      "
      +---+---+
      | a | b |
      +---+---+
      | 1 | 2 |
      | 3 | 4 |
      +---+---+
      ")
    (table-str [{:a 1 :b 2} {:a 3 :b 4}]))))

;(table [["1" "2"]["3" "4"] ["5", "6"]])
