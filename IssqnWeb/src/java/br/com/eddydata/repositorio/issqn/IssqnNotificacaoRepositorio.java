/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnNotificacao;
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
 * @author d.morais
 */
public class IssqnNotificacaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnNotificacaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnNotificacao salvarNotificacao(IssqnNotificacao n) {
        if (n.getId() == null) {
            return adicionarEntidade(IssqnNotificacao.class, n);
        } else {
            return setEntidade(IssqnNotificacao.class, n);
        }
    }

    public synchronized void removerNotificacao(int notificacaoId) {
        IssqnNotificacao h = getEntidade(IssqnNotificacao.class, notificacaoId);
        removerEntidade(h);
    }

    public IssqnNotificacao obterNotificacaoPorId(int id) {
        return getEntidade(IssqnNotificacao.class, id);
    }

    public List<IssqnNotificacao> obterNotificacaoPorContribuinte(Integer idNotificacao) {
        return getListaPura(IssqnNotificacao.class,
                "select n from IssqnNotificacao n"
                + "\njoin n.iss i"
                + "\nwhere i.id = ?1"
                + "\norder by n.id", idNotificacao);

    }
    
    public void imprimirAlertaNotificacao(Date dtInicial,Date dtTermino,String orgaoId) throws Exception
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
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("periodo","PERÍODO: "+Util.convertToBrDate(dtInicial)+" à "+Util.convertToBrDate(dtTermino));

            List<IssqnNotificacao> lista = getListaPura(IssqnNotificacao.class,
                "select n from IssqnNotificacao n"
                + "\njoin n.iss i"
                + "\nwhere n.dtPrazo between ?1 and ?2"
                + "\norder by n.dtPrazo", dtInicial,dtTermino);

            for (IssqnNotificacao n : lista) {
                HashMap field = new HashMap();

                field.put("dtPrazo", n.getDtPrazo());
                field.put("contribuinte", n.getIss().getPessoa().getNome());
                field.put("notificacao", n.getNotificacao());
              
                lst.add(field);
            }
            rpt.jasperReport("aviso_notificacao", "application/pdf", lst, p, "aviso_notificacao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    
    
    } 

}
