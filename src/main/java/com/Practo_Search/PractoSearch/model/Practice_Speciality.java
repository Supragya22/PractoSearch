package com.Practo_Search.PractoSearch.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "practice_specialities")
public class Practice_Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "practice_id", nullable = false)
    private Practice practice;

    @ManyToOne
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

}
