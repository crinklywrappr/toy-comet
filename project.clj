(defproject ws-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [info.sunng/ring-jetty9-adapter "0.12.8"]
                 [mount "0.1.16"]
                 [cheshire "5.10.0"]
                 [selmer "1.12.31"]]
  :repl-options {:init-ns ws-test.core})
