(ns pedestal-sample-project.models.models
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "jdbc:mysql://localhost:3306/pedestal_sample_project?user=root"))

(defn all
  []
  (into [] (sql/query spec ["SELECT * FROM form_tbl ORDER BY id DESC"])))

(defn create
  [f]
  (sql/insert! spec :form_tbl [:body] [f]))

