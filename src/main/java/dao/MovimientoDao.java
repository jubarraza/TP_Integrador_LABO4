package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entidad.Movimiento;
import entidad.TipoDeMovimiento;
import entidad.Usuario;

public interface MovimientoDao {
	public List<Movimiento> ReadAll();
	public int Insert(Movimiento mov);
	public boolean update(Movimiento mov);
	Movimiento getDetalleMov(ResultSet rs) throws SQLException;
	Movimiento getMovimientoPorID(int id);
	TipoDeMovimiento getIDTipoMov(int tipoMov);	
	public List<Movimiento> getMovimientosPorCuenta(String cuenta);
}