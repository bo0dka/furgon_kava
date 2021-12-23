-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Дек 11 2021 г., 17:33
-- Версия сервера: 10.4.11-MariaDB
-- Версия PHP: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `kava`
--

-- --------------------------------------------------------

--
-- Структура таблицы `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_type` varchar(20) NOT NULL,
  `product_price` double NOT NULL,
  `product_size` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Структура таблицы `trucks`
--

CREATE TABLE `trucks` (
  `truck_id` int(11) NOT NULL,
  `truck_name` varchar(100) NOT NULL,
  `truck_max_size` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Структура таблицы `truck_items`
--

CREATE TABLE `truck_items` (
  `ti_id` int(11) NOT NULL,
  `ti_truck_id` int(11) NOT NULL,
  `ti_product_id` int(11) NOT NULL,
  `ti_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- Индексы таблицы `trucks`
--
ALTER TABLE `trucks`
  ADD PRIMARY KEY (`truck_id`);

--
-- Индексы таблицы `truck_items`
--
ALTER TABLE `truck_items`
  ADD PRIMARY KEY (`ti_id`),
  ADD KEY `fk__ti_product__product_id` (`ti_product_id`),
  ADD KEY `fk__ti_truck__truck_id` (`ti_truck_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `trucks`
--
ALTER TABLE `trucks`
  MODIFY `truck_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `truck_items`
--
ALTER TABLE `truck_items`
  MODIFY `ti_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `truck_items`
--
ALTER TABLE `truck_items`
  ADD CONSTRAINT `fk__ti_product__product_id` FOREIGN KEY (`ti_product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk__ti_truck__truck_id` FOREIGN KEY (`ti_truck_id`) REFERENCES `trucks` (`truck_id`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
