package com.mbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbl.entity.FileDetails;

public interface FileDetailsRepository extends JpaRepository<FileDetails, Long> {
}
