package ru.otus.service;

import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

public class ClientTemplate {
    private final String id;
    private final String name;
    private final String address;
    private final String phones;


    public ClientTemplate(Client client) {
        this.id = client.getId().toString();
        this.name = client.getName();
        this.address = client.getAddress().getStreet();
        this.phones = String.join("; ",
                client.getPhones()
                        .stream()
                        .map(Phone::getNumber)
                        .toList());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhones() {
        return phones;
    }
}
