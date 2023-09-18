#! /usr/bin/env sh

###############################################################################
# This script is used in place of `./gradlew dockerPush` because of an issue
# that prevents us from selecting the base container issue we want to use.
#
# https://github.com/micronaut-projects/micronaut-gradle-plugin/issues/820
###############################################################################

if [ -z "${DOCKER_IMAGE}" ]; then
    echo "Need a container image in env var DOCKER_IMAGE" && exit 1
fi

./gradlew assemble
docker build . -f scripts/Dockerfile -t "${DOCKER_IMAGE}"
docker push "${DOCKER_IMAGE}"
