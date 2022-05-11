(ns pedestal-sample-project.views.layout
  (:require [ring.util.response :as ring-resp]
            [hiccup.core :as hcp-core]
            [hiccup.page :as hcp-page]))


(defn header-components
  [title]
  (list
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   [:title title]
   [:link {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
           :rel "stylesheet"
           :integrity "sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
           :crossorigin "anonymous"}]))

(defn script-components
  []
  (list
   [:script {:src "https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
             :integrity "sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
             :crossorigin "anonymous"}]
   [:script {:src "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
             :integrity "sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
             :crossorigin "anonymous"}]))

(defn layouts
  [title headers inputan & body]
  (ring-resp/response (hcp-page/html5
                       {:lang "en"}
                       (hcp-core/html

                        [:head (header-components title)]

                        [:body
                         [:div
                          {:class "container"}
                          headers]
                         [:br]
                         [:div
                          {:class "container"}
                          [:div {:class "row"}
                           [:div {:class "col-md-3"}]
                           [:div {:class "col-md-6"}
                            inputan]
                           [:div {:class "col-md-3"}]]
                          [:div {:class "row mt-5"}
                           [:div {:class "col-md-2"}]
                           [:div {:class "col-md-8"}
                            body]
                           [:div {:class "col-md-2"}]]
                          (script-components)]]))))
