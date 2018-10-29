/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnContribuinteTaxa;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

public class IssqnConfiguracaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnConfiguracaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnConfiguracao salvarConfiguracao(IssqnConfiguracao t) {
        if (t.getId() == null) {
            return adicionarEntidade(IssqnConfiguracao.class, t);
        } else {
            return setEntidade(IssqnConfiguracao.class, t);
        }
    }

    public IssqnConfiguracao obterConfiguracao(String orgaoId) {
        return getEntidadePura(IssqnConfiguracao.class,
                "select c from IssqnConfiguracao c "
                + "\n where c.orgao.idOrgao = ?1 "
                + "\norder by c.id desc", orgaoId
        );
    }

    public void abrirExercicio(Integer ano) {
        List<String> sql = getEntityManager().createNativeQuery("select id_exercicio from exercicio where id_exercicio = " + ano).getResultList();

        if (sql.isEmpty()) {

            getEntityManager().createNativeQuery("update usuario set id_exercicio = " + ano).executeUpdate();

            getEntityManager().createNativeQuery("insert into exercicio (id_exercicio,id_orgao) values(" + ano + ",'020000')").executeUpdate();

            List<Issqn> lst = getListaPura(Issqn.class, "select i from Issqn i where i.idExercicio = ?1", ano - 1);

            for (Issqn i : lst) {
                getEntityManager().detach(i);
                List<IssqnContribuinteTaxa> lstTaxa = getListaPura(IssqnContribuinteTaxa.class, "select i from IssqnContribuinteTaxa i where i.iss.id = ?1", i.getId());
                i.setId(null);
                i.setIdExercicio(ano);
                adicionarEntidade(Issqn.class, i);
                for (IssqnContribuinteTaxa t : lstTaxa) {
                    getEntityManager().detach(t);
                    t.setId(null);
                    t.setIss(i);
                    adicionarEntidade(IssqnContribuinteTaxa.class, t);
                }
            }

            Funcao.notificacaoSucesso("Abertura de exercício efetuada com sucesso!");
        } else {
            Funcao.avisoErro("Exercício já existe!");
        }
    }
}
