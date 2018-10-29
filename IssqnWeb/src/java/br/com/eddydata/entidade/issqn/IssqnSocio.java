/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.entidade.geral.Pessoa;
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
@Table(name = "issqn_socio")
public class IssqnSocio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_socio")
    private Integer id;

    @JoinColumn(name = "id_imovel", referencedColumnName = "id_imovel")
    @ManyToOne(fetch = FetchType.LAZY)
    private Imovel imovel;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;
    
    @Column(name = "is_responsavel")
    private Boolean isResponsavel;
    
        public IssqnSocio() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Boolean getIsResponsavel() {
        return isResponsavel;
    }

    public void setIsResponsavel(Boolean isResponsavel) {
        this.isResponsavel = isResponsavel;
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
        if (!(object instanceof IssqnSocio)) {
            return false;
        }
        IssqnSocio other = (IssqnSocio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnSocio[ idSocio=" + id + " ]";
    }

}
