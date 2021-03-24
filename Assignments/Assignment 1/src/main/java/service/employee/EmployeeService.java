package service.employee;

import model.Account;
import model.Client;
import model.validation.Notification;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeService {

    Notification<Boolean> addClient(Client client);

    Notification<List<Client>> viewAll();

    Notification<Client> findByName(String name);

    Notification<Client> findById(Long id);

    Notification<Boolean> updateClient(Client client);

    Notification<Boolean> createAccount(Account account);

    Notification<List<Account>> findClientAccounts(Long id);

    Notification<Account> findAccountById(Long id);

    Notification<Boolean> updateAccount(Account account);

    Notification<Boolean> deleteAccount(Long id);

    String showAccountInfo(Account account);

    String showClientInfo(Client client);


}
