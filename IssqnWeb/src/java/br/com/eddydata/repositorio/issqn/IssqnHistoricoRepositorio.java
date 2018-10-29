/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnHistorico;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
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
 * @author David
 */
public class IssqnHistoricoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnHistoricoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnHistorico salvarHistorico(IssqnHistorico h) {
        if (h.getId() == null) {
            return adicionarEntidade(IssqnHistorico.class, h);
        } else {
            return setEntidade(IssqnHistorico.class, h);
        }
    }

    public synchronized void removerHistorico(int historicoId) {
        IssqnHistorico h = getEntidade(IssqnHistorico.class, historicoId);
        removerEntidade(h);
    }

    public IssqnHistorico obterHistoricoPorId(int id) {
        return getEntidade(IssqnHistorico.class, id);
    }

    public List<IssqnHistorico> obterHistoricoPorContribuinte(Integer idContribuinte) {
        return getListaPura(IssqnHistorico.class,
                "select h from IssqnHistorico h"
                + "\njoin h.iss i"
                + "\nwhere i.id = ?1"
                + "\norder by h.id", idContribuinte);

    }

    public void imprimirHistoricoContribuinte(String orgaoId, Issqn issqn, String emailUsuario) throws Exception {
        {
            try {
                Map p = new HashMap();
                ArrayList<HashMap> lst = new ArrayList<>();
                ReportGenerator rpt = new ReportGenerator();

                ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
                if (orgao == null) {
                    throw new Exception("Informações do orgão não encontradas!");
                }
                byte[] logotipo_bytes = orgao.getLogotipo();
                ImageIcon logotipo = new ImageIcon();
                if (logotipo_bytes != null) {
                    logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
                }

                //-- PARAMETROS
                p.put("orgao", orgao.getNome());
                p.put("nomeContribuinte", issqn.getPessoa().getNome());
                p.put("brasao", logotipo.getImage());

                String dataGeracao = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                p.put("assinatura", "Documento assinado por: " + emailUsuario + ", em " + dataGeracao);

                List<IssqnHistorico> lista = getListaPura(IssqnHistorico.class,
                        "select h from IssqnHistorico h"
                        + "\njoin h.iss i"
                        + "\njoin i.pessoa p"
                        + "\nwhere p.id = ?1"
                        + "\norder by h.id", issqn.getPessoa().getId());

                for (IssqnHistorico ihr : lista) {
                    HashMap field = new HashMap();
                    field.put("data", Util.parseSqlToBrDate(Util.dateToday()));

                    StringBuilder sb = new StringBuilder();
                    sb.append("\nData Alteração: ");
                    sb.append(new SimpleDateFormat("dd/MM/yyyy").format(ihr.getHistoricoData() != null ? ihr.getHistoricoData() : ihr.getIss().getDtInicio()));
                    if (ihr.getHistoricoData() == null) {
                        ihr.setHistoricoData(ihr.getIss().getDtInicio());
                        setEntidade(IssqnHistorico.class, ihr);
                    }
                    sb.append("\nHistórico Assunto: ");
                    sb.append(ihr.getHistoricoAssunto());
                    sb.append("\nAlteração: ");
                    sb.append(ihr.getHistoricoObs());
                    if (ihr.getProcesso() != null) {
                        sb.append("\nProcesso: ");
                        sb.append(ihr.getProcesso());
                    }

                    field.put("alteracoes", sb.toString());
                    lst.add(field);
                }
                rpt.jasperReport("historico_contribuinte", "application/pdf", lst, p, "historico_contribuinte");
            } catch (NoResultException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
