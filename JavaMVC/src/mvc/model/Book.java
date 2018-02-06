package mvc.model;

public class Book {

	private Integer id; 
	private String tytul;
	private String autor;
	private String wydawnictwo;
	private Integer ilosc;
	
	public Book(Integer id, String tytul, String autor, String wydawnictwo, Integer ilosc) {
		this(tytul, autor, wydawnictwo, ilosc);
		this.id = id;
	}
	
	public Book(String tytul, String autor, String wydawnictwo, Integer ilosc) {
		this.tytul = tytul;
		this.autor = autor;
		this.wydawnictwo = wydawnictwo;
		this.ilosc = ilosc;
	}

	public Integer getId() {
		return id;
	}
	
	public String getTytul() {
		return tytul;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public String getWydawnictwo() {
		return wydawnictwo;
	}
	
	public Integer getIlosc() {
		return ilosc;
	}
	

}

