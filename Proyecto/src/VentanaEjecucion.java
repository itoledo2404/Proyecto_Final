import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;


public class VentanaEjecucion extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox ;
	//Creo los elementos de conexion a la BD
	Statement insselect = null;// instrucción de consulta
	Statement insupdate = null;// instrucción de consulta
	Statement instruccion= null;// instrucción de consulta
	Statement insdelete = null;// instrucción de consulta
	ResultSet Resultado = null;// maneja los resultados
	ResultSet ResultControl = null;// maneja los resultados
	Connection conexion = null; //mamnejador de conexion
	private String tipoop;
	private String  campoSql;
	private GestionDB gestion;
	private String desplegable;
	private JTextField textField;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public VentanaEjecucion(Connection conexion,String tipo,String campo) {
		
		tipoop = tipo;
		campoSql= campo;
		this.conexion = conexion;
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\MODULO\\img32\\Power-On.png"));
		setTitle("Ejecucion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(40, 11, 424, 20);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Ejecuta");
		btnNewButton.setIcon(new ImageIcon("C:\\MODULO\\img48\\Hand2.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String desplegable = (String) comboBox.getSelectedItem();
				ejecucion(desplegable,tipoop);
			}
		});
		btnNewButton.setBounds(115, 87, 291, 100);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(5, 217, 500, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		rellenarCombo();
	}
	//Rellenamos el combo con la tabla articulos
	public void rellenarCombo(){
		try{
			
			String valor="vacio";
			String groupby="";
			
		if (tipoop =="pedidos")
			groupby ="PED_ID";
		else{
			groupby ="COM_ID";
		}
			
		// crea objeto Statement para consultar la base de datos
    	insselect = (Statement) this.conexion.createStatement();
    	// consulta la base de datos
    	Resultado = insselect.executeQuery("SELECT " + groupby + " FROM " + tipoop + " GROUP BY "+groupby);
    	//Añadir datos al modelo
    	
    	while (Resultado.next()) {
    		valor = Resultado.getString(campoSql); 
    		this.comboBox.addItem(valor);
    	}
    	Resultado.close();
    	}catch( SQLException excepcionSql ){
    	excepcionSql.printStackTrace();
    	}// fin de catch
	
	}
	//Metodo para ejecutar el pedido
	public void ejecucion(String pedido,String tipo){
		
		try{
			String campo1 ="";
			String campo2="";
			String campoSql1="";
			String campoSql2="";
			String operador="";
			String filtro="";
			String txt="";
			//Segun el tipo que sea uso unos campos
			switch(tipo){
			case "compras":
				filtro = "COM_ID";
				campoSql1="COM_ARTICULO";
				campoSql2="COM_CANTIDAD";
				operador = "st_existencia - ";
				break; 
			case "pedidos":
				filtro = "PED_ID";
				campoSql1="PED_ARTICULO";
				campoSql2="PED_CANTIDAD";
				operador = "st_existencia + ";
				break; 
			
			}
			// consulta la base de datos
			insselect = (Statement) conexion.createStatement();	
			// consulta la base de datos
			Resultado = insselect.executeQuery("SELECT * FROM " +  tipo + " WHERE "+ filtro  + " = " + "'"+ pedido +"'");
		
	    	//Añadir datos al modelo
	    	
	    	while (Resultado.next()) {
	    		campo1= Resultado.getString(campoSql1); 
	    		campo2= Resultado.getString(campoSql2); 
	    		
	    		// consulta la base de datos
				instruccion = (Statement) conexion.createStatement();
				// Buscamos si existe el Articulo en la tabla stock
				ResultControl= instruccion.executeQuery ("SELECT ST_ARTICULO FROM dat_stock WHERE ST_ARTICULO  = "+ "'"+campo1+"'" );		 

				// Si no existe lo avisamos de que no todas las filas se han completado
				if (!ResultControl.next()) {
					
					
					textField.setText("Linas de pedido si procesar verifique los pedidos y las ubicaciones");
					
				}else{
	    		
	    		//Actualizo el stock
	    		insupdate = (Statement) conexion.createStatement();	
	    		String sql_update="UPDATE dat_stock SET ";
	    		sql_update=sql_update + " st_existencia= " + operador +  campo2 + " WHERE  " + "st_articulo = " + "'"+campo1+"'" ;
				System.out.println(sql_update);
				insupdate.executeUpdate(sql_update);
	    		//Borro la linea de pedido
				insdelete = (Statement) conexion.createStatement();	
				String sql_delete=" DELETE FROM " + tipo + " WHERE " + filtro + " = " + "'"+ pedido +"'";
				sql_delete =sql_delete + "AND " + campoSql1 + " = " +"'" + campo1 + "'" ;
				System.out.println(sql_delete);
				insdelete.executeUpdate(sql_delete);
				}
	    	}
	    	Resultado.close();
			}catch( SQLException excepcionSql ){
				excepcionSql.printStackTrace();
				}// fin
	}
}
