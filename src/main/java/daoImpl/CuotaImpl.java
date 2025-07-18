package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import dao.CuotaDao;
import entidad.Cuota;
import entidad.Prestamo;

public class CuotaImpl implements CuotaDao {

    private Connection conexion;

    private static final String INSERT_CUOTA =
        "INSERT INTO cuotas (id_prestamo, num_cuota, monto, estado, fecha_pago) " +
        "VALUES (?, ?, ?, 1, ?);";


    public CuotaImpl(Connection conexion) {
        this.conexion = conexion;
    }

    public CuotaImpl() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public boolean generarCuotas(Prestamo prestamo) {
        boolean exito = false;
        PreparedStatement stmt = null;

        try {
            stmt = conexion.prepareStatement(INSERT_CUOTA);

            for (int i = 1; i <= prestamo.getCuotas(); i++) {
                stmt.setInt(1, prestamo.getIdPrestamo());
                stmt.setInt(2, i);
                stmt.setDouble(3, prestamo.getImporteMensual());

                stmt.setNull(4, java.sql.Types.TIMESTAMP);
                stmt.addBatch();
            }

            int[] resultados = stmt.executeBatch();
            exito = resultados.length == prestamo.getCuotas();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return exito;
    }

	

	@Override
	public List<Cuota> readAllByPrestamoId(int idPrestamo) {
	    PreparedStatement statement;
	    ResultSet resultSet;
	    ArrayList<Cuota> cuotas = new ArrayList<>();
	    Connection conexion = Conexion.getConexion().getSQLConexion(); 
	    
	    String query = "SELECT * FROM cuotas WHERE id_prestamo = ? ORDER BY num_cuota ASC";
	    
	    try {
	        statement = conexion.prepareStatement(query);
	        statement.setInt(1, idPrestamo);
	        resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            cuotas.add(getCuota(resultSet));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cuotas;
	}

	private Cuota getCuota(ResultSet resultSet) throws SQLException {
	    int idPagoDeCuota = resultSet.getInt("id_pago_de_cuota");
	    int idPrestamo = resultSet.getInt("id_prestamo");
	    short numCuota = resultSet.getShort("num_cuota");
	    double monto = resultSet.getDouble("monto");
	    boolean estado = resultSet.getBoolean("estado");
	    
	    LocalDate fechaPago = null;
	    if (resultSet.getDate("fecha_pago") != null) {
	        fechaPago = resultSet.getDate("fecha_pago").toLocalDate();
	    }

	    return new Cuota(idPagoDeCuota, idPrestamo, numCuota, monto, estado, fechaPago);
	}
	
	@Override
	public Cuota readOne(int idCuota) {
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    String query = "SELECT * FROM cuotas WHERE id_pago_de_cuota = ?";

	    try (PreparedStatement statement = conexion.prepareStatement(query)) {
	        statement.setInt(1, idCuota);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                return getCuota(resultSet);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	@Override
	public boolean pagarCuota(int idCuota, String numCuentaOrigen) {
	    Connection cn = Conexion.getConexion().getSQLConexion();
	    try {
	        cn.setAutoCommit(false);

	        //  Obtener datos de la cuota
	        double montoCuota = 0;
	        int idPrestamo = 0;
	        try (PreparedStatement st1 = cn.prepareStatement("SELECT monto, id_prestamo FROM cuotas WHERE id_pago_de_cuota = ? AND estado = 1")) {
	            st1.setInt(1, idCuota);
	            ResultSet rs = st1.executeQuery();
	            if (rs.next()) {
	                montoCuota = rs.getDouble("monto");
	                idPrestamo = rs.getInt("id_prestamo");
	            } else {
	                throw new SQLException("La cuota no existe o ya fue pagada.");
	            }
	        }

	        //  Descontar saldo de la cuenta de origen
	        try (PreparedStatement st2 = cn.prepareStatement("UPDATE cuentas SET saldo = saldo - ? WHERE num_de_cuenta = ? AND saldo >= ?")) {
	            st2.setDouble(1, montoCuota);
	            st2.setString(2, numCuentaOrigen);
	            st2.setDouble(3, montoCuota);
	            if (st2.executeUpdate() == 0) {
	                throw new SQLException("Saldo insuficiente o cuenta no encontrada.");
	            }
	        }

	        // Actualizar estado de la cuota a "Pagado"
	        try (PreparedStatement st3 = cn.prepareStatement("UPDATE cuotas SET estado = 0, fecha_pago = CURDATE() WHERE id_pago_de_cuota = ?")) {
	            st3.setInt(1, idCuota);
	            st3.executeUpdate();
	        }

	        // Verifica si quedan cuotas pendientes para ese prstamo
	        int cuotasPendientes = 0;
	        try (PreparedStatement st4 = cn.prepareStatement("SELECT COUNT(*) FROM cuotas WHERE id_prestamo = ? AND estado = 1")) {
	            st4.setInt(1, idPrestamo);
	            ResultSet rs = st4.executeQuery();
	            if (rs.next()) {
	                cuotasPendientes = rs.getInt(1);
	            }
	        }

	        // Si no quedan cuotas pendientes, marcar el prestamo como finalizado
	        if (cuotasPendientes == 0) {
	            try (PreparedStatement st5 = cn.prepareStatement("UPDATE prestamos SET finalizado = 1 WHERE id_prestamo = ?")) {
	                st5.setInt(1, idPrestamo);
	                st5.executeUpdate();
	                System.out.println("PRÉSTAMO FINALIZADO: ID " + idPrestamo);
	            }
	        }

	        //confirmar la transaccion completa
	        cn.commit();
	        return true;

	    } catch (SQLException e) {
	        System.err.println("ERROR EN LA TRANSACCIÓN DE PAGO, revirtiendo cambios: " + e.getMessage());
	        try {
	            if (cn != null) cn.rollback();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	        return false;
	    } finally {
	        try {
	            cn.setAutoCommit(true); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


}
