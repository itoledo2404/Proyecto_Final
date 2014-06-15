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
public class VentanaCompras extends JFrame {

	
	private JPanel contentPane;
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane scp;
	
	
	//Creo los elementos de conexion a la BD
	Statement instruccion = null;// instrucción de consulta
	ResultSet conjuntoResultados = null;// maneja los resultados
	Connection conexion = null; //mamnejador de conexion
	private JTextField txtPedido;
	private JTextField txtDescripcion;
	private JTextField txtExistencia;
	private JCheckBox chckbxModificar;
	private JComboBox comboBox;
	private String desplegable;
	GestionDB gestion;
	
	/**
	 * Create the frame.
	 */
	public VentanaCompras(Connection conexion) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\MODULO\\img32\\Paste2.png"));
		setTitle("Pedidos");
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
		         txtPedido.setText(art);
		         comboBox.setSelectedItem(desc);
		         txtDescripcion.setText(notas);
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
				int txt1 = Integer.valueOf(txtPedido.getText());
				double txt2 =  Double.valueOf(txtExistencia.getText());
				
				if (txt1 == 0 || txt2 == 0)
				{
					JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Ubicacion y existencia)");
				}
				else
				{
					gestion.InsertStock(txt1, desplegable, txtDescripcion.getText(), txt2);
					RellenarJtable();
					txtPedido.setText("0");
					txtDescripcion.setText("");
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
				int txt1 = Integer.valueOf(txtPedido.getText());
				double txt2 =  Double.valueOf(txtExistencia.getText());
				
				if (txt1 == 0 || txt2 == 0)
				{
					JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Ubicacion y existencia)");
				}
				else
				{
				gestion.UpdateStock(txt1, desplegable, txtDescripcion.getText(), txt2);
				RellenarJtable();
				txtPedido.setText("0");
				txtDescripcion.setText("");
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
				int txt1 = Integer.valueOf(txtPedido.getText());
				
				if (txt1 == 0)
				{
					JOptionPane.showMessageDialog(null,"Debe introducir los campos obligatorios (Ubicacion)");
				}
				else
				{
				gestion.DeleteStock(txt1);
				RellenarJtable();
				txtPedido.setText("0");
				txtDescripcion.setText("");
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
		
		JLabel lblPedido = new JLabel("Pedido");
		lblPedido.setBounds(10, 24, 81, 14);
		panel.add(lblPedido);
		
		txtPedido = new JTextField();
		txtPedido.setBounds(83, 21, 315, 20);
		panel.add(txtPedido);
		txtPedido.setColumns(10);
		
		JLabel lblArticulo = new JLabel("Articulo");
		lblArticulo.setBounds(10, 55, 81, 14);
		panel.add(lblArticulo);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(83, 83, 315, 20);
		panel.add(txtDescripcion);
		
		JLabel lbldes = new JLabel("Descripcion");
		lbldes.setBounds(10, 86, 81, 14);
		panel.add(lbldes);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(10, 117, 81, 14);
		panel.add(lblCantidad);
		
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
				txtPedido.setText("0");
				txtDescripcion.setText("");
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
	          String columna[]=new String[]{"Pedido","Descripcion","Articulo","Cantidad"};
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
	    	conjuntoResultados = instruccion.executeQuery("SELECT * FROM pedidos");
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