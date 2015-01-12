(ns clojure-in-action-lein.chapter8
  (:import (java.text SimpleDateFormat)
           (java.util Calendar GregorianCalendar)))

(defn date [date-string]
  (let [f (SimpleDateFormat. "yyyy-MM-dd")
        d (.parse f date-string)]
    (doto (GregorianCalendar.)
      (.setTime d))))

(defn day-from [d]
  (.get d Calendar/DAY_OF_MONTH))

(defn pad [n]
  (if (< n 10) (str "0" n) (str n)))

(defn month-from [d]
  (inc (.get d Calendar/MONTH)))

(defn year-from [d]
  (.get d Calendar/YEAR))

(defn as-string [d]
  (let [y (year-from d)
        m (pad (month-from d))
        d (pad (day-from d))]
    (clojure.string/join "-" [y m d])))

