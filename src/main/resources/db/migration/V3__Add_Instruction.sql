CREATE TABLE instruction (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    recipe_id INTEGER NOT NULL,
    step_number INTEGER NOT NULL,
    text VARCHAR(2000) NOT NULL,
    name VARCHAR(2000),
    FOREIGN KEY (recipe_id) REFERENCES recipe(id)
);