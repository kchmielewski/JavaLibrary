package mvc;

import org.apache.log4j.PropertyConfigurator;

import mvc.controller.KlientController;
import mvc.dao.PostgresqlDAOFactory;
import mvc.repository.KlientRepo;
import mvc.repository.IKlientRepo;
import mvc.view.KlientView;
import java.sql.*;


public class KlientDBApplication {

	public static void main(String[] args) {
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);

		KlientRepo model = new KlientRepo();
		model.createTableKlient();
		KlientView view = new KlientView();
		new KlientController(model, view);
		
		view.setVisible(true);
	}
}
