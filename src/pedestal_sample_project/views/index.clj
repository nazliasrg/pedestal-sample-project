(ns pedestal-sample-project.views.index
  (:require [pedestal-sample-project.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.page :as hcp-page]
            [hiccup.form :as hcp-form]
            [ring.util.anti-forgery :as anti-forgery]))

(defn form-format
  []
  [:div {:id "form-project" :class "sixteen columns alpha omega"}
   (hcp-form/form-to [:post "/"]
                     [:div
                      {:class "form-group"}
                      (anti-forgery/anti-forgery-field)
                      (hcp-form/label "f" "Masukkan data yang ingin ditambahkan!") [:br]
                      (hcp-form/text-area {:class "form-control"} "f" ) [:br]
                      (hcp-form/submit-button {:class "btn btn-primary"} "Tambah Data")]
                 )])


(defn display-index 
  [forms]
  [:div {:class "card"
         :style {:width "100px"}}
   [:ul {:class "list-group list-group-flush"}
    (map
     (fn [f] [:li {:class "list-group-item text-center bg-light"} (h (:body f))])
     forms)]
   ])

(defn index 
  [forms]
  (layout/layouts "Pedestal Sample Project"
                  [:div {:class "mt-5 text-center"}
                   [:h1 "Pedestal Sample Project"]]
                  [:center (form-format)]
                  [:div {:class "mt-5"}]
                  (display-index forms)))