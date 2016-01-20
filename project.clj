(defproject clj-kinesis-client "0.0.4"
  :description "Clojure AWS Kinesis client"
  :url "https://github.com/adtile/clj-kinesis-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.amazonaws/amazon-kinesis-client "1.6.1" :exclusions [com.amazonaws/aws-java-sdk-dynamodb]]]
  :profiles {:dev {:dependencies [[clj-containment-matchers "1.0.1"]]}}
  :signing {:gpg-key "webmaster@adtile.me"})
