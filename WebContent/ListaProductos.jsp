<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista Productos</title>

<style type="text/css">
	th{
		font-size: 1.2em;
		font-weight: bold;
		color: #FFFFFF;
		background-color: #08088A;
	}
	td{
		text-align: center;
		background-color: #5882FA;
	}
	table{
		float: left;
	}
	#contenedorBoton{
		margin-left: 800px;
	}
</style>

</head>
<body>
	
	<c:if test="${excepcion!=null}">${excepcion}</c:if>
	<c:if test="${excepcion==null}">SIN ERRORES</c:if>

	<table>
		<tr>
			<th>Código Artículo</th>
			<th>Sección</th>
			<th>Nombre Artículo</th>
			<th>Fecha</th>
			<th>Precio</th>
			<th>Importado</th>
			<th>País de Orígen</th>
			<th>Acción</th>
		</tr>
		
		<c:forEach var="prod" items="${LISTAPRODUCTOS}">
		
			<c:url var="linkTemp" value="ControladorProductos">
				<c:param name="instruccion" value="cargar"></c:param>
				<c:param name="CArticulo" value="${prod.cArt}"></c:param>
			</c:url>

			<c:url var="linkTempEliminar" value="ControladorProductos">
				<c:param name="instruccion" value="eliminar"></c:param>
				<c:param name="CArticulo" value="${prod.cArt}"></c:param>
			</c:url>
					
			<tr>
<%-- 				<td>${prod.getcArt()}</td> --%>
<%-- 				<td>${prod.getSeccion()}</td> --%>
<%-- 				<td>${prod.getnArt()}</td> --%>
<%-- 				<td>${prod.getFecha()}</td> --%>
<%-- 				<td>${prod.getPrecio()}</td> --%>
<%-- 				<td>${prod.getImportado()}</td> --%>
<%-- 				<td>${prod.getpOrig()}</td> --%>
			
				<td>${prod.cArt}</td>
				<td>${prod.seccion}</td>
				<td>${prod.nArt}</td>
				<td>${prod.fecha}</td>
				<td>${prod.precio}</td>
				<td>${prod.importado}</td>
				<td>${prod.pOrig}</td>
				
				<td><a href="${linkTemp}">Actualizar</a>&nbsp;<a href="${linkTempEliminar}">Eliminar</a></td>
<%-- 			<td><a href="ControladorProductos?instruccion=cargar&CArticulo=${prod.cArt}">Actualizar</a></td> --%>				
							
			</tr>
			 
		</c:forEach>
	</table>
	
	<c:url var="linkActualizar" value="ControladorProductos">
		<c:param name="instruccion" value="listar"></c:param>
		<c:param name="excepcion" value="null"></c:param>
	</c:url>	

	<div id="contenedorBoton">
		<input type="button" value="Insertar Registro" 
		onclick="window.location.href='inserta_producto.jsp'"/>
		<br/> 
		<a href="${linkActualizar}">Refrescar</a>
	</div>
	
</body>
</html>