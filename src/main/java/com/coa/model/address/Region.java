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
@Table(name="region")
public class Region {

    @Id
    @Column(name="region_code")
    private String regionCode;


    @Column(name="region_name")
    private String regionName;

    @ToString.Exclude
    @OneToMany(mappedBy = "region",cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Address> addresses;

    public Region(String regionCode, String regionName) {
        this.regionCode = regionCode;
        this.regionName = regionName;
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
