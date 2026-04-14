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

(defn toggle-tasks [])

(defn delete-task [])

(defn remove-index [])

(defn -main []
  (loop []
    (println "Welcome to ToDo task tracker")
    (println "1. Add task")

    (println "Choose the action:")
    (let [choice (read-line)]
      (cond
        (= choice "1") (add-task)
        (= choice "5") (do (println "Bye bye!") (System/exit 0))
        :else          (println "Wrong choice"))
      (recur))))
