package com.coa.model.address;


import com.coa.model.Agency;
import com.coa.model.Visitor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "region")
    private  Region region;

    @OneToMany(mappedBy = "address",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Visitor> visitors;
}
