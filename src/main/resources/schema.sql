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

CREATE TABLE IF NOT EXISTS instruction (
    instruction_id SERIAL PRIMARY KEY,
    recipe INT,
    step_number INT,
    instruction TEXT,
    FOREIGN KEY (recipe) REFERENCES recipe(recipe)
);

CREATE TABLE IF NOT EXISTS keyword (
    keyword_id SERIAL PRIMARY KEY,
    recipe INT,
    keyword_name VARCHAR(50),
    FOREIGN KEY (recipe) REFERENCES recipe(recipe)
);

CREATE TABLE IF NOT EXISTS ingredient (
    ingredient_id SERIAL PRIMARY KEY,
    recipe INT,
    ingredient_name VARCHAR(255),
    FOREIGN KEY (recipe) REFERENCES recipe(recipe)
);

CREATE TABLE IF NOT EXISTS category (
    category SERIAL PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE,
);

CREATE TABLE IF NOT EXISTS recipe_category (
    recipe INT,
    category INT,
    PRIMARY KEY (recipe, category),
    FOREIGN KEY (recipe) REFERENCES recipe(recipe),
    FOREIGN KEY (category) REFERENCES category(category)
);

CREATE TABLE IF NOT EXISTS cuisine (
    cuisine SERIAL PRIMARY KEY,
    cuisine_name VARCHAR(255) UNIQUE,
);

CREATE TABLE IF NOT EXISTS recipe_cuisine (
    recipe INT,
    cuisine INT,
    PRIMARY KEY (recipe, cuisine),
    FOREIGN KEY (recipe) REFERENCES recipes(recipe),
    FOREIGN KEY (cuisine) REFERENCES cuisine(cuisine)
);

CREATE TABLE IF NOT EXISTS recipe (
    id SERIAL PRIMARY KEY,
    image_url VARCHAR(255),
    name VARCHAR(255),
    description TEXT,
    cook_time VARCHAR(50),
    prep_time VARCHAR(50),
    total_time VARCHAR(50),
    recipe_url VARCHAR(255),
    recipe_yield VARCHAR(255),
    nutrition INT,
    FOREIGN KEY (nutrition) REFERENCES nutrition(nutrition)
);

