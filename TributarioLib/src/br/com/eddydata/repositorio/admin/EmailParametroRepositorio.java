/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.EmailParametro;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.BusinessViolation;
import javax.persistence.EntityManager;

public class EmailParametroRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public EmailParametroRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public EmailParametro salvarEmailParametro(EmailParametro c) {
        if (c.getId() == null) {
            return adicionarEntidade(EmailParametro.class, c);
        } else {
            return setEntidade(EmailParametro.class, c);
        }
    }

    public void removerEmailParametro(int anexoId) throws BusinessViolation {
        EmailParametro c = getEntidade(EmailParametro.class, anexoId);
        if (c == null) {
            throw new BusinessViolation("EmailParametro não encontrado para exclusão");
        } else {
            removerEntidade(c);
        }
    }

    public EmailParametro obterEmailParametroPorId(int id) {
        return getEntidade(EmailParametro.class, id);
    }

    public EmailParametro obterEmailParametro() {
        return getEntidadePura(EmailParametro.class, "select e from EmailParametro e");
    }

}
