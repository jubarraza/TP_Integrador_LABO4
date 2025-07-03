package dao;

import java.util.List;

import entidad.Cliente;

public interface ClienteDao {
	
	public List<Cliente> ReadAll();
	public List<Cliente> ReadAll(String condicion);
	public int Insert(Cliente cliente);
	public boolean update(Cliente cliente);
	public boolean Delete(String dni);
	public Cliente getClientePorID(int id);
	public Cliente ReadOne(int idCliente);
	public Cliente getPorIdUsuario(int idUsuario);
	public boolean buscarDni(String dni);
}
