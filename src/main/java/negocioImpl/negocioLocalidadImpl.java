package negocioImpl;

import java.sql.Connection;
import java.util.List;

import dao.LocalidadDao;
import daoImpl.LocalidadImpl;
import entidad.Localidad;
import negocio.negocioLocalidad;

public class negocioLocalidadImpl implements negocioLocalidad {

	private LocalidadDao localidadDao;
	
	public negocioLocalidadImpl(Connection conexion) {
		this.localidadDao = new LocalidadImpl(conexion);
	}

	public negocioLocalidadImpl(LocalidadDao localidadDao) {
		this.localidadDao = localidadDao;
	}
	
	public negocioLocalidadImpl() {
		localidadDao = new LocalidadImpl();
	}
	
	@Override
	public List<Localidad> readAll() {
		return localidadDao.readAll();
	}

}
