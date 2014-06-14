import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;
public class GestionDB {
	//Creo los elementos de conexion a la BD
		Statement instruccion = null;// instrucción de consulta
		ResultSet Resultado = null;// maneja los resultados
		Connection conexion = null; //mamnejador de conexion
		
	public GestionDB(Connection conexion) {
		
		//DB Manejador de la conexion
	   this.conexion =conexion;
	    
	    
	}
	
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
	public void ControlArticulos(String Articulo) {
			
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
	

}
