SELECT t.codigo, t.tipo, t.direccion
FROM tienda t
RIGHT JOIN bodega_tienda b
ON  t.codigo = b.codigo_tienda WHERE b.codigo_usuario_bodega = 'aa';

/*para mostrar los pedidos a cargo de un usuarioBodega*/
SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda 
FROM pedido p
RIGHT JOIN bodega_tienda b
ON  p.codigo_tienda = b.codigo_tienda;

SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda 
FROM pedido p
RIGHT JOIN bodega_tienda b
ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = 'A' AND p.estado = 0;

/*para mostrar las incidencias a cargo de un usuarioBodega*/
SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda 
FROM incidencia p
RIGHT JOIN bodega_tienda b
ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = 'A' AND p.estado = 0;

SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda 
FROM incidencia p
RIGHT JOIN bodega_tienda b
ON  p.codigo_tienda = b.codigo_tienda 
WHERE b.codigo_usuario_bodega = 'A' AND p.estado = 0 AND p.codigo_tienda = ?;


SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda 
FROM incidencia p
RIGHT JOIN bodega_tienda b
ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = 'A';

/*mostrar devoluciones por tienda y por usuarioBodega*/
SELECT p.id, p.fecha, p.estado, p.total, p.usuario_tienda, p.codigo_tienda 
FROM devolucion p
RIGHT JOIN bodega_tienda b
ON  p.codigo_tienda = b.codigo_tienda 
WHERE b.codigo_usuario_bodega = 'A' AND p.estado = 0 AND p.codigo_tienda = 11;

SELECT p.id, p.fecha, p.estado, p.total, p.usuario_tienda, p.codigo_tienda 
FROM devolucion p
RIGHT JOIN bodega_tienda b
ON  p.codigo_tienda = b.codigo_tienda 
WHERE b.codigo_usuario_bodega = 'A' AND p.estado = 0;

/*mostrar los envios a una tienda*/

SELECT p.codigo, p.tipo 
FROM tienda p
RIGHT JOIN usuario_tienda u
ON  p.codigo = u.codigo_tienda 
WHERE u.codigo != '';

/*reportes tienda*/
/* reporte 1*/
SELECT p.codigo, p.nombre, p.costo, p.precio, c.existencia FROM  producto p
RIGHT JOIN catalogo c
ON p.codigo = c.codigo_producto
WHERE  c.codigo_tienda = '' AND c.existencia < 1;
/*reporte 2, reporte de pedidos*/


SELECT * FROM pedido WHERE usuario_tienda = 'COD2luis' AND estado = 'PENDIENTE' AND fecha BETWEEN '2023-03-06' AND '2023-03-06';
SELECT * FROM incidencia WHERE estado = '' AND codigo_tienda = '' AND fecha BETWEEN '' AND '';
SELECT * FROM devolucion WHERE estado = '' AND codigo_tienda = '' AND fecha BETWEEN '' AND '';
/* reportes del administrador*/

/* REPORTE DE SUPERVISOR*/
/*reporte de pedidos*/

SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor
FROM pedido p
LEFT JOIN tienda t
ON p.codigo_tienda = t.codigo WHERE t.tipo = 'SUPERVISADA' AND t.codigo = 'COD5';




