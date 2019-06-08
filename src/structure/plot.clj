(ns structure.plot
  (:require [quil.core :as q]
            [structure.themes :as themes])
  )

;; namespace for drawing functions, including utility functions abstracted from other sketches.

(defn h
  ([] (h 1.0))
  ([value] (* (q/height) value)))

(defn w
  ([] (w 1.0))
  ([value] (* (q/width) value)))

(defn draw [params]
  ;; Set the background color

  (let [colors (themes/fetch-colors)
        color-probs (zipmap
                     (keys colors)
                     (-> (vals colors)
                         (#(map themes/get-key-num %))
                         (#(map #(* -1 %)))
                         (#(powerlaws/powers-of 5 %))
                         )
                            )
        ]

    (q/background 200 0 25)
    )

  ;; (q/ellipse (w 0.2) (w 0.2) (w 0.05) (w 0.05))
  ;; (structure/tower params)
  )
