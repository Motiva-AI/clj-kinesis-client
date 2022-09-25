(defproject ai.motiva/clj-kinesis-client "1.0.1-SNAPSHOT"
  :description "Clojure AWS Kinesis client"
  :url "https://github.com/Motiva-AI/clj-kinesis-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[com.amazonaws/amazon-kinesis-client "1.14.8" :exclusions [com.amazonaws/aws-java-sdk-dynamodb]]]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.11.1"]
                                  [clj-containment-matchers "1.0.1"]]}})
