/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

public class OrgaoRepositorio extends Repositorio {
    
    private static final long serialVersionUID = 1L;

    public OrgaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }
    
    public ContabilOrgao getOrgao(int id) {
        return getEntidade(ContabilOrgao.class, id);
    }
    
    public ContabilOrgao setOrgao(ContabilOrgao c) {
        return setEntidade(ContabilOrgao.class, c);
    }
    
    public ContabilOrgao adicionarOrgao(ContabilOrgao c) {
        adicionarEntidade(ContabilOrgao.class, c);
        return c;
    }
    
    public void removerOrgao(ContabilOrgao c) {
        removerEntidade(c);
    }
    
    public ContabilOrgao getOrgaoPorId(int id) {
        return getEntityManager().find(ContabilOrgao.class, id);
    }
    
    public List<ContabilOrgao> getOrgaos() {
        return getListaPura(ContabilOrgao.class, "select o from ContabilOrgao o order by o.nome");        
    }
    
    public List<ContabilOrgao> getOrgaoPorNome(String nome) {
        return getListaPura(ContabilOrgao.class, "select o from ContabilOrgao o where o.nome like ?1 order by o.nome", nome);
    }
    
}
