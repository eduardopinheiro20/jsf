/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_alvara_emissao")
public class IssqnAlvaraEmissao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "data_emissao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;

    @Column(name = "data_validade", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValidade;

    @Column(name = "nr_alvara", nullable = false)
    private Integer nrAlvara;

    @Column(name = "protocolo", length = 12)
    private String protocolo;

    @Lob
    @Column(name = "arquivo", nullable = false)
    private byte[] arquivo;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orgao", referencedColumnName = "id_orgao", nullable = false)
    private ContabilOrgao orgao;

    public IssqnAlvaraEmissao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Integer getNrAlvara() {
        return nrAlvara;
    }

    public void setNrAlvara(Integer nrAlvara) {
        this.nrAlvara = nrAlvara;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    public ContabilOrgao getOrgao() {
        return orgao;
    }

    public void setOrgao(ContabilOrgao orgao) {
        this.orgao = orgao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final IssqnAlvaraEmissao other = (IssqnAlvaraEmissao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnAlvara{" + "id=" + id + ", nrAlvara=" + nrAlvara + '}';
    }

}
