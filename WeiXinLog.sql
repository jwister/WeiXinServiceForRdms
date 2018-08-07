CREATE DATABASE  IF NOT EXISTS `weixin` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `weixin`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: weixin
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `curenttoken`
--

DROP TABLE IF EXISTS `curenttoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curenttoken` (
  `id` int(11) NOT NULL,
  `curenttoken` varchar(2000) DEFAULT NULL,
  `curenttime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curenttoken`
--

LOCK TABLES `curenttoken` WRITE;
/*!40000 ALTER TABLE `curenttoken` DISABLE KEYS */;
INSERT INTO `curenttoken` VALUES (1,'EzTGBq6pqk5Agy9iZRGWV-67tHM7jt4uXYzZTmGIPgxsTodzROjw-eVdNy4RFyWa8t4OyKDIyBLz9Rxm8MYP3fUsHbOpyGnxXamTk5FtqQTSWWgF4Amw_75m9f7kilVo-bcStsnqK-DDzClgnsDFEkzfeCpQBAfI0ssjk6-hsPYwinhh4kl26V81RzYVF2cgz-j23XG_WplhaZwjP7kWLQ','2018-06-25 14:12:38');
/*!40000 ALTER TABLE `curenttoken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usercfg`
--

DROP TABLE IF EXISTS `usercfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usercfg` (
  `username` varchar(400) NOT NULL,
  `password` varchar(400) DEFAULT NULL,
  `projectname` varchar(400) DEFAULT NULL,
  `projectid` varchar(400) DEFAULT NULL,
  `workload` varchar(400) DEFAULT NULL,
  `content` varchar(400) DEFAULT NULL,
  `enable` varchar(45) DEFAULT '1',
  `issend` varchar(45) DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usercfg`
--

LOCK TABLES `usercfg` WRITE;
/*!40000 ALTER TABLE `usercfg` DISABLE KEYS */;
INSERT INTO `usercfg` VALUES ('hanchao','hanchao','111','2112','10','修改代码','0','0'),('liujiang','liujiang','ceshi','2112','10','修改日志','1','0');
/*!40000 ALTER TABLE `usercfg` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-25 17:13:38
