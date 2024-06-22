CREATE TABLE IF NOT EXISTS nutrition (
    nutrition_id SERIAL PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS instructions (
    instruction_id SERIAL PRIMARY KEY,
    recipe_id INT,
    step_number INT,
    instruction TEXT,
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);

CREATE TABLE IF NOT EXISTS keywords (
    keyword_id SERIAL PRIMARY KEY,
    recipe_id INT,
    keyword VARCHAR(50),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);

CREATE TABLE IF NOT EXISTS ingredients (
    ingredient_id SERIAL PRIMARY KEY,
    recipe_id INT,
    ingredient_name VARCHAR(255),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);

CREATE TABLE IF NOT EXISTS categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS recipe_category (
    recipe_id INT,
    category_id INT,
    PRIMARY KEY (recipe_id, category_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE IF NOT EXISTS cuisines (
    cuisine_id SERIAL PRIMARY KEY,
    cuisine_name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS recipe_cuisine (
    recipe_id INT,
    cuisine_id INT,
    PRIMARY KEY (recipe_id, cuisine_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    FOREIGN KEY (cuisine_id) REFERENCES cuisines(cuisine_id)
);

CREATE TABLE IF NOT EXISTS recipes (
    recipe_id SERIAL PRIMARY KEY,
    image_url VARCHAR(255),
    name VARCHAR(255),
    description TEXT,
    cook_time VARCHAR(50),
    prep_time VARCHAR(50),
    total_time VARCHAR(50),
    recipe_url VARCHAR(255),
    recipe_yield VARCHAR(255),
    nutrition_id INT,
    FOREIGN KEY (nutrition_id) REFERENCES nutrition(nutrition_id)
);

