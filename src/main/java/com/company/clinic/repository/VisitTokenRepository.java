package com.company.clinic.repository;

import com.company.clinic.model.visit.VisitToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitTokenRepository extends JpaRepository<VisitToken, Long> {

    Optional<VisitToken> findByToken(String token);
}