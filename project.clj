(defproject table "0.4.0"
  :description "Display ascii tables for almost any data structure with ease"
  :url "http://github.com/cldwalker/table"
  :license {:name "The MIT License"
            :url "https://en.wikipedia.org/wiki/MIT_License"}
  :repl-options  {:init-ns table.core}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles  {:1.4  {:dependencies  [[org.clojure/clojure "1.4.0"]]}}
  :aliases {"all" ["with-profile" "dev:dev,1.4"]})
