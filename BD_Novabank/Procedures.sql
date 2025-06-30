Drop procedure if exists ActualizarUsuario;

DELIMITER //

CREATE PROCEDURE ActualizarUsuario (IN U_id_cliente INT, IN U_nombre_usuario VARCHAR(20),
    IN U_contrasenia VARCHAR(12), IN U_id_tipouser TINYINT, IN U_estado TINYINT(1) 
)
BEGIN
    UPDATE usuarios
    SET
        nombreusuario = U_nombre_usuario,
        contrasenia = U_contrasenia,
        id_tipouser = U_id_tipouser,
        estado = U_estado
    WHERE
        id_cliente = U_id_cliente;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS ActualizarCliente;

DELIMITER //
CREATE PROCEDURE ActualizarCliente (
    IN p_id_cliente INT,
    IN p_direccion VARCHAR(100),
    IN p_id_localidad SMALLINT,
    IN p_correo VARCHAR(50),
    IN p_telefono VARCHAR(15)
)
BEGIN
    UPDATE clientes
    SET
        direccion = p_direccion,
        id_localidad = p_id_localidad,
        correo = p_correo,
        telefono = p_telefono
    WHERE
        id_cliente = p_id_cliente;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS EliminarLogicoUsuario;
DELIMITER //
CREATE PROCEDURE EliminarLogicoUsuario(IN p_id_usuario INT)
BEGIN
    UPDATE usuarios SET estado = 0 WHERE id_usuario = p_id_usuario;
    
END //
DELIMITER ;
