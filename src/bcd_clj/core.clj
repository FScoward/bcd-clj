;; https://gist.github.com/neuro-sys/953548 の clojure 実装
(ns bcd-clj.core)

; 桁数計算
(def counter (ref 0))
(defn calc-digits [num]
  (dosync (alter counter inc))
  (def result (/ num 10))
  (if (>= result 1) (calc-digits result) counter))

(defn dec-to-bcd-array [num]
  ; 桁数
  (def digit (deref (calc-digits num)))

  (def byte-len (if (= 0 (rem digit 2))
                  (/ digit 2)
                  (/ (+ digit 1) 2)))
  ; 奇数か？
  (def isOdd (odd? num))

  (def bcd (byte-array byte-len))

  (loop [i 0]
    (when (< i digit)
      (let [tmp (rem num 10)]
        ; todo
        (if (and (= i (- digit 1)) isOdd)
          (aset-byte bcd (/ i 2) tmp)
          (if (= 0 (/ i 2))
            (aset-byte bcd (/ i 2) tmp)
            (aset-byte bcd (/ i 2) (bit-shift-left tmp 4))
            ))
        (recur (inc i)))))
  (vec bcd)
  )



(dec-to-bcd-array 123)
