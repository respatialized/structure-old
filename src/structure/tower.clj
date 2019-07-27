(ns structure.tower
  (:require
   [thi.ng.geom.core :as g]
   [thi.ng.geom.polygon :as p]))

;; a tower is just a collection of geometries.
;; we can add constraints as we go.
(def sample-tower
  {
   :pediment []
   :main []
   :capital []
   })


;; don't abstract too early!

;; component 1: the basic "blocks" that an antenna is built from.
;; specified by a 4 point polygon.

(def sample-block (p/polygon [[50 50] [0.2 0.1] [0.2 0.2] [0.1 0.2] [0.1 0.1]])
  )

;; render 1: a box with crossbeams
(defn basic-support [pts])
