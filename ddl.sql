CREATE DATABASE IF NOT EXISTS `opdracht2-arjan`;
USE `opdracht2-arjan`;

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `instock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CHECK (instock <= 100)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `stock` (
  `product_id` int(10) unsigned NOT NULL,
  `mutation_amount` int(10) unsigned NOT NULL,
  `mutation_date` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`product_id`,`mutation_amount`,`mutation_date`),
  CONSTRAINT `product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;