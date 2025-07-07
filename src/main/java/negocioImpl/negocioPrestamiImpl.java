package negocioImpl;

import java.util.List;

import dao.PrestamoDao;
import entidad.Prestamo;
import negocio.negocioPrestamo;

public class negocioPrestamiImpl implements negocioPrestamo {
	
	private PrestamoDao prestamoDao;

	public negocioPrestamiImpl(PrestamoDao prestamoDao) {
		this.prestamoDao = prestamoDao;
	}

	@Override
	public boolean insert(Prestamo prestamo) {
		return prestamoDao.insert(prestamo);
	}

	@Override
	public List<Prestamo> readAll() {
		return prestamoDao.readAll();
	}
	
	
	
}
