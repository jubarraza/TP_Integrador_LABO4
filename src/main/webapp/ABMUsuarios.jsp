<%@page import="java.util.List" %>
<%@page import="entidad.Localidad" %>
<%@page import="entidad.Provincia" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Registro de Usuario - Novabank</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="Style.css"/>
</head>
<body>
<jsp:include page="Nav.jsp" />

<div class="bg-gradient-custom min-vh-100 d-flex justify-content-center align-items-center py-5">
  <div class="container">
    <div class="row align-items-center">
      
      <!-- Imagen a la izquierda -->
      <div class="col-md-6 text-center mb-4 mb-md-0">
        <img src="assets/registroNuevoCliente.png" alt="Registro Novabank" class="img-left img-fluid">
      </div>

      <!-- Formulario a la derecha -->
      <div class="col-md-6">
        <div class="form-container">

          <div class="text-center mb-4">
            <h2 class="fw-bold text-primary">Crea una nueva cuenta</h2>
            <p class="text-muted">Completa los campos para dar de alta un usuario</p>
          </div>

          <form action="InsertarUserClienteServlet" method="post" class="row g-3" onsubmit="return validarFormulario();">
			
			
            <div class="col-md-6">
              <label for="txtCUIL" class="form-label">CUIL</label>
              <input name="txtCUIL" class="form-control rounded-pill bg-light border-0" type="text" placeholder="Ingrese CUIL" />
            </div>

            <div class="col-md-6">
              <label for="txtNombreUsuario" class="form-label">Nombre</label>
              <input name="txtNombreUsuario" class="form-control rounded-pill bg-light border-0" type="text" placeholder="Nombre completo" />
            </div>

            <div class="col-md-6">
              <label for="txtApellidoUsuario" class="form-label">Apellido</label>
              <input name="txtApellidoUsuario" class="form-control rounded-pill bg-light border-0" type="text" placeholder="Apellido" />
            </div>

            <div class="col-md-6">
              <label for="ddlSexo" class="form-label">Sexo</label>
              <select name="ddlSexo"  id="ddlSexo" class="form-select rounded-pill bg-light border-0" required>
                <option value="" disabled selected>Seleccione</option>
                <option value="F">Femenino</option>
                <option value="M">Masculino</option>
              </select>
            </div>

            <div class="col-md-6">
              <label for="txtNacionalidad" class="form-label">Nacionalidad</label>
              <input name="txtNacionalidad" class="form-control rounded-pill bg-light border-0" type="text" placeholder="Nacionalidad" />
            </div>

            <div class="col-md-6">
              <label for="txtFechaNac" class="form-label">Fecha de Nacimiento</label>
              <input name="txtFechaNac" class="form-control rounded-pill bg-light border-0" type="date" />
            </div>

            <div class="col-md-6">
              <label for="txtDomicilio" class="form-label">Domicilio</label>
              <input name="txtDomicilio" class="form-control rounded-pill bg-light border-0" type="text" placeholder="Dirección" />
            </div>

			
            <div class="col-md-6">
              <label for="txtLocalidad" class="form-label">Localidad</label>
			<select name="Localidad" class="form-select">
		    <%
		        List<Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");
		        if (localidades != null && !localidades.isEmpty()) {
		            for (Localidad local : localidades) {
		    %>
		                <option value="<%= local.getIdLocalidad() %>">
		                	<%= local.getDescripcion() %>
		                </option>
		    <%
		            }
		        } else {
		    %>
		        <option value="">No hay Localidades disponibles</option>
		    <%
		        }
		    %>
		</select>
            </div>

            <div class="col-md-6">
              <label for="txtProvincia" class="form-label">Provincia</label>
			<select name="Provincia" class="form-select">
		    <%
		        List<Provincia> provincias = (List<Provincia>) request.getAttribute("provincias");
		        if (provincias != null && !provincias.isEmpty()) {
		            for (Provincia provincia : provincias) {
		    %>
		                <option value="<%= provincia.getIdProvincia() %>">
		                	<%= provincia.getDescripcion() %>
		                </option>
		    <%
		            }
		        } else {
		    %>
		        <option value="">No hay provincias disponibles</option>
		    <%
		        }
		    %>
		</select>
            </div>

            <div class="col-md-6">
              <label for="txtEmail" class="form-label">Correo Electrónico</label>
              <input name="txtEmail" class="form-control rounded-pill bg-light border-0" type="email" placeholder="nombre@ejemplo.com" />
            </div>

            <div class="col-md-6">
              <label for="txtTelefono" class="form-label">Teléfono</label>
              <input name="txtTelefono" class="form-control rounded-pill bg-light border-0" type="tel" placeholder="Teléfono" />
            </div>
            
             <div class="col-md-6">
              <label for="txtDNI" class="form-label">DNI</label>
              <input id="txtDNI" name="txtDNI" class="form-control rounded-pill bg-light border-0" type="text" placeholder="Ingrese DNI" />
            </div>
            
 			<div class="border row g-3 mt-3">
 			  <div class="col-md-6">
              <label for="txtUsuario" class="form-label">Usuario</label>
              <input name="txtUsuario" class="form-control rounded-pill bg-light border-0" type="text" placeholder="Nombre de usuario:" />
            </div>
			
            <div class="col-md-6">
              <label for="txtContrasenia" class="form-label">Contraseña</label>
              <input name="txtContrasenia" class="form-control rounded-pill bg-light border-0" type="password" placeholder="Mínimo 8 caracteres" />
            </div>

            <div class="col-md-6">
              <label for="txtContraseniaC" class="form-label">Confirmar Contraseña</label>
              <input name="txtContraseniaC" class="form-control rounded-pill bg-light border-0" type="password" placeholder="Confirmar contraseña" />
            </div>
 			</div>

			<div id="errorMensaje" class="alert alert-danger d-none" role="alert"></div>
			
            <!-- Botones -->
            <div class="col-12 d-grid gap-2 mt-3">
              <button type="submit" class="btn btn-primary btn-lg rounded-pill">Guardar</button>
              <button type="reset" class="btn btn-outline-secondary btn-lg rounded-pill">Cancelar</button>
            </div>

          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<%
	    String mensajeExito = (String) request.getAttribute("mensajeExito");
	    String mensajeError = (String) request.getAttribute("mensajeError");
    	if (mensajeExito != null) {
	%>
    	<%= mensajeExito %>
	<%
    	} else if (mensajeError != null) {
	%>
    	<%= mensajeError %>
	<%
    	}
	%>

