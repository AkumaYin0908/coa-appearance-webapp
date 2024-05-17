package com.coa.model.address;


import com.coa.model.Agency;
import com.coa.model.Visitor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barangay")
    private Barangay barangay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality")
    private Municipality municipality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province")
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region")
    private  Region region;

    @ToString.Exclude
    @OneToMany(mappedBy = "address",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Visitor> visitors;


    public Address(Long id, Barangay barangay, Municipality municipality, Province province, Region region) {
        this.id = id;
        this.barangay = barangay;
        this.municipality = municipality;
        this.province = province;
        this.region = region;
    }

    public void addVisitor(Visitor visitor){
        if(visitors == null){
            visitors = new ArrayList<>();
        }

        visitor.setAddress(this);
        visitors.add(visitor);
    }

    public void removeVisitor(Visitor visitor){
        visitor.setAddress(null);
        if(visitors !=null) visitors.remove(visitor);
    }
}
