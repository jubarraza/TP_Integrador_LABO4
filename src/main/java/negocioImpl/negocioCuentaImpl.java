package negocioImpl;

import java.util.List;

import dao.CuentaDao;
import entidad.Cuenta;
import entidad.TipoDeCuenta;
import negocio.negocioCuenta;

public class negocioCuentaImpl implements negocioCuenta {
	
	private CuentaDao cuentaDao; 
	
	@Override
	public boolean insert(Cuenta cuenta) {
		return cuentaDao.insert(cuenta);
	}

	@Override
	public List<Cuenta> readAll() {
		return cuentaDao.readAll();
	}

	@Override
	public List<Cuenta> readAll(String Condicion) {
		return cuentaDao.readAll(Condicion);
	}

	@Override
	public List<TipoDeCuenta> readAllTipoDeCuenta() {
		return cuentaDao.readAllTipoDeCuenta();
	}

	@Override
	public boolean update(Cuenta cuenta) {
		return cuentaDao.update(cuenta);
	}

	@Override
	public boolean actualizarTipoCuentaYEstado(Cuenta cuenta) {
		return cuentaDao.actualizarTipoCuentaYEstado(cuenta);
	}

	@Override
	public boolean deactivateAccountsByClientId(int idCliente) {
		return cuentaDao.deactivateAccountsByClientId(idCliente);
	}

	@Override
	public List<Cuenta> readAllByClienteId(int idCliente) {
		return cuentaDao.readAllByClienteId(idCliente);
	}

}
