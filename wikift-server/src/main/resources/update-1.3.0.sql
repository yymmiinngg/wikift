CREATE TABLE `article_history`(
  `ah_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `ah_version` VARCHAR(100) NOT NULL COMMENT '修改版本' ,
  `ah_content` TEXT NOT NULL COMMENT '修改内容' ,
  `ah_users_id` BIGINT(20) NOT NULL ,
  `ah_article_id` BIGINT(20) NOT NULL ,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY(`ah_id`) ,
  CONSTRAINT `FK_ah_users_id` FOREIGN KEY(`ah_users_id`) REFERENCES `users`(`u_id`) ON DELETE CASCADE ,
  CONSTRAINT `FK_ah_article_id` FOREIGN KEY(`ah_article_id`) REFERENCES `article`(`a_id`) ON DELETE CASCADE ,
  INDEX `FK_ah_users_id` USING BTREE(`ah_users_id`) COMMENT ''
) ENGINE = `InnoDB` AUTO_INCREMENT = 1 DEFAULT CHARACTER
SET utf8 COLLATE utf8_general_ci ROW_FORMAT = DYNAMIC COMMENT = '' CHECKSUM = 0 DELAY_KEY_WRITE = 0;