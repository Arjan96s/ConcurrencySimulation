CREATE DATABASE IF NOT EXISTS `opdracht2`
USE `opdracht2`;

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `instock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stock` (
  `product_id` int(10) unsigned NOT NULL,
  `mutation_amount` int(10) unsigned NOT NULL,
  `mutation_date` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`product_id`,`mutation_amount`,`mutation_date`),
  CONSTRAINT `product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;