package ru.otus.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.otus.crm.model.Phone;

import java.util.List;

@Table("client")
public class Client implements Persistable<String> {

    @Transient
    private final boolean isNew;

    @Id
    private Long id;

    private String name;

    private Address address;

    @MappedCollection(idColumn = "manager_id")
    private List<Phone> phones;

    public Client(String name, Address address, List<Phone> phones, boolean isNew) {
        this.isNew = isNew;
        this.id = null;
        this.name = name;
        this.address = address;
        this.phones = phones;

    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address=").append(address);
        sb.append(", phones=").append(phones);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
