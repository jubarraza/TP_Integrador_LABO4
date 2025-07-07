package negocio;

import java.util.List;

import entidad.Prestamo;

public interface negocioPrestamo {
	public boolean insert(Prestamo prestamo);
	public List<Prestamo> readAll();
}
