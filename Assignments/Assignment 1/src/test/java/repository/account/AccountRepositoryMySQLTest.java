package repository.account;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {
    private static AccountRepository repository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass(){
        JDBConnectionWrapper connectionWrapper = DatabaseConnectionFactory.getConnectionWrapper(true);
        repository = new AccountRepositoryMySQL(connectionWrapper.getConnection());
        clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection(),repository);
    }

    @Before
    public void setup() {
        clientRepository.removeAll();
        repository.removeAll();
        Client client = new ClientBuilder().setName("cristi").setIdNumber("101238457").setCardNumber("14825819556").setAddress("str poro").setPhone("0748650589").build();
        Client client2 = new ClientBuilder().setName("roli").setIdNumber("101258457").setCardNumber("14821819556").setAddress("str kosuth").setPhone("0745650589").build();
        clientRepository.saveClient(client);
        clientRepository.saveClient(client2);
        Account account = new AccountBuilder().setIdClient(1L).setIban("RO00RZBR1234123412341234").setType("credit").setMoney(1000.0).setCreationDate(Date.valueOf(LocalDate.now())).build();
        Account account1 = new AccountBuilder().setIdClient(1L).setIban("RO00RZBR1234123412345678").setType("debit").setMoney(1000.0).setCreationDate(Date.valueOf(LocalDate.now())).build();
        repository.addAccount(account);
        repository.addAccount(account1);
    }

    @Test
    public void addAccount() {
        //Assert.assertEquals(0,repository.findAccountsByClientId(1L).size());
        Assert.assertEquals(2,repository.findAccountsByClientId(1L).size());
    }

    @Test
    public void findAccountsByClientId() {
        Assert.assertEquals(2,repository.findAccountsByClientId(1L).size());
        Assert.assertEquals(0, repository.findAccountsByClientId(2L).size());
    }

    @Test
    public void findAccountByIban() throws SQLException {
        Assert.assertEquals("RO00RZBR1234123412341234",repository.findAccountByIban("RO00RZBR1234123412341234").getIban());
    }

    @Test
    public void findAccountById() throws SQLException {
        Assert.assertEquals("RO00RZBR1234123412341234",repository.findAccountById(1L).getIban());
    }

    @Test
    public void update() throws SQLException {
        Account account = repository.findAccountById(1L);
        account.setMoney(500.0);
        repository.update(account);
        Assert.assertEquals(Double.valueOf(500.0),repository.findAccountById(1L).getMoney());
    }

    @Test
    public void deleteAccountByIban() {
        repository.deleteAccountByIban("RO00RZBR1234123412345678");
        Assert.assertEquals(1,repository.findAccountsByClientId(1L).size());
    }

    @Test
    public void deleteAccountById() {
        repository.deleteAccountById(1L);
        Assert.assertEquals(1,repository.findAccountsByClientId(1L).size());
    }


}