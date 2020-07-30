(ns clojure-bits-state.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

; Atom: mutable variable, used to keep state
; Can check it out with: 
;   - who-atom
;   - @who-atom
; Can modify it with:
;   - reset!
;      (reset! who-atom :butterfly)
;   - swap!
;      to apply a function to it:
;         (swap! who-atom change)
(def who-atom (atom :caterpillar))

(defn change [state]
  (case state
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly))

; Atom called counter
; We can call swap! 5 times to increase it like this:
;   - (dotimes [_ 5] (swap! counter inc))
; We can have 3 different threads increase counter like this:
;   - (let [n 5]
;       (future (dotimes [_ 5] (swap! counter inc)))
;       (future (dotimes [_ 5] (swap! counter inc)))
;       (future (dotimes [_ 5] (swap! counter inc))))
; If the function called with swap! are kept side-effects free
; clojure makes sure the operations are atomic and consistent and
; we don't have to worry about the possibility of one of the thread
; to mess up some other function's job giving the wrong output.
(def counter (atom 0))

(defn thread-inc-5-times 
  []
  (let [n 5]
    (future (dotimes [_ 5] (swap! counter inc)))
    (future (dotimes [_ 5] (swap! counter inc)))
    (future (dotimes [_ 5] (swap! counter inc)))))

; Impure function: print the value (side-effect) then
; increases it.
(defn inc-print [val]
  (println val)
  (inc val))

; Even if the atom is accessed at the same time by multiple
; functions, the way swap! is implemented makes sure that
; another thread hasn't changed the value in the meantime by
; comparng the current value of the atom again.
; Calling the following function you might notice that some of
; the numbers are printed more than once as one of the thread
; noticed a change on the value while they were working on it,
; however the final result is correct (+ 15) as the operation
; is retried.
(defn thread-inc-5-times-impure 
  []
  (let [n 5]
    (future (dotimes [_ 5] (swap! counter inc-print)))
    (future (dotimes [_ 5] (swap! counter inc-print)))
    (future (dotimes [_ 5] (swap! counter inc-print)))))

; Refs are used when more than one thing have to change in
; a coordinated way.
; We define Alice's height and the amount of the growing
; biscuits she has on her right hand.
(def alice-height (ref 3))
(def righthand-bites (ref 10))

; the we define a function that increases Alice's height
; by 24% every time she has a biscuit.
; If the number of biscuits is >0 (pos?) we decrease that
; number and increase Alice's size. These multiple changes
; are carried out using alter but we need to include the
; logic within a dosync block!!
; REFS could be very useful if implementing a transaction
; in a Banking apllication as two accounts need to be altered
; in a  concurrent and coordinated way.
; An Alternative, faster way of run concurrent coordinated
; changes is by using 'commute' instead of 'alter', but it 
; can only be used on commutative functions AND 'commute'
; WILL NOT RETRY!!
(defn eat-biscuit-right-hand
  []
  (dosync (when (pos? @righthand-bites)
            (alter righthand-bites dec)
            (alter alice-height #(+ % 24)))))

; When calling this function, we are doing concurrent and
; coordinated changes.
(defn thread-eat-right-hand 
  []
  (let [n 2]
    (future (dotimes [_ 5] (eat-biscuit-right-hand)))
    (future (dotimes [_ 5] (eat-biscuit-right-hand)))
    (future (dotimes [_ 5] (eat-biscuit-right-hand)))))

;;
;; ATOMS:  Independent Synchronous  Changes
;; REFS:   Coordinated Synchronous  Changes
;; Agents: Independent Asynchronous Changes
;;

; Defining an agent, check it out with '@name' as you
; would with an atom (dereferencing).
(def character (agent :caterpillar))

; Let's create a function to change the state of the
; character.
(defn change-character
  [state]
  (case state
    :caterpillar :chrysalid
    :chrysalid :butterfly
    :butterfly :egg
    :egg :caterpillar))

; The state of an agent can be changed with 'send'.
(defn circle-of-life [char-name] 
  (send char-name change-character))

; HOWEVER for potentially IO BLOCKING actions use 'send-off'
; and use 'send' for CPU-bound operations.
(defn circle-of-life-IO [char-name] 
  (send-off char-name change-character))
