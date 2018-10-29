/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.repositorio.issqn.IssqnMotivoCancelamentoRepositorio;
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
public class IssqnMotivoCancelamentoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnMotivoCancelamentoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnMotivoCancelamentoRepositorio(em);
    }

    /**
     * metodo de validacao do motivo de cancelamento
     *
     * @param motivo
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarMotivo(IssqnMotivoCancel motivo) throws BusinessViolation, Exception {
        if (motivo == null) {
            throw new Exception("Motivo de cancelamento não foi passado como parâmetro");
        }
        if (motivo.getDescricao()== null || motivo.getDescricao().trim().equals("")) {
            throw new BusinessViolation("Descrição não informada!");
        }
    }

    /**
     * método para incluir ou salvar motivo de cancelamento
     *
     * @param c
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnMotivoCancel salvarMotivoCancelamento(IssqnMotivoCancel c) throws BusinessViolation, Exception {
        try {
            verificarMotivo(c);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.salvarMotivoCancelamento(c);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover motivo cancelamento
     *
     * @param motivoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerMotivoCancelamento(int motivoId) throws BusinessViolation, Exception {
        IssqnMotivoCancel t = repositorio.obterMotivoCancelamentoPorId(motivoId);
        if (t == null) {
            throw new Exception("Motivo de cancelamento não encontrado para exclusão");
        }

        try {
            repositorio.removerMotivoCancelamento(motivoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo motivo de cancelamento pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnMotivoCancel obterCategoriaPorId(int id) throws BusinessViolation, Exception {
        IssqnMotivoCancel t;

        try {
            t = repositorio.obterMotivoCancelamentoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Motivo de cancelamento não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar os motivos de cancelamento
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnMotivoCancel> obterMotivosCancelamento(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterMotivosCancelamento(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
