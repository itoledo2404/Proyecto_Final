import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import javax.swing.JTable;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Toolkit;

import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.lang.String;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaArticulos extends JFrame {

	
	private JPanel contentPane;
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane scp;
	
	
	//Creo los elementos de conexion a la BD
	Statement instruccion = null;// instrucción de consulta
	ResultSet conjuntoResultados = null;// maneja los resultados
	Connection conexion = null; //mamnejador de conexion
	private JTextField txtArticulo;
	private JTextField txtDescripcion;
	private JTextField txtNotas;
	private JTextField txtUnidad;
	GestionDB gestion;
	
	/**
	 * Create the frame.
	 */
	public VentanaArticulos(Connection conexion) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\MODULO\\img32\\Book-Blue.png"));
		setTitle("Maestro Articulos");
		this.conexion=conexion;
		gestion = new GestionDB(this.conexion);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 694);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//JTABLE
        //1.- Definimos el modelo de JTable, en nuestro caso DefaultModel
		//2.- Definimos los títulos de las columnas
		//3.- REllenamos las filas a partir de la consulta a la base de datos
		//3.- Creamos el JTable y le asignamosel modelo rellenado
		//4,. Ponemos el JTable dentro de un JScrollPane para poder hacer scroll
		//5.- Damos visivilidad al JTable con setViewportView
        dtm = new DefaultTableModel(null,rellenarTitColumnas());
        
		//CONEXION A BASE DE DATOS
		//gestor.conexionDB();
        RellenarJtable();
		
		
		table = new JTable();
		table.setModel(dtm);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 675, 363);
		scrollPane.add(table);
		//REvisar la documentación para ver que significa el JScrollPane
		//http://docs.oracle.com/javase/7/docs/api/javax/swing/JScrollPane.html
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				gestion.InsertArticulos(txtArticulo.getText(), txtDescripcion.getText(), txtNotas.getText(), txtUnidad.getText());
				RellenarJtable();
			}
		});
		btnNuevo.setIcon(new ImageIcon("C:\\MODULO\\img32\\Add.png"));
		btnNuevo.setBounds(129, 397, 131, 50);
		contentPane.add(btnNuevo);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gestion.UpdateArticulos(txtArticulo.getText(), txtDescripcion.getText(), txtNotas.getText(), txtUnidad.getText());
				RellenarJtable();
			}
		});
		btnModificar.setIcon(new ImageIcon("C:\\MODULO\\img32\\File1-Edit1.png"));
		btnModificar.setBounds(270, 397, 131, 50);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gestion.DeleteArticulos(txtArticulo.getText());
				RellenarJtable();
			}
		});
		btnEliminar.setIcon(new ImageIcon("C:\\MODULO\\img32\\Delete1.png"));
		btnEliminar.setBounds(411, 397, 124, 50);
		contentPane.add(btnEliminar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Creacion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(129, 458, 485, 178);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Articulo");
		lblNewLabel.setBounds(10, 24, 81, 14);
		panel.add(lblNewLabel);
		
		txtArticulo = new JTextField();
		txtArticulo.setBounds(83, 21, 315, 20);
		panel.add(txtArticulo);
		txtArticulo.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(83, 52, 315, 20);
		panel.add(txtDescripcion);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(10, 55, 81, 14);
		panel.add(lblDescripcion);
		
		txtNotas = new JTextField();
		txtNotas.setColumns(10);
		txtNotas.setBounds(83, 83, 315, 20);
		panel.add(txtNotas);
		
		JLabel lblNotas = new JLabel("Notas");
		lblNotas.setBounds(10, 86, 81, 14);
		panel.add(lblNotas);
		
		JLabel lblUnidad = new JLabel("Unidad");
		lblUnidad.setBounds(10, 117, 81, 14);
		panel.add(lblUnidad);
		
		txtUnidad = new JTextField();
		txtUnidad.setColumns(10);
		txtUnidad.setBounds(83, 114, 315, 20);
		panel.add(txtUnidad);


}

	    //Encabezados de la tabla
	    private String[] rellenarTitColumnas()
	    {
	          String columna[]=new String[]{"Articulo","Descripcion","Notas","Unidad"};
	          return columna;
	    }
	    private void RellenarJtable(){
	    	try{
	    		
	    	//lIMPIO EL JTABLE
	    		int sizeModel = dtm.getRowCount();
	    		 
	    	for (int i = 0; i < sizeModel ; i ++) {
	    		dtm.removeRow(0);
	    	}
	    	// crea objeto Statement para consultar la base de datos
	    	instruccion = (Statement) this.conexion.createStatement();
	    	// consulta la base de datos
	    	conjuntoResultados = instruccion.executeQuery("SELECT ART_ARTICULO,ART_DESCRIPCION,ART_NOTAS,ART_UNIDAD FROM dat_articulos");
	    	//Añadir datos al modelo
	    	Object datos[]=new Object[4]; //Numero de columnas de la tabla
	    	while (conjuntoResultados.next()) {
	    	for (int i = 0; i < 4; i++) {
	    	datos[i] = conjuntoResultados.getObject(i + 1);
	    	}
	    	dtm.addRow(datos);
	    	}
	    	conjuntoResultados.close();
	    	}catch( SQLException excepcionSql ){
	    	excepcionSql.printStackTrace();
	    	}// fin de catch
	    	
	    }
	   
	  
}
