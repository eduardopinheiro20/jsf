/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

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
@Table(name = "issqn_veiculo")
public class IssqnVeiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo")
    private Integer id;
    @Column(name = "chassi", length = 50)
    private String chassi;
    @Column(name = "cor", length = 50)
    private String cor;
    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;
    @Column(name = "ano_modelo")
    private Integer anoModelo;
    @Column(name = "placa", length = 10)
    private String placa;
    @Column(name = "modelo", length = 100)
    private String modelo;
    @Column(name = "marca", length = 100)
    private String marca;
    @Column(name = "combustivel", length = 100)
    private String combustivel;
    @Column(name = "renavam", length = 50)
    private String renavam;
    @Column(name = "capacidade_tanque")
    private Double capacidadeTanque;
    @Column(name = "nr_passageiros")
    private Integer nrPassageiros;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    public IssqnVeiculo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public Double getCapacidadeTanque() {
        return capacidadeTanque;
    }

    public void setCapacidadeTanque(Double capacidadeTanque) {
        this.capacidadeTanque = capacidadeTanque;
    }

    public Integer getNrPassageiros() {
        return nrPassageiros;
    }

    public void setNrPassageiros(Integer nrPassageiros) {
        this.nrPassageiros = nrPassageiros;
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
        if (!(object instanceof IssqnVeiculo)) {
            return false;
        }
        IssqnVeiculo other = (IssqnVeiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnVeiculo[ idVeiculo=" + id + " ]";
    }

}
