package com.mbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbl.entity.Audit;

public interface AuditRepository extends JpaRepository<Audit, Long>{

    
}
