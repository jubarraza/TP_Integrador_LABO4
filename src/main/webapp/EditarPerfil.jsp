<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="entidad.Localidad" %>
<%@ page import="entidad.Cliente" %>

<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7f6; font-family: 'Inter', sans-serif; }
        .form-card { margin-top: 50px; padding: 30px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); border-radius: 15px; background-color: white; }
        .form-label { font-weight: 600; }
    </style>
</head>
<body>
    <%
        Cliente cliente = (Cliente) request.getAttribute("cliente");
        List<Localidad> listaLocalidades = (List<Localidad>) request.getAttribute("listaLocalidades");
        if (cliente == null) {
            cliente = new Cliente(); 
        }
    %>
    <jsp:include page="Nav.jsp" />

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-7">
                <div class="form-card">
                    <h2 class="text-center mb-4">Editar Mis Datos</h2>
                    <form action="perfil" method="post">
                        <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

                        <p><strong>DNI:</strong> <%= cliente.getDni() %></p>
                        <p><strong>Nombre:</strong> <%= cliente.getNombre() %> <%= cliente.getApellido() %></p>
                        <hr>
                        
                        <div class="mb-3">
                            <label for="direccion" class="form-label">Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" value="<%= cliente.getDireccion() %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="text" class="form-control" id="telefono" name="telefono" value="<%= cliente.getTelefono() %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="correo" class="form-label">Correo Electrónico</label>
                            <input type="email" class="form-control" id="correo" name="correo" value="<%= cliente.getCorreo() %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="localidad" class="form-label">Localidad</label>
                            <select class="form-select" id="localidad" name="localidad" required>
                                <option value="">-- Seleccione una localidad --</option>
                                <%
                                    if (listaLocalidades != null) {
                                        for (Localidad loc : listaLocalidades) {
                                            String selected = "";
                                            if (cliente.getLocalidad() != null && loc.getIdLocalidad() == cliente.getLocalidad().getIdLocalidad()) {
                                                selected = "selected";
                                            }
                                            out.println("<option value='" + loc.getIdLocalidad() + "' " + selected + ">" + loc.getDescripcion() + "</option>");
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                            <a href="perfil" class="btn btn-secondary">Cancelar</a>
                            <button type="submit" class="btn btn-success">Guardar Cambios</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="Footer.html" />
</body>
</html>


