/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.servico.folha;

import br.com.eddydata.entidade.folha.Funcionarios;
import br.com.eddydata.repositorio.folha.FolhaRepositorio;
import br.com.eddydata.servico.Servico;
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

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FolhaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private FolhaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new FolhaRepositorio(em);
    }

    /**
     * Retorna o funcionario se o usuario e cpf forem validos
     *
     * @param usuario
     * @param cpf
     * @return
     */
    public Funcionarios getFuncionarioPorUsuarioCPF(String usuario, String cpf) {
        return repositorio.getFuncionarioPorUsuarioCPF(usuario, cpf);
    }

    /**
     * Atualiza informações do funcionário
     *
     * @param f
     * @return
     */
    public Funcionarios salvarFuncionario(Funcionarios f) {
        return repositorio.salvarFuncionario(f);
    }

}
