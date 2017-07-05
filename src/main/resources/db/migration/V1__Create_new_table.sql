-- -----------------------------------------------------
-- Table `mydb`.`TEST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TEST` (
  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));

-- INSERT
INSERT INTO mydb.TEST (name) VALUES ('test1');
