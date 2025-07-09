package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.ClienteImpl;
import entidad.Cliente;
import negocioImpl.negocioClienteImpl;

@WebServlet("/listarClientes")
public class ListarClientesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ListarClientesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        negocioClienteImpl negocioClienteImpl = new negocioClienteImpl();
        List<Cliente> clientesList = negocioClienteImpl.ReadAll(); 
        request.setAttribute("listaUsuarios", clientesList);
        request.getRequestDispatcher("ListaUser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Si estás usando el botón en el JSP, lo redirigimos al GET
        doGet(request, response);
    }
}

