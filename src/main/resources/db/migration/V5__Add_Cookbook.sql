CREATE TABLE cookbook (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner_id INTEGER NOT NULL,
    recipe_count INTEGER NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

ALTER TABLE recipe
ADD COLUMN cookbook_id INTEGER;

ALTER TABLE recipe
ADD FOREIGN KEY (cookbook_id)
REFERENCES cookbook(id);
