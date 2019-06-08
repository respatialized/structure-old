(ns structure.tower
  (:require [thi.ng/geom :as geom]))

;; a tower is just a collection of geometries.
;; we can add constraints as we go.
(def sample-tower
  {
   :pediment []
   :main []
   :capital []
   })


;; don't abstract too early!
