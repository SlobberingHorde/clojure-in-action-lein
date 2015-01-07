(ns clojure-in-action-lein.chapter6)

;; Declare a mutable managed reference
(def all-users (ref {}))

(deref all-users)

;; Function to create a new user
(defn new-user [id login monthly-budget]
  {:id id
   :login login
   :monthly-budget monthly-budget
   :total-expenses 0})

;; Alter function that uses dosync and alter to add a new user to the managed ref
(defn add-new-user [login budget-amount]
  (dosync
    (let [current-number (count @all-users)
          user (new-user (inc current-number) login budget-amount)]
      (alter all-users assoc login user))))

;; alter takes a ref, a function and its args.
;; In this case it takes the assoc fn that takes the all-users, and adds the user with the key of login to it

;; adds jerry with a monthly budget of 150.00
(add-new-user "jerry" 150.00)

;; using an agent to update asynchronously
;; this example sets up a bad scenario
(def bad-agent (agent 10))

(send bad-agent / 0) ;Causes divide by zero
(deref bad-agent)
;;The book implies deref'ing this will give an exception but my test doesn't, it returns the old value
;; using (agent-errors bad-ref) returns a list of exceptions so that is how to detect it
(agent-errors bad-agent)

;; prints out the stacktrace, as it were
(use '[clojure.string :only (join)])

(let [e (first (agent-errors bad-agent))
      st (.getStackTrace e)]
  (println (.getMessage e))
  (println (join "\n" st)))

(clear-agent-errors bad-agent)

;refs are synchronous and coordinated in STM
;agents are asynchronous and independent in STM
;atoms are synchronous and independent so no need for transactions

;;example of a watcher to pay attention to mutation
(def adi (atom 0))

(defn on-change [the-key the-ref old-value new-value]
 (println "Hey, seeing change from" old-value "to" new-value))

(add-watch adi :adi-watcher on-change)

;(swap! adi inc) ; causes the watch to fire
;(remove-watch adi :adi-watcher) ; removes the watch from the mutable object


;;futures in Clojure
(defn long-calculation [num1 num2]
  (java.lang.Thread/sleep 5000)
  (* num1 num2))

(defn long-run []
  (let [x (long-calculation 11 13)
        y (long-calculation 13 17)
        z (long-calculation 17 19)]
    (* x y z)))

(time (long-run))
 
(defn fast-run []
  (let [x (future (long-calculation 11 13))
        y (future (long-calculation 13 17))
        z (future (long-calculation 17 19))]
    (* @x @y @z)))

(time (fast-run))

    
