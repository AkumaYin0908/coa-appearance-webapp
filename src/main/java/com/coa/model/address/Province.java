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
@Table(name="province")
public class Province {

    @Id
    @Column(name="pro_code")
    private String proCode;


    @Column(name="pro_name")
    private String proName;

    @ToString.Exclude
    @OneToMany(mappedBy = "province",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Address> addresses;

    public Province(String proCode, String proName) {
        this.proCode = proCode;
        this.proName = proName;
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
