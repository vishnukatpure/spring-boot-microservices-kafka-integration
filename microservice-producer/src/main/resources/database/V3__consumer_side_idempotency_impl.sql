CREATE TABLE `processed_event` (
	`event_id` VARCHAR(255) NOT NULL,
	`event_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`event_id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;
