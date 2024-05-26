package com.coa.model.address;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Table(name="province")
public class Province extends Base {

//    @Id
//    @Column(name="pro_code")
//    private String code;
//
//
//    @Column(name="pro_name")
//    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "province",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Address> addresses;


    public Province(String code, String name) {
        super(code, name);
    }

    public void addAddress(Address address){
        if(addresses == null){
            addresses = new ArrayList<>();
        }

        address.setProvince(this);
        addresses.add(address);
    }

    public void removeAddress(Address address){
        address.setProvince(null);
        if(addresses!=null) addresses.remove(address);
    }


}
