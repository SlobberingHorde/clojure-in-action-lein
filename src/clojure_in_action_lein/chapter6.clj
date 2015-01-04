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