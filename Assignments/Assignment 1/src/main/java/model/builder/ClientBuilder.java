package model.builder;

import model.Account;
import model.Client;

import java.util.List;

public class ClientBuilder {
    private final Client client;

    public ClientBuilder (){
        client = new Client();
    }

    public ClientBuilder setId(Long id){
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }

    public ClientBuilder setCardNumber(String cardNumber){
        client.setCardNumber(cardNumber);
        return this;
    }

    public ClientBuilder setIdNumber(String idNumber){
        client.setIdNumber(idNumber);
        return this;
    }

    public ClientBuilder setPhone(String phone){
        client.setPhone(phone);
        return this;
    }

    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setAccounts(List<Account> accounts){
        client.setAccounts(accounts);
        return this;
    }

    public Client build(){
        return client;
    }
}
