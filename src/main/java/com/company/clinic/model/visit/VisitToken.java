package com.company.clinic.model.visit;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VisitToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "visitId", referencedColumnName = "id")
    private Visit visit;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public VisitToken() {
    }

    public VisitToken(String token, Visit visit, LocalDateTime expiryDate) {
        this.token = token;
        this.visit = visit;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}