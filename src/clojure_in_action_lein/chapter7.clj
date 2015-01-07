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