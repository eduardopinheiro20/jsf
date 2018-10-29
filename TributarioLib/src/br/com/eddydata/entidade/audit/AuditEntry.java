/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.audit;

import br.com.eddydata.entidade.admin.Usuario;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eddylucas
 */
@Entity
@Table(name = "audit_entry")
public class AuditEntry implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String UPDATE_OPERATION = "EDIÇÃO";
    public static final String INSERT_OPERATION = "INSERÇÃO";
    public static final String DELETE_OPERATION = "EXCLUSÃO";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Column
    protected Integer baseEntityId;
    
    @Column
    protected String baseFieldName;
    
    @Column
    protected String operation;
    
    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date operationTime;
    
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "auditEntry")
    protected Collection<AuditField> fields;
    
    @Column(length = 100)
    protected String tableName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    protected Usuario user;
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Integer getBaseEntityId()
    {
        return baseEntityId;
    }
    public void setBaseEntityId(Integer resourceEntityId)
    {
        this.baseEntityId = resourceEntityId;
    }
    
    public String getOperation()
    {
        return operation;
    }
    public void setOperation(String action)
    {
        this.operation = action;
    }
    
    public Date getOperationTime()
    {
        return operationTime;
    }
    public void setOperationTime(Date operationTime)
    {
        this.operationTime = operationTime;
    }
    
    public Collection<AuditField> getFields()
    {
        return fields;
    }
    
    public void setFields(Collection<AuditField> fields)
    {
        this.fields = fields;
    }
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getBaseFieldName() {
        return baseFieldName;
    }

    public void setBaseFieldName(String baseFieldName) {
        this.baseFieldName = baseFieldName;
    }
    
    /**
     * Set hashCode to entity's ID
     */
    public int hashCode()
    {
        return id.intValue();
    }
    
    /**
     * Assign equivalence based on entity's ID
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof AuditEntry)
        {
            if (((AuditEntry) obj).getId() == id)
                return true;
        }
        return false;
    }
}
