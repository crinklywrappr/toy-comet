(ns ws-test.core
  (:require [ring.adapter.jetty9 :as jetty]
            [mount.core :refer [defstate start stop] :as mount]
            [cheshire.core :as json]
            [selmer.parser :refer [render-file]]
            [ws-test.comet :as comet])
  (:import [java.nio ByteBuffer]))

(defn dummy-app [req]
  {:status 200
   :body (render-file "index.html" {})})

(defn restart []
  (stop)
  (start))

(defstate server
  :start
  (jetty/run-jetty
   dummy-app
   {:port 5000
    :join? false
    :websockets  (merge (comet/ws-handler "/ws1")
                        (comet/ws-handler "/ws2"))
    :allow-null-path-info true})
  :stop
  (jetty/stop-server server))
