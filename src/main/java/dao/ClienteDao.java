package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entidad.Cliente;

public interface ClienteDao {
	
	public List<Cliente> ReadAll();
	public List<Cliente> ReadAll(String condicion);
	public int Insert(Cliente cliente);
	public boolean update(Cliente cliente);
	public boolean Delete(String dni);
	Cliente getCliente(ResultSet rs) throws SQLException;
	Cliente getClientePorID(int id);
	public Cliente ReadOne(int idCliente);
	
}
