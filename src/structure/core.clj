(ns structure.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [structure.plot :as plot] ))

(defn setup []
  "Initial setup fn.
   Returns the parameters passed to draw."

  (q/no-loop) ; static output
  (q/color-mode :hsb 1.0) ; changing the color space to match thi.ng
  ; setup function returns initial state. It contains
  ; circle color and position.
  {})

(q/defsketch tableau
  :title "RESPATIALIZED // STRUCTURE"
  :size [1200 1700]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :draw (var plot/draw)
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])

(defn refresh []
  (use :reload 'structure.plot)
  (.loop tableau))
