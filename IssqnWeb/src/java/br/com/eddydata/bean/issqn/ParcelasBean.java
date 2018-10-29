/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Thiago Martos
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.dto.issqn.CancelamentoDTO;
import br.com.eddydata.dto.issqn.ParcelaDTO;
import br.com.eddydata.dto.issqn.RetornoBuscaParcelaDTO;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.ParcelasServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ParcelasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ParcelasServico servico;

    @EJB
    private IssqnServico servicoIssqn;

    @Inject
    private GlobalBean global;

    private Issqn contribuinte;
    private String inscricao;
    private Integer movimentoId;

    private List<ParcelaDTO> lstParcela;
    private List<CancelamentoDTO> lstCancelamento;
    private List<RetornoBuscaParcelaDTO> lstBuscaParcela;
    private List<Object[]> listTaxa;
    private Double[] total;
    
    private ParcelaDTO selectedParcelaDTO;

    @PostConstruct
    public void init() {
        selectedParcelaDTO = null;
    }

    public void onParcelaSelected(SelectEvent event) {
        selectedParcelaDTO = (ParcelaDTO) event.getObject();
        StringBuilder pesquisa = new StringBuilder();
        pesquisa.append(" AND II.INSCRICAO = ").append(Util.quotarStr(inscricao));
        if (selectedParcelaDTO == null) {
            return;
        }
        try {
            lstBuscaParcela = servico.buscarParcela(global.getExercicio().getAno(), pesquisa.toString());
            if (lstBuscaParcela.size() == 1) {
                movimentoId = lstBuscaParcela.get(0).getIdMovimento();
            } else{
                return;
            }

            listTaxa = servico.obterTaxas(movimentoId, selectedParcelaDTO.getParc(), selectedParcelaDTO.getValor(),Util.extractInt(selectedParcelaDTO.getNossoNumero()));
        } catch (Exception ex) {
            Funcao.avisoAtencao("Não foi possível buscar as taxas do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

    }

    public void buscarParcelas() {
        if (inscricao == null || inscricao.isEmpty()) {
            Funcao.avisoAtencao("Informar um contribuinte");
            return;
        }

        StringBuilder pesquisa = new StringBuilder();
        pesquisa.append("AND II.INSCRICAO = ").append(Util.quotarStr(inscricao));

        try {
            lstBuscaParcela = servico.buscarParcela(global.getExercicio().getAno(), pesquisa.toString());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível buscar parcelas!");
            System.out.println(ex.getMessage());
            return;
        }

        if (lstBuscaParcela.size() == 1) {
            movimentoId = lstBuscaParcela.get(0).getIdMovimento();
            selecionarMovimento();
        }
    }

    public void selecionarMovimento() {
        if (movimentoId == null) {
            return;
        }

        buscarDados();
    }

    private void buscarDados() {
        StringBuilder pesquisa = new StringBuilder();
        pesquisa.append(" AND I.INSCRICAO = ").append(Util.quotarStr(inscricao));

        //PARCELAS
        try {
            lstParcela = servico.obterParcelas(global.getExercicio().getAno(), pesquisa.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            Funcao.avisoAtencao("Não foi possível buscar as parcelas do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

        //CANCELAMENTOS
        try {
            lstCancelamento = servico.obterCancelamentos(global.getExercicio().getAno(), pesquisa.toString());
        } catch (Exception ex) {
            Funcao.avisoAtencao("Não foi possível buscar os cancelamentos do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

        //TOTAIS
        try {
            total = servico.obterTotais(global.getExercicio().getAno(), contribuinte.getId());
        } catch (Exception ex) {
            Funcao.avisoAtencao("Não foi possível buscar os totais do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

    }

    public void buscaPorInscricao() {

        if (inscricao == null || inscricao.trim().isEmpty()) {
            return;
        }
        try {
            contribuinte = servicoIssqn.obterIssqnPorInscricao(inscricao, global.getExercicio().getAno());
        } catch (BusinessViolation ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar inscrição\n" + ex.getMessage());
        }

        selecionarIssqn();
    }

    public void selecionarIssqn() {
        if (contribuinte == null) {
            Funcao.avisoAtencao("Contribuinte não selecionado");
            return;
        }
        inscricao = contribuinte.getInscricao();
        buscarDados();

        Pessoa p = contribuinte.getPessoa();
        if (p != null) {
            contribuinte.setCnpjCpf(p.getCpfCnpj());
        }
        Funcao.executarJavaScript("updateFields();");
    }

    //------------ getters e setters ---------------//
    public Issqn getContribuinte() {
        if (contribuinte == null) {
            contribuinte = new Issqn();
        }
        return contribuinte;
    }

    public void setContribuinte(Issqn contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public List<ParcelaDTO> getLstParcela() {
        return lstParcela;
    }

    public void setLstParcela(List<ParcelaDTO> lstParcela) {
        this.lstParcela = lstParcela;
    }

    public List<CancelamentoDTO> getLstCancelamento() {
        return lstCancelamento;
    }

    public void setLstCancelamento(List<CancelamentoDTO> lstCancelamento) {
        this.lstCancelamento = lstCancelamento;
    }

    public List<Object[]> getListTaxa() {
        return listTaxa;
    }

    public void setListTaxa(List<Object[]> listTaxa) {
        this.listTaxa = listTaxa;
    }

    public Double[] getTotal() {
        return total;
    }

    public void setTotal(Double[] total) {
        this.total = total;
    }

    public List<RetornoBuscaParcelaDTO> getLstBuscaParcela() {
        return lstBuscaParcela;
    }

    public void setLstBuscaParcela(List<RetornoBuscaParcelaDTO> lstBuscaParcela) {
        this.lstBuscaParcela = lstBuscaParcela;
    }

    public Integer getMovimentoId() {
        return movimentoId;
    }

    public void setMovimentoId(Integer movimentoId) {
        this.movimentoId = movimentoId;
    }

    public ParcelaDTO getSelectedParcelaDTO() {
        return selectedParcelaDTO;
    }

    public void setSelectedParcelaDTO(ParcelaDTO selectedParcelaDTO) {
        this.selectedParcelaDTO = selectedParcelaDTO;
    }

}
