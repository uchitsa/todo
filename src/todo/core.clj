(ns todo.core
  (:gen-class))

(def tasks (atom []))

(defn add-task []
  (println "Enter the new task:")
  (let [task (read-line)]
    (swap! tasks conj {:text task :done false})
    (println "Task added!")))

(defn show-tasks []
  (if (empty? @tasks)
    (println "Task list is empty")
    (do (println "Your tasks:")
      (doseq [[idx task] (map-indexed vector @tasks)]
        (let [status (if (:done task) "✅" " ")]
          (println (str (inc idx)) ". [" status "] " (:text task)))))))

(defn toggle-tasks []
  (show-tasks)
  (println "Enter the number of task to change the state:")
  (let [idx (read-line)]
    (if (and
         (number? (try (Integer/parseInt idx) (count @tasks) (catch Exception _ nil)))
         (> (Integer/parseInt idx) 0)
         (<= (Integer/parseInt idx) (count @tasks)))
      (do
        (swap! tasks update (dec (Integer/parseInt idx)) #(assoc % :done (not (:done %))))
        (println "State is changed!"))
      (println "Wrong number"))))


(defn remove-index [coll idx]
  (concat (take idx coll) (drop (inc idx) coll)))

(defn delete-task []
  (show-tasks)
  (println "Enter the number of task to delete:")
  (let [idx (read-line)]
    (if (and (number? (try (Integer/parseInt idx) (catch Exception _ nil)))
             (> (Integer/parseInt idx) 0)
             (<= (Integer/parseInt idx) (count @tasks)))
      (do
        (swap! tasks (fn [ts] (vec (remove-index ts (dec (Integer/parseInt idx))))))
        (println "The task is deleted!"))
      (println "Wrong number"))))


(defn -main []
  (loop []
    (println "Welcome to ToDo task tracker")
    (println "1. Add task")
    (println "2. Show tasks")
    (println "3. Change the task state")
    (println "4. Delete the task")
    (println "5. Quit")

    (println "Choose the action:")
    (let [choice (read-line)]
      (cond
        (= choice "1") (add-task)
        (= choice "2") (show-tasks)
        (= choice "3") (toggle-tasks)
        (= choice "4") (delete-task)
        (= choice "5") (do (println "Bye bye!") (System/exit 0))
        :else          (println "Wrong choice"))
      (recur))))
