# Introduction

## SQL Queries

###### Table Setup (DDL)
It's important that we first create our tables before we can try to run any queries against them! <br />

We can set those up by running the following DDL queries.

```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```

<br />

```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```

<br />

```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```

<br />

Next we can insert our dummy data that's in the clubdata.sql file. You can try that with the following. <br />
```psql -U <username> -f clubdata.sql -d postgres -x -q```
<br /> <br />
Or you can run the queries in a GUI db manager like ```dbeaver``` or ```pgadmin```.
<br /> <br />
The order of insertion is important, since bookings relies on both facilities and members, it's important to insert data into those two tables first.

###### Question 1: Show all members 

```sql
SELECT *
FROM cd.members
```

###### Questions 2: Lorem ipsum...

```sql
SELECT blah blah 
```
