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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1', 'APARTAMENTO', '1', '24241000', 'MARIO VIANA 501', 'NITEROI', 'RJ', 'BRASIL');
INSERT INTO `address` VALUES ('2', 'TRABALHO', '1', '20071000', 'PRESIDENTE VARGAS 482', 'RIO DE JANEIRO', 'RJ', 'BRASIL');
INSERT INTO `address` VALUES ('3', 'CASA', '3', '24281999', 'ASSIS RIBEIRO 485', 'BARRA DO PIRAI', 'RJ', 'BRASIL');

-- ----------------------------
-- Table structure for carts
-- ----------------------------
DROP TABLE IF EXISTS `carts`;
CREATE TABLE `carts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `session_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of category
-- ----------------------------

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
  `price` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `img` longtext DEFAULT NULL,
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
INSERT INTO `roles` VALUES ('1', 'adm');
INSERT INTO `roles` VALUES ('2', 'cliente');

-- ----------------------------
-- Table structure for sales
-- ----------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_id` int(11) NOT NULL,
  `total_price` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `address_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sales_fk0` (`cart_id`),
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Igor Lisboa', 'igor.lisboa@id.uff.br', 'KPVROM66KPV', '1');
INSERT INTO `users` VALUES ('2', 'Caio Barros', 'caiowey@id.uff.br', 'FLA2019', '1');
INSERT INTO `users` VALUES ('3', 'Zezinho da Esquina', 'ze@fulano.com.br', 'EUAMOPACOCA', '2');

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
