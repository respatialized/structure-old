(ns structure.plot
  (:require [quil.core :as q]
            [thi.ng.color.core :as color]
            [structure.powerlaws :as powerlaws]
            [structure.themes :as themes])
  )

;; namespace for drawing functions, including utility functions abstracted from other sketches.

(defn h
  ([] (h 1.0))
  ([value] (* (q/height) value)))

(defn w
  ([] (w 1.0))
  ([value] (* (q/width) value)))

(defn css->color
  "returns a quil-ready color vector from the given hex code.
   if no param specified, returns in rgba."
  ([csscode]
   (css->color csscode :rgb))
  ([csscode k]
   (let [colorspaces {:rgb color/as-rgba :hsb color/as-hsva}]
     @((k colorspaces) (color/css csscode))
     )
   ))


(defn draw [params]

  (let [colors (themes/fetch-colors)
        color-probs (zipmap
                     (keys colors)
                     (-> (keys colors)
                         (#(map themes/get-key-num %))
                         (#(map (fn [n] (* -1 n)) %))
                         (#(powerlaws/powers-of 5 %))
                         )
                     )
        ]

    ;; Set the background color
    (apply q/background (css->color (:color03 colors) :hsb))
    )

  ;; (q/ellipse (w 0.2) (w 0.2) (w 0.05) (w 0.05))
  ;; (structure/tower params)
  )
