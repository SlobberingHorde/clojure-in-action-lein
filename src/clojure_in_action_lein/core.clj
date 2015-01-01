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



;;section 4.3.2 on multimethods
(defn fee-amount [percentage user]
  (float (* 0.01 percentage (:salary user))))

(defmulti affiliate-fee :referrer)

(defmethod affiliate-fee :mint.com [user]
  (fee-amount 0.03 user))

(defmethod affiliate-fee :google.com [user]
  (fee-amount 0.01 user))

(defmethod affiliate-fee :default [user]
  (fee-amount 0.02 user))

(def user-1 {:login "rob" :referrer :mint.com :salary 100000})
(def user-2 {:login "kyle" :referrer :google.com :salary 90000})
(def user-3 {:login "celeste" :referrer :yahoo.com :salary 70000})



(defn profit-rating [user]
  (let [ratings [::bronze ::silver ::gold ::platinum]]
    (nth ratings (rand-int (count ratings)))))

(defn fee-category [user]
  [(:referrer user) (profit-rating user)])

(defmulti profit-based-affiliate-fee fee-category)

(defmethod profit-based-affiliate-fee [:mint.com ::bronze] [user]
  (fee-amount 0.03 user))
(defmethod profit-based-affiliate-fee [:mint.com ::silver] [user]
  (fee-amount 0.04 user))
(defmethod profit-based-affiliate-fee [:mint.com ::gold] [user]
  (fee-amount 0.05 user))
(defmethod profit-based-affiliate-fee [:mint.com ::platinum] [user]
  (fee-amount 0.05 user))
(defmethod profit-based-affiliate-fee [:google.com ::gold] [user]
  (fee-amount 0.03 user))
(defmethod profit-based-affiliate-fee [:google.com ::platinum] [user]
  (fee-amount 0.03 user))
(defmethod profit-based-affiliate-fee :default [user]
  (fee-amount 0.02 user))

(derive ::bronze ::basic)
(derive ::silver ::basic)
(derive ::gold ::premier)
(derive ::platinum ::premier)

(defmulti affiliate-fee-for-hierarchy fee-category)
(defmethod affiliate-fee-for-hierarchy [:mint.com ::bronze] [user]
  (fee-amount 0.03 user))
(defmethod affiliate-fee-for-hierarchy [:mint.com ::silver] [user]
  (fee-amount 0.04 user))
(defmethod affiliate-fee-for-hierarchy [:mint.com ::premier] [user]
  (fee-amount 0.05 user))
(defmethod affiliate-fee-for-hierarchy [:google.com ::premier] [user]
  (fee-amount 0.03 user))
(defmethod affiliate-fee-for-hierarchy :default [user]
  (fee-amount 0.02 user))


