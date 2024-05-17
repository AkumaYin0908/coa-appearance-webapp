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
@Table(name="barangay")
public class Barangay {

    @Id
    @Column(name="brgy_code")
    private String brgyCode;


    @Column(name="brgy_name")
    private String brgyName;

    @ToString.Exclude
    @OneToMany(mappedBy = "barangay",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Address> addresses;


    public Barangay(String brgyCode, String brgyName) {
        this.brgyCode = brgyCode;
        this.brgyName = brgyName;
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
