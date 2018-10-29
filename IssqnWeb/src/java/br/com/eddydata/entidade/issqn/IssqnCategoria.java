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

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_categoria")
public class IssqnCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;
    @Column(name = "nome", length = 80)
    private String nome;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "status", length = 1)
    private String status;
    @Column(name = "texto_alvara", length = 1000)
    private String textoAlvara;
    @Column(name = "cod_categoria")
    private Integer codCategoria;

    public IssqnCategoria() {
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTextoAlvara() {
        return textoAlvara;
    }

    public void setTextoAlvara(String textoAlvara) {
        this.textoAlvara = textoAlvara;
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
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
        if (!(object instanceof IssqnCategoria)) {
            return false;
        }
        IssqnCategoria other = (IssqnCategoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnCategoria[ idCategoria=" + id + " ]";
    }

}
