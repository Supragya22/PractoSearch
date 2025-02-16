package com.Practo_Search.PractoSearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "specialities")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "specialities")
    @JsonIgnore
    private List<Doctor> doctors;

    @JsonIgnore
    @ManyToMany(mappedBy = "specialities")
    private List<Practice> practices;

    @JsonIgnore
    @OneToMany(mappedBy = "speciality")
    private List<Doctor_Speciality> doctorSpecialities;
    @JsonIgnore
    @OneToMany(mappedBy = "speciality")
    private List<Practice_Speciality> practiceSpecialities;

}

