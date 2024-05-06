CREATE TABLE IF NOT EXISTS `actor_movie` (
  `id` int AUTO_INCREMENT  PRIMARY KEY,
  `actor_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);
