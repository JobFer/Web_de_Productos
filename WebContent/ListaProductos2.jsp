<%@ page language="java" contentType="text/html; charset=ISO-8859-1 " pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, com.pildorasinformaticas.productos.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista Productos 2</title>

<style type="text/css">
	th{
		border-bottom: solid #F00 1px;
	}
</style>

<%
	List<Productos> losProductos = (List<Productos>) request.getAttribute("LISTAPRODUCTOS");
%>

</head>
<body>

<!-- 	Forma del video 252 -->
<%-- 	<%=losProductos %> --%>

<!-- 	Forma del video 253 -->
	<table>
		<tr>
			<th>Código Artículo</th>
			<th>Sección</th>
			<th>Nombre Artículo</th>
			<th>Fecha</th>
			<th>Precio</th>
			<th>Importado</th>
			<th>País de Orígen</th>
		</tr>
		<%for(Productos p:losProductos){%>
			<tr>
				<td><%=p.getcArt()%></td>
				<td><%=p.getSeccion()%></td>	
				<td><%=p.getnArt()%></td>	
				<td><%=p.getFecha()%></td>	
				<td><%=p.getPrecio()%></td>	
				<td><%=p.getImportado()%></td>	
				<td><%=p.getpOrig()%></td>				
			</tr>
		<%}%>
	</table>

</body>
</html>