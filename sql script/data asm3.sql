-- Dump completed on 2024-04-12 11:02:51
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: monorail.proxy.rlwy.net    Database: asm3
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

drop database if exists `asm3`;
create database `asm3`;
use `asm3`;

--
-- Table structure for table `active`
--

DROP TABLE IF EXISTS `active`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `active` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT 'Active',
  `description` text,
  `value` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `active`
--

LOCK TABLES `active` WRITE;
/*!40000 ALTER TABLE `active` DISABLE KEYS */;
INSERT INTO `active` VALUES (1,'Active',NULL,1),(2,'Active',NULL,1),(3,'Active',NULL,1),(4,'Active',NULL,1),(5,'Active',NULL,1),(6,'Active',NULL,1),(7,'Active',NULL,1),(8,'Active',NULL,1),(9,'Active',NULL,1),(10,'Active',NULL,1),(11,'Active',NULL,1),(12,'Active',NULL,1),(13,'Active',NULL,1),(14,'Active',NULL,1),(15,'Active',NULL,1),(16,'Active',NULL,1),(17,'Active',NULL,1),(18,'Active',NULL,1),(19,'Active',NULL,1);
/*!40000 ALTER TABLE `active` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clinic`
--

DROP TABLE IF EXISTS `clinic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clinic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` text,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clinic`
--

LOCK TABLES `clinic` WRITE;
/*!40000 ALTER TABLE `clinic` DISABLE KEYS */;
INSERT INTO `clinic` VALUES (1,'Minh Đức','Tp Bến Tre',NULL,300000,'bla bla',NULL),(2,'Bệnh viện Chợ Rẫy','TP HCM',NULL,500000,'okok',NULL),(3,'Bệnh viện đa khoa khu vực Thủ Đức','TP Thủ Đức',NULL,400000,'hehe',NULL),(4,'Bệnh viện Cù Lao Minh','MCN, Bến Tre',NULL,250000,'hihi',NULL);
/*!40000 ALTER TABLE `clinic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_information`
--

DROP TABLE IF EXISTS `doctor_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `introduce` text,
  `training_process` text,
  `achievements` text,
  `specialization_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `specialization_id` (`specialization_id`),
  CONSTRAINT `doctor_information_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `doctor_information_ibfk_2` FOREIGN KEY (`specialization_id`) REFERENCES `specialization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_information`
--

LOCK TABLES `doctor_information` WRITE;
/*!40000 ALTER TABLE `doctor_information` DISABLE KEYS */;
INSERT INTO `doctor_information` VALUES (1,'Introduction 1','Training 1','Achievements 1',1,2),(2,'Introduction 2','Training 2','Achievements 2',2,3),(3,'Introduction 3','Training 3','Achievements 3',3,4),(4,'Introduction 4','Training 4','Achievements 4',7,5),(5,'Introduction 5','Training 5','Achievements 5',8,6),(6,'Introduction 6','Training 6','Achievements 6',10,7),(7,'Introduction 7','Training 7','Achievements 7',16,8),(8,'Introduction 8','Training 8','Achievements 8',17,9);
/*!40000 ALTER TABLE `doctor_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `doctor_information_id` int DEFAULT NULL,
  `pathology` varchar(255) DEFAULT NULL,
  `description` text,
  `status_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `doctor_information_id` (`doctor_information_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `patients_ibfk_2` FOREIGN KEY (`doctor_information_id`) REFERENCES `doctor_information` (`id`),
  CONSTRAINT `patients_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `patients_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,'Patient 1','Male','111111111','1990-01-01','Address 1',2,'Pathology 1',NULL,NULL,10,NULL,NULL,NULL),(2,'Patient 2','Female','222222222','1991-02-02','Address 2',2,'Pathology 2',NULL,NULL,11,NULL,NULL,NULL),(3,'Patient 3','Male','333333333','1992-03-03','Address 3',2,'Pathology 3',NULL,NULL,12,NULL,NULL,NULL),(4,'Patient 4','Male','444444444','1993-04-04','Address 4',3,'Pathology 4',NULL,NULL,13,NULL,NULL,NULL),(5,'Patient 5','Female','555555555','1994-05-05','Address 5',3,'Pathology 5',NULL,NULL,14,NULL,NULL,NULL),(6,'Patient 6','Male','666666666','1995-06-06','Address 6',7,'Pathology 6',NULL,NULL,15,NULL,NULL,NULL),(7,'Patient 7','Male','777777777','1996-07-07','Address 7',8,'Pathology 7',NULL,NULL,16,NULL,NULL,NULL);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients_status`
--

DROP TABLE IF EXISTS `patients_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` text,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients_status`
--

LOCK TABLES `patients_status` WRITE;
/*!40000 ALTER TABLE `patients_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `patients_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_DOCTOR'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `doctor_information_id` int DEFAULT NULL,
  `patients_id` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `price` double DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `doctor_information_id` (`doctor_information_id`),
  KEY `patients_id` (`patients_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`doctor_information_id`) REFERENCES `doctor_information` (`id`),
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`patients_id`) REFERENCES `patients` (`id`),
  CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,2,1,'2023-01-16','09:00:00',300000,NULL,NULL,NULL,NULL),(2,2,2,'2023-01-17','08:00:00',300000,NULL,NULL,NULL,NULL),(3,2,3,'2023-01-18','15:00:00',300000,NULL,NULL,NULL,NULL),(4,3,4,'2023-01-16','09:00:00',300000,NULL,NULL,NULL,NULL),(5,3,5,'2023-01-17','08:00:00',300000,NULL,NULL,NULL,NULL),(6,7,6,'2023-01-18','15:00:00',500000,NULL,NULL,NULL,NULL),(7,8,7,'2023-01-18','15:00:00',500000,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialization`
--

DROP TABLE IF EXISTS `specialization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialization` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `image` varchar(255) DEFAULT NULL,
  `clinic_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `clinic_id` (`clinic_id`),
  CONSTRAINT `specialization_ibfk_1` FOREIGN KEY (`clinic_id`) REFERENCES `clinic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialization`
--

LOCK TABLES `specialization` WRITE;
/*!40000 ALTER TABLE `specialization` DISABLE KEYS */;
INSERT INTO `specialization` VALUES (1,'Nội khoa','Khoa khám và điều trị các bệnh nội khoa như tim mạch, hô hấp, tiêu hóa, thận, nội tiết, thần kinh,...',NULL,1),(2,'Ngoại khoa','Khoa khám và điều trị các bệnh ngoại khoa như phẫu thuật tổng quát, phẫu thuật chấn thương chỉnh hình, phẫu thuật sản phụ khoa,...',NULL,1),(3,'Sản khoa','Khoa khám và điều trị các bệnh sản phụ khoa như thai sản, phụ khoa, hiếm muộn,...',NULL,1),(4,'Chẩn đoán hình ảnh','Khoa thực hiện các kỹ thuật chẩn đoán hình ảnh như X-quang, siêu âm, chụp CT, chụp MRI,...',NULL,1),(5,'Xét nghiệm','Khoa thực hiện các xét nghiệm y khoa như xét nghiệm máu, xét nghiệm nước tiểu, xét nghiệm sinh hóa,...',NULL,1),(6,'Răng hàm mặt','Khoa khám và điều trị các bệnh răng hàm mặt như sâu răng, viêm nha chu, niềng răng,...',NULL,1),(7,'Tai mũi họng','Khoa khám và điều trị các bệnh tai mũi họng như viêm tai giữa, viêm mũi dị ứng, viêm họng,...',NULL,1),(8,'Y học cổ truyền','Khoa khám và điều trị các bệnh bằng y học cổ truyền như châm cứu, bấm huyệt, xoa bóp,...',NULL,1),(9,'Tim mạch','Khoa khám và điều trị các bệnh về tim mạch như suy tim, nhồi máu cơ tim,...',NULL,2),(10,'Hô hấp','Khoa khám và điều trị các bệnh về hô hấp như viêm phổi, hen suyễn,...',NULL,2),(11,'Tiêu hóa','Khoa khám và điều trị các bệnh về tiêu hóa như viêm dạ dày, táo bón,...',NULL,2),(12,'Thận','Khoa khám và điều trị các bệnh về thận như suy thận, sỏi thận,...',NULL,2),(13,'Nội tiết','Khoa khám và điều trị các bệnh về nội tiết như tiểu đường, suy giáp,...',NULL,2),(14,'Thần kinh','Khoa khám và điều trị các bệnh về thần kinh như đột quỵ, đau đầu,...',NULL,2),(15,'Phẫu thuật tổng quát','Khoa khám và điều trị các bệnh ngoại khoa tổng quát như viêm ruột thừa, ung thư đại tràng,...',NULL,2),(16,'Phẫu thuật chấn thương chỉnh hình','Khoa khám và điều trị các bệnh ngoại khoa chấn thương chỉnh hình như gãy xương, trật khớp,...',NULL,2),(17,'Phẫu thuật sản phụ khoa','Khoa khám và điều trị các bệnh ngoại khoa sản phụ khoa như phẫu thuật phụ khoa, phẫu thuật thai sản,...',NULL,2),(18,'Y học hạt nhân','Khoa khám và điều trị các bệnh bằng y học hạt nhân như xạ trị, xạ trị điều biến cường độ,...',NULL,2),(19,'Y học truyền nhiễm','Khoa khám và điều trị các bệnh truyền nhiễm như COVID-19, HIV/AIDS,...',NULL,2),(20,'Chẩn đoán hình ảnh','Khoa thực hiện các kỹ thuật chẩn đoán hình ảnh như X-quang, siêu âm, chụp CT, chụp MRI,...',NULL,3),(21,'Xét nghiệm','Khoa thực hiện các xét nghiệm y khoa như xét nghiệm máu, xét nghiệm nước tiểu, xét nghiệm sinh hóa,...',NULL,3);
/*!40000 ALTER TABLE `specialization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(128) NOT NULL,
  `matching_password` varchar(128) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` text,
  `avatar` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `active_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `active_id` (`active_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`active_id`) REFERENCES `active` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Admin','Nam','admin@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,1),(2,'doctor1','Nam','doctor1@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,2),(3,'doctor2','Nam','doctor2@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2N0b3IyQGdtYWlsLmNvbSIsImlhdCI6MTcxMTkzNTYxNywiZXhwIjoxNzEyNTQwNDE3fQ.azS8U6aJQiAvxfKL-4Zi7GfIuoAeigTyD-9qgqQY528',3),(4,'doctor3','Nam','doctor3@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,4),(5,'doctor4','Nam','doctor4@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,5),(6,'doctor5','Nam','doctor5@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,6),(7,'doctor6','Nam','doctor6@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,7),(8,'doctor7','Nam','doctor7@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,8),(9,'doctor8','Nam','doctor8@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,9),(10,'user1','Nam','user1@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,10),(11,'user2','Nam','user2@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,11),(12,'user3','Nam','user3@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,12),(13,'user4','Nam','user4@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,13),(14,'user5','Nam','user5@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,14),(15,'user6','Nam','user6@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,15),(16,'user7','Nam','user7@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,16),(17,'user8','Nam','user8@gmail.com','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','$2a$10$Zb32nEu4dL3fvzayv5GoXeMuAsdIdWPcNjbhZIxbUEoaPXNRh0t22','Cẩm Sơn',NULL,NULL,'0971563392',NULL,17);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(1,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-12 11:03:17