(ns clojure-in-action-lein.chapter7)

;; doesn't work because the rules of functions eval the passed args meaning the functions are evaluated and THEN the if executes
;; so both functions execute before the if test can short circuit the 'then'
;(defn unless [test then]
;  (if (not test)
;    then))


;; Use a macro instead
(defmacro unless [test then]
  (list 'if (list 'not test)
        then))


(defn exhibits-oddity? [x]
  (unless (even? x)
          (println "Very odd indeed")))

;; shows that the symbols are expanded into place, not evaluated as a function, and THEN the function executes
(macroexpand 
  '(unless (even? x) (prinln "Very odd indeed")))

(defmacro unless [test then]
  `(if (not ~test)
     ~then))

;; The '`' starts the template and everything after it is verbatim
;; except things that are prefixed with '~'. That unquotes the symbol
;; expanding it like a variable. ~test and ~then are replaced with their
;; values.

;; This one allows multiple s-expressions if the test is true
(defmacro unless [test & exprs]
  `(if (not ~test)
     (do ~exprs)))
;; Fails because the & gathers all of them into a list itself so extra parens are there

;;This splices the contents of the list instead of just unquoting the list
(defmacro unless [test & exprs]
  `(if (not ~test)
     (do ~@exprs)))

