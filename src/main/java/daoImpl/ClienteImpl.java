package daoImpl;


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.ClienteDao;
import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;
import entidad.TipoUser;
import entidad.Usuario;

public class ClienteImpl implements ClienteDao{
	
	private Connection conexion;
	private static final String insert = "Insert into clientes\r\n"
			+ "(dni, cuil, nombre, apellido, sexo, nacionalidad, fechanacimiento, direccion, id_localidad, correo, telefono, fecha_alta) \r\n"
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "UPDATE clientes set estado = 0 WHERE dni LIKE ?";
	private static final String readall =  "select * from vista_clientes WHERE estadoUsuario = 1";
	private static final String READ_ONE_BY_ID = "SELECT * FROM vista_clientes WHERE id_cliente = ?";
	
	
	public ClienteImpl(Connection conexion) {
        this.conexion = conexion;
    }

	public ClienteImpl() {
		// TODO Auto-generated constructor stub
	}

	public Cliente ReadOne(int idCliente) {
        PreparedStatement statement;
        ResultSet resultSet;
        Cliente cliente = null; 
        
        try {
            statement = this.conexion.prepareStatement(READ_ONE_BY_ID);
            statement.setInt(1, idCliente);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cliente = getCliente(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

	@Override
	public List<Cliente> ReadAll() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Cliente> cliente = new ArrayList<Cliente>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				cliente.add(getCliente(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			 System.err.println("Error al leer la base de datos: tabla Clientes");
			 e.printStackTrace();
		}

		return cliente;
	}

	@Override
	public List<Cliente> ReadAll(String condicion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Insert(Cliente cliente) {

		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int idGenerado = -1;
		
		try {
			statement = conexion.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, cliente.getDni());
			statement.setString(2, cliente.getCuil());
			statement.setString(3, cliente.getNombre());
			statement.setString(4, cliente.getApellido());
			statement.setString(5, String.valueOf(cliente.getSexo())); // casteo para que nos convierta el char a string e inserte luego en bd.
			statement.setString(6, cliente.getNacionalidad());
			statement.setDate(7, Date.valueOf(cliente.getFechaNacimiento())); // se usa Date.valueOf porque java tiene un metodo estático que convierte un LocalDate en un java.sql.Date
			statement.setString(8, cliente.getDireccion());
			statement.setInt(9, cliente.getLocalidad().getIdLocalidad());
			statement.setString(10, cliente.getCorreo());
			statement.setString(11, cliente.getTelefono());
			statement.setDate(12, Date.valueOf(cliente.getFechaAlta()));
			 
			if (statement.executeUpdate() > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
					idGenerado = rs.getInt(1); 
				}
				conexion.commit();
			}
		}

		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return idGenerado;
		
	}

	@Override
	public boolean update(Cliente cliente) {
		CallableStatement cs = null;
        boolean seActualizo = false;
        try {
            String sql = "{CALL ActualizarCliente(?, ?, ?, ?, ?)}";
            
            cs = this.conexion.prepareCall(sql);
            cs.setInt(1, cliente.getIdCliente());
            cs.setString(2, cliente.getDireccion());
            cs.setInt(3, cliente.getLocalidad().getIdLocalidad());
            cs.setString(4, cliente.getCorreo());
            cs.setString(5, cliente.getTelefono());
            
            int filasAfectadas = cs.executeUpdate();
            System.out.println("DAO: Filas afectadas por la actualización: " + filasAfectadas);
            
            if (filasAfectadas > 0) {
                System.out.println("DAO: La actualización fue exitosa. Haciendo commit...");
                this.conexion.commit();
                seActualizo = true;
            } else {
                 System.out.println("DAO: No se afectaron filas. Haciendo rollback.");
                 this.conexion.rollback();
            }
        } catch (SQLException e) {
            System.err.println("DAO: ¡ERROR! Ocurrió una SQLException al actualizar.");
            e.printStackTrace(); 
            try {
                this.conexion.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return seActualizo;
	}

	@Override
	public boolean Delete(String dni) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = (PreparedStatement) conexion.prepareStatement(delete);
			statement.setString(1, dni);
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isdeleteExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return isdeleteExitoso;

		
	}


	@Override
	public Cliente getClientePorID(int id) {
		Cliente cliente = null;
	    PreparedStatement stmt;
	    ResultSet rs;
	    Connection conn = Conexion.getConexion().getSQLConexion();

	    String query = "SELECT * FROM vista_clientes WHERE id_cliente = ?";

	    try {
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            cliente = getCliente(rs);
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al obtener cliente por ID: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return cliente;
		
		
	}
	
	@Override 
	public Cliente getCliente(ResultSet resultSet) throws SQLException {
		
		// Provincia
	    short idProvincia = resultSet.getShort("idProvincia");
	    String nombreProvincia = resultSet.getString("provincia");
	    Provincia provincia = new Provincia(idProvincia, nombreProvincia);

	    // Localidad
	    short idLocalidad = resultSet.getShort("id_localidad");
	    String nombreLocalidad = resultSet.getString("localidad");
	    Localidad localidad = new Localidad(idLocalidad, nombreLocalidad, provincia);

	    // Usuario (puede ser null)
	    int idUsuario = resultSet.getInt("id_usuario");
	    byte idTipoUser = resultSet.getByte("id_tipouser");
	    String descTipoUser = resultSet.getString("descUsuario");
	    String nombreUsuario = resultSet.getString("nombreusuario");
	    String contrasenia = resultSet.getString("contrasenia");
	    boolean estadoUsuario = resultSet.getBoolean("estadoUsuario");
	    TipoUser tipoUser = new TipoUser(idTipoUser, descTipoUser);
	    Usuario usuario = new Usuario(idUsuario, 0, nombreUsuario, contrasenia, tipoUser, estadoUsuario);

	    // Cliente
	    int idCliente = resultSet.getInt("id_cliente");
	    String dni = resultSet.getString("dni");
	    String cuil = resultSet.getString("cuil");
	    String nombre = resultSet.getString("nombre");
	    String apellido = resultSet.getString("apellido");
	    char sexo = resultSet.getString("sexo").charAt(0);
	    String nacionalidad = resultSet.getString("nacionalidad");
	    LocalDate fechaNacimiento = resultSet.getDate("fechanacimiento").toLocalDate();
	    String direccion = resultSet.getString("direccion");
	    String correo = resultSet.getString("correo");
	    String telefono = resultSet.getString("telefono");
	    LocalDate fechaAlta = resultSet.getDate("altaCliente").toLocalDate();
	    boolean estado = resultSet.getBoolean("estadoUsuario");

	    // Construcción final del objeto Cliente
	    Cliente cliente = new Cliente(
	        idCliente,
	        dni,
	        cuil,
	        nombre,
	        apellido,
	        sexo,
	        nacionalidad,
	        fechaNacimiento,
	        direccion,
	        localidad,
	        correo,
	        telefono,
	        usuario,
	        fechaAlta,
	        estado
	    );

	    return cliente;
	}
	
	@Override
	public Cliente getPorIdUsuario(int idUsuario) {
	    Cliente cliente = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    String query = "SELECT * FROM vista_clientes WHERE id_usuario = ?";

	    try {
	        stmt = this.conexion.prepareStatement(query);
	        stmt.setInt(1, idUsuario);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            cliente = getCliente(rs);
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al obtener cliente por ID de usuario: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }

	    return cliente;
	}

}
