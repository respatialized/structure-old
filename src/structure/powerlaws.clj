(ns structure.powerlaws
  )

(defn powers-of [base nums]
  "Raises base to the given powers. Expects a sequence."
  (map #(Math/pow base %) nums))
