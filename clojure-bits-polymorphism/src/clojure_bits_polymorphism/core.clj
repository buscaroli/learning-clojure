(ns clojure-bits-polymorphism.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

;; There are different ways of implementing polymorphism in
;; Clojure.

; FIRST: Using the function 'cond'
; If none of the comparisons return true the function returns nil.
(defn get-input-type [input]
  (cond
    (= java.lang.String (class input)) "Input is a String."
    (= clojure.lang.Keyword (class input)) "Input is a Keyword."
    (= java.lang.Long (class input)) "Input is a Long Integer."))

; SECOND: Using Clojure's Multimethods
; We first define the multimethod and a function that specifies how
; it's going to dispatch, in this case the dispatch is going to be
; on the class of the input, but we can also use a function made by us.
; Our function is going to:
;   - be a multimethod (defmulti)
;   - be dispatched on the 'class' function
;   - take one argument (as the class function only takes one argument)
(defmulti get-input-type-mm class)

; We add one defmethod for each case we want to consider, eg String,
; Keyword, Int etc.
; It's possible to define a 'default' case.
(defmethod get-input-type-mm java.lang.String [input]
  (str "Input is of type String - " input))

(defmethod get-input-type-mm clojure.lang.Keyword [input]
  (str "Input is of type Keyword - " input))

(defmethod get-input-type-mm java.lang.Long [input]
  (str "Input is of type Long Integer - " input))

(defmethod get-input-type-mm :default [input]
  (str "This sloppy function doesn't recognise this input - " input))

; Let's try usimg our own function
(defmulti adjust-speed (fn [speed]
                         (if (< speed 50) 
                           :faster
                           :slower)))

(defmethod adjust-speed :faster [_]
  println "You are going too slow...")

(defmethod adjust-speed :slower [_]
  println "Are you trying to kill me? Slow Down!")

;; THIRD: Using 'Protocols'
; Multimethods are great for using polymorphism on one function,
; Protocols may be better for handling polymorphism with Group
; Of Functions.
; First we define the protocol
(defprotocol EatCake
  (eat-cake [this]))

; Then we implement the protocol for all types using 'extend-protocol'
(extend-protocol EatCake
  java.lang.String
  (eat-cake [this]
    (str "The cake is " this "!"))
  
  clojure.lang.Keyword
  (eat-cake [this]
    (case this
      :small "You are a cheapo..."
      :big "This cake must have cost a fortune!"
      "This cake is weird..."))
  
  java.lang.Long
  (eat-cake [this]
    (if (< this 15)
      (str "You paid " this " quids for this? Cheapo...")
      (str "You paid " this " quids for this? Is it made of GOLD?!?"))))





