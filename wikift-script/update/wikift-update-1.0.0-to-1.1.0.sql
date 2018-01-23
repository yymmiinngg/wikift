USE wikift;

SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE users ADD u_email VARCHAR(50) NOT NULL;
ALTER TABLE users ADD u_active BOOLEAN DEFAULT TRUE COMMENT '该账号是否激活';
ALTER TABLE users ADD u_lock BOOLEAN DEFAULT FALSE COMMENT '该账号是否锁定';

DROP TABLE IF EXISTS `users_type`;
CREATE TABLE `users_type` (
  `ut_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ut_code` varchar(20) NOT NULL,
  `ut_name` varchar(100) NOT NULL,
  `ut_active` tinyint(1) NOT NULL DEFAULT '1',
  `ut_type` tinyint(1) DEFAULT '1' COMMENT '类型true为内置, false为第三方',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `users_type` VALUES ('1', 'EMBEDDED', '内置', '1', '1', '2018-01-22 14:44:24'), ('2', 'LDAP', 'LDAP', '1', '0', '2018-01-22 14:44:44');

DROP TABLE IF EXISTS `users_type_relation`;
CREATE TABLE `users_type_relation` (
  `utr_users_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `utr_users_type_id` bigint(20) NOT NULL,
  KEY `utr_users_type_id` (`utr_users_type_id`),
  KEY `utr_users_id` (`utr_users_id`),
  CONSTRAINT `FK_utr_users_relation_id` FOREIGN KEY (`utr_users_id`) REFERENCES `users` (`u_id`) ON DELETE CASCADE,
  CONSTRAINT `FK_utr_users_type_relation_id` FOREIGN KEY (`utr_users_type_id`) REFERENCES `users_type` (`ut_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users_type_relation(utr_users_id, utr_users_type_id) SELECT u.u_id, 1 FROM users AS u;

SET FOREIGN_KEY_CHECKS = 1;