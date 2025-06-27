<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="fragmentos/VerificarSesion.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transferencias</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<link rel="stylesheet" href="https://cdn.datatables.net/2.3.2/css/dataTables.dataTables.css" />
  
<script src="https://cdn.datatables.net/2.3.2/js/dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#table_id').DataTable();
	});
</script>

</head>
<body>
	<jsp:include page="Nav.jsp"/>
	 
	<div class="container mb-4 mt-4">
		<section class="row justify-content-center">
		
			<article class="col-12 text-center mb-4">
				<h1 class="fw text-primary">
					Transferencias
				</h1>
			</article>
			
			<article class="col-12">
				<h3 class="text-center">
					¿Querés hacer una nueva transferencia?
				</h3>
			</article>
			
			<article class="d-flex justify-content-center gap-3 col-12 mx-auto m-2">
			    <a href="TransferenciaNueva.jsp" type="button" class="btn btn-primary btn-lg" style="width: 250px; height: 50px;">
			        A un CBU
			    </a>
			    <a href="TransferenciaNueva.jsp" type="button" class="btn btn-primary btn-lg" style="width: 250px; height: 50px;">
			        A una Cuenta Propia
			    </a>
			</article>
			
			
			<article class="col-12">
				<table border="1" id="table_id">
	
				<thead>	<tr>
						<td><b>FECHA</b></td>
						<td><b>DESTINATARIO</b></td>
						<td><b>MONTO</b></td>
						<td><b>ESTADO</b></td>
					</tr>
				</thead>
				
				</table>
			</article>
			
		</section>	
	</div>
	<jsp:include page="Footer.html" />
</body>
</html>