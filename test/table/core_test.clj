(ns table.core-test
  (:require clojure.string)
  (:use clojure.test
        table.core))

(defn unindent [string]
  (clojure.string/replace (clojure.string/trim string) #"\n\s*" "\n"))

(deftest test-table-prints-to-out
  (is (=
    (str (unindent
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ") "\n") 
    (with-out-str (table [["1" "2"] ["3" "4"]])))))

(deftest test-table-with-vecs-in-vec
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

(deftest test-table-with-maps-in-vec
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

(deftest test-table-with-top-level-map
  (is (=
    (unindent
      "
      +-----+-------+
      | key | value |
      +-----+-------+
      | :a  | 1     |
      | :b  | 2     |
      +-----+-------+
      ")
    (table-str {:a 1 :b 2}))))

(deftest test-table-with-auto-width
  (is (=
    (unindent
      "
      +----+----+
      | a  | b  |
      +----+----+
      | 11 | 22 |
      | 3  | 4  |
      +----+----+
      ")
    (table-str [{:a 11 :b 22} {:a 3 :b 4}]))))

(deftest test-table-with-non-string-values
  (is (=
    (unindent
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ")
    (table-str [[1 2] [3 4]]))))

(deftest test-table-with-string-keys
  (is (=
    (unindent
      "
      +---+
      | a |
      +---+
      | 1 |
      | 2 |
      +---+
      ")
     (table-str [{"a" 1} {"a" 2}]))))

(deftest test-table-with-different-keys-per-row
  (is (=
    (unindent
      "
      +---+---+
      | a | b |
      +---+---+
      | 1 |   |
      |   | 2 |
      +---+---+
      ")
    (table-str [{:a 1} {:b 2}]))))

(deftest test-table-with-lists-in-list
  (is (=
    (unindent
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ")
     (table-str '((1 2) (3 4))))))

(deftest test-table-with-vecs-in-list
  (is (=
    (unindent
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ")
    (table-str '([1 2] [3 4])))))

(deftest test-table-with-sets-in-vec
  (is (=
    (unindent
      "
      +---+---+
      | 1 | 2 |
      +---+---+
      | 3 | 4 |
      +---+---+
      ")
    (table-str [#{1 2} #{3 4}]))))

(deftest test-table-with-vecs-in-set
   (is (=
    (unindent
      "
      +---+---+
      | 3 | 4 |
      +---+---+
      | 1 | 2 |
      +---+---+
      ")
    (table-str #{[1 2] [3 4]}))))

(deftest test-table-with-nil-values
  (is (=
    (unindent
      "
      +---+
      | a |
      +---+
      |   |
      +---+
      ")
    (table-str [{:a nil}]))))

(deftest test-table-with-nil
  (is (=
    (unindent
      "
      +--+
      |  |
      +--+
      +--+
      "
    ))))

(deftest test-table-with-org-style
  (is (=
    (unindent
      "
      |---+---|
      | 1 | 2 |
      |---+---|
      | 3 | 4 |
      |---+---|
      ")
      (table-str [[1 2] [3 4]] :style :org))))

(deftest test-table-with-unicode-style
  (is (=
    (unindent
      "
      ┌───┬───┐
      │ 1 │ 2 │
      ├───┼───┤
      │ 3 ╎ 4 │
      └───┴───┘
      ")
    (table-str [[1 2] [3 4]] :style :unicode))))

(deftest test-table-with-markdown-style
  (is (=
    (str "\n" (unindent
      "
      | 10 | 20 |
      |--- | ---|
      | 3  | 4  |
      ") "\n")
    (table-str [[10 20] [3 4]] :style :github-markdown))))

(deftest test-table-with-empty-cells
  (is (=
    (unindent
      "
      +--+---+
      |  | 2 |
      +--+---+
      |  | 4 |
      +--+---+
      ")
    (table-str [["" "2"] ["" "4"]]))))

(deftest test-table-with-sort-option
  (is (=
    (unindent
      "
      +----+----+
      | 1  | 2  |
      +----+----+
      | :a | :b |
      | :c | :d |
      +----+----+
      ")
    (table-str  [[1 2] [:c :d]  [:a :b]] :sort true))))

;(defn test-ns-hook []
;  (test-table-with-top-level-map))
