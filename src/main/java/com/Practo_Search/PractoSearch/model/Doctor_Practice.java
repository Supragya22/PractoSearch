package com.Practo_Search.PractoSearch.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "doctor_practices")
public class Doctor_Practice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "practice_id", nullable = false)
    private Practice practice;

}

