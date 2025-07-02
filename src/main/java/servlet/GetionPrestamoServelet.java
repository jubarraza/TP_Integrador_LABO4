package servlet;

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
import javax.servlet.http.HttpSession;

import daoImpl.CuentaImpl;
import entidad.Cuenta;
import entidad.Usuario;



@WebServlet("/GetionPrestamoServelet")
public class GetionPrestamoServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetionPrestamoServelet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false); // no crea nueva sesión si no existe
		 CuentaImpl cimpl = new CuentaImpl();
		 
		    if (session != null) {
		    	Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogin");

		        if (usuario != null) {
		            int idCliente = usuario.getIdcliente();
		            List<Cuenta> listCuentas = cimpl.readAllCliente(idCliente);
		            
		            request.setAttribute("cuentasTotal", listCuentas);
		        }
		    }
		    
		    RequestDispatcher rd = request.getRequestDispatcher("NuevoPrestamo.jsp");
			rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cuota = request.getParameter("cantidadCuotas");
		String importePedido = request.getParameter("txtMonto");
		LocalDate fechaPrimerVencimiento = LocalDate.now().plusMonths(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		double monto = 0;
		int cuotas = 0;
		int tna = 28;
		
		    try {
		        monto = Double.parseDouble(importePedido);
		        cuotas = Integer.parseInt(cuota);
		    } catch (NumberFormatException e) {
		        request.setAttribute("error", "Datos inválidos en monto o cuotas.");
		        request.getRequestDispatcher("NuevoPrestamo.jsp").forward(request, response);
		        return;
		    }
		    
		// Calcular cuota mensual
		double cuotaMensual = calcularCuota(monto, tna, cuotas);
		double montoTotal = cuotaMensual * cuotas;
		
		
		request.setAttribute("monto", monto);
		request.setAttribute("tna", tna);
		request.setAttribute("montoTotal", montoTotal);
		request.setAttribute("cuotaMensual", cuotaMensual);
		request.setAttribute("primerVencimiento", fechaPrimerVencimiento.format(formatter));
		
		 Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogin");
		    if (usuario != null) {
		        CuentaImpl cimpl = new CuentaImpl();
		        List<Cuenta> cuentas = cimpl.readAllCliente(usuario.getIdcliente());
		        request.setAttribute("cuentasTotal", cuentas);
		    }
		
		RequestDispatcher rd = request.getRequestDispatcher("NuevoPrestamo.jsp");
		rd.forward(request, response);
	}
	
	public static double calcularCuota(double monto, double tasaAnual, int plazoMeses) {
	    double tasaMensual = tasaAnual / 12 / 100;  // Convertir tasa anual a mensual (ej: 12% anual → 1% mensual)
	    double interesPorMes = monto * tasaMensual; // Interés simple del mes
	    double amortizacion = monto / plazoMeses;   // Parte del monto que se paga cada mes (sin intereses)
	    return amortizacion + interesPorMes;        // Cuota mensual = amortización + interés
	}

}
