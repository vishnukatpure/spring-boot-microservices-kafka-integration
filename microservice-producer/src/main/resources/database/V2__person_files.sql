--
-- Table structure for table `people`
--

DROP TABLE IF EXISTS `people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `people` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_by` BIGINT(20) NULL DEFAULT NULL,
	`created_date` DATETIME(6) NOT NULL,
	`updated_by` BIGINT(20) NULL DEFAULT NULL,
	`updated_date` DATETIME(6) NOT NULL,
	`age` INT(11) NULL DEFAULT NULL,
	`email` VARCHAR(255) NULL DEFAULT NULL,
	`first_name` VARCHAR(255) NULL DEFAULT NULL,
	`last_name` VARCHAR(255) NULL DEFAULT NULL,
	`mobile` VARCHAR(255) NULL DEFAULT NULL,
	`profile_picture` VARCHAR(255) NULL DEFAULT NULL,
	`version` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `idx_person_email` (`email`),
	INDEX `idx_person_mobile` (`mobile`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;



DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;

CREATE TABLE `files` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`created_by` BIGINT(20) NULL DEFAULT NULL,
	`created_date` DATETIME(6) NOT NULL,
	`updated_by` BIGINT(20) NULL DEFAULT NULL,
	`updated_date` DATETIME(6) NOT NULL,
	`content_type` VARCHAR(255) NULL DEFAULT NULL,
	`file_data` LONGBLOB NULL,
	`file_id` VARCHAR(255) NOT NULL,
	`file_name` VARCHAR(255) NOT NULL,
	`file_size` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

