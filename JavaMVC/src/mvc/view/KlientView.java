package mvc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.*;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import mvc.controller.KlientController;
import mvc.dao.DAOFactory;
import mvc.dao.PostgresqlBookDAO;
import mvc.dao.PostgresqlDAOFactory;
import mvc.dao.PostgresqlKlientDAO;
import mvc.dao.SqliteDAOFactory;
import mvc.model.Book;
import mvc.model.Klient;
import mvc.repository.KlientRepo;
import mvc.controller.BookController;

public class KlientView extends JPanel {
	private static final Logger logger = Logger.getLogger(PostgresqlKlientDAO.class);
	private static final long serialVersionUID = 1L;

	private static final Object[] COLUMN_NAMES = {"ID", "Imie", "Nazwisko", "Miasto", "Rok urodzenia", "Ksiazka"};

	private KlientController controller;
	private JLabel lImie, lNazwisko, lMiasto, lRokUrodzenia, lKsiazka;
	private JTextField tfImie, tfNazwisko, tfMiasto, tfRokUrodzenia,tfCos;
	private JTable table;
	private DefaultTableModel tableModel;
	 static JComboBox ksiazka = new JComboBox();

	public KlientView() {
		super();


	    tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
	    table = new JTable(tableModel);
        table.setSize(new Dimension(1200, 600));
	    table.setPreferredSize(new Dimension(0,2000));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        table.setRowHeight(table.getRowHeight() + 50);
	    table.setAutoCreateRowSorter(true); 
	    
	    JButton dodajKlienta = new JButton("Dodaj klienta");
	    dodajKlienta.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		Klient klient = new Klient(
	    				tfImie.getText(),
	    				tfNazwisko.getText(),
	    				tfMiasto.getText(),
	    				Integer.parseInt(tfRokUrodzenia.getText()),
	    				(String)ksiazka.getSelectedItem());
	    		controller.insertKlient(klient);
	    		Odejmijilosc();
	    		BookView.listaksiazek();
	    	
	    	}
	    });
	    	

	    JButton usunKlienta = new JButton("Usuń klienta");
	    usunKlienta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					Integer klientId = (Integer) tableModel.getValueAt(selectedRow, 0);
					controller.deleteKlient(klientId);
				}
				Dodajilosc();
				BookView.listaksiazek();
				}
	    });
	    
	    JButton edytujKlienta = new JButton("Edytuj klienta");
	    edytujKlienta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					Integer klientId = (Integer) tableModel.getValueAt(selectedRow, 0);
					Klient klient = new Klient(
							klientId,
		    				tfImie.getText(),
		    				tfNazwisko.getText(),
		    				tfMiasto.getText(),
		    				Integer.parseInt(tfRokUrodzenia.getText()),
		    				tfCos.getText());
					controller.updateKlient(klient);
				}
			}
	    });
	    
	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
    				tfImie.setText((String) tableModel.getValueAt(selectedRow, 1));
    				tfNazwisko.setText((String) tableModel.getValueAt(selectedRow, 2));
    				tfMiasto.setText((String) tableModel.getValueAt(selectedRow, 3));
    				tfRokUrodzenia.setText("" + (Integer) tableModel.getValueAt(selectedRow, 4));
    				ksiazka.setSelectedItem((String) tableModel.getValueAt(selectedRow, 5));
					tfCos.setText((String) tableModel.getValueAt(selectedRow, 5));}
			}
	    });
	    
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setPreferredSize(new Dimension(1150, 450));
	    
	    lImie = new JLabel("Imię");
	    lNazwisko = new JLabel("Nazwisko");
	    lMiasto = new JLabel("Miasto");
	    lRokUrodzenia = new JLabel("Rok urodzenia");
	    lKsiazka = new JLabel("Ksiazka");
	    tfImie = new JTextField(15);
	    tfNazwisko = new JTextField(15);
	    tfMiasto = new JTextField(10);
	    tfRokUrodzenia = new JTextField(4);
	    tfCos = new JTextField(40);
	    tfCos.hide();
	    Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;
	    ksiazka.setBounds(100,100,100,100);

	    try{
	    	if(DAOFactory.baza == 1) {
	    		conn = SqliteDAOFactory.createConnection();	
	    	}
	    	else {
	    		conn = PostgresqlDAOFactory.createConnection();	
	    	}
	        st = conn.createStatement();
	        String s = "select Tytul from book where ilosc >0 ORDER BY Tytul asc";
	        rs = st.executeQuery(s);
	          while(rs.next())
	          {
	              ksiazka.addItem(rs.getString(1));
	          }
	      }catch(Exception e){
	          JOptionPane.showMessageDialog(null, "ERROR");
	      }finally{
	          try{
	              st.close();
	              rs.close();
	              conn.close();
	          }catch(Exception e){
	              JOptionPane.showMessageDialog(null, "ERROR CLOSE");
	          }
	      }
	    
	    JPanel inputPanel = new JPanel();
	    JPanel inputPanel2 = new JPanel();
	    inputPanel.add(lImie);
	    inputPanel.add(tfImie);
	    inputPanel.add(lNazwisko);
	    inputPanel.add(tfNazwisko);
	    inputPanel.add(lMiasto);
	    inputPanel.add(tfMiasto);
	    inputPanel.add(lRokUrodzenia);
	    inputPanel.add(tfRokUrodzenia);
	    inputPanel.add(lKsiazka);
	    inputPanel.add(ksiazka);
	    inputPanel.add(tfCos);
	    inputPanel2.add(dodajKlienta);
	    inputPanel2.add(usunKlienta);
	    inputPanel2.add(edytujKlienta);
	    this.add(scrollPane, BorderLayout.CENTER);
	    this.add(inputPanel, BorderLayout.SOUTH);
	    this.add(inputPanel2, BorderLayout.SOUTH);
	    inputPanel.setPreferredSize(new Dimension(1400,30));
	    inputPanel2.setPreferredSize(new Dimension(1200,30));	
	}

	public void setController(KlientController controller) {
		this.controller = controller;
	}
	
	public void refreshKlienci(List<Klient> klienci) {
		
		if (klienci.size() > 0){
			tableModel.getDataVector().clear();
			for (Klient klient : klienci) {
				tableModel.addRow(new Object[] {klient.getId(), klient.getImie(), klient.getNazwisko(),
						klient.getMiasto(), klient.getRokUrodzenia(), klient.getKsiazka()});
			}
		} else {
			tableModel.setRowCount(0);
		}
	}
	
	public void Odejmijilosc() {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
        try{
	    	if(DAOFactory.baza == 1) {
	    		conn = SqliteDAOFactory.createConnection();	
	    	}
	    	else {
	    		conn = PostgresqlDAOFactory.createConnection();	
	    	}

	        String s = "update book set ilosc = ilosc-1 where tytul = ?";
	        pst = conn.prepareStatement(s,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, (String) ksiazka.getSelectedItem());
	        pst.execute();
            rs = pst.getGeneratedKeys();
       
            if (rs.next() && rs != null) {
            	return;
            }
	    	        
	      }catch(Exception e1){
	            logger.error("cos");;
	      }finally{
	          try{
	              pst.close();
	              rs.close();
	              conn.close();
	          }catch(Exception e1){
	              JOptionPane.showMessageDialog(null, "ERROR CLOSE");
	          }
	      }
	}
	public void Dodajilosc() {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
        try{
	    	if(DAOFactory.baza == 1) {
	    		conn = SqliteDAOFactory.createConnection();	
	    	}
	    	else {
	    		conn = PostgresqlDAOFactory.createConnection();	
	    	}

	        String s = "update book set ilosc = ilosc+1 where tytul = ?";
	        pst = conn.prepareStatement(s,Statement.RETURN_GENERATED_KEYS);
	        pst.setString(1, tfCos.getText());	     
	        pst.execute();
            rs = pst.getGeneratedKeys();

            if (rs.next() && rs != null) {
            	return;
            }
	    	        
	      }catch(Exception e1){
	            logger.error("coś nie działa");;
	      }finally{
	          try{
	              pst.close();
	              rs.close();
	              conn.close();
	          }catch(Exception e1){
	              JOptionPane.showMessageDialog(null, "ERROR CLOSE");
	          }
	      }
        
	}
}
