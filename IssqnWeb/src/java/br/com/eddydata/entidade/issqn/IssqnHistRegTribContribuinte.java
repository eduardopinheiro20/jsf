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
@Table(name = "issqn_hist_reg_trib_contribuinte")
public class IssqnHistRegTribContribuinte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "regime", length = 70)
    private String regime;
    
    @Column(name = "observacao", length = 5000)
    private String observacao;
    
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.DATE)
    private Date dtInicio;
    
    @Column(name = "dt_termino")
    @Temporal(TemporalType.DATE)
    private Date dtTermino;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtTermino() {
        return dtTermino;
    }

    public void setDtTermino(Date dtTermino) {
        this.dtTermino = dtTermino;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.regime);
        hash = 67 * hash + Objects.hashCode(this.observacao);
        hash = 67 * hash + Objects.hashCode(this.dtInicio);
        hash = 67 * hash + Objects.hashCode(this.dtTermino);
        hash = 67 * hash + Objects.hashCode(this.iss);
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
        final IssqnHistRegTribContribuinte other = (IssqnHistRegTribContribuinte) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.regime, other.regime)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        if (!Objects.equals(this.dtInicio, other.dtInicio)) {
            return false;
        }
        if (!Objects.equals(this.dtTermino, other.dtTermino)) {
            return false;
        }
        if (!Objects.equals(this.iss, other.iss)) {
            return false;
        }
        return true;
    }
    
    


}
