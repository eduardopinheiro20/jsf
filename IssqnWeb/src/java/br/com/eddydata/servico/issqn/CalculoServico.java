/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.repositorio.issqn.CalculoRepositorio;
import br.com.eddydata.repositorio.issqn.cidade.AmericoBrasiliense;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import java.util.Date;
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
 * @author lucas
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CalculoServico extends Servico {
    
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private CalculoRepositorio repositorio;
    
    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new AmericoBrasiliense(em);
    }
    
    /**
     * Efetua o cálculo de apenas um contribuinte, no exercício informado no parâmetro
     * @param issqn
     * @param usuario
     * @param proporcional
     * @param motivoCancelamento
     * @throws RuntimeException 
     * @throws BusinessViolation 
     */
    public void calculoContribuinte(Issqn issqn, String usuario, boolean proporcional, IssqnMotivoCancel motivoCancelamento,Integer tipoCobranca) throws RuntimeException, BusinessViolation,Exception {
        repositorio.calculoContribuinte(issqn, usuario, proporcional, motivoCancelamento,tipoCobranca);
    }
    
    /**
     * Efetua o cálculo geral do exercício informado
     * @param id_exercicio
     * @param usuario
     * @param proporcional
     * @param dataAberturaMaxima
     * @throws RuntimeException 
     * @throws BusinessViolation 
     */
    public void calculoGeral(int id_exercicio, String usuario, boolean proporcional, Date dataAberturaMaxima,Integer tipoCobranca) throws RuntimeException, BusinessViolation, Exception {
        repositorio.calculoGeral(id_exercicio, usuario, proporcional, dataAberturaMaxima,tipoCobranca);
    }
    
    /**
     * Excluir todos os movimentos 
     * @param exercicio
     */
    public void cancelarGeral(Integer exercicio,int tipoCobranca) throws BusinessViolation {
        repositorio.excluirMovimento(exercicio,null,tipoCobranca);
    }
    
    /**
     * Cancela todos os movimentos do contribuinte
     * @param issqn
     * @param motivo
     * @param processo 
     */
    public void cancelarMovimento(Issqn issqn, IssqnMotivoCancel motivo, String processo,Integer tipoCobranca) throws BusinessViolation {
        repositorio.cancelarMovimento(issqn, motivo, processo,tipoCobranca);
    }
    
    /**
     * Verificar se há pagamentos efetuados
     * @param issqn
     * @return 
     */
    public Boolean existePagamento(Issqn issqn) {
        return repositorio.existePagamento(issqn);
    }
    
    /**
     * Cancela o movimento eventual do contribuinte
     * @param issqn
     * @param motivo
     * @param parcela 
     */
    public void cancelarEventual(Issqn issqn, IssqnMotivoCancel motivo, Integer parcela) {
        repositorio.cancelarEventual(issqn, motivo, parcela);
    }
    
    /**
     * Verificar se há calculo para um tipo de cobrança selecionado
     * @param issqn
     * @return 
     */
    public Boolean existeCalculoTipoCobranca(Integer exercicio,Integer id_iss,Integer tipoCobranca) {
        return repositorio.existeCalculoTipoCobranca(exercicio,id_iss,tipoCobranca);
    }
}
