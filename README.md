# clj-kinesis-client

[![CircleCI](https://circleci.com/gh/Motiva-AI/clj-kinesis-client/tree/master.svg?style=svg)](https://circleci.com/gh/Motiva-AI/clj-kinesis-client/tree/master)
[![Clojars Project](https://img.shields.io/clojars/v/clj-kinesis-client.svg)](https://clojars.org/ai.motiva/clj-kinesis-client)

A minimalistic Clojure wrapper for AWS Kinesis client.

## Usage

```clojure
(use 'clj-kinesis-client.core)

(let [client (create-client)]
  (put-records client "my-stream" ["event1" "event2"]))

(let [client (create-client)]
  (put-record client "my-stream" "event2"))

```

## Development

```sh
$ docker-compose up -d
# Disable CBOR https://github.com/mhart/kinesalite#cbor-protocol-issues-with-the-java-sdk
$ AWS_CBOR_DISABLE=1 lein test
```

## License

Copyright © 2016 Adtile Technologies Inc.

Distributed under the Eclipse Public License version 1.0.
