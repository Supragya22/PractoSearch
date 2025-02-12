package com.Practo_Search.PractoSearch.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "doctor_specialities")
public class Doctor_Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

}
