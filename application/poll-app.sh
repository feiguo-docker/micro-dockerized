#!/bin/bash
usage() {
	echo "usage: $0 <host_name> <host_port>"
}

displayServerLog(){
    echo "Showing tail of server.log"
    ./docker-compose.sh logs application | tail -n 400
    echo "End of server.log"
}

checkResponseCode() {
  NEW_LINE=$'\n'
  URL=http://$HOST:$PORT/$1
  echo "Waiting for application to respond from $URL"
  NEXT_WAIT_TIME=1
  MAX_WAIT_TIME=45
  SLEEP_TIME=1
  until [ $NEXT_WAIT_TIME -gt $MAX_WAIT_TIME ] || [ $(curl -s -o /dev/null -w "%{http_code}" $URL) == $2 ]; do
    echo -n '.'
    sleep $SLEEP_TIME
    (( NEXT_WAIT_TIME++ ))
  done
  if [ $NEXT_WAIT_TIME -gt $MAX_WAIT_TIME ]
  then
    echo "$NEW_LINE checkResponseCode: error timeout after $NEXT_WAIT_TIME sec with $URL"
    displayServerLog
    exit 1
  else
    echo "$NEW_LINE checkResponseCode: successfully after $NEXT_WAIT_TIME sec wait time with $URL"
  fi
}

if [ "$#" -ne 2 ]; then
	usage
	exit 1
fi

export HOST=$1
export PORT=$2

checkResponseCode addressbook/api/status/ping 200

exit 0

