(defproject ai.motiva/clj-kinesis-client "1.0.1-SNAPSHOT"
  :description "Clojure AWS Kinesis client"
  :url "https://github.com/Motiva-AI/clj-kinesis-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[com.amazonaws/amazon-kinesis-client "1.14.8" :exclusions [com.amazonaws/aws-java-sdk-dynamodb]]
                 [org.clojure/data.json "2.4.0"]]

  :plugins [[day8/lein-git-inject "0.0.14"]]
  :middleware [leiningen.git-inject/middleware]

  :profiles {:dev {:source-paths ["src" "dev/src"]
                   ;; "test" is included by default - adding it here confuses
                   ;; circleci.test which runs everything twice.
                   :test-paths []
                   :resource-paths ["dev/resources"]

                   :dependencies [[org.clojure/clojure "1.11.1"]
                                  [clj-containment-matchers "1.0.1"]
                                  [org.clojure/tools.namespace "1.3.0"]
                                  [circleci/circleci.test "0.5.0"]
                                  ]}}

  :aliases {"test"   ["run" "-m" "circleci.test/dir" :project/test-paths]
            "tests"  ["run" "-m" "circleci.test"]
            "retest" ["run" "-m" "circleci.test.retest"]}

  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      "motiva-ai"
                              :password      :env
                              :sign-releases false}]])
