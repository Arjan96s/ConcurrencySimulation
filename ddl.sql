CREATE DATABASE IF NOT EXISTS `opdracht2`;
USE `opdracht2`;

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `instock` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CHECK (instock <= 100)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `stock` (
  `product_id` int(10) unsigned NOT NULL,
  `mutation_amount` int(10) NOT NULL,
  `mutation_date` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`product_id`,`mutation_amount`,`mutation_date`),
  CONSTRAINT `product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;

DELIMITER $$
CREATE TRIGGER update_instock BEFORE INSERT ON stock
FOR EACH ROW
  BEGIN
    SET @instock = 0;
    SELECT instock INTO @instock FROM product WHERE id = NEW.product_id;
    IF (@instock + NEW.mutation_amount) <= 100 THEN
      SET @newInstock = @instock + NEW.mutation_amount;
      UPDATE product SET instock=@newInstock WHERE id = NEW.product_id;
    ELSE
      SET NEW.mutation_amount = 100 - @instock;
      UPDATE product SET instock=100 WHERE id = NEW.product_id;
    END IF;
  END $$
DELIMITER ;