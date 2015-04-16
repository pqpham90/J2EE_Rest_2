-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema myeavesdrop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema myeavesdrop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `myeavesdrop` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `myeavesdrop` ;

-- -----------------------------------------------------
-- Table `myeavesdrop`.`projects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myeavesdrop`.`projects` ;

CREATE TABLE IF NOT EXISTS `myeavesdrop`.`projects` (
  `project_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`project_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myeavesdrop`.`meetings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myeavesdrop`.`meetings` ;

CREATE TABLE IF NOT EXISTS `myeavesdrop`.`meetings` (
  `meeting_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `year` VARCHAR(4) NOT NULL,
  `projects_project_id` INT NOT NULL,
  PRIMARY KEY (`meeting_id`, `projects_project_id`),
  INDEX `fk_meetings_projects1_idx` (`projects_project_id` ASC),
  CONSTRAINT `fk_meetings_projects`
    FOREIGN KEY (`projects_project_id`)
    REFERENCES `myeavesdrop`.`projects` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
