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

# HTTP Get Request
quote=$(curl --request GET \
	--url 'https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=MSFT&datatype=json' \
	--header 'X-RapidAPI-Host: alpha-vantage.p.rapidapi.com' \
	--header 'X-RapidAPI-Key: '$api_key'' | jq)

if [ $? -ne 0 ]; then
    echo "There was an error processing your request. Make sure your API key is valid"
    exit 1
fi

# Parse JSON
symbol=$(echo "$quote" | grep "symbol" | awk '{print $3}' | sed s/,//)
open=$(echo "$quote" | grep "open" | awk '{print $3}' | sed s/,//)
high=$(echo "$quote" | grep "high" | awk '{print $3}' | sed s/,//)
low=$(echo "$quote" | grep "low" | awk '{print $3}' | sed s/,//)
price=$(echo "$quote" | grep "price" | awk '{print $3}' | sed s/,//)
volume=$(echo "$quote" | grep "volume" | awk '{print $3}' | sed s/,//)
latest_trading_day=$(echo "$quote" | grep "latest trading day" | awk '{print $4}' | sed s/,//)

# Maybe could have use jq --raw-output '."Global Quote".symbol'

# Create Insert Statement
insert_statement="INSERT INTO quotes(
	symbol, open, high, low, price, volume
)
VALUES (
	'$symbol', $open, $high, $low, $price, $volume
)"

export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $psql_database -U $psql_username  -c "$insert_statement"
