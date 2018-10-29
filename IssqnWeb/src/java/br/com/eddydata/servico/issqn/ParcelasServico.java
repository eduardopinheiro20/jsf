/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.dto.issqn.CancelamentoDTO;
import br.com.eddydata.dto.issqn.ParcelaDTO;
import br.com.eddydata.dto.issqn.RetornoBuscaParcelaDTO;
import br.com.eddydata.repositorio.issqn.AlvaraRepositorio;
import br.com.eddydata.repositorio.issqn.ParcelasRepositorio;
import br.com.eddydata.servico.Servico;
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
public class ParcelasServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private ParcelasRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new ParcelasRepositorio(em);
    }

    public List<ParcelaDTO> obterParcelas(Integer exercicio, String filtro)
            throws Exception {
        if (exercicio == null) {
            throw new Exception("Exercicio não passado como parâmetro");
        }

        return repositorio.obterParcelas(exercicio, filtro);
    }

    public List<CancelamentoDTO> obterCancelamentos(Integer exercicio, String filtro)
            throws Exception {
        if (exercicio == null) {
            throw new Exception("Exercicio não passado como parâmetro");
        }

        return repositorio.obterCancelamentos(exercicio, filtro);
    }

    /**
     *
     * @param movimentoParcelaId
     * @return 0 = descrição, 1 = valor
     * @throws Exception
     */
    public List<Object[]> obterTaxas(Integer movimentoParcelaId, Integer movimentoId,Double vlParcela,int nossoNumero) throws Exception {
        if (movimentoParcelaId == null) {
            throw new Exception("ID_MOVIMENTO_PARCELA não passado como parâmetro");
        }
        if (movimentoId == null) {
            throw new Exception("ID_MOVIMENTO não passado como parâmetro");
        }
        return repositorio.obterTaxas(movimentoParcelaId,movimentoId,vlParcela,nossoNumero);
    }

    /**
     *
     * @param exercicio
     * @param issId
     * @return      <pre>
     *  0 = VL_DESCONTO
     *  1 = VL_JUROS
     *  2 = VL_MULTA
     *  3 = VL_PAGO
     * </pre>
     *
     * @throws Exception
     */
    public Double[] obterTotais(Integer exercicio, Integer issId) throws Exception {
        if (exercicio == null) {
            throw new Exception("Exercicio não passado como parâmetro");
        }
        if (issId == null) {
            throw new Exception("ID_ISS não passado como parâmetro");
        }

        return repositorio.obterTotais(exercicio, issId);
    }

    public List<RetornoBuscaParcelaDTO> buscarParcela(Integer exercicio, String filtro) {
        return repositorio.buscarParcela(exercicio, filtro);
    }
}
