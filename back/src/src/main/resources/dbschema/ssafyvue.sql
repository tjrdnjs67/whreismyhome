-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafyvue
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafyvue
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyvue` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafyvue` ;

-- -----------------------------------------------------
-- Table `ssafyvue`.`sidocode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`sidocode` (
  `sidoCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sidoCode`),
  UNIQUE INDEX `sidoName` (`sidoName` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`dongcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`dongcode` (
  `dongCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`dongCode`),
  INDEX `sidoName` (`sidoName` ASC) VISIBLE,
  CONSTRAINT `dongcode_ibfk_1`
    FOREIGN KEY (`sidoName`)
    REFERENCES `ssafyvue`.`sidocode` (`sidoName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`baseaddress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`baseaddress` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `lat` VARCHAR(20) NULL DEFAULT NULL,
  `lng` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `dongCode` (`dongCode` ASC) VISIBLE,
  CONSTRAINT `baseaddress_ibfk_1`
    FOREIGN KEY (`dongCode`)
    REFERENCES `ssafyvue`.`dongcode` (`dongCode`))
ENGINE = InnoDB
AUTO_INCREMENT = 3597
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`member` (
  `userid` VARCHAR(16) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `userpwd` VARCHAR(16) NOT NULL,
  `usertype` CHAR(1) check (usertype in  ('a', 'u')),
  `email` VARCHAR(20) NULL DEFAULT NULL,
  `joindate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`board` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(16) NULL DEFAULT NULL,
  `articletype` CHAR(1) check (articletype in  ('q', 'b')),
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT '0',
  `regtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleno`),
  INDEX `board_to_member_fk` (`userid` ASC) VISIBLE,
  CONSTRAINT `board_to_member_fk`
    FOREIGN KEY (`userid`)
    REFERENCES `ssafyvue`.`member` (`userid`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`guguncode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`guguncode` (
  `gugunCode` VARCHAR(10) NOT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`gugunCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`houseinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`houseinfo` (
  `aptCode` INT NOT NULL,
  `aptName` VARCHAR(50) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  `buildYear` INT NULL DEFAULT NULL,
  `jibun` VARCHAR(30) NULL DEFAULT NULL,
  `lat` VARCHAR(30) NULL DEFAULT NULL,
  `lng` VARCHAR(30) NULL DEFAULT NULL,
  `img` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`aptCode`),
  INDEX `dongCode` (`dongCode` ASC) VISIBLE,
  CONSTRAINT `houseinfo_ibfk_1`
    FOREIGN KEY (`dongCode`)
    REFERENCES `ssafyvue`.`dongcode` (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`housedeal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`housedeal` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `aptCode` INT NULL DEFAULT NULL,
  `dealAmount` VARCHAR(20) NULL DEFAULT NULL,
  `dealYear` INT NULL DEFAULT NULL,
  `dealMonth` INT NULL DEFAULT NULL,
  `dealDay` INT NULL DEFAULT NULL,
  `area` VARCHAR(30) NULL DEFAULT NULL,
  `floor` VARCHAR(30) NULL DEFAULT NULL,
  `type` VARCHAR(30) NULL DEFAULT NULL,
  `rentMoney` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `aptCode` (`aptCode` ASC) VISIBLE,
  CONSTRAINT `housedeal_ibfk_1`
    FOREIGN KEY (`aptCode`)
    REFERENCES `ssafyvue`.`houseinfo` (`aptCode`))
ENGINE = InnoDB
AUTO_INCREMENT = 68865
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyvue`.`memo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyvue`.`memo` (
  `memono` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(16) NULL DEFAULT NULL,
  `comment` VARCHAR(500) NULL DEFAULT NULL,
  `memotime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `articleno` INT NULL DEFAULT NULL,
  PRIMARY KEY (`memono`),
  INDEX `memo_to_board_fk` (`articleno` ASC) VISIBLE,
  INDEX `memo_to_member_fk_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `memo_to_board_fk`
    FOREIGN KEY (`articleno`)
    REFERENCES `ssafyvue`.`board` (`articleno`),
  CONSTRAINT `memo_to_member_fk`
    FOREIGN KEY (`userid`)
    REFERENCES `ssafyvue`.`member` (`userid`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
