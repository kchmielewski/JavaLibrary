package mvc.model;

public class Klient {

	private Integer id; 
	private String imie;
	private String nazwisko;
	private String miasto;
	private Integer rokUrodzenia;
	private String ksiazka;
	
	public Klient(Integer id, String imie, String nazwisko, String miasto, Integer rokUrodzenia, String ksiazka) {
		this(imie, nazwisko, miasto, rokUrodzenia, ksiazka);
		this.id = id;
	}
	
	public Klient(String imie, String nazwisko, String miasto, Integer rokUrodzenia, String ksiazka) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.miasto = miasto;
		this.rokUrodzenia = rokUrodzenia;
		this.ksiazka = ksiazka;
	}

	public Integer getId() {
		return id;
	}
	
	public String getImie() {
		return imie;
	}
	
	public String getNazwisko() {
		return nazwisko;
	}
	
	public String getMiasto() {
		return miasto;
	}
	
	public Integer getRokUrodzenia() {
		return rokUrodzenia;
	}
	
	public String getKsiazka() {
		return ksiazka;
	}

}

