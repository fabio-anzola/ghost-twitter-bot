-- MySQL dump 10.13  Distrib 5.7.32, for Linux (x86_64)
--
-- Host: localhost    Database: twitterbot
-- ------------------------------------------------------
-- Server version	5.7.32-0ubuntu0.18.04.1

--
-- Table structure for table `blog_id`
--

DROP TABLE IF EXISTS `blog_id`;
CREATE TABLE `blog_id` (
  `id` varchar(100) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Table structure for table `twitter_oauth`
--

DROP TABLE IF EXISTS `twitter_oauth`;
CREATE TABLE `twitter_oauth` (
  `pk_oauth_id` int(11) NOT NULL AUTO_INCREMENT,
  `oauth1` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `oauth2` varchar(255) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`pk_oauth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
