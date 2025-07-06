Use banco;


--// Esta vista_clientes deben cambiar 
drop view if exists vista_clientes;

CREATE VIEW vista_clientes AS
SELECT 
    c.id_cliente, c.dni, c.cuil, c.nombre, c.apellido, c.sexo, 
    c.nacionalidad, c.fechanacimiento,  c.direccion, 
    l.id_localidad,
    l.descripcion AS localidad, 
    u.id_usuario, u.nombreusuario, u.contrasenia, 
    u.id_tipouser, tu.descripcion AS descUsuario, 
    u.estado AS estadoUsuario, 
    p.id_provincia AS idProvincia, 
    p.descripcion AS provincia, 
    c.correo, c.telefono, 
    c.fecha_alta AS altaCliente, 
    c.estado AS estadoCliente
FROM clientes AS c
INNER JOIN localidades AS l ON c.id_localidad = l.id_localidad
INNER JOIN provincias AS p ON l.id_provincia = p.id_provincia
INNER JOIN usuarios AS u ON u.id_cliente = c.id_cliente
INNER JOIN tipo_de_usuarios AS tu ON u.id_tipouser = tu.id_tipouser;   
    
    
drop view if exists vista_cuentas;

CREATE VIEW vista_cuentas AS
SELECT 
	cue.num_de_cuenta, cue.cbu, cue.fecha_creacion as altaCuenta, cue.fecha_baja as bajaCuenta, tc.id_tipocuenta, tc.descripcion as descTipoCuenta,
    c.id_cliente, c.dni, c.cuil, c.nombre, c.apellido, c.sexo, c.nacionalidad, c.fechanacimiento, c.direccion, l.id_localidad ,l.descripcion AS localidad,
    u.id_usuario, u.nombreusuario,u.contrasenia ,u.id_tipouser, tu.descripcion as descUsuario, u.estado as estadoUsuario, p.id_provincia AS idProvincia, p.descripcion AS provincia, c.correo, 
    c.telefono, c.fecha_alta as altaCliente, c.estado as estadoCliente, cue.saldo, cue.estado as estadoCuenta
FROM
    clientes AS c
INNER JOIN
    localidades AS l ON c.id_localidad = l.id_localidad
INNER JOIN
    provincias AS p ON l.id_provincia = p.id_provincia
INNER JOIN
    cuentas AS cue ON c.id_cliente = cue.id_cliente
INNER JOIN 
	tipo_de_cuentas AS tc ON cue.id_tipocuenta = tc.id_tipocuenta
INNER JOIN
    usuarios AS u ON u.id_cliente = c.id_cliente
INNER JOIN
    tipo_de_usuarios AS tu ON u.id_tipouser = tu.id_tipouser; 
    
drop view if exists vista_usuarios;

CREATE VIEW vista_usuarios AS
SELECT
    u.id_usuario AS 'id',u.id_cliente as 'idCliente', u.nombreusuario as 'nombreUsuario', u.contrasenia, 
    tu.id_tipouser as 'IdTipoUser', tu.descripcion AS 'Descripcion', u.estado    
FROM
    usuarios AS u
JOIN
    tipo_de_usuarios AS tu
ON
    u.id_tipouser = tu.id_tipouser;

--//Estas vista_cliente_id y vista_cantidad_cuentas_activas deben agregar

drop view if exists vista_clientes_id;
    
CREATE VIEW vista_clientes_id AS
SELECT id_cliente, dni
FROM clientes;

drop view if exists vista_cantidad_cuentas_activas;

CREATE VIEW vista_cantidad_cuentas_activas AS
SELECT 
    cl.dni,
    COUNT(c.id_cliente) AS cantidad
FROM cuentas AS c
INNER JOIN clientes AS cl ON c.id_cliente = cl.id_cliente
WHERE c.estado = 1
GROUP BY cl.dni;


--Prestamos vista modificada
DROP VIEW IF EXISTS vista_prestamos;

CREATE VIEW vista_prestamos AS
SELECT 
    p.id_prestamo AS id,
    p.num_de_cuenta AS NumCuenta,
    p.fecha,
    p.importe_pedido AS importePedido,
    p.cuotas,
    p.importe_mensual AS importeMensual,
    p.estado,
    p.aprobado,
    p.finalizado,
    u.nombreusuario
FROM prestamos p
INNER JOIN cuentas c ON p.num_de_cuenta = c.num_de_cuenta
INNER JOIN clientes cli ON c.id_cliente = cli.id_cliente
INNER JOIN usuarios u ON cli.id_cliente = u.id_cliente;

