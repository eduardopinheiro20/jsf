package br.com.eddydata.repositorio.folha;

import br.com.eddydata.entidade.folha.Funcionarios;
import br.com.eddydata.repositorio.Repositorio;
import javax.persistence.EntityManager;

/**
 *
 * @author Eddydata
 */
public class FolhaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public FolhaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Funcionarios getFuncionarioPorUsuarioCPF(String usuario, String cpf) {
        return getEntidadePura(Funcionarios.class,
                "select f from Funcionarios f "
                + "\n where f.usuario = ?1 and FUNCTION('REPLACE',FUNCTION('REPLACE', f.cpf, '.', ''), '-', '') = ?2", usuario, cpf);
    }

    public Funcionarios salvarFuncionario(Funcionarios f) {
        return setEntidade(Funcionarios.class, f);
    }
    
}
