package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;

    Client findByName(String name) throws SQLException;

    boolean saveClient(Client client);

    void removeAll();

    boolean update(Client client);
}
