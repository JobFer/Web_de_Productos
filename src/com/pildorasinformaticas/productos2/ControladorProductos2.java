package com.pildorasinformaticas.productos2;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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

import com.pildorasinformaticas.productos2.Productos;

@WebServlet("/ControladorProductos2")
public class ControladorProductos2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ModeloProductos modeloProductos;
	
	@Resource(name="jdbc/Productos")
	private DataSource miPool;
	
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			modeloProductos = new ModeloProductos(miPool);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

//	//Forma del video 252, 253
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		List<Productos> productos;
//		try {
//			productos = modeloProductos.getProductos();
//			request.setAttribute("LISTAPRODUCTOS", productos);
////			RequestDispatcher miDispatcher = request.getRequestDispatcher("/productos2/ListaProductos2.jsp");
//			//Forma del video 254
//			RequestDispatcher miDispatcher = request.getRequestDispatcher("/productos2/ListaProductos.jsp");
//			miDispatcher.forward(request, response);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	

	//Forma del video 256
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String elComando = request.getParameter("instruccion");
		System.out.println("elComando: " + elComando);
		
		if(elComando == null)
			elComando = "listar";
		
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
		
//		SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
//		Date Fecha = null;
//		try {
//			Fecha = formato.parse(request.getParameter("fecha"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
//		String Fecha = request.getParameter("fecha");		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date Fecha = formato.parse(request.getParameter("fecha")); 
		
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
		
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/productos2/actualizarProducto.jsp");
		miDispatcher.forward(request, response);
	}



	private void agregarProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String CodArticulo = request.getParameter("CArt"); 
		String Seccion = request.getParameter("seccion");
		String NombreArticulo = request.getParameter("NArt");
		
//		SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
//		Date Fecha = null;
//		try {
//			Fecha = formato.parse(request.getParameter("fecha"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

//		String Fecha = request.getParameter("fecha");
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date Fecha = formato.parse(request.getParameter("fecha")); 
		
		double Precio = Double.parseDouble(request.getParameter("precio"));
		String Importado = request.getParameter("importado");
		String PaisOrigen = request.getParameter("POrig");
		
		Productos NuevoProducto = new Productos(CodArticulo,Seccion, NombreArticulo, Precio, Fecha, Importado, PaisOrigen);
		
		modeloProductos.agregarElNuevoProducto(NuevoProducto);
		
		obtenerProductos(request, response);
				
	}



	private void obtenerProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			List<Productos> productos = modeloProductos.getProductos();
			request.setAttribute("LISTAPRODUCTOS", productos);
			request.setAttribute("excepcion", null);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/productos2/ListaProductos.jsp");
//			RequestDispatcher miDispatcher = request.getRequestDispatcher("/productos2/ListaProductos2.jsp");
			miDispatcher.forward(request, response);
			
	}
	
	private void obtenerProductosError(HttpServletRequest request, HttpServletResponse response, String ex) {
		
		try {
			
			List<Productos> productos = modeloProductos.getProductos();
			request.setAttribute("LISTAPRODUCTOS", productos);
			request.setAttribute("excepcion", ex);
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/productos2/ListaProductos.jsp");
//			RequestDispatcher miDispatcher = request.getRequestDispatcher("/productos2/ListaProductos2.jsp");
			miDispatcher.forward(request, response);
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}	

}
