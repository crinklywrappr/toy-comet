(ns ws-test.comet
  (:require [ring.adapter.jetty9 :as jetty]
            [cheshire.core :as json])
  (:import [java.nio ByteBuffer]))

(def conns (atom {}))

(defn broadcast [conns msg]
  (let [x (json/encode msg)]
    (doseq [c conns]
      (jetty/send! c x))))

(defn broadcast-bytes [conns buffer]
  (doseq [c conns]
    (jetty/send! c buffer)))

(defn ws-handler [path]
  (swap! conns assoc path [])
  {path
   {:on-connect
    (fn [ws]
      (swap! conns assoc path
             (conj (get @conns path) ws)))

    :on-close
    (fn [ws status-code reason]
      (swap! conns assoc path
             (remove #(= % ws) (get @conns path))))

    :on-text
    (fn [ws text-message]
      (broadcast
       (remove #(= % ws) (get @conns path))
       text-message))

    :on-bytes
    (fn [ws bytes offset len]
      (let [buffer (ByteBuffer/wrap bytes offset len)]
        (broadcast-bytes
         (remove #(= % ws) (get @conns path))
         buffer)))}})
