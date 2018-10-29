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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Joylson T
 */

@Entity
@Table(name = "issqn_historico_taxa")
public class IssqnHistoricoTaxa implements Serializable {   
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "valor_antigo")
    private double valorAntigo;
    @Column(name = "valor_atual")
    private double valorAtual;
    @Column(name = "data_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Column(name = "data_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
    @Column(name = "taxa")
    private String taxa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValorAntigo() {
        return valorAntigo;
    }

    public void setValorAntigo(double valorAntigo) {
        this.valorAntigo = valorAntigo;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }   
    
}
