package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

import dao.PrestamoDao;
import entidad.Prestamo;

public class PrestamoImpl implements PrestamoDao {
	
	private static final String insertPrestamo = "INSERT INTO prestamos (num_de_cuenta, fecha, importe_pedido, cuotas, importe_mensual, estado, aprobado, finalizado) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String readAll = "select * from vista_prestamos";
	
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

	@Override
	public List<Prestamo> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		try {
			statement = conexion.prepareStatement(readAll);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				prestamos.add(getPrestamo(resultSet));
			}
			
		} catch (SQLException e) {
			System.err.println("Error al leer la base de datos");
			e.printStackTrace();;
		}
		return prestamos;
	}
	
	private Prestamo getPrestamo(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String numCuenta = resultSet.getString("NumCuenta");
		LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
		double importePedido = resultSet.getDouble("importePedido");
		short cuotas = resultSet.getShort("cuotas");
		double importeMensual = resultSet.getDouble("importeMensual");
		boolean estado = resultSet.getBoolean("estado");
		boolean aprobado = resultSet.getBoolean("aprobado");
		boolean finalizado = resultSet.getBoolean("finalizado");
		
		return new Prestamo(id, numCuenta, fecha, importePedido, cuotas, importeMensual, estado, aprobado, finalizado);
		
	}

}
