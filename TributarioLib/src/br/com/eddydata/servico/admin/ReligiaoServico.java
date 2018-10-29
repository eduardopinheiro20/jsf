/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geral.Religiao;
import br.com.eddydata.repositorio.admin.ReligiaoRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReligiaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private ReligiaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new ReligiaoRepositorio(em);
    }

    /**
     * método para incluir ou salvar religioes
     *
     * @param r
     * @return
     * @throws Exception
     */
    public Religiao salvarReligiao(Religiao r) throws Exception {
        if (r == null) {
            throw new Exception("Informe a religião a ser salva");
        }

        try {
            return repositorio.salvarReligiao(r);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover religiao
     *
     * @param religiaoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerReligiao(int religiaoId) throws BusinessViolation, Exception {
        Religiao r = repositorio.obterReligiaoPorId(religiaoId);
        if (r == null) {
            throw new Exception("Religião não encontrada para exclusão");
        }

        try {
            repositorio.removerReligiao(religiaoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar religiao pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public Religiao obterReligiaoPorId(int id) throws BusinessViolation, Exception {
        Religiao p;

        try {
            p = repositorio.obterReligiaoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (p == null) {
            throw new BusinessViolation("Religião não encontrada!");
        } else {
            return p;
        }
    }

    /**
     * método para retornar as religioes
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<Religiao> obterReligioes(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterReligioes(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
