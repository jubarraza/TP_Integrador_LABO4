<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.List, entidad.Cuenta, entidad.Movimiento" %>
<%@ include file="fragmentos/VerificarSesion.jspf" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Home Banking - Cuentas y Movimientos</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous" />
    <!-- Bootstrap Iconos -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Hoja de estilos -->
    <link rel="stylesheet" href="Styles.css" type="text/css" />
    <!-- Favicon  -->
    <link rel="icon" href="assets/bank.png" type="image/png" />

    <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            background-image: url('assets/bg1.jpg');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }
        .container {
            flex: 1;
            background-color: rgba(255, 255, 255, 0.9);
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.3);
            margin-top: 2rem;
            margin-bottom: 2rem;
        }
        h2 {
            font-weight: 700;
            margin-bottom: 1.5rem;
        }
        table th, table td {
            vertical-align: middle !important;
        }
    </style>
</head>
<body>

<div class="container">

    <h2 class="text-center text-primary">
        <i class="bi bi-bank"></i> Cuentas
    </h2>

    <table class="table table-bordered table-striped table-hover align-middle text-center">
        <thead class="bg-primary text-white">
            <tr>
                <th><i class="bi bi-credit-card"></i> Número</th>
                <th><i class="bi bi-card-list"></i> Tipo</th>
                <th><i class="bi bi-currency-dollar"></i> Saldo</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
                if (cuentas != null && !cuentas.isEmpty()) {
                    for (Cuenta c : cuentas) {
            %>
                <tr>
                    <td><%= c.getNumDeCuenta() %></td>
                    <td><%= c.getTipoCuenta() %></td>
                    <td>$ <%= String.format("%.2f", c.getSaldo() != null ? c.getSaldo() : 0.0) %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="3">No hay cuentas para mostrar</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <!-- Formulario para filtrar movimientos -->
    <form method="post" action="MovimientoServlet" class="mb-4">
        <input type="hidden" name="operacion" value="Movimientos" />
        <label for="cuentaSelect">Filtrar por cuenta:</label>
        <select name="cuenta" id="cuentaSelect" class="form-select w-auto d-inline-block">
            <option value="">-- Seleccione una cuenta --</option>
            <% 
                if (cuentas != null) {
                    for (Cuenta c : cuentas) { 
                        String cuentaSel = (String) request.getAttribute("cuentaSeleccionada");
            %>
                <option value="<%= c.getNumDeCuenta() %>" 
                    <%= (cuentaSel != null && cuentaSel.equals(c.getNumDeCuenta())) ? "selected" : "" %>>
                    <%= c.getNumDeCuenta() %> - <%= c.getTipoCuenta() %>
                </option>
            <% 
                    } 
                } 
            %>
        </select>
        <button type="submit" class="btn btn-primary">Buscar</button>
    </form>

    <h2 class="text-center text-success mt-5">
        <i class="bi bi-arrow-repeat"></i> Movimientos
    </h2>
    <table class="table table-bordered table-striped table-hover align-middle text-center">
        <thead class="bg-success text-white">
            <tr>
                <th><i class="bi bi-calendar-event"></i> Fecha</th>
                <th><i class="bi bi-journal-text"></i> Detalle</th>
                <th><i class="bi bi-cash-stack"></i> Monto</th>
                <th><i class="bi bi-tag"></i> Tipo</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
                if (movimientos != null && !movimientos.isEmpty()) {
                    for (Movimiento m : movimientos) {
            %>
                <tr>
                    <td><%= m.getFecha() != null ? m.getFecha() : "" %></td>
                    <td><%= m.getDetalle() != null ? m.getDetalle() : "" %></td>
                    <td>$ <%= String.format("%.2f", m.getImporte() != null ? m.getImporte() : 0.0) %></td>
                    <td><%= m.getTipoMovimiento() != null ? m.getTipoMovimiento() : "" %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="4">No hay movimientos para mostrar</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <!-- Paginación + Buscador -->
    <div class="d-flex justify-content-between align-items-center mt-3">

        <!-- Paginación -->
        <nav>
            <ul class="pagination pagination-sm mb-0">
                <li class="page-item disabled"><a class="page-link" href="#">&laquo;</a></li>
                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
            </ul>
        </nav>

        <!-- Botón de búsqueda -->
        <div class="input-group" style="max-width: 250px;">
            <span class="input-group-text">Buscar</span>
            <input id="searchDetalle" type="text" class="form-control" placeholder="Detalle" />
            <button id="btnBuscar" class="btn btn-outline-secondary" type="button">
                <i class="bi bi-search"></i>
            </button>
        </div>

    </div>

</div>

<!-- Bootstrap JS y Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
        crossorigin="anonymous"></script>


</body>
</html>
