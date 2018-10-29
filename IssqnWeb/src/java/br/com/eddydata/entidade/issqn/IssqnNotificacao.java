/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author d.morais
 */
@Entity
@Table(name = "issqn_notificacao")
public class IssqnNotificacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "notificacao", length = 500)
    private String notificacao;
    
    @Column(name = "observacao", length = 5000)
    private String observacao;
    
    @Column(name = "dt_prazo")
    @Temporal(TemporalType.DATE)
    private Date dtPrazo;
    
    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDtPrazo() {
        return dtPrazo;
    }

    public void setDtPrazo(Date dtPrazo) {
        this.dtPrazo = dtPrazo;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.notificacao);
        hash = 97 * hash + Objects.hashCode(this.observacao);
        hash = 97 * hash + Objects.hashCode(this.dtPrazo);
        hash = 97 * hash + Objects.hashCode(this.iss);
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
        final IssqnNotificacao other = (IssqnNotificacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.notificacao, other.notificacao)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        if (!Objects.equals(this.dtPrazo, other.dtPrazo)) {
            return false;
        }
        if (!Objects.equals(this.iss, other.iss)) {
            return false;
        }
        return true;
    }

    

}
