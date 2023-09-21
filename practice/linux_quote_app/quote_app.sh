#!/bin/bash

api_key=$1
psql_host=$2
psql_port=$3
psql_database=$4
psql_username=$5
psql_password=$6
symbols=$7

if [ $# -ne 7 ]; then
    echo "Incorrect number of arguments. Expected 7, got $#"
    exit 1
fi