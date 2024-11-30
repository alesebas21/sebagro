CREATE DATABASE  IF NOT EXISTS `sebagro` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `sebagro`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sebagro
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento` (
  `id_movimiento` int(11) NOT NULL AUTO_INCREMENT,
  `id_sociedad` int(11) DEFAULT NULL,
  `id_localidad_origen` int(11) DEFAULT NULL,
  `id_localidad_destino` int(11) DEFAULT NULL,
  `id_tipo_movimiento` int(11) DEFAULT NULL,
  `id_chofer_transporte` int(11) DEFAULT NULL,
  `id_estado` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `ctg` varchar(50) NOT NULL,
  `fecha` date NOT NULL,
  `insert_id_user` int(11) DEFAULT NULL,
  `id_contraparte` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_movimiento`),
  UNIQUE KEY `ctg` (`ctg`),
  KEY `id_tipo_movimiento` (`id_tipo_movimiento`),
  KEY `insert_id_user` (`insert_id_user`),
  KEY `idx_movimiento_id_sociedad` (`id_sociedad`),
  KEY `idx_movimiento_id_localidad_origen` (`id_localidad_origen`),
  KEY `idx_movimiento_id_localidad_destino` (`id_localidad_destino`),
  KEY `idx_movimiento_id_chofer_transporte` (`id_chofer_transporte`),
  KEY `idx_movimiento_ctg` (`ctg`),
  KEY `idx_movimiento_id_estado` (`id_estado`),
  KEY `idx_movimiento_fecha` (`fecha`),
  KEY `idx_movimiento_id_producto` (`id_producto`),
  KEY `movimiento_ibfk_9` (`id_contraparte`),
  CONSTRAINT `movimiento_ibfk_1` FOREIGN KEY (`id_sociedad`) REFERENCES `sociedades` (`id_sociedad`),
  CONSTRAINT `movimiento_ibfk_2` FOREIGN KEY (`id_localidad_origen`) REFERENCES `localidades` (`id_localidad`),
  CONSTRAINT `movimiento_ibfk_3` FOREIGN KEY (`id_localidad_destino`) REFERENCES `localidades` (`id_localidad`),
  CONSTRAINT `movimiento_ibfk_4` FOREIGN KEY (`id_tipo_movimiento`) REFERENCES `tipo_movimiento` (`id_tipo_movimiento`),
  CONSTRAINT `movimiento_ibfk_5` FOREIGN KEY (`id_chofer_transporte`) REFERENCES `chofer_transporte` (`id_chofer_transporte`),
  CONSTRAINT `movimiento_ibfk_6` FOREIGN KEY (`id_estado`) REFERENCES `estado` (`id_estado`),
  CONSTRAINT `movimiento_ibfk_7` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`),
  CONSTRAINT `movimiento_ibfk_8` FOREIGN KEY (`insert_id_user`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `movimiento_ibfk_9` FOREIGN KEY (`id_contraparte`) REFERENCES `sociedades` (`id_sociedad`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
/*!40000 ALTER TABLE `movimiento` DISABLE KEYS */;
INSERT INTO `movimiento` VALUES (1,1,1,2,1,1,8,1,'CTG001','2024-10-23',NULL,NULL),(2,1,2,1,2,2,2,2,'CTG002','2024-10-24',NULL,NULL),(3,1,1,1,NULL,NULL,NULL,1,'1','2024-10-26',NULL,1),(4,2,3,1,2,NULL,1,2,'4','2024-10-05',NULL,1),(5,2,3,1,2,5,7,2,'5555','2024-10-26',NULL,1),(6,2,3,2,1,6,5,1,'43523444','2024-10-27',NULL,1),(7,1,1,3,2,NULL,1,1,'42342342','2024-10-27',NULL,2),(8,1,2,1,2,NULL,1,2,'42323232','2024-10-27',NULL,2),(9,1,2,1,1,NULL,5,2,'213123123','2024-10-27',NULL,2),(10,2,1,3,2,NULL,1,2,'1451234123','2024-10-27',NULL,1),(11,2,1,3,2,9,7,2,'1231241','2024-10-27',NULL,1),(12,1,1,3,1,10,1,2,'23451234','2024-10-27',NULL,2),(13,2,1,3,2,11,7,2,'3124123','2024-12-09',NULL,1),(14,1,2,3,2,12,1,2,'55555','2024-10-26',NULL,2),(15,2,1,3,2,13,1,2,'124123123','2024-10-09',NULL,1),(16,2,4,1,2,14,1,2,'1231231','2024-11-09',NULL,1),(17,3,4,1,1,15,5,2,'4324142','2024-10-09',NULL,1),(18,3,3,1,2,16,7,1,'22222','2024-10-28',NULL,1);
/*!40000 ALTER TABLE `movimiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trigger_verificar_estado_aprobacion
BEFORE UPDATE ON movimiento
FOR EACH ROW
BEGIN
    -- Verifica si el nuevo estado es "Aprobado"
    IF NEW.id_estado = 3 THEN
        -- Verifica que el estado actual sea "Pendiente Aprobación"
        IF OLD.id_estado != 4 THEN
            SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'El camión no se encuentra en estado Pendiente Aprobación y no puede ser aprobado.';
        END IF;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-31  0:22:43
