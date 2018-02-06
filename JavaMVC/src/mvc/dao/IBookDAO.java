package mvc.dao;

import java.util.List;
import mvc.model.Book;

public interface IBookDAO {
    
    public int create(Book book);
    
    public void createTable();
 
    public Book read(int id);

    public List<Book> readAll();
 
    public boolean update(Book book);
 
    public boolean delete(int id);
}
