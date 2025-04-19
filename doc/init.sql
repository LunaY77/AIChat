CREATE Database AIChat;
USE AIChat;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
                       `id` BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
                       `name` VARCHAR(20) NULL COMMENT '用户昵称',
                       `password` varchar(255) not null comment '用户密码',
                       `create_time` DATETIME NOT NULL DEFAULT Now() COMMENT '创建时间',
                       `update_time` DATETIME NOT NULL DEFAULT Now() on update NOW() COMMENT '修改时间'
)  ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ai_message`;
CREATE TABLE `ai_message`(
                             `id` bigint not null auto_increment primary key,
                             `message_type` int not null comment 'ai消息类型(0 user / 1 assistant / 2 system)',
                             `content` text not null comment 'ai消息内容',
                             `session_id` varchar(36) null comment 'ai消息会话id',
                             `user_id` bigint not null comment '用户id',
                             `create_time` DATETIME NOT NULL DEFAULT Now() COMMENT '创建时间',
                             `update_time` DATETIME NOT NULL DEFAULT Now() on update NOW() COMMENT '修改时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'ai消息表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ai_session`;
CREATE TABLE `ai_session`(
                             `id` varchar(36) not null primary key,
                             `name` varchar(32) not null comment '会话名称',
                             `user_id` bigint not null comment '用户id',
                             `create_time` DATETIME NOT NULL DEFAULT Now() COMMENT '创建时间',
                             `update_time` DATETIME NOT NULL DEFAULT Now() on update NOW() COMMENT '修改时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'ai会话表' ROW_FORMAT = Dynamic;
