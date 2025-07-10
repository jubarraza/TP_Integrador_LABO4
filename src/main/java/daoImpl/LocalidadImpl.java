
package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import dao.LocalidadDao;
import entidad.Localidad;

public class LocalidadImpl implements LocalidadDao {

	private static final String readall = "Select id_localidad, descripcion FROM localidades";

	public LocalidadImpl(Connection conn) {
	}

	public LocalidadImpl() {
	}

	@Override
	public List<Localidad> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Localidad> listaLocalidades = new ArrayList<Localidad>();
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				listaLocalidades.add(getLocalidadFromResultSet(resultSet));
			    
			}
			System.out.println("Cantidad de localidades encontradas: " + listaLocalidades.size());
		} catch (SQLException e) {
			System.out.println("Error al leer Localidades:");
	       
			e.printStackTrace();
		}

		return listaLocalidades;
	}

	private Localidad getLocalidadFromResultSet(ResultSet rs) throws SQLException {
		short id = rs.getShort("id_localidad");
		String descripcion = rs.getString("descripcion");

	    return new Localidad(id, descripcion);
	
	}



}
