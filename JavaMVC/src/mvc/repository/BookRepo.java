package mvc.repository;

import java.util.List;

import mvc.dao.DAOFactory;
import mvc.dao.IBookDAO;
import mvc.model.Book;

public class BookRepo implements IBookRepo{
	DAOFactory Factory = DAOFactory.getDAOFactory(DAOFactory.baza);
    IBookDAO bookDAO = Factory.getBookDAO();
 
	@Override
	public List<Book> getBooks() {
        return bookDAO.readAll();
	}
	
	@Override
	public void insertBook(Book book) {
        bookDAO.create(book);
	}

	@Override
	public void createTableBook() {
		bookDAO.createTable();
	}
	
	
	public void updateBook(Book book) {
		bookDAO.update(book);	
	}
	
	public void deleteBook(Integer bookId) {
		bookDAO.delete(bookId);
	}
}
