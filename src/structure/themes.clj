(ns structure.themes
  (:require [clojure.data.json :as json]
            [thi.ng.color.core :as color]
            [clojure.java.io :as io]))

;; themes - a namespace for working with color schemes

(defn expand-kw [keystr]
  "Converts the key string into a keyword and inserts a digit if necessary"
  (let [numstr (last (clojure.string/split keystr #"color"))]
    (keyword
     (str
      "color"
      (format "%02d" (Integer/parseInt numstr))))))

(defn get-key-num [kw]
  "Gets the number value of the given color keyword."
  (-> kw
      name
      (#(clojure.string/split % #"color"))
      last
      (Integer/parseInt))
  )

(defn fetch-colors
  "Returns the colors from the given wal JSON file."
  ([]
   (fetch-colors "/home/andrew/.cache/wal/colors.json"))
  ([path]
   (let [cs
         (get (-> path
                  slurp
                  json/read-str) "colors")]
     (into (sorted-map) (for [[k colorcode] cs] [(expand-kw k) colorcode])))))
