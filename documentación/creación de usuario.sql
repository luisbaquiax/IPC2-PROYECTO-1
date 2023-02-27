 /*usuario para la conexión a la base de datos store*/
CREATE USER 'store'@'localhost' IDENTIFIED BY 'adminTienda1234@';
GRANT ALL PRIVILEGES ON store.* TO 'store'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
