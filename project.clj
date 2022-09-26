(defproject ai.motiva/clj-kinesis-client "1.0.1-SNAPSHOT"
  :description "Clojure AWS Kinesis client"
  :url "https://github.com/Motiva-AI/clj-kinesis-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[com.amazonaws/amazon-kinesis-client "1.14.8" :exclusions [com.amazonaws/aws-java-sdk-dynamodb]]
                 [org.clojure/data.json "2.4.0"]]

  :plugins [[day8/lein-git-inject "0.0.14"]]
  :middleware [leiningen.git-inject/middleware]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.11.1"]
                                  [clj-containment-matchers "1.0.1"]]}}

  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      "motiva-ai"
                              :password      :env
                              :sign-releases false}]])
