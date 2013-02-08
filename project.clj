(defproject table "0.3.2"
  :description "Display ascii tables for almost any data structure with ease"
  :url "http://github.com/cldwalker/table"
  :license {:name "The MIT License"
            :url "https://en.wikipedia.org/wiki/MIT_License"}
  :repl-options  {:init-ns table.core}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles {:1.5 {:dependencies [[org.clojure/clojure "1.5.0-RC1"]]}}
  :aliases {"all" ["with-profile" "dev:dev,1.5"]})
