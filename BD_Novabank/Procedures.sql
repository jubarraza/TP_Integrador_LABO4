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