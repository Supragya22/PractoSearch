package com.Practo_Search.PractoSearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int experience;

    private String qualifications;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="doctor_specialities",
    joinColumns = @JoinColumn(name = "doctor_id"),
    inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    @JsonIgnore
    private List<Speciality> specialities;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="doctor_practices",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "practice_id"))
    @JsonIgnore
    private List<Practice> practices;

    @OneToMany(mappedBy = "doctor")
    private List<Doctor_Speciality> doctorSpecialities;

    @OneToMany(mappedBy = "doctor")
    private List<Doctor_Practice> doctorPractices;
}
