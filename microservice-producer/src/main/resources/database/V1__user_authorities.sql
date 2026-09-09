-- Dumping structure for table demo.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_by` BIGINT(20) NULL DEFAULT NULL,
	`created_date` DATETIME(6) NOT NULL,
	`updated_by` BIGINT(20) NULL DEFAULT NULL,
	`updated_date` DATETIME(6) NOT NULL,
	`account_non_expired` TINYINT(1) NULL DEFAULT '1',
	`account_non_locked` TINYINT(1) NULL DEFAULT '1',
	`credentials_non_expired` TINYINT(1) NULL DEFAULT '1',
	`email` VARCHAR(255) NULL DEFAULT NULL,
	`enabled` BIT(1) NOT NULL,
	`first_name` VARCHAR(255) NULL DEFAULT NULL,
	`last_name` VARCHAR(255) NULL DEFAULT NULL,
	`password` VARCHAR(255) NULL DEFAULT NULL,
	`sex` VARCHAR(255) NULL DEFAULT NULL,
	`username` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `UKr43af9ap4edm43mmtq01oddj6` (`username`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;



--
-- Dumping data for table `users`
--
LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,NULL,NOW(),NULL,NOW(),1,1,1,'testt01021990@gmail.com',1,NULL,NULL,'$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',NULL,"testt01021990@gmail.com");
UNLOCK TABLES;


--
-- Table structure for table `roles`
--
DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_by` BIGINT(20) NULL DEFAULT NULL,
	`created_date` DATETIME(6) NOT NULL,
	`updated_by` BIGINT(20) NULL DEFAULT NULL,
	`updated_date` DATETIME(6) NOT NULL,
	`role` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=3
;



INSERT INTO `roles` VALUES (1,1,NOW(),NULL,NOW(),'ROLE_USER');
INSERT INTO `roles` VALUES (2,1,NOW(),NULL,NOW(),'ROLE_ADMIN');

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `user_authorities`;

CREATE TABLE `user_authorities` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_by` BIGINT(20) NULL DEFAULT NULL,
	`created_date` DATETIME(6) NOT NULL,
	`updated_by` BIGINT(20) NULL DEFAULT NULL,
	`updated_date` DATETIME(6) NOT NULL,
	`role_id` BIGINT(20) NULL DEFAULT NULL,
	`username_id` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK9218rxk6srmo5d10dkbwpiyqm` (`role_id`),
	INDEX `FKse0bl9w2setnqjfq2xk5tdmfs` (`username_id`),
	CONSTRAINT `FK9218rxk6srmo5d10dkbwpiyqm` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
	CONSTRAINT `FKse0bl9w2setnqjfq2xk5tdmfs` FOREIGN KEY (`username_id`) REFERENCES `users` (`id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;



LOCK TABLES `user_authorities` WRITE;
INSERT INTO `user_authorities` VALUES (1,NULL,NOW(),NULL,NOW(),1,1);
UNLOCK TABLES;
