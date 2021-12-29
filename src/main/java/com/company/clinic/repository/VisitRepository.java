package com.company.clinic.repository;

import com.company.clinic.model.visit.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    boolean existsByDoctor_IdAndPatient_IdAndVisitTime(long doctorId, long patientId, LocalDateTime visitTime);

    boolean existsByDoctor_IdAndVisitTimeBetween(long doctorId, LocalDateTime min, LocalDateTime max);

    boolean existsByPatient_IdAndVisitTimeBetween(long patientId, LocalDateTime min, LocalDateTime max);

    List<Visit> findByDoctor_Id(long doctorId);

}