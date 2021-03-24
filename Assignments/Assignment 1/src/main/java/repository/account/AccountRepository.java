package repository.account;

import model.Account;
import model.Client;
import repository.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    List<Account> findAccountsByClientId(Long id);

    Account findAccountByIban(String iban) throws SQLException;

    Account findAccountById(Long id)throws SQLException;

    void deleteAccountByIban(String iban);

    boolean deleteAccountById(Long id);

    boolean addAccount(Account account);

    boolean update(Account account);

    boolean removeAll();
}
