/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.suporte;

import br.com.eddydata.entidade.admin.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {

    // servidor de teste
    @SuppressWarnings("unused")
    public static void recuperaSenha(Usuario usuario, Integer ano,
            String hostname, String porta, String email, String senha, boolean starttls, String addressemail) throws EmailException {
        Email email2 = new SimpleEmail();
        email2 = Util.conectaEmail(hostname, porta, email, senha, starttls, addressemail);
        email2.setSubject("Recuperacao de senha ao sistema EDDYDATA-noreply");
        email2.setMsg("EDDYDATA"
                + "\n"
                + "siga as instrucoes abaixo para prosseguir com a solicitacao"
                + "\n"
                + "\n"
                + "Clique no link e informe o codigo abaixo: "
                + "softcidade.eddydata.com/Softcidade/esqueceuSenha.xhtml?faces-redirect=true"
                + "\n" + "\n" + usuario.getSenha());
        email2.addTo(usuario.getEmail().trim());
        String resposta = email2.send();
        FacesContext.getCurrentInstance()
                .addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "E-MAIL ENVIADO COM SUCESSO PARA: "
                                + usuario.getEmail(),
                                "Informação"));
    }

    // email para confirmação de autorização!
    @SuppressWarnings("unused")
    public static void confirmaAcesso(Usuario usuario, Integer ano,
            String hostname, String porta, String email, String senha, boolean starttls, String addressemail) throws EmailException {
        Email email2 = new SimpleEmail();        
        email2 = Util.conectaEmail(hostname, porta, email, senha, starttls, addressemail);
        email2.setSubject("EDDYDATA-noreply");
        email2.setMsg("Sua solicitação de acesso ao sistema foi aceita." + "\n"
                + "Clique no endereço abaixo digite seu email e senha" + "\n" + "\n"
                + "softcidade.eddydata.com/Softcidade/" + "\n" + "\n"
                + "NÃO RESPONDA, EMAIL AUTOMATICO.");
        email2.addTo(usuario.getEmail().trim());
        String resposta = email2.send();
        System.out.println(resposta);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_WARN, "CONFIRMAÇÃO DE ACESSO ENVIADO PARA O EMAIL: " + usuario.getEmail(), "Informação"));
    }

    
}
