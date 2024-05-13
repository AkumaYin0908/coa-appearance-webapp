package com.coa.model.address;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="municipality")
public class Municipality {

    @Id
    @Column(name="mun_code")
    private String munCode;

    @Column(name="mun_name")
    private String munName;

    @ToString.Exclude
    @OneToMany(mappedBy = "municipality",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Address> addresses;

    public Municipality(String munCode, String munName) {
        this.munCode = munCode;
        this.munName = munName;
    }

    public void addAddress(Address address){
        if(addresses == null){
            addresses = new ArrayList<>();
        }
        address.setMunicipality(this);
        addresses.add(address);
    }

    public void removeAddress(Address address){
        address.setMunicipality(null);
        addresses.remove(address);
    }
}
