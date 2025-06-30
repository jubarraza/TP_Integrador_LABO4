package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Validacion.Validaciones;
import daoImpl.CuentaImpl;
import entidad.Cliente;
import entidad.Cuenta;

/**
 * Servlet implementation class ReportesServlet
 */
@WebServlet("/ReportesServlet")
public class ReportesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CuentaImpl cuentaDao = new CuentaImpl();
		
		int cuentasActivas = 0;
        int cuentasActivadas = 0;
        int cuentasInactivadas = 0;
        float porcCuentasActivas = 100;
        float porcCuentasInactivas = 100;
        
        List<Cliente> listaClientes = new ArrayList<>();
        
        List<Cuenta> listaCuentas = cuentaDao.readAll();        
        listaCuentas = cuentaDao.readAll();        
        ListIterator<Cuenta> lista = listaCuentas.listIterator();
        
        int clientesActivos = 0;
        
    
        
        LocalDate fechaHasta = LocalDate.now();
        LocalDate fechaInicio = Year.of(fechaHasta.getYear()).atDay(1);;
        
        
        
        boolean VerificarImput = true;

       
        
        if(request.getParameter("btnfiltrar")!=null) 
        {	
        	String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaHastaStr = request.getParameter("fechaHasta");
             
            if(Validaciones.Verificarfecha(fechaInicioStr) && Validaciones.Verificarfecha(fechaHastaStr))
            {
            	fechaInicio = LocalDate.parse(fechaInicioStr);
            	fechaHasta = LocalDate.parse(fechaHastaStr);           	   
            }
            else
            {
            	 VerificarImput = false;            	  
            }
            
            if(fechaHasta.isBefore(fechaInicio))
            {
            	VerificarImput = false; 
            }
        }
        
        
        while(lista.hasNext())
        {	
        	Cuenta cuenta = lista.next();
        	Cliente cliente = cuenta.getCliente();
        	listaClientes.add(cliente);         	
        	{	
        		if(cuenta.Estado())
        		{
        			cuentasActivas += 1;
        			if(!cuenta.getFechaCreacion().isBefore(fechaInicio) && !cuenta.getFechaCreacion().isAfter(fechaHasta))
        			{
        				{
        					cuentasActivadas +=1;
        				}
        			}
        		}
        		else
        		{
        			if(cuenta.getFechaBaja() != null)
            		{
                		if(!cuenta.getFechaBaja().isBefore(fechaInicio) && !cuenta.getFechaBaja().isAfter(fechaHasta))
                    	{	
                    		{
                    			cuentasInactivadas +=1;;
                    		}	
                    	}
            		}
        		}	
        	}
           	 	
        }	
        
        
    	listaClientes = clientesUnicos(listaClientes);
		ListIterator<Cliente> listaC = listaClientes.listIterator();

		while(listaC.hasNext())
	        {
	        	Cliente Cliente = listaC.next();
	        	if(Cliente.Estado())
	        	{
	        		clientesActivos +=1;
	        	}
	        }

        

		
		if(cuentasActivadas == 0)
		{
			porcCuentasActivas = 0;				
		} 
		else 
		{
			if (cuentasInactivadas == 0)
			{
				 porcCuentasInactivas = 0;
			}
			else
			{				
				porcCuentasActivas =  ((float)cuentasActivadas/(cuentasActivadas+cuentasInactivadas))*100;
		        porcCuentasInactivas =  ((float)cuentasInactivadas/(cuentasActivadas+cuentasInactivadas))*100;	
			}			
		}

        request.setAttribute("porcCuentasActivas", porcCuentasActivas);
        request.setAttribute("porcCuentasInactivas", porcCuentasInactivas);
        request.setAttribute("cuentasActivadas", cuentasActivadas);
        request.setAttribute("cuentasInactivadas", cuentasInactivadas);
		request.setAttribute("cuentasActivas", cuentasActivas);
		
		
		//Request Fecha
		request.setAttribute("fechaHasta", fechaHasta.toString());
        request.setAttribute("fechaInicio", fechaInicio.toString());

		
		
		request.setAttribute("clientesActivos", clientesActivos);
		
	    request.setAttribute("VerificarImput", VerificarImput);
	    
		int[] meses = contarMeses(listaCuentas, 1);
		request.setAttribute("meses", meses);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("Reportes.jsp"); 
		rd.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
	private int[] contarMeses(List<Cuenta> cuentas,int anio) {
        
        int[] meses = new int[12]; 

        for (Cuenta cuenta : cuentas) {
        	
            LocalDate fecha = cuenta.getFechaCreacion();
            if (fecha != null) {        
                int indiceMes = fecha.getMonthValue() - 1;
                meses[indiceMes]++;
            }
        }
        return meses;
    }
	
	 
	public List<Cliente> clientesUnicos(List<Cliente> listaClientes) {
	    Set<Cliente> clientesUnicosSet = new HashSet<>(listaClientes);
	    List<Cliente> listaClientesUnicos = new ArrayList<>(clientesUnicosSet);
	    return listaClientesUnicos;
	}


}
