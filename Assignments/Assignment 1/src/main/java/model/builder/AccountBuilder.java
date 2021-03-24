package model.builder;

import model.Account;

import java.time.LocalDate;
import java.util.Date;

public class AccountBuilder {
    private final Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setIdClient(Long id){
        account.setIdClient(id);
        return this;
    }

    public AccountBuilder setIban(String iban) {
        account.setIban(iban);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setMoney(Double money){
        account.setMoney(money);
        return this;
    }

    public AccountBuilder setCreationDate(Date date){
        account.setCreationDate(date);
        return this;
    }

    public Account build() {
        return account;
    }
}
