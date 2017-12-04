DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  ur_id          BIGINT(20) NOT NULL AUTO_INCREMENT,
  ur_name        VARCHAR(255)        DEFAULT NULL,
  ur_description VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (ur_id)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  u_id       BIGINT(20)   NOT NULL AUTO_INCREMENT,
  u_username VARCHAR(255) NOT NULL,
  u_password VARCHAR(255) NOT NULL,
  PRIMARY KEY (u_id)
);

DROP TABLE IF EXISTS user_role_relation;
CREATE TABLE user_role_relation (
  urr_user_id BIGINT(20) NOT NULL,
  urr_role_id BIGINT(20) NOT NULL,
  CONSTRAINT FK859n2jvi8ivhui0rl0esws6o FOREIGN KEY (urr_user_id) REFERENCES user (u_id),
  CONSTRAINT FKa68196081fvovjhkek5m97n3y FOREIGN KEY (urr_role_id) REFERENCES user_role (ur_id)
);