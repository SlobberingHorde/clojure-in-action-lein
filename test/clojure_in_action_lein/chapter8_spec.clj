(ns clojure-in-action-lein.chapter8-spec 
  (:use clojure-in-action-lein.chapter8)
  (:use clojure.test))

(deftest test-simple-data-parsing
  (let [d (date "2009-1-22")]
    (is (= (day-from d) 22))
    (is (= (month-from d) 1))
    (is (= (year-from d) 2009))))
