<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="fragmentos/VerificarSesion.jspf"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Autorización de Préstamos</title>
    <!-- Bootstrap-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" xintegrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
    <link rel="StyleSheet" href="Styles.css" type="text/css"/>
    <!-- Favicon -->
    <link rel="icon" href="assets/bank.png" type="image/png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    

    <style>
        /* General styles and main container */
        body {
            background-color: var(--body-color);
            font-family: "Inter", sans-serif;
            color: var(--text-color);
            background-image: url('assets/bg1.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	flex: 1;
        }
        .main-content {
            padding-top: 30px;
            padding-bottom: 50px;
            min-height: calc(100vh - 120px); 
        }
        .card-custom {
            background-color: var(--second-color);
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            padding: 20px;
        }

        /* Section header */
        .section-header-custom {
            background-color: var(--primary-color);
            color: var(--second-color);
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 30px;
            text-align: center;
            display: flex; 
            justify-content: space-between;
            align-items: center;
        }
        .section-header-custom h1 {
            margin-bottom: 0;
            color: var(--second-color); 
            width: 100%;
        }

        /* Filter controls */
        .filter-controls {
            display: flex;
            align-items: center;
            gap: 10px; 
            margin-bottom: 20px;
        }
        .filter-controls .form-select {
            max-width: 250px; 
            border-radius: 5px;
            border: 1px solid var(--btn-color); 
            padding: 8px 12px;
        }
        .filter-controls .btn {
             border-radius: 5px;
             padding: 8px 15px;
             font-weight: 500;
        }

        /* Table */
        .table th {
            background-color: var(--primary-alpha-color);
            color: var(--second-color);
            vertical-align: middle;
            text-align: center;
        }
        .table td {
            vertical-align: middle;
            text-align: center;
            color: var(--text-color);
        }
        .table tbody tr:nth-child(even) {
            background-color: #f8f9fa; 
        }
        .table-responsive {
            margin-top: 20px;
            border-radius: 8px;
            overflow: hidden; 
        }

        /* Status badges */
        .status-badge {
            display: inline-block;
            padding: .35em .65em;
            font-size: .8em; 
            font-weight: 700;
            line-height: 1;
            text-align: center;
            white-space: nowrap;
            vertical-align: baseline;
            border-radius: .375rem; 
            color: white; 
            min-width: 80px; 
        }
        /* Badge colors based on your example */
        /* Adapted to authorization states */
        .status-badge.PENDIENTE_APROBACION { background-color: #ffc107; color: #212529; } 
        .status-badge.ACTIVO { background-color: #28a745; } 
        .status-badge.RECHAZADO { background-color: #dc3545; } 
        .status-badge.FINALIZADO { background-color: #007bff; } 


        .btn-action-approve {
            background-color: #28a745; 
            border-color: #28a745;
            color: white;
            border-radius: 5px;
            padding: 5px 10px;
            font-size: 0.9em;
            transition: background-color 0.3s ease, transform 0.2s ease;
            margin-right: 5px;
        }
        .btn-action-approve:hover {
            background-color: #218838;
            border-color: #1e7e34;
            transform: translateY(-1px);
            color: white;
        }
        .btn-action-disapprove {
            background-color: #dc3545; /* Red */
            border-color: #dc3545;
            color: white;
            border-radius: 5px;
            padding: 5px 10px;
            font-size: 0.9em;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .btn-action-disapprove:hover {
            background-color: #c82333;
            border-color: #bd2130;
            transform: translateY(-1px);
            color: white;
        }

        .pagination .page-item.active .page-link {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            color: var(--second-color);
        }
        .pagination .page-link {
            color: var(--primary-color);
            border: 1px solid var(--primary-alpha-color);
        }
        .pagination .page-link:hover {
            color: var(--second-color);
            background-color: var(--primary-alpha-color);
            border-color: var(--primary-color);
        }
    </style>
</head>
<body>
    <jsp:include page="Nav.jsp"/>

    <div class="container main-content">
        <div class="section-header-custom">
            <h1>Autorización de Préstamos</h1>
        </div>

        <div class="card-custom">
            <form action="AutorizacionesPrestamosServlet" method="get" class="filter-controls">
                <label for="filtroEstado" class="form-label mb-0">Filtrar por Estado:</label>
                <select class="form-select" id="filtroEstado" name="estado">
                    <option value="PENDIENTE_APROBACION">Pendiente de aprobación</option>
                    <option value="ACTIVO">Activo</option>
                    <option value="RECHAZADO" >Rechazado</option>
                    <option value="FINALIZADO">Finalizado</option>
                    <option value="TODOS">Todos los estados</option>
                </select>
                <button type="submit" class="btn btn-primary">Aplicar Filtro</button>
                <button type="button" class="btn btn-secondary" name="btnLimpiarFiltro"><i class="fas fa-times"></i> Limpiar Filtro</button>
            </form>

             <div class="table-responsive">
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>ID Préstamo</th>
                            <th>Fecha de Pedido</th>
                            <th>Monto Solicitado</th>
                            <th>Monto Cuota Mensual</th>
                            <th>Cant. Cuotas Totales</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                            <th>Fecha de Aprobación</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1001</td>
                            <td>01/05/2025</td>
                            <td>$10000.00</td>
                            <td>$250.00</td>
                            <td>48</td>
                            <td>
                                <span class="status-badge PENDIENTE_APROBACION">PENDIENTE DE APROBACIÓN</span>
                            </td>
                            <td>
                                <a href="#" class="btn btn-sm btn-action-approve">Aprobar</a>
                                <a href="#" class="btn btn-sm btn-action-disapprove">Desaprobar</a>
                            </td>
                            <td></td> </tr>
                        <tr>
                            <td>1002</td>
                            <td>15/04/2025</td>
                            <td>$5000.00</td>
                            <td>$125.00</td>
                            <td>24</td>
                            <td>
                                <span class="status-badge ACTIVO">ACTIVO</span>
                            </td>
                            <td></td> <td>20/04/2025</td> </tr>
                        <tr>
                            <td>1003</td>
                            <td>01/03/2024</td>
                            <td>$2000.00</td>
                            <td>$100.00</td>
                            <td>20</td>
                            <td>
                                <span class="status-badge FINALIZADO">FINALIZADO</span>
                            </td>
                            <td></td> <td>05/03/2024</td> </tr>
                        <tr>
                            <td>1004</td>
                            <td>10/06/2025</td>
                            <td>$7500.00</td>
                            <td>$180.00</td>
                            <td>36</td>
                            <td>
                                <span class="status-badge RECHAZADO">RECHAZADO</span>
                            </td>
                            <td></td> <td>12/06/2025</td> </tr>
                        </tbody>
                </table>
            </div>
        </div>
    </div>


			<nav aria-label="Navegación de páginas"
				class="pagination-container d-flex justify-content-center">
				<ul class="pagination">
					<li class="page-item disabled"><span class="page-link">&laquo;</span>
					</li>
					<li class="page-item active"><a class="page-link" href="#">1</a></li>
					<li class="page-item"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
			</nav>

    <jsp:include page="Footer.html"/>
<!--      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script> 
Se deja comentado porque sino genera conflicto con la declaracion que tiene el nav y no funcionan los dropdowns-->
</body>
</html>