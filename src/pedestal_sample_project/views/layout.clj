(ns pedestal-sample-project.views.layout
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
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
  [title headers & body]
  (ring-resp/response (hcp-page/html5
                       {:lang "en"}
                       (hcp-core/html
                        
                        [:head (header-components title)]

                        [:body
                         [:div#header
                          {:class "container"
                           :style {:margin-top 50}}
                          headers]
                         [:br]
                         [:div 
                          {:class "container"}
                          body
                          
                          (script-components)]
                         ]))))

(defn four-oh-four []
  (layouts "Page Not Found"
          [:div {:id "four-oh-four"}
           "The page you requested could not be found"]))