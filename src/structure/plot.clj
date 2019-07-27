(ns structure.plot
  (:require [quil.core :as q]
            [thi.ng.color.core :as color]
            [structure.powerlaws :as powerlaws]
            [structure.themes :as themes]))

;; namespace for drawing functions, including utility functions abstracted from other sketches.


(defn h
  ([] (h 1.0))
  ([value] (* (q/height) value)))

(defn w
  ([] (w 1.0))
  ([value] (* (q/width) value)))

(defn unmap
  "takes a width/height and a pixel value and converts it to a fraction"
  [v dim]
  (float (/ v dim)))

(defn css->color
  "returns a quil-ready color vector from the given hex code.
   if no param specified, returns in rgba."
  ([csscode]
   (css->color csscode :rgb))
  ([csscode k]
   (let [colorspaces {:rgb color/as-rgba :hsb color/as-hsva}]
     @((k colorspaces) (color/css csscode)))))

(defn random-color-grid
  [rows cols color-probs]
   (for [r (range rows)]
     (shuffle (powerlaws/nearest
     (sort (for [c (range cols)] (rand)))
     (keys color-probs)))))

(defn random-colors [n color-probs]
  (shuffle (powerlaws/nearest
            (sort (for [i (range n)] (rand)))
            (keys color-probs))))

(defn draw [params]
  (q/no-loop) ; static output

  (let [colors (themes/fetch-colors)
        color-probs (into (sorted-map) (zipmap
                     (-> (keys colors)
                         (#(map themes/get-key-num %))
                         (#(map (fn [n] (* -1 n)) %))
                         (#(powerlaws/powers-of 1.2 %)))
                     (keys colors)))
        rows 160
        cols 20
        xspan (/ 1 cols)
        yspan (/ 1 rows)
        fill-colors (random-colors (* rows cols) color-probs)]

    ;; Set the background color
    (apply q/background (css->color (:color15 colors) :hsb))

    ;; fill the squares
    (doseq [r (range rows)
            c (range cols)]
      (let [x (w (* xspan c))
            y (h (* yspan r))
            colorkey (nth fill-colors (+ (* c cols) r))]
        (q/no-stroke)
        (apply q/fill (css->color (get colors (get color-probs colorkey)) :hsb))
        (q/rect x y (w (* 0.85 xspan)) (h yspan))
        ))

    ;; (doseq [a (range 20)
    ;;         b (range 20)]
    ;;   (let [x (w (* a (/ 1 20))) y (h (* b (/ 1 20)))]
    ;;     (q/fill 0.0 0.0 0.0)
    ;;     (q/rect x y 20 20)))

    )

  ;; (structure/tower params)
  )
