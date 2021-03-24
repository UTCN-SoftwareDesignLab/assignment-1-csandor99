package service.employee;

import model.Account;
import model.Client;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeServiceMySQL implements EmployeeService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public EmployeeServiceMySQL(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> addClient(Client client) {
        Notification<Boolean> notification = new Notification<>();

        ClientValidator clientValidator = new ClientValidator(client);
        boolean valid = clientValidator.validate();

        if(!valid){
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        } else {
            notification.setResult(clientRepository.saveClient(client));
        }

        return notification;
    }

    @Override
    public Notification<List<Client>> viewAll() {
        Notification<List<Client>> notification = new Notification<>();

        notification.setResult(clientRepository.findAll());
        return notification;
    }

    @Override
    public Notification<Client> findByName(String name) {
        Notification<Client> notification = new Notification<>();

        try {
            notification.setResult(clientRepository.findByName(name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public Notification<Client> findById(Long id) {
        Notification<Client> notification = new Notification<>();

        try {
            notification.setResult(clientRepository.findById(id));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public Notification<Boolean> updateClient(Client client) {
        Notification<Boolean> notification = new Notification<>();

        ClientValidator clientValidator = new ClientValidator(client);
        boolean valid = clientValidator.validate();

        if(!valid){
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        } else {
            notification.setResult(clientRepository.update(client));
        }

        return notification;
    }

    @Override
    public Notification<Boolean> createAccount(Account account) {
        Notification<Boolean> notification = new Notification<>();

        AccountValidator accountValidator = new AccountValidator(account);
        boolean valid = accountValidator.validate();

        if(!valid){
            accountValidator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        } else {
            account.setCreationDate(Date.valueOf(LocalDate.now()));
            notification.setResult(accountRepository.addAccount(account));
        }

        return notification;
    }

    @Override
    public Notification<List<Account>> findClientAccounts(Long id) {
        Notification<List<Account>> notification = new Notification<>();

        notification.setResult(accountRepository.findAccountsByClientId(id));

        return notification;
    }

    @Override
    public Notification<Account> findAccountById(Long id) {
        Notification<Account> notification = new Notification<>();

        try {
            notification.setResult(accountRepository.findAccountById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public Notification<Boolean> updateAccount(Account account) {
        Notification<Boolean> notification = new Notification<>();

        notification.setResult(accountRepository.update(account));

        return notification;
    }

    @Override
    public Notification<Boolean> deleteAccount(Long id) {
        Notification<Boolean> notification = new Notification<>();

        notification.setResult(accountRepository.deleteAccountById(id));

        return notification;
    }

    @Override
    public String showAccountInfo(Account account) {
        return "Id: " + account.getId() + "\n"
                + "Iban: " + account.getIban() + "\n"
                + "Account type: " + account.getType() + "\n"
                + "Account balance: " + account.getMoney() + "\n"
                + "Creation date: " + account.getCreationDate() + "\n";
    }

    @Override
    public String showClientInfo(Client client) {
        return "Id: " + client.getId() + "\n"
                + "Name: " + client.getName() + "\n"
                + "Card number: " + client.getCardNumber() + "\n"
                + "Personal numeric code: " + client.getIdNumber() + "\n"
                + "Phone: " + client.getPhone() + "\n"
                + "Address: " + client.getAddress() + "\n";
    }


}
