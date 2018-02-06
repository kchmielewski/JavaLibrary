package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mvc.model.Book;

public class PostgresqlBookDAO implements IBookDAO {

	private static final Logger logger = Logger.getLogger(PostgresqlBookDAO.class);
    
	private static final String CREATE_QUERY = 
			"INSERT INTO book(tytul, autor, wydawnictwo, ilosc)  VALUES (?,?,?,?)";
    private static final String READ_QUERY = 
    		"SELECT id, tytul, autor, wydawnictwo, ilosc FROM book WHERE id = ?";
    private static final String READALL_QUERY = 
    		"SELECT id, tytul, autor, wydawnictwo, ilosc FROM book";
    private static final String UPDATE_QUERY = 
    		"UPDATE book SET tytul=?, autor=?, wydawnictwo=?,ilosc=? WHERE id = ?";
    private static final String DELETE_QUERY = 
    		"DELETE FROM book WHERE id = ?";
	private static final String CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS book(id SERIAL,tytul CHAR(25) PRIMARY KEY, autor CHAR(25), wydawnictwo CHAR(25), ilosc INT)";
	
	@Override
	public int create(Book book) {
		Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            conn = PostgresqlDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(CREATE_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTytul());
            preparedStatement.setString(2, book.getAutor());
            preparedStatement.setString(3, book.getWydawnictwo());
            preparedStatement.setInt(4, book.getIlosc());
            preparedStatement.execute();
            result = preparedStatement.getGeneratedKeys();
 
            if (result.next() && result != null) {
                return result.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                logger.error(rse.getMessage());
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                logger.error(sse.getMessage());
            }
            try {
                conn.close();
            } catch (Exception cse) {
                logger.error(cse.getMessage());
            }
        }
        return -1;
	}

	@Override
	public Book read(int id) {
		Book book = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
        	conn = PostgresqlDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(READ_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
 
            if (result.next() && result != null) {
            	book = new Book(result.getInt(1), 
            						result.getString(2), 
            						result.getString(3), 
            						result.getString(4), 
            						result.getInt(5));
            } else {
                // TODO
            	logger.info("Brak klienta o ID = "+ id);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                logger.error(rse.getMessage());
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                logger.error(sse.getMessage());
            }
            try {
                conn.close();
            } catch (Exception cse) {
                logger.error(cse.getMessage());
            }
        }
        return book;
	}

	@Override
	public List<Book> readAll() {
		List<Book> lista_ksiazek = new ArrayList<Book>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
        	conn = PostgresqlDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(READALL_QUERY);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            
            if (result != null){
            while(result.next())
            	lista_ksiazek.add(
            					new Book(
            							result.getInt(1), 
            							result.getString(2), 
            							result.getString(3), 
            							result.getString(4), 
            							result.getInt(5)
            							)
            					);
            } else {
                // TODO
            	logger.info("Brak książek");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                logger.error(rse.getMessage());
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                logger.error(sse.getMessage());
            }
            try {
                conn.close();
            } catch (Exception cse) {
                logger.error(cse.getMessage());
            }
        }
        return lista_ksiazek;
	}

	@Override
	public boolean update(Book book) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
        	conn = PostgresqlDAOFactory.createConnection();
            preparedStatement = conn.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, book.getTytul());
            preparedStatement.setString(2, book.getAutor());
            preparedStatement.setString(3, book.getWydawnictwo());
            preparedStatement.setInt(4, book.getIlosc());
            preparedStatement.setInt(5, book.getId());
            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                logger.error(sse.getMessage());
            }
            try {
                conn.close();
            } catch (Exception cse) {
                logger.error(cse.getMessage());
            }
        }
        return false;
	}

	@Override
	public boolean delete(int id) {
		Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    try {
	    	conn = PostgresqlDAOFactory.createConnection();
	    	preparedStatement = conn.prepareStatement(DELETE_QUERY);
	    	preparedStatement.setInt(1, id);
	    	preparedStatement.execute();
	    	return true;
	    } catch (SQLException e) {
	    	logger.error(e.getMessage());
	    } finally {
	    	try {
	    		preparedStatement.close();
	    	} catch (Exception sse) {
	    		logger.error(sse.getMessage());
	    	}
	    	try {
	    		conn.close();
	        } catch (Exception cse) {
	        	logger.error(cse.getMessage());
	        }
	    }
	    return false;
	}
	
	@Override
	public void createTable(){
		Connection conn = null;
		Statement stmt = null;
		try {
	    	conn = PostgresqlDAOFactory.createConnection();
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(CREATE_TABLE);
		} catch (SQLException e) {
	    	logger.error(e.getMessage());
		} finally {
	    	try {
	    		stmt.close();
	    	} catch (Exception sse) {
	    		logger.error(sse.getMessage());
	    	}
	    	try {
	    		conn.close();
	        } catch (Exception cse) {
	        	logger.error(cse.getMessage());
	        }
		}
	}
}
