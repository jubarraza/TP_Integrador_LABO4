package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Validacion.Validaciones;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoDeCuenta;
import excepcion.ClienteNoExisteExcepcion;
import negocioImpl.negocioCuentaImpl;


@WebServlet("/InsertCuentasServlet")
public class InsertCuentasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InsertCuentasServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		negocioCuentaImpl negocioCuentaImpl = new negocioCuentaImpl();
		List<TipoDeCuenta> listaTipo = negocioCuentaImpl.readAllTipoDeCuenta();
		
		request.setAttribute("tipoCuenta", listaTipo);
		
		RequestDispatcher rd = request.getRequestDispatcher("ABMCuentas.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String dni = request.getParameter("txtDni");
	    String numCuenta = request.getParameter("txtNumeroCuenta");
	    String cbu = request.getParameter("txtCbu");
	    
	    if (dni == null || dni.trim().isEmpty() || numCuenta == null || numCuenta.trim().isEmpty() ||
	            cbu == null || cbu.trim().isEmpty()) {

	            request.setAttribute("mensajeError", "Todos los campos son obligatorios.");
	            doGet(request, response);
	            return;
	        }
	    
	    if (!dni.matches("\\d{7,8}")) {
	        request.setAttribute("mensajeError", "El DNI debe contener solo números y tener entre 7 y 8 dígitos.");
	        doGet(request, response);
	        return;
	    }
	    
	    
	    if (!numCuenta.matches("\\d{11,13}")) {
	        request.setAttribute("mensajeError", "El número de cuenta debe contener solo números y tener entre 11 y 13 dígitos.");
	        doGet(request, response);
	        return;
	    }
	    
	    
	    if (!cbu.matches("\\d{22}")) {
	        request.setAttribute("mensajeError", "El CBU debe contener exactamente 22 dígitos numéricos.");
	        doGet(request, response);
	        return;
	    }
	    
	    LocalDate fechaAlta = LocalDate.now();
	    
	    int idTipo = Integer.parseInt(request.getParameter("tipoCuenta"));

	    negocioCuentaImpl negocioCuentaImpl = new negocioCuentaImpl();

	    // Verificar si el cliente existe por su DNI
	    int idCliente = negocioCuentaImpl.buscarId(dni);
	   	    
	    try {
	        Validaciones.ClienteInexistente(idCliente); 
	    } catch (ClienteNoExisteExcepcion e) {
	        request.setAttribute("mensajeError", "El DNI no pertenece a un cliente.");
	        doGet(request, response);
	        return;
	    }
	    
	    
	    
	    //Verificar cantidad de cuentas
	    int cantCuenta = negocioCuentaImpl.cantidadCuentas(dni);
	    if (cantCuenta >= 3) {
	    	request.setAttribute("mensajeError", "No puede crear mas cuentas, 3 es el limite.");
	        doGet(request, response);
	        return;
		}

	    // Construir TipoDeCuenta y Cliente
	    TipoDeCuenta tipo = new TipoDeCuenta();
	    tipo.setIdTipoCuenta((short) idTipo);

	    Cliente cliente = new Cliente();
	    cliente.setIdCliente(idCliente); // ← correcto

	    // Crear Cuenta
	    Cuenta cuenta = new Cuenta(numCuenta, cbu, fechaAlta, tipo, cliente);

	    boolean insertCuenta = negocioCuentaImpl.insert(cuenta);
	    if (insertCuenta) {
	        request.setAttribute("mensajeExito", "Cuenta agregada correctamente.");
	    } else {
	        request.setAttribute("mensajeError", "No se pudo agregar la cuenta.");
	    }

	    doGet(request, response);
	}

}
