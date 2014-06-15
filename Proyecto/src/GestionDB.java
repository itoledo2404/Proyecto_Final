import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;
public class GestionDB implements Serializable{
	//Creo los elementos de conexion a la BD
		Statement instruccion = null;// instrucción de consulta
		ResultSet Resultado = null;// maneja los resultados
		Connection conexion = null; //mamnejador de conexion
		
	public GestionDB(Connection conexion) {
		
		//DB Manejador de la conexion
	   this.conexion =conexion;
	    
	    
	}
	//Gestion del MAestro de Articulos
	public void InsertArticulos(String Articulo,String Descripcion,String Notas, String Unidad) {
	
		try{
			if (Articulo.equals("") || Descripcion.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Articulo y descripcion)");
			}else{
				
				// consulta la base de datos
				instruccion = (Statement) conexion.createStatement();
				// Buscamos si existe el Articulo
				Resultado= instruccion.executeQuery ("SELECT art_articulo FROM dat_articulos WHERE art_articulo = '"+Articulo+"'");		 

				// Si no existe lo insertamos
				if (!Resultado.next()) {
					// insercion en base de datos
					String sql_inst="INSERT INTO dat_articulos ( art_articulo,art_descripcion,art_notas,art_unidad)";
					sql_inst=sql_inst+ "VALUES( '"+Articulo+"','"+Descripcion+"','"+Notas+"','"+Unidad +"')";
					System.out.println(sql_inst);
					instruccion.executeUpdate(sql_inst);
				}else{
					// Si existe lanzamos un mensaje
					JOptionPane.showMessageDialog(null,"Al articulo ya esta en la base de datos Articulo = " + Articulo);
				}
			}
	
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
			
	}
	public void UpdateArticulos(String Articulo,String Descripcion,String Notas, String Unidad) {
		
		try{
			if (Articulo.equals("") || Descripcion.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Articulo y descripcion)");
			}else{
			// consulta la base de datos
			instruccion = (Statement) conexion.createStatement();
			// insercion en base de datos
			String sql_update="UPDATE dat_articulos SET ";
			sql_update=sql_update + "art_descripcion = " + "'"+Descripcion+"'" + ",art_notas = " + "'"+Notas+"'"+ ",art_unidad = "+ "'"+ Unidad +"'" + "WHERE art_articulo =" +  "'"+Articulo+"'";
			System.out.println(sql_update);
			instruccion.executeUpdate(sql_update);
			}
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
		
			
	}
	public void DeleteArticulos(String Articulo) {
		
		try{
			
			if (Articulo.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Debe introducir un codigo Articulo");
			}else{
			// consulta la base de datos
			instruccion = (Statement) conexion.createStatement();
			// insercion en base de datos
			String sql_delete=" DELETE FROM dat_articulos WHERE art_articulo =  ";
			sql_delete =sql_delete + "'"+Articulo+"'";
			JOptionPane.showMessageDialog(null,sql_delete);
			instruccion.executeUpdate(sql_delete);}
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
			
	}
	//Gestion del Stock	
	public void InsertStock(int ubicacion,String Articulo,String Posicion,double Existencia) {
			
		try{
				// consulta la base de datos
				instruccion = (Statement) conexion.createStatement();
				
				// insercion en base de datos
				String sql_inst="INSERT INTO dat_stock( st_ubicacion,st_articulo,st_posicion,st_existencia)";
				sql_inst=sql_inst+ "VALUES( "+ubicacion+",'"+Articulo+"','"+Posicion+"',"+Existencia +")";
				System.out.println(sql_inst);
				instruccion.executeUpdate(sql_inst);
			
	
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
			
	}
    public void UpdateStock(int ubicacion,String Articulo,String Posicion,double Existencia) {
		
		try{
			
			// consulta la base de datos
			instruccion = (Statement) conexion.createStatement();
			// insercion en base de datos
			String sql_update="UPDATE dat_stock SET ";
			sql_update=sql_update + "st_articulo = " + "'"+Articulo+"'"+ ",st_posicion = " + "'"+Posicion+"'"+ ",st_existencia= "+  Existencia + "WHERE st_ubicacion =" + ubicacion ;
			System.out.println(sql_update);
			instruccion.executeUpdate(sql_update);
			
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
		
			
	}
	public void DeleteStock(int ubicacion) {
		
		try{
			
			// consulta la base de datos
			instruccion = (Statement) conexion.createStatement();
			// insercion en base de datos
			String sql_delete=" DELETE FROM dat_stock WHERE st_ubicacion =  ";
			sql_delete =sql_delete + ubicacion;
			JOptionPane.showMessageDialog(null,sql_delete);
			instruccion.executeUpdate(sql_delete);
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
			
	}
	public void InsertLineaPedido(String Pedido,String Descripcion,String Articulo, double cantidad) {
		
		try{
			if (Articulo.equals("") || Pedido.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Articulo y pedido)");
			}else{
				
				// consulta la base de datos
				instruccion = (Statement) conexion.createStatement();
				// Buscamos si existe el Articulo
				Resultado= instruccion.executeQuery ("SELECT PED_ARTICULO FROM pedidos WHERE PED_ID  = "+ "'"+Pedido+"'" + " AND PED_ARTICULO  = " + "'"+Articulo+"'" );		 

				// Si no existe lo insertamos
				if (!Resultado.next()) {
					// insercion en base de datos
					String sql_inst="INSERT INTO pedidos ( PED_ID,PED_DESCRIPCION,PED_ARTICULO,PED_CANTIDAD)";
					sql_inst=sql_inst+ "VALUES( '"+Pedido+"','"+Descripcion+"','"+Articulo+"' ,"+ cantidad +")";
					System.out.println(sql_inst);
					instruccion.executeUpdate(sql_inst);
				}else{
					// Si existe lanzamos un mensaje
					JOptionPane.showMessageDialog(null,"Al articulo ya esta en el pedido Articulo = " + Articulo);
				}
			}
	
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
			
	}
	public void UpdateLineaPedido(String Pedido,String Descripcion,String Articulo, double cantidad) {
		
		try{
			if (Articulo.equals("") || Pedido.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Articulo y Pedido)");
			}else{
			// consulta la base de datos
			instruccion = (Statement) conexion.createStatement();
			// insercion en base de datos
			String sql_update="UPDATE pedidos SET ";
			sql_update=sql_update + "PED_DESCRIPCION = " + "'"+Descripcion+"'" + ",PED_CANTIDAD = " + cantidad+ " WHERE PED_ID =" +  "'"+Pedido+"'" +" AND PED_ARTICULO = " +"'"+Articulo+"'";
			System.out.println(sql_update);
			instruccion.executeUpdate(sql_update);
			}
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
		
			
	}
public void DeleteLineaPedidos(String pedido, String Articulo) {
		
		try{
			
			// consulta la base de datos
			instruccion = (Statement) conexion.createStatement();
			// insercion en base de datos
			String sql_delete=" DELETE FROM pedidos WHERE PED_ID =  ";
			sql_delete =sql_delete + "'" + pedido + "'" + " AND PED_ARTICULO = "+"'" + Articulo + "'" ;
			JOptionPane.showMessageDialog(null,sql_delete);
			instruccion.executeUpdate(sql_delete);
		}catch( SQLException excepcionSql ){
		excepcionSql.printStackTrace();
		}// fin
			
	}
public void InsertLineaCompras(String Pedido,String Descripcion,String Articulo, double cantidad) {
	
	try{
		if (Articulo.equals("") || Pedido.equals(""))
		{
			JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Articulo y pedido)");
		}else{
			
			// consulta la base de datos
			instruccion = (Statement) conexion.createStatement();
			// Buscamos si existe el Articulo
			Resultado= instruccion.executeQuery ("SELECT COM_ARTICULO FROM compras WHERE COM_ID  = "+ "'"+Pedido+"'" + " AND COM_ARTICULO  = " + "'"+Articulo+"'" );		 

			// Si no existe lo insertamos
			if (!Resultado.next()) {
				// insercion en base de datos
				String sql_inst="INSERT INTO compras (COM_ID,COM_PROVEEDOR,COM_ARTICULO,COM_CANTIDAD)";
				sql_inst=sql_inst+ "VALUES( '"+Pedido+"','"+Descripcion+"','"+Articulo+"' ,"+ cantidad +")";
				System.out.println(sql_inst);
				instruccion.executeUpdate(sql_inst);
			}else{
				// Si existe lanzamos un mensaje
				JOptionPane.showMessageDialog(null,"Al articulo ya esta en el pedido de compra Articulo = " + Articulo);
			}
		}

	}catch( SQLException excepcionSql ){
	excepcionSql.printStackTrace();
	}// fin
		
}
public void UpdateLineaCompras(String Pedido,String Descripcion,String Articulo, double cantidad) {
	
	try{
		if (Articulo.equals("") || Pedido.equals(""))
		{
			JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Articulo y Pedido)");
		}else{
		// consulta la base de datos
		instruccion = (Statement) conexion.createStatement();
		// insercion en base de datos
		String sql_update="UPDATE compras SET ";
		sql_update=sql_update + "COM_PROVEEDOR = " + "'"+Descripcion+"'" + ",COM_CANTIDAD = " + cantidad+ " WHERE COM_ID =" +  "'"+Pedido+"'" +" AND COM_ARTICULO = " +"'"+Articulo+"'";
		System.out.println(sql_update);
		instruccion.executeUpdate(sql_update);
		}
	}catch( SQLException excepcionSql ){
	excepcionSql.printStackTrace();
	}// fin
	
		
}
public void DeleteLineaCompras(String pedido, String Articulo) {
	
	try{
		
		// consulta la base de datos
		instruccion = (Statement) conexion.createStatement();
		// insercion en base de datos
		String sql_delete=" DELETE FROM compras WHERE COM_ID =  ";
		sql_delete =sql_delete + "'" + pedido + "'" + " AND COM_ARTICULO = "+"'" + Articulo + "'" ;
		JOptionPane.showMessageDialog(null,sql_delete);
		instruccion.executeUpdate(sql_delete);
	}catch( SQLException excepcionSql ){
	excepcionSql.printStackTrace();
	}// fin
		
}
	
}
