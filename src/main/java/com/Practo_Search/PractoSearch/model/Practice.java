package com.Practo_Search.PractoSearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<Doctor> doctors;

    @ManyToMany
    @JoinTable(name="practice_specialities",
            joinColumns = @JoinColumn(name = "practice_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    @JsonIgnore
    private List<Speciality> specialities;

    @OneToMany(mappedBy = "practice")
    private List<Doctor_Practice> doctorPractices;

    @OneToMany(mappedBy = "practice")

    private List<Practice_Speciality> practiceSpecialities;
}
