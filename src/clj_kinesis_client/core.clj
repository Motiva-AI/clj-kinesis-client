(ns clj-kinesis-client.core
  (:require [clojure.data.json :as json])
  (:import
    (com.amazonaws
      ClientConfiguration)
    (com.amazonaws.regions
      Regions)
    (com.amazonaws.services.kinesis
      AmazonKinesisClient)
    (com.amazonaws.services.kinesis.model
      PutRecordsRequest
      PutRecordsRequestEntry)
    (java.nio
      ByteBuffer)
    (java.util
      UUID)))


(defn create-client
  [& {:keys [max-connections max-error-retry endpoint region tcp-keep-alive]
      :or {max-connections 50 max-error-retry 1 tcp-keep-alive false}}]
  (let [configuration (-> (ClientConfiguration.)
                          (.withMaxErrorRetry max-error-retry)
                          (.withMaxConnections max-connections)
                          (.withTcpKeepAlive tcp-keep-alive))
        client (AmazonKinesisClient. configuration)]
    (when endpoint
      (.setEndpoint client endpoint))
    (if region
      (.withRegion client (Regions/fromName region))
      client)))


(defn- uuid
  []
  (str (UUID/randomUUID)))


(defn- ->json-byte-buffer
  [coll]
  (-> (json/write-str coll)
      (.getBytes "UTF-8")
      (ByteBuffer/wrap)))


(defn- put-record-response->map
  [response]
  {:sequence-number (.getSequenceNumber response)
   :shard-id (.getShardId response)
   :error-code (.getErrorCode response)})


(defn put-record
  [client stream-name event]
  (let [response (.putRecord client stream-name (->json-byte-buffer event) (uuid))]
    (put-record-response->map response)))


(defn- build-put-records-request-entry
  [coll]
  (doto (PutRecordsRequestEntry.)
    (.withData (->json-byte-buffer coll))
    (.withPartitionKey (uuid))))


(defn- build-put-records-request
  [records]
  (doto (PutRecordsRequest.)
    (.withStreamName stream-name)
    (.withRecords (map build-put-records-request-entry records))))


(defn put-records
  [client stream-name records]
  (loop [request           (build-put-records-request records)
         resends-performed 0]

    (let [response
          (.putRecords client request)

          failed-record-count
          (.getFailedRecordCount response)

          records-to-resend
          (-> (map put-record-response->map (.getRecords response))
              (filter #(some? :error-code)))]

      (if (pos? records-to-resend)
        (do
          (Thread/sleep 500)
          (recur records-to-resend (+ resends-performed (count records-to-resend))))))))
