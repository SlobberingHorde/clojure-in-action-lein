(ns clojure-in-action-lein.core)

;;map of user info used for the functions below
(def users [
            {:username "kyle"
             :balance 175.00
             :member-since "2009-04-16"}
            {:username "zak"
             :balance 12.95
             :member-since "2009-02-01"}
            {:username "rob"
             :balance 97.50
             :member-since "2009-03-30"}
            ])

;;takes a user map and looks up the username
(defn username [user]
  (user :username))

;;takes a user map and looks up the balance
(defn balance [user]
  (user :balance))

;;takes an ordering function as a param and returns a function using that fn as the sorter
(defn sorter-using [ordering-fn]
  (fn [users]
    (sort-by ordering-fn users)))

;;creates a function that uses the balance as the sort value and assigns it to the var poorest-first
(def poorest-first (sorter-using balance))

;;creates a function that uses the username as the sort value and assigns it to the var alphabetically
(def alphabetically (sorter-using username))


;;Call these functions like so: (alphabetically users) and (poorest-first users)
