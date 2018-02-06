package mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class PostgresqlDAOFactory extends DAOFactory {
	private static final Logger logger = Logger
            .getLogger(PostgresqlDAOFactory.class);

    public static final String DRIVER = "org.postgresql.Driver";
    public static final String DBURL = "jdbc:postgresql:test.db";
    public static final String login = "admin";
    public static final String password = "admin";
    
    
    public static Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DBURL,login,password);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return conn;
    }
    @Override
	public IBookDAO getBookDAO() {
		return new PostgresqlBookDAO();
	}
	@Override
	public IKlientDAO getKlientDAO() {
		return new PostgresqlKlientDAO();
	}

}
