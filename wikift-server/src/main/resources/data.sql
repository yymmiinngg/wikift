-- 路由初始化数据
INSERT INTO role (r_id, r_name, r_description) VALUES (1, 'USER', 'User Role - 拥有普通用户权限');
INSERT INTO role (r_id, r_name, r_description) VALUES (2, 'ADMIN', 'Admin Role - 拥有平台所有权限');
-- 用户初始化数据,密码默认123456
INSERT INTO users (u_id, u_username, u_password, u_avatar)
VALUES (1, 'user', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'http://www.ambari.com.cn/assets/logo-5irencws.png');
-- 用户与路由关系初始化数据
INSERT INTO users_role_relation (urr_user_id, urr_role_id) VALUES (1, 1);
INSERT INTO users_role_relation (urr_user_id, urr_role_id) VALUES (2, 1);
INSERT INTO users_role_relation (urr_user_id, urr_role_id) VALUES (2, 2);
-- 用户组初始化数据
INSERT INTO groups (g_id, g_name, g_description, g_enabled) VALUES (1, 'wikift_users', 'Wikift User Group', TRUE);
INSERT INTO groups (g_id, g_name, g_description, g_enabled) VALUES (2, 'wikift_admins', 'Wikift Admin Group', TRUE);
-- 用户与用户组关系初始化数据
INSERT INTO users_groups_relation (ugr_user_id, ugr_group_id) VALUES (1, 1);
INSERT INTO users_groups_relation (ugr_user_id, ugr_group_id) VALUES (2, 2);
-- 初始化文章类型
INSERT INTO article_type (at_code, at_title) values ( 'original', '原创作品');
INSERT INTO article_type (at_code, at_title) values ( 'reprint', '转载作品');
-- 初始化通告类型
INSERT INTO remind_type (rt_id, rt_code, rt_title) VALUES (1, 'WEBSITE_REMIND', '网站通告');
INSERT INTO remind_type (rt_id, rt_code, rt_title) VALUES (2, 'ARTICLE_REMIND', '文章通告');
-- 初始化标签类型
INSERT INTO article_tag(at_title) VALUES ('java');
INSERT INTO article_tag(at_title) VALUES ('javascript');
INSERT INTO article_tag(at_title) VALUES ('angularjs');
INSERT INTO article_tag(at_title) VALUES ('jquery');
INSERT INTO article_tag(at_title) VALUES ('php');
INSERT INTO article_tag(at_title) VALUES ('nodeJS');
INSERT INTO article_tag(at_title) VALUES ('hadoop');
INSERT INTO article_tag(at_title) VALUES ('storm');
INSERT INTO article_tag(at_title) VALUES ('spark');
INSERT INTO article_tag(at_title) VALUES ('html5');
INSERT INTO article_tag(at_title) VALUES ('ios');
INSERT INTO article_tag(at_title) VALUES ('android');
INSERT INTO article_tag(at_title) VALUES ('vue.js');
INSERT INTO article_tag(at_title) VALUES ('react.js');
INSERT INTO article_tag(at_title) VALUES ('css');
INSERT INTO article_tag(at_title) VALUES ('mysql');
INSERT INTO article_tag(at_title) VALUES ('hbase');
INSERT INTO article_tag(at_title) VALUES ('linux');
INSERT INTO article_tag(at_title) VALUES ('大数据');
INSERT INTO article_tag(at_title) VALUES ('云计算');
INSERT INTO article_tag(at_title) VALUES ('kvm');
INSERT INTO article_tag(at_title) VALUES ('apache');
INSERT INTO article_tag(at_title) VALUES ('spring');
