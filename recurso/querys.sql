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
