/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcelaItem;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.ImageIcon;

/**
 *
 * @author Tales
 */
public class IssqnMovimentoParcelaItemRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;


    public IssqnMovimentoParcelaItemRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnMovimentoParcelaItem salvarIssqnMovimentoParcelaItem(IssqnMovimentoParcelaItem i) {
        if (i.getId() == null) {
            return adicionarEntidade(IssqnMovimentoParcelaItem.class, i);
        } else {
            return setEntidade(IssqnMovimentoParcelaItem.class, i);
        }
    }

    public synchronized void removerIssqnMovimentoParcelaItem(int issqnId) {
        IssqnMovimentoParcelaItem t = getEntidade(IssqnMovimentoParcelaItem.class, issqnId);
        removerEntidade(t);
    }

    /**
     * 
     * @param idLote
     * @param ano
     * @return 
     */
    public List<IssqnMovimentoParcelaItem> obterIssqnMovimentoParcelaItemPorLote(int idLote, int ano) {
        return getListaPura(IssqnMovimentoParcelaItem.class,
                "select distinct i from IssqnMovimentoParcelaItem i"
                + "\njoin i.idMovimentoParcela p"
                + "\nwhere p.lote = ?1"
                + " and i.idExercicio = ?2"
                + "\norder by p.lote", idLote, ano
        );
        
    }
    
    /**
     * 
     * @param dataInicial
     * @param dataFinal
     * @return 
     */
    public List<IssqnMovimentoParcelaItem> obterIssqnMovimentoParcelaPorDataPagamento(Date dataInicial, Date dataFinal){
        return getListaPura(IssqnMovimentoParcelaItem.class,
                "select distinct i from IssqnMovimentoParcelaItem i"
                + "\njoin i.idMovimentoParcela p"
                + "\nwhere p.dtPagamento between ?1 and ?2"
                + "\norder by p.lote", dataInicial, dataFinal
        );
    }
    
    /**
     * 
     * @param dataInicial
     * @param dataFinal
     * @return 
     */
    public List<IssqnMovimentoParcelaItem> obterIssqnMovimentoParcelaPorDataBaixa(Date dataInicial, Date dataFinal){
        return getListaPura(IssqnMovimentoParcelaItem.class,
                "select distinct i from IssqnMovimentoParcelaItem i"
                + "\njoin i.idMovimentoParcela p"
                + "\nwhere p.dtBaixa between ?1 and ?2"
                + "\norder by p.lote", dataInicial, dataFinal
        );
    }
    public IssqnMovimentoParcelaItem obterIssqnMovimentoParcelaItemPorId(int id) {
        return getEntidade(IssqnMovimentoParcelaItem.class, id);
    }

    /**
     *
     * @param orgaoId
     * @param filtro
     * @throws Exception
     */
    public void imprimirRelacaoPagamentos(String orgaoId, String filtro, String pesquisa) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lista = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do órgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getLogotipo();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
            }
            //--Parameters
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("pesquisa", pesquisa);
            p.put("usuario", "");

            String sql = "SELECT P.NOME AS CONTRIBUINTE, I.INSCRICAO, MP.VL_PARCELA, MP.PARCELA,"
                    + "\n MP.VL_PAGO, MP.LOTE, MP.DT_PAGAMENTO, MP.DT_VENCIMENTO, MP.DT_BAIXA, B.NOME AS BANCO"
                    + "\n FROM ISSQN I"
                    + "\n INNER JOIN ISSQN_MOVIMENTO M ON M.ID_ISS = I.ID_ISS"
                    + "\n INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO"
                    + "\n LEFT JOIN BANCO B ON B.ID_BANCO = MP.ID_BANCO"
                    + "\n INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA"
                    + "\n WHERE " + filtro
                    + " ORDER BY P.NOME ";
            List<Object[]> lstObj = createNativeQuery(sql);
            
            for(Object[] obj : lstObj) {
                HashMap field = new HashMap();

                field.put("contribuinte", obj[0].toString());
                field.put("inscricao", obj[1].toString());
                field.put("vl_parcela", Util.extractDouble(obj[2].toString()));
                field.put("parcela", Util.extractInt(obj[3].toString()));
                field.put("vl_pago", Util.extractDouble(obj[4].toString()));
                field.put("lote", obj[5].toString());
                field.put("dt_pagamento", obj[6]);
                field.put("dt_baixa", obj[8]);
                field.put("banco", obj[9].toString());
                
                lista.add(field);
            }
            rpt.jasperReport("totalizacao_pagamento_geral", "application/pdf", lista, p, "totalizacao_pagamento_geral");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param id_lote
     * @return
     */
    private List<IssqnMovimentoParcelaItem> buscaPagamentosPorLote(int id_lote) {
        List<IssqnMovimentoParcelaItem> lstObj = new ArrayList<>();

        String sql = "SELECT P.NOME AS CONTRIBUINTE, I.INSCRICAO, MP.VL_PARCELA, MP.PARCELA,"
                + "\n MP.VL_PAGO, MP.LOTE, MP.DT_PAGAMENTO, MP.DT_VENCIMENTO, MP.DT_BAIXA, B.NOME AS BANCO"
                + "\n FROM ISSQN I"
                + "\n INNER JOIN ISSQN_MOVIMENTO M ON M.ID_ISS = I.ID_ISS"
                + "\n INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO"
                + "\n LEFT JOIN BANCO B ON B.ID_BANCO = MP.ID_BANCO"
                + "\n INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA"
                + "\n WHERE MP.LOTE = " + id_lote
                + " ORDER BY P.NOME ";
        try {
            lstObj = createNativeQuery(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lstObj;
    }

    /**
     *
     * @param dtInicial
     * @param dtFinal
     * @return
     */
    private List<IssqnMovimentoParcelaItem> obterPagamentosPorDtPagamento(String dtInicial, String dtFinal) {
        List<IssqnMovimentoParcelaItem> lstObj = new ArrayList<>();

        String sql = "SELECT P.NOME AS CONTRIBUINTE, I.INSCRICAO, MP.VL_PARCELA, MP.PARCELA,"
                + "\n MP.VL_PAGO, MP.LOTE, MP.DT_PAGAMENTO, MP.DT_VENCIMENTO, MP.DT_BAIXA, B.NOME AS BANCO"
                + "\n FROM ISSQN I"
                + "\n INNER JOIN ISSQN_MOVIMENTO M ON M.ID_ISS = I.ID_ISS"
                + "\n INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO"
                + "\n LEFT JOIN BANCO B ON B.ID_BANCO = MP.ID_BANCO"
                + "\n INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA"
                + "\n WHERE MP.DT_PAGAMENTO BETWEEN " + dtInicial + " AND " + dtFinal
                + "\nORDER BY P.NOME ";
        try {
            lstObj = createNativeQuery(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lstObj;
    }

    /**
     *
     * @param dtInicial
     * @param dtFinal
     * @return
     */
    private List<Object[]> obterPagamentosPorDtBaixa(String dtInicial, String dtFinal) {
        List<Object[]> lstObj = new ArrayList<>();

        String sql = "SELECT P.NOME AS CONTRIBUINTE, I.INSCRICAO, MP.VL_PARCELA, MP.PARCELA,"
                + "\n MP.VL_PAGO, MP.LOTE, MP.DT_PAGAMENTO, MP.DT_VENCIMENTO, MP.DT_BAIXA, B.NOME AS BANCO"
                + "\n FROM ISSQN I"
                + "\n INNER JOIN ISSQN_MOVIMENTO M ON M.ID_ISS = I.ID_ISS"
                + "\n INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO"
                + "\n LEFT JOIN BANCO B ON B.ID_BANCO = MP.ID_BANCO"
                + "\n INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA"
                + "\n WHERE MP.DT_BAIXA BETWEEN " + dtInicial + " AND " + dtFinal
                + "\nORDER BY P.NOME ";
        try {
            lstObj = createNativeQuery(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lstObj;
    }

}
