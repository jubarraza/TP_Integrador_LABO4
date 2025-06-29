package servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.ClienteImpl;
import daoImpl.Conexion;
import daoImpl.LocalidadImpl;
import daoImpl.ProvinciaImp;
import daoImpl.UsuarioImpl;
import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;
import entidad.TipoUser;
import entidad.Usuario;


@WebServlet("/InsertarUserClienteServlet")
public class InsertarUserClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public InsertarUserClienteServlet() {
        super();
    }


    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	
    	if ("alta".equals(action)) {
    	ProvinciaImp provDao = new ProvinciaImp();
		List<Provincia> provinciaList = provDao.readAll();
		request.setAttribute("provincias", provinciaList);
		
		LocalidadImpl lcDao = new LocalidadImpl();
		List<Localidad> localidadesList = lcDao.readAll();
		request.setAttribute("localidades", localidadesList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/ABMUsuarios.jsp");
		rd.forward(request, response);
    	}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Parámetros del formulario 
		request.setCharacterEncoding("UTF-8");
		
	    String dni = request.getParameter("txtDNI");
	    String cuil = request.getParameter("txtCUIL");
	    String nombre = request.getParameter("txtNombreUsuario");
	    String apellido = request.getParameter("txtApellidoUsuario");
	    String sexoStr = request.getParameter("ddlSexo");
	    char sexo = ' ';
	    if (sexoStr != null && !sexoStr.trim().isEmpty()) {
	        sexo = sexoStr.trim().charAt(0);
	    }
	    String nacionalidad = request.getParameter("txtNacionalidad");
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("txtFechaNac"), formatter);
	    String direccion = request.getParameter("txtDomicilio");
	    short idLocalidad = Short.parseShort(request.getParameter("Localidad"));
	    short idProvincia = Short.parseShort(request.getParameter("Provincia"));
	    String correo = request.getParameter("txtEmail");
	    String telefono = request.getParameter("txtTelefono");
	    LocalDate fechaAlta = LocalDate.now();

	    
	    // USUARIO
	    String nombreUsuario = request.getParameter("txtUsuario");
	    String contrasenia = request.getParameter("txtContrasenia");
	    System.out.println("Contraseña recibida: '" + contrasenia + "'");
	    String contraseñaC = request.getParameter("txtContraseniaC");

	    if (contrasenia == null || contrasenia.trim().isEmpty()) {
	        request.setAttribute("mensajeError", "La contraseña no puede estar vacía.");
	        doGet(request, response);
	        return;
	    }

	    if (!contrasenia.equals(contraseñaC)) {
	        request.setAttribute("mensajeError", "Las contraseñas no coinciden.");
	        doGet(request, response);
	        return;
	    }
	    
	    // Asociar provincia y localidad
	    Localidad localidad = new Localidad();
	    localidad.setIdLocalidad(idLocalidad);

	    Provincia provincia = new Provincia();
	    provincia.setIdProvincia(idProvincia);

	    // Crear Cliente
	    Cliente nuevoCliente = new Cliente();
	    nuevoCliente.setDni(dni);
	    nuevoCliente.setCuil(cuil);
	    nuevoCliente.setNombre(nombre);
	    nuevoCliente.setApellido(apellido);
	    nuevoCliente.setSexo(sexo);
	    nuevoCliente.setNacionalidad(nacionalidad);
	    nuevoCliente.setFechaNacimiento(fechaNacimiento);
	    nuevoCliente.setDireccion(direccion);
	    nuevoCliente.setLocalidad(localidad);
	    nuevoCliente.setCorreo(correo);
	    nuevoCliente.setTelefono(telefono);
	    nuevoCliente.setFechaAlta(fechaAlta);

	    Connection conexion = null;
	    boolean exito = false;

	    try {
	        conexion = Conexion.getConexion().getSQLConexion();
	        conexion.setAutoCommit(false); // IMPORTANTE: para manejar manualmente la transacción

	        // Insertar cliente
	        ClienteImpl clienteDao = new ClienteImpl(conexion);
	        int idGenerado = clienteDao.Insert(nuevoCliente); 

	        boolean insertado = false;

	        if (idGenerado > 0) {
	            Usuario user = new Usuario();
	            user.setIdcliente(idGenerado);
	            user.setNombreUsuario(nombreUsuario);
	            user.setContrasenia(contrasenia);
	            user.setEstado(true);

	            TipoUser tipoUser = new TipoUser();
	            tipoUser.setIdTipoUser((byte) 2);
	            user.setTipoUser(tipoUser);

	            UsuarioImpl dao = new UsuarioImpl();
	            insertado = dao.Insert(user);
	        }

	        if (idGenerado > 0 && insertado) {
	            conexion.commit(); 
	            request.setAttribute("mensajeExito", "Usuario agregado exitosamente");
	            request.getRequestDispatcher("Home.jsp").forward(request, response);

	        } else {
	        	conexion.rollback(); 
	        	RequestDispatcher rd = request.getRequestDispatcher("/ABMUsuarios.jsp");
	        	rd.forward(request, response);

	        }

	    } catch (Exception e) {
	        try {
	            if (conexion != null) conexion.rollback(); 
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	        request.setAttribute("mensajeError", "Error en la base de datos: " + e.getMessage());
	    } finally {
	        try {
	            if (conexion != null) {
	                conexion.setAutoCommit(true); // restaurar autocommit por si se reutiliza
	                conexion.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    doGet(request, response);
	}
}