CREATE TABLE IF NOT EXISTS `StudentProfile` (
    `id` INT PRIMARY KEY REFERENCES `Student`(`id`), 

    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL
);