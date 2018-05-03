<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1 style="text-align: center;">Actualizar Producto (DATE)</h1>
	<form name="form1" action="ControladorProductos2" method="get" >
	
	<input type="hidden" name="instruccion" value="actualizarBBDD"/>
	<table>
		<tr>
			<td>
				<label for="CArt">Código Artículo</label>
			</td>
			<td>
				<input type="text" name="CArt" id="CArt" readonly="readonly" value="${ProductoActualizar.cArt}"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="seccion">Sección</label>
			</td>
			<td>
				<input type="text" name="seccion" id="seccion" value="${ProductoActualizar.seccion}"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="NArt">Nombre Artículo</label>
			</td>
			<td>
				<input type="text" name="NArt" id="NArt" value="${ProductoActualizar.nArt}"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="fecha">Fecha</label>
			</td>
			<td>
				<input type="text" name="fecha" id="fecha" value="${ProductoActualizar.fecha}"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="precio">Precio</label>
			</td>
			<td>
				<input type="text" name="precio" id="precio" value="${ProductoActualizar.precio}"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="importado">Importado</label>
			</td>
			<td>
				<input type="text" name="importado" id="importado" value="${ProductoActualizar.importado}"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="POrig">País de Origen</label>
			</td>
			<td>
				<input type="text" name="POrig" id="POrig" value="${ProductoActualizar.pOrig}"/>
			</td>
		</tr>
		<tr>
			<td><input type="submit" name="envio" id="envio" value="Enviar"/>
			<td><input type="reset" name="borrar" id="borrar" value="Restablecer"/>
		</tr>	
	</table>
	</form>

</body>
</html>