CREATE TABLE IF NOT EXISTS `movie` (
  `id` int AUTO_INCREMENT  PRIMARY KEY,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `rating` decimal DEFAULT NULL,
  `release_date` date NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);
