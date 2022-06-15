package javadb;

import java.sql.*;

public class KoneksiDb {

    private JFrame frmFormDataBarang;
	private JTextField txtkdbrg;
	private JTextField txtnmbrg;
	private JTextField txtstock;
	private JTextField txtstokmin;
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/penjualan";
    static final String USER = "root";
    static final String PASS = "";
    
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	
	/**
	*Launch the application.
	*/
	
    public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
		    public void run() {
			    try {
				    FormBarang window = new FormBarang();
					window.frmFormDataBarang.setVisible(true);
				} catch (Exception e){
				    e.printStackTrace();
				}
            }
        });			
	}
	
	/**
	*Create the application.
	*/
	public FormBarang() {
	    initialize();
	}

	/**
	*initialize the contents of the frame.
	*/
	private void initialize() {
	   frmFormDataBarang = new JFrame();
	   frmFormDataBarang.addWindowListener(new WindowAdapter() {
	       @Override
		   public void windowOpened(WindowEvent e) {
		       showData();
		   }
       });
	   frmFormDataBarang.setTitle("Form data barang");
	   frmFormDataBarang.setBounds(100, 100, 753, 484);
	   frmFormDataBarang.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frmFormDataBarang.getContentPane().setLayout(null);
	   
	   JLabel lblNewLabel = new JLabel("Data Barang");
	   lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	   lblNewLabel.setBounds(51, 51, 128, 21);
	   frmFormDataBarang.getContentPane().add(lblNewLabel);
	   
	   JLabel lblNewLabel_1 = new JLabel("Kode Barang");
	   lblNewLabel.setBounds(51, 104, 85, 14);
	   frmFormDataBarang.getContentPane().add(lblNewLabel_1);
	   
	   JLabel lblNewLabel_2 = new JLabel("Nama Barang");
	   lblNewLabel.setBounds(51, 135, 85, 14);
	   frmFormDataBarang.getContentPane().add(lblNewLabel_2);
	   
	   JLabel lblNewLabel_3 = new JLabel("Satuan");
	   lblNewLabel.setBounds(51, 168, 46, 14);
	   frmFormDataBarang.getContentPane().add(lblNewLabel_3);
	   
	   JLabel lblNewLabel_4 = new JLabel("Stok");
	   lblNewLabel.setBounds(51, 201, 46, 14);
	   frmFormDataBarang.getContentPane().add(lblNewLabel_4);
	   
	   JLabel lblNewLabel_5 = new JLabel("Stok minimal");
	   lblNewLabel.setBounds(51, 236, 85, 14);
	   frmFormDataBarang.getContentPane().add(lblNewLabel_5);
	   
	   txtkdbrg = new JTextField();
	   txtkdbrg.setBounds(146, 101, 94, 20);
	   frmFormDataBarang.getContentPane().add(txtkdbrg);
	   txtkdbrg.setColumns(10);
	   
	   JComboBox boxsatuan = new JComboBox();
	   boxsatuan.setModel(new DefaultComboBoxModel(new String[] {"buah", "liter", "lembar", "kilogram"}));
	   boxsatuan.setBounds(146, 164, 94, 22);
	   frmFormDataBarang.getContentPane().add(boxsatuan);
	   
	   txtstock = new JTextField();
	   txtstock.setBounds(146, 198, 86, 20);
	   frmFormDataBarang.getContentPane().add(txtstock);
	   txtstock.setColumns(10);
	   
	   txtstokmin = new JTextField();
	   txtstokmin.setBounds(146, 233, 86, 20);
	   frmFormDataBarang.getContentPane().add(txtstokmin);
	   txtstokmin.setColumns(10);
	   
	    JButton tblSimpan = new JButton("simpan");
	    tblSimpan.addActionListener(new ActionListener() {
	        public void actionPerformed(Action event e){
		        String kode_brg = txtkdbrg.getText();
		        String nama_brg = txtnmbrg.getText();
                Object satuan = boxSatuan.getSelectedItem();
                int Stock = Integer.parseInt(txtstock.getText());
                int stokmin = Integer.paseInt(txtstokmin.getText());
					   
			    insert(kode, nm_brg, satuan, stock, stokmin);
			}
        });
        tblSimpan.setBounds(51, 314, 89, 23);
		frmFormDataBarang.getContentPane().add(tblSimpan);
		
		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetform();
			}
		});
        btnNewButton_1.setBounds(174, 359, 89, 23);
        frmFormDataBarang.getContentPane().add(btnNewButton_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 57, 375, 361);
		frmFormDataBarang.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String kode = table.getValueAt(table.getSelectedRow(),0).toString();
				getData(kode);
				tblSimpan.setEnabled(false);
			}
		});
        scrollPane.setViewportView(table);
		
		JButton tblEdit = new JButton("edit");
		tblEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editData();
			}
		});
		tblEdit.setBounds(174, 314, 89, 23);
        frmFormDataBarang.getContentPane().add(tblEdit);
		
		
		JButton tblHapus = new JButton("Hapus");
		tblHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    int respone = JOptionPane.showConfirmDialog(null, "Yakin Ingin Hapus Data?");
				if(respone==0)
				{
					if(table.getSelectedRow() >= 0 )
			        {
						hapusData(txtkdbrg.getText());
					}
				}
				else
				{
				       JOptionPane.showMessageDialog(null, "Hapus data dibatalkan");
				}
			}	
		});
		tblHapus.setBounds(51, 359, 89, 23);
        frmFormDataBarang.getContentPane().add(tblHapus);
