SELECT t.codigo, t.tipo, t.direccion
FROM tienda t
RIGHT JOIN bodega_tienda b
ON  t.codigo = b.codigo_tienda WHERE b.codigo_usuario_bodega = 'aa';
/* USUARIO BODEGA**/
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
/* REPORTE DE BODEGA */
/*Reporte de devoluciones de una tienda en específico en un intervalo de tiempo por estado.  */

SELECT d.id, d.fecha, d.estado, d.total, d.usuario_tienda, d.codigo_tienda
FROM devolucion d
LEFT JOIN bodega_tienda b 
ON d.codigo_tienda = b.codigo_tienda WHERE codigo_usuario_bodega = 'COD3' AND d.estado = 'ACTIVA';

SELECT d.id, d.fecha, d.estado, d.total, d.usuario_tienda, d.codigo_tienda
FROM devolucion d
LEFT JOIN bodega_tienda b 
/*mostrar los envios a una tienda*/

SELECT p.codigo, p.tipo 
FROM tienda p
RIGHT JOIN usuario_tienda u
ON  p.codigo = u.codigo_tienda 
WHERE u.codigo != '';

/* REPOORTES TIENDA*/
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

SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor
FROM pedido p
LEFT JOIN tienda t
ON p.codigo_tienda = t.codigo WHERE t.tipo = 'SUPERVISADA' AND p.estado = 'PENDIENTE';

/* ADMINISTRACION */
REPORTE DE TIENDAS CON MÁS PEDIDOS POR FECHA Y ESTADO

SELECT COUNT(id) AS numero_pedidos, codigo_tienda
FROM pedido p
WHERE p.estado ='SOLICITADO' AND fecha BETWEEN '2023-03-06' AND '2023-03-06'
GROUP BY codigo_tienda
HAVING COUNT(id) LIMIT 5;

SELECT COUNT(id) AS numero_pedidos, codigo_tienda
FROM pedido p
WHERE p.estado ='SOLICITADO' AND fecha BETWEEN '2023-03-06' AND '2023-03-06'
GROUP BY codigo_tienda
HAVING COUNT(id) LIMIT 5;

REPORTE DE USUARIOS CON MAS ENVIOS

SELECT COUNT(id) AS numero_envios , usuario_bodega
FROM envio
GROUP BY usuario_bodega
ORDER BY COUNT(id) DESC Limit 5;

SELECT COUNT(id) AS numero_envios , usuario_bodega
FROM envio e
WHERE e.fecha_salida BETWEEN '2023-03-10' AND '2023-03-10'
GROUP BY usuario_bodega
ORDER BY COUNT(id) DESC Limit 5;

REPORTE DE USUARIOS_TIENDA CON MAS PEDIDOS GENERADOS

SELECT COUNT(id) AS numero_pedidos , usuario_tienda
FROM pedido
GROUP BY usuario_tienda
ORDER BY COUNT(id) DESC Limit 5;