<script>
  function mostrarError(mensaje) {
    const errorDiv = document.getElementById("errorMensaje");
    errorDiv.textContent = mensaje;
    errorDiv.classList.remove("d-none");
    errorDiv.classList.add("show");
    window.scrollTo({ top: errorDiv.offsetTop - 20, behavior: 'smooth' });
  }

  function ocultarError() {
    const errorDiv = document.getElementById("errorMensaje");
    errorDiv.classList.add("d-none");
    errorDiv.textContent = "";
  }

  function validarFormulario() {
    ocultarError(); // Oculta errores anteriores

    const dni = document.getElementById("txtDNI").value.trim();
    const cuil = document.getElementsByName("txtCUIL")[0].value.trim();
    const pass = document.getElementsByName("txtContrasenia")[0].value;
    const passC = document.getElementsByName("txtContraseniaC")[0].value;
    const email = document.getElementsByName("txtEmail")[0].value.trim();
    const sexo = document.getElementById("ddlSexo").value;
    const provincia = document.getElementsByName("Provincia")[0].value;
    const localidad = document.getElementsByName("Localidad")[0].value;

    if (dni === "" || !/^\d+$/.test(dni)) {
      mostrarError("Por favor, ingrese un DNI válido (solo números).");
      return false;
    }

    if (cuil === "" || !/^\d{10,11}$/.test(cuil)) {
      mostrarError("Por favor, ingrese un CUIL válido (10 u 11 números).");
      return false;
    }

    if (pass.length < 8) {
      mostrarError("La contraseña debe tener al menos 8 caracteres.");
      return false;
    }

    if (pass !== passC) {
      mostrarError("Las contraseñas no coinciden.");
      return false;
    }

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!regexEmail.test(email)) {
      mostrarError("Ingrese un correo electrónico válido.");
      return false;
    }

    if (sexo === "") {
      mostrarError("Seleccione un sexo.");
      return false;
    }

    if (provincia === "") {
      mostrarError("Seleccione una provincia.");
      return false;
    }

    if (localidad === "") {
      mostrarError("Seleccione una localidad.");
      return false;
    }

    return true;
  }
</script>
<jsp:include page="Footer.html" />

</body>
</html>

