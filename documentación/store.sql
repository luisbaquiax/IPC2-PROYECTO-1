DROP SCHEMA IF EXISTS store;

CREATE SCHEMA store;

USE store;

CREATE TABLE IF NOT EXISTS producto(
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(45) NOT NULL,
    costo DOUBLE NOT NULL,
    precio DOUBLE NOT NULL,
    existencia int NOT NULL,
    PRIMARY KEY(codigo)
);

CREATE TABLE IF NOT EXISTS tienda(
    codigo VARCHAR(20) NOT NULL,
    direccion VARCHAR(45) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    PRIMARY KEY(codigo)
);

CREATE TABLE IF NOT EXISTS catalogo(
    codigo_tienda VARCHAR(20) NOT NULL,
    codigo_producto VARCHAR(20) NOT NULL, 
    existencia INT NOT NULL,
    PRIMARY KEY(codigo_tienda, codigo_producto),
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_producto) REFERENCES producto(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS usuario(
    codigo VARCHAR(20) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    nombre_usuario VARCHAR(45) NOT NULL,
    nombre VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS administrador(
    codigo VARCHAR(20) NOT NULL,
    PRIMARY KEY(codigo),
    FOREIGN KEY(codigo) REFERENCES usuario(codigo)
);

CREATE TABLE IF NOT EXISTS usuario_tienda(
    codigo VARCHAR(20) NOT NULL,
    codigo_tienda VARCHAR(20) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY(codigo),
    FOREIGN KEY(codigo) REFERENCES usuario(codigo),
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE  
);

CREATE TABLE IF NOT EXISTS usuario_supervisor(
    codigo VARCHAR(20) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY(codigo),
    FOREIGN KEY(codigo) REFERENCES usuario(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS usuario_bodega(
    codigo VARCHAR(20) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY(codigo),
    FOREIGN KEY(codigo) REFERENCES usuario(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS bodega_tienda(
    codigo_usuario_bodega VARCHAR(20) NOT NULL,
    codigo_tienda VARCHAR(20) NOT NULL,
    PRIMARY KEY(codigo_usuario_bodega, codigo_tienda),
    FOREIGN KEY(codigo_usuario_bodega) REFERENCES usuario_bodega(codigo) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS pedido(
    id INT AUTO_INCREMENT NOT NULL,
    fecha DATE NOT NULL,
    total DOUBLE NOT NULL,
    estado VARCHAR(45) NOT NULL,
    usuario_tienda VARCHAR(20) NOT NULL,
    codigo_tienda VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(usuario_tienda) REFERENCES usuario_tienda(codigo) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_pedido(
    id_pedido INT NOT NULL,
    codigo_producto VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    PRIMARY KEY(id_pedido, codigo_producto),
    FOREIGN KEY(id_pedido) REFERENCES pedido(id) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_producto) REFERENCES producto(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS incidencia(
    id INT AUTO_INCREMENT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(45) NOT NULL,
    usuario_tienda VARCHAR(20) NOT NULL,
    codigo_tienda VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(usuario_tienda) REFERENCES usuario_tienda(codigo) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_incidencia(
    id_incidencia INT NOT NULL,
    codigo_producto VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(45) NOT NULL,
    PRIMARY KEY(id_incidencia, codigo_producto),
    FOREIGN KEY(id_incidencia) REFERENCES incidencia(id) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_producto) REFERENCES producto(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS devolucion(
    id INT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(45) NOT NULL,
    total DOUBLE NOT NULL,
    usuario_tienda VARCHAR(20) NOT NULL,
    codigo_tienda VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(usuario_tienda) REFERENCES usuario_tienda(codigo) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_devolucion(
    id_devolucion INT NOT NULL,
    codigo_producto VARCHAR(20) NOT NULL,
    costo_unitario DOUBLE NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(45) NOT NULL,
    PRIMARY KEY(id_devolucion, codigo_producto),
    FOREIGN KEY(id_devolucion) REFERENCES devolucion(id) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_producto) REFERENCES producto(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS envio(
    id INT AUTO_INCREMENT NOT NULL,
    fecha_salida DATE NOT NULL,
    fecha_llegada DATE NOT NULL,
    total DOUBLE NOT NULL,
    estado VARCHAR(45) NOT NULL,
    codigo_tienda VARCHAR(20) NOT NULL,
    usuario_bodega VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE,
    FOREIGN KEY(usuario_bodega) REFERENCES usuario_bodega(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_envio(
    id_envio INT NOT NULL,
    codigo_producto VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    PRIMARY KEY(id_envio, codigo_producto),
    FOREIGN KEY(id_envio) REFERENCES envio(id) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_producto) REFERENCES producto(codigo) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS supervision(
    id INT AUTO_INCREMENT NOT NULL,
    codigo_supervisor VARCHAR(20) NOT NULL,
    codigo_tienda VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(codigo_supervisor) REFERENCES usuario_supervisor(codigo) ON UPDATE CASCADE,
    FOREIGN KEY(codigo_tienda) REFERENCES tienda(codigo) ON UPDATE CASCADE
);
