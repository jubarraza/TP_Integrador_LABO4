Use banco;

drop view if exists vista_clientes;

CREATE VIEW vista_clientes AS
SELECT 
    c.id_cliente, c.dni, c.cuil, c.nombre, c.apellido, c.sexo, c.nacionalidad, c.fechanacimiento,  c.direccion, l.id_localidad ,l.descripcion AS localidad, 
	u.id_usuario, u.nombreusuario,u.contrasenia, u.id_tipouser, tu.descripcion as descUsuario, u.estado as estadoUsuario, p.id_provincia, p.descripcion AS provincia, c.correo, 
    c.telefono, c.fecha_alta as altaCliente, c.estado as estadoCliente
FROM
    clientes AS c
INNER JOIN
    localidades AS l ON c.id_localidad = l.id_localidad
INNER JOIN
    provincias AS p ON l.id_provincia = p.id_provincia
 INNER JOIN
    usuarios AS u ON u.id_cliente = c.id_cliente
  INNER JOIN
    tipo_de_usuarios AS tu ON u.id_tipouser = tu.id_tipouser;     
    
    
drop view if exists vista_cuentas;

CREATE VIEW vista_cuentas AS
SELECT 
	cue.num_de_cuenta, cue.cbu, cue.fecha_creacion as altaCuenta, cue.fecha_baja as bajaCuenta, tc.id_tipocuenta, tc.descripcion as descTipoCuenta,
    c.id_cliente, c.dni, c.cuil, c.nombre, c.apellido, c.sexo, c.nacionalidad, c.fechanacimiento, c.direccion, l.id_localidad ,l.descripcion AS localidad,
    u.id_usuario, u.nombreusuario,u.contrasenia ,u.id_tipouser, tu.descripcion as descUsuario, u.estado as estadoUsuario, p.id_provincia, p.descripcion AS provincia, c.correo, 
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