(ns gilbot.core
  (:require [discljord.connections :as c]
            [discljord.messaging :as m]
            [discljord.events :as e]
            [clojure.core.async :as a]
            [clojure.string :as str]
            [gilbot.command-parser :as cp]
            [gilbot.copy-pasta :as memes]
            [clojure.data.json :as json]))

(def state (atom nil))

(defmulti handle-event
  (fn [event-type event-data]
    event-type))

(defmethod handle-event :default
  [event-type event-data])

(defn send-chat-message!
  [channel-id message]
  (m/create-message! (:messaging @state)
                     channel-id
                     :content
                     message))

(defn handle!
  [action channel-id]
  (let [send-message! (partial send-chat-message! channel-id)]
    (case action
      ("gabs" "comissao") (send-message! (memes/comissao))
      "disconnect" (send-message! "não")
      ("python" "pythonzeras") (send-message! (memes/get-python-text))
      (send-message! "num tem esse comando"))))

(defmethod handle-event :message-create
  [event-type {{bot :bot} :author :keys [channel-id content]}]
  (if-let [action (cp/get-bot-command content)]
    (handle! action channel-id)))

(defn get-secrets!
  []
  (let [secrets-str (slurp "secrets/secrets.json")
        secrets (json/read-str secrets-str :key-fn keyword)]
    secrets))

(defn get-token!
  []
  (get (get-secrets!) :token))

(defn get-channel!
  []
  (get (get-secrets!) :channel))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [token (get-token!)
        channel (get-channel!)
        event-ch (a/chan 100)
        connection-ch (c/connect-bot! token event-ch)
        message-ch (m/start-connection! token)
        initial-state {:connection connection-ch
                       :event event-ch
                       :messaging message-ch}]
    (println "token " token)
    (println "channel " channel)
    (println "bot-command " (cp/get-bot-command ">hello-world"))
    (println "non bot-command " (cp/get-bot-command "hello-world"))
    (reset! state initial-state)
    (e/message-pump! event-ch handle-event)
    (c/disconnect-bot! connection-ch)
    (m/stop-connection! message-ch)))
