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
@Table(name = "issqn_movimento")
public class IssqnMovimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimento")
    private Integer id;
    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;
    @Column(name = "id_exercicio")
    private Integer idExercicio;
    @Column(name = "isento", length = 5)
    private String isento;
    @Column(name = "vl_total")
    private Double vlTotal;
    @Column(name = "vl_desconto")
    private Double vlDesconto;
    @Column(name = "cancelado_movimento")
    private Integer canceladoMovimento;
    @Column(name = "dt_cancelado")
    @Temporal(TemporalType.DATE)
    private Date dtCancelado;
    @Column(name = "motivo", length = 80)
    private String motivo;
    @Column(name = "usuario", length = 25)
    private String usuario;
    @Column(name = "usuario_cancelamento", length = 25)
    private String usuarioCancelamento;
    @Column(name = "dt_calculo")
    @Temporal(TemporalType.DATE)
    private Date dtCalculo;
    @Column(name = "tp_movimento")
    private Integer tpMovimento;
    @Column(name = "id_exercicio_base")
    private Integer idExercicioBase;
    @Column(name = "numero_processo", length = 20)
    private String numeroProcesso;
    @Column(name = "compensacao")
    private Double compensacao;

    public IssqnMovimento() {
    }

    public IssqnMovimento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public String getIsento() {
        return isento;
    }

    public void setIsento(String isento) {
        this.isento = isento;
    }

    public Double getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(Double vlTotal) {
        this.vlTotal = vlTotal;
    }

    public Double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(Double vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public Integer getCanceladoMovimento() {
        return canceladoMovimento;
    }

    public void setCanceladoMovimento(Integer canceladoMovimento) {
        this.canceladoMovimento = canceladoMovimento;
    }

    public Date getDtCancelado() {
        return dtCancelado;
    }

    public void setDtCancelado(Date dtCancelado) {
        this.dtCancelado = dtCancelado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioCancelamento() {
        return usuarioCancelamento;
    }

    public void setUsuarioCancelamento(String usuarioCancelamento) {
        this.usuarioCancelamento = usuarioCancelamento;
    }

    public Date getDtCalculo() {
        return dtCalculo;
    }

    public void setDtCalculo(Date dtCalculo) {
        this.dtCalculo = dtCalculo;
    }

    public Integer getTpMovimento() {
        return tpMovimento;
    }

    public void setTpMovimento(Integer tpMovimento) {
        this.tpMovimento = tpMovimento;
    }

    public Integer getIdExercicioBase() {
        return idExercicioBase;
    }

    public void setIdExercicioBase(Integer idExercicioBase) {
        this.idExercicioBase = idExercicioBase;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Double getCompensacao() {
        return compensacao;
    }

    public void setCompensacao(Double compensacao) {
        this.compensacao = compensacao;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
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
        if (!(object instanceof IssqnMovimento)) {
            return false;
        }
        IssqnMovimento other = (IssqnMovimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnMovimento[ idMovimento=" + id + " ]";
    }

}
