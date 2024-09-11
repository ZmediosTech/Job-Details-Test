package com.mbl.service;

import java.util.List;
import java.util.Map;

import com.mbl.entity.Audit;

public interface AuditService {

    List<Audit> getAudits();
    
    List<Audit> auditSave(String entityName, Long entityId, String changeType, Map<String, String> changes);
    
    void saveAuditDetails(List<Audit> audits);
}
