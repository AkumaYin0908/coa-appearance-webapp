package com.coa.model.address;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Table(name="region")
public class Region extends Base{

//    @Id
//    @Column(name="region_code")
//    private String code;
//
//
//    @Column(name="region_name")
//    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "region",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Address> addresses;

    public Region(String code, String name) {
        super(code, name);
    }

    public void addAddress(Address address){
        if(addresses == null){
            addresses = new ArrayList<>();
        }
        address.setRegion(this);
        addresses.add(address);
    }

    public void removeAddress(Address address){
        address.setRegion(null);
        if(addresses!=null) addresses.remove(address);
    }
}
