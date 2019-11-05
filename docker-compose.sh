#!/bin/bash
echo "docker-compose -p $PROJECT_NAME ${@:1}"
docker-compose -p $PROJECT_NAME ${@:1}
return_code=$?
exit $return_code
