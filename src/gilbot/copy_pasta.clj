(ns gilbot.copy-pasta
  (:require [clojure.string :as str]))

(defn- get-comissao!
  []
  (slurp "resources/copy-pastas/comissao.txt"))

(defn- get-python-texts!
  []
  (str/split (slurp "resources/copy-pastas/python.txt") #"\n"))

(def python-texts (get-python-texts!))

(defn get-python-text
  []
  (rand-nth python-texts))

(def comissao (get-comissao!))