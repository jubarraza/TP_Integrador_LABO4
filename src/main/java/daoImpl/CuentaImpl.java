package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.CuentaDao;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoDeCuenta;

public class CuentaImpl implements CuentaDao{

	private static final String insert = "insert into cuentas (num_de_cuenta, cbu, fecha_creacion, fecha_baja, id_tipocuenta, id_cliente, saldo, estado) values (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String baja = "update cuentas set estado = 0, fecha_baja = (curdate()) where num_de_cuenta = ?;";
	private static final String readall = "select * from vista_cuentas";
	private static String tipoCuentas = "select * from tipo_de_cuentas"; 
	
	private static final String buscarId = "SELECT id_cliente FROM vista_clientes_id WHERE dni = ?;";
	private static final String buscarNumCuenta = "select num_de_cuenta from cuentas where num_de_cuenta = ?;";
	private static final String buscarCbu = "select cbu from cuentas where cbu = ?;";
	
	private static final String cantCuentas = "SELECT cantidad FROM vista_cantidad_cuentas_activas WHERE dni = ?;";
	private static final  String readAllxID = "SELECT * FROM vista_cuentas WHERE id_cliente = ? AND estadoCuenta = 1";

	
	@Override
	public boolean insert(Cuenta cuenta) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean insertExitoso = false;
		
		try {
			statement = conexion.prepareStatement(insert);
			statement.setString(1, cuenta.getNumDeCuenta());
			statement.setString(2, cuenta.getCbu());
			
			LocalDate fecha_creacion = cuenta.getFechaCreacion();
			statement.setDate(3, Date.valueOf(fecha_creacion));
			
			LocalDate fecha_baja = cuenta.getFechaBaja();
			if (fecha_baja != null) {
				statement.setDate(4, Date.valueOf(fecha_baja));
			}
			
			statement.setInt(5, cuenta.getTipoCuenta().getIdTipoCuenta());
			statement.setInt(6, cuenta.getCliente().getIdCliente());
			statement.setDouble(7, cuenta.getSaldo());
			statement.setBoolean(8, cuenta.Estado());
			
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
	
	// AGREGAR ESTE MÉTODO COMPLETO DENTRO DE CuentaImpl.java

	@Override
	public boolean deactivateAccountsByClientId(int idCliente) {
	    PreparedStatement statement;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    boolean isSuccess = false;

	    // Esta consulta pone estado=0 y la fecha de baja a las cuentas activas de un cliente.
	    String sql = "UPDATE cuentas SET estado = 0, fecha_baja = CURDATE() WHERE id_cliente = ? AND estado = 1";

	    try {
	        statement = conexion.prepareStatement(sql);
	        statement.setInt(1, idCliente);

	        // executeUpdate() devuelve el número de filas afectadas.
	        // Si es > 0, significa que se desactivaron una o más cuentas.
	        if (statement.executeUpdate() >= 0) {
	            isSuccess = true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        isSuccess = false;
	    }
	    return isSuccess;
	}
	
	public int buscarId(String dni) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int id = 0;
		
		try {
			statement = conexion.prepareStatement(buscarId);
			statement.setString(1, dni);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
            	id = rs.getInt("id_cliente");
            }
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return id;
	}
	
	public int cantidadCuentas(String dni) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cant = 0;
		
		try {
			statement = conexion.prepareStatement(cantCuentas);
			statement.setString(1, dni);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
            	cant = rs.getInt("cantidad");
            }
            
            
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return cant;
	}
	
	public boolean darDeBajaCuenta(String cuenta) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean bajaExitosa = false;
		
