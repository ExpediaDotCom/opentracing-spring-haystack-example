#!/usr/bin/env bash
set -eo pipefail
set -o errexit

echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

docker tag opentracing-spring-haystack-example expediadotcom/opentracing-spring-haystack-example:${SHA}
docker push expediadotcom/opentracing-spring-haystack-example:${SHA}

docker tag opentracing-spring-haystack-example expediadotcom/opentracing-spring-haystack-example:latest
docker push expediadotcom/opentracing-spring-haystack-example:latest

