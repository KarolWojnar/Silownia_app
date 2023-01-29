-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 29 Sty 2023, 18:03
-- Wersja serwera: 10.4.27-MariaDB
-- Wersja PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `silka`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `silownia_dane`
--

CREATE TABLE `silownia_dane` (
  `ID` int(5) NOT NULL,
  `imie` text NOT NULL,
  `nazwisko` text NOT NULL,
  `nr_telefonu` text NOT NULL,
  `email` text NOT NULL,
  `haslo` text NOT NULL,
  `data_karnet` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `silownia_dane`
--

INSERT INTO `silownia_dane` (`ID`, `imie`, `nazwisko`, `nr_telefonu`, `email`, `haslo`, `data_karnet`) VALUES
(1, 'Janek', 'Nowakowski', '5554433341', 'jnowak@wp.pl', 'Janek123', '2023-03-29'),
(2, 'Kamil', 'Wojtowicz', '886994446', 'wojtek@wp.pl', 'essa', '2023-01-31'),
(3, 'Kamil', 'Wojtowicz', '886994446', 'wojtek@wp.pl', 'Kamil123', '2023-02-11'),
(4, 'Kamil', 'Małysz', '886994441', 'kmalysz@wp.pl', 'kmalysz', '2023-02-17'),
(5, 'Kuba', 'Nowakowski', '886994412', 'knowaczkiewicz@wp.pl', 'knowaczkiewicz', '2023-03-03'),
(6, 'Ania', 'Król', '886194446', 'akrol@wp.pl', 'akrol', '2023-03-09'),
(7, 'Wiktoria', 'Wojtowicz', '816994446', 'wwojtowicz@wp.pl', 'wwojtowicz', '2023-02-09'),
(8, 'Adam', 'Dąbrowski', '886994126', 'adabrowski@wp.pl', 'adabrowski', '2023-01-04'),
(9, 'Mateusz', 'Wozniak', '822994446', 'mwozniak@wp.pl', 'mwozniak', '2023-02-24'),
(10, 'Kamila', 'Kowalski', '844494446', 'kkowalski@wp.pl', 'kkowalski', '2023-02-23'),
(11, 'Michał', 'Nowakowski', '811994446', 'mnowakowski@wp.pl', 'mnowakowski', '2023-04-13'),
(12, 'Wojtek', 'Kowal', '886991234', 'wkowal@wp.pl', 'wkowal', '2023-02-15'),
(13, 'Anna', 'Nowak', '881234446', 'anowak@wp.pl', 'anowak', '2023-01-24'),
(14, 'Julia', 'Wojcik', '555594446', 'jwojcik@wp.pl', 'jwojcik', '2023-02-28');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `silownia_dane`
--
ALTER TABLE `silownia_dane`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `silownia_dane`
--
ALTER TABLE `silownia_dane`
  MODIFY `ID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
