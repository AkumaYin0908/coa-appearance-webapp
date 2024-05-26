package com.coa.model.address;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Table(name="barangay")
public class Barangay extends Base{

//    @Id
//    @Column(name="brgy_code")
//    private String code;
//
//
//    @Column(name="brgy_name")
//    private String name;



    @ToString.Exclude
    @OneToMany(mappedBy = "barangay",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Address> addresses;

    public Barangay(String code, String name) {
        super(code, name);
    }

    public void addAddress(Address address){
        if(addresses == null){
            addresses = new ArrayList<>();
        }
        address.setBarangay(this);
        addresses.add(address);
    }

    public void removeAddress(Address address){
        address.setBarangay(null);
        if(addresses!=null) addresses.remove(address);
    }


}
