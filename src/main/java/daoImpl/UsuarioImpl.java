package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.CallableStatement;

import dao.UsuarioDao;
import entidad.TipoUser;
import entidad.Usuario;

public class UsuarioImpl implements UsuarioDao {
	private static final String insert = "Insert into usuarios\r\n"
			+ "(id_cliente, nombreusuario, contrasenia, id_tipouser) \r\n" + "values(?, ?, ?, ?)";
	private static final String readall = "select * from vista_usuarios";

	private Connection conexion;

	public UsuarioImpl(Connection conexion) {
		this.conexion = conexion;
	}
	
	public UsuarioImpl() {}

	@Override
	public List<Usuario> ReadAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				usuarios.add(getUsuario(resultSet));
			}
		} catch (SQLException e) {
			System.err.println("Error al leer la base de datos:");
			e.printStackTrace();
		}

		return usuarios;
	}

	@Override
	public List<Usuario> ReadAll(int estado) {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Conexion conexion = Conexion.getConexion();
		String query = readall + " where estado = " + estado;
		try {
			statement = conexion.getSQLConexion().prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				usuarios.add(getUsuario(resultSet));
			}
		} catch (SQLException e) {
			System.err.println("Error al leer la base de datos:");
			e.printStackTrace();
		}

		return usuarios;
	}

	@Override
	public Usuario Read(String usuario, String Pass) {

		Usuario user = new Usuario();
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		String query = readall + " where nombreUsuario = ? and contrasenia = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(query);
			statement.setString(1, usuario);
			statement.setString(2, Pass);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = getUsuario(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Error al leer la base de datos:");
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public boolean Update(Usuario usuario) {
		Conexion conexion = Conexion.getConexion();
		Connection cn = conexion.getSQLConexion();
		boolean updateExitoso = false;
		CallableStatement cst = null;

		try {
			cst = conexion.prepareCallableStatement("CALL ActualizarUsuario(?,?,?,?,?)");

			cst.setInt(1, usuario.getIdcliente());
			cst.setString(2, usuario.getNombreUsuario());
			cst.setString(3, usuario.getContrasenia());
			cst.setInt(4, usuario.getTipoUser().getIdTipoUser());
			cst.setBoolean(5, usuario.getEstado());

			int filasAfectadas = cst.executeUpdate();

			if (filasAfectadas > 0) {
				updateExitoso = true;
				cn.commit();
			} else {
				cn.rollback();
			}

		} catch (SQLException e) {
			System.err.println("Error al actualizar usuario: " + e.getMessage());
		} finally {
			try {
				if (cst != null) {
					cst.close();
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}

		return updateExitoso;
	}

	@Override
	public boolean Insert(Usuario usuario) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean insertUsuarioExitoso = false;

		// Validar contraseña
		if (usuario.getContrasenia() == null || usuario.getContrasenia().trim().isEmpty()) {
			System.out.println("Error: La contraseña no puede ser nula o vacía");
			return false;
		}

		try {
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, usuario.getIdcliente());
			statement.setString(2, usuario.getNombreUsuario());
			statement.setString(3, usuario.getContrasenia());
			statement.setInt(4, usuario.getTipoUser().getIdTipoUser());

			if (statement.executeUpdate() > 0) {
				// conexion.commit();
				insertUsuarioExitoso = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertUsuarioExitoso;
	}

	private Usuario getUsuario(ResultSet resultSet) throws SQLException {

		byte idTipoUser = resultSet.getByte("IdTipoUser");
		String descTipoUser = resultSet.getString("Descripcion");
		TipoUser tipoUser = new TipoUser(idTipoUser, descTipoUser);

		int idUsuario = resultSet.getInt("id");
		int idCliente = resultSet.getInt("idCliente");
		String nombreUsuario = resultSet.getString("nombreUsuario");
		String contrasenia = resultSet.getString("contrasenia");
		boolean estadoUsuario = resultSet.getBoolean("estado");
		Usuario usuario = new Usuario(idUsuario, idCliente, nombreUsuario, contrasenia, tipoUser, estadoUsuario);
		return usuario;
	}

	@Override
	public Usuario Autenticar(String username, String password) {

		Usuario user = null;
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		String query = readall + " where nombreUsuario = ? and contrasenia = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = getUsuario(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Error al leer la base de datos:");
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public boolean logicalDelete(int idUsuario) {
		Connection cn = Conexion.getConexion().getSQLConexion();
	    boolean isDeleted = false;
	    CallableStatement cst = null;
	    try {
	        cst = cn.prepareCall("CALL EliminarLogicoUsuario(?)");
	        cst.setInt(1, idUsuario);
	        
	        if (cst.executeUpdate() > 0) {
	            cn.commit();
	            isDeleted = true;
	        } else {
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al dar de baja al usuario: " + e.getMessage());
	        try {
	            if (cn != null) cn.rollback();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    } finally {
	        try {
	            if (cst != null) cst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return isDeleted;
	}

}
