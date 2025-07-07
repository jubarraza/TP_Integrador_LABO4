package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            conexion.commit();
            exito = resultados.length == prestamo.getCuotas();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conexion != null) conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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


}
