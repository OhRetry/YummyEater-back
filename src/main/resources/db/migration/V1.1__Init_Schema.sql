-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Table `article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `article` (
  `article_id` BIGINT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(5000) NULL DEFAULT NULL,
  PRIMARY KEY (`article_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `UK__CATEGORY__NAME` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `oauth_provider_name` VARCHAR(255) NULL DEFAULT NULL,
  `account_pw` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `UK__USER__EMAIL` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UK__USER__USERNAME` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `food_review_rating_count`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_review_rating_count` (
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
-- Table `nutrient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nutrient` (
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
-- Table `food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food` (
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
  INDEX `FK__FOOD__ARTICLE_ID` (`article_id` ASC) VISIBLE,
  INDEX `FK__FOOD__FOOD_REVIEW_RATING_COUNT_ID` (`food_review_rating_count_id` ASC) VISIBLE,
  INDEX `FK__USER__NUTRIENT_ID` (`nutrient_id` ASC) VISIBLE,
  INDEX `FK__FOOD__USER_ID` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK__FOOD__USER_ID`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`),
  CONSTRAINT `FK__FOOD__FOOD_REVIEW_RATING_COUNT_ID`
    FOREIGN KEY (`food_review_rating_count_id`)
    REFERENCES `food_review_rating_count` (`food_review_rating_count_id`),
  CONSTRAINT `FK__USER__NUTRIENT_ID`
    FOREIGN KEY (`nutrient_id`)
    REFERENCES `nutrient` (`nutrient_id`),
  CONSTRAINT `FK__FOOD__ARTICLE_ID`
    FOREIGN KEY (`article_id`)
    REFERENCES `article` (`article_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `food_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_category` (
  `category_id` BIGINT NOT NULL,
  `food_id` BIGINT NOT NULL,
  PRIMARY KEY (`category_id`, `food_id`),
  INDEX `FK__FOOD_CATEGORY__FOOD_ID` (`food_id` ASC) VISIBLE,
  CONSTRAINT `FK__FOOD_CATEGORY__FOOD_ID`
    FOREIGN KEY (`food_id`)
    REFERENCES `food` (`food_id`),
  CONSTRAINT `FK__FOOD_CATEGORY__CATEGORY_ID`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`category_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `food_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_resource` (
  `resource_key` VARCHAR(255) NOT NULL,
  `food_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`resource_key`),
  INDEX `FK__FOOD_RESOURCE__FOOD_ID` (`food_id` ASC) VISIBLE,
  CONSTRAINT `FK__FOOD_RESOURCE__FOOD_ID`
    FOREIGN KEY (`food_id`)
    REFERENCES `food` (`food_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `food_review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_review` (
  `food_review_id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `content` VARCHAR(1000) NULL DEFAULT NULL,
  `rating` INT NULL DEFAULT NULL,
  `food_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`food_review_id`),
  INDEX `FK__FOOD_REVIEW__FOOD_ID` (`food_id` ASC) VISIBLE,
  INDEX `FK__FOOD_REVIEW__USER_ID` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK__FOOD_REVIEW__FOOD_ID`
    FOREIGN KEY (`food_id`)
    REFERENCES `food` (`food_id`),
  CONSTRAINT `FK__FOOD_REVIEW__USER_ID`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `food_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_tag` (
  `food_id` BIGINT NOT NULL,
  `tag` VARCHAR(255) NOT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`food_id`, `tag`),
  CONSTRAINT `FK__FOOD_TAG__FOOD_ID`
    FOREIGN KEY (`food_id`)
    REFERENCES `food` (`food_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `refresh_token_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `refresh_token_info` (
  `refresh_token` VARCHAR(255) NOT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `last_modified_at` DATETIME(6) NULL DEFAULT NULL,
  `last_access_token` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`refresh_token`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `temp_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temp_resource` (
  `resource_key` VARCHAR(255) NOT NULL,
  `upload_time` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`resource_key`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
