package mvc;

import org.apache.log4j.PropertyConfigurator;

import mvc.controller.BookController;
import mvc.repository.BookRepo;
import mvc.view.BookView;

public class CreateBook {

	public static void main(String[] args) {
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
		BookRepo model = new BookRepo();
		BookView view = new BookView();
		new BookController(model, view);
		
		view.setVisible(true);
	}
}
