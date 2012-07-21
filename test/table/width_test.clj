(ns table.width-test
  (:use clojure.test table.width))

; for max width of 238, 76 is the max-width-per-field for 3 fields
(deftest test-auto-resize-allows-over-max-field-to-get-width-from-under-max-fields
  (is
    (=
      [74 74 80]
      (auto-resize-widths [74 74 88]))))

(deftest test-auto-resize-leaves-under-max-fields-alone
  (is
    (=
      [10 10 15]
      (auto-resize-widths [10 10 15]))))

(deftest test-auto-resize-reduces-all-max-fields
  (is
    (=
      [76 76 76]
      (auto-resize-widths [80 100 94]))))
