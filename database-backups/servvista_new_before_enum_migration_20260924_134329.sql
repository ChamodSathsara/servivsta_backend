-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: servvista_new
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `servvista_new`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `servvista_new` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `servvista_new`;

--
-- Table structure for table `agreement_attachment`
--

DROP TABLE IF EXISTS `agreement_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agreement_attachment` (
  `agreement_id` bigint NOT NULL,
  `attachment_id` bigint NOT NULL,
  PRIMARY KEY (`agreement_id`,`attachment_id`),
  KEY `fk_agreement_attachment_attachment_id` (`attachment_id`),
  CONSTRAINT `fk_agreement_attachment_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `machine_agreement` (`agreement_id`),
  CONSTRAINT `fk_agreement_attachment_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`attachment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agreement_attachment`
--

LOCK TABLES `agreement_attachment` WRITE;
/*!40000 ALTER TABLE `agreement_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `agreement_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agreement_status_history`
--

DROP TABLE IF EXISTS `agreement_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agreement_status_history` (
  `agreement_status_history_id` bigint NOT NULL AUTO_INCREMENT,
  `agreement_id` bigint NOT NULL,
  `previous_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `new_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `changed_at` timestamp NOT NULL,
  `changed_by` bigint DEFAULT NULL,
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`agreement_status_history_id`),
  KEY `fk_agreement_status_history_agreement_id` (`agreement_id`),
  KEY `fk_agreement_status_history_changed_by` (`changed_by`),
  CONSTRAINT `fk_agreement_status_history_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `machine_agreement` (`agreement_id`),
  CONSTRAINT `fk_agreement_status_history_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agreement_status_history`
--

LOCK TABLES `agreement_status_history` WRITE;
/*!40000 ALTER TABLE `agreement_status_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `agreement_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agreement_type`
--

DROP TABLE IF EXISTS `agreement_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agreement_type` (
  `agreement_type_id` bigint NOT NULL AUTO_INCREMENT,
  `agreement_type_code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `agreement_type_name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`agreement_type_id`),
  UNIQUE KEY `agreement_type_code` (`agreement_type_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agreement_type`
--

LOCK TABLES `agreement_type` WRITE;
/*!40000 ALTER TABLE `agreement_type` DISABLE KEYS */;
INSERT INTO `agreement_type` VALUES (1,'FS','Free Service',1),(2,'MA','Maintenance Agreement',1);
/*!40000 ALTER TABLE `agreement_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `area_id` bigint NOT NULL AUTO_INCREMENT,
  `area_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `area_name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `area_code` (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attachment` (
  `attachment_id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `storage_key` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mime_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `file_size_bytes` bigint DEFAULT NULL,
  `uploaded_by_user_id` bigint DEFAULT NULL,
  `uploaded_by_portal_account_id` bigint DEFAULT NULL,
  `uploaded_at` timestamp NOT NULL,
  `note` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`attachment_id`),
  UNIQUE KEY `uk_attachment_storage_key` (`storage_key`),
  KEY `fk_attachment_uploaded_by_user_id` (`uploaded_by_user_id`),
  KEY `fk_attachment_uploaded_by_portal_account_id` (`uploaded_by_portal_account_id`),
  CONSTRAINT `fk_attachment_uploaded_by_portal_account_id` FOREIGN KEY (`uploaded_by_portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`),
  CONSTRAINT `fk_attachment_uploaded_by_user_id` FOREIGN KEY (`uploaded_by_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_log` (
  `audit_log_id` bigint NOT NULL AUTO_INCREMENT,
  `entity_type` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `entity_id` bigint NOT NULL,
  `action` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `old_values` text COLLATE utf8mb4_unicode_ci,
  `new_values` text COLLATE utf8mb4_unicode_ci,
  `performed_by_user_id` bigint DEFAULT NULL,
  `performed_by_portal_account_id` bigint DEFAULT NULL,
  `performed_at` timestamp NOT NULL,
  `ip_address` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`audit_log_id`),
  KEY `fk_audit_log_performed_by_user_id` (`performed_by_user_id`),
  KEY `fk_audit_log_performed_by_portal_account_id` (`performed_by_portal_account_id`),
  CONSTRAINT `fk_audit_log_performed_by_portal_account_id` FOREIGN KEY (`performed_by_portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`),
  CONSTRAINT `fk_audit_log_performed_by_user_id` FOREIGN KEY (`performed_by_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_log`
--

LOCK TABLES `audit_log` WRITE;
/*!40000 ALTER TABLE `audit_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `breakdown`
--

DROP TABLE IF EXISTS `breakdown`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `breakdown` (
  `breakdown_id` bigint NOT NULL AUTO_INCREMENT,
  `breakdown_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `machine_id` bigint NOT NULL,
  `customer_site_id` bigint NOT NULL,
  `reported_by_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reported_by_portal_account_id` bigint DEFAULT NULL,
  `reported_by_user_id` bigint DEFAULT NULL,
  `reported_note` text COLLATE utf8mb4_unicode_ci,
  `informed_solution_type_id` bigint DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PROCESSING',
  `approved_by` bigint DEFAULT NULL,
  `approved_at` timestamp NULL DEFAULT NULL,
  `start_note` text COLLATE utf8mb4_unicode_ci,
  `started_at` timestamp NULL DEFAULT NULL,
  `actual_solution_type_id` bigint DEFAULT NULL,
  `solution_note` text COLLATE utf8mb4_unicode_ci,
  `completed_at` timestamp NULL DEFAULT NULL,
  `cancelled_by` bigint DEFAULT NULL,
  `cancelled_at` timestamp NULL DEFAULT NULL,
  `cancel_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `contact_email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `contact_name` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contact_mobile` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reported_by_email` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `started_by_email` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `completed_by_email` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `solution_category` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_meter` bigint DEFAULT NULL,
  PRIMARY KEY (`breakdown_id`),
  UNIQUE KEY `breakdown_number` (`breakdown_number`),
  KEY `fk_breakdown_machine_id` (`machine_id`),
  KEY `fk_breakdown_customer_site_id` (`customer_site_id`),
  KEY `fk_breakdown_reported_by_portal_account_id` (`reported_by_portal_account_id`),
  KEY `fk_breakdown_reported_by_user_id` (`reported_by_user_id`),
  KEY `fk_breakdown_informed_solution_type_id` (`informed_solution_type_id`),
  KEY `fk_breakdown_actual_solution_type_id` (`actual_solution_type_id`),
  KEY `fk_breakdown_approved_by` (`approved_by`),
  KEY `fk_breakdown_cancelled_by` (`cancelled_by`),
  CONSTRAINT `fk_breakdown_actual_solution_type_id` FOREIGN KEY (`actual_solution_type_id`) REFERENCES `solution_type` (`solution_type_id`),
  CONSTRAINT `fk_breakdown_approved_by` FOREIGN KEY (`approved_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_breakdown_cancelled_by` FOREIGN KEY (`cancelled_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_breakdown_customer_site_id` FOREIGN KEY (`customer_site_id`) REFERENCES `customer_site` (`customer_site_id`),
  CONSTRAINT `fk_breakdown_informed_solution_type_id` FOREIGN KEY (`informed_solution_type_id`) REFERENCES `solution_type` (`solution_type_id`),
  CONSTRAINT `fk_breakdown_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_breakdown_reported_by_portal_account_id` FOREIGN KEY (`reported_by_portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`),
  CONSTRAINT `fk_breakdown_reported_by_user_id` FOREIGN KEY (`reported_by_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breakdown`
--

LOCK TABLES `breakdown` WRITE;
/*!40000 ALTER TABLE `breakdown` DISABLE KEYS */;
/*!40000 ALTER TABLE `breakdown` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `breakdown_attachment`
--

DROP TABLE IF EXISTS `breakdown_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `breakdown_attachment` (
  `breakdown_id` bigint NOT NULL,
  `attachment_id` bigint NOT NULL,
  PRIMARY KEY (`breakdown_id`,`attachment_id`),
  KEY `fk_breakdown_attachment_attachment_id` (`attachment_id`),
  CONSTRAINT `fk_breakdown_attachment_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`attachment_id`),
  CONSTRAINT `fk_breakdown_attachment_breakdown_id` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breakdown_attachment`
--

LOCK TABLES `breakdown_attachment` WRITE;
/*!40000 ALTER TABLE `breakdown_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `breakdown_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `breakdown_recall`
--

DROP TABLE IF EXISTS `breakdown_recall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `breakdown_recall` (
  `breakdown_recall_id` bigint NOT NULL AUTO_INCREMENT,
  `breakdown_id` bigint NOT NULL,
  `recall_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `recall_date` timestamp NOT NULL,
  `recall_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recalled_by` bigint DEFAULT NULL,
  `recalled_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`breakdown_recall_id`),
  UNIQUE KEY `recall_number` (`recall_number`),
  KEY `fk_breakdown_recall_breakdown_id` (`breakdown_id`),
  KEY `fk_breakdown_recall_recalled_by` (`recalled_by`),
  CONSTRAINT `fk_breakdown_recall_breakdown_id` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`),
  CONSTRAINT `fk_breakdown_recall_recalled_by` FOREIGN KEY (`recalled_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breakdown_recall`
--

LOCK TABLES `breakdown_recall` WRITE;
/*!40000 ALTER TABLE `breakdown_recall` DISABLE KEYS */;
/*!40000 ALTER TABLE `breakdown_recall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `breakdown_status_history`
--

DROP TABLE IF EXISTS `breakdown_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `breakdown_status_history` (
  `history_id` bigint NOT NULL AUTO_INCREMENT,
  `breakdown_id` bigint NOT NULL,
  `from_status` varchar(30) DEFAULT NULL,
  `to_status` varchar(30) NOT NULL,
  `changed_by_email` varchar(150) DEFAULT NULL,
  `changed_at` timestamp NOT NULL,
  PRIMARY KEY (`history_id`),
  KEY `breakdown_id` (`breakdown_id`),
  CONSTRAINT `breakdown_status_history_ibfk_1` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breakdown_status_history`
--

LOCK TABLES `breakdown_status_history` WRITE;
/*!40000 ALTER TABLE `breakdown_status_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `breakdown_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `breakdown_technician_assignment`
--

DROP TABLE IF EXISTS `breakdown_technician_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `breakdown_technician_assignment` (
  `breakdown_technician_assignment_id` bigint NOT NULL AUTO_INCREMENT,
  `breakdown_id` bigint NOT NULL,
  `technician_id` bigint NOT NULL,
  `assigned_at` timestamp NOT NULL,
  `assigned_by` bigint DEFAULT NULL,
  `unassigned_at` timestamp NULL DEFAULT NULL,
  `assignment_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CURRENT',
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`breakdown_technician_assignment_id`),
  KEY `fk_breakdown_technician_assignment_breakdown_id` (`breakdown_id`),
  KEY `fk_breakdown_technician_assignment_technician_id` (`technician_id`),
  KEY `fk_breakdown_technician_assignment_assigned_by` (`assigned_by`),
  CONSTRAINT `fk_breakdown_technician_assignment_assigned_by` FOREIGN KEY (`assigned_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_breakdown_technician_assignment_breakdown_id` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`),
  CONSTRAINT `fk_breakdown_technician_assignment_technician_id` FOREIGN KEY (`technician_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breakdown_technician_assignment`
--

LOCK TABLES `breakdown_technician_assignment` WRITE;
/*!40000 ALTER TABLE `breakdown_technician_assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `breakdown_technician_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `city_id` bigint NOT NULL AUTO_INCREMENT,
  `area_id` bigint NOT NULL,
  `city_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`city_id`),
  KEY `fk_city_area_id` (`area_id`),
  CONSTRAINT `fk_city_area_id` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `company_id` bigint NOT NULL AUTO_INCREMENT,
  `company_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `company_code` (`company_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'GESTETNER','Gestetner',1),(2,'FINTECH','Fintech',1);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_customer`
--

DROP TABLE IF EXISTS `company_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_customer` (
  `customer_id` bigint NOT NULL,
  `company_id` bigint NOT NULL,
  PRIMARY KEY (`customer_id`,`company_id`),
  KEY `fk_company_customer_company_id` (`company_id`),
  CONSTRAINT `fk_company_customer_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_company_customer_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_customer`
--

LOCK TABLES `company_customer` WRITE;
/*!40000 ALTER TABLE `company_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `company_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `sage_code` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address_line_1` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address_line_2` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address_line_3` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `head_office_tel_number` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `head_office_email` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `customer_grade_id` bigint DEFAULT NULL,
  `customer_type_id` bigint NOT NULL,
  `sub_customer_type_id` bigint DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `sage_code` (`sage_code`),
  KEY `fk_customer_customer_grade_id` (`customer_grade_id`),
  KEY `fk_customer_customer_type_id` (`customer_type_id`),
  KEY `fk_customer_sub_customer_type_id` (`sub_customer_type_id`),
  CONSTRAINT `fk_customer_customer_grade_id` FOREIGN KEY (`customer_grade_id`) REFERENCES `customer_grade` (`customer_grade_id`),
  CONSTRAINT `fk_customer_customer_type_id` FOREIGN KEY (`customer_type_id`) REFERENCES `customer_type` (`customer_type_id`),
  CONSTRAINT `fk_customer_sub_customer_type_id` FOREIGN KEY (`sub_customer_type_id`) REFERENCES `sub_customer_type` (`sub_customer_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_grade`
--

DROP TABLE IF EXISTS `customer_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_grade` (
  `customer_grade_id` bigint NOT NULL AUTO_INCREMENT,
  `grade_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`customer_grade_id`),
  UNIQUE KEY `grade_code` (`grade_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_grade`
--

LOCK TABLES `customer_grade` WRITE;
/*!40000 ALTER TABLE `customer_grade` DISABLE KEYS */;
INSERT INTO `customer_grade` VALUES (1,'A','Grade A',1),(2,'B','Grade B',1),(3,'C','Grade C',1);
/*!40000 ALTER TABLE `customer_grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_portal_account`
--

DROP TABLE IF EXISTS `customer_portal_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_portal_account` (
  `portal_account_id` bigint NOT NULL AUTO_INCREMENT,
  `site_contact_id` bigint NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `last_login_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`portal_account_id`),
  UNIQUE KEY `site_contact_id` (`site_contact_id`),
  CONSTRAINT `fk_customer_portal_account_site_contact_id` FOREIGN KEY (`site_contact_id`) REFERENCES `site_contact` (`site_contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_portal_account`
--

LOCK TABLES `customer_portal_account` WRITE;
/*!40000 ALTER TABLE `customer_portal_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_portal_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_salesman_assignment`
--

DROP TABLE IF EXISTS `customer_salesman_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_salesman_assignment` (
  `customer_salesman_assignment_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `salesman_id` bigint NOT NULL,
  `valid_from` date NOT NULL,
  `valid_to` date DEFAULT NULL,
  `is_current` tinyint(1) NOT NULL DEFAULT '1',
  `assigned_by` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`customer_salesman_assignment_id`),
  KEY `fk_customer_salesman_assignment_customer_id` (`customer_id`),
  KEY `fk_customer_salesman_assignment_salesman_id` (`salesman_id`),
  KEY `fk_customer_salesman_assignment_assigned_by` (`assigned_by`),
  CONSTRAINT `fk_customer_salesman_assignment_assigned_by` FOREIGN KEY (`assigned_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_customer_salesman_assignment_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_customer_salesman_assignment_salesman_id` FOREIGN KEY (`salesman_id`) REFERENCES `salesman` (`salesman_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_salesman_assignment`
--

LOCK TABLES `customer_salesman_assignment` WRITE;
/*!40000 ALTER TABLE `customer_salesman_assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_salesman_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_site`
--

DROP TABLE IF EXISTS `customer_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_site` (
  `customer_site_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `site_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address_line_1` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address_line_2` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address_line_3` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `area_id` bigint NOT NULL,
  `city_id` bigint NOT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  `is_head_office` tinyint(1) NOT NULL DEFAULT '0',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`customer_site_id`),
  KEY `fk_customer_site_customer_id` (`customer_id`),
  KEY `fk_customer_site_area_id` (`area_id`),
  KEY `fk_customer_site_city_id` (`city_id`),
  CONSTRAINT `fk_customer_site_area_id` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`),
  CONSTRAINT `fk_customer_site_city_id` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_customer_site_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_site`
--

LOCK TABLES `customer_site` WRITE;
/*!40000 ALTER TABLE `customer_site` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_type`
--

DROP TABLE IF EXISTS `customer_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_type` (
  `customer_type_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_type_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`customer_type_id`),
  UNIQUE KEY `customer_type_name` (`customer_type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_type`
--

LOCK TABLES `customer_type` WRITE;
/*!40000 ALTER TABLE `customer_type` DISABLE KEYS */;
INSERT INTO `customer_type` VALUES (1,'STANDARD',1);
/*!40000 ALTER TABLE `customer_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dealer`
--

DROP TABLE IF EXISTS `dealer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dealer` (
  `dealer_id` bigint NOT NULL AUTO_INCREMENT,
  `dealer_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dealer_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dealer_contact_number` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`dealer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dealer`
--

LOCK TABLES `dealer` WRITE;
/*!40000 ALTER TABLE `dealer` DISABLE KEYS */;
/*!40000 ALTER TABLE `dealer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `division`
--

DROP TABLE IF EXISTS `division`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `division` (
  `division_id` bigint NOT NULL AUTO_INCREMENT,
  `division_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `division_name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`division_id`),
  UNIQUE KEY `division_code` (`division_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `division`
--

LOCK TABLES `division` WRITE;
/*!40000 ALTER TABLE `division` DISABLE KEYS */;
INSERT INTO `division` VALUES (1,'RENTAL','Rental',1),(2,'OUTRIGHT','Outright',1),(3,'WORKSHOP','Workshop',1),(4,'AC','AC',1),(5,'PRODUCTION','Production',1),(6,'ELECTRONIC','Electronic',1);
/*!40000 ALTER TABLE `division` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estimate`
--

DROP TABLE IF EXISTS `estimate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estimate` (
  `estimate_id` bigint NOT NULL AUTO_INCREMENT,
  `estimate_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `machine_id` bigint NOT NULL,
  `service_schedule_id` bigint DEFAULT NULL,
  `breakdown_id` bigint DEFAULT NULL,
  `technician_id` bigint NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'DRAFT',
  `approved_by` bigint DEFAULT NULL,
  `approved_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `total_amount` decimal(14,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`estimate_id`),
  UNIQUE KEY `estimate_number` (`estimate_number`),
  KEY `fk_estimate_machine_id` (`machine_id`),
  KEY `fk_estimate_service_schedule_id` (`service_schedule_id`),
  KEY `fk_estimate_breakdown_id` (`breakdown_id`),
  KEY `fk_estimate_technician_id` (`technician_id`),
  KEY `fk_estimate_approved_by` (`approved_by`),
  KEY `fk_estimate_created_by` (`created_by`),
  CONSTRAINT `fk_estimate_approved_by` FOREIGN KEY (`approved_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_estimate_breakdown_id` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`),
  CONSTRAINT `fk_estimate_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_estimate_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_estimate_service_schedule_id` FOREIGN KEY (`service_schedule_id`) REFERENCES `service_schedule` (`service_schedule_id`),
  CONSTRAINT `fk_estimate_technician_id` FOREIGN KEY (`technician_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estimate`
--

LOCK TABLES `estimate` WRITE;
/*!40000 ALTER TABLE `estimate` DISABLE KEYS */;
/*!40000 ALTER TABLE `estimate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estimate_acceptance`
--

DROP TABLE IF EXISTS `estimate_acceptance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estimate_acceptance` (
  `estimate_acceptance_id` bigint NOT NULL AUTO_INCREMENT,
  `estimate_id` bigint NOT NULL,
  `accepted_by_type` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `portal_account_id` bigint DEFAULT NULL,
  `recorded_by_user_id` bigint DEFAULT NULL,
  `accepted_at` timestamp NOT NULL,
  `reference_number` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reference_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `channel` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'APP',
  `note` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`estimate_acceptance_id`),
  UNIQUE KEY `estimate_id` (`estimate_id`),
  KEY `fk_estimate_acceptance_portal_account_id` (`portal_account_id`),
  KEY `fk_estimate_acceptance_recorded_by_user_id` (`recorded_by_user_id`),
  CONSTRAINT `fk_estimate_acceptance_estimate_id` FOREIGN KEY (`estimate_id`) REFERENCES `estimate` (`estimate_id`),
  CONSTRAINT `fk_estimate_acceptance_portal_account_id` FOREIGN KEY (`portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`),
  CONSTRAINT `fk_estimate_acceptance_recorded_by_user_id` FOREIGN KEY (`recorded_by_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estimate_acceptance`
--

LOCK TABLES `estimate_acceptance` WRITE;
/*!40000 ALTER TABLE `estimate_acceptance` DISABLE KEYS */;
/*!40000 ALTER TABLE `estimate_acceptance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estimate_acceptance_attachment`
--

DROP TABLE IF EXISTS `estimate_acceptance_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estimate_acceptance_attachment` (
  `estimate_acceptance_id` bigint NOT NULL,
  `attachment_id` bigint NOT NULL,
  PRIMARY KEY (`estimate_acceptance_id`,`attachment_id`),
  KEY `fk_estimate_acceptance_attachment_attachment_id` (`attachment_id`),
  CONSTRAINT `fk_estimate_acceptance_attachment_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`attachment_id`),
  CONSTRAINT `fk_estimate_acceptance_attachment_estimate_acceptance_id` FOREIGN KEY (`estimate_acceptance_id`) REFERENCES `estimate_acceptance` (`estimate_acceptance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estimate_acceptance_attachment`
--

LOCK TABLES `estimate_acceptance_attachment` WRITE;
/*!40000 ALTER TABLE `estimate_acceptance_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `estimate_acceptance_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estimate_attachment`
--

DROP TABLE IF EXISTS `estimate_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estimate_attachment` (
  `estimate_id` bigint NOT NULL,
  `attachment_id` bigint NOT NULL,
  PRIMARY KEY (`estimate_id`,`attachment_id`),
  KEY `fk_estimate_attachment_attachment_id` (`attachment_id`),
  CONSTRAINT `fk_estimate_attachment_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`attachment_id`),
  CONSTRAINT `fk_estimate_attachment_estimate_id` FOREIGN KEY (`estimate_id`) REFERENCES `estimate` (`estimate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estimate_attachment`
--

LOCK TABLES `estimate_attachment` WRITE;
/*!40000 ALTER TABLE `estimate_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `estimate_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estimate_line`
--

DROP TABLE IF EXISTS `estimate_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estimate_line` (
  `estimate_line_id` bigint NOT NULL AUTO_INCREMENT,
  `estimate_id` bigint NOT NULL,
  `part_id` bigint DEFAULT NULL,
  `pending_part_name` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `qty` int NOT NULL,
  `line_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CATALOG',
  `resolved_by` bigint DEFAULT NULL,
  `resolved_at` timestamp NULL DEFAULT NULL,
  `unit_price_snapshot` decimal(12,2) NOT NULL DEFAULT '0.00',
  `note` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`estimate_line_id`),
  KEY `fk_estimate_line_estimate_id` (`estimate_id`),
  KEY `fk_estimate_line_part_id` (`part_id`),
  KEY `fk_estimate_line_resolved_by` (`resolved_by`),
  CONSTRAINT `fk_estimate_line_estimate_id` FOREIGN KEY (`estimate_id`) REFERENCES `estimate` (`estimate_id`),
  CONSTRAINT `fk_estimate_line_part_id` FOREIGN KEY (`part_id`) REFERENCES `part` (`part_id`),
  CONSTRAINT `fk_estimate_line_resolved_by` FOREIGN KEY (`resolved_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estimate_line`
--

LOCK TABLES `estimate_line` WRITE;
/*!40000 ALTER TABLE `estimate_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `estimate_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estimate_status_history`
--

DROP TABLE IF EXISTS `estimate_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estimate_status_history` (
  `estimate_status_history_id` bigint NOT NULL AUTO_INCREMENT,
  `estimate_id` bigint NOT NULL,
  `previous_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `new_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `changed_by` bigint DEFAULT NULL,
  `changed_at` timestamp NOT NULL,
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`estimate_status_history_id`),
  KEY `fk_estimate_status_history_estimate_id` (`estimate_id`),
  KEY `fk_estimate_status_history_changed_by` (`changed_by`),
  CONSTRAINT `fk_estimate_status_history_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_estimate_status_history_estimate_id` FOREIGN KEY (`estimate_id`) REFERENCES `estimate` (`estimate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estimate_status_history`
--

LOCK TABLES `estimate_status_history` WRITE;
/*!40000 ALTER TABLE `estimate_status_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `estimate_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field_service_feedback`
--

DROP TABLE IF EXISTS `field_service_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_service_feedback` (
  `feedback_id` bigint NOT NULL AUTO_INCREMENT,
  `service_schedule_id` bigint DEFAULT NULL,
  `breakdown_id` bigint DEFAULT NULL,
  `rating` int NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci,
  `submitted_by_portal_account_id` bigint NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`feedback_id`),
  UNIQUE KEY `service_schedule_id` (`service_schedule_id`),
  UNIQUE KEY `breakdown_id` (`breakdown_id`),
  KEY `fk_field_service_feedback_submitted_by_portal_account_id` (`submitted_by_portal_account_id`),
  CONSTRAINT `fk_field_service_feedback_breakdown_id` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`),
  CONSTRAINT `fk_field_service_feedback_service_schedule_id` FOREIGN KEY (`service_schedule_id`) REFERENCES `service_schedule` (`service_schedule_id`),
  CONSTRAINT `fk_field_service_feedback_submitted_by_portal_account_id` FOREIGN KEY (`submitted_by_portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_service_feedback`
--

LOCK TABLES `field_service_feedback` WRITE;
/*!40000 ALTER TABLE `field_service_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `field_service_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','complete dbml schema','SQL','V1__complete_dbml_schema.sql',557972544,'root','2026-09-22 03:57:11',20976,1),(2,'2','seed reference data','SQL','V2__seed_reference_data.sql',1632804555,'root','2026-09-22 03:57:11',12,1),(3,'3','workshop and local storage','SQL','V3__workshop_and_local_storage.sql',-1380094013,'root','2026-09-22 03:57:11',117,1),(4,'4','resolved requirements','SQL','V4__resolved_requirements.sql',1255751280,'root','2026-09-22 03:57:12',1017,1),(5,'5','runtime alignment and security','SQL','V5__runtime_alignment_and_security.sql',666441365,'root','2026-09-22 03:57:14',1268,1),(6,'6','move role to users','SQL','V6__move_role_to_users.sql',-1372646846,'root','2026-09-23 06:18:25',10321,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `installation_attachment`
--

DROP TABLE IF EXISTS `installation_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `installation_attachment` (
  `installation_submission_id` bigint NOT NULL,
  `attachment_id` bigint NOT NULL,
  PRIMARY KEY (`installation_submission_id`,`attachment_id`),
  KEY `fk_installation_attachment_attachment_id` (`attachment_id`),
  CONSTRAINT `fk_installation_attachment_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`attachment_id`),
  CONSTRAINT `fk_installation_attachment_installation_submission_id` FOREIGN KEY (`installation_submission_id`) REFERENCES `installation_submission` (`installation_submission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installation_attachment`
--

LOCK TABLES `installation_attachment` WRITE;
/*!40000 ALTER TABLE `installation_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `installation_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `installation_job`
--

DROP TABLE IF EXISTS `installation_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `installation_job` (
  `installation_job_id` bigint NOT NULL AUTO_INCREMENT,
  `job_number` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company_id` bigint NOT NULL,
  `division_id` bigint NOT NULL,
  `customer_id` bigint NOT NULL,
  `customer_site_id` bigint DEFAULT NULL,
  `machine_invoice_id` bigint DEFAULT NULL,
  `dealer_id` bigint DEFAULT NULL,
  `rep_id` bigint DEFAULT NULL,
  `assigned_technician_id` bigint NOT NULL,
  `expected_install_date` date DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ASSIGNED',
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `machine_id` bigint DEFAULT NULL,
  `agreement_type_code` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agreement_period_years` int DEFAULT NULL,
  `visits_per_year` int DEFAULT NULL,
  PRIMARY KEY (`installation_job_id`),
  UNIQUE KEY `job_number` (`job_number`),
  KEY `fk_installation_job_company_id` (`company_id`),
  KEY `fk_installation_job_division_id` (`division_id`),
  KEY `fk_installation_job_customer_id` (`customer_id`),
  KEY `fk_installation_job_customer_site_id` (`customer_site_id`),
  KEY `fk_installation_job_machine_invoice_id` (`machine_invoice_id`),
  KEY `fk_installation_job_dealer_id` (`dealer_id`),
  KEY `fk_installation_job_rep_id` (`rep_id`),
  KEY `fk_installation_job_assigned_technician_id` (`assigned_technician_id`),
  KEY `fk_installation_job_created_by` (`created_by`),
  CONSTRAINT `fk_installation_job_assigned_technician_id` FOREIGN KEY (`assigned_technician_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_installation_job_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_installation_job_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_installation_job_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_installation_job_customer_site_id` FOREIGN KEY (`customer_site_id`) REFERENCES `customer_site` (`customer_site_id`),
  CONSTRAINT `fk_installation_job_dealer_id` FOREIGN KEY (`dealer_id`) REFERENCES `dealer` (`dealer_id`),
  CONSTRAINT `fk_installation_job_division_id` FOREIGN KEY (`division_id`) REFERENCES `division` (`division_id`),
  CONSTRAINT `fk_installation_job_machine_invoice_id` FOREIGN KEY (`machine_invoice_id`) REFERENCES `machine_invoice` (`machine_invoice_id`),
  CONSTRAINT `fk_installation_job_rep_id` FOREIGN KEY (`rep_id`) REFERENCES `rep` (`rep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installation_job`
--

LOCK TABLES `installation_job` WRITE;
/*!40000 ALTER TABLE `installation_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `installation_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `installation_status_history`
--

DROP TABLE IF EXISTS `installation_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `installation_status_history` (
  `installation_status_history_id` bigint NOT NULL AUTO_INCREMENT,
  `installation_job_id` bigint NOT NULL,
  `previous_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `new_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `changed_at` timestamp NOT NULL,
  `changed_by` bigint DEFAULT NULL,
  PRIMARY KEY (`installation_status_history_id`),
  KEY `fk_installation_status_history_installation_job_id` (`installation_job_id`),
  KEY `fk_installation_status_history_changed_by` (`changed_by`),
  CONSTRAINT `fk_installation_status_history_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_installation_status_history_installation_job_id` FOREIGN KEY (`installation_job_id`) REFERENCES `installation_job` (`installation_job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installation_status_history`
--

LOCK TABLES `installation_status_history` WRITE;
/*!40000 ALTER TABLE `installation_status_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `installation_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `installation_submission`
--

DROP TABLE IF EXISTS `installation_submission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `installation_submission` (
  `installation_submission_id` bigint NOT NULL AUTO_INCREMENT,
  `installation_job_id` bigint NOT NULL,
  `machine_id` bigint NOT NULL,
  `model_id` bigint NOT NULL,
  `customer_site_id` bigint NOT NULL,
  `site_contact_id` bigint NOT NULL,
  `install_date` date NOT NULL,
  `initial_meter_reading` bigint DEFAULT NULL,
  `agreement_type_requested_id` bigint DEFAULT NULL,
  `warranty_note` text COLLATE utf8mb4_unicode_ci,
  `submitted_by` bigint NOT NULL,
  `submitted_at` timestamp NOT NULL,
  `verification_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING_VERIFICATION',
  `verified_by` bigint DEFAULT NULL,
  `verified_at` timestamp NULL DEFAULT NULL,
  `verification_note` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`installation_submission_id`),
  KEY `fk_installation_submission_installation_job_id` (`installation_job_id`),
  KEY `fk_installation_submission_machine_id` (`machine_id`),
  KEY `fk_installation_submission_model_id` (`model_id`),
  KEY `fk_installation_submission_customer_site_id` (`customer_site_id`),
  KEY `fk_installation_submission_site_contact_id` (`site_contact_id`),
  KEY `fk_installation_submission_submitted_by` (`submitted_by`),
  KEY `fk_installation_submission_verified_by` (`verified_by`),
  KEY `fk_installation_submission_agreement_type_requested_id` (`agreement_type_requested_id`),
  CONSTRAINT `fk_installation_submission_agreement_type_requested_id` FOREIGN KEY (`agreement_type_requested_id`) REFERENCES `agreement_type` (`agreement_type_id`),
  CONSTRAINT `fk_installation_submission_customer_site_id` FOREIGN KEY (`customer_site_id`) REFERENCES `customer_site` (`customer_site_id`),
  CONSTRAINT `fk_installation_submission_installation_job_id` FOREIGN KEY (`installation_job_id`) REFERENCES `installation_job` (`installation_job_id`),
  CONSTRAINT `fk_installation_submission_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_installation_submission_model_id` FOREIGN KEY (`model_id`) REFERENCES `machine_model` (`model_id`),
  CONSTRAINT `fk_installation_submission_site_contact_id` FOREIGN KEY (`site_contact_id`) REFERENCES `site_contact` (`site_contact_id`),
  CONSTRAINT `fk_installation_submission_submitted_by` FOREIGN KEY (`submitted_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_installation_submission_verified_by` FOREIGN KEY (`verified_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installation_submission`
--

LOCK TABLES `installation_submission` WRITE;
/*!40000 ALTER TABLE `installation_submission` DISABLE KEYS */;
/*!40000 ALTER TABLE `installation_submission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_otp`
--

DROP TABLE IF EXISTS `login_otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_otp` (
  `login_otp_id` bigint NOT NULL AUTO_INCREMENT,
  `portal_account_id` bigint NOT NULL,
  `otp_code_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `purpose` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'LOGIN',
  `expires_at` timestamp NOT NULL,
  `consumed_at` timestamp NULL DEFAULT NULL,
  `attempt_count` int NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL,
  `requested_ip` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`login_otp_id`),
  KEY `fk_login_otp_portal_account_id` (`portal_account_id`),
  CONSTRAINT `fk_login_otp_portal_account_id` FOREIGN KEY (`portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_otp`
--

LOCK TABLES `login_otp` WRITE;
/*!40000 ALTER TABLE `login_otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `login_otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine` (
  `machine_id` bigint NOT NULL AUTO_INCREMENT,
  `machine_reference_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `serial_number` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company_id` bigint NOT NULL,
  `division_id` bigint NOT NULL,
  `model_id` bigint NOT NULL,
  `current_status_id` bigint NOT NULL,
  `current_customer_site_id` bigint DEFAULT NULL,
  `current_main_technician_id` bigint DEFAULT NULL,
  `current_service_technician_id` bigint DEFAULT NULL,
  `machine_invoice_id` bigint DEFAULT NULL,
  `dealer_id` bigint DEFAULT NULL,
  `rep_id` bigint DEFAULT NULL,
  `original_install_date` date DEFAULT NULL,
  `credit_note_number` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by` bigint NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `current_customer_id` bigint DEFAULT NULL,
  `current_site_id` bigint DEFAULT NULL,
  PRIMARY KEY (`machine_id`),
  UNIQUE KEY `machine_reference_number` (`machine_reference_number`),
  UNIQUE KEY `serial_number` (`serial_number`),
  KEY `fk_machine_company_id` (`company_id`),
  KEY `fk_machine_division_id` (`division_id`),
  KEY `fk_machine_model_id` (`model_id`),
  KEY `fk_machine_current_status_id` (`current_status_id`),
  KEY `fk_machine_current_customer_site_id` (`current_customer_site_id`),
  KEY `fk_machine_current_main_technician_id` (`current_main_technician_id`),
  KEY `fk_machine_current_service_technician_id` (`current_service_technician_id`),
  KEY `fk_machine_machine_invoice_id` (`machine_invoice_id`),
  KEY `fk_machine_dealer_id` (`dealer_id`),
  KEY `fk_machine_rep_id` (`rep_id`),
  KEY `fk_machine_created_by` (`created_by`),
  KEY `fk_machine_updated_by` (`updated_by`),
  KEY `idx_machine_serial_reference` (`serial_number`,`machine_reference_number`),
  CONSTRAINT `fk_machine_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_machine_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_current_customer_site_id` FOREIGN KEY (`current_customer_site_id`) REFERENCES `customer_site` (`customer_site_id`),
  CONSTRAINT `fk_machine_current_main_technician_id` FOREIGN KEY (`current_main_technician_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_current_service_technician_id` FOREIGN KEY (`current_service_technician_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_current_status_id` FOREIGN KEY (`current_status_id`) REFERENCES `machine_status` (`machine_status_id`),
  CONSTRAINT `fk_machine_dealer_id` FOREIGN KEY (`dealer_id`) REFERENCES `dealer` (`dealer_id`),
  CONSTRAINT `fk_machine_division_id` FOREIGN KEY (`division_id`) REFERENCES `division` (`division_id`),
  CONSTRAINT `fk_machine_machine_invoice_id` FOREIGN KEY (`machine_invoice_id`) REFERENCES `machine_invoice` (`machine_invoice_id`),
  CONSTRAINT `fk_machine_model_id` FOREIGN KEY (`model_id`) REFERENCES `machine_model` (`model_id`),
  CONSTRAINT `fk_machine_rep_id` FOREIGN KEY (`rep_id`) REFERENCES `rep` (`rep_id`),
  CONSTRAINT `fk_machine_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine`
--

LOCK TABLES `machine` WRITE;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_agreement`
--

DROP TABLE IF EXISTS `machine_agreement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_agreement` (
  `agreement_id` bigint NOT NULL AUTO_INCREMENT,
  `agreement_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `machine_id` bigint NOT NULL,
  `agreement_type_id` bigint NOT NULL,
  `agreement_start_date` date NOT NULL,
  `agreement_end_date` date NOT NULL,
  `agreement_period_years` int NOT NULL,
  `visits_per_year` int NOT NULL,
  `annual_payment` decimal(14,2) DEFAULT NULL,
  `full_payment` decimal(14,2) DEFAULT NULL,
  `discount` decimal(14,2) DEFAULT NULL,
  `vat_percentage` decimal(5,2) DEFAULT NULL,
  `vat_amount` decimal(14,2) DEFAULT NULL,
  `agreement_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `installation_job_id` bigint DEFAULT NULL,
  `previous_agreement_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`agreement_id`),
  UNIQUE KEY `agreement_number` (`agreement_number`),
  KEY `fk_machine_agreement_machine_id` (`machine_id`),
  KEY `fk_machine_agreement_agreement_type_id` (`agreement_type_id`),
  KEY `fk_machine_agreement_installation_job_id` (`installation_job_id`),
  KEY `fk_machine_agreement_previous_agreement_id` (`previous_agreement_id`),
  KEY `fk_machine_agreement_created_by` (`created_by`),
  KEY `fk_machine_agreement_updated_by` (`updated_by`),
  CONSTRAINT `fk_machine_agreement_agreement_type_id` FOREIGN KEY (`agreement_type_id`) REFERENCES `agreement_type` (`agreement_type_id`),
  CONSTRAINT `fk_machine_agreement_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_agreement_installation_job_id` FOREIGN KEY (`installation_job_id`) REFERENCES `installation_job` (`installation_job_id`),
  CONSTRAINT `fk_machine_agreement_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_machine_agreement_previous_agreement_id` FOREIGN KEY (`previous_agreement_id`) REFERENCES `machine_agreement` (`agreement_id`),
  CONSTRAINT `fk_machine_agreement_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_agreement`
--

LOCK TABLES `machine_agreement` WRITE;
/*!40000 ALTER TABLE `machine_agreement` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_agreement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_assignment`
--

DROP TABLE IF EXISTS `machine_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_assignment` (
  `machine_assignment_id` bigint NOT NULL AUTO_INCREMENT,
  `machine_id` bigint NOT NULL,
  `customer_id` bigint NOT NULL,
  `customer_site_id` bigint NOT NULL,
  `assignment_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `agreement_id` bigint DEFAULT NULL,
  `installation_job_id` bigint DEFAULT NULL,
  `assigned_from` date NOT NULL,
  `assigned_to` date DEFAULT NULL,
  `is_current` tinyint(1) NOT NULL DEFAULT '1',
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`machine_assignment_id`),
  UNIQUE KEY `is_current` (`is_current`),
  KEY `fk_machine_assignment_machine_id` (`machine_id`),
  KEY `fk_machine_assignment_customer_id` (`customer_id`),
  KEY `fk_machine_assignment_customer_site_id` (`customer_site_id`),
  KEY `fk_machine_assignment_installation_job_id` (`installation_job_id`),
  KEY `fk_machine_assignment_created_by` (`created_by`),
  KEY `fk_machine_assignment_agreement_id` (`agreement_id`),
  CONSTRAINT `fk_machine_assignment_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `machine_agreement` (`agreement_id`),
  CONSTRAINT `fk_machine_assignment_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_assignment_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_machine_assignment_customer_site_id` FOREIGN KEY (`customer_site_id`) REFERENCES `customer_site` (`customer_site_id`),
  CONSTRAINT `fk_machine_assignment_installation_job_id` FOREIGN KEY (`installation_job_id`) REFERENCES `installation_job` (`installation_job_id`),
  CONSTRAINT `fk_machine_assignment_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_assignment`
--

LOCK TABLES `machine_assignment` WRITE;
/*!40000 ALTER TABLE `machine_assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_invoice`
--

DROP TABLE IF EXISTS `machine_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_invoice` (
  `machine_invoice_id` bigint NOT NULL AUTO_INCREMENT,
  `invoice_number` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `belita_invoice_number` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`machine_invoice_id`),
  UNIQUE KEY `invoice_number` (`invoice_number`),
  KEY `fk_machine_invoice_customer_id` (`customer_id`),
  KEY `fk_machine_invoice_created_by` (`created_by`),
  CONSTRAINT `fk_machine_invoice_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_invoice_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_invoice`
--

LOCK TABLES `machine_invoice` WRITE;
/*!40000 ALTER TABLE `machine_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_invoice_attachment`
--

DROP TABLE IF EXISTS `machine_invoice_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_invoice_attachment` (
  `machine_invoice_id` bigint NOT NULL,
  `attachment_id` bigint NOT NULL,
  PRIMARY KEY (`machine_invoice_id`,`attachment_id`),
  KEY `fk_machine_invoice_attachment_attachment_id` (`attachment_id`),
  CONSTRAINT `fk_machine_invoice_attachment_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`attachment_id`),
  CONSTRAINT `fk_machine_invoice_attachment_machine_invoice_id` FOREIGN KEY (`machine_invoice_id`) REFERENCES `machine_invoice` (`machine_invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_invoice_attachment`
--

LOCK TABLES `machine_invoice_attachment` WRITE;
/*!40000 ALTER TABLE `machine_invoice_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_invoice_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_live_location`
--

DROP TABLE IF EXISTS `machine_live_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_live_location` (
  `machine_id` bigint NOT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by_user_id` bigint DEFAULT NULL,
  `updated_by_portal_account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`machine_id`),
  KEY `fk_machine_live_location_updated_by_user_id` (`updated_by_user_id`),
  CONSTRAINT `fk_machine_live_location_updated_by_user_id` FOREIGN KEY (`updated_by_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_live_location`
--

LOCK TABLES `machine_live_location` WRITE;
/*!40000 ALTER TABLE `machine_live_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_live_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_model`
--

DROP TABLE IF EXISTS `machine_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_model` (
  `model_id` bigint NOT NULL AUTO_INCREMENT,
  `company_id` bigint NOT NULL,
  `manufacturer_id` bigint NOT NULL,
  `machine_type_id` bigint NOT NULL,
  `model_number` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `model_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  KEY `fk_machine_model_company_id` (`company_id`),
  KEY `fk_machine_model_manufacturer_id` (`manufacturer_id`),
  KEY `fk_machine_model_machine_type_id` (`machine_type_id`),
  KEY `fk_machine_model_created_by` (`created_by`),
  CONSTRAINT `fk_machine_model_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_machine_model_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_model_machine_type_id` FOREIGN KEY (`machine_type_id`) REFERENCES `machine_type` (`machine_type_id`),
  CONSTRAINT `fk_machine_model_manufacturer_id` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_model`
--

LOCK TABLES `machine_model` WRITE;
/*!40000 ALTER TABLE `machine_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_status`
--

DROP TABLE IF EXISTS `machine_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_status` (
  `machine_status_id` bigint NOT NULL AUTO_INCREMENT,
  `status_code` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status_name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `applies_to_division` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`machine_status_id`),
  UNIQUE KEY `status_code` (`status_code`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_status`
--

LOCK TABLES `machine_status` WRITE;
/*!40000 ALTER TABLE `machine_status` DISABLE KEYS */;
INSERT INTO `machine_status` VALUES (1,'AVAILABLE','Available',NULL,1),(2,'INSTALLATION_PENDING','Installation Pending',NULL,1),(3,'PENDING_VERIFICATION','Pending Verification',NULL,1),(4,'INSTALLED','Installed',NULL,1),(5,'ACTIVE_FS','Active Free Service',NULL,1),(6,'ACTIVE_MA','Active Maintenance Agreement',NULL,1),(7,'NO_SERVICE','No Service',NULL,1),(8,'OUT_OF_SERVICE','Out of Service',NULL,1),(9,'RETURNED','Returned',NULL,1),(10,'CREDIT_NOTE','Credit Note',NULL,1),(11,'REMOVED','Removed',NULL,1),(12,'DISPOSED','Disposed',NULL,1),(13,'UNDER_REPAIR','Under Repair','WORKSHOP',1),(14,'REPAIR_COMPLETED','Repair Completed','WORKSHOP',1);
/*!40000 ALTER TABLE `machine_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_status_history`
--

DROP TABLE IF EXISTS `machine_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_status_history` (
  `machine_status_history_id` bigint NOT NULL AUTO_INCREMENT,
  `machine_id` bigint NOT NULL,
  `previous_status_id` bigint DEFAULT NULL,
  `new_status_id` bigint NOT NULL,
  `changed_at` timestamp NOT NULL,
  `changed_by` bigint DEFAULT NULL,
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`machine_status_history_id`),
  KEY `fk_machine_status_history_machine_id` (`machine_id`),
  KEY `fk_machine_status_history_previous_status_id` (`previous_status_id`),
  KEY `fk_machine_status_history_new_status_id` (`new_status_id`),
  KEY `fk_machine_status_history_changed_by` (`changed_by`),
  CONSTRAINT `fk_machine_status_history_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_status_history_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_machine_status_history_new_status_id` FOREIGN KEY (`new_status_id`) REFERENCES `machine_status` (`machine_status_id`),
  CONSTRAINT `fk_machine_status_history_previous_status_id` FOREIGN KEY (`previous_status_id`) REFERENCES `machine_status` (`machine_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_status_history`
--

LOCK TABLES `machine_status_history` WRITE;
/*!40000 ALTER TABLE `machine_status_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_technician_assignment`
--

DROP TABLE IF EXISTS `machine_technician_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_technician_assignment` (
  `machine_technician_assignment_id` bigint NOT NULL AUTO_INCREMENT,
  `machine_id` bigint NOT NULL,
  `technician_id` bigint NOT NULL,
  `assignment_role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `assigned_from` date NOT NULL,
  `assigned_to` date DEFAULT NULL,
  `is_current` tinyint(1) NOT NULL DEFAULT '1',
  `assigned_by` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`machine_technician_assignment_id`),
  UNIQUE KEY `is_current` (`is_current`),
  KEY `fk_machine_technician_assignment_machine_id` (`machine_id`),
  KEY `fk_machine_technician_assignment_technician_id` (`technician_id`),
  KEY `fk_machine_technician_assignment_assigned_by` (`assigned_by`),
  CONSTRAINT `fk_machine_technician_assignment_assigned_by` FOREIGN KEY (`assigned_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_technician_assignment_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_machine_technician_assignment_technician_id` FOREIGN KEY (`technician_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_technician_assignment`
--

LOCK TABLES `machine_technician_assignment` WRITE;
/*!40000 ALTER TABLE `machine_technician_assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_technician_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_type`
--

DROP TABLE IF EXISTS `machine_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_type` (
  `machine_type_id` bigint NOT NULL AUTO_INCREMENT,
  `machine_type_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `machine_type_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`machine_type_id`),
  UNIQUE KEY `machine_type_name` (`machine_type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_type`
--

LOCK TABLES `machine_type` WRITE;
/*!40000 ALTER TABLE `machine_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_warranty`
--

DROP TABLE IF EXISTS `machine_warranty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine_warranty` (
  `machine_warranty_id` bigint NOT NULL AUTO_INCREMENT,
  `machine_id` bigint NOT NULL,
  `warranty_type_id` bigint NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `duration_months` int DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`machine_warranty_id`),
  KEY `fk_machine_warranty_machine_id` (`machine_id`),
  KEY `fk_machine_warranty_warranty_type_id` (`warranty_type_id`),
  KEY `fk_machine_warranty_created_by` (`created_by`),
  CONSTRAINT `fk_machine_warranty_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_machine_warranty_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_machine_warranty_warranty_type_id` FOREIGN KEY (`warranty_type_id`) REFERENCES `warranty_type` (`warranty_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_warranty`
--

LOCK TABLES `machine_warranty` WRITE;
/*!40000 ALTER TABLE `machine_warranty` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_warranty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufacturer`
--

DROP TABLE IF EXISTS `manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacturer` (
  `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
  `manufacturer_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`manufacturer_id`),
  UNIQUE KEY `manufacturer_name` (`manufacturer_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacturer`
--

LOCK TABLES `manufacturer` WRITE;
/*!40000 ALTER TABLE `manufacturer` DISABLE KEYS */;
/*!40000 ALTER TABLE `manufacturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meter_counter_type`
--

DROP TABLE IF EXISTS `meter_counter_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meter_counter_type` (
  `meter_counter_type_id` bigint NOT NULL AUTO_INCREMENT,
  `counter_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `counter_name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`meter_counter_type_id`),
  UNIQUE KEY `counter_code` (`counter_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meter_counter_type`
--

LOCK TABLES `meter_counter_type` WRITE;
/*!40000 ALTER TABLE `meter_counter_type` DISABLE KEYS */;
INSERT INTO `meter_counter_type` VALUES (1,'TOTAL','Total',1),(2,'BW','Black and White',1),(3,'COLOR','Color',1),(4,'SCAN','Scan',1);
/*!40000 ALTER TABLE `meter_counter_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meter_reading`
--

DROP TABLE IF EXISTS `meter_reading`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meter_reading` (
  `meter_reading_id` bigint NOT NULL AUTO_INCREMENT,
  `machine_id` bigint NOT NULL,
  `meter_counter_type_id` bigint NOT NULL,
  `reading_value` bigint NOT NULL,
  `reading_datetime` timestamp NOT NULL,
  `source_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `installation_submission_id` bigint DEFAULT NULL,
  `service_schedule_id` bigint DEFAULT NULL,
  `breakdown_id` bigint DEFAULT NULL,
  `captured_by_user_id` bigint DEFAULT NULL,
  `captured_by_portal_account_id` bigint DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `note` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`meter_reading_id`),
  KEY `fk_meter_reading_machine_id` (`machine_id`),
  KEY `fk_meter_reading_meter_counter_type_id` (`meter_counter_type_id`),
  KEY `fk_meter_reading_installation_submission_id` (`installation_submission_id`),
  KEY `fk_meter_reading_service_schedule_id` (`service_schedule_id`),
  KEY `fk_meter_reading_breakdown_id` (`breakdown_id`),
  KEY `fk_meter_reading_captured_by_user_id` (`captured_by_user_id`),
  KEY `fk_meter_reading_captured_by_portal_account_id` (`captured_by_portal_account_id`),
  CONSTRAINT `fk_meter_reading_breakdown_id` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`),
  CONSTRAINT `fk_meter_reading_captured_by_portal_account_id` FOREIGN KEY (`captured_by_portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`),
  CONSTRAINT `fk_meter_reading_captured_by_user_id` FOREIGN KEY (`captured_by_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_meter_reading_installation_submission_id` FOREIGN KEY (`installation_submission_id`) REFERENCES `installation_submission` (`installation_submission_id`),
  CONSTRAINT `fk_meter_reading_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_meter_reading_meter_counter_type_id` FOREIGN KEY (`meter_counter_type_id`) REFERENCES `meter_counter_type` (`meter_counter_type_id`),
  CONSTRAINT `fk_meter_reading_service_schedule_id` FOREIGN KEY (`service_schedule_id`) REFERENCES `service_schedule` (`service_schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meter_reading`
--

LOCK TABLES `meter_reading` WRITE;
/*!40000 ALTER TABLE `meter_reading` DISABLE KEYS */;
/*!40000 ALTER TABLE `meter_reading` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `notification_id` bigint NOT NULL AUTO_INCREMENT,
  `event_type_id` bigint DEFAULT NULL,
  `machine_id` bigint DEFAULT NULL,
  `installation_job_id` bigint DEFAULT NULL,
  `service_schedule_id` bigint DEFAULT NULL,
  `breakdown_id` bigint DEFAULT NULL,
  `estimate_id` bigint DEFAULT NULL,
  `subject` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `body` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `retry_count` int NOT NULL DEFAULT '0',
  `sent_at` timestamp NULL DEFAULT NULL,
  `event_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entity_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entity_id` bigint DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `fk_notification_event_type_id` (`event_type_id`),
  KEY `fk_notification_machine_id` (`machine_id`),
  KEY `fk_notification_installation_job_id` (`installation_job_id`),
  KEY `fk_notification_service_schedule_id` (`service_schedule_id`),
  KEY `fk_notification_breakdown_id` (`breakdown_id`),
  KEY `fk_notification_estimate_id` (`estimate_id`),
  KEY `fk_notification_created_by` (`created_by`),
  KEY `idx_notification_delivery_retry` (`status`,`retry_count`),
  KEY `idx_notification_entity` (`event_code`,`entity_type`,`entity_id`),
  CONSTRAINT `fk_notification_breakdown_id` FOREIGN KEY (`breakdown_id`) REFERENCES `breakdown` (`breakdown_id`),
  CONSTRAINT `fk_notification_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_notification_estimate_id` FOREIGN KEY (`estimate_id`) REFERENCES `estimate` (`estimate_id`),
  CONSTRAINT `fk_notification_event_type_id` FOREIGN KEY (`event_type_id`) REFERENCES `notification_event_type` (`notification_event_type_id`),
  CONSTRAINT `fk_notification_installation_job_id` FOREIGN KEY (`installation_job_id`) REFERENCES `installation_job` (`installation_job_id`),
  CONSTRAINT `fk_notification_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_notification_service_schedule_id` FOREIGN KEY (`service_schedule_id`) REFERENCES `service_schedule` (`service_schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_delivery`
--

DROP TABLE IF EXISTS `notification_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_delivery` (
  `notification_delivery_id` bigint NOT NULL AUTO_INCREMENT,
  `notification_recipient_id` bigint NOT NULL,
  `channel` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `attempted_at` timestamp NULL DEFAULT NULL,
  `delivered_at` timestamp NULL DEFAULT NULL,
  `read_at` timestamp NULL DEFAULT NULL,
  `error_message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`notification_delivery_id`),
  KEY `fk_notification_delivery_notification_recipient_id` (`notification_recipient_id`),
  CONSTRAINT `fk_notification_delivery_notification_recipient_id` FOREIGN KEY (`notification_recipient_id`) REFERENCES `notification_recipient` (`notification_recipient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_delivery`
--

LOCK TABLES `notification_delivery` WRITE;
/*!40000 ALTER TABLE `notification_delivery` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification_delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_event_type`
--

DROP TABLE IF EXISTS `notification_event_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_event_type` (
  `notification_event_type_id` bigint NOT NULL AUTO_INCREMENT,
  `event_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`notification_event_type_id`),
  UNIQUE KEY `event_code` (`event_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_event_type`
--

LOCK TABLES `notification_event_type` WRITE;
/*!40000 ALTER TABLE `notification_event_type` DISABLE KEYS */;
INSERT INTO `notification_event_type` VALUES (1,'PORTAL_OTP','Portal login OTP'),(2,'PASSWORD_RESET','Password reset'),(3,'UPCOMING_SERVICE','Upcoming service'),(4,'AGREEMENT_EXPIRING','Agreement expiry reminder'),(5,'ESTIMATE_SENT','Estimate sent'),(6,'ESTIMATE_ACCEPTED','Estimate accepted'),(7,'BREAKDOWN_COMPLETED','Breakdown completed');
/*!40000 ALTER TABLE `notification_event_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_recipient`
--

DROP TABLE IF EXISTS `notification_recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_recipient` (
  `notification_recipient_id` bigint NOT NULL AUTO_INCREMENT,
  `notification_id` bigint NOT NULL,
  `recipient_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'EMAIL_ADDRESS',
  `user_id` bigint DEFAULT NULL,
  `portal_account_id` bigint DEFAULT NULL,
  `channel` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`notification_recipient_id`),
  KEY `fk_notification_recipient_notification_id` (`notification_id`),
  KEY `fk_notification_recipient_user_id` (`user_id`),
  KEY `fk_notification_recipient_portal_account_id` (`portal_account_id`),
  CONSTRAINT `fk_notification_recipient_notification_id` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`notification_id`),
  CONSTRAINT `fk_notification_recipient_portal_account_id` FOREIGN KEY (`portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`),
  CONSTRAINT `fk_notification_recipient_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_recipient`
--

LOCK TABLES `notification_recipient` WRITE;
/*!40000 ALTER TABLE `notification_recipient` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification_recipient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `part`
--

DROP TABLE IF EXISTS `part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `part` (
  `part_id` bigint NOT NULL AUTO_INCREMENT,
  `part_code` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `part_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unit_price` decimal(12,2) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`part_id`),
  UNIQUE KEY `part_code` (`part_code`),
  KEY `fk_part_created_by` (`created_by`),
  CONSTRAINT `fk_part_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `part`
--

LOCK TABLES `part` WRITE;
/*!40000 ALTER TABLE `part` DISABLE KEYS */;
/*!40000 ALTER TABLE `part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `part_machine_model`
--

DROP TABLE IF EXISTS `part_machine_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `part_machine_model` (
  `part_id` bigint NOT NULL,
  `model_id` bigint NOT NULL,
  PRIMARY KEY (`part_id`,`model_id`),
  KEY `fk_part_machine_model_model_id` (`model_id`),
  CONSTRAINT `fk_part_machine_model_model_id` FOREIGN KEY (`model_id`) REFERENCES `machine_model` (`model_id`),
  CONSTRAINT `fk_part_machine_model_part_id` FOREIGN KEY (`part_id`) REFERENCES `part` (`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `part_machine_model`
--

LOCK TABLES `part_machine_model` WRITE;
/*!40000 ALTER TABLE `part_machine_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `part_machine_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_token`
--

DROP TABLE IF EXISTS `password_reset_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset_token` (
  `password_reset_token_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `token_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `expires_at` timestamp NOT NULL,
  `used_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`password_reset_token_id`),
  UNIQUE KEY `token_hash` (`token_hash`),
  KEY `fk_password_reset_token_user_id` (`user_id`),
  CONSTRAINT `fk_password_reset_token_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_token`
--

LOCK TABLES `password_reset_token` WRITE;
/*!40000 ALTER TABLE `password_reset_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_reset_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `permission_id` bigint NOT NULL AUTO_INCREMENT,
  `permission_code` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `permission_code` (`permission_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'WORKSHOP_CUSTOMER_CREATE','Create walk-in customers'),(2,'WORKSHOP_MACHINE_CREATE','Create workshop machines'),(3,'WORKSHOP_REPAIR_UPDATE','Update workshop repairs'),(4,'MACHINE_VIEW_OWN_CUSTOMERS','View assigned customer machines'),(5,'MACHINE_VIEW_ALL','View all machines'),(6,'AGREEMENT_VIEW','View agreements'),(7,'ESTIMATE_VIEW','View estimates');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_account_machine`
--

DROP TABLE IF EXISTS `portal_account_machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portal_account_machine` (
  `portal_account_machine_id` bigint NOT NULL AUTO_INCREMENT,
  `portal_account_id` bigint NOT NULL,
  `machine_id` bigint NOT NULL,
  `granted_at` timestamp NOT NULL,
  `granted_by` bigint DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `revoked_at` timestamp NULL DEFAULT NULL,
  `revoked_by` bigint DEFAULT NULL,
  `revoke_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`portal_account_machine_id`),
  KEY `fk_portal_account_machine_revoked_by` (`revoked_by`),
  KEY `fk_portal_account_machine_portal_account_id` (`portal_account_id`),
  KEY `fk_portal_account_machine_machine_id` (`machine_id`),
  KEY `fk_portal_account_machine_granted_by` (`granted_by`),
  CONSTRAINT `fk_portal_account_machine_granted_by` FOREIGN KEY (`granted_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_portal_account_machine_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_portal_account_machine_portal_account_id` FOREIGN KEY (`portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`),
  CONSTRAINT `fk_portal_account_machine_revoked_by` FOREIGN KEY (`revoked_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_account_machine`
--

LOCK TABLES `portal_account_machine` WRITE;
/*!40000 ALTER TABLE `portal_account_machine` DISABLE KEYS */;
/*!40000 ALTER TABLE `portal_account_machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_refresh_token`
--

DROP TABLE IF EXISTS `portal_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portal_refresh_token` (
  `portal_refresh_token_id` bigint NOT NULL AUTO_INCREMENT,
  `portal_account_id` bigint NOT NULL,
  `token_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `issued_at` timestamp NOT NULL,
  `expires_at` timestamp NOT NULL,
  `revoked_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`portal_refresh_token_id`),
  UNIQUE KEY `token_hash` (`token_hash`),
  KEY `fk_portal_refresh_token_portal_account_id` (`portal_account_id`),
  CONSTRAINT `fk_portal_refresh_token_portal_account_id` FOREIGN KEY (`portal_account_id`) REFERENCES `customer_portal_account` (`portal_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_refresh_token`
--

LOCK TABLES `portal_refresh_token` WRITE;
/*!40000 ALTER TABLE `portal_refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `portal_refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `refresh_token_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `token_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `device_label` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `issued_at` timestamp NOT NULL,
  `expires_at` timestamp NOT NULL,
  `revoked_at` timestamp NULL DEFAULT NULL,
  `ip_address` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`refresh_token_id`),
  UNIQUE KEY `token_hash` (`token_hash`),
  KEY `fk_refresh_token_user_id` (`user_id`),
  CONSTRAINT `fk_refresh_token_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rep`
--

DROP TABLE IF EXISTS `rep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rep` (
  `rep_id` bigint NOT NULL AUTO_INCREMENT,
  `rep_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rep_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rep_mobile_number` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`rep_id`),
  UNIQUE KEY `rep_code` (`rep_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rep`
--

LOCK TABLES `rep` WRITE;
/*!40000 ALTER TABLE `rep` DISABLE KEYS */;
/*!40000 ALTER TABLE `rep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `permission_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`role`,`permission_id`),
  KEY `fk_role_permission_permission_id` (`permission_id`),
  CONSTRAINT `fk_role_permission_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesman`
--

DROP TABLE IF EXISTS `salesman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salesman` (
  `salesman_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `salesman_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `salesman_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mobile_number` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`salesman_id`),
  UNIQUE KEY `salesman_code` (`salesman_code`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `fk_salesman_company_id` (`company_id`),
  CONSTRAINT `fk_salesman_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesman`
--

LOCK TABLES `salesman` WRITE;
/*!40000 ALTER TABLE `salesman` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_recall`
--

DROP TABLE IF EXISTS `service_recall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_recall` (
  `service_recall_id` bigint NOT NULL AUTO_INCREMENT,
  `service_schedule_id` bigint NOT NULL,
  `recall_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `previous_scheduled_date` date NOT NULL,
  `new_scheduled_date` date NOT NULL,
  `recall_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recalled_by` bigint DEFAULT NULL,
  `recalled_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`service_recall_id`),
  UNIQUE KEY `recall_number` (`recall_number`),
  KEY `fk_service_recall_service_schedule_id` (`service_schedule_id`),
  KEY `fk_service_recall_recalled_by` (`recalled_by`),
  CONSTRAINT `fk_service_recall_recalled_by` FOREIGN KEY (`recalled_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_service_recall_service_schedule_id` FOREIGN KEY (`service_schedule_id`) REFERENCES `service_schedule` (`service_schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_recall`
--

LOCK TABLES `service_recall` WRITE;
/*!40000 ALTER TABLE `service_recall` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_recall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_schedule`
--

DROP TABLE IF EXISTS `service_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_schedule` (
  `service_schedule_id` bigint NOT NULL AUTO_INCREMENT,
  `agreement_id` bigint NOT NULL,
  `machine_id` bigint NOT NULL,
  `agreement_year_number` int NOT NULL,
  `visit_number` int NOT NULL,
  `expected_visit_date` date NOT NULL,
  `scheduled_date` date DEFAULT NULL,
  `actual_visit_date` date DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'SCHEDULED',
  `assigned_technician_id` bigint DEFAULT NULL,
  `start_note` text COLLATE utf8mb4_unicode_ci,
  `started_at` timestamp NULL DEFAULT NULL,
  `completed_at` timestamp NULL DEFAULT NULL,
  `solution_type_id` bigint DEFAULT NULL,
  `solution_note` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`service_schedule_id`),
  KEY `fk_service_schedule_agreement_id` (`agreement_id`),
  KEY `fk_service_schedule_machine_id` (`machine_id`),
  KEY `fk_service_schedule_assigned_technician_id` (`assigned_technician_id`),
  KEY `fk_service_schedule_solution_type_id` (`solution_type_id`),
  KEY `idx_service_display_date` (`status`,`expected_visit_date`,`scheduled_date`),
  CONSTRAINT `fk_service_schedule_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `machine_agreement` (`agreement_id`),
  CONSTRAINT `fk_service_schedule_assigned_technician_id` FOREIGN KEY (`assigned_technician_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_service_schedule_machine_id` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_service_schedule_solution_type_id` FOREIGN KEY (`solution_type_id`) REFERENCES `solution_type` (`solution_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_schedule`
--

LOCK TABLES `service_schedule` WRITE;
/*!40000 ALTER TABLE `service_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_contact`
--

DROP TABLE IF EXISTS `site_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `site_contact` (
  `site_contact_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_site_id` bigint NOT NULL,
  `contact_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mobile_number` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `designation` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_primary` tinyint(1) NOT NULL DEFAULT '0',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`site_contact_id`),
  KEY `fk_site_contact_customer_site_id` (`customer_site_id`),
  CONSTRAINT `fk_site_contact_customer_site_id` FOREIGN KEY (`customer_site_id`) REFERENCES `customer_site` (`customer_site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_contact`
--

LOCK TABLES `site_contact` WRITE;
/*!40000 ALTER TABLE `site_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solution_type`
--

DROP TABLE IF EXISTS `solution_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solution_type` (
  `solution_type_id` bigint NOT NULL AUTO_INCREMENT,
  `solution_code` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `solution_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`solution_type_id`),
  UNIQUE KEY `solution_code` (`solution_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solution_type`
--

LOCK TABLES `solution_type` WRITE;
/*!40000 ALTER TABLE `solution_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `solution_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_customer_type`
--

DROP TABLE IF EXISTS `sub_customer_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_customer_type` (
  `sub_customer_type_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_type_id` bigint NOT NULL,
  `sub_customer_type_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sub_customer_type_id`),
  KEY `fk_sub_customer_type_customer_type_id` (`customer_type_id`),
  CONSTRAINT `fk_sub_customer_type_customer_type_id` FOREIGN KEY (`customer_type_id`) REFERENCES `customer_type` (`customer_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_customer_type`
--

LOCK TABLES `sub_customer_type` WRITE;
/*!40000 ALTER TABLE `sub_customer_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `sub_customer_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_company`
--

DROP TABLE IF EXISTS `user_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_company` (
  `user_id` bigint NOT NULL,
  `company_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`company_id`),
  KEY `fk_user_company_company_id` (`company_id`),
  CONSTRAINT `fk_user_company_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_user_company_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_company`
--

LOCK TABLES `user_company` WRITE;
/*!40000 ALTER TABLE `user_company` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_service_area`
--

DROP TABLE IF EXISTS `user_service_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_service_area` (
  `user_id` bigint NOT NULL,
  `city_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`,`city_id`),
  KEY `fk_user_service_area_city_id` (`city_id`),
  CONSTRAINT `fk_user_service_area_city_id` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_user_service_area_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_service_area`
--

LOCK TABLES `user_service_area` WRITE;
/*!40000 ALTER TABLE `user_service_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_service_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `tech_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mobile_number` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `division_id` bigint DEFAULT NULL,
  `primary_area_id` bigint DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `role` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `tech_code` (`tech_code`),
  KEY `fk_users_division_id` (`division_id`),
  KEY `fk_users_primary_area_id` (`primary_area_id`),
  CONSTRAINT `fk_users_division_id` FOREIGN KEY (`division_id`) REFERENCES `division` (`division_id`),
  CONSTRAINT `fk_users_primary_area_id` FOREIGN KEY (`primary_area_id`) REFERENCES `area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,'chamod','0758806437','sathsara@gestetnersl.com','$2a$12$PVBZaSpxU1uwnpjONdEqhuxX86SguWWnOQ8KHxnuOHCYW2Sx79.lm',1,NULL,NULL,'2026-09-24 08:12:10',NULL,NULL,NULL,'ADMIN');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warranty_type`
--

DROP TABLE IF EXISTS `warranty_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warranty_type` (
  `warranty_type_id` bigint NOT NULL AUTO_INCREMENT,
  `warranty_type_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `warranty_type_name` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`warranty_type_id`),
  UNIQUE KEY `warranty_type_code` (`warranty_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warranty_type`
--

LOCK TABLES `warranty_type` WRITE;
/*!40000 ALTER TABLE `warranty_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `warranty_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workshop_repair_attachment`
--

DROP TABLE IF EXISTS `workshop_repair_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workshop_repair_attachment` (
  `workshop_repair_job_id` bigint NOT NULL,
  `attachment_id` bigint NOT NULL,
  PRIMARY KEY (`workshop_repair_job_id`,`attachment_id`),
  KEY `attachment_id` (`attachment_id`),
  CONSTRAINT `workshop_repair_attachment_ibfk_1` FOREIGN KEY (`workshop_repair_job_id`) REFERENCES `workshop_repair_job` (`workshop_repair_job_id`),
  CONSTRAINT `workshop_repair_attachment_ibfk_2` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`attachment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workshop_repair_attachment`
--

LOCK TABLES `workshop_repair_attachment` WRITE;
/*!40000 ALTER TABLE `workshop_repair_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `workshop_repair_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workshop_repair_job`
--

DROP TABLE IF EXISTS `workshop_repair_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workshop_repair_job` (
  `workshop_repair_job_id` bigint NOT NULL AUTO_INCREMENT,
  `repair_number` varchar(30) NOT NULL,
  `machine_id` bigint NOT NULL,
  `customer_id` bigint NOT NULL,
  `assigned_technician_id` bigint NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'RECEIVED',
  `fault_note` text NOT NULL,
  `received_at` timestamp NOT NULL,
  `started_at` timestamp NULL DEFAULT NULL,
  `completed_at` timestamp NULL DEFAULT NULL,
  `completion_note` text,
  `customer_email` varchar(150) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`workshop_repair_job_id`),
  UNIQUE KEY `repair_number` (`repair_number`),
  KEY `fk_workshop_machine` (`machine_id`),
  KEY `fk_workshop_customer` (`customer_id`),
  KEY `fk_workshop_technician` (`assigned_technician_id`),
  KEY `idx_workshop_status` (`status`,`assigned_technician_id`),
  CONSTRAINT `fk_workshop_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_workshop_machine` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`),
  CONSTRAINT `fk_workshop_technician` FOREIGN KEY (`assigned_technician_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workshop_repair_job`
--

LOCK TABLES `workshop_repair_job` WRITE;
/*!40000 ALTER TABLE `workshop_repair_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `workshop_repair_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workshop_repair_status_history`
--

DROP TABLE IF EXISTS `workshop_repair_status_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workshop_repair_status_history` (
  `history_id` bigint NOT NULL AUTO_INCREMENT,
  `workshop_repair_job_id` bigint NOT NULL,
  `previous_status` varchar(30) DEFAULT NULL,
  `new_status` varchar(30) NOT NULL,
  `note` varchar(500) DEFAULT NULL,
  `changed_by` bigint DEFAULT NULL,
  `changed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`history_id`),
  KEY `workshop_repair_job_id` (`workshop_repair_job_id`),
  CONSTRAINT `workshop_repair_status_history_ibfk_1` FOREIGN KEY (`workshop_repair_job_id`) REFERENCES `workshop_repair_job` (`workshop_repair_job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workshop_repair_status_history`
--

LOCK TABLES `workshop_repair_status_history` WRITE;
/*!40000 ALTER TABLE `workshop_repair_status_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `workshop_repair_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'servvista_new'
--

--
-- Dumping routines for database 'servvista_new'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-24 13:43:30
