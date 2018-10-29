/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnQuestionario;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.Toolkit;
import java.util.ArrayList;
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
public class IssqnQuestionarioRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnQuestionarioRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnQuestionario salvarQuestionario(IssqnQuestionario a) {
        if (a.getId() == null) {
            return adicionarEntidade(IssqnQuestionario.class, a);
        } else {
            return setEntidade(IssqnQuestionario.class, a);
        }
    }

    public synchronized void removerQuestionario(int questionarioId) {
        IssqnQuestionario t = getEntidade(IssqnQuestionario.class, questionarioId);
        removerEntidade(t);
    }

    public IssqnQuestionario obterQuestionarioPorId(int id) {
        return getEntidade(IssqnQuestionario.class, id);
    }

    public List<IssqnQuestionario> obterQuestionario(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where a.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', a.descricao)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnQuestionario.class,
                "select a from IssqnQuestionario a "
                + where
                + "\norder by a.id", limite
        );

    }
    
    public void imprimirQuestionario(String orgaoId) throws Exception
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

            List<IssqnQuestionario> lista = getListaPura(IssqnQuestionario.class,
                "select a from IssqnQuestionario a where a.ativo = true"
                + "\norder by a.ordem"
              );


            for (IssqnQuestionario i : lista) {
                HashMap field = new HashMap();

                field.put("descricao", i.getOrdem().toString() + " - " + i.getDescricao());
              
                lst.add(field);
            }
            rpt.jasperReport("questionario_contribuinte", "application/pdf", lst, p, "questionario_contribuinte");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    
    
    }

}
