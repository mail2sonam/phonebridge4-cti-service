-- MySQL dump 10.14  Distrib 5.5.68-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: telephony
-- ------------------------------------------------------
-- Server version	5.5.68-MariaDB

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `userextension` varchar(255) DEFAULT NULL,
  `extensionstatus` varchar(255) DEFAULT NULL,
  `onbreak` varchar(255) DEFAULT NULL,
  `popupstatus` varchar(255) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `context` varchar(255) DEFAULT NULL,
  `callstatus` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `deleted` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `extensiontype` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `popupstatusupdatetime` varchar(255) DEFAULT NULL,
  `agenttype` varchar(255) DEFAULT NULL,
  `ivruser` tinyint(1) DEFAULT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `queue_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (7,'praveena123','4000','0','none','free','agent','praveena','from-internal','free','9',NULL,'0','SIP','praveena.chinigi@gmail.com',NULL,NULL,0,'ppp',NULL,NULL),(16,'ffff',NULL,NULL,NULL,NULL,'department','fff',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'ppp',NULL),(67,'Priya','','0','none','free','Admin','Priya','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(68,'Pavithira','3000','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(69,'Padma','3001','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(70,'DurgaDevi','3002','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(71,'Megala','3003','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(72,'Roshni','3004','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(73,'Divya','3005','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(74,'Janani','3006','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(75,'Selvi','3007','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(76,'AdhishtaDeva','3008','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(77,'Vineetha','3009','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(78,'Poornima','3010','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(79,'Gunaseeli','3011','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(80,'Hemamalini','3012','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(81,'AbdulArif','3013','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(82,'PrakashRaj','3014','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(83,'Vijay','3015','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(84,'Bazurdeen','3016','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(85,'ParimalaGandhi','3017','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(86,'AsabiyaResbin','3018','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(87,'AnuBharathy','3019','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(88,'Pooni','3020','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(89,'Boobalan','3021','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(90,'SudhanaKumar','3022','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(91,'Nandini','3023','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(92,'RamyaRavi','3024','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(93,'Suresh','3025','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(94,'Jayanthi','3026','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(95,'ArulThangam','3027','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(96,'Harishtest','2525','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(97,'priyatest','2626','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL),(98,'SriBhavatharani','3028','0','none','free','agent','Amtex@123','from-internal','free','1',NULL,'0','SIP','pravee@gmail.com',NULL,NULL,0,'HO',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-11 16:52:41
