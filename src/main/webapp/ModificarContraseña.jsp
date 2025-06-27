<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Cambiar Constraseña - Novabank</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="Style.css"/>
</head>
<body>

<jsp:include page="Nav.jsp"/> 

<div class="bg-gradient-custom min-vh-100 d-flex justify-content-center align-items-center py-5">
  <div class="container">
    <div class="row align-items-center">
      
      <!-- Imagen a la izquierda -->
      <div class="col-md-6 text-center mb-4 mb-md-0">
        <img src="assets/Pass1.png" alt="Password Novabank" class="img-left img-fluid">
      </div>

      <!-- Formulario a la derecha -->
      <div class="col-md-6">
        <div class="form-container">

          <div class="text-center mb-4">
            <h2 class="fw-bold text-primary">¿Querés cambiar tu contraseña?</h2>
            <p class="text-muted">Es importante mantener tus datos seguros</p>
          </div>

          <form class="row g-3">

            <div class="col-md-6">
              <label for="txtContraseña" class="form-label">Ingresa nueva Contraseña</label>
              <input id="txtContraseña" class="form-control rounded-pill bg-light border-0" type="password" placeholder="Mínimo 8 caracteres" />
            </div>

            <div class="col-md-6">
              <label for="txtContraseñaC" class="form-label">Confirmar Contraseña</label>
              <input id="txtContraseñaC" class="form-control rounded-pill bg-light border-0" type="password" placeholder="Nueva Contraseña" />
            </div>

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
<jsp:include page="Footer.html"/>
</body>
</html>
