<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="fragmentos/VerificarSesion.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nueva Transferencia - Novabank</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
 <link rel="stylesheet" href="Style.css"/>
</head>
<body>

<jsp:include page="Nav.jsp"/> 


  <main class="container mt-5">
    <div class="row justify-content-center align-items-center">
      <div class="col-lg-8">
        <div class="card p-4">
          <h3 class="fw mb-4 text-center text-primary"><i class="bi bi-arrow-left-right"></i> Nueva Transferencia </h3>

          <form>
            <div class="mb-3">
              <label for="cuentaOrigen" class="form-label text-primary"><i class="bi bi-wallet2 me-1"></i>Cuenta Origen</label>
              <select class="form-select" id="cuentaOrigen" required>
                <option selected disabled value="">Seleccionar cuenta</option>
                <option>Cuenta Corriente - $12.000,00</option>
                <option>Caja de Ahorro - $8.450,00</option>
              </select>
            </div>

            <div class="mb-3">
              <label for="cbu" class="form-label text-primary"><i class="bi bi-upc-scan me-1"></i>CBU/CVU de destino</label>
              <input type="text" class="form-control" id="cbu" placeholder="Ingrese CBU o CVU" required>
            </div>

            <div class="mb-3">
              <label for="monto" class="form-label text-primary"><i class="bi bi-cash-coin me-1"></i>Monto a transferir ($)</label>
              <input type="number" class="form-control" id="monto" placeholder="0.00" required>
            </div>

            <div class="mb-3">
              <label for="detalle" class="form-label text-primary"><i class="bi bi-pencil-square me-1"></i>Detalle</label>
              <textarea class="form-control" id="detalle" rows="3" placeholder="Motivo de la transferencia"></textarea>
            </div>

            <div class="d-flex justify-content-end gap-2">
              <button type="reset" class="btn btn-secondary"><i class="bi bi-x-circle me-1"></i>Cancelar</button>
              <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle me-1"></i>Confirmar Transferencia</button>
            </div>
          </form>

        </div>
      </div>
    </div>
  </main>
<jsp:include page="Footer.html" />
</body>
</html>