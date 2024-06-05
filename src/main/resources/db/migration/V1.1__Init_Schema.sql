-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema yummyeater
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema yummyeater
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `yummyeater` DEFAULT CHARACTER SET utf8 ;
USE `yummyeater` ;

-- -----------------------------------------------------
-- Table `yummyeater`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`article` (
  `article_id` BIGINT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(5000) NULL DEFAULT NULL,
  PRIMARY KEY (`article_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`category` (
  `category_id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `UK_46ccwnsi9409t36lurvtyljak` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `oauth_provider_name` VARCHAR(255) NULL DEFAULT NULL,
  `account_pw` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `UK_ob8kqyqqgmefl0aco34akdtpe` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`food_review_rating_count`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`food_review_rating_count` (
  `food_review_rating_count_id` BIGINT NOT NULL AUTO_INCREMENT,
  `rate1` BIGINT NULL DEFAULT NULL,
  `rate2` BIGINT NULL DEFAULT NULL,
  `rate3` BIGINT NULL DEFAULT NULL,
  `rate4` BIGINT NULL DEFAULT NULL,
  `rate5` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`food_review_rating_count_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`nutrient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`nutrient` (
  `nutrient_id` BIGINT NOT NULL AUTO_INCREMENT,
  `calorie` FLOAT NULL DEFAULT NULL,
  `carbohydrate` FLOAT NULL DEFAULT NULL,
  `dietary_fiber` FLOAT NULL DEFAULT NULL,
  `fat` FLOAT NULL DEFAULT NULL,
  `natrium` FLOAT NULL DEFAULT NULL,
  `protein` FLOAT NULL DEFAULT NULL,
  `saturated_fat` FLOAT NULL DEFAULT NULL,
  `sugars` FLOAT NULL DEFAULT NULL,
  `unsaturated_fat` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`nutrient_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`food` (
  `food_id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `amount` FLOAT NULL DEFAULT NULL,
  `img_url` VARCHAR(255) NULL DEFAULT NULL,
  `ingredient` VARCHAR(255) NULL DEFAULT NULL,
  `maker` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `price` BIGINT NULL DEFAULT NULL,
  `rating` FLOAT NULL DEFAULT NULL,
  `servings` INT NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `type` VARCHAR(255) NULL DEFAULT NULL,
  `views` INT NULL DEFAULT NULL,
  `article_id` BIGINT NULL DEFAULT NULL,
  `food_review_rating_count_id` BIGINT NULL DEFAULT NULL,
  `nutrient_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`food_id`),
  INDEX `FK8cubhwm5eikq41tjn91phvjdx` (`article_id` ASC) VISIBLE,
  INDEX `FK3cnaeosa8jmky9hsxu4om84yn` (`food_review_rating_count_id` ASC) VISIBLE,
  INDEX `FK7c7pxshfmhk8ppqry64sgq3gs` (`nutrient_id` ASC) VISIBLE,
  INDEX `FK1csc0frldw8uudah1mqoi3pf0` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK1csc0frldw8uudah1mqoi3pf0`
    FOREIGN KEY (`user_id`)
    REFERENCES `yummyeater`.`user` (`user_id`),
  CONSTRAINT `FK3cnaeosa8jmky9hsxu4om84yn`
    FOREIGN KEY (`food_review_rating_count_id`)
    REFERENCES `yummyeater`.`food_review_rating_count` (`food_review_rating_count_id`),
  CONSTRAINT `FK7c7pxshfmhk8ppqry64sgq3gs`
    FOREIGN KEY (`nutrient_id`)
    REFERENCES `yummyeater`.`nutrient` (`nutrient_id`),
  CONSTRAINT `FK8cubhwm5eikq41tjn91phvjdx`
    FOREIGN KEY (`article_id`)
    REFERENCES `yummyeater`.`article` (`article_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`food_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`food_category` (
  `category_id` BIGINT NOT NULL,
  `food_id` BIGINT NOT NULL,
  PRIMARY KEY (`category_id`, `food_id`),
  INDEX `FK2dh3obfbbqnpsr2qpqprewq8i` (`food_id` ASC) VISIBLE,
  CONSTRAINT `FK2dh3obfbbqnpsr2qpqprewq8i`
    FOREIGN KEY (`food_id`)
    REFERENCES `yummyeater`.`food` (`food_id`),
  CONSTRAINT `FKs5eg2saqpvfbag6axyocc4jnm`
    FOREIGN KEY (`category_id`)
    REFERENCES `yummyeater`.`category` (`category_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`food_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`food_resource` (
  `resource_key` VARCHAR(255) NOT NULL,
  `food_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`resource_key`),
  INDEX `FKio8rpxii4vxt2uwi1nbufawn5` (`food_id` ASC) VISIBLE,
  CONSTRAINT `FKio8rpxii4vxt2uwi1nbufawn5`
    FOREIGN KEY (`food_id`)
    REFERENCES `yummyeater`.`food` (`food_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`food_review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`food_review` (
  `food_review_id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `content` VARCHAR(1000) NULL DEFAULT NULL,
  `rating` INT NULL DEFAULT NULL,
  `food_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`food_review_id`),
  INDEX `FK7fw3pjxx7akc2e3t0m6axbcc5` (`food_id` ASC) VISIBLE,
  INDEX `FKt7ri8fb3457vswa41cplxm9en` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK7fw3pjxx7akc2e3t0m6axbcc5`
    FOREIGN KEY (`food_id`)
    REFERENCES `yummyeater`.`food` (`food_id`),
  CONSTRAINT `FKt7ri8fb3457vswa41cplxm9en`
    FOREIGN KEY (`user_id`)
    REFERENCES `yummyeater`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`food_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`food_tag` (
  `food_id` BIGINT NOT NULL,
  `tag` VARCHAR(255) NOT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`food_id`, `tag`),
  CONSTRAINT `FKo6suo4blauymr1i36k683r4v1`
    FOREIGN KEY (`food_id`)
    REFERENCES `yummyeater`.`food` (`food_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`refresh_token_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`refresh_token_info` (
  `refresh_token` VARCHAR(255) NOT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `last_access_token` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`refresh_token`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `yummyeater`.`temp_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yummyeater`.`temp_resource` (
  `resource_key` VARCHAR(255) NOT NULL,
  `upload_time` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`resource_key`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
