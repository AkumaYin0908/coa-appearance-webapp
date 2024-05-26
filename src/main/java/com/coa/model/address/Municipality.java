package com.coa.model.address;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Table(name="municipality")
public class Municipality extends Base{

//    @Id
//    @Column(name="mun_code")
//    private String code;
//
//    @Column(name="mun_name")
//    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "municipality",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Address> addresses;

    public Municipality(String code, String name) {
        super(code, name);
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
        if(addresses!=null) addresses.remove(address);
    }
}
