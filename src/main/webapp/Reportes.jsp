<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="fragmentos/VerificarSesion.jspf"%>
<html>
<head>
<meta charset="UTF-8">
<title>Reportes</title>

<script src="https://cdn.tailwindcss.com"></script>
<style>
    .report-btn-primary, .report-btn-secondary {
        color: white;
        padding: 0.5rem 1.5rem;
        border-radius: 0.375rem;
        font-weight: 600;
        transition: background-color 0.2s ease-in-out;
        display: inline-flex;
        justify-content: center;
        align-items: center;
        border: none;
    }

    .report-btn-primary {
        background-color: #3B82F6;
    }
    .report-btn-primary:hover {
        background-color: #2563EB;
    }

    .report-btn-secondary {
        background-color: #6B7280;
    }
    .report-btn-secondary:hover {
        background-color: #4B5563;
    }

    .report-error-message {
        color: #EF4444;
        font-size: 0.875rem;
        margin-top: 0.5rem;
    }

    .report-bg-primary {
        background-color: #3B82F6 !important;
        color: white !important;
    }
    .report-bg-success {
        background-color: #22C55E !important;
        color: white !important;
    }
    .report-bg-warning {
        background-color: #FBBF24 !important;
        color: white !important;
    }
    .report-bg-danger {
        background-color: #EF4444 !important;
        color: white !important;
    }
    
    .report-card {
        border-radius: 0.5rem;
        box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        border: 1px solid rgba(0,0,0,0.1);
    }
    .report-card-header {
        padding: 1rem 1.5rem;
        border-bottom: 1px solid rgba(0,0,0,0.1);
        font-weight: 600;
        background-color: rgba(0,0,0,0.03); 
    }
    .report-card-body {
        padding: 1.5rem;
    }

    @media (min-width: 768px) {
        .report-col-md-5 {
            width: 41.666667%; 
        }
        .report-col-md-2 {
            width: 16.666667%; 
        }
    }
</style>

</head>
<body>
	<jsp:include page="Nav.jsp" />

	<div class="container mx-auto px-4 mt-4">
	    <section class="flex flex-wrap -mx-2">
	    	<article class="w-full text-center mb-4">
				<h1 class="font-semibold text-blue-600 text-3xl">
					Reportes
				</h1>
			</article>			
			<form action="ReportesServlet" method="POST" id="filterForm" class="w-full">
			<%String fechaInicio = (String) request.getAttribute("fechaInicio"); %>
			<%String fechaHasta = (String) request.getAttribute("fechaHasta"); %>
			<section class="w-full px-2 mt-4 mb-8">
                <article class="w-full">
                    <div class="bg-white p-6 rounded-lg shadow-md">
                        <div class="flex flex-wrap -mx-2 items-end"> 
                                <div class="w-full md:w-5/12 px-2 mb-4 md:mb-0 flex flex-col justify-end">
        							<label for="IdfechaInicio" class="block text-gray-700 text-sm font-bold mb-2">Fecha desde:</label>
        							<input type="date" class="form-input w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              								 id="IdfechaInicio" name="fechaInicio" value="<%= fechaInicio != null ? fechaInicio : "" %>">
   								</div>
    							<div class="w-full md:w-5/12 px-2 mb-4 md:mb-0 flex flex-col justify-end">
        							<label for="IdfechaHasta" class="block text-gray-700 text-sm font-bold mb-2">Fecha Hasta:</label>
        							<input type="date" class="form-input w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
               								id="IdfechaHasta" name="fechaHasta" value="<%= fechaHasta != null ? fechaHasta : "" %>">
    							</div>
                                <div class="w-full md:w-2/12 px-2 flex flex-col justify-end">
                                    <div class="block text-gray-700 text-sm font-bold mb-2 invisible">Placeholder</div>
                                    <div class="flex space-x-2">
                                        <button type="submit" name="btnfiltrar" id="Bfiltro" class="report-btn-primary flex-grow">Filtrar</button>
                                        <button type="submit" name="btnLimpiar" id="Blimpiar" class="report-btn-secondary flex-grow">Limpiar</button>
                                    </div>
    							</div>
                        </div>

                        <%boolean verificarInput = (boolean) request.getAttribute("VerificarImput"); 
                        if(!verificarInput)
                        { 
                        %>	                        
                        	<div id="dateError" class="report-error-message mt-2">
                            "Verifique las fechas ingresadas"
                            </div>
                            <% }%> 
                    </div>
                </article>
            </section>
            </form>

	    	<%int cuentasActivas = (int) request.getAttribute("cuentasActivas"); %>	    	
	    	<%float porcCuentasActivas = (float) request.getAttribute("porcCuentasActivas"); %>
	    	<%float porcCuentasInactivas = (float) request.getAttribute("porcCuentasInactivas"); %>
	    	<%int cuentasInactivadas =  (int) request.getAttribute("cuentasInactivadas");%>
	    	<%int clientesActivos = (int) request.getAttribute("clientesActivos"); %>
			<%int cuentasActivadas = (int) request.getAttribute("cuentasActivadas"); %>


	        <article class="w-full md:w-1/4 px-2 mb-4">
	            <div class="report-card report-bg-primary h-full">
	                <div class="report-card-header">Clientes Actuales</div>
	                <div class="report-card-body">
	                    <h5 class="report-card-title text-2xl font-bold"><%= clientesActivos %></h5>
	                    <p class="report-card-text">Total de clientes actuales.</p>
	                </div>
	            </div>
	        </article>
	        <article class="w-full md:w-1/4 px-2 mb-4">
	            <div class="report-card report-bg-success h-full">
	                <div class="report-card-header">Cuentas Activas</div>
	                <div class="report-card-body">
	                    <h5 class="report-card-title text-2xl font-bold"><%= cuentasActivas%></h5>
	                    <p class="report-card-text">Total de cuentas activas</p>
	                </div>
	            </div>
	        </article>
	        <article class="w-full md:w-1/4 px-2 mb-4">
	            <div class="report-card report-bg-warning h-full">
	                <div class="report-card-header">Cuentas activadas</div>
	                <div class="report-card-body">
	                    <h5 class="report-card-title text-2xl font-bold"><%= cuentasActivadas %></h5>
	                    <p class="report-card-text">Total Periodo seleccionado</p>
	                </div>
	            </div>
	        </article>
	        <article class="w-full md:w-1/4 px-2 mb-4">
	             <div class="report-card report-bg-danger h-full"> <div class="report-card-header">Cuentas desactivadas</div>
	                <div class="report-card-body">
	                    <h5 class="report-card-title text-2xl font-bold"><%=cuentasInactivadas %></h5>
	                    <p class="report-card-text">Total Periodo seleccionado</p>
	                </div>
	            </div>
	        </article>
	        
	        
	    </section>

	    <div class="flex flex-wrap -mx-2">
	        <div class="w-full px-2 mb-4">
	            <div class="report-card"> <div class="report-card-header">Altas de Cuentas Mensuales (Pendiente para prestamos)</div> <div class="report-card-body"> <canvas id="barChart"></canvas>
	                </div>
	            </div>
	        </div>
	        <div class="w-full md:w-1/2 px-2 mt-3 mb-4">
	            <div class="report-card h-full"> <div class="report-card-header">Porcentaje de Cuentas (Activas/Inactivas)</div> <div class="report-card-body"> <canvas id="pieChart"></canvas>
	                </div>
	            </div>
	        </div>
	        <div class="w-full md:w-1/2 px-2 mt-3 mb-4">
	            <div class="report-card h-full"> <div class="report-card-header">Porcentaje de prestamos (Activos/Finalizados)</div> <div class="report-card-body"> <canvas id="pieChart2"></canvas>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

