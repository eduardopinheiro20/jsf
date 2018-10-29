/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geral;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author d.morais
 */
@Entity
@Table(name = "deficiencia_pessoa")
public class DeficienciaPessoa implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
  
    @Column (name = "fisica")
    private Boolean fisica;
    
    @Column (name = "mental")
    private Boolean mental;
    
    @Column (name = "auditiva")
    private Boolean auditiva;
    
    @Column (name = "visual")
    private Boolean visual;
   
    @Column(name = "observacao",length = 255)
    private String observacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFisica() {
        return fisica;
    }

    public void setFisica(Boolean fisica) {
        this.fisica = fisica;
    }

    public Boolean getMental() {
        return mental;
    }

    public void setMental(Boolean mental) {
        this.mental = mental;
    }

    public Boolean getAuditiva() {
        return auditiva;
    }

    public void setAuditiva(Boolean auditiva) {
        this.auditiva = auditiva;
    }

    public Boolean getVisual() {
        return visual;
    }

    public void setVisual(Boolean visual) {
        this.visual = visual;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeficienciaPessoa other = (DeficienciaPessoa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
       
    
}
