package com.Practo_Search.PractoSearch.model;

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
    private List<Doctor> doctors;

//    @ManyToMany(mappedBy = "specialities")
//    private List<Practice> practices;


}

