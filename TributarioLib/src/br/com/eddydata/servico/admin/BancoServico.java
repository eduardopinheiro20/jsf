/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.repositorio.admin.BancoRepositorio;
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
 * @author Eddydata
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BancoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private BancoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new BancoRepositorio(em);
    }

    /**
     * metódo de validacao do banco
     *
     * @param banco
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarBanco(Banco banco) throws BusinessViolation, Exception {
        if (banco == null) {
            throw new Exception("Banco não foi passado como parâmetro");
        }
        if (banco.getNome() == null || banco.getNome().trim().equals("")) {
            throw new BusinessViolation("Nome não informado!");
        }
        if (banco.getAgencia() == null || banco.getAgencia().trim().equals("")) {
            throw new BusinessViolation("Agência não informada!");
        }
        if (banco.getDvAgencia() == null || banco.getDvAgencia().trim().equals("")) {
            throw new BusinessViolation("Digito da agência não informado!");
        }
        if (banco.getNumero() == null || banco.getNumero().trim().equals("")) {
            throw new BusinessViolation("Número do banco não informado!");
        }
        if (banco.getBoletoCarteira() == null || banco.getBoletoCarteira().trim().equals("")) {
            throw new BusinessViolation("Número da carteira não informado!");
        }
        if (banco.getNumeroConvenio() == null || banco.getNumeroConvenio().trim().equals("")) {
            throw new BusinessViolation("Número do convênio não informado!");
        }
    }

    /**
     * método para incluir ou salvar bancos
     *
     * @param b
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public Banco salvarBanco(Banco b) throws BusinessViolation, Exception {
        if (b == null) {
            throw new Exception("Informe o banco a ser salvo");
        }

        try {
            verificarBanco(b);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarBanco(b);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover bancos e bancos de atividades
     *
     * @param bancoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerBanco(int bancoId) throws BusinessViolation, Exception {
        Banco b = repositorio.obterBancoPorId(bancoId);
        if (b == null) {
            throw new Exception("Banco não encontrado para exclusão");
        }

        try {
            repositorio.removerBanco(bancoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar banco pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public Banco obterBancoPorId(int id) throws BusinessViolation, Exception {
        Banco b;

        try {
            b = repositorio.obterBancoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (b == null) {
            throw new BusinessViolation("Banco não encontrado!");
        } else {
            return b;
        }
    }

    /**
     * método para retornar as bancos
     *
     * @param filtro
     * @param limite
     * @param cnab
     * @return
     * @throws Exception
     */
    public List<Banco> obterBancos(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterBancos(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
