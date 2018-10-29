/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnTaxa;
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

public class IssqnTaxaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnTaxaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnTaxa salvarTaxa(IssqnTaxa t) {
        if (t.getId() == null) {
            return adicionarEntidade(IssqnTaxa.class, t);
        } else {
            return setEntidade(IssqnTaxa.class, t);
        }
    }

    public synchronized void removerTaxa(int taxaId) {
        IssqnTaxa t = getEntidade(IssqnTaxa.class, taxaId);
        removerEntidade(t);
    }

    public IssqnTaxa obterTaxaPorId(int id) {
        return getEntidade(IssqnTaxa.class, id);
    }

    public List<IssqnTaxa> obterTaxas(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n and t.id = " + filtro;
            } else {
                where += "\n and UPPER(t.nome) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnTaxa.class,
                "select t from IssqnTaxa t "
                + "\nleft join t.parente p"
                + "\n where (p.id = 0 or p.id is null) "
                + where
                + "\norder by t.id", limite
        );

    }

    public List<IssqnTaxa> obterItens(Integer taxaId) {
        return getListaPura(IssqnTaxa.class,
                "select t from IssqnTaxa t"
                + "\njoin t.parente p"
                + "\n where p.id = ?1", taxaId
        );
    }

    public void imprimirTaxas(String orgaoId, String ordem) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = null;
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans

            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());

            List<Object[]> lstObj = getListaPura(Object[].class,
                    "SELECT DISTINCT t1.id, t1.nome, t1.valor, t1.tpValor "
                    + "from IssqnTaxa t1 "
                    + "\norder by " + ordem
            );

            for (Object[] obj : lstObj) {
                HashMap field = new HashMap();

                field.put("grupo", (obj[1] == null ? "" : obj[1].toString().toUpperCase()));
                field.put("aliquotas", (obj[1] == null ? "" : obj[1].toString().toUpperCase()));
                field.put("valor", (obj[2] == null ? 0.0 : Double.parseDouble(obj[2].toString())));
                field.put("tipo_valor", (obj[3] == null ? "" : obj[3].toString()));

                lst.add(field);
            }
            rpt.jasperReport("taxas", "application/pdf", lst, p, "taxas");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

}
