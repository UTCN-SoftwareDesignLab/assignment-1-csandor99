package repository.client;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository repository;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass(){
        JDBConnectionWrapper connectionWrapper = DatabaseConnectionFactory.getConnectionWrapper(true);
        repository = new ClientRepositoryMySQL(connectionWrapper.getConnection(),accountRepository);
    }

    @Before
    public void setup() {
        repository.removeAll();
    }

    @Test
    public void findAll() {
        Client client = new ClientBuilder().setName("cristi").setIdNumber("101238457").setCardNumber("14825819556").setAddress("str poro").setPhone("0748650589").build();
        Client client2 = new ClientBuilder().setName("roli").setIdNumber("101258457").setCardNumber("14821819556").setAddress("str kosuth").setPhone("0745650589").build();
        repository.saveClient(client);
        repository.saveClient(client2);
        List<Client> clients = repository.findAll();
        Assert.assertTrue(clients.size()==2);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Client client = new ClientBuilder().setName("cristi").setIdNumber("101238457").setCardNumber("14825819556").setAddress("str poro").setPhone("0748650589").build();
        Client client2 = new ClientBuilder().setName("roli").setIdNumber("101258457").setCardNumber("14821819556").setAddress("str kosuth").setPhone("0745650589").build();
        repository.saveClient(client);
        repository.saveClient(client2);
        Assert.assertTrue(repository.findById(2L).getName().equals("roli"));
    }

    @Test
    public void findByName() throws SQLException {
        Client client = new ClientBuilder().setName("cristi").setIdNumber("101238457").setCardNumber("14825819556").setAddress("str poro").setPhone("0748650589").build();
        Client client2 = new ClientBuilder().setName("roli").setIdNumber("101258457").setCardNumber("14821819556").setAddress("str kosuth").setPhone("0745650589").build();
        repository.saveClient(client);
        repository.saveClient(client2);
        Assert.assertTrue(repository.findByName("cristi").getAddress().equals("str poro"));
    }

    @Test
    public void saveClient() {
        Client client = new ClientBuilder().setName("vasi").setIdNumber("101258454").setCardNumber("14821819554").setAddress("str rep").setPhone("0745650586").build();
        Assert.assertTrue(repository.saveClient(client));
    }

    @Test
    public void removeAll(){
        repository.removeAll();
        List<Client> clients = repository.findAll();
        Assert.assertTrue(clients.isEmpty());
    }

    @Test
    public void update() {
    }
}