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
import javax.servlet.http.HttpSession;

import daoImpl.CuentaImpl;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoDeCuenta;


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
		CuentaImpl cuentaI = new CuentaImpl();
		List<TipoDeCuenta> listaTipo = cuentaI.readAllTipoDeCuenta();
		request.setAttribute("tipoCuenta", listaTipo);
		
		RequestDispatcher rd = request.getRequestDispatcher("ABMCuentas.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Obtener parámetros del formulario
	    String dni = request.getParameter("txtDni");
	    String numCuenta = request.getParameter("txtNumeroCuenta");
	    
	    if (numCuenta.length() > 13) {
	        request.setAttribute("mensajeError", "Número de cuenta excede la longitud permitida.");
	        doGet(request, response);
	        return;
	    }
	    
	    String cbu = request.getParameter("txtCbu");
	    
	    if (cbu.length() > 23) {
	        request.setAttribute("mensajeError", "CBU excede la longitud permitida.");
	        doGet(request, response);
	        return;
	    }
	    
	    if (dni == null || dni.trim().isEmpty() || numCuenta == null || numCuenta.trim().isEmpty() ||
	            cbu == null || cbu.trim().isEmpty()) {

	            request.setAttribute("mensajeError", "Todos los campos son obligatorios.");
	            doGet(request, response);
	            return;
	        }
	    
	    LocalDate fechaAlta = LocalDate.now();
	    
	    int idTipo = Integer.parseInt(request.getParameter("tipoCuenta"));

	    // Instancia de acceso a datos
	    CuentaImpl cuentaI = new CuentaImpl();

	    // Verificar si el cliente existe por su DNI
	    int idCliente = cuentaI.buscarId(dni);
	    if (idCliente <= 0) {
	        request.setAttribute("mensajeError", "El DNI no pertenece a un cliente.");
	        doGet(request, response);
	        return;
	    }
	    
	    //Verificar cantidad de cuentas
	    int cantCuenta = cuentaI.cantidadCuentas(dni);
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

	    // Crear objeto Cuenta con saldo inicial, estado=true, fechaBaja=null
	    Cuenta cuenta = new Cuenta(numCuenta, cbu, fechaAlta, tipo, cliente);

	    // Intentar insertar la cuenta
	    boolean insertCuenta = cuentaI.insert(cuenta);
	    if (insertCuenta) {
	        request.setAttribute("mensajeExito", "Cuenta agregada correctamente.");
	    } else {
	        request.setAttribute("mensajeError", "No se pudo agregar la cuenta.");
	    }

	    // Redirigir a doGet
	    doGet(request, response);
	}

}
