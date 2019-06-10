(ns structure.powerlaws
  )

(defn powers-of [base nums]
  "Raises base to the given powers. Expects a sequence."
  (map #(Math/pow base %) nums))

(defn nearest [a b]
  (if-let [more-b (next b)]
    (let [[x y] b
          m (/ (+ x y) 2)
          [<=m >m] (split-with #(<= % m) a)]
      (lazy-cat (repeat (count <=m) x)
                (nearest >m more-b)))
    (repeat (count a) (first b))))


