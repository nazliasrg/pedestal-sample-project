(ns pedestal-sample-project.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [pedestal-sample-project.views.index :as view]
            [pedestal-sample-project.models.models :as model]
            [clojure.string :as str]
            [io.pedestal.interceptor :as interceptor]
            [cheshire.core :as json]))


(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))


(defn about-page2
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [request]
  (view/index (model/all)))

(def content-length-json-body
  (interceptor/interceptor
   {:name ::content-length-json-body
    :leave (fn [context]
             (let [response (:response context)
                   body (:body response)
                   json-response-body (if body (json/generate-string body) "")
                    ;; Content-Length is the size of the response in bytes
                    ;; Let's count the bytes instead of the string, in case there are unicode characters
                   content-length (count (.getBytes ^String json-response-body))
                   headers (:headers response {})]
               (assoc context
                      :response {:status (:status response)
                                 :body json-response-body
                                 :headers (merge headers
                                                 {"Content-Type" "application/json;charset=UTF-8"
                                                  "Content-Length" (str content-length)})})))}))

(defn create
  [f]
  ;; (ring-resp/response (str ((vec (vals (:form-params f))) 1)))
  (when-not (str/blank? (str ((vec (vals (:form-params f))) 1)))
    (model/create (str (str ((vec (vals (:form-params f))) 1)))))
  (ring-resp/redirect "/")
  )

;; Defines "/" and "/about" routes with their associated :get handlers.
;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params) http/html-body])
(def custom-interceptors [(body-params/body-params) content-length-json-body])

;; Tabular routes
(def routes #{["/" :get (conj common-interceptors `home-page)]
              ["/about" :get (conj common-interceptors `about-page)]
              ["/" :post (conj custom-interceptors `create)]})

;; Map-based routes
;(def routes `{"/" {:interceptors [(body-params/body-params) http/html-body]
;                   :get home-page
;                   "/about" {:get about-page}}})

;; Terse/Vector-based routes
;; (def routes
;;   `[[["/" {:get home-page}
;;       ^:interceptors [(body-params/body-params) http/html-body]
;;       ["/about" {:get about-page}]
;;       ["/" {:post create}]]]])


;; Consumed by pedestal-sample-project.server/create-server
;; See http/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; ::http/interceptors []
              ::http/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::http/allowed-origins ["scheme://host:port"]

              ;; Tune the Secure Headers
              ;; and specifically the Content Security Policy appropriate to your service/application
              ;; For more information, see: https://content-security-policy.com/
              ;;   See also: https://github.com/pedestal/pedestal/issues/499
              ;;::http/secure-headers {:content-security-policy-settings {:object-src "'none'"
              ;;                                                          :script-src "'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:"
              ;;                                                          :frame-ancestors "'none'"}}

              ;; Root for resource interceptor that is available by default.
              ::http/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ;;  This can also be your own chain provider/server-fn -- http://pedestal.io/reference/architecture-overview#_chain_provider
              ::http/type :jetty
              ;;::http/host "localhost"
              ::http/port 8080
              ;; Options to pass to the container (Jetty)
              ::http/container-options {:h2c? true
                                        :h2? false
                                        ;:keystore "test/hp/keystore.jks"
                                        ;:key-password "password"
                                        ;:ssl-port 8443
                                        :ssl? false
                                        ;; Alternatively, You can specify you're own Jetty HTTPConfiguration
                                        ;; via the `:io.pedestal.http.jetty/http-configuration` container option.
                                        ;:io.pedestal.http.jetty/http-configuration (org.eclipse.jetty.server.HttpConfiguration.)
                                        }})
