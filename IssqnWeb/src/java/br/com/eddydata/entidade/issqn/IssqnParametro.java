/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.geral.Banco;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_parametro")
public class IssqnParametro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "antecipar_postgar", length = 1)
    private String anteciparPostgar;
    @Column(name = "mensagem", length = 5000)
    private String mensagem;
    @Column(name = "mensagem_fim", length = 5000)
    private String mensagemFim;
    @Column(name = "ufm")
    private Double ufm;
    @Column(name = "vl_expediente")
    private Double vlExpediente;
    @Column(name = "id_exercicio")
    private Integer idExercicio;
    @Column(name = "vl_ufesp")
    private Double vlUfesp;
    @JoinColumn(name = "id_banco", referencedColumnName = "id_banco")
    @ManyToOne(fetch = FetchType.LAZY)
    private Banco idBanco;

    public IssqnParametro() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnteciparPostgar() {
        return anteciparPostgar;
    }

    public void setAnteciparPostgar(String anteciparPostgar) {
        this.anteciparPostgar = anteciparPostgar;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagemFim() {
        return mensagemFim;
    }

    public void setMensagemFim(String mensagemFim) {
        this.mensagemFim = mensagemFim;
    }

    public Double getUfm() {
        return ufm;
    }

    public void setUfm(Double ufm) {
        this.ufm = ufm;
    }

    public Double getVlExpediente() {
        return vlExpediente;
    }

    public void setVlExpediente(Double vlExpediente) {
        this.vlExpediente = vlExpediente;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public Double getVlUfesp() {
        return vlUfesp;
    }

    public void setVlUfesp(Double vlUfesp) {
        this.vlUfesp = vlUfesp;
    }

    public Banco getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Banco idBanco) {
        this.idBanco = idBanco;
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
        if (!(object instanceof IssqnParametro)) {
            return false;
        }
        IssqnParametro other = (IssqnParametro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnParametro[ id=" + id + " ]";
    }

}
