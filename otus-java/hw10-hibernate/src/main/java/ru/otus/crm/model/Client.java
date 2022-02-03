package ru.otus.crm.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_client_address"))
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Phone> phones;

    public Client() {
    }

    public Client(String name, Address address, List<Phone> phones) {
        this.id = null;
        this.name = name;
        this.address = address;
        phones.forEach(phone -> phone.setClient(this));
        this.phones = phones;

    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        for (Phone phone : phones) {
            phone.setClient(this);
        }
        this.phones = phones;
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    private void setPhones(List<Phone> phones) {
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
}
