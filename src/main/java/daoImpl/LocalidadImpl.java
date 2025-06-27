package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.LocalidadDao;
import entidad.Localidad;
import entidad.Provincia;

public class LocalidadImpl implements LocalidadDao {

	private Connection conexion;
	private static final String READ_ALL = "SELECT l.id_localidad, l.descripcion AS localidad_desc, p.id_provincia, p.descripcion AS provincia_desc FROM localidades l INNER JOIN provincias p ON l.id_provincia = p.id_provincia ORDER BY l.descripcion ASC";

	public LocalidadImpl(Connection conexion) {
		this.conexion = conexion;
	}

	public LocalidadImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Localidad> readAll() {
		ArrayList<Localidad> listaLocalidades = new ArrayList<>();
		PreparedStatement statement;
		ResultSet resultSet;

		try {
			statement = this.conexion.prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				listaLocalidades.add(getLocalidadFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaLocalidades;
	}

	private Localidad getLocalidadFromResultSet(ResultSet rs) throws SQLException {
		Provincia provincia = new Provincia();
		provincia.setIdProvincia((short) rs.getInt("id_provincia"));
		provincia.setDescripcion(rs.getString("provincia_desc"));

		Localidad localidad = new Localidad();
		localidad.setIdLocalidad((short) rs.getInt("id_localidad"));
		localidad.setDescripcion(rs.getString("localidad_desc"));
		localidad.setProvincia(provincia);

		return localidad;
	}



}
