INSERT INTO user_role (ur_id, ur_name, ur_description)
VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO user_role (ur_id, ur_name, ur_description)
VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: admin
INSERT INTO user (u_id, u_username, u_password)
VALUES (1, 'user', '821f498d827d4edad2ed0960408a98edceb661d9f34287ceda2962417881231a');
INSERT INTO user (u_id, u_username, u_password)
VALUES (2, 'admin', '821f498d827d4edad2ed0960408a98edceb661d9f34287ceda2962417881231a');


INSERT INTO user_role_relation (urr_user_id, urr_role_id) VALUES (1, 1);
INSERT INTO user_role_relation (urr_user_id, urr_role_id) VALUES (2, 1);
INSERT INTO user_role_relation (urr_user_id, urr_role_id) VALUES (2, 2);
