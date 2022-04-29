(ns pedestal-sample-project.views.index
  (:require [pedestal-sample-project.views.layout :as layout]
            [hiccup.core :as hcp-core]
            [hiccup.page :as hcp-page]
            [hiccup.form :as hcp-form]
            [ring.util.anti-forgery :as anti-forgery]))

(defn form-format
  []
  [:div {:id "form-project" :class "sixteen columns alpha omega"}
   (hcp-form/form-to [:post "/"]
                 (anti-forgery/anti-forgery-field)
                 (form/label "shout" "What do you want to SHOUT?")
                 (form/text-area "shout")
                 (form/submit-button "SHOUT!"))])