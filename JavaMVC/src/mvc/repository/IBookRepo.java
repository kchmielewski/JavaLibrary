package mvc.repository;

	import java.util.List;

import mvc.model.Book;

public interface IBookRepo {
	
	public List<Book> getBooks();
	public void insertBook(Book book);
	public void updateBook(Book book);
	public void deleteBook(Integer bookId);
	
	public void createTableBook();
}
