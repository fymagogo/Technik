-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2018 at 07:18 PM
-- Server version: 5.7.21-log
-- PHP Version: 7.0.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eventhub`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `EventName` text NOT NULL,
  `CategoryId` int(11) NOT NULL,
  `MainImage` varchar(255) NOT NULL,
  `EventDate` datetime NOT NULL,
  `TimeCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LocationId` int(11) DEFAULT NULL,
  `EventId` int(11) NOT NULL,
  `Interested` int(11) UNSIGNED NOT NULL DEFAULT '0',
  `Attending` int(10) UNSIGNED NOT NULL DEFAULT '0',
  `Description` text,
  `Username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`EventName`, `CategoryId`, `MainImage`, `EventDate`, `TimeCreated`, `LocationId`, `EventId`, `Interested`, `Attending`, `Description`, `Username`) VALUES
('Eventevent', 1, 'https://firebasestorage.googleapis.com/v0/b/eventhub-2a170.appspot.com/o/eventflyer1.jpg?alt=media&token=3dea86f2-7a60-4bcb-a99b-b7922031583c', '2018-03-03 20:30:00', '2018-03-02 21:04:58', 1, 1, 2, 1, 'This Event is the biggest event to hit the streets of Kumasi. The EventEvent!! At the Ratray Park. So all you sexy ladies and handsome dudes should come in your numbers.  Oh yeah and Shatta Wale and Mr.Eazi dey perform. hehe', ''),
('Mr.Biggs reopening buffet.', 2, 'http://www.pinterest.com/boards/aesthetic22/Id=123451K', '2018-03-20 04:12:11', '2018-03-19 19:26:18', 2, 2, 0, 0, 'Mr Biggss is opening again oo.', ''),
('KenkeyFest', 4, 'www.google.com/image/catanga', '2018-03-28 04:15:00', '2018-03-22 16:34:53', 3, 3, 1, 1, 'Kenkey Buffet', '');

-- --------------------------------------------------------

--
-- Table structure for table `eventcategory`
--

CREATE TABLE `eventcategory` (
  `CategoryId` int(11) NOT NULL,
  `CategoryName` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `eventcategory`
--

INSERT INTO `eventcategory` (`CategoryId`, `CategoryName`) VALUES
(1, 'SPORTS'),
(2, 'Sports'),
(3, 'Religious'),
(4, 'Education'),
(5, 'Lifestyle');

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `LocationId` int(11) NOT NULL,
  `Longitude` double NOT NULL,
  `Latitude` double NOT NULL,
  `LocationName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`LocationId`, `Longitude`, `Latitude`, `LocationName`) VALUES
(1, 1.2445536456, -2.34565332, 'Ab3 St'),
(2, 12.4445, 1.276583923, 'Delta star avenue '),
(3, 1.000002, 1.112211, 'Katanga Dining Hall'),
(4, 2.333, 4.24442, 'Ahodwo'),
(5, 1.2424242, 13145, 'CirscuDuSoleill'),
(6, 1.2424242, 0, 'Pizzahut');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Firstname` varchar(30) NOT NULL,
  `Lastname` varchar(30) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` char(60) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `PhoneNumber` varchar(30) NOT NULL,
  `TimeAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Firstname`, `Lastname`, `username`, `password`, `Email`, `PhoneNumber`, `TimeAdded`) VALUES
('Kwadwo', 'Owusu', 'KAOwusu1', '$2a$10$ZqFd1QWPiWIHLo6./owe0uDPfNkcl4yTVkCKVpcBSWOSlKWrc8xHO', 'owusukojo97@gmail.com', '0551515302', '2018-03-21 22:55:42'),
('SELORM', 'ABOFRA', 'Serlomi', '$2a$10$gW77Hucttdix1UfbS8fWLeuNDJ1BlcldhAhp4y5pWhJ/FdvMU0J0C', 'asdkmroles@gmail.com', '0551515302', '2018-03-21 22:55:42');

-- --------------------------------------------------------

--
-- Table structure for table `usersatteding`
--

CREATE TABLE `usersatteding` (
  `users_username` varchar(20) NOT NULL,
  `event_EventId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usersatteding`
--

INSERT INTO `usersatteding` (`users_username`, `event_EventId`) VALUES
('Serlomi', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usersinterested`
--

CREATE TABLE `usersinterested` (
  `users_username` varchar(20) NOT NULL,
  `event_EventId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `usersubscriptions`
--

CREATE TABLE `usersubscriptions` (
  `Username` varchar(30) NOT NULL,
  `CategoryId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usersubscriptions`
--

INSERT INTO `usersubscriptions` (`Username`, `CategoryId`) VALUES
('Serlomi', 2),
('Serlomi', 4),
('KAOwusu1', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`EventId`),
  ADD KEY `LocationId` (`LocationId`);
ALTER TABLE `event` ADD FULLTEXT KEY `EventName` (`EventName`);
ALTER TABLE `event` ADD FULLTEXT KEY `EventName_2` (`EventName`,`Description`);
ALTER TABLE `event` ADD FULLTEXT KEY `EventName_3` (`EventName`,`Description`);
ALTER TABLE `event` ADD FULLTEXT KEY `EventName_4` (`EventName`);

--
-- Indexes for table `eventcategory`
--
ALTER TABLE `eventcategory`
  ADD PRIMARY KEY (`CategoryId`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`LocationId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`);

--
-- Indexes for table `usersatteding`
--
ALTER TABLE `usersatteding`
  ADD KEY `fk_UsersAtteding_users1_idx` (`users_username`),
  ADD KEY `fk_UsersAtteding_event1_idx` (`event_EventId`);

--
-- Indexes for table `usersinterested`
--
ALTER TABLE `usersinterested`
  ADD KEY `fk_UsersInterested_users1_idx` (`users_username`),
  ADD KEY `fk_UsersInterested_event1_idx` (`event_EventId`);

--
-- Indexes for table `usersubscriptions`
--
ALTER TABLE `usersubscriptions`
  ADD KEY `Username` (`Username`),
  ADD KEY `CategoryId` (`CategoryId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `EventId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `eventcategory`
--
ALTER TABLE `eventcategory`
  MODIFY `CategoryId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `LocationId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `event_ibfk_1` FOREIGN KEY (`LocationId`) REFERENCES `location` (`LocationId`);

--
-- Constraints for table `usersatteding`
--
ALTER TABLE `usersatteding`
  ADD CONSTRAINT `fk_UsersAtteding_event1` FOREIGN KEY (`event_EventId`) REFERENCES `event` (`EventId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UsersAtteding_users1` FOREIGN KEY (`users_username`) REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `usersinterested`
--
ALTER TABLE `usersinterested`
  ADD CONSTRAINT `fk_UsersInterested_event1` FOREIGN KEY (`event_EventId`) REFERENCES `event` (`EventId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UsersInterested_users1` FOREIGN KEY (`users_username`) REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `usersubscriptions`
--
ALTER TABLE `usersubscriptions`
  ADD CONSTRAINT `usersubscriptions_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `users` (`username`),
  ADD CONSTRAINT `usersubscriptions_ibfk_2` FOREIGN KEY (`CategoryId`) REFERENCES `eventcategory` (`CategoryId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
