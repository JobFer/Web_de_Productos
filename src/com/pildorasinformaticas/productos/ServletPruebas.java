package com.pildorasinformaticas.productos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/ServletPruebas")
public class ServletPruebas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/Productos")
	private DataSource miPool;
	
//    public ServletPruebas() {
//        super();
//    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {

//        	response.setContentType("text/plain");
        	response.setContentType("text/html;charset=UTF-8");
        	Connection miConexion = miPool.getConnection();
//        	System.out.println(miConexion.getClass().getName());
        	
        	String miSql = "SELECT * FROM PRODUCTOS";
        	Statement miStatement = miConexion.createStatement();
        	ResultSet miResultSet = miStatement.executeQuery(miSql);
        	
        	while (miResultSet.next()) {
				String nombre_articulo = miResultSet.getString(3);
//				String nombre_articulo = miResultSet.getString("NOMBREARTICULO");
				out.println(nombre_articulo + "<br/>");
			}
        	
//        	ModeloProductos mp = new ModeloProductos(miPool);
//        	List<Productos> lista = mp.getProductos();
//        	out.println(lista);
        	
        }catch (Exception e) {
        	e.printStackTrace();
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
