start transaction;

drop database if exists `Acme-Gym`;
create database `Acme-Gym`;

use `Acme-Gym`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `Acme-Gym`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter,
	create temporary tables, lock tables, create view, create routine,
	alter routine, execute, trigger, show view on `Acme-Gym`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Gym
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `dayWeek` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endTime` varchar(255) DEFAULT NULL,
  `isCancelled` bit(1) DEFAULT NULL,
  `numSeats` int(11) DEFAULT NULL,
  `startTime` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `gym_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p7iscm1n2rfw495tpwnkookj3` (`gym_id`),
  CONSTRAINT `FK_p7iscm1n2rfw495tpwnkookj3` FOREIGN KEY (`gym_id`) REFERENCES `gym` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (48,0,1,'A very funny class','10:00','\0',22,'09:00','Spinning',42),(49,0,2,'A very funny class','19:00','\0',33,'18:00','Tabata',43);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_customer`
--

DROP TABLE IF EXISTS `activity_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_customer` (
  `Activity_id` int(11) NOT NULL,
  `customers_id` int(11) NOT NULL,
  KEY `FK_i2tb3uoolnxniqpreoojtq99p` (`customers_id`),
  KEY `FK_3g7daexb70kgecsnr7yvgqkek` (`Activity_id`),
  CONSTRAINT `FK_3g7daexb70kgecsnr7yvgqkek` FOREIGN KEY (`Activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `FK_i2tb3uoolnxniqpreoojtq99p` FOREIGN KEY (`customers_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_customer`
--

LOCK TABLES `activity_customer` WRITE;
/*!40000 ALTER TABLE `activity_customer` DISABLE KEYS */;
INSERT INTO `activity_customer` VALUES (48,44),(49,45);
/*!40000 ALTER TABLE `activity_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_pictures`
--

DROP TABLE IF EXISTS `activity_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_pictures` (
  `Activity_id` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_465ltkojh9ooei6h62da9lw3m` (`Activity_id`),
  CONSTRAINT `FK_465ltkojh9ooei6h62da9lw3m` FOREIGN KEY (`Activity_id`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_pictures`
--

LOCK TABLES `activity_pictures` WRITE;
/*!40000 ALTER TABLE `activity_pictures` DISABLE KEYS */;
INSERT INTO `activity_pictures` VALUES (48,''),(49,'');
/*!40000 ALTER TABLE `activity_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_trainer`
--

DROP TABLE IF EXISTS `activity_trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_trainer` (
  `Activity_id` int(11) NOT NULL,
  `trainers_id` int(11) NOT NULL,
  KEY `FK_r9qaqslh5b4mepc60066idiv2` (`trainers_id`),
  KEY `FK_23bmrghtx8fr7ryho7h0nvy3u` (`Activity_id`),
  CONSTRAINT `FK_23bmrghtx8fr7ryho7h0nvy3u` FOREIGN KEY (`Activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `FK_r9qaqslh5b4mepc60066idiv2` FOREIGN KEY (`trainers_id`) REFERENCES `trainer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_trainer`
--

LOCK TABLES `activity_trainer` WRITE;
/*!40000 ALTER TABLE `activity_trainer` DISABLE KEYS */;
INSERT INTO `activity_trainer` VALUES (48,46),(49,47);
/*!40000 ALTER TABLE `activity_trainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `actorName` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `actorName` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (39,0,'Pablo','Sevilla','España','pablo@hotmail.com','+5 (10) 9132','41001','Pablito',31);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annotation`
--

DROP TABLE IF EXISTS `annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `momentWritten` varchar(255) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `activity_id` int(11) DEFAULT NULL,
  `actorStores_id` int(11) DEFAULT NULL,
  `actorWrites_id` int(11) NOT NULL,
  `gym_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_472i1t9ukw8tl9vj222ard5lc` (`activity_id`),
  KEY `FK_4xsyicyueumjt4rinr7tcx9r7` (`gym_id`),
  CONSTRAINT `FK_472i1t9ukw8tl9vj222ard5lc` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `FK_4xsyicyueumjt4rinr7tcx9r7` FOREIGN KEY (`gym_id`) REFERENCES `gym` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annotation`
--

LOCK TABLES `annotation` WRITE;
/*!40000 ALTER TABLE `annotation` DISABLE KEYS */;
INSERT INTO `annotation` VALUES (50,0,'20-07-2017 20:20',3,'A very funny class cool =D',NULL,45,44,NULL),(51,0,'30-07-2017 20:20',3,'good gym',NULL,NULL,45,43),(52,0,'20-07-2017 20:20',3,'good trainer and ver funny',NULL,46,40,NULL),(53,0,'30-07-2017 20:20',2,'not bad activity',49,NULL,45,NULL),(54,0,'20-07-2017 20:20',0,'i am so good trainer that you',NULL,46,47,NULL),(55,0,'30-07-2017 20:20',1,'bad gym',NULL,NULL,41,42),(56,0,'20-07-2017 20:20',3,'very funny activity',48,NULL,46,NULL),(57,0,'30-07-2017 20:20',0,'be careful with your annotations or ban',NULL,47,39,NULL),(58,0,'20-07-2017 20:20',2,'good maganer',NULL,40,44,NULL);
/*!40000 ALTER TABLE `annotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `actorName` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pwmktpkay2yx7v00mrwmuscl8` (`userAccount_id`),
  CONSTRAINT `FK_pwmktpkay2yx7v00mrwmuscl8` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (44,0,'Laura','Sevilla','España','laura@hotmail.com','+5 (10) 6643','41001','Padial',32),(45,0,'Misty','Sevilla','España','Misty@hotmail.com','+5 (10) 9152','41001','Lopez',34);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_activity`
--

DROP TABLE IF EXISTS `customer_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_activity` (
  `Customer_id` int(11) NOT NULL,
  `activities_id` int(11) NOT NULL,
  KEY `FK_7u1sf71oni51dlk4b937q5osf` (`activities_id`),
  KEY `FK_h2di58qd0lgfs183fe50g5sq1` (`Customer_id`),
  CONSTRAINT `FK_7u1sf71oni51dlk4b937q5osf` FOREIGN KEY (`activities_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `FK_h2di58qd0lgfs183fe50g5sq1` FOREIGN KEY (`Customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_activity`
--

LOCK TABLES `customer_activity` WRITE;
/*!40000 ALTER TABLE `customer_activity` DISABLE KEYS */;
INSERT INTO `customer_activity` VALUES (44,48),(45,49);
/*!40000 ALTER TABLE `customer_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_gym`
--

DROP TABLE IF EXISTS `customer_gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_gym` (
  `Customer_id` int(11) NOT NULL,
  `gyms_id` int(11) NOT NULL,
  KEY `FK_y6bv8tmf8a9d732kvgngm5rg` (`gyms_id`),
  KEY `FK_t58ig2htxsoopo7yrmk8wv3dn` (`Customer_id`),
  CONSTRAINT `FK_t58ig2htxsoopo7yrmk8wv3dn` FOREIGN KEY (`Customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_y6bv8tmf8a9d732kvgngm5rg` FOREIGN KEY (`gyms_id`) REFERENCES `gym` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_gym`
--

LOCK TABLES `customer_gym` WRITE;
/*!40000 ALTER TABLE `customer_gym` DISABLE KEYS */;
INSERT INTO `customer_gym` VALUES (44,42),(45,43);
/*!40000 ALTER TABLE `customer_gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym`
--

DROP TABLE IF EXISTS `gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `isDelete` bit(1) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym`
--

LOCK TABLES `gym` WRITE;
/*!40000 ALTER TABLE `gym` DISABLE KEYS */;
INSERT INTO `gym` VALUES (42,0,'pueblo paleta',22.33,'\0','https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg','gym rock'),(43,0,'pueblo paleta',22.33,'\0','https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg','gym water');
/*!40000 ALTER TABLE `gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_customer`
--

DROP TABLE IF EXISTS `gym_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_customer` (
  `Gym_id` int(11) NOT NULL,
  `customers_id` int(11) NOT NULL,
  KEY `FK_h8rjqemeo4b1feoagcibdbeet` (`customers_id`),
  KEY `FK_4l6u4yg3bbv37oeajn1gtuama` (`Gym_id`),
  CONSTRAINT `FK_4l6u4yg3bbv37oeajn1gtuama` FOREIGN KEY (`Gym_id`) REFERENCES `gym` (`id`),
  CONSTRAINT `FK_h8rjqemeo4b1feoagcibdbeet` FOREIGN KEY (`customers_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_customer`
--

LOCK TABLES `gym_customer` WRITE;
/*!40000 ALTER TABLE `gym_customer` DISABLE KEYS */;
INSERT INTO `gym_customer` VALUES (42,44),(43,45);
/*!40000 ALTER TABLE `gym_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_trainer`
--

DROP TABLE IF EXISTS `gym_trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_trainer` (
  `Gym_id` int(11) NOT NULL,
  `trainers_id` int(11) NOT NULL,
  UNIQUE KEY `UK_56p9go7xb54ens92n1gx9cyle` (`trainers_id`),
  KEY `FK_qumcdkoveo2918q1on1hud6tw` (`Gym_id`),
  CONSTRAINT `FK_56p9go7xb54ens92n1gx9cyle` FOREIGN KEY (`trainers_id`) REFERENCES `trainer` (`id`),
  CONSTRAINT `FK_qumcdkoveo2918q1on1hud6tw` FOREIGN KEY (`Gym_id`) REFERENCES `gym` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_trainer`
--

LOCK TABLES `gym_trainer` WRITE;
/*!40000 ALTER TABLE `gym_trainer` DISABLE KEYS */;
INSERT INTO `gym_trainer` VALUES (42,46),(43,47);
/*!40000 ALTER TABLE `gym_trainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `actorName` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (40,0,'Brook','Sevilla','España','Brook@hotmail.com','+5 (10) 9151','41001','Lopez',37),(41,0,'Misty','Sevilla','España','Misty@hotmail.com','+5 (10) 9152','41001','Lopez',38);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_gym`
--

DROP TABLE IF EXISTS `manager_gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_gym` (
  `Manager_id` int(11) NOT NULL,
  `gyms_id` int(11) NOT NULL,
  UNIQUE KEY `UK_403tjunmqbwfd05lkf2gl5xuy` (`gyms_id`),
  KEY `FK_8hvl6ijxg55krr2qykaem9qam` (`Manager_id`),
  CONSTRAINT `FK_403tjunmqbwfd05lkf2gl5xuy` FOREIGN KEY (`gyms_id`) REFERENCES `gym` (`id`),
  CONSTRAINT `FK_8hvl6ijxg55krr2qykaem9qam` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_gym`
--

LOCK TABLES `manager_gym` WRITE;
/*!40000 ALTER TABLE `manager_gym` DISABLE KEYS */;
INSERT INTO `manager_gym` VALUES (40,42),(41,43);
/*!40000 ALTER TABLE `manager_gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabuword`
--

DROP TABLE IF EXISTS `tabuword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tabuword` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabuword`
--

LOCK TABLES `tabuword` WRITE;
/*!40000 ALTER TABLE `tabuword` DISABLE KEYS */;
INSERT INTO `tabuword` VALUES (59,0,'sex'),(60,0,'viagra');
/*!40000 ALTER TABLE `tabuword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer`
--

DROP TABLE IF EXISTS `trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `actorName` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1v0ljnwuhlwur1d8ukoe2vlhp` (`userAccount_id`),
  CONSTRAINT `FK_1v0ljnwuhlwur1d8ukoe2vlhp` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer`
--

LOCK TABLES `trainer` WRITE;
/*!40000 ALTER TABLE `trainer` DISABLE KEYS */;
INSERT INTO `trainer` VALUES (46,0,'Brook','Sevilla','España','Brook@hotmail.com','+5 (10) 9142','41001','Lopez',35),(47,0,'Misty','Sevilla','España','Misty@hotmail.com','+5 (10) 9152','41001','Lopez',36);
/*!40000 ALTER TABLE `trainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `activate` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (31,0,'','21232f297a57a5a743894a0e4a801fc3','administrator'),(32,0,'','91ec1f9324753048c0096d036a694f86','customer'),(33,0,'','1b3231655cebb7a1f783eddf27d254ca','super'),(34,0,'','5ce4d191fd14ac85a1469fb8c29b7a7b','customer2'),(35,0,'','4d9a96c8e1650dc161f1adcf5c5082a0','trainer1'),(36,0,'','6662f54a6c5033357408e6839a5c0a05','trainer2'),(37,0,'','c240642ddef994358c96da82c0361a58','manager1'),(38,0,'','8df5127cd164b5bc2d2b78410a7eea0c','manager2');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (31,'ADMINISTRATOR'),(32,'CUSTOMER'),(33,'ADMINISTRATOR'),(33,'CUSTOMER'),(33,'TRAINER'),(33,'MANAGER'),(34,'CUSTOMER'),(35,'TRAINER'),(36,'TRAINER'),(37,'MANAGER'),(38,'MANAGER');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-23 12:09:41

commit;