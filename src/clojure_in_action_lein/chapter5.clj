(ns clojure-in-action-lein.chapter5
  (:import (java.text SimpleDateFormat)
           (java.util Calendar)
           (java.util TimeZone)))

(defn date-from-string [date-string]
  (let [sdf (SimpleDateFormat. "yyyy-MM-dd")]
    (.parse sdf date-string)))

(.. 
  (Calendar/getInstance)
  (getTimeZone)
  (getDisplayName true TimeZone/SHORT))

(defn the-past-midnight2 []
  (let [calendar-obj (Calendar/getInstance)]
    (.set calendar-obj Calendar/AM_PM Calendar/AM)
    (.set calendar-obj Calendar/HOUR 0)
    (.set calendar-obj Calendar/MINUTE 0)
    (.set calendar-obj Calendar/SECOND 0)
    (.set calendar-obj Calendar/MILLISECOND 0)
    (.getTime calendar-obj)))


;; Method to extract the weekYear from a Clojure map representing Calendar.getInstance()
(defn get-week-year [calendar-map]
  (:weekYear calendar-map))

;; Converts a java bean, Calendar instance in this case, into an immutable Clojure map
(bean (Calendar/getInstance))


;; Dealing with Java Arrays
(def tokens (.split "clojure.in.action" "\\."))
(alength tokens)
(aget tokens 2)
(aset tokens 2 "actionable")