<%int[] meses = (int[]) request.getAttribute("meses"); %>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>

<script>
    
    Chart.register(ChartDataLabels);

    var ctxBar = document.getElementById('barChart').getContext('2d');
    var barChart = new Chart(ctxBar, {
        type: 'bar',
        data: {
            labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            datasets: [{
                label: 'Altas de cuentas mensual',
                data: [<%=meses[0]%>, <%=meses[1]%>, <%=meses[2]%>, <%=meses[3]%>, <%=meses[4]%>, <%=meses[5]%>, <%=meses[6]%>, <%=meses[7]%>, <%=meses[8]%>, <%=meses[9]%>, <%=meses[10]%>, <%=meses[11]%>],
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            
            plugins: {
                datalabels: {
                    anchor: 'end', 
                    align: 'top',  
                    formatter: function(value, context) {
                        return value; 
                    },
                    color: '#444', 
                    font: {
                        weight: 'bold', 
                        size: 12        
                    }
                }
            }
            
        }
    });

 
    var ctxPie = document.getElementById('pieChart').getContext('2d');
    var pieChart = new Chart(ctxPie, {
        type: 'pie',
        data: {
            labels: ['Cuentas Activadas', 'Cuentas Inactivadas'],
            datasets: [{
                data: [<%=porcCuentasActivas%>, <%=porcCuentasInactivas%>],
                backgroundColor: ['#76d7c4', '#f1948a']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            let label = context.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed !== null) {
                                label += context.parsed.toFixed(2) + '%';
                            }
                            return label;
                        }
                    }
                },
                
                datalabels: {
                    formatter: (value, ctx) => {
                        let sum = 0;
                        let dataArr = ctx.chart.data.datasets[0].data;
                        dataArr.map(data => {
                            sum += data;
                        });
                        let percentage = (value * 100 / sum).toFixed(2) + "%";
                        return percentage;
                    },
                    color: '#fff', 
                    font: {
                        weight: 'bold',
                        size: 14
                    }
                }
            }
        }
    });

    

    var ctxPie2 = document.getElementById('pieChart2').getContext('2d');
    var pieChart2 = new Chart(ctxPie2, { 
        type: 'pie',
        data: {
            labels: ['Aprobados', 'Rechazados'],
            datasets: [{
                data: [50, 50], 
                backgroundColor: ['#85c1e9', '#8e44ad']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            let label = context.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed !== null) {
                                label += context.parsed.toFixed(2) + '%';
                            }
                            return label;
                        }
                    }
                },
                datalabels: {
                    formatter: (value, ctx) => {
                        let sum = 0;
                        let dataArr = ctx.chart.data.datasets[0].data;
                        dataArr.map(data => {
                            sum += data;
                        });
                        let percentage = (value * 100 / sum).toFixed(2) + "%";
                        return percentage;
                    },
                    color: '#fff',
                    font: {
                        weight: 'bold',
                        size: 14
                    }
                }
            }
        }
    });
</script>

</body>
</html>
