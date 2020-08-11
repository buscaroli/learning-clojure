(ns clojure-bits-data-structures.core
  (:require [clojure.string :as str]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(def list1 [:a :b :c :d :e])
(def list2 ["Matt" "Mel" "John"])

(defn swap-12 [collection]
  (into (empty collection)
        (interleave 
         (take-nth 2 (drop 1 collection))
         (take-nth 2 collection))))

(def compilation [
                  {:title "Zooropa" :band "U2" :year "1990"}
                  {:title "Nord Sud Ovest Est" :band "883" :year "1992"}
                  {:title "La Donna Cannone" :band "De Gregori" :year "1984"}
                  {:title "Attenti al Lupo" :band "Lucio Dalla" :year "1996"}
                  {:title "Wonder What" :year "2000"}
                  {:title "Come Mai" :band "883" :year "1992"}
                  {:title "Hanno ucciso l'Uomo Ragno" :band "883" :year "1992"}
                  {:title "Another song" :year "2000"}])

(defn summary [{:keys [title band year]}]
  (str "Title: " title ", released in " year ". (" band ")."))


(defn sum-collection 
  ([coll] (sum-collection coll 0))
  ([coll acc] 
   (if (empty? coll)
     acc
     (recur (rest coll) (+ (first coll) acc)))))

(defn clean-phrase 
  [phrase]
  ((comp str/capitalize str/trim) phrase))

(def character-list [
                     {:name "Gandalf"
                      :attr {:str 12
                             :dex 12
                             :int 20}}
                     {:name "Frodo"
                      :attr {:str 8
                             :dex 18
                             :int 16}}
                     {:name "Legolas"
                      :attr {:str 14
                             :dex 20
                             :int 14}}
                     {:name "Gimli"
                      :attr {:str 18
                             :dex 10
                             :int 8}}])

(defn get-names
"Get the names of the Characters." 
  [coll]
  (map (comp :name) coll))

(defn get-strength
"Get the Strengths of the Characters."
  [coll]
  (for [x coll]
    (:str (:attr x))))

(defn get-strength2
"Get the Strengths of the Characters, using 'comp'."
  [coll]
  (for [x coll]
    ((comp :str :attr) x)))

(defn get-str
"Gets the Names and Strengths of the Characters."
  [coll]
  (for [x coll]
    (str (:name x) ": "((comp :str :attr) x))))

(defn get-int
"Gets the Names and Intelligence values of the Characters."
  [coll]
  (for [x coll]
    (str (:name x) ": "((comp :int :attr) x))))

(defn get-dex
"Gets the Names and the Dexterity values of the Characters."
  [coll]
  (for [x coll]
    (str (:name x) ": "((comp :dex :attr) x))))

(defn print-stats 
"Prints the Stats of the Characters."
  [coll]
  (do
    (println (get-str coll))
    (println (get-dex coll))
    (println (get-int coll))))

(defn get-hi-dex-chars
"Gets the Characters with a Dex higher than 12."
  [coll]
  (for [x coll
        :when (> ((comp :dex :attr) x) 12)] 
    (:name x)))

(defn get-hi-attr-chars
"Gets the CHaracters with a specified attribute higher than 12."
  [selection coll]
  (for [x coll
        :when (> ((comp selection :attr) x) 12)] 
    (:name x)))

(defn get-selected-chars
"Gets the Characters whose attributes depend on the given function."
  [func coll]
  (for [x coll
        :when (func x)] 
    (:name x)))

(defn get-higher-then-attr-chars
"Gets the Characters whose selected attribute is higher than the specified value."
  [attribute val coll]
  (for [x coll
        :when (> ((comp attribute :attr) x) val)] 
    (:name x)))

