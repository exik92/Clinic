package com.company.clinic.repository;

import com.company.clinic.model.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByNip(long nip);

    Optional<Doctor> findById(long id);

    boolean existsByNip(long nip);

}