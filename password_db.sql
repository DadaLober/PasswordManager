-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2023 at 07:02 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `password_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `password_db`
--

CREATE TABLE `password_db` (
  `pid` int(5) NOT NULL,
  `webname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `notes` varchar(1000) NOT NULL,
  `website` varchar(255) NOT NULL,
  `iconpath` varchar(255) DEFAULT 'DEFAULT',
  `favorite` int(1) DEFAULT 0,
  `trashitem` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `password_db`
--

INSERT INTO `password_db` (`pid`, `webname`, `email`, `password`, `notes`, `website`, `iconpath`, `favorite`, `trashitem`) VALUES
(1, 'Facebook', 'oliveros@gmail.com', 'facebookPassword', 'Better connecting you with family.', 'www.facebook.com', 'icons/icons8-facebook-48.png', 0, 0),
(2, 'Adobe', 'JohnDoe@gmail.com', 'adobePassword', 'Creativity for all.', 'www.adobe.com', 'icons/icons8-adobe-64.png', 0, 0),
(3, 'Instagram', 'patacsil@gmail.com', 'inspasss', 'Life is the biggest party you\'ll ever be at.', 'www.instagram.com', 'icons/icons8-instagram-48.png', 0, 0),
(4, 'Telegram', 'kalabaw@gmail.com', 'kalabaw', 'The People\'s Paper.', 'www.telegram.com', 'icons/icons8-telegram-app-48.png', 0, 0),
(5, 'Twitter', 'elonmusk@gmail.com', 'lonpumps', 'We believe real change starts with conversation.', 'www.twitter.com', 'icons/icons8-twitter-48.png', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `password_db`
--
ALTER TABLE `password_db`
  ADD PRIMARY KEY (`pid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `password_db`
--
ALTER TABLE `password_db`
  MODIFY `pid` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
