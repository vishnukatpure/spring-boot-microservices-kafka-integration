--
-- Table structure for table `people`
--

DROP TABLE IF EXISTS `people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `people` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`age` INT(11) NULL DEFAULT NULL,
	`first_name` VARCHAR(255) NULL DEFAULT NULL,
	`last_name` VARCHAR(255) NULL DEFAULT NULL,
	`createBy` BIGINT(20) NULL DEFAULT NULL,
	`createDate` DATETIME NULL DEFAULT NULL,
	`updatedBy` BIGINT(20) NULL DEFAULT NULL,
	`updatedDate` DATETIME NULL DEFAULT NULL,
	`create_by` BIGINT(20) NULL DEFAULT NULL,
	`create_date` DATETIME(6) NULL DEFAULT NULL,
	`updated_by` BIGINT(20) NULL DEFAULT NULL,
	`updated_date` DATETIME(6) NULL DEFAULT NULL,
	`version` BIGINT(20) NOT NULL,
	`email` VARCHAR(255) NULL DEFAULT NULL,
	`mobile` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=11
;

/*!40101 SET character_set_client = @saved_cs_client */
