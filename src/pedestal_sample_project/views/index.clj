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
                      {:class "card p-5 form-group"}
                      (anti-forgery/anti-forgery-field)
                      (hcp-form/label "f" "Masukkan data yang ingin ditambahkan!")
                      (hcp-form/text-area {:class "form-control mt-2"} "f")
                      (hcp-form/submit-button {:class "btn btn-primary mt-2"} "Tambah Data")]
                 )])


(defn display-index 
  [forms]
  [:div 
    (map
     (fn [f] [:div {:class "card mt-2"}
              [:div {:class "card-header"}
               [:small 
                [:strong (str "ID : " (h (:id f)))]]]
              [:div {:class "card-body text-center"} (h (:body f))]])
     forms)
   ])

(defn index 
  [forms]
  (layout/layouts "Pedestal Sample Project"
                  [:div {:class "mt-5 text-center"}
                   [:h1 "Pedestal Sample Project"]]
                  [:center (form-format)]
                  [:div {:class "mt-4"}]
                  (display-index forms)))