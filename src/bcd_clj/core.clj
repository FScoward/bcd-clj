(ns bcd-clj.core)

; 桁数計算
(def counter (ref 0))
(defn calc-digits [num]
(dosync (alter counter inc))
(def result (quot num 10))
(if (>= result 1) (calc-digits result) counter)
  (deref counter))

(defn toBCD [num]
  (def digits (calc-digits num))
  (def str-array
    (for [x (range digits)]
      (Integer/toBinaryString (int (.charAt (.toString num) x)))))
  (map (fn [x] (subs x 2)) str-array))

(toBCD 123)
