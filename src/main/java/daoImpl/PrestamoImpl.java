package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.PrestamoDao;
import entidad.Prestamo;

public class PrestamoImpl implements PrestamoDao {
	
	private static final String insertPrestamo = "INSERT INTO prestamos (num_de_cuenta, fecha, importe_pedido, cuotas, importe_mensual, estado, aprobado, finalizado) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	@Override
	public boolean insert(Prestamo prestamo) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean insertExitoso = false;
		
		try {
			statement = conexion.prepareStatement(insertPrestamo);
			statement.setString(1, prestamo.getNumDeCuenta());
			
			LocalDate fechaPedido = prestamo.getFecha();
			statement.setDate(2, Date.valueOf(fechaPedido));
			
			statement.setDouble(3, prestamo.getImportePedido());
			statement.setShort(4, prestamo.getCuotas());
			statement.setDouble(5, prestamo.getImporteMensual());
			statement.setBoolean(6, prestamo.isEstado());
			statement.setBoolean(7, prestamo.isAprobado());
			statement.setBoolean(8, prestamo.isFinalizado());
			
			if (statement.executeUpdate() > 0) {
				conexion.commit();
				insertExitoso = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return insertExitoso;
	}

}
