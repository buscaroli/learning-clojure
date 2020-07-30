(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
   (when (= 2 2)
     (prn "True")
     (prn "False")))

(defn error-msg
  [severity]
  (str "ERROR:"
       (if (= severity :mild)
         "Don't worry too much"
         "That's REALLY bad!")))

(def race {
           :human 8
           :elf 10
           :dwarf 6
           :hobbit 12})

(defn new-player
  [x]
  (x race 8))

(defn inc-maker
  [amount]
  #(+ % amount))

(def inc5
  (inc-maker 5))

(defn decr
  [n]
  (- n 1))

(defn count-down
  [n]
  (loop [iter n]
    (println (str "> " iter))
    (if (< iter 2)
      (println "> Zero!")
      (recur (decr iter)))))

(defn bigger
  [npc]
  {:name (:name npc) :race (str "BIG " (:race npc)) :age (:age npc)})
  
(def matt {:name "Matt" :race "Human" :age 42})
(def john {:name "John" :race "Ogre" :age 31})
(def pip {:name "Pip" :race "Hobbit" :age 65})

(def math-grades [8.0 8.0 7.5 7.0 9.0])
(def physics-grades [7.0 7.5 8.0 8.0 6.5])
(def english-grades [6.0 6.5 7.0 7.5 7.0])

(def sum #(reduce + %))

(def avg #(/ (sum %) (count %)))

(defn stats
  [grades]
  (map #(% grades) [sum count avg]))

;; Can also do (map stats [math-grades physics-grades english-grades])

(defn tabulize-grades
  [math phys engl]
  {:math math 
   :physics phys 
   :english engl})

(defn school-grades
  [math phys engl]
  (map tabulize-grades math phys engl))

(def phone-reg
  [{:name "Matt" :number 123456}
   {:name "Mel" :number 345612}
   {:name "John" :number 243567}
   {:name "Sue" :number 543765}])

(defn get-names
  [phone-reg]
  (map :name phone-reg))

(defn get-nums
  [phone-reg]
  (map :number phone-reg))
