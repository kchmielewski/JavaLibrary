package mvc.dao;

import mvc.dao.IKlientDAO;
import mvc.dao.IBookDAO;

public abstract class DAOFactory {

	public static final int POSTGRESQL = 0;  
    public static final int SQLITE = 1;
    public static int baza = DAOFactory.POSTGRESQL;
    public abstract IKlientDAO getKlientDAO();
    public abstract IBookDAO getBookDAO();


    public static DAOFactory getDAOFactory(int database) {

        switch (database) {
        case POSTGRESQL:
            return new PostgresqlDAOFactory();
        case SQLITE:
             return new SqliteDAOFactory();
        default:
            return null;
        }
    }

}
