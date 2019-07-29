(ns structure.sierpinski
  (:require
   [thi.ng.geom.core :as g]
   [thi.ng.geom.circle :as c]
   [thi.ng.geom.polygon :as p]
   [clojure2d.core :as clj2d]
   [fastmath.core :as m]))

(defn translate-pt
  "Returns a point from the given point given a distance (in pixels) and bearing (in radians)."
  [[x y] bearing dist]
  (let [ydelt (m/* (m/cos bearing) dist)
        xdelt (m/* (m/sin bearing) dist)]
    [(m/+ x xdelt) (m/+ y ydelt)]))

(defn tri
  "Returns a triangle of points based on the starting point and radius given."
  [pt radius]
  (let [top (translate-pt pt 0.0 radius)
        right (translate-pt pt (m// (m/* 2 m/PI) 3) radius)
        left (translate-pt pt (m// (m/* 4 m/PI) 3) radius)]
    [top right left]))

(def surface (clj2d/canvas 500 500))
(def display (clj2d/show-window surface "RESPATIALIZED // STRUCTURE // SIERPINSKI"))

(def startx 300)
(def starty 300)
(def bearings (range 0.0 (* m/PI 2) (* m/PI 0.05)))

(def endpt (translate-pt [startx starty] 1.62 100))
(def endpts (map #(translate-pt [startx starty] % 100) bearings))

(defn ellipse-pts [canvas pts]
  (doseq [pt pts]
    (clj2d/ellipse canvas (first pt) (nth pt 1) 60 60 true)))

(defn lazy-frac [func startpt]
  (let [mfn (fn [c] (flatten (map func c)))]
    (iterate mfn startpt)))

(defn triflat [pts radius]
  (apply concat (map #(tri % radius) pts)))

;; data structure for sierpinski triangle: a map of the form
;; {:pts [[x1 y1] [x2 y2] ... [xn yn]]
;;  :radius 20}

(defn sierpinski-iter [smap]
  {:pts (triflat (:pts smap) (:radius smap))
   :radius (m// (:radius smap) 2)})

(def sierpoints
  (:pts
   (last
    (take-while
     #(< 0 (:radius %))
     (iterate sierpinski-iter {:pts [[250 250]] :radius 125})))))

(def current-frame
  (clj2d/with-canvas-> surface
    (clj2d/set-background 50 50 50)
    (clj2d/set-stroke 1)
    (clj2d/set-color 205 205 205)
    (ellipse-pts sierpoints)))


