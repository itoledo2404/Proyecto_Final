import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.EventQueue;





import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import javax.swing.JTable;
import javax.swing.border.LineBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
public class VentanaExistencias extends JFrame {

	
	private JPanel contentPane;
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane scp;
	
	
	//Creo los elementos de conexion a la BD
	Statement instruccion = null;// instrucción de consulta
	ResultSet conjuntoResultados = null;// maneja los resultados
	Connection conexion = null; //mamnejador de conexion
	private JTextField txtUbicacion;
	private JTextField txtPosicioin;
	private JTextField txtExistencia;
	private JCheckBox chckbxModificar;
	private JComboBox comboBox;
	private String desplegable;
	GestionDB gestion;
	
	/**
	 * Create the frame.
	 */
	public VentanaExistencias(Connection conexion) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\MODULO\\img32\\Package4.png"));
		setTitle("Stock");
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
		
		chckbxModificar = new JCheckBox("Modificar");
		chckbxModificar.setBounds(21, 397, 97, 23);
		contentPane.add(chckbxModificar);
		// set state
		chckbxModificar.setSelected(false);
		
			/*Gestion para obtener los valores de jtable*/
			table.addMouseListener(new MouseAdapter() {
		         @Override
		         public void mouseClicked(MouseEvent arg0) {
		         //Obtenemos la fila seleccionada
		         int fila=table.getSelectedRow();
		      // check state
		 		if (chckbxModificar.isSelected()) {
		 		 
		         //Obtenemos el valor
		         String art=table.getValueAt(fila, 0).toString();
		         String desc=table.getValueAt(fila, 1).toString();
		         String notas=table.getValueAt(fila, 2).toString();
		         String unidad=table.getValueAt(fila, 3).toString();
		         txtUbicacion.setText(art);
		         comboBox.setSelectedItem(desc);
		         txtPosicioin.setText(notas);
		         txtExistencia.setText(unidad);
		 		} 
		         }
		        });
		 
		
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
				desplegable = (String) comboBox.getSelectedItem();
				int txt1 = Integer.valueOf(txtUbicacion.getText());
				double txt2 =  Double.valueOf(txtExistencia.getText());
				
				if (txt1 == 0 || txt2 == 0)
				{
					JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Ubicacion y existencia)");
				}
				else
				{
					gestion.InsertStock(txt1, desplegable, txtPosicioin.getText(), txt2);
					RellenarJtable();
					txtUbicacion.setText("0");
					txtPosicioin.setText("");
					txtExistencia.setText("0");
					comboBox.setSelectedIndex(-1);
				}
			}
		});
		btnNuevo.setIcon(new ImageIcon("C:\\MODULO\\img32\\Add.png"));
		btnNuevo.setBounds(129, 397, 131, 50);
		contentPane.add(btnNuevo);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				desplegable = (String) comboBox.getSelectedItem();
				int txt1 = Integer.valueOf(txtUbicacion.getText());
				double txt2 =  Double.valueOf(txtExistencia.getText());
				
				if (txt1 == 0 || txt2 == 0)
				{
					JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Ubicacion y existencia)");
				}
				else
				{
				gestion.UpdateStock(txt1, desplegable, txtPosicioin.getText(), txt2);
				RellenarJtable();
				txtUbicacion.setText("0");
				txtPosicioin.setText("");
				txtExistencia.setText("0");
				comboBox.setSelectedIndex(-1);
				}
			}
		});
		btnModificar.setIcon(new ImageIcon("C:\\MODULO\\img32\\File1-Edit1.png"));
		btnModificar.setBounds(270, 397, 131, 50);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int txt1 = Integer.valueOf(txtUbicacion.getText());
				
				if (txt1 == 0)
				{
					JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Ubicacion)");
				}
				else
				{
				gestion.DeleteStock(txt1);
				RellenarJtable();
				txtUbicacion.setText("0");
				txtPosicioin.setText("");
				txtExistencia.setText("0");
				comboBox.setSelectedIndex(-1);}
			}
		});
		btnEliminar.setIcon(new ImageIcon("C:\\MODULO\\img32\\Delete1.png"));
		btnEliminar.setBounds(411, 397, 124, 50);
		contentPane.add(btnEliminar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Creacion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(129, 458, 485, 187);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ubicacion");
		lblNewLabel.setBounds(10, 24, 81, 14);
		panel.add(lblNewLabel);
		
		txtUbicacion = new JTextField();
		txtUbicacion.setText("0");
		txtUbicacion.setBounds(83, 21, 315, 20);
		panel.add(txtUbicacion);
		txtUbicacion.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Articulo");
		lblDescripcion.setBounds(10, 55, 81, 14);
		panel.add(lblDescripcion);
		
		txtPosicioin = new JTextField();
		txtPosicioin.setColumns(10);
		txtPosicioin.setBounds(83, 83, 315, 20);
		panel.add(txtPosicioin);
		
		JLabel lblNotas = new JLabel("Posicion");
		lblNotas.setBounds(10, 86, 81, 14);
		panel.add(lblNotas);
		
		JLabel lblUnidad = new JLabel("Existencia");
		lblUnidad.setBounds(10, 117, 81, 14);
		panel.add(lblUnidad);
		
		txtExistencia = new JTextField();
		txtExistencia.setText("0");
		txtExistencia.setColumns(10);
		txtExistencia.setBounds(83, 114, 315, 20);
		panel.add(txtExistencia);
		  
		comboBox = new JComboBox();
		comboBox.setBounds(83, 52, 315, 20);
		panel.add(comboBox);
		
		JButton btnNewButton = new JButton("Limpiar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtUbicacion.setText("0");
				txtPosicioin.setText("");
				txtExistencia.setText("0");
				comboBox.setSelectedIndex(-1);
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\MODULO\\img32\\Clean.png"));
		btnNewButton.setBounds(158, 145, 142, 31);
		panel.add(btnNewButton);
		  rellenarCombo();	
		


}
	//Rellenamos el combo con la tabla articulos
	public void rellenarCombo(){
		try{
			
			String valor="vacio";
		// crea objeto Statement para consultar la base de datos
    	instruccion = (Statement) this.conexion.createStatement();
    	// consulta la base de datos
    	conjuntoResultados = instruccion.executeQuery("SELECT * FROM dat_articulos");
    	//Añadir datos al modelo
    	
    	while (conjuntoResultados.next()) {
    		valor = conjuntoResultados.getString("art_articulo"); 
    		this.comboBox.addItem(valor);
    	}
    	conjuntoResultados.close();
    	}catch( SQLException excepcionSql ){
    	excepcionSql.printStackTrace();
    	}// fin de catch
	
	}


	    //Encabezados de la tabla
	    private String[] rellenarTitColumnas()
	    {
	          String columna[]=new String[]{"Ubicacion","Articulo","Posicion","Existencias"};
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
	    	conjuntoResultados = instruccion.executeQuery("SELECT ST_UBICACION,ST_ARTICULO,ST_POSICION,ST_EXISTENCIA FROM dat_stock");
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