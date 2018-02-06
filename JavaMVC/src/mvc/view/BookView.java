package mvc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import mvc.controller.BookController;
import mvc.dao.DAOFactory;
import mvc.dao.PostgresqlDAOFactory;
import mvc.dao.SqliteDAOFactory;
import mvc.model.Book;
import mvc.repository.KlientRepo;

public class BookView extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Object[] COLUMN_NAMES = {"ID", "Tytul", "Autor", "Wydawnictwo", "Ilosc"};

	public BookController controller;

	private JLabel lTytul, lAutor, lWydawnictwo, lIlosc;
	private JTextField tfTytul, tfAutor, tfWydawnictwo, tfIlosc;
	private JTable table;
	private static DefaultTableModel tableModel;
	JComboBox ksiazka = new JComboBox();
	public BookView() {

		super();


	    tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
	    table = new JTable(tableModel);
        table.setSize(new Dimension(1200, 600));
	    table.setPreferredSize(new Dimension(0,2000));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        table.setRowHeight(table.getRowHeight() + 50);
	    table.setAutoCreateRowSorter(true); 

	   
	    JButton dodajKlienta = new JButton("Dodaj książkę");
	    dodajKlienta.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		Book book = new Book(
	    				tfTytul.getText(),
	    				tfAutor.getText(),
	    				tfWydawnictwo.getText(),
	    				Integer.parseInt(tfIlosc.getText()));
	    		controller.insertBook(book);
	    		listaksiazek();
	    	}
	    });
	    


	    JButton usunKsiazke = new JButton("Usuń książkę");
	    usunKsiazke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					Integer BookId = (Integer) tableModel.getValueAt(selectedRow, 0);
					controller.deleteBook(BookId);
					listaksiazek();
				}
			}
	    });
	    
	    JButton edytujKsiazke = new JButton("Edytuj ksiazke");
	    edytujKsiazke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					Integer BookId = (Integer) tableModel.getValueAt(selectedRow, 0);
					Book book = new Book(
							BookId,
		    				tfTytul.getText(),
		    				tfAutor.getText(),
		    				tfWydawnictwo.getText(),
		    				Integer.parseInt(tfIlosc.getText()));
					controller.updateBook(book);
				}
			}
	    });
	    
	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
    				tfTytul.setText((String) tableModel.getValueAt(selectedRow, 1));
    				tfAutor.setText((String) tableModel.getValueAt(selectedRow, 2));
    				tfWydawnictwo.setText((String) tableModel.getValueAt(selectedRow, 3));
    				tfIlosc.setText("" + (Integer) tableModel.getValueAt(selectedRow, 4));
    			}
			}
	    });
	    
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.createHorizontalScrollBar();
	    scrollPane.setPreferredSize(new Dimension(1150, 450));
	    lTytul = new JLabel("Tytuł");
	    lAutor = new JLabel("Autor");
	    lWydawnictwo = new JLabel("Wydawnictwo");
	    lIlosc = new JLabel("Ilość");
	    tfTytul = new JTextField(15);
	    tfAutor = new JTextField(15);
	    tfWydawnictwo = new JTextField(15);
	    tfIlosc = new JTextField(10);
	    	
	    JPanel inputPanel = new JPanel();
	    JPanel inputPanel2 = new JPanel();
	    inputPanel.add(lTytul);
	    inputPanel.add(tfTytul);
	    inputPanel.add(lAutor);
	    inputPanel.add(tfAutor);
	    inputPanel.add(lWydawnictwo);
	    inputPanel.add(tfWydawnictwo);
	    inputPanel.add(lIlosc);
	    inputPanel.add(tfIlosc);
	    inputPanel2.add(dodajKlienta);
	    inputPanel2.add(usunKsiazke);
	    inputPanel2.add(edytujKsiazke);
	    this.add(scrollPane, BorderLayout.CENTER);
	    this.add(inputPanel, BorderLayout.SOUTH);
	    this.add(inputPanel2, BorderLayout.SOUTH);
	    inputPanel.setPreferredSize(new Dimension(1000,30));
	    inputPanel2.setPreferredSize(new Dimension(1200,30));	    
    
	}
	
	public void setController(BookController controller) {
		this.controller = controller;
	}
	
	public void refreshKsiazki(List<Book> books) {
		
		if (books.size() > 0){
			tableModel.getDataVector().clear();
			for (Book book : books) {
				tableModel.addRow(new Object[] {book.getId(), book.getTytul(), book.getAutor(),
						book.getWydawnictwo(), book.getIlosc()});
			}
		} else {
			tableModel.setRowCount(0);
		}
	}
	public static void listaksiazek() {
	    Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;

	    try{
	    	if(DAOFactory.baza == 1) {
	    		conn = SqliteDAOFactory.createConnection();	
	    	}
	    	else {
	    		conn = PostgresqlDAOFactory.createConnection();	
	    	}

	    	st = conn.createStatement();
	        String s = "select Tytul from book where Ilosc > 0 ORDER BY Tytul asc";
	        rs = st.executeQuery(s);
	        KlientView.ksiazka.removeAllItems();	    	        
	        while(rs.next())
	          {
	              KlientView.ksiazka.addItem(rs.getString(1));
	          }
	      }catch(Exception e1){
	          JOptionPane.showMessageDialog(null, "ERROR");
	      }finally{
	          try{
	              st.close();
	              rs.close();
	              conn.close();
	          }catch(Exception e1){
	              JOptionPane.showMessageDialog(null, "ERROR CLOSE");
	          }
	      }
	}
}
