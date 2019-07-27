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
  (let [ydelt (m/* (m/sin bearing) dist)
        xdelt (m/* (m/cos bearing) dist)]
    [(m/+ x xdelt) (m/+ y ydelt)]))

(defn tri
  "Returns a triangle of points based on the starting point and radius given."
  [pt radius]
  (let [top (translate-pt pt 0.0 radius)
        right (translate-pt (m// (m/* 2 m/PI) 3))
        left (translate-pt (m// (m/* 4 m/PI) 3))])
  )

(def surface (clj2d/canvas 500 500))
(def display (clj2d/show-window surface "RESPATIALIZED // STRUCTURE // SIERPINSKI"))

(def startx 300)
(def starty 300)
(def bearings (range 0.0 (* m/PI 2) (* m/PI 0.05)))

(def endpt (translate-pt [startx starty] 1.62 100))
(def endpts (map #(translate-pt [startx starty] % 100) bearings))

(defn ellipse-pts [canvas]
  (doseq [pt endpts]
    (clj2d/ellipse canvas (first pt) (nth pt 1) 10 10)))

(def current-frame
  (clj2d/with-canvas-> surface
    (clj2d/set-background 10 5 5)
    (clj2d/set-color 210 210 200)
    (clj2d/rect 100 100 200 200) ;; draw rectangle
    (clj2d/set-color 255 0 0)
    (clj2d/ellipse (first endpt) (nth endpt 1) 10 10)
    (clj2d/set-color 0 255 0)
    (clj2d/ellipse startx starty 10 10)
    (clj2d/set-color 0 0 255)
    ellipse-pts))


