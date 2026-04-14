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
          (println (str (inc idx)) ". ["status"] " (:text task)))))))

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

(defn delete-task [])

(defn remove-index [])

(defn -main []
  (loop []
    (println "Welcome to ToDo task tracker")
    (println "1. Add task")
    (println "2. Show tasks")
    (println "3. Change the task state")

    (println "5. Quit")

    (println "Choose the action:")
    (let [choice (read-line)]
      (cond
        (= choice "1") (add-task)
        (= choice "2") (show-tasks)
        (= choice "3") (toggle-tasks)
        (= choice "5") (do (println "Bye bye!") (System/exit 0))
        :else          (println "Wrong choice"))
      (recur))))
