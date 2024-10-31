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
-- Temporary view structure for view `vista_movimientos_pendientes_autorizacion`
--

DROP TABLE IF EXISTS `vista_movimientos_pendientes_autorizacion`;
/*!50001 DROP VIEW IF EXISTS `vista_movimientos_pendientes_autorizacion`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_movimientos_pendientes_autorizacion` AS SELECT 
 1 AS `patente`,
 1 AS `nombre_sociedad`,
 1 AS `nombre_producto`,
 1 AS `numero_movimiento`,
 1 AS `nombre_chofer`,
 1 AS `fecha`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_movimientos_por_sociedad`
--

DROP TABLE IF EXISTS `vista_movimientos_por_sociedad`;
/*!50001 DROP VIEW IF EXISTS `vista_movimientos_por_sociedad`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_movimientos_por_sociedad` AS SELECT 
 1 AS `nombre_sociedad`,
 1 AS `nombre_producto`,
 1 AS `peso_neto`,
 1 AS `numero_movimiento`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_stock_total_por_sociedad_producto`
--

DROP TABLE IF EXISTS `vista_stock_total_por_sociedad_producto`;
/*!50001 DROP VIEW IF EXISTS `vista_stock_total_por_sociedad_producto`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_stock_total_por_sociedad_producto` AS SELECT 
 1 AS `nombre_sociedad`,
 1 AS `nombre_producto`,
 1 AS `stock_total`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_movimientos_por_fecha_sociedad`
--

DROP TABLE IF EXISTS `vista_movimientos_por_fecha_sociedad`;
/*!50001 DROP VIEW IF EXISTS `vista_movimientos_por_fecha_sociedad`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_movimientos_por_fecha_sociedad` AS SELECT 
 1 AS `nombre_sociedad`,
 1 AS `nombre_producto`,
 1 AS `peso_neto`,
 1 AS `fecha`,
 1 AS `numero_movimiento`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_total_movimientos_por_producto_sociedad`
--

DROP TABLE IF EXISTS `vista_total_movimientos_por_producto_sociedad`;
/*!50001 DROP VIEW IF EXISTS `vista_total_movimientos_por_producto_sociedad`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_total_movimientos_por_producto_sociedad` AS SELECT 
 1 AS `nombre_sociedad`,
 1 AS `nombre_producto`,
 1 AS `total_movimientos`,
 1 AS `peso_neto_total`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vista_movimientos_pendientes_autorizacion`
--

/*!50001 DROP VIEW IF EXISTS `vista_movimientos_pendientes_autorizacion`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_movimientos_pendientes_autorizacion` AS select `t`.`patente` AS `patente`,`s`.`nombre` AS `nombre_sociedad`,`p`.`descripcion` AS `nombre_producto`,`m`.`id_movimiento` AS `numero_movimiento`,`ch`.`nombre` AS `nombre_chofer`,`m`.`fecha` AS `fecha` from (((((`movimiento` `m` join `producto` `p` on(`m`.`id_producto` = `p`.`id_producto`)) join `sociedades` `s` on(`m`.`id_sociedad` = `s`.`id_sociedad`)) join `chofer_transporte` `ct` on(`m`.`id_chofer_transporte` = `ct`.`id_chofer_transporte`)) join `chofer` `ch` on(`ct`.`id_chofer` = `ch`.`id_chofer`)) join `transporte` `t` on(`t`.`id_transporte` = `ct`.`id_transporte`)) where `m`.`id_estado` = 6 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_movimientos_por_sociedad`
--

/*!50001 DROP VIEW IF EXISTS `vista_movimientos_por_sociedad`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_movimientos_por_sociedad` AS select `s`.`nombre` AS `nombre_sociedad`,`p`.`descripcion` AS `nombre_producto`,`pe`.`peso_bruto` - `pe`.`peso_tara` AS `peso_neto`,`m`.`id_movimiento` AS `numero_movimiento` from (((`pesaje` `pe` join `movimiento` `m` on(`m`.`id_movimiento` = `pe`.`id_movimiento`)) join `producto` `p` on(`m`.`id_producto` = `p`.`id_producto`)) join `sociedades` `s` on(`m`.`id_sociedad` = `s`.`id_sociedad`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_stock_total_por_sociedad_producto`
--

/*!50001 DROP VIEW IF EXISTS `vista_stock_total_por_sociedad_producto`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_stock_total_por_sociedad_producto` AS select `s`.`nombre` AS `nombre_sociedad`,`p`.`descripcion` AS `nombre_producto`,sum(case when `m`.`id_tipo_movimiento` = 1 then `pe`.`peso_neto` when `m`.`id_tipo_movimiento` = 2 then -`pe`.`peso_neto` else 0 end) AS `stock_total` from (((`movimiento` `m` join `pesaje` `pe` on(`m`.`id_movimiento` = `pe`.`id_movimiento`)) join `sociedades` `s` on(`m`.`id_sociedad` = `s`.`id_sociedad`)) join `producto` `p` on(`m`.`id_producto` = `p`.`id_producto`)) group by `s`.`nombre`,`p`.`descripcion` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_movimientos_por_fecha_sociedad`
--

/*!50001 DROP VIEW IF EXISTS `vista_movimientos_por_fecha_sociedad`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_movimientos_por_fecha_sociedad` AS select `s`.`nombre` AS `nombre_sociedad`,`p`.`descripcion` AS `nombre_producto`,`pe`.`peso_bruto` - `pe`.`peso_tara` AS `peso_neto`,`m`.`fecha` AS `fecha`,`m`.`id_movimiento` AS `numero_movimiento` from (((`pesaje` `pe` join `movimiento` `m` on(`m`.`id_movimiento` = `pe`.`id_movimiento`)) join `producto` `p` on(`m`.`id_producto` = `p`.`id_producto`)) join `sociedades` `s` on(`m`.`id_sociedad` = `s`.`id_sociedad`)) order by `m`.`fecha` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_total_movimientos_por_producto_sociedad`
--

/*!50001 DROP VIEW IF EXISTS `vista_total_movimientos_por_producto_sociedad`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_total_movimientos_por_producto_sociedad` AS select `s`.`nombre` AS `nombre_sociedad`,`p`.`descripcion` AS `nombre_producto`,count(`m`.`id_movimiento`) AS `total_movimientos`,sum(`pe`.`peso_bruto` - `pe`.`peso_tara`) AS `peso_neto_total` from (((`pesaje` `pe` join `movimiento` `m` on(`m`.`id_movimiento` = `pe`.`id_movimiento`)) join `producto` `p` on(`m`.`id_producto` = `p`.`id_producto`)) join `sociedades` `s` on(`m`.`id_sociedad` = `s`.`id_sociedad`)) group by `s`.`nombre`,`p`.`descripcion` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Dumping events for database 'sebagro'
--

--
-- Dumping routines for database 'sebagro'
--
/*!50003 DROP FUNCTION IF EXISTS `calcular_peso_neto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calcular_peso_neto`(peso_bruto DECIMAL(10,2), peso_tara DECIMAL(10,2)) RETURNS decimal(10,2)
    DETERMINISTIC
BEGIN
    RETURN peso_bruto - peso_tara;
END ;;
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
