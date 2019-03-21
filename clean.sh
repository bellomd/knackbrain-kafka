#!/bin/bash

# Docker commands
docker rm -f zookeeper
docker rm -f kafka
docker rm -f springbootkafka
docker image rm -f knackbrain-kafka_app

# Linux commands
rm -rf target/
