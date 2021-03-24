package model.validation;

import model.Account;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AccountValidator {
    private final Account account;
    private final List<String> errors;
    private static final String IBAN_REGEX = "^[a-zA-Z]{2}[0-9]{2}\\s?[a-zA-Z0-9]{4}\\s?[0-9]{4}\\s?[0-9]{3}([a-zA-Z0-9]\\s?[a-zA-Z0-9]{0,4}\\s?[a-zA-Z0-9]{0,4}\\s?[a-zA-Z0-9]{0,4}\\s?[a-zA-Z0-9]{0,3})?$";

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public boolean validate(){
        validateIban();
        validateType();
        validateMoney();
        return errors.isEmpty();
    }

    private void validateMoney() {
        if(account.getMoney() == null){
            errors.add("Not a valid balance!");
        }
    }

    private void validateType() {
        String[] typesStr = new String[]{"credit", "debit"};
        List<String> types = Arrays.asList(typesStr);
        if(!types.contains(account.getType())){
            errors.add("Not a valid type of account (ex: credit, debit)");
        }
    }

    private void validateIban() {
        if(!Pattern.compile(IBAN_REGEX).matcher(account.getIban()).matches()){
            errors.add("Invalid iban!");
        }
    }

    public List<String> getErrors(){
        return this.errors;
    }

}
