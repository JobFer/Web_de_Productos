package com.pildorasinformaticas.productos2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;


public class ModeloProductos {

	private DataSource origenDatos;

	public ModeloProductos(DataSource origenDatos) {
		this.origenDatos = origenDatos;
	}
	
	public List<Productos> getProductos() throws Exception{
		
		List<Productos> productos = new ArrayList<Productos>(); //Ponemos el generico para que no salga un WARNING
		try(Connection miConexion =  origenDatos.getConnection()) {
//		try {
//			Connection miConexion =  origenDatos.getConnection();
			String miSql = "SELECT * FROM PRODUCTOS";
			//    	String miSql = "SELECT CODIGOARTICULO, SECCION, NOMBREARTICULO, PRECIO,"
			//    			+ " FECHA, DATE_FORMAT(FECHA,'%Y-%m-%d') AS FE, IMPORTADO, PAISDEORIGEN FROM PRODUCTOS";
			Statement miStatement = miConexion.createStatement();
			ResultSet miResultSet = miStatement.executeQuery(miSql);
			
			while (miResultSet.next()) {
				String cArt = miResultSet.getString("CODIGOARTICULO");
				String seccion = miResultSet.getString("SECCION");
				String nArt = miResultSet.getString("NOMBREARTICULO");
				double precio = miResultSet.getDouble("PRECIO");
				
				//String fecha = miResultSet.getString("FE");
				Date fecha = miResultSet.getDate("FECHA");
				
				String importado = miResultSet.getString("IMPORTADO");
				String p_orig = miResultSet.getString("PAISDEORIGEN");
				
				Productos temProd = new Productos(cArt, seccion, nArt, precio, fecha, importado, p_orig);
				productos.add(temProd);
				
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("Error en la BD al LISTAR");
		} 
    	return productos;
	}

	//public void agregarElNuevoProducto(Productos nuevoProducto) throws ParseException {
	public void agregarElNuevoProducto(Productos nuevoProducto) throws Exception {

		try(Connection miConexion =  origenDatos.getConnection()) {
//		try {
//			Connection miConexion =  origenDatos.getConnection();
			String sql = "INSERT INTO PRODUCTOS (CODIGOARTICULO, SECCION, NOMBREARTICULO, PRECIO, FECHA, IMPORTADO, PAISDEORIGEN) " + 
					 "VALUES(?,?,?,?,?,?,?)";
			PreparedStatement miStatement = miConexion.prepareStatement(sql);
			
			miStatement.setString(1, nuevoProducto.getcArt());
			miStatement.setString(2, nuevoProducto.getSeccion());
			miStatement.setString(3, nuevoProducto.getnArt());
			miStatement.setDouble(4, nuevoProducto.getPrecio());
			
//			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//			Date dateUtil = formato.parse(nuevoProducto.getFecha()); 
			
			Date dateUtil = nuevoProducto.getFecha();
			java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
			
			System.out.println("dateSql:" + dateSql);
			System.out.println("dateUtil:" + dateUtil);
			
			miStatement.setDate(5, dateSql);
			miStatement.setString(6, nuevoProducto.getImportado());
			miStatement.setString(7, nuevoProducto.getpOrig());
			 
			miStatement.execute();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new Exception("Error en la BD al INSERTAR");
		}
 
	}

	public Productos getProducto(String codigoArticulo) throws Exception {
		
		Productos elProducto = null;
		try(Connection miConexion =  origenDatos.getConnection()) {
//		try {
//			Connection miConexion =  origenDatos.getConnection();
			String sql = "SELECT * FROM PRODUCTOS WHERE CODIGOARTICULO = ?";
			PreparedStatement miStatement = miConexion.prepareStatement(sql);
			miStatement.setString(1, codigoArticulo);
			ResultSet miResultSet =  miStatement.executeQuery();
					
			if(miResultSet.next()){
				//En el video no se envia este primer campo, pero es mejor enviarlo
				String c_art = miResultSet.getString("CODIGOARTICULO");
				String seccion = miResultSet.getString("SECCION");
				String n_art = miResultSet.getString("NOMBREARTICULO");
				double precio = miResultSet.getDouble("PRECIO");
				Date fecha = miResultSet.getDate("FECHA");
				String importado = miResultSet.getString("IMPORTADO");
				String p_orig = miResultSet.getString("PAISDEORIGEN");
				
				elProducto = new Productos(c_art, seccion, n_art, precio, fecha, importado, p_orig);
//				elProducto = new Productos(seccion, n_art, precio, fecha, importado, p_orig);
				return elProducto;
			}else{
				throw new Exception("No hemos encontrado el producto con código de artículo: " + codigoArticulo);
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("Error en la BD al BUSCAR el PRODUCTO: " + codigoArticulo);
		}
	}

	public void actualizarProducto(Productos productoActualizado) throws Exception {
		
		try(Connection miConexion =  origenDatos.getConnection()) {
//		try {
//			Connection miConexion =  origenDatos.getConnection();
			String sql = "UPDATE PRODUCTOS SET SECCION = ?, NOMBREARTICULO = ?,"
					+ " PRECIO = ?, FECHA = ?, IMPORTADO = ?, PAISDEORIGEN = ? "
					+ "WHERE CODIGOARTICULO = ?";
			
			
			java.sql.Date dateSql = new java.sql.Date(productoActualizado.getFecha().getTime());
			
			PreparedStatement miStatement = miConexion.prepareStatement(sql);
			miStatement.setString(1, productoActualizado.getSeccion());
			miStatement.setString(2, productoActualizado.getnArt());
			miStatement.setDouble(3, productoActualizado.getPrecio());
			miStatement.setDate(4, dateSql);
			miStatement.setString(5, productoActualizado.getImportado());
			miStatement.setString(6, productoActualizado.getpOrig());
			miStatement.setString(7, productoActualizado.getcArt());
			
			miStatement.executeUpdate();
		
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new Exception("Error en la BD al MODIFICAR");
		}
		
	}

	public void eliminarProducto(String codigoArticulo) throws Exception{
		
		try(Connection miConexion =  origenDatos.getConnection()) {
//		try {
//			Connection miConexion =  origenDatos.getConnection();
			String sql = "DELETE FROM PRODUCTOS WHERE CODIGOARTICULO = ?";

			PreparedStatement miStatement = miConexion.prepareStatement(sql);
			miStatement.setString(1, codigoArticulo);

			miStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error en la BD al ELIMINAR");
		}		
		
	}
	
}






