.EXPORT_ALL_VARIABLES:

SHELL := /bin/bash
EXECUTOR := 5
NETWORK := 192.99.$(EXECUTOR)
DATABASE_HOST := $(NETWORK).2
APPLICATION_PORT := 8080
DEBUG_PORT := 8000
DATABASE_PORT := 5432
APPLICATION_HOST := $(NETWORK).3
PROJECT_NAME := addressbook-$(EXECUTOR)
TAG := latest

#################################################
# AWS
#################################################
aws-setup:
	make -C aws setup

aws-destroy:
	make -C aws destroy

#################################################
# APP
#################################################

app-build:
	mvn clean install -f application/addressbook/

app-create-person:
	curl -X POST -H 'Content-Type: application/json' -i http://$(APPLICATION_HOST):8080/addressbook/api/contacts --data '{"firstName":"Krzysztof", "lastName": "Wolf"}'

app-read-person:
	curl -X GET -H 'Content-Type: application/json' -i http://$(APPLICATION_HOST):8080/addressbook/api/contacts/1

#################################################
# DOCKER
#################################################

docker-build:
ifeq ($(OS),Windows_NT)
	dos2unix database/01-init.sh
endif
	./docker-compose.sh build --parallel

docker-up:
	./docker-compose.sh up -d

docker-down:
	./docker-compose.sh down

docker-up-local:
	./docker-compose.sh -f docker-compose.yaml -f docker-compose-local.yaml up -d

docker-poll-app:
	./application/poll-app.sh ${APPLICATION_HOST} ${APPLICATION_PORT}
