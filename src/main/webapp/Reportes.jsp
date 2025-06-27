<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="fragmentos/VerificarSesion.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reportes</title>
</head>
<body>
	<jsp:include page="Nav.jsp"/>
	
	<div class="container mt-4">
	    <!-- Tarjetas con información -->
	    <section class="row">
	    	<article class="col-12 text-center mb-4">
				<h1 class="fw text-primary">
					Reportes
				</h1>
			</article>
	    	
	        <article class="col-md-4">
	            <div class="card text-bg-primary mb-3">
	                <div class="card-header">NN</div>
	                <div class="card-body">
	                    <h5 class="card-title">1,200</h5>
	                    <p class="card-text">NN.</p>
	                </div>
	            </div>
	        </article>
	        <article class="col-md-4">
	            <div class="card text-bg-success mb-3">
	                <div class="card-header">NN</div>
	                <div class="card-body">
	                    <h5 class="card-title">25,000,000</h5>
	                    <p class="card-text">NN.</p>
	                </div>
	            </div>
	        </article>
	        <article class="col-md-4">
	            <div class="card text-bg-warning mb-3">
	                <div class="card-header">NN</div>
	                <div class="card-body">
	                    <h5 class="card-title">350</h5>
	                    <p class="card-text">NN.</p>
	                </div>
	            </div>
	        </article>
	    </section>
	
	    <!-- Gráficos -->
	    <div class="row">
	        <div class="col-12">
	            <div class="card">
	                <div class="card-header">NN</div>
	                <div class="card-body">
	                    <canvas id="barChart"></canvas>
	                </div>
	            </div>
	        </div>
	        <div class="col-md-6 mt-3 mb-3">
	            <div class="card">
	                <div class="card-header">NN</div>
	                <div class="card-body">
	                    <canvas id="pieChart"></canvas>
	                </div>
	            </div>
	        </div>
	        <div class="col-md-6 mt-3 mb-3">
	            <div class="card">
	                <div class="card-header">NN</div>
	                <div class="card-body">
	                    <canvas id="pieChart2"></canvas>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

<!-- Script para gráficos con Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Gráfico de Barras
    var ctxBar = document.getElementById('barChart').getContext('2d');
    var barChart = new Chart(ctxBar, {
        type: 'bar',
        data: {
            labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Nobiembre', 'Diciembre'],
            datasets: [{
                label: 'NN',
                data: [3456, 7890, 14567, 2345, 19876, 5678, 12345, 9876, 15000, 2001, 17500, 3000],
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
            }
        }
    });

    // Gráfico de Torta
    var ctxPie = document.getElementById('pieChart').getContext('2d');
    var pieChart = new Chart(ctxPie, {
        type: 'pie',
        data: {
            labels: ['NN', 'NN'],
            datasets: [{
                data: [30, 70],
                backgroundColor: ['#FFCE56', '#4BC0C0']
            }]
        },
        options: {
            responsive: true
        }
    });
    
    var ctxPie = document.getElementById('pieChart2').getContext('2d');
    var pieChart = new Chart(ctxPie, {
        type: 'pie',
        data: {
            labels: ['NN', 'NN'],
            datasets: [{
                data: [85, 15],
                backgroundColor: ['#FF6384', '#36A2EB']
            }]
        },
        options: {
            responsive: true
        }
    });
</script>
	
</body>
</html>