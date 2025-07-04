package dao;

import java.util.List;


import entidad.Usuario;

public interface UsuarioDao {
	
	public boolean Insert(Usuario usuario);
	public List<Usuario> ReadAll();
	public List<Usuario> ReadAll(int estado);
	public Usuario Read(String usuario, String Pass);
	public boolean Update(Usuario usuario);
	Usuario Autenticar(String username, String password);
	public boolean logicalDelete(int idUsuario);
}
