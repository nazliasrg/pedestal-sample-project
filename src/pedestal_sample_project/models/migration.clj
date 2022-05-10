(ns pedestal-sample-project.models.migration
  (:require [clojure.java.jdbc :as sql]
            [pedestal-sample-project.models.models :as models]))

(defn migrated?
  []
  (-> (sql/query models/spec
                 [(str "SELECT COUNT(*) FROM information_schema.tables "
                       "WHERE table_name='form_tbl'")])
      first :count pos?))

(defn migrate
  []
  (when (not (migrated?))
    (print "Creating database structure...") (flush)
    (sql/db-do-commands models/spec
                        (sql/create-table-ddl
                         :form_tbl
                         [[:id :serial "PRIMARY KEY"]
                          [:body :varchar "NOT NULL"]
                          [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]]))
    (println " done")))


