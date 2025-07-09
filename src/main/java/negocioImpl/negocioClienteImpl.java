package negocioImpl;

import java.util.List;

import dao.ClienteDao;
import daoImpl.ClienteImpl;
import entidad.Cliente;

import negocio.negocioCliente;


public class negocioClienteImpl implements negocioCliente {
	
	private ClienteDao clienteDao;

	public negocioClienteImpl(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}
	
	public negocioClienteImpl() {
		clienteDao = new ClienteImpl();
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

	@Override
	public Cliente ReadOne(int idCliente) {
		return clienteDao.ReadOne(idCliente);
	}

	
}
