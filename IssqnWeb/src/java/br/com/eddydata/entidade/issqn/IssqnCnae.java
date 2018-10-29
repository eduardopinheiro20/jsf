/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_cnae")
public class IssqnCnae implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cnae")
    private Integer id;
    @Column(name = "nome", length = 512)
    private String nome;
    @Column(name = "codigo_cnae")
    private String codigoCnae;
    @Column(name = "tp_atividade")
    private Integer tpAtividade;
    @Transient
    private IssqnCnaeIss cnaeIss;

    public IssqnCnae() {
    }

    public IssqnCnae(Integer idCnae) {
        this.id = idCnae;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoCnae() {
        return codigoCnae;
    }

    public void setCodigoCnae(String codigoCnae) {
        this.codigoCnae = codigoCnae;
    }

    public IssqnCnaeIss getCnaeIss() {
        return cnaeIss;
    }

    public void setCnaeIss(IssqnCnaeIss cnaeIss) {
        this.cnaeIss = cnaeIss;
    }

    public Integer getTpAtividade() {
        return tpAtividade;
    }

    public void setTpAtividade(Integer tpAtividade) {
        this.tpAtividade = tpAtividade;
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
        if (!(object instanceof IssqnCnae)) {
            return false;
        }
        IssqnCnae other = (IssqnCnae) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnCnae[ idCnae=" + id + " ]";
    }

}
