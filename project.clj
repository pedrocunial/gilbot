(defproject gilbot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.suskalo/discljord "0.2.7"]
                 [org.clojure/data.json "1.0.0"]]
  :plugins [[lein-cljfmt "0.6.7"]]
  :main ^:skip-aot gilbot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
