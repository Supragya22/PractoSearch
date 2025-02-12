package com.Practo_Search.PractoSearch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "practices")
public class Practice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  String name;

    private String address;

    private String state;

    private String city;

    private String website;

    @ManyToMany(mappedBy = "practices")
    private List<Doctor> doctors;

    @ManyToMany
    @JoinTable(name="practice_specialities",
            joinColumns = @JoinColumn(name = "practice_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<Speciality> specialities;
}
