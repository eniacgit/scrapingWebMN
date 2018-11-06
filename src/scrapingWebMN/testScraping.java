package scrapingWebMN;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

public class testScraping extends JFrame {

	private JPanel contentPane;
	private JList listaProductos;
	private JTextField textFieldNombre;
	private JTextField textFieldPrecio;
	private JTextArea textAreaDescripcion;
	private JTextArea textAreaMates;
	private JTextArea textAreaFormaPago;
	private JTextField textFieldURL;
	private JTextField textFieldDimension;
	private JTextField textFieldDimensiones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testScraping frame = new testScraping();
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
	public testScraping() {
		setTitle("Test Sraping MN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCategorias = new JLabel("Categorias");
		lblCategorias.setBounds(12, 12, 122, 15);
		contentPane.add(lblCategorias);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 33, 213, 170);
		contentPane.add(scrollPane);
		
		JList listaCategorias = new JList();
		listaCategorias.setBackground(new Color(250, 250, 210));
		listaCategorias.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String categoria = (String) listaCategorias.getSelectedValue();
				Scraping sc = new Scraping();
				ArrayList lstProd = sc.obtenerProductosCategoria(categoria);
				DefaultListModel modelo = new DefaultListModel();
				for (int i=0; i< lstProd.size();i++) {
					modelo.addElement(lstProd.get(i));					
				}
				listaProductos.setModel(modelo);				
			}
		});
		scrollPane.setViewportView(listaCategorias);
		
		JButton btnNewButton = new JButton("Obtener Categorías");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "http://menini-nicola.com/tienda";
				Scraping sc = new Scraping();
				ArrayList lstCat = sc.obtenerCategorias(url);
				DefaultListModel modelo = new DefaultListModel();
				for (int i=0; i< lstCat.size();i++) {
					modelo.addElement(lstCat.get(i));					
				}
				listaCategorias.setModel(modelo);
				
				
			}
		});
		btnNewButton.setBounds(12, 215, 213, 25);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(237, 33, 401, 205);
		contentPane.add(scrollPane_1);
		
		listaProductos = new JList();
		listaProductos.setBackground(new Color(250, 250, 210));
		listaProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nombre = (String) listaProductos.getSelectedValue();
				nombre = nombre.replaceAll(" ", "-").toLowerCase();
				if (nombre.equals("funda-con-solapa-macbook-pro-13″-lana-y-cuero"))
					nombre = "funda-macbook-lyc";
				if (nombre.equals("funda-macbook-pro-13″-lana-y-cuero"))
					nombre = "funda-macbook-pro-13-lana-y-cuero";
				
				
				System.out.println(nombre);				
				Scraping sc = new Scraping();
				VOProductoMN producto = sc.obtenerProducto(nombre);
				
				textFieldNombre.setText(producto.getNombre());
				textFieldPrecio.setText(""+producto.getPrecio());
				textAreaDescripcion.setText(producto.getDescripcion());
				textAreaMates.setText(producto.getMateriales());
				textAreaFormaPago.setText(producto.getFormaPago());
				textFieldDimensiones.setText(producto.getDimensiones());
				textFieldURL.setText(producto.getUrlImagen());
				textFieldDimension.setText(producto.getDimensiones());
			}
		});
		scrollPane_1.setViewportView(listaProductos);
		
		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setBounds(237, 12, 144, 15);
		contentPane.add(lblProductos);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(12, 262, 70, 15);
		contentPane.add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setForeground(new Color(0, 0, 128));
		textFieldNombre.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldNombre.setBackground(new Color(224, 255, 255));
		textFieldNombre.setBounds(85, 260, 341, 19);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(444, 262, 55, 15);
		contentPane.add(lblPrecio);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setForeground(new Color(0, 0, 128));
		textFieldPrecio.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldPrecio.setBackground(new Color(224, 255, 255));
		textFieldPrecio.setBounds(497, 260, 141, 19);
		contentPane.add(textFieldPrecio);
		textFieldPrecio.setColumns(10);
		
		JLabel lblDescripcin = new JLabel("Descripción:");
		lblDescripcin.setBounds(12, 299, 184, 15);
		contentPane.add(lblDescripcin);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 326, 626, 122);
		contentPane.add(scrollPane_2);
		
		textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setLineWrap(true);
		textAreaDescripcion.setForeground(new Color(0, 0, 128));
		textAreaDescripcion.setFont(new Font("Dialog", Font.BOLD, 12));
		textAreaDescripcion.setBackground(new Color(224, 255, 255));
		scrollPane_2.setViewportView(textAreaDescripcion);
		
		JLabel lblMateriales = new JLabel("Materiales y terminación:");
		lblMateriales.setBounds(22, 468, 227, 15);
		contentPane.add(lblMateriales);
		
		JLabel lblTerminacin = new JLabel("Forma de pago:");
		lblTerminacin.setBounds(22, 599, 213, 15);
		contentPane.add(lblTerminacin);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(12, 493, 626, 94);
		contentPane.add(scrollPane_3);
		
		textAreaMates = new JTextArea();
		textAreaMates.setForeground(new Color(0, 0, 128));
		textAreaMates.setFont(new Font("Dialog", Font.BOLD, 12));
		textAreaMates.setBackground(new Color(224, 255, 255));
		scrollPane_3.setViewportView(textAreaMates);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(12, 616, 626, 58);
		contentPane.add(scrollPane_4);
		
		textAreaFormaPago = new JTextArea();
		textAreaFormaPago.setForeground(new Color(0, 0, 128));
		textAreaFormaPago.setFont(new Font("Dialog", Font.BOLD, 12));
		textAreaFormaPago.setBackground(new Color(224, 255, 255));
		scrollPane_4.setViewportView(textAreaFormaPago);
		
		JLabel lblUrlImagen = new JLabel("URL imagen:");
		lblUrlImagen.setBounds(12, 688, 102, 15);
		contentPane.add(lblUrlImagen);
		
		textFieldURL = new JTextField();
		textFieldURL.setForeground(new Color(0, 0, 128));
		textFieldURL.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldURL.setBackground(new Color(224, 255, 255));
		textFieldURL.setBounds(111, 686, 527, 19);
		contentPane.add(textFieldURL);
		textFieldURL.setColumns(10);
		
		JLabel label = new JLabel("Dimensiones:");
		label.setBounds(280, 297, 131, 15);
		contentPane.add(label);
		
		textFieldDimensiones = new JTextField();
		
		textFieldDimension = new JTextField();
		textFieldDimension.setForeground(SystemColor.activeCaption);
		textFieldDimension.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldDimension.setColumns(10);
		textFieldDimension.setBackground(new Color(224, 255, 255));
		textFieldDimension.setBounds(384, 295, 254, 19);
		contentPane.add(textFieldDimension);
	}
}
