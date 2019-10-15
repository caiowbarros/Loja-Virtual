/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-10-15 02:05:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `zipcode` int(11) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `address_fk0` (`user_id`),
  CONSTRAINT `address_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of address
-- ----------------------------

-- ----------------------------
-- Table structure for carts
-- ----------------------------
DROP TABLE IF EXISTS `carts`;
CREATE TABLE `carts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `ip` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `carts_fk0` (`user_id`),
  CONSTRAINT `carts_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of carts
-- ----------------------------

-- ----------------------------
-- Table structure for carts_products
-- ----------------------------
DROP TABLE IF EXISTS `carts_products`;
CREATE TABLE `carts_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `carts_products_fk0` (`cart_id`),
  KEY `carts_products_fk1` (`product_id`),
  CONSTRAINT `carts_products_fk0` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`),
  CONSTRAINT `carts_products_fk1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of carts_products
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `father_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_fk0` (`father_category_id`),
  CONSTRAINT `category_fk0` FOREIGN KEY (`father_category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'Playstation', null);
INSERT INTO `category` VALUES ('2', 'Xbox', null);
INSERT INTO `category` VALUES ('3', 'Wii', null);
INSERT INTO `category` VALUES ('4', 'Acessórios', '1');
INSERT INTO `category` VALUES ('5', 'Consoles', '1');
INSERT INTO `category` VALUES ('6', 'Jogos', '1');
INSERT INTO `category` VALUES ('7', 'Acessórios', '2');
INSERT INTO `category` VALUES ('8', 'Consoles', '2');
INSERT INTO `category` VALUES ('9', 'Jogos', '2');
INSERT INTO `category` VALUES ('10', 'Acessórios', '3');
INSERT INTO `category` VALUES ('11', 'Consoles', '3');
INSERT INTO `category` VALUES ('12', 'Jogos', '3');

-- ----------------------------
-- Table structure for favorite_products
-- ----------------------------
DROP TABLE IF EXISTS `favorite_products`;
CREATE TABLE `favorite_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `favorite_products_fk0` (`user_id`),
  KEY `favorite_products_fk1` (`product_id`),
  CONSTRAINT `favorite_products_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `favorite_products_fk1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of favorite_products
-- ----------------------------

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `description` varchar(255) NOT NULL,
  `img` longtext NOT NULL,
  `category_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `products_fk0` (`category_id`),
  CONSTRAINT `products_fk0` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of products
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', 'ADM');
INSERT INTO `roles` VALUES ('2', 'CLIENTE');

-- ----------------------------
-- Table structure for sales
-- ----------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_id` int(11) NOT NULL,
  `total_price` decimal(11,2) NOT NULL,
  `created_at` datetime NOT NULL,
  `address_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sales_fk0` (`cart_id`) USING BTREE,
  KEY `sales_fk1` (`address_id`),
  KEY `sales_fk2` (`user_id`),
  CONSTRAINT `sales_fk0` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`),
  CONSTRAINT `sales_fk1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `sales_fk2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sales
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_fk0` (`role_id`),
  CONSTRAINT `users_fk0` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Igor Lisboa', 'igor.lisboa@icraft.com.br', 'JAVAeh(h@t0', '1');
INSERT INTO `users` VALUES ('2', 'teste', 'igor@outlook.com', '123', '2');

-- ----------------------------
-- Table structure for user_produts_rating
-- ----------------------------
DROP TABLE IF EXISTS `user_produts_rating`;
CREATE TABLE `user_produts_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `rating` tinyint(1) NOT NULL DEFAULT 0,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_produts_rating_fk0` (`user_id`),
  KEY `user_produts_rating_fk1` (`product_id`),
  CONSTRAINT `user_produts_rating_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_produts_rating_fk1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_produts_rating
-- ----------------------------

-- ----------------------------
-- View structure for vw_category
-- ----------------------------
DROP VIEW IF EXISTS `vw_category`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `vw_category` AS SELECT
	c.id,
	concat(pai.`name`, ' > ', c.`name`) category_name
FROM
	category c
LEFT JOIN category pai ON (
	c.father_category_id = pai.id
)
WHERE
	c.father_category_id IS NOT NULL ;
