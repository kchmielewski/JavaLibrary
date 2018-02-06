package mvc.controller;

import java.util.List;

import mvc.model.Book;
import mvc.repository.BookRepo;
import mvc.view.BookView;

public class BookController {

	private BookRepo model;
	private BookView view;
	
	public BookController(BookRepo model, BookView view) {
		this.model = model;
		this.view = view;
		
		view.setController(this);
		refreshKsiazki();
	}
	
	public void insertBook(Book book) {
		model.insertBook(book);
		refreshKsiazki();
	}
	
	public void updateBook(Book book) {
		model.updateBook(book);
		refreshKsiazki();
	}
	
	public void deleteBook(Integer bookId) {
		model.deleteBook(bookId);
		refreshKsiazki();
	}
	
	public void refreshKsiazki() {
		List<Book> books = model.getBooks();
		view.refreshKsiazki(books);
	}
}

