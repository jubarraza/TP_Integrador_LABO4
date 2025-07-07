package negocioImpl;

import java.util.List;

import dao.ClienteDao;
import entidad.Cliente;
import negocio.negocioCliente;

public class negocioClienteImpl implements negocioCliente {
	private ClienteDao clienteDao;

	public negocioClienteImpl(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	@Override
	public List<Cliente> ReadAll() {
		return clienteDao.ReadAll();
	}

	@Override
	public List<Cliente> ReadAll(String condicion) {
		return clienteDao.ReadAll(condicion);
	}

	@Override
	public int Insert(Cliente cliente) {
		return clienteDao.Insert(cliente);
	}

	@Override
	public boolean update(Cliente cliente) {
		return clienteDao.update(cliente);
	}

	@Override
	public boolean Delete(String dni) {
		return clienteDao.Delete(dni);
	}
	
	
}
