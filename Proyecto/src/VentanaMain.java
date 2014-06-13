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


public class VentanaMain extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

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
		setFont(new Font("Cordia New", Font.PLAIN, 83));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\MODULO\\img32\\Box1-download.png"));
		setTitle("GESTION WMS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Gestion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 85, 514, 99);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Articulos");
		btnNewButton.setIcon(new ImageIcon("C:\\MODULO\\img32\\Square-Blue-A.png"));
		btnNewButton.setSelectedIcon(null);
		btnNewButton.setBounds(10, 22, 125, 66);
		panel.add(btnNewButton);
		
		JButton btnPedidos = new JButton("Pedidos");
		btnPedidos.setIcon(new ImageIcon("C:\\MODULO\\img32\\File1-Edit2.png"));
		btnPedidos.setBounds(134, 22, 119, 66);
		panel.add(btnPedidos);
		
		JButton btnCompras = new JButton("Compras");
		btnCompras.setIcon(new ImageIcon("C:\\MODULO\\img32\\Basket2.png"));
		btnCompras.setBounds(252, 22, 132, 66);
		panel.add(btnCompras);
		
		JButton btnStock = new JButton("Stock");
		btnStock.setIcon(new ImageIcon("C:\\MODULO\\img32\\Box4-import.png"));
		btnStock.setBounds(379, 22, 114, 66);
		panel.add(btnStock);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ejecucion", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 207, 320, 99);
		contentPane.add(panel_1);
		
		JButton btnRealizaCompra = new JButton("Realiza Compra");
		btnRealizaCompra.setIcon(new ImageIcon("C:\\MODULO\\img32\\Power-On.png"));
		btnRealizaCompra.setBounds(10, 22, 146, 66);
		panel_1.add(btnRealizaCompra);
		
		JButton btnInsertaStock = new JButton("Reliza Pedido");
		btnInsertaStock.setIcon(new ImageIcon("C:\\MODULO\\img32\\Power-On.png"));
		btnInsertaStock.setBounds(155, 22, 146, 66);
		panel_1.add(btnInsertaStock);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(514, 11, 106, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblGestorDeAlmacen = new JLabel("GESTOR DE ALMACEN 1.0");
		lblGestorDeAlmacen.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblGestorDeAlmacen.setBounds(10, 11, 506, 49);
		contentPane.add(lblGestorDeAlmacen);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("C:\\MODULO\\img32\\User4-Group.png"));
		btnNewButton_1.setBounds(531, 42, 89, 56);
		contentPane.add(btnNewButton_1);
	}
}
