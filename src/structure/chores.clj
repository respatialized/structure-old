(ns structure.chores
  (:require [java-time :as time]))

(def chores
  {:weekly #{"clean kitchen" "restock food + supplies" "mop floors"
             "launder clothes" "launder linens" "water + check on plants"
             "clean bathroom" "change sheets" "dust surfaces" "vacuum bedroom"
             "vacuum common areas"}
   :daily #{"neaten" "put clothes away" "cook" "post-cooking cleanup"
            "clean up dishes" "sweep bedroom" "sweep kitchen"}})

(def essential-chores #{"clean kitchen" "restock food + supplies" "launder clothes"
                        "sweep bedroom" "cook" "post-cooking cleanup" "clean up dishes"
                        "sweep kitchen"})

(def inessential-chores (clojure.set/difference
                         (apply clojure.set/union (vals chores))
                         essential-chores))

(def weekend-chores #{"clean kitchen" "launder clothes" "vacuum common areas"})
(def weeknight-chores (clojure.set/difference
                       (apply clojure.set/union (vals chores))
                       weekend-chores))
;; another key dimension: salience
(def importance ["essential daily" "inessential daily" "essential weekly" "inessential weekly"])
;; salience is time-dependent: on the weekends, the essential weekly chores take priority over
;; the daily ones.

(defn valid-chore? [chore date]
  "Returns the chore if it could be done on that day."
  (if ((:daily chores) chore) chore
      (let [dow (time/value (time/property date :day-of-week))]
        (if (and (#{1 2 3 4 5 6} dow) (weekend-chores chore)) nil
            chore))))

(defn mandatory-chore? [chore date]
  "Returns the chore if it must be done on that day."
  (if (inessential-chores chore) nil
      ((:daily chores) chore)))


(defn make-week
  "Produces a series of maps from Periods to chores.
   Takes a LocalDate or three ints (year month day) as input, or the current date.
   Assumes the chores should be completed within the Period."
  ([] (make-week (time/local-date)))
  ([year month day] (make-week (time/local-date year month day)))
  ([start-date]
   (let [end-date (time/plus start-date (time/days 6))
         week (take 7 (time/iterate time/plus start-date (time/days 1)))
         weekend (filter (fn [d] (#{0 7} (time/property d :day-of-week))) week)]
     (assoc (into {} (for [day week] [(time/format "YYYY/MM/dd" day) (:daily chores)]))
            "weekly" (:weekly chores)))))
