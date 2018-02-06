package mvc.repository;

import java.util.List;

import mvc.dao.DAOFactory;
import mvc.dao.IKlientDAO;
import mvc.model.Klient;

public class KlientRepo implements IKlientRepo{
	DAOFactory Factory = DAOFactory.getDAOFactory(DAOFactory.baza);
    IKlientDAO klientDAO = Factory.getKlientDAO();
    
    
	@Override
	public List<Klient> getKlienci() {
        return klientDAO.readAll();
	}
	
	@Override
	public void insertKlient(Klient klient) {
        klientDAO.create(klient);
	}

	@Override
	public void createTableKlient() {
		klientDAO.createTable();
	}
	
	
	public void updateKlient(Klient klient) {
		klientDAO.update(klient);	
	}
	
	public void deleteKlient(Integer klientId) {
		klientDAO.delete(klientId);
	}

}

