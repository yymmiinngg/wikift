ALTER TABLE `users_article_view_relation`
  MODIFY COLUMN `uavr_user_id` BIGINT(20) DEFAULT NULL
  FIRST,
  MODIFY COLUMN `uavr_view_device` VARCHAR(50) CHARACTER SET utf8
COLLATE utf8_general_ci DEFAULT NULL
  AFTER `uavr_view_count`;