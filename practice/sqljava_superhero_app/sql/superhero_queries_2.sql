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
SELECT superhero_name FROM superhero GROUP BY superhero_name;