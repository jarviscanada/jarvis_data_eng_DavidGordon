# Introduction
This project consists of several SQL tables that contain different relations to one another, in a PostgreSQL database running in a docker container. The purpose of this project was to become more familiar with SQL and it's various features, such as Aggregate functions, String functions, Joins, Unions and more. We created queries to Insert, Update and Delete records from tables, used Joins to combine tables for more complex views into our data, and Subqueries to collect and display specific information, like the facility with the highest bookings.

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



#### Modifying Data

##### 1. Insert some data into a table
```sql
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES (9, 'Spa', 20, 30, 100000, 800);
```

##### 2. Insert calculated data into a table
```sql
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
SELECT
  (SELECT MAX(facid)
   FROM cd.facilities) + 1,
       'Spa',
       20,
       30,
       100000,
       800;
```

##### 3. Update some existing data
```sql
UPDATE cd.facilities
SET initialoutlay = 10000
WHERE name = 'Tennis Court 2'
```

##### 4. Update a row based on the contents of another row
```sql
UPDATE cd.facilities
SET membercost =
  (SELECT membercost
    FROM cd.facilities
    WHERE facid = 1 ) * 1.1,
    guestcost =
    (SELECT guestcost
     FROM cd.facilities
     WHERE facid = 1 ) * 1.1 WHERE facid = 1
  DELETE
  FROM cd.bookings;
```

##### 5. Delete all bookings
```sql
DELETE 
FROM cd.bookings;
```

##### 6. Delete a member from the cd.members table
```sql
DELETE
FROM cd.members
WHERE memid = 37;
```

#### Basics

##### 1. Control which rows are retrieved - part 2
```sql
SELECT facid,
       name,
       membercost,
       monthlymaintenance
FROM cd.facilities
WHERE membercost > 0
  AND membercost < monthlymaintenance / 50;
```

##### 2. Basic string searches
```sql
SELECT *
FROM cd.facilities
WHERE name LIKE '%Tennis%'
  
##### 3. Matching against multiple possible values
```sql
SELECT *
FROM cd.facilities WHERE facid IN (1,5);
```

##### 4. Working with dates
```sql
SELECT memid,
       surname,
       firstname,
       joindate
FROM cd.members
WHERE joindate > '09/01/2012'
```

##### 5. Combining results from multiple queries
```sql
SELECT surname
FROM cd.members AS surname
UNION
SELECT name
FROM cd.facilities AS surname 
  ```

#### Joins

##### 1. Retrieve the start times of members' bookings
```sql
SELECT starttime
FROM cd.bookings b
INNER JOIN cd.members m ON b.memid = m.memid
WHERE m.firstname = 'David'
  AND m.surname = 'Farrell';
```

##### 2. Work out the start times of bookings for tennis courts
```sql
SELECT starttime AS
START, name
FROM cd.bookings b
INNER JOIN cd.facilities f ON b.facid = f.facid
WHERE f.name LIKE 'Tennis Court %'
  AND b.starttime >= '09/21/2012'
  AND b.starttime < '09/22/2012'
ORDER BY b.starttime;
```

##### 3. Produce a list of all members, along with their recommender
```sql
SELECT m.firstname AS memfname,
       m.surname AS memsname,
       r.firstname AS recfname,
       r.surname AS recsname
FROM cd.members m
LEFT JOIN cd.members r ON r.memid = m.recommendedby
ORDER BY memsname,
         memfname;
```

##### 4. Produce a list of all members who have recommended another member
```sql
SELECT DISTINCT r.firstname AS firstname,
                r.surname AS surname
FROM cd.members m
INNER JOIN cd.members r ON r.memid = m.recommendedby
ORDER BY r.surname,
         r.firstname;
```

##### 5. Produce a list of all members, along with their recommender, using no joins
```sql
SELECT DISTINCT m.firstname || ' ' || m.surname AS member,
  (SELECT r.firstname || ' ' || r.surname AS recommender
   FROM cd.members r
   WHERE r.memid = m.recommendedby)
FROM cd.members m
ORDER BY member;
```

#### Aggregation

##### 1. Count the number of recommendations each member makes
```sql
SELECT recommendedby,
       COUNT(memid)
FROM cd.members
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby
```

##### 2. List the total slots booked per facility
```sql
SELECT facid,
       SUM(slots) AS "Total Slots"
FROM cd.bookings
GROUP BY facid
ORDER BY facid;
```

##### 3. List the total slots booked per facility in a given month
```sql
SELECT facid,
       SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE starttime >= '09/01/2012'
  AND starttime < '10/01/2012'
GROUP BY facid
ORDER BY sum(slots) ASC;
```

##### 4. List the total slots booked per facility per month
```sql
SELECT facid,
       EXTRACT(MONTH
               FROM starttime) AS MONTH,
       SUM(slots) AS "Total Slots"
FROM cd.bookings
WHERE EXTRACT(YEAR
              FROM starttime) = 2012
GROUP BY facid,
         MONTH
ORDER BY facid,
         MONTH;
```

##### 5. Find the count of members who have made at least one booking
```sql
SELECT COUNT(DISTINCT memid)
FROM cd.bookings;
```

##### 6. List each member's first booking after September 1st 2012
```sql
SELECT m.surname,
       m.firstname,
       m.memid,
       MIN(b.starttime)
FROM cd.members m
INNER JOIN cd.bookings b ON b.memid = m.memid
WHERE starttime > '09/01/2012'
GROUP BY m.surname,
         m.firstname,
         m.memid
ORDER BY m.memid;
```

##### 7. Produce a list of member names, with each row containing the total member count
```sql
SELECT
  (SELECT COUNT(memid)
   FROM cd.members) AS COUNT,
       firstname,
       surname
FROM cd.members
ORDER BY joindate
```

##### 8. Produce a numbered list of members
```sql
SELECT ROW_NUMBER() OVER(),
                    firstname,
                    surname
FROM cd.members
ORDER BY joindate;
```

##### 9. Output the facility id that has the highest number of slots booked, again
```sql
SELECT facid,
       total
FROM
  (SELECT facid,
          sum(slots) AS total,
          rank() OVER (
                       ORDER BY sum(slots) DESC) rank
   FROM cd.bookings
   GROUP BY facid) AS ranked
WHERE rank = 1
```

#### Strings

##### 1. Format the names of members
```sql
SELECT surname || ', ' || firstname AS name
FROM cd.members;
```

##### 2. Find telephone numbers with parentheses
```sql
SELECT memid,
       telephone
FROM cd.members
WHERE telephone LIKE '(%)%'
```

##### 3. Count the number of members whose surname starts with each letter of the alphabet
```sql
SELECT SUBSTR (mems.surname,
               1,
               1) AS letter,
              count(*) AS COUNT
FROM cd.members mems
GROUP BY letter
ORDER BY letter
```
