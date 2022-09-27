FROM clojure:openjdk-11-lein
ADD . /lib
WORKDIR /lib

RUN apt-get update && \
  apt-get install -y netcat
