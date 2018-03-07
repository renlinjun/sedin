/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.249
Source Server Version : 50630
Source Host           : 192.168.1.249:3306
Source Database       : sedin

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2017-10-23 16:33:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_res
-- ----------------------------
DROP TABLE IF EXISTS `m_res`;
CREATE TABLE `m_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT '0',
  `type` varchar(3) DEFAULT NULL COMMENT '002=角色 003=menu  001=人员',
  `sort` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0' COMMENT '0-正常 -1-删除',
  `url` varchar(500) DEFAULT NULL,
  `res_pic` varchar(500) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT '' COMMENT '文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=522 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_res
-- ----------------------------
INSERT INTO `m_res` VALUES ('1', 'admin', '0', '001', '0', '0', null, null, null, '');
INSERT INTO `m_res` VALUES ('2', '员工管理', '7', '003', '0', '0', '/user', null, null, 'func/User.vue');
INSERT INTO `m_res` VALUES ('3', '角色管理', '7', '003', '0', '0', '/form', null, null, 'func/Form.vue');
INSERT INTO `m_res` VALUES ('4', '权限管理', '7', '003', '0', '0', '/main', null, null, 'func/Main.vue');
INSERT INTO `m_res` VALUES ('7', '系统管理', '0', '003', '11', '0', '/main', null, null, 'func/Main.vue');
INSERT INTO `m_res` VALUES ('24', '菜单管理', '7', '003', '1', '0', '/main', null, null, 'func/Main.vue');
INSERT INTO `m_res` VALUES ('521', '教程', '0', '003', '0', '0', '/openOA', null, null, 'func/OpenOA.vue');

-- ----------------------------
-- Table structure for m_res_rel
-- ----------------------------
DROP TABLE IF EXISTS `m_res_rel`;
CREATE TABLE `m_res_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `res_id` bigint(20) DEFAULT NULL,
  `rel_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1225 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_res_rel
-- ----------------------------

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL COMMENT '0-男 1-女',
  `id_card` varchar(30) DEFAULT NULL,
  `status` varchar(50) DEFAULT '0' COMMENT '0-禁止登录 1--可用 -2-删除',
  `tel` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `qq` varchar(50) DEFAULT NULL,
  `res_id` bigint(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES ('1', '管理员', '$10$QkslJHZDwgGhXzEd/4xBBehwDmMZxdUt8zKdCpgk04UNWgd5Ryfai', '0', '121212121', '1', null, 'admin', '1111', '1', '1111', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508308875988&di=5aaa7d9d3bf1212850c73a5c1840b21c&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120412123914329.jpg');
