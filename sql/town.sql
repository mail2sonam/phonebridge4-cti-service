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
-- Table structure for table `town`
--

DROP TABLE IF EXISTS `town`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `town` (
  `town_id` int(100) NOT NULL AUTO_INCREMENT,
  `town_name` varchar(255) DEFAULT NULL,
  `district_id` int(100) DEFAULT NULL,
  PRIMARY KEY (`town_id`),
  KEY `district_id` (`district_id`),
  CONSTRAINT `district_id` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town`
--

LOCK TABLES `town` WRITE;
/*!40000 ALTER TABLE `town` DISABLE KEYS */;
INSERT INTO `town` VALUES (1,'CHENNAI',1),(2,'COIMBATORE',6),(3,'CUDDALORE',7),(4,'DHARMAPURI',8),(5,'DINDIGUL',9),(6,'ERODE',10),(7,'KANCHIPURAM',11),(8,'KANNIYAKUMARI',12),(9,'KARUR',13),(10,'KRISHNAGIRI',14),(11,'MADURAI',15),(12,'NAGAPATTINAM',17),(13,'NAMAKKAL',18),(14,'NILGIRIS',19),(15,'PERAMBALUR',20),(16,'PUDUKOTTAI',21),(18,'SALEM',23),(19,'SIVAGANGAI',24),(20,'THANJAVUR',26),(21,'THENI',28),(22,'TIRUNELVELI',35),(23,'TIRUPPUR',36),(24,'TIRUVANNAMALAI',38),(25,'TIRUVARUR',32),(26,'TIRUVALLUR',37),(27,'TUTICORIN',41),(28,'TRICHY',39),(29,'VELLORE',42),(30,'VIRUDHUNAGAR',45),(31,'ARIYALUR',2),(32,'VILLUPURAM',44),(34,'DMS',1),(40,'Kancheepuram',11),(50,'Ramanathapuram',22),(54,'Tiruchirappalli',39),(58,'Thiruvallur',37),(59,'Thoothukudi',41),(60,'Thirunelveli',25),(70,'Tharmaburi',27),(86,'Thanjavour',26),(91,'Thiruvarur',32),(95,'Vellur',42),(98,'St. Santhome',1),(100,'Mugalivakkam,',1),(101,'Nanganallur',1),(102,'Jawahar Nagar',1),(103,'Ganapathy',6),(104,'Chidambaram',7),(105,'Pathirikuppam',7),(106,'R.M Colony',9),(107,'Thottiyode',12),(108,'Melaveethi',17),(109,'Thillaipuram',18),(110,'M.G. Puram',20),(111,'Omalur',23),(112,'kaduvelli',37),(113,'yagappa Nagar',26),(114,'Ottacamund',19),(115,'Connoor',19),(116,'koduvillarpatti',28),(117,'Cheyyar',5),(118,'vettavalam road',38),(119,'Nagai old salai',32),(120,'Thiruthuraiipoodi',32),(121,'E, Millerpuram',41),(123,'Cheranmahadevi',35),(124,'Ammapalayam',36),(125,'porur',37),(126,'Mannarpuram',39),(127,'Murukeri village',42),(128,'Tirupattur',42),(129,'salamedu',44),(130,'trichy main road',44),(132,'Kulithalai',13),(146,'Tiruvarur',32),(154,'Trippur',36),(166,'Chennai A',1),(167,'Chennai B',1),(168,'SRCW',25),(169,'R.A Puram',1),(172,'P.H. Road',1),(175,'Anna Salai',1),(178,'Subbarayalu Nagar',7),(181,'Teachers Colony',10),(182,'Perundurai',10),(184,'Nagercoil',12),(185,'Karungal Post',12),(186,'Palavanthangal',11),(187,'Puzhithivakkam',11),(188,'Vadipatti',15),(189,'Thirumangalam',15),(190,'SS Colony',15),(193,'Mayiladuthurai',16),(196,'Attur',20),(200,'Shevapet',23),(201,'Omalur Post',23),(203,'Kamatchipuram',28),(204,'Periyakulam',28),(205,'Aundipatti',28),(208,'Mogappair west',1),(209,'MDM Nagar',37),(210,'Villivakkam',37),(211,'Tiruttani',30),(212,'Tirupur',36),(213,'Thiruvannamalai',38),(214,'Cheyyar',5),(215,'Palayamkottai',35),(216,'Kalakad',35),(217,'Khajamalai Colony',39),(218,'Teppakulam',15),(219,'Crawfod',39),(223,'Selaiyur',11);
/*!40000 ALTER TABLE `town` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-03 23:44:00
