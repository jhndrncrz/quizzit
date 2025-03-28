CREATE TABLE IF NOT EXISTS `Quiz` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,

    `title` VARCHAR(255) NOT NULL,
    `description` VARCHAR(1023) NOT NULL,

    `created_by` INT REFERENCES `Student`(`id`),

    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
