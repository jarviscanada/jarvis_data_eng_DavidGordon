--- ===== Selection and Filtering =====

-- Question 1.
-- Show the first superhero
SELECT * FROM superhero LIMIT 1;

-- Question 2.
-- Show the last superhero
SELECT * FROM superhero ORDER BY id DESC LIMIT 1;

-- Question 3.
-- Show the superhero name of all superheroes with the same colour eyes and hair
SELECT superhero_name FROM superhero WHERE eye_colour_id = 23 AND hair_colour_id = 1;

-- Question 4.
-- Show the superhero name and full name of all superheroes with a superhero name that ends in Man or Woman
SELECT superhero_name, full_name FROM superhero WHERE superhero_name LIKE '%Man' OR superhero_name LIKE '%Woman';

-- Question 5.
-- Show the superhero name of the 5 tallest superheroes in descending order
SELECT superhero_name FROM superhero WHERE height_cm IS NOT NULL ORDER BY height_cm DESC LIMIT 5;

-- Question 6.
-- Show the superhero name of the 5 lightest superheroes with weight greater than 0kg in ascending order
SELECT superhero_name FROM superhero WHERE weight_kg > 0 ORDER BY weight_kg LIMIT 5;

-- Question 7.
-- Show the first 5 superheroes who do not use an alias in ascending order of id
SELECT * FROM superhero WHERE superhero_name IS NULL ORDER BY id LIMIT 5;

-- Question 8.
-- Show the first 5 superheroes who do not have a full name in ascending order of id
SELECT * FROM superhero WHERE full_name IS NULL ORDER BY id LIMIT 5;

-- Question 9.
-- Show the superhero name of all superheroes who are neither Male or Female
SELECT s.superhero_name FROM superhero s JOIN gender g ON g.id = s.gender_id WHERE g.gender != 'Male' AND g.gender != 'Female';

-- Question 10.
-- Show the superhero name of all superheroes who are Female and Neutral in alignment
SELECT superhero_name FROM superhero s JOIN gender g ON g.id = s.gender_id JOIN alignment a ON a.id = s.alignment_id WHERE g.gender = 'Female' AND a.alignment = 'Neutral';

--- ===== Aggregation =====

-- Question 1.
-- Show the number of superheroes who have superhero names that start with the letter A
SELECT COUNT(*) FROM superhero WHERE superhero_name LIKE 'A%';

-- Question 2.
-- Show the superhero name of the tallest superheroes for each gender
SELECT superhero_name, gender FROM (SELECT gender, MAX(height_cm) as height_cm FROM gender g JOIN superhero s ON s.gender_id = g.id 
GROUP BY gender HAVING MAX(height_cm) IS NOT NULL) subq JOIN superhero s ON s.height_cm = subq.height_cm;

-- Question 3.
-- Show the distribution of superheroes by publisher. Exclude superheroes who do not belong to any publishers (publisher is ‘’)
SELECT publisher_name, COUNT(*) FROM publisher p JOIN superhero s ON p.id = s.publisher_id WHERE publisher_name != '' GROUP BY publisher_name;

-- Question 4.
-- Show the superhero names of all superheroes who take on multiple forms (their name appears multiple times in the database)
SELECT superhero_name, COUNT(id) FROM superhero GROUP BY superhero_name HAVING COUNT(id) > 1;

-- Question 5.
-- Show the distribution of superheroes by eye colour and hair colour. Order your result, showing the 10 most common combinations

-- Question 6.
-- Show the number of superheroes who have the exact same eye and hair colour. Exclude superheroes who do not have any colour in their eyes or hair (colour is No Colour).
SELECT COUNT(*) as Heroes FROM superhero s JOIN colour eyes ON eyes.id = s.eye_colour_id JOIN colour hair ON hair.id = s.hair_colour_id WHERE eyes.colour = hair.colour;

-- Question 7.
-- Show the average height and average weight of all superheroes whose name ends in Man
SELECT AVG(height_cm) as Height, AVG(weight_kg) as Weight FROM superhero WHERE superhero_name LIKE '%Man';

-- Question 8.
-- Show the top 10 races by average height having average height less than 200cm. Display your results in descending order
SELECT race, AVG(height_cm) as height, AVG(weight_kg) as weight FROM superhero s JOIN race r ON r.id = s.race_id
GROUP BY race HAVING AVG(height_cm) < 200 ORDER BY AVG(height_cm) DESC LIMIT 10;

-- Question 9.
-- Show the gender distribution by count, average height, and average weight of all superheroes without a publisher (publisher name is ‘’)
SELECT gender, COUNT(*), AVG(height_cm), AVG(weight_kg) FROM superhero s JOIN gender g ON g.id = s.gender_id
JOIN publisher p ON p.id = s.publisher_id WHERE publisher_name != '' GROUP BY gender;

-- Question 10.
-- Show the 5 most common races of superheroes who are not Good. Display your results in descending order and exclude superheroes without a race (race is -)
SELECT race, COUNT(*) FROM race r JOIN superhero s ON r.id = s.race_id JOIN alignment a ON a.id = s.alignment_id WHERE a.alignment != 'Good' AND race != '-' GROUP BY race ORDER BY COUNT(*) DESC LIMIT 10;

-- ===== String manipulation and Window Functions =====

-- Question 1.
SELECT '"' || SUBSTR(full_name, 0, POSITION(' ' IN full_name)) ||
' ' || superhero_name || ' ' || SUBSTR(full_name, POSITION(' ' IN full_name), LENGTH(full_name)) || '"' as superhero
FROM superhero s JOIN publisher p ON s.publisher_id = p.id WHERE LENGTH(full_name) - LENGTH(REPLACE(full_name,' ', '')) < 2;

-- Question 3.
-- Show the superhero name and full name of the 10th superhero (by id) who uses an alias
SELECT superhero_name, full_name FROM (SELECT id, superhero_name, full_name, RANK() OVER(ORDER BY id) rnk FROM superhero WHERE superhero_name != full_name) as subq WHERE rnk = 10;

