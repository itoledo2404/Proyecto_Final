import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaMain extends JFrame {

	private JPanel contentPane;
	private VentanaArticulos frameArticulos;
	private VentanaExistencias frameExistencias;
	private VentanaPedidos framePedidos;
	private VentanaCompras frameCompras;
	private VentanaEjecucion frameEjecucion;
	//DB Manejador de la conexion
    Connection conexion = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMain frame = new VentanaMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaMain() {
		
		
	    
	  //Conectarnos a la base de datos
 		try{
 			Class.forName("com.mysql.jdbc.Driver");
 			// Establece la conexion con la DB
 			conexion = DriverManager.getConnection("jdbc:mysql://localhost/wmsdb","root","ivan2404");
 			}catch( SQLException excepcionSql ){
 				excepcionSql.printStackTrace();
 			}// fin de catch
 			catch( ClassNotFoundException noEncontroClase )
 			{
 				noEncontroClase.printStackTrace();
 			}// fin de catch
 		
		setFont(new Font("Cordia New", Font.PLAIN, 83));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\MODULO\\img32\\Box1-download.png"));
		setTitle("GESTION WMS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Gestion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 85, 514, 99);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnArticulos = new JButton("Articulos");
		btnArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameArticulos = new VentanaArticulos(conexion);
				frameArticulos.setVisible(true);
				frameArticulos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnArticulos.setIcon(new ImageIcon("C:\\MODULO\\img32\\Square-Blue-A.png"));
		btnArticulos.setSelectedIcon(null);
		btnArticulos.setBounds(10, 22, 125, 66);
		panel.add(btnArticulos);
		
		JButton btnPedidos = new JButton("Pedidos");
		btnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				framePedidos = new VentanaPedidos(conexion);
				framePedidos.setVisible(true);
				framePedidos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnPedidos.setIcon(new ImageIcon("C:\\MODULO\\img32\\File1-Edit2.png"));
		btnPedidos.setBounds(134, 22, 119, 66);
		panel.add(btnPedidos);
		
		JButton btnCompras = new JButton("Compras");
		btnCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameCompras = new VentanaCompras(conexion);
				frameCompras.setVisible(true);
				frameCompras.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnCompras.setIcon(new ImageIcon("C:\\MODULO\\img32\\Basket2.png"));
		btnCompras.setBounds(252, 22, 132, 66);
		panel.add(btnCompras);
		
		JButton btnStock = new JButton("Stock");
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameExistencias = new VentanaExistencias(conexion);
				frameExistencias.setVisible(true);
				frameExistencias.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnStock.setIcon(new ImageIcon("C:\\MODULO\\img32\\Box4-import.png"));
		btnStock.setBounds(379, 22, 125, 66);
		panel.add(btnStock);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ejecucion", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 207, 376, 99);
		contentPane.add(panel_1);
		
		JButton btnRealizaCompra = new JButton("Realiza Compra");
		btnRealizaCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameEjecucion = new VentanaEjecucion(conexion,"compras","COM_ID");
				frameEjecucion.setVisible(true);
				frameEjecucion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		});
		btnRealizaCompra.setIcon(new ImageIcon("C:\\MODULO\\img32\\Power-On.png"));
		btnRealizaCompra.setBounds(10, 22, 170, 66);
		panel_1.add(btnRealizaCompra);
		
		JButton btnRelizarPedido = new JButton("Reliza Pedido");
		btnRelizarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameEjecucion = new VentanaEjecucion(conexion,"pedidos","PED_ID");
				frameEjecucion.setVisible(true);
				frameEjecucion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		});
		btnRelizarPedido.setIcon(new ImageIcon("C:\\MODULO\\img32\\Power-On.png"));
		btnRelizarPedido.setBounds(190, 22, 170, 66);
		panel_1.add(btnRelizarPedido);
		
		JLabel lblGestorDeAlmacen = new JLabel("GESTOR DE TIENDA 1.0");
		lblGestorDeAlmacen.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblGestorDeAlmacen.setBounds(10, 11, 514, 49);
		contentPane.add(lblGestorDeAlmacen);
	}
}
