/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.audit;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author eddylucas
 */
@Entity
@Table(name = "audit_field")
public class AuditField implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String fieldName;
    
    @Column
    @Lob
    private String fieldValue;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUDITENTRYID")
    private AuditEntry auditEntry;
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getFieldName()
    {
        return fieldName;
    }
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
    
    public String getFieldValue()
    {
        return fieldValue;
    }
    public void setFieldValue(String fieldValue)
    {
        this.fieldValue = fieldValue;
    }
    
    public AuditEntry getAuditEntry()
    {
        return auditEntry;
    }
    public void setAuditEntry(AuditEntry auditEntry)
    {
        this.auditEntry = auditEntry;
    }
}
