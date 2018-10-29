/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.EmailParametro;
import br.com.eddydata.entidade.admin.Usuario;
import br.com.eddydata.entidade.folha.Funcionarios;
import br.com.eddydata.repositorio.admin.EmailParametroRepositorio;
import br.com.eddydata.repositorio.admin.UsuarioRepositorio;
import br.com.eddydata.repositorio.folha.FolhaRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.StringMD5;
import br.com.eddydata.suporte.Util;
import java.util.Random;
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
public class EmailParametroServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private EmailParametroRepositorio repositorio;
    private UsuarioRepositorio usuarioRepositorio;
    private FolhaRepositorio folhaRepositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new EmailParametroRepositorio(em);
        usuarioRepositorio = new UsuarioRepositorio(em);
        folhaRepositorio = new FolhaRepositorio(em);
    }

    /**
     * método para incluir ou salvar email parametros
     *
     * @param c
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public EmailParametro salvarEmailParametro(EmailParametro c) throws BusinessViolation, Exception {
        if (c == null) {
            throw new BusinessViolation("Informe o email parametro a ser salvo");
        }

        try {
            return repositorio.salvarEmailParametro(c);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover email parametros
     *
     * @param emailParametroId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerEmailParametro(int emailParametroId) throws BusinessViolation, Exception {
        try {
            repositorio.removerEmailParametro(emailParametroId);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar o email parametro pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public EmailParametro obterEmailParametroPorId(int id) throws BusinessViolation, Exception {
        EmailParametro c;

        try {
            c = repositorio.obterEmailParametroPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (c == null) {
            throw new BusinessViolation("EmailParametro não encontrado!");
        } else {
            return c;
        }
    }

    /**
     * método para retornar os parametros de email
     *
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public EmailParametro obterEmailParametro() throws BusinessViolation, Exception {
        EmailParametro p;

        try {
            p = repositorio.obterEmailParametro();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (p == null) {
            System.out.println("EmailParametros não encontrados, utilizando valores padrões");
            p = new EmailParametro();

            p.setAddressEmail("");
            p.setEmailAuthenticated(false);
            p.setEmailEmail("");
            p.setEmailHostname("");
            p.setEmailPassword("");
            p.setEmailPort(0);
            p.setEmailSolicitacao("");
            p.setEmailStarttls(false);
        }

        return repositorio.salvarEmailParametro(p);
    }

    public boolean recuperarSenha(String email) throws BusinessViolation, Exception {
        try {
            Usuario usuarioLogado = usuarioRepositorio.getUsuarioPorEmail(email);

            if (usuarioLogado != null) {
                if (usuarioLogado.getId() == null || !usuarioLogado.getAtivo()) {
                    throw new BusinessViolation("Este e-mail não esta registrado no sistema, por-favor informe um e-mail válido!");
                } else if (usuarioLogado.getAtivo()) {
                    usuarioLogado.setEsquiciSenha(true);
                    Random gerador = new Random();
                    int numero = gerador.nextInt();
                    final String novaSenha = Util.extractStr(numero < 0 ? numero * -1 : numero);
                    String encryptedPassword = StringMD5.md5(novaSenha);
                    usuarioLogado.setSenha(encryptedPassword);
                    usuarioRepositorio.setUsuario(usuarioLogado);

                    EmailParametro p = obterEmailParametro();
                    String assunto = "Recuperacao de senha do sistema";
                    String mensagem = "Sistema de Holerite - Eddydata"
                            + "<br />"
                            + "Siga as instruções abaixo para prosseguir com a solicitação"
                            + "<br />"
                            + "<br />"
                            + "<br />"
                            + "<br />"
                            + "Sua nova senha é: " + novaSenha;
                    String to = usuarioLogado.getEmail().trim();
                    try {
                        Util.dispararEmail(p, assunto, mensagem, to);
                    } catch (Exception ex) {
                        throw ex;
                    }
                } else {
                    throw new BusinessViolation("O seu usuário não está ativo, entre em contato com um administrador!");
                }
            } else {
                System.out.println("...Digite corretamente as informacoes para recuperar seu acesso");
                throw new BusinessViolation("REGISTRO NÃO ENCONTRADO."
                        + " DIGITE SEU E-MAIL CORRETAMENTE"
                        + " CASO NÃO SEJA POSSÍVEL, ENTRE EM CONTATO COM O ADMINISTRADOR DO SISTEMA");
            }
        } catch (Exception ex) {
            if (ex instanceof BusinessViolation) {
                throw new BusinessViolation(ex.getMessage());
            } else {
                System.out.println("Não foi possível recuperar acesso ao sistema\n" + ex.getMessage());
                throw new Exception("Não foi possível recuperar acesso ao sistema");
            }
        }

        return true;
    }

    public boolean recuperarSenhaFolha(String usuario, String cpf, String email) throws BusinessViolation, Exception {
        if (usuario == null || "".equals(usuario.trim())) {
            throw new BusinessViolation("Informe o seu usuário!");
        }
        if (cpf == null || "".equals(cpf.trim())) {
            throw new BusinessViolation("Informe o seu CPF!");
        }
        if (email == null || "".equals(email.trim())) {
            throw new BusinessViolation("Informe um e-mail para receber a nova senha");
        }
        if (!Util.isValidEmail(email.trim())) {
            throw new BusinessViolation("Informe um e-mail válido!");
        }

        Funcionarios funcionarioLogado = folhaRepositorio.getFuncionarioPorUsuarioCPF(usuario, cpf.replace(".", "").replace("-", ""));

        if (funcionarioLogado != null) {
            Random gerador = new Random();
            int numero = gerador.nextInt();
            final String novaSenha = Util.extractStr(numero < 0 ? numero * -1 : numero);
            funcionarioLogado.setSenha(novaSenha);
            funcionarioLogado.setEMail(email);
            funcionarioLogado.setPrimeiroAcesso(1);
            folhaRepositorio.salvarFuncionario(funcionarioLogado);

            EmailParametro p = obterEmailParametro();
            String assunto = "Recuperacao de acesso ao sistema";
            String mensagem = "Sistema de Holerite - Eddydata"
                    + "<br />"
                    + "Siga as instruções abaixo para prosseguir com a solicitação"
                    + "<br />"
                    + "<br />"
                    + "<br />"
                    + "<br />"
                    + "Sua nova senha é: " + novaSenha;
            try {
                Util.dispararEmail(p, assunto, mensagem, email);
            } catch (Exception ex) {
                throw ex;
            }
        } else {
            System.out.println("...Digite corretamente as informacoes para recuperar seu acesso");
            throw new BusinessViolation("REGISTRO NÃO ENCONTRADO."
                    + " VERIFIQUE SEU USUÁRIO E CPF."
                    + " CASO NÃO SEJA POSSÍVEL, ENTRE EM CONTATO COM O ADMINISTRADOR DO SISTEMA");
        }

        return true;
    }

}
