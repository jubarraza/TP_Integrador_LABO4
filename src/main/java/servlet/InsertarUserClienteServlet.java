package servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Validacion.Validaciones;
import daoImpl.Conexion;
import daoImpl.LocalidadImpl;
import daoImpl.ProvinciaImp;
import daoImpl.UsuarioImpl;
import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;
import entidad.TipoUser;
import entidad.Usuario;
import negocioImpl.negocioClienteImpl;
import negocioImpl.negocioUsuarioImpl;


@WebServlet("/InsertarUserClienteServlet")

public class InsertarUserClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public InsertarUserClienteServlet() {
        super();
    }


    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cargarListas(request);
		request.getRequestDispatcher("/ABMUsuarios.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Parámetros del formulario 
		request.setCharacterEncoding("UTF-8");
		
	    String dni = request.getParameter("txtDNI");
	    if (dni == null || !dni.matches("\\d+")) {
	    	setValoresFormularioEnRequest(request);
	    	mostrarError(request, response, "DNI inválido. Debe contener solo números.");
			return;
	    }
	    // Validar si DNI ya existe en la BD
	    if (Validaciones.existeDNI(dni)) {
	    	setValoresFormularioEnRequest(request);
	    	mostrarError(request, response, "El DNI ya está registrado.");
			return;
	    }
	    
	    String cuil = request.getParameter("txtCUIL");
	    if (cuil == null || !cuil.matches("\\d{10,11}")) {
	    	setValoresFormularioEnRequest(request);
	    	mostrarError(request, response, "CUIL inválido. Debe tener entre 10 y 11 dígitos.");
			return;
	    }
	    
	    String nombre = request.getParameter("txtNombreUsuario");
	    String apellido = request.getParameter("txtApellidoUsuario");
	    String nacionalidad = request.getParameter("txtNacionalidad");
	    String direccion = request.getParameter("txtDomicilio");
	    
	    if(esVacio(nombre)) {setValoresFormularioEnRequest(request); mostrarError(request, response, "El nombre no puede estar vacío."); return;}
	    if(esVacio(apellido)) {setValoresFormularioEnRequest(request); mostrarError(request, response, "El apellido no puede estar vacío."); return;}
	    if(esVacio(nacionalidad)) {setValoresFormularioEnRequest(request); mostrarError(request, response, "La nacionalidad no puede estar vacía."); return;}
	    if(esVacio(direccion)) {setValoresFormularioEnRequest(request); mostrarError(request, response, "La dirección no puede estar vacía."); return;}
	    
	    
	    
	    String sexoStr = request.getParameter("ddlSexo");
	    char sexo = ' ';
	    if (sexoStr != null && !sexoStr.trim().isEmpty()) {
	        sexo = sexoStr.trim().charAt(0);
	    }

	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    //LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("txtFechaNac"), formatter);
	    // Validar fecha de nacimiento con método existente (si querés)
	    LocalDate fechaNacimiento;
		try {
			fechaNacimiento = LocalDate.parse(request.getParameter("txtFechaNac"), formatter);
			int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
			if (edad < 18 || edad > 100) {
				setValoresFormularioEnRequest(request);
				mostrarError(request, response, "Debe tener entre 18 y 100 años para registrarse.");
				return;
			}
		} catch (Exception e) {
			setValoresFormularioEnRequest(request);
			mostrarError(request, response, "Fecha de nacimiento inválida.");
			return;
		}
	    
	    short idLocalidad, idProvincia;
	    try {
	    	  idLocalidad = Short.parseShort(request.getParameter("Localidad"));
	    	  idProvincia = Short.parseShort(request.getParameter("Provincia"));
	    } catch(NumberFormatException e) {
	    	setValoresFormularioEnRequest(request);
	    	mostrarError(request, response, "Debe seleccionar una provincia y localidad válidas.");
			return;
	    }
	    String correo = request.getParameter("txtEmail");
	    if (correo == null || !correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
	    	setValoresFormularioEnRequest(request);
	    	mostrarError(request, response, "Correo electrónico inválido.");
			return;
	    }
	    if (Validaciones.existeEmail(correo)) {
	    	setValoresFormularioEnRequest(request);
	    	mostrarError(request, response, "El correo electrónico ya está registrado.");
			return;
	    }
	    
	    String telefono = request.getParameter("txtTelefono");
	    if(telefono == null || telefono.trim().isEmpty() || !telefono.matches("\\d+")) {
	    	setValoresFormularioEnRequest(request);
	    	mostrarError(request, response, "El teléfono debe contener solo números.");
			return;
		}
	    
	    LocalDate fechaAlta = LocalDate.now();

	    
	    // USUARIO
	    String nombreUsuario = request.getParameter("txtUsuario");
	    String contrasenia = request.getParameter("txtContrasenia");
	    String contraseñaC = request.getParameter("txtContraseniaC");
	    
	    if(esVacio(nombreUsuario)) {setValoresFormularioEnRequest(request); mostrarError(request, response, "El nombre de usuario no puede estar vacío."); return; }	    
		if (contrasenia == null || contrasenia.length() < 8) { mostrarError(request, response, "La contraseña debe tener al menos 8 caracteres."); return; }
		if (!contrasenia.equals(contraseñaC)) { mostrarError(request, response, "Las contraseñas no coinciden."); return; }

	    
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
        System.out.println("Paso 2: Insertando cliente");
	    Connection conexion = null;

	    try {
	        conexion = Conexion.getConexion().getSQLConexion();
	        conexion.setAutoCommit(false); // IMPORTANTE: para manejar manualmente la transacción

	        // Insertar cliente
	        negocioClienteImpl negocioClienteImpl = new negocioClienteImpl();
	        int idGenerado = negocioClienteImpl.Insert(nuevoCliente); 
		    System.out.println("Paso 3: Cliente insertado, id: " + idGenerado);
		    
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

	            negocioUsuarioImpl negocio = new negocioUsuarioImpl(conexion);
	            insertado = negocio.Insert(user);
	        }

	        if (idGenerado > 0 && insertado) {
	            conexion.commit(); 
	            System.out.println("Paso 4: Insertando usuario");
	            request.setAttribute("mensajeExito", "Cliente agregado correctamente.");
	            request.getRequestDispatcher("Home.jsp").forward(request, response);
	            return;

	        } else {
	        	conexion.rollback(); 
	        	mostrarError(request, response, "Error al registrar usuario/cliente.");
	        }

	    } catch (Exception e) {
	        try {
	            if (conexion != null) conexion.rollback(); 
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        
	        	} 
	        mostrarError(request, response, "Error de base de datos: " + e.getMessage());
	    }
	    //RequestDispatcher rd = request.getRequestDispatcher("/ABMUsuarios.jsp");
		//rd.forward(request, response);

	}
	
	private boolean esVacio(String valor) {
		return valor == null || valor.trim().isEmpty();
	}
	
	private void mostrarError(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
		cargarListas(request);
		request.setAttribute("mensajeError", mensaje);
		request.getRequestDispatcher("/ABMUsuarios.jsp").forward(request, response);
	}
	
	private void cargarListas(HttpServletRequest request) {
		request.setAttribute("provincias", new ProvinciaImp().readAll());
		request.setAttribute("localidades", new LocalidadImpl().readAll());
	}

	private void setValoresFormularioEnRequest(HttpServletRequest request) {
	    request.setAttribute("txtDNI", request.getParameter("txtDNI"));
	    request.setAttribute("txtCUIL", request.getParameter("txtCUIL"));
	    request.setAttribute("txtNombreUsuario", request.getParameter("txtNombreUsuario"));
	    request.setAttribute("txtApellidoUsuario", request.getParameter("txtApellidoUsuario"));
	    request.setAttribute("txtNacionalidad", request.getParameter("txtNacionalidad"));
	    request.setAttribute("txtDomicilio", request.getParameter("txtDomicilio"));
	    request.setAttribute("ddlSexo", request.getParameter("ddlSexo"));
	    request.setAttribute("txtFechaNac", request.getParameter("txtFechaNac"));
	    request.setAttribute("Localidad", request.getParameter("Localidad"));
	    request.setAttribute("Provincia", request.getParameter("Provincia"));
	    request.setAttribute("txtEmail", request.getParameter("txtEmail"));
	    request.setAttribute("txtTelefono", request.getParameter("txtTelefono"));
	    request.setAttribute("txtUsuario", request.getParameter("txtUsuario"));
	}


}
