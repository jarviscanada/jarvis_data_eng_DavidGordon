-- Question 1.
-- Show the gender distribution of the number of `Good` superheroes who have `Green` eyes or any form of `Red` hair (just red or a combination including red) or any form of `White` eyes and not `Black` hair. Display your results in alphabetical order of gender.

SELECT Count(*), gender_id as Gender FROM superhero 
JOIN colour 
ON colour.id = superhero.eye_colour_id
JOIN alignment
ON alignment.id = superhero.alignment_id
WHERE colour.colour LIKE '%Red%' 
OR colour.colour LIKE '%Green%'
AND alignment_id = 1
GROUP BY Gender;

-- Question 2.
-- Show the superhero distribution by publisher. Include the number of superheroes and the percentage belonging to that publisher. We will exclude superheroes who do not belong to any specific publishers. To avoid clutter, we will also show only publishers who take up 10% or more of the total population.

SELECT publisher.publisher_name AS Publisher, Count(*) AS Superheros
FROM superhero 
JOIN publisher 
ON publisher.id = superhero.publisher_id
WHERE publisher.id IS NOT NULL
AND publisher.publisher_name != ''
GROUP BY publisher.publisher_name;

-- Question 3.
-- Show the name and race of the top 10 superheroes (in order) by height differential to the average height of everyone in their race. Include only superheroes who have weight less than 80kg and exclude all superheroes without a race and without a height.

SELECT superhero_name AS name, race.race, AVG(height_cm) OVER(PARTITION BY superhero_name, race.race) FROM superhero
JOIN race 
ON race.id = superhero.race_id
WHERE weight_kg < 80
AND race != '-'
AND height_cm != 0
ORDER BY height_cm DESC
LIMIT 10;
