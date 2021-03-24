package model.validation;

import model.Client;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private final Client client;
    private final List<String> errors;

    public ClientValidator(Client client){
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate(){
        validateCardNumber(client.getCardNumber());
        validatePnc(client.getIdNumber());
        validatePhone(client.getPhone());
        return errors.isEmpty();
    }

    private void validatePhone(String phone) {
        if(phone.length() != 10){
            errors.add("Phone number invalid");
        }
        if(!onlyDigits(phone)){
            errors.add("Phone number must contain only digits");
        }
    }

    private void validatePnc(String idNumber) {
        if(idNumber.length() != 13){
            errors.add("Personal numeric code invalid! Must have 13 digits");
        }

        if(!onlyDigits(idNumber)){
            errors.add("Personal numeric code must contain only digits");
        }
    }

    private void validateCardNumber(String cardNumber) {
        if(cardNumber.length() != 16){
            errors.add("Card number invalid! Must have 16 digits");
        }

        if(!onlyDigits(cardNumber)){
            errors.add("Card number must contain only digits");
        }
    }

    public List<String> getErrors(){
        return this.errors;
    }

    private boolean onlyDigits(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}
