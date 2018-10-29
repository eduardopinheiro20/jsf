/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import java.io.Serializable;
import java.util.Date;
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
 * @author eddydata
 */
@Entity
@Table(name = "issqn_historico")
public class IssqnHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iss_historico")
    private Integer id;
    @Column(name = "historico_assunto", length = 70)
    private String historicoAssunto;
    @Column(name = "historico_obs", length = 5000)
    private String historicoObs;
    @Column(name = "historico_data")
    @Temporal(TemporalType.DATE)
    private Date historicoData;
    @Column(name = "processo_data")
    @Temporal(TemporalType.DATE)
    private Date ProcessoData;
    @Column(name = "processo", length = 70)
    private String processo;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    public IssqnHistorico() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHistoricoAssunto() {
        return historicoAssunto;
    }

    public void setHistoricoAssunto(String historicoAssunto) {
        this.historicoAssunto = historicoAssunto;
    }

    public String getHistoricoObs() {
        return historicoObs;
    }

    public void setHistoricoObs(String historicoObs) {
        this.historicoObs = historicoObs;
    }

    public Date getHistoricoData() {
        return historicoData;
    }

    public void setHistoricoData(Date historicoData) {
        this.historicoData = historicoData;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    public Date getProcessoData() {
        return ProcessoData;
    }

    public void setProcessoData(Date ProcessoData) {
        this.ProcessoData = ProcessoData;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssqnHistorico)) {
            return false;
        }
        IssqnHistorico other = (IssqnHistorico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnHistorico[ idIssHistorico=" + id + " ]";
    }

}
