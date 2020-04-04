(ns gilbot.command-parser
  (:require [clojure.string :as str]))

(def ^:const prefix ">")

(defn get-bot-command
  [message]
  (let [has-prefix (str/starts-with? message prefix)]
    (if has-prefix (subs message 1) nil)))