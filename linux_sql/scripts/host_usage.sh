psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
  echo "Illegal number of arguments. Expected 5, got '$#'"
  exit 1
fi

vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

memory_free=$(echo "$vmstat_mb" | tail -1 | awk '{print $4}' | xargs)
cpu_idle=$(echo "$vmstat_mb" | tail -1 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_mb" | tail -1 | awk '{print $14}' | xargs)
disk_io=$(vmstat --unit M -d | tail -1 | awk '{print $10}' | xargs)
disk_available=$(df -BM / | tail -1 | awk {'print $4'} | sed s/M// | xargs)

# Current time in `YYYY-MM-DD HH:MM:SS` UTC format
timestamp=$(vmstat -t | awk '{print $18, $19}' | tail -1 | xargs)

# Subquery to find matching id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

# PSQL command: Inserts server usage data into host_usage table
insert_statement="INSERT INTO host_usage(
  timestamp, host_id, memory_free, cpu_idle, 
  cpu_kernel, disk_io, disk_available
) 
VALUES 
  (
    '$timestamp', $host_id, $memory_free, 
    '$cpu_idle', '$cpu_kernel', $disk_io, 
    $disk_available
  );
"

export PGPASSWORD=$psql_password

# Insert host usage into the database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_statement"
exit $?
