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
@Table(name = "issqn_contribuinte_taxa")
public class IssqnContribuinteTaxa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribuinte_taxa")
    private Integer id;
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.DATE)
    private Date dtInicio;
    @Column(name = "motivo_inicio", length = 70)
    private String motivoInicio;
    @Column(name = "usuario_inicio", length = 25)
    private String usuarioInicio;
    @Column(name = "dt_fim")
    @Temporal(TemporalType.DATE)
    private Date dtFim;
    @Column(name = "motivo_fim", length = 70)
    private String motivoFim;
    @Column(name = "usuario_fim", length = 25)
    private String usuarioFim;
    @Column(name = "estimado")
    private Integer estimado;
    @Column(name = "id_exercicio")
    private Integer idExercicio;
    @Column(name = "vl_taxa")
    private Double vlTaxa;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;
    @JoinColumn(name = "id_taxa", referencedColumnName = "id_taxa")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnTaxa taxa;

    public IssqnContribuinteTaxa() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public String getMotivoInicio() {
        return motivoInicio;
    }

    public void setMotivoInicio(String motivoInicio) {
        this.motivoInicio = motivoInicio;
    }

    public String getUsuarioInicio() {
        return usuarioInicio;
    }

    public void setUsuarioInicio(String usuarioInicio) {
        this.usuarioInicio = usuarioInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public String getMotivoFim() {
        return motivoFim;
    }

    public void setMotivoFim(String motivoFim) {
        this.motivoFim = motivoFim;
    }

    public String getUsuarioFim() {
        return usuarioFim;
    }

    public void setUsuarioFim(String usuarioFim) {
        this.usuarioFim = usuarioFim;
    }

    public Integer getEstimado() {
        return estimado;
    }

    public void setEstimado(Integer estimado) {
        this.estimado = estimado;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public Double getVlTaxa() {
        return vlTaxa;
    }

    public void setVlTaxa(Double vlTaxa) {
        this.vlTaxa = vlTaxa;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    public IssqnTaxa getTaxa() {
        return taxa;
    }

    public void setTaxa(IssqnTaxa taxa) {
        this.taxa = taxa;
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
        if (!(object instanceof IssqnContribuinteTaxa)) {
            return false;
        }
        IssqnContribuinteTaxa other = (IssqnContribuinteTaxa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnContribuinteTaxa[ idContribuinteTaxa=" + id + " ]";
    }

}
