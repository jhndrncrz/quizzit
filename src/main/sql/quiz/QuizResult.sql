CREATE TABLE IF NOT EXISTS `QuizResult` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,

    `student_id` INT NOT NULL REFERENCES `Student`(`id`),
    `quiz_id` INT NOT NULL REFERENCES `Quiz`(`id`),
    
    `score` INT NOT NULL DEFAULT 0,
    `is_submitted` BOOLEAN NOT NULL DEFAULT FALSE,

    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);