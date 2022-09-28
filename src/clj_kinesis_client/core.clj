(ns clj-kinesis-client.core
  (:require
    [clojure.data.json :as json])
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
   :shard-id (.getShardId response)})


(defn- put-records-response->map
  [response]
  {:failed-record-count (.getFailedRecordCount response)
   :records (map put-record-response->map (.getRecords response))})


(defn put-record
  [client stream-name event]
  (let [response (.putRecord client stream-name (->json-byte-buffer event) (uuid))]
    (put-record-response->map response)))


(defn- put-records-request
  [stream-name events]
  (let [obj->put-entry (fn [entry]
                         (-> (PutRecordsRequestEntry.)
                             (.withData (->json-byte-buffer entry))
                             (.withPartitionKey (uuid))))]
    (-> (PutRecordsRequest.)
        (.withStreamName stream-name)
        (.withRecords (map obj->put-entry events)))))


(defn put-records
  [client stream-name events]
  (->> (put-records-request stream-name events)
       (.putRecords client)
       (put-records-response->map)))
