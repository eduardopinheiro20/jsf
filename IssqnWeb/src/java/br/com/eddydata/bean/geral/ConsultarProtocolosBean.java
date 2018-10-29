/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.geral;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.JSFUtils;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.servico.issqn.IssqnServico;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.tempuri.ArrayOfString;
import org.tempuri.ConsultarProtocoloPorCNPJRequest;
import org.tempuri.ConsultarProtocoloPorCNPJResponse;
import org.tempuri.Operacionais;
import org.tempuri.Solicitacao;

@Named
@ViewScoped
public class ConsultarProtocolosBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Solicitacao> listaSolicitacao = new ArrayList<>();
    private Solicitacao solicitacao = null;
    private Operacionais ws;

    @Inject
    private GlobalBean global;


    @PostConstruct
    public void init() {
        String cnpj_pessoa = (String) JSFUtils.flashScope().get("cnpjPessoa");
        if (cnpj_pessoa == null || cnpj_pessoa.equals("")) {
            JSFUtils.redirecionarPagina("login");
            return;
        }
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ws_sil", "qaws123!".toCharArray());
            }
        });
        SSLContext ctx;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        } catch (Exception ex) {
        }

        try {
            ArrayOfString arr = new ArrayOfString();
            arr.getString().add(cnpj_pessoa);
            ws = new Operacionais();
            ConsultarProtocoloPorCNPJRequest parametros = new ConsultarProtocoloPorCNPJRequest();
            parametros.setCNPJs(arr);

            ConsultarProtocoloPorCNPJResponse response = ws.getBasicHttpBindingIOperacionais().consultarProtocoloPorCNPJ(parametros, global.getAuthenticationHeader());

            listaSolicitacao = response.getSolicitacoes().getSolicitacao();
            if (listaSolicitacao.isEmpty()) {                
                    JSFUtils.redirecionarPagina("registro");                
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("");
        }
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public void abrirRegistro(Solicitacao s) {
        if (s == null) {
            return;
        }
        JSFUtils.flashScope().put("solicitacao", s);
        JSFUtils.redirecionarPagina("registro");
    }

    public void cancelar() {
        solicitacao = null;
    }

    public void alterar(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public List<Solicitacao> getListaSolicitacao() {
        return listaSolicitacao;
    }

    public void setListaSolicitacao(List<Solicitacao> listaSolicitacao) {
        this.listaSolicitacao = listaSolicitacao;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

}
