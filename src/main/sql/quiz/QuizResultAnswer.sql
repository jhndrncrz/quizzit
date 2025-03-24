CREATE TABLE IF NOT EXISTS `QuizResultAnswer` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,

    `result_id` INT NOT NULL REFERENCES `QuizResult`(`id`),
    `question_id` INT NOT NULL REFERENCES `QuizQuestion`(`id`),
    `answer_id` INT NOT NULL REFERENCES `QuizAnswer`(`id`),

    `is_correct` BOOLEAN NOT NULL,

    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);