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

;;Section 3.2.1 on binding
;;=> (def ^:dynamic *db-host* "localhost")
;;#'clojure-in-action-lein.core/*db-host*
;;=> (defn expense-report [start-date end-date]
;;     (println *db-host*))
;;#'clojure-in-action-lein.core/expense-report
;;=> (expense-report "2014-07-14" "2014-12-31")
;;localhost
;;nil
;;=> (binding [*db-host* "production"]
;;     (expense-report "2014-07-14" "2014-12-31"))
;;production
;;nil


;; section 3.5 - cut n paste and run these in the repl to see the results of each call-twice return val
(defn ^:dynamic twice [x]
  (println "original function")
  (* 2 x))

(defn call-twice [y]
  (twice y))

(defn with-log [function-to-call log-statement]
  (fn [& args]
    (println log-statement)
    (apply function-to-call args)))

(call-twice 10)

(binding [twice (with-log twice "Calling the twice function")]
  (call-twice 20))

(call-twice 30)

;;Because bindings are thread-local and functions like map are lazy you must force the lazy functions (using doall)
;;to realize the value at the correct time. Otherwise the binding will go out of scope before the values are 
;;realized and the results will not be what you expect


;;3.2.3 Closures over free vars
(defn create-scalar [scale]
  (fn [x]
    (* x scale)))

(def percent-scalar (create-scalar 100))

(def another-scalar (create-scalar 2))




