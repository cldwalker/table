(ns table.width)

; TODO: calculate width from env
(def ^:dynamic *width* 238)
; TODO: calculate border lengths from styles
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

; think of inner-borders as interposed between fields to understand why
; it's one less than the number of fields
(defn actual-width [current-width field-count]
  (- current-width (+ (* 2 outer-border-length) (* (dec field-count) inner-border-length))))

(defn auto-resize-widths [widths]
  (loop [new-widths [] widths widths field-count (count widths) max-width *width*]
    (if (empty? widths)
      new-widths
      (let [width (first widths)
            width-per-field (max-width-per-field max-width field-count)
            new-width (if (< width width-per-field) width width-per-field)]
        #_(prn field-count new-width max-width width-per-field)
        (recur
          (conj new-widths new-width)
          (rest widths)
          (- field-count 1)
          (- max-width (+ new-width inner-border-length)))))))

