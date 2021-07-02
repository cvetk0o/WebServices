insert ignore into roles(id, name) values(1,"ROLE_USER");
insert ignore into roles(id, name) values(2, "ROLE_ADMIN");
insert ignore into roles(id, name) values(3, "ROLE_MESSAGE");
insert ignore into roles(id, name) values(4, "ROLE_ADVERTISE");
insert ignore into roles(id, name) values(5, "ROLE_APPOINT");
insert ignore into roles(id, name) values(6, "ROLE_COMMENT");
insert ignore into roles(id, name) values(7, "ROLE_RATING");

-- ############################################################

INSERT IGNORE INTO `users`
(`id`,`email`,`first_name`,`last_name`,`password`,`username`)
VALUES
(1,"zeljkom96@gmail.com","agent","agent","$2a$15$mHyJ3lKp/HyLsYaA.Kcei.qFcVam9suPa/Tax6plO9CnVvwMkBqYe","agent");


INSERT IGNORE INTO `users`
(`id`,`email`,`first_name`,`last_name`,`password`,`username`)
VALUES
(2,"baweje1127@dfb55.com","user","user","$2a$15$mHyJ3lKp/HyLsYaA.Kcei.qFcVam9suPa/Tax6plO9CnVvwMkBqYe","user");

-- #########################################################

INSERT IGNORE INTO `user_roles`
(`user_id`,`role_id`)
VALUES
(1,2);

INSERT IGNORE INTO `user_roles`
(`user_id`,`role_id`)
VALUES
(2,1);

INSERT IGNORE INTO `user_roles`
(`user_id`,`role_id`)
VALUES
(2,3);

INSERT IGNORE INTO  `user_roles`
(`user_id`,`role_id`)
VALUES
(2,4);

INSERT IGNORE INTO  `user_roles`
(`user_id`,`role_id`)
VALUES
(2,5);

INSERT IGNORE INTO  `user_roles`
(`user_id`,`role_id`)
VALUES
(2,6);

INSERT IGNORE INTO  `user_roles`
(`user_id`,`role_id`)
VALUES
(2,7);