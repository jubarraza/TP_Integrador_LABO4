package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.Conexion;
import daoImpl.CuentaImpl;
import daoImpl.MovimientoImpl;
import entidad.Cuenta;
import entidad.Movimiento;
import entidad.Usuario;

@WebServlet("/MovimientoServlet")
public class MovimientoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	
	@Override
	public void init() throws ServletException {
		super.init();
		// Obtener la conexión global desde tu clase Conexion
		conn = Conexion.getConexion().getSQLConexion();
	}

	@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    MovimientoImpl movDao = new MovimientoImpl(conn);
		    CuentaImpl cuentaDao = new CuentaImpl();
		    
		    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		    
		    int idCliente = usuario.getIdcliente(); //
		    List<Cuenta> listCuentas = cuentaDao.readAllByClienteId(idCliente);

		    request.setAttribute("cuentas", listCuentas);  
		    request.getRequestDispatcher("Movimientos.jsp").forward(request, response);
		}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    MovimientoImpl movDao = new MovimientoImpl(conn);
	    CuentaImpl cuentaDao = new CuentaImpl();

	    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	    
	    String operacion = request.getParameter("operacion");
	    if ("Movimientos".equals(operacion)) {
	    	int idCliente = usuario.getIdcliente(); //
	    	String numCuenta = request.getParameter("cuenta");

	        List<Movimiento> movimientos;
	        if (numCuenta == null || numCuenta.isEmpty()) {
	            movimientos = new ArrayList<>();
	            List<Cuenta> cuentasCliente = cuentaDao.readAllByClienteId(idCliente);
	            for (Cuenta cuenta : cuentasCliente) {
	            	List<Movimiento> movs = movDao.getMovimientosPorCuenta(numCuenta);
	            	if(movs != null) {
	            		movimientos.addAll(movs);
	            	}
	            }
	        } else {
	            movimientos = movDao.getMovimientosPorCuenta(numCuenta);
	        }

	        List<Cuenta> cuentas = cuentaDao.readAllByClienteId(idCliente);
	        request.setAttribute("cuentaSeleccionada", numCuenta);
	        request.setAttribute("cuentas", cuentas);             
	        request.setAttribute("movimientos", movimientos);      
	        request.getRequestDispatcher("Movimientos.jsp").forward(request, response);
	    } else {
	        response.sendRedirect("MovimientoServlet");
	    }
	}


}
