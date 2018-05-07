package com.pildorasinformaticas.productos;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Para GLASSFISH y PAYARA
//@DataSourceDefinition(name = "java:app/jdbc/Productos",
//url = "jdbc:mysql://localhost:3306/curso_sql",
//className = "com.mysql.jdbc.Driver",
//user = "root",
//password = "123456789",
//databaseName = "curso_sql",
//serverName = "localhost")

@WebServlet("/ControladorProductos")
public class ControladorProductos extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/Productos")
//	@Resource(lookup = "java:app/jdbc/Productos")
	private DataSource miPool;
	
	private ModeloProductos modeloProductos; 
	
//	public ControladorProductos() {
//		modeloProductos = new ModeloProductos(miPool);
//	}
	
	@Override
	public void init() {//throws ServletException {
//		super.init();
		try {
			modeloProductos = new ModeloProductos(miPool);
		} catch (Exception e) {
//			throw new ServletException(e);
			System.out.println("Excepción: " + e.getMessage());
		}
	}

	
//	//Forma del video 252, 253
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		List<Productos> productos;
//		try {
//			System.out.println("modeloProductos.origenDatos: " + modeloProductos.origenDatos + "\n");
//
//			productos = modeloProductos.getProductos();
//			request.setAttribute("LISTAPRODUCTOS", productos);
////			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos2.jsp");
//			//Forma del video 254
//			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos.jsp");
//			miDispatcher.forward(request, response);
//		}
//		catch (Exception e) {
////			e.printStackTrace();
//			request.setAttribute("excepcion", e.getMessage());
//			request.setAttribute("LISTAPRODUCTOS", null);
//			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos.jsp");
//			miDispatcher.forward(request, response);
//		}
//	}
	

    //Forma del video 256
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String elComando = request.getParameter("instruccion");
		
		System.out.println("elComando: " + elComando);
		
		if(elComando == null)
			elComando = "listar";
		
		
		/************************************************/
		
//		switch(elComando){
//		
//		case "listar":
//			try {
//				obtenerProductos(request, response);
//			} catch (Exception e) {
//				System.out.println("EXCEPCIÓN al LISTAR: " + e.getMessage());
//				obtenerProductosError(request, response, e.getMessage());
//			}
//			break;
//			
//		case "insertarBBDD":
//			try {
//				agregarProductos(request, response);
//			} catch (Exception e) {
//				System.out.println("EXCEPCIÓN al INSERTAR: " + e.getMessage());
//				obtenerProductosError(request, response, e.getMessage());
//			}
//			break;
//	
//		case "actualizarBBDD":
//			try {
//				actualizaProductos(request, response);
//			} catch (Exception e) {
//				System.out.println("EXCEPCIÓN al MODIFICAR: " + e.getMessage());
//				obtenerProductosError(request, response, e.getMessage());
//			}
//			break;
//			
//		case "cargar":
//			try {
//				cargaProductos(request, response);
//			} catch (Exception e) {
////				e.printStackTrace();
//				System.out.println("EXCEPCIÓN al BUSCAR: " + e.getMessage());
//				obtenerProductosError(request, response, e.getMessage());				
//			}
//			break;
//
//		case "eliminar":
//			try {
//				eliminarProductos(request, response);
//			} catch (Exception e) {
////				e.printStackTrace();
//				System.out.println("EXCEPCIÓN al ELIMINAR: " + e.getMessage());
//				obtenerProductosError(request, response, e.getMessage());				
//			}
//			break;			
//			
//		default:
//			try {
//				obtenerProductos(request, response);
//			} catch (Exception e) {
//				System.out.println("EXCEPCIÓN al LISTAR: " + e.getMessage());
//				obtenerProductosError(request, response, e.getMessage());
//			}
//			break;
//		}
		
		/****************************************************/
		
		//Con un solo TRY-CATCH:
		try {
			switch (elComando) {

			case "listar":
				obtenerProductos(request, response);
				break;

			case "insertarBBDD":
				agregarProductos(request, response);
				break;

			case "actualizarBBDD":
				actualizaProductos(request, response);
				break;

			case "cargar":
				cargaProductos(request, response);
				break;

			case "eliminar":
				eliminarProductos(request, response);
				break;

			default:
				obtenerProductos(request, response);
				break;
			}
		} catch (Exception e) {
			System.out.println("EXCEPCIÓN: " + e.getMessage());
			obtenerProductosError(request, response, e.getMessage());	
		}			
		
	}

	
	private void eliminarProductos(HttpServletRequest request, HttpServletResponse response) throws Exception{

		String codigoArticulo = request.getParameter("CArticulo");
		modeloProductos.eliminarProducto(codigoArticulo);
		
		obtenerProductos(request, response);	
	}



	private void actualizaProductos(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String CodArticulo = request.getParameter("CArt"); 
		String Seccion = request.getParameter("seccion");
		String NombreArticulo = request.getParameter("NArt");
		String Fecha = request.getParameter("fecha");
		double Precio = Double.parseDouble(request.getParameter("precio"));
		String Importado = request.getParameter("importado");
		String PaisOrigen = request.getParameter("POrig");
		
		Productos ProductoActualizado = new Productos(CodArticulo,Seccion, NombreArticulo, Precio, Fecha, Importado, PaisOrigen);
		
		modeloProductos.actualizarProducto(ProductoActualizado);
	
		obtenerProductos(request, response);
	}

	private void cargaProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String codigoArticulo = request.getParameter("CArticulo");
		Productos elProducto = modeloProductos.getProducto(codigoArticulo);
		request.setAttribute("ProductoActualizar", elProducto);
		
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/actualizarProducto.jsp");
		miDispatcher.forward(request, response);
	}



	private void agregarProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String CodArticulo = request.getParameter("CArt"); 
		String Seccion = request.getParameter("seccion");
		String NombreArticulo = request.getParameter("NArt");
		String Fecha = request.getParameter("fecha");
		double Precio = Double.parseDouble(request.getParameter("precio"));
		String Importado = request.getParameter("importado");
		String PaisOrigen = request.getParameter("POrig");
		
		Productos NuevoProducto = new Productos(CodArticulo,Seccion, 
				NombreArticulo, Precio, Fecha, Importado, PaisOrigen);
		
		modeloProductos.agregarElNuevoProducto(NuevoProducto);
		
		obtenerProductos(request, response);
	}



	private void obtenerProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			List<Productos> productos = modeloProductos.getProductos();
			request.setAttribute("LISTAPRODUCTOS", productos);
			request.setAttribute("excepcion", null);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos.jsp");
//			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos2.jsp");
			miDispatcher.forward(request, response);
			
	}
	
	private void obtenerProductosError(HttpServletRequest request, HttpServletResponse response, String ex) {
		
		try {
			
//			List<Productos> productos = modeloProductos.getProductos();
			request.setAttribute("LISTAPRODUCTOS", null); 
			request.setAttribute("excepcion", ex);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos.jsp");
//			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos2.jsp");
			miDispatcher.forward(request, response);
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}	

}
