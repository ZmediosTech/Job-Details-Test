package com.mbl.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbl.entity.Audit;
import com.mbl.repository.AuditRepository;
import com.mbl.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public List<Audit> getAudits() {
	
	return auditRepository.findAll();
    }

    @Override
    public void saveAuditDetails(List<Audit> audits)
    {
	auditRepository.saveAll(audits);
    }

    @Override
    public List<Audit> auditSave(String entityName, Long entityId, String changeType, Map<String, String> changes){
	List<Audit> listAudits= new ArrayList<>();
	
	for (Map.Entry<String, String> entry : changes.entrySet()) {
            Audit audit = new Audit();
            audit.setEntityName(entityName);
            audit.setEntityId(entityId);
            audit.setFieldName(entry.getKey());
            audit.setOldValue(entry.getValue().split(",")[0]); 
            audit.setNewValue(entry.getValue().split(",")[1]); 
            audit.setChangeDate(LocalDateTime.now());
            audit.setChangeType(changeType);
            listAudits.add(audit);
           
        }
	return listAudits;
    }

}
