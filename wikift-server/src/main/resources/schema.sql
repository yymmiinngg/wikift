-- CREATE DATABASE wikift;
-- USE wikift;
-- 路由表
DROP TABLE IF EXISTS role;
CREATE TABLE role (
  r_id          BIGINT(20) NOT NULL AUTO_INCREMENT,
  r_name        VARCHAR(255)        DEFAULT NULL,
  r_description VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (r_id)
);
-- 用户表
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  u_id         BIGINT(20)   NOT NULL AUTO_INCREMENT,
  u_username   VARCHAR(25)  NOT NULL,
  u_password   VARCHAR(255) NOT NULL,
  u_avatar     VARCHAR(255),
  u_alias_name VARCHAR(25),
  u_signature  VARCHAR(200),
  PRIMARY KEY (u_id)
);
-- 用户路由关系表
DROP TABLE IF EXISTS users_role_relation;
CREATE TABLE users_role_relation (
  urr_user_id BIGINT(20) NOT NULL,
  urr_role_id BIGINT(20) NOT NULL,
  CONSTRAINT FK_user_id FOREIGN KEY (urr_user_id) REFERENCES users (u_id),
  CONSTRAINT FK_role_id FOREIGN KEY (urr_role_id) REFERENCES role (r_id)
);
-- 用户关注关系表
DROP TABLE IF EXISTS users_follow_relation;
CREATE TABLE users_follow_relation (
  ufr_user_id_follw BIGINT(20) NOT NULL COMMENT '关注者用户id',
  ufr_user_id_cover BIGINT(20) NOT NULL COMMENT '被关注用户id',
  ufr_create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);
-- 用户组表
DROP TABLE IF EXISTS groups;
CREATE TABLE groups (
  g_id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  g_name        VARCHAR(255) NOT NULL,
  g_description VARCHAR(255) NOT NULL,
  g_enabled     BOOLEAN      NOT NULL DEFAULT TRUE,
  PRIMARY KEY (g_id)
);
-- 用户与组关系表
DROP TABLE IF EXISTS users_groups_relation;
CREATE TABLE users_groups_relation (
  ugr_user_id  BIGINT(20) NOT NULL,
  ugr_group_id BIGINT(20) NOT NULL,
  CONSTRAINT FK_user_relation_id FOREIGN KEY (ugr_user_id) REFERENCES users (u_id),
  CONSTRAINT FK_group_relation_id FOREIGN KEY (ugr_group_id) REFERENCES groups (g_id)
);
-- 文章表
DROP TABLE IF EXISTS article;
CREATE TABLE article (
  a_id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  a_title       VARCHAR(255) NOT NULL,
  a_content     TEXT         NOT NULL,
  a_create_time TIMESTAMP,
  PRIMARY KEY (a_id)
);
-- 用户与文章关系表
DROP TABLE IF EXISTS users_article_relation;
CREATE TABLE users_article_relation (
  uar_user_id    BIGINT(20) NOT NULL,
  uar_article_id BIGINT(20) NOT NULL,
  CONSTRAINT FK_users_relation_id FOREIGN KEY (uar_user_id) REFERENCES users (u_id),
  CONSTRAINT FK_article_relation_id FOREIGN KEY (uar_article_id) REFERENCES article (a_id)
);
-- 用户赞文章关系表
DROP TABLE IF EXISTS users_article_fabulous_relation;
CREATE TABLE users_article_fabulous_relation (
  uafr_user_id    BIGINT(20) NOT NULL,
  uafr_article_id BIGINT(20) NOT NULL,
  uafr_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
  CONSTRAINT FK_users_fabulous_relation_id FOREIGN KEY (uafr_user_id) REFERENCES users (u_id),
  CONSTRAINT FK_article_fabulous_relation_id FOREIGN KEY (uafr_article_id) REFERENCES article (a_id)
);