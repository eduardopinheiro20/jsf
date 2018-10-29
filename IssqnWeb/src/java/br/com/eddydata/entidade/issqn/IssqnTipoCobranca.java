/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.geral.DividaTipo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_tipo_cobranca")
public class IssqnTipoCobranca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cobranca")
    private Integer id;
    @Column(name = "id_exercicio")
    private Integer idExercicio;
    @Column(name = "descricao", length = 50)
    private String descricao;
    @Column(name = "qtd_parcela")
    private Integer qtdParcela;
    @Column(name = "vl_desconto")
    private Double vlDesconto;
    @Column(name = "mensagem", length = 250)
    private String mensagem;
    @Column(name = "dia_vencimento")
    private Integer diaVencimento;
    @Column(name = "mes_vencimento")
    private Integer mesVencimento;
    @Column(name = "mes_atraso")
    private Integer mesAtraso;
    @Column(name = "tipo_desconto")
    private Short tipoDesconto;
    @Column(name = "is_bimestral")
    private Integer isBimestral;
    @Column(name = "tp_cobranca")
    private Integer tpCobranca;
    @Column(name = "is_fixo")
    private Short isFixo;
    @Column(name = "id_exercicio_base")
    private Integer idExercicioBase;
    @Column(name = "bloqueio")
    private boolean bloqueio;

    @JoinColumn(name = "id_tipo_divida", referencedColumnName = "id_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private DividaTipo tipoDivida;
    @JoinColumn(name = "id_tipo_divida_estimado", referencedColumnName = "id_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private DividaTipo tipoDividaEstimado;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_tipo_cobranca")
    private Collection<IssqnTipoCobrancaItem> itens = new ArrayList<>();

    public IssqnTipoCobranca() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtdParcela() {
        return qtdParcela;
    }

    public void setQtdParcela(Integer qtdParcela) {
        this.qtdParcela = qtdParcela;
    }

    public Double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(Double vlDesconto) {
        if(vlDesconto == null){
            vlDesconto = 0.0;
        }
        this.vlDesconto = vlDesconto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Integer getMesVencimento() {
        return mesVencimento;
    }

    public void setMesVencimento(Integer mesVencimento) {
        this.mesVencimento = mesVencimento;
    }

    public Integer getMesAtraso() {
        return mesAtraso;
    }

    public void setMesAtraso(Integer mesAtraso) {
        this.mesAtraso = mesAtraso;
    }

    public Short getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(Short tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public Integer getIsBimestral() {
        return isBimestral;
    }

    public void setIsBimestral(Integer isBimestral) {
        this.isBimestral = isBimestral;
    }

    public Integer getTpCobranca() {
        return tpCobranca;
    }

    public void setTpCobranca(Integer tpCobranca) {
        this.tpCobranca = tpCobranca;
    }

    public Short getIsFixo() {
        return isFixo;
    }

    public void setIsFixo(Short isFixo) {
        this.isFixo = isFixo;
    }

    public Integer getIdExercicioBase() {
        return idExercicioBase;
    }

    public void setIdExercicioBase(Integer idExercicioBase) {
        this.idExercicioBase = idExercicioBase;
    }

    public DividaTipo getTipoDivida() {
        return tipoDivida;
    }

    public void setTipoDivida(DividaTipo tipoDivida) {
        this.tipoDivida = tipoDivida;
    }

    public DividaTipo getTipoDividaEstimado() {
        return tipoDividaEstimado;
    }

    public void setTipoDividaEstimado(DividaTipo tipoDividaEstimado) {
        this.tipoDividaEstimado = tipoDividaEstimado;
    }

    public Collection<IssqnTipoCobrancaItem> getItens() {
        return itens;
    }

    public void setItens(Collection<IssqnTipoCobrancaItem> itens) {
        this.itens = itens;
    }

    public boolean isBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(boolean bloqueio) {
        this.bloqueio = bloqueio;
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
        if (!(object instanceof IssqnTipoCobranca)) {
            return false;
        }
        IssqnTipoCobranca other = (IssqnTipoCobranca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnTipoCobranca[ idTipoCobranca=" + id + " ]";
    }

}
