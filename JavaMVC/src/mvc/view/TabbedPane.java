package mvc.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.PropertyConfigurator;
import mvc.controller.KlientController;
import mvc.repository.KlientRepo;
import mvc.view.KlientView;
import mvc.controller.BookController;
import mvc.repository.BookRepo;
import mvc.view.BookView;

public class TabbedPane {

	
    JFrame frame = new JFrame();
    JTabbedPane tabbedPane = new JTabbedPane();


    public TabbedPane() {
        frame.setSize(1200, 600);


		BookRepo modelbook = new BookRepo();
    	modelbook.createTableBook();
    	BookView book = new BookView();
    	new BookController(modelbook, book);        
        
		KlientRepo modelklient = new KlientRepo();
		modelklient.createTableKlient();
		KlientView klient = new KlientView();
		new KlientController(modelklient, klient);
		
    	tabbedPane.add("Klient", klient);
        tabbedPane.add("Book", book);

        frame.getContentPane().add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TabbedPane();
            }
        });
    }

}