# Introduction
The Linux Cluster Monitoring Agent is a simple yet powerful solution to gathering a host system's hardware information and usage. This product is intended for IT teams who need a way to log and review their systems usage to manage server costs and performance. 

# Quick Start
- Clone the repo
- Start a psql instance using psql_docker.sh
- Create tables using ddl.sql
- Insert hardware specs data into the DB using host_info.sh
- Insert hardware usage data into the DB using host_usage.sh
- Crontab setup

# Implemenation
The Monitoring Agent is implemented in Bash, communicating to a PostgresSQL instance through docker. The project's development history is available via git commits.

## Architecture
Draw a cluster diagram with three Linux hosts, a DB, and agents (use draw.io website). Image must be saved to the `assets` directory.

## Scripts
Shell script description and usage
- psql_docker.sh > [start|stop|create] > Creates a Docker container with the latest PostgreSQL image
- host_info.sh > host, port, db, username, password > Reads host information such as hostname, architecture, total memory, and inserts a row into the PostgreSQL database
- host_usage.sh > host, port, db, username, password > Reads host information such as hostname, architecture, total memory, and inserts a row into the PostgreSQL database
- crontab > crontab -e 't script-path' > Schedules a cron job to run every 't' seconds
- ddl.sql > Connects to the host_agent database and creates usage and info tables

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`

# Test
How did you test your bash scripts DDL? What was the result?

# Deployment
The agent is deployed via a cron job that stores a usage and info record into the PostgreSQL database every 60 seconds

# Improvements
- Error handling
- Windows compatibility
- UI 