		try {
			statement = conexion.prepareStatement(baja);
			statement.setString(1, cuenta);
			
			int filaBorrada = statement.executeUpdate();
			 conexion.commit();
			
			if(filaBorrada > 0) {
				bajaExitosa = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return bajaExitosa;
	}
	
	public List<Cuenta> readAll() {
		
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cuentas.add(getCuenta(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			 System.err.println("Error al leer la base de datos:");
			 e.printStackTrace();
		}

		return cuentas;
	}
	

	@Override
	public List<Cuenta> readAll(String Condicion) {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
		Conexion conexion = Conexion.getConexion();
		String query = "select * from vista_cuentas where " + Condicion;  
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(query);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cuentas.add(getCuenta(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			 System.err.println("Error al leer la base de datos:");
			 e.printStackTrace();
		}

		return cuentas;
	}
	
	private Cuenta getCuenta(ResultSet resultSet) throws SQLException {
		 
		ClienteImpl clienteImpl = new ClienteImpl();
	    Cliente cliente = clienteImpl.getCliente(resultSet);

	    short idTipoCuenta = resultSet.getShort("id_tipocuenta");
	    String descTipoCuenta = resultSet.getString("descTipoCuenta");
	    TipoDeCuenta tipoDeCuenta = new TipoDeCuenta(idTipoCuenta, descTipoCuenta);

	    String numDeCuenta = resultSet.getString("num_de_cuenta");
	    String cbu = resultSet.getString("cbu");
	    LocalDate fechaCreacionCuenta = resultSet.getDate("altaCuenta").toLocalDate();

	    LocalDate fechaBajaCuenta = null;
	    if (resultSet.getDate("bajaCuenta") != null) {
	        fechaBajaCuenta = resultSet.getDate("bajaCuenta").toLocalDate();
	    }

	    double saldo = resultSet.getDouble("saldo");
	    boolean estadoCuenta = resultSet.getBoolean("estadoCuenta");

	    return new Cuenta(
	        numDeCuenta,
	        cbu,
	        fechaCreacionCuenta,
	        fechaBajaCuenta,
	        tipoDeCuenta,
	        cliente,
	        saldo,
	        estadoCuenta
	    );
		
	}



	@Override
	public List<TipoDeCuenta> readAllTipoDeCuenta() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<TipoDeCuenta> tipoDeCuentas = new ArrayList<TipoDeCuenta>();
		Conexion conexion = Conexion.getConexion(); 
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(tipoCuentas);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				tipoDeCuentas.add(getTipoDeCuenta(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			 System.err.println("Error al leer la base de datos:");
			 e.printStackTrace();
		}

		return tipoDeCuentas;
	}
	
		
	private TipoDeCuenta getTipoDeCuenta(ResultSet resultSet) throws SQLException {
	        short idTipoCuenta = resultSet.getShort("id_tipocuenta"); 
	        String descTipoCuenta = resultSet.getString("descripcion"); 
	        return new TipoDeCuenta(idTipoCuenta, descTipoCuenta);		
	}
	
	@Override
	public Cuenta obtenerCuentaPorNumero(String nroCuenta) {
	    PreparedStatement statement;
	    ResultSet resultSet;
	    Cuenta cuenta = null;

	    Conexion conexion = Conexion.getConexion();
	    String query = "SELECT * FROM cuentas WHERE num_de_cuenta = ?";

	    try {
	        statement = conexion.getSQLConexion().prepareStatement(query);
	        statement.setString(1, nroCuenta);
	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            cuenta = getCuenta(resultSet);
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al obtener la cuenta por número:");
	        e.printStackTrace();
	    }

	    return cuenta;
	}
	
	@Override 
	public boolean update(Cuenta cuenta) {
		 Conexion conexion = Conexion.getConexion();
		    PreparedStatement stmt = null;
		    boolean resultado = false;

		    String sql = "UPDATE cuentas SET cbu = ?, fecha_creacion = ?, fecha_baja = ?, " +
		                 "id_tipocuenta = ?, id_cliente = ?, saldo = ?, estado = ? " +
		                 "WHERE num_de_cuenta = ?";

		    try {
		        stmt = conexion.getSQLConexion().prepareStatement(sql);
		        stmt.setString(1, cuenta.getCbu());
		        stmt.setDate(2, cuenta.getFechaCreacion() != null ? Date.valueOf(cuenta.getFechaCreacion()) : null);
		        stmt.setDate(3, cuenta.getFechaBaja() != null ? Date.valueOf(cuenta.getFechaBaja()) : null);
		        stmt.setInt(4, cuenta.getTipoCuenta().getIdTipoCuenta());
		        stmt.setInt(5, cuenta.getCliente().getIdCliente());
		        stmt.setDouble(6, cuenta.getSaldo());
		        stmt.setBoolean(7, cuenta.Estado());
		        stmt.setString(8, cuenta.getNumDeCuenta());

		        int rowsUpdated = stmt.executeUpdate();
		        conexion.getSQLConexion().commit(); 
		        resultado = rowsUpdated > 0;

		    } catch (SQLException e) {
		        try {
		            conexion.getSQLConexion().rollback();  // Revertir si algo falla
		        } catch (SQLException rollbackEx) {
		            rollbackEx.printStackTrace();
		        }
		        e.printStackTrace();
		    } finally {
		        try {
		            if (stmt != null) stmt.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return resultado;
	}
	

	
	@Override 
	public boolean actualizarTipoCuentaYEstado(Cuenta cuenta) {
	    PreparedStatement statement;
	    Connection conexion = Conexion.getConexion().getSQLConexion();

	    String query = "UPDATE cuentas SET id_tipocuenta = ?, estado = ? WHERE num_de_cuenta = ?";

	    try {
	        statement = conexion.prepareStatement(query);
	        statement.setInt(1, cuenta.getTipoCuenta().getIdTipoCuenta());
	        statement.setBoolean(2, cuenta.Estado());
	        statement.setString(3, cuenta.getNumDeCuenta());

	        if (statement.executeUpdate() > 0) {
	            conexion.commit();
	            return true;
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al actualizar tipo de cuenta y estado:");
	        e.printStackTrace();
	        try {
	            conexion.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }

	    return false;
	}
	
	@Override
	public List<Cuenta> readAllByClienteId(int idCliente) {
	    PreparedStatement statement;
	    ResultSet resultSet;
	    ArrayList<Cuenta> cuentas = new ArrayList<>();
	    Connection conexion = Conexion.getConexion().getSQLConexion(); 
	    
	    try {
	        statement = conexion.prepareStatement(readAllxID);
	        statement.setInt(1, idCliente);
	        resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            cuentas.add(getCuenta(resultSet)); 
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al leer las cuentas del cliente:");
	        e.printStackTrace();
	    }
	    
	    return cuentas;
	}

	@Override
	public boolean buscarNumCuenta(String numCuenta) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;
		
		try {
			statement = conexion.prepareStatement(buscarNumCuenta);
			statement.setString(1, numCuenta);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
            	existe = true;
            }
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return existe;
	}

	@Override
	public boolean buscarCbu(String cbu) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;
		
		try {
			statement = conexion.prepareStatement(buscarCbu);
			statement.setString(1, cbu);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
            	existe = true;
            }
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return existe;
	}
}
