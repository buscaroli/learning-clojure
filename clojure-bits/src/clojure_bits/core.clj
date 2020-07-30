(ns clojure-bits.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn greet [name language]
  (if (= language :italian)
    (str "Buon Giorno " name)
    (str "Good Day " name)))

; Partial application
(def greet-matt 
  (partial greet "Matt"))

(def numbers [10 15 20 25 30])

(defn multiply-5
  [n]
  (* n 5))

(defn add-10
  [n]
  (+ n 10))

; Composition
; Same as (add-10 (multiply-5 n))
(defn mult5-then-add10
  [n]
  ((comp add-10 multiply-5) n))

(defn adder
  [x y]
  (+ x y))

; Partial application
(def add-5 (partial adder 5))

(defn roll-dice
  []
  (+ 1 (rand-int 6)))

; Multi-arity function
(defn dice-roller
  ([] "How many faces for the dice?")
  ([n] (+ 1 (rand-int n))))


; NB If using 'def' instead of 'defn' you have to call the function without
; the (), eg 'roll-20' instead of '(roll-20)' !!
(defn roll-20 
  []
  (dice-roller 20))

; repeat not= repeatedly 
; -> true
; try to call the following two functions

; repeat returns a value over and over again
; in fact needs to be called with () eg '(roll-dice)'
(defn repeat-5
  []
  (repeat 5 (roll-dice)))

; repeatedly repeats a function over and over again
; in fact doesn't need to be called with () eg 'roll-dice'
(defn repeatedly-5
  []
  (repeatedly 5 roll-dice))

; cycle cycles through the elements of a collection generating an infinite list
(defn cycle-12
  [collection]
  (take 12 (cycle collection)))

; Recursion
; Pass a collection of strings to the function
(defn weather-today
"Takes a collection of strings and an empty collection and returns a list of phrases with the items inside each string."
  [in out]
  (if (empty? in) out
      (weather-today (rest in)
                     (conj out (str "Today is " (first in))))))
  
; Easier Recursive version using 'loop'
; Note there is no need to pass an empty collection as a local variable is created within the loop declaration.
(defn weather-today-loop
  [collection]
  (loop [in collection
         out []]
    (if (empty? in) out
        (recur (rest in)
               (conj out (str "Today is " (first in)))))))


(def animals ["Giraffe" "Elephant" "Cat" "Dog" "Monkey" "Gorilla"])

; Try this function in map adding a lazy sequence
; eg (map add-to-start (range) animals)
; eg (map add-to-start (cycle ["one" "two" "three"]) animals)
(defn add-to-start
  [a b]
  (str a ". " b))

