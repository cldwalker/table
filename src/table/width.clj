(ns table.width)

(def ^:dynamic *width* 238)
; " | " and "-+-" are inner borders
(def inner-border-length 3)
; "+-" and "-+" are outer borders
(def outer-border-length 2)
(declare get-initial-widths max-width-per-field actual-width auto-resize-widths)

(defn get-widths [all-rows]
  (-> all-rows get-initial-widths vec auto-resize-widths))

(defn get-initial-widths [all-rows]
  (map
    (fn [idx]
      (apply max (map #(count (str (nth % idx))) all-rows)))
    (range (count (first all-rows)))))

(defn max-width-per-field [current-width field-count]
  (quot (actual-width current-width field-count) field-count))

(defn actual-width [current-width field-count]
  (- current-width (+ (* 2 outer-border-length) (* (dec field-count) inner-border-length))))

(defn auto-resize-widths [widths]
  (loop [new-widths [] old-widths widths field-count (count widths) max-width *width*]
    (if (empty? old-widths)
      new-widths
      (let [width (first old-widths)
            width-per-field (max-width-per-field max-width field-count)
            new-width (if (< width width-per-field) width width-per-field)]
        #_(prn field-count new-width max-width width-per-field)
        (recur
          (conj new-widths new-width)
          (rest old-widths)
          (- field-count 1)
          (- max-width (+ new-width inner-border-length)))))))

