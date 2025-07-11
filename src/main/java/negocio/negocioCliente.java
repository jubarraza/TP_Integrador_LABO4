package negocio;

import java.util.List;

import entidad.Cliente;

public interface negocioCliente {
	public List<Cliente> ReadAll();
	public List<Cliente> ReadAll(String condicion);
	public int Insert(Cliente cliente);
	public boolean update(Cliente cliente);
	public boolean Delete(String dni);
	public Cliente ReadOne(int idCliente);
	public Cliente getClientePorID(int id);
	public Cliente getPorIdUsuario(int idUsuario);
}
