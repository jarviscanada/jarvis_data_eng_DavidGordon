#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
  echo "Illegal number of arguments. Expected 5, got '$#'"
  exit 1
fi

lscpu_out=`lscpu`

hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture" | awk '{print $2}' | xargs) 
cpu_model=$(echo "$lscpu_out" | egrep "^Model" | awk '{print $2}' | xargs) 
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU MHz" | awk '{print $3}' | xargs) 
l2_cache=$(echo "$lscpu_out" | egrep "^L2 cache" | awk '{print $3}' | sed 's/K//' | xargs) 
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}' | xargs)

# Current time in `YYYY-MM-DD HH:MM:SS` UTC format
timestamp=$(vmstat -t | awk '{print $18, $19}' | tail -n1 | xargs)

# Subquery to find matching id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname ='$hostname')";

# PSQL command: Inserts server usage data into host_usage table
insert_statement="INSERT INTO host_info(
  hostname, cpu_number, cpu_architecture, 
  cpu_model, cpu_mhz, l2_cache, total_mem, 
  timestamp
) 
VALUES 
  (
    '$hostname', $cpu_number, '$cpu_architecture', 
    '$cpu_model', $cpu_mhz, $l2_cache, 
    $total_mem, '$timestamp'
  );
"

export PGPASSWORD=$psql_password

# Insert host info into the database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_statement"
exit $?

