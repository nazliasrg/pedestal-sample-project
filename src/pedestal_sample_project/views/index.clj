(ns pedestal-sample-project.views.index
  (:require [pedestal-sample-project.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as hcp-form]
            [ring.util.anti-forgery :as anti-forgery]))

(defn form-format
  []
  [:div {:id "form-project" :class "sixteen columns alpha omega"}
   (hcp-form/form-to [:post "/"]
                     [:div
                      {:class "form-group"}
                      (anti-forgery/anti-forgery-field)
                      [:div {:class "row"}
                       [:div {:class "col-md-2"}]
                       [:div {:class "col-md-8"}
                        (hcp-form/text-area {:class "form-control mt-1"} "f")
                        (hcp-form/submit-button {:class "btn btn-primary mt-3"} "Tambah Data")]
                       [:div {:class "col-md-2"}]]])])


(defn display-index
  [forms]
  [:div
   (map
    (fn [f] [:div {:class "card mt-3"}
             [:div {:class "card-header"}
              [:small {:class "text-muted"}
               [:strong (str "ID : " (h (:id f)))]]]
             [:div {:class "card-body text-center"}
              (h (:body f))]])
    forms)])

(defn index 
  [forms]
  (layout/layouts "Pedestal Sample Project"
                  [:div {:class "mt-5 text-center"}
                   [:h1 "Pedestal Sample Project"]]
                  [:center (form-format)]
                  [:div {:class "mt-3"}]
                  (display-index forms)))