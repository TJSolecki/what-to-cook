CREATE TABLE nutrition (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    calories VARCHAR(255),
    carbohydrate_content VARCHAR(255),
    protein_content VARCHAR(255),
    fat_content VARCHAR(255),
    saturated_fat_content VARCHAR(255),
    cholesterol_content VARCHAR(255),
    sodium_content VARCHAR(255),
    fiber_content VARCHAR(255),
    sugar_content VARCHAR(255),
    unsaturated_fat_content VARCHAR(255),
    serving_size VARCHAR(255)
);

ALTER TABLE recipe
ADD COLUMN nutrition_id INTEGER;

ALTER TABLE recipe
ADD FOREIGN KEY (nutrition_id)
REFERENCES nutrition(id);

