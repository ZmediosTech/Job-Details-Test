package com.mbl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbl.entity.ContainerDetails;
import com.mbl.repository.ContainerDetailsRepo;

@Service
public class ContainerDetailsService {

    @Autowired
    private ContainerDetailsRepo containerRepository;
    
    public List<ContainerDetails> getContainters()
    {
	
	return containerRepository.findAll();
    }
}
