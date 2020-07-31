(ns clojure-bits-core-async.core
  (:gen-class)
  (:require [clojure.core.async :as async]))


; Channels that serve drinks
(def bar-chan (async/chan 10))
(def pub-chan (async/chan 10))

; Channel to store the received drink
(def drink-chan (async/chan 10))


(defn random-add []
"Function used to simule the passing of time taken to perform an action."
  (reduce + (conj [] (repeat 1 (rand-int 100000)))))
  
(defn request-bar-drink []
"Waits a random amount of time then puts a drink in the bar channel." 
  (async/go
    (random-add)
    (async/>! bar-chan "Here's your cocktail!")))

(defn request-pub-drink []
"Waits a random amount of time then puts a drink in the pub channel." 
  (async/go
    (random-add)
    (async/>! pub-chan "Here's your Beer mate!")))

(defn request-drink []
"Orders a drink at the bar and pub, only accepts the first one that gets returned (from the fastest service)."
  (request-bar-drink)
  (request-pub-drink)
  (async/go (let [[val] (async/alts! [bar-chan
                                      pub-chan])]
              (async/>! drink-chan val))))


(defn -main [& args]
  (println "Where's my drink?")
  (request-drink)
  (println (async/<!! drink-chan)))

