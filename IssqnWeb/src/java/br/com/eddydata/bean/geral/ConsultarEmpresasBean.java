/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.geral;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.UtilBean;
import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.entidade.geo.Abreviatura;
import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.entidade.geo.Logradouro;
import br.com.eddydata.entidade.geo.LogradouroPK;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnCategoria;
import br.com.eddydata.entidade.issqn.IssqnCnaeIss;
import br.com.eddydata.entidade.issqn.IssqnSocio;
import br.com.eddydata.entidade.referencia.Situacao;
import br.com.eddydata.entidade.referencia.TipoPessoa;
import br.com.eddydata.servico.admin.AbreviaturaServico;
import br.com.eddydata.servico.admin.BairroServico;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.LogradouroServico;
import br.com.eddydata.servico.admin.PessoaServico;
import br.com.eddydata.servico.issqn.BairroLogradouroServico;
import br.com.eddydata.servico.issqn.ImovelServico;
import br.com.eddydata.servico.issqn.IssqnAtividadeServico;
import br.com.eddydata.servico.issqn.IssqnCategoriaServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.IssqnSocioServico;
import br.com.eddydata.suporte.StringMD5;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.tempuri.AtividadeCNAE;
import org.tempuri.ConsultarNumeroProtocolosPorCNPJRequest;
import org.tempuri.ConsultarNumeroProtocolosPorCNPJResponse;
import org.tempuri.ConsultarNumeroProtocolosRequest;
import org.tempuri.ConsultarNumeroProtocolosResponse;
import org.tempuri.ConsultarProtocoloRequest;
import org.tempuri.Licenca;
import org.tempuri.Operacionais;
import org.tempuri.ResponsavelEmpresa;
import org.tempuri.Solicitacao;

@Named
@ViewScoped
public class ConsultarEmpresasBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Solicitacao> listaEmpresas = new ArrayList<>();
    private Solicitacao solicitacao = null;
    private Operacionais ws;
    private String textoFiltro;
    private Date dataInicial;
    private Date dataFinal;
    private Issqn contribuinte = new Issqn();
    private BairroLogradouro bl = new BairroLogradouro();
    private Imovel i = new Imovel();

    @EJB
    private IssqnServico servico;
    @EJB
    private IssqnCategoriaServico categoriaServico;
    @EJB
    private IssqnSocioServico socioServico;
    @EJB
    private BairroLogradouroServico bairroLogradouro;
    @EJB
    private CidadeServico cidadeServico;
    @EJB
    private BairroServico bairroServico;
    @EJB
    private LogradouroServico logradouroServico;
    @EJB
    private ImovelServico imovelServico;
    @EJB
    private PessoaServico pessoaServico;
    @EJB
    private AbreviaturaServico abreviaturaServico;
    @EJB
    private IssqnAtividadeServico cnaeServico;

    @Inject
    private GlobalBean global;

    @Inject
    private UtilBean util;

    @PostConstruct
    public void init() {
//        consultarTodos();
    }

    private void consultarTodos() {
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
            System.out.println(ex.getMessage());
        }
        try {
            ws = new Operacionais();
            ConsultarNumeroProtocolosRequest parametros = new ConsultarNumeroProtocolosRequest();
            parametros.setCnae(null);
//            parametros.setIdMunicipio(249);
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            parametros.setDataInicial(date);
            GregorianCalendar c2 = (GregorianCalendar) c.clone();
            c2.add(Calendar.MONTH, 1);
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
            parametros.setDataFinal(date2);
            ConsultarNumeroProtocolosResponse response = ws.getBasicHttpBindingIOperacionais().consultarNumeroProtocolos(parametros, global.getAuthenticationHeader());
            ConsultarProtocoloRequest cpr = new ConsultarProtocoloRequest();
            cpr.setProtocolos(response.getSolicitacoes());

            listaEmpresas = ws.getBasicHttpBindingIOperacionais().consultarProtocolo(cpr, global.getAuthenticationHeader()).getSolicitacoes().getSolicitacao();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("");
        }
    }

    public void buscarProtocolosPorPeriodo() {
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
            System.out.println(ex.getMessage());
        }
        try {
            ws = new Operacionais();
            ConsultarNumeroProtocolosRequest parametros = new ConsultarNumeroProtocolosRequest();
            parametros.setCnae(null);
            GregorianCalendar c = new GregorianCalendar();
            if (dataInicial != null) {
                c.setTime(dataInicial);
            } else {
                c.setTime(new Date());
                c.add(Calendar.MONTH, -1);
            }
            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            parametros.setDataInicial(date);
            GregorianCalendar c2 = (GregorianCalendar) c.clone();
            if (dataFinal != null) {
                c2.setTime(dataFinal);
            } else {
                c2.setTime(new Date());
                c2.add(Calendar.MONTH, 0);
            }
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
            parametros.setDataFinal(date2);
            ConsultarNumeroProtocolosResponse response = ws.getBasicHttpBindingIOperacionais().consultarNumeroProtocolos(parametros, global.getAuthenticationHeader());
            ConsultarProtocoloRequest cpr = new ConsultarProtocoloRequest();
            cpr.setProtocolos(response.getSolicitacoes());

            listaEmpresas = ws.getBasicHttpBindingIOperacionais().consultarProtocolo(cpr, global.getAuthenticationHeader()).getSolicitacoes().getSolicitacao();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("");
        }
    }

    public void filtrar() {
        textoFiltro = textoFiltro.replace("-", "").replace(".", "").replace("/", "").trim();
        if (textoFiltro == null || textoFiltro.isEmpty()) {
            consultarTodos();
            return;
        } else if (!Util.isNumeric(textoFiltro)) {
            Funcao.avisoAtencao("O CNPJ deve ser formado apenas por números");
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
            System.out.println(ex.getMessage());
        }
        try {
            ws = new Operacionais();
            ConsultarNumeroProtocolosPorCNPJRequest parametros = new ConsultarNumeroProtocolosPorCNPJRequest();
            parametros.setCnpj(textoFiltro);
            ConsultarNumeroProtocolosPorCNPJResponse response = ws.getBasicHttpBindingIOperacionais().consultarNumeroProtocolosPorCNPJ(parametros, global.getAuthenticationHeader());
            ConsultarProtocoloRequest cpr = new ConsultarProtocoloRequest();
            cpr.setProtocolos(response.getSolicitacoes());

            listaEmpresas = ws.getBasicHttpBindingIOperacionais().consultarProtocolo(cpr, global.getAuthenticationHeader()).getSolicitacoes().getSolicitacao();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("");
        }
    }

    public void cancelar() {
        solicitacao = null;
    }

    public void alterar(Solicitacao solicitacao) {
        contribuinte = new Issqn();
        this.solicitacao = solicitacao;
        this.solicitacao.getEmpresa().setNomeEmpresa(util.retiraAcento(solicitacao.getEmpresa().getNomeEmpresa().toUpperCase()));
        this.solicitacao.getEmpresa().setDDD1(solicitacao.getEmpresa().getDDD1().trim());
        this.solicitacao.getEmpresa().setTelefone1(solicitacao.getEmpresa().getTelefone1().trim());
        this.solicitacao.getEmpresa().setDDD2(solicitacao.getEmpresa().getDDD2().trim());
        this.solicitacao.getEmpresa().setTelefone2(solicitacao.getEmpresa().getTelefone2().trim());
        this.solicitacao.getEmpresa().getNaturezaJuridica().setNomeNaturezaJuridica(util.retiraAcento(solicitacao.getEmpresa().getNaturezaJuridica().getNomeNaturezaJuridica().toUpperCase()));
        this.solicitacao.getEmpresa().getPorte().setDescricaoPorte(util.retiraAcento(solicitacao.getEmpresa().getPorte().getDescricaoPorte().toUpperCase()));
        this.solicitacao.getEmpresa().getMunicipio().getEstado().setNome(util.retiraAcento(solicitacao.getEmpresa().getMunicipio().getEstado().getNome().toUpperCase()));
        this.solicitacao.getEmpresa().getMunicipio().setNome(util.retiraAcento(solicitacao.getEmpresa().getMunicipio().getNome().toUpperCase()));
        this.solicitacao.getEmpresa().setBairro(util.retiraAcento(solicitacao.getEmpresa().getBairro().toUpperCase()));
        this.solicitacao.getEmpresa().getTipoLogradouro().setNomeLogradouro(util.retiraAcento(solicitacao.getEmpresa().getTipoLogradouro().getNomeLogradouro().toUpperCase()));
        this.solicitacao.getEmpresa().setLogradouro(util.retiraAcento(solicitacao.getEmpresa().getLogradouro().toUpperCase()));
        this.solicitacao.getEmpresa().setComplemento(util.retiraAcento(solicitacao.getEmpresa().getComplemento().toUpperCase()));
        this.solicitacao.getEmpresa().setReferencia(util.retiraAcento(solicitacao.getEmpresa().getReferencia().toUpperCase()));
        this.solicitacao.getEmpresa().setNire(solicitacao.getEmpresa().getNire().substring(solicitacao.getEmpresa().getSetorQuadraLote().indexOf("@") + 1));
        this.solicitacao.getEmpresa().setSetorQuadraLote(solicitacao.getEmpresa().getSetorQuadraLote().substring(solicitacao.getEmpresa().getSetorQuadraLote().indexOf("@") + 1));
    }

    public void salvarContribuinte() {

        if (solicitacao.getEmpresa().getCpnj() != null) {
            contribuinte = servico.obterIssqnPorCPF(solicitacao.getEmpresa().getCpnj());
            if (contribuinte == null) {
                contribuinte = new Issqn();
                bl = new BairroLogradouro();
                i = new Imovel();
                contribuinte.setPessoa(new Pessoa());
                contribuinte.getPessoa().setTpPessoa(null);
                contribuinte.setImovel(new Imovel());
                contribuinte.getImovel().setBairrologradouro(new BairroLogradouro());
                contribuinte.getImovel().getBairrologradouro().setBairro(new Bairro());
                contribuinte.getImovel().getBairrologradouro().setCidade(new Cidade());
                contribuinte.getImovel().getBairrologradouro().setLogradouro(new Logradouro());
                contribuinte.getImovel().getBairrologradouro().getLogradouro().setLogradouroPK(new LogradouroPK());
                contribuinte.getImovel().getBairrologradouro().getCidade().setEstado(new Estado());
            } else {
                Funcao.mensagemAtencao("Operação cancelada", "Contribuinte já possui cadastro!");
                return;
            }
            salvar();
        }
    }

    public void salvar() {

        List<IssqnCategoria> categoria = new ArrayList<>();

        try {
            categoria = categoriaServico.obterCategorias(solicitacao.getEmpresa().getPorte().getDescricaoPorte(), 1);
            if (!categoria.isEmpty()) {
                contribuinte.setCategoria(categoria.get(0));
            } else {
                IssqnCategoria categoriaNova = new IssqnCategoria();
                categoriaNova.setNome(solicitacao.getEmpresa().getPorte().getDescricaoPorte());
                categoriaNova.setValor(0.0);
                contribuinte.setCategoria(categoriaServico.salvarCategoria(categoriaNova));
            }

            //endereco
            List<Cidade> lstCidade = new ArrayList<>();
            Cidade cidade = new Cidade();
            lstCidade = cidadeServico.getCidadePorNome(solicitacao.getEmpresa().getMunicipio().getNome());
            if (!lstCidade.isEmpty()) {
                cidade = lstCidade.get(0);
                contribuinte.getImovel().getBairrologradouro().setCidade(cidade);
            } else {
                cidade.getEstado().setNome((solicitacao.getEmpresa().getMunicipio().getEstado().getNome()));
                cidade.getEstado().setUf(solicitacao.getEmpresa().getMunicipio().getEstado().getSigla());
                cidade.setNome(solicitacao.getEmpresa().getMunicipio().getNome());
                cidade.setUf(solicitacao.getEmpresa().getMunicipio().getSiglaEstado());
                cidade = cidadeServico.adicionarCidade(cidade);
                contribuinte.getImovel().getBairrologradouro().setCidade(cidade);
            }
            List<Bairro> lstBairro = new ArrayList<>();
            Bairro bairro = new Bairro();
            lstBairro = bairroServico.obterBairrosPorNome(cidade.getIdCidade(), solicitacao.getEmpresa().getBairro());
            if (!lstBairro.isEmpty()) {
                bairro = lstBairro.get(0);
                contribuinte.getImovel().getBairrologradouro().setBairro(bairro);
            } else {
                bairro.setCidade(cidade);
                bairro.setNome(solicitacao.getEmpresa().getBairro());
                Abreviatura ab = new Abreviatura();
                List<Abreviatura> listAb = new ArrayList<>();
                listAb = abreviaturaServico.obterAbreviaturas(solicitacao.getEmpresa().getBairro().substring(0, 3), 1);
                if (!listAb.isEmpty()) {
                    ab = listAb.get(0);
                } else {
                    ab = abreviaturaServico.obterAbreviaturaPorId(23);
                }
                bairro.setAbreviatura(ab);
                bairro = bairroServico.salvarBairro(bairro);
                contribuinte.getImovel().getBairrologradouro().setBairro(bairro);
            }
            List<Logradouro> lstLogradouro = new ArrayList<>();
            Logradouro logradouro = new Logradouro();
            lstLogradouro = logradouroServico.obterLogradourosPorNome(cidade.getIdCidade(), solicitacao.getEmpresa().getTipoLogradouro().getNomeLogradouro()
                    + solicitacao.getEmpresa().getLogradouro());
            if (!lstLogradouro.isEmpty()) {
                logradouro = lstLogradouro.get(0);
                contribuinte.getImovel().getBairrologradouro().setLogradouro(logradouro);
            } else {
                logradouro.setLogradouroPK(new LogradouroPK());
                logradouro.getLogradouroPK().setIdCidade(cidade.getIdCidade());
                Abreviatura ab = new Abreviatura();
                List<Abreviatura> listAb = new ArrayList<>();
                listAb = abreviaturaServico.obterAbreviaturas(solicitacao.getEmpresa().getTipoLogradouro().getNomeLogradouro().substring(0, 3), 1);
                if (!listAb.isEmpty()) {
                    ab = listAb.get(0);
                } else {
                    ab = abreviaturaServico.obterAbreviaturaPorId(23);
                }
                logradouro.setAbreviatura(ab);
                logradouro.setNome(solicitacao.getEmpresa().getTipoLogradouro().getNomeLogradouro() + " " + solicitacao.getEmpresa().getLogradouro());
                logradouro = logradouroServico.adicionarLogradouro(logradouro);
                contribuinte.getImovel().getBairrologradouro().setLogradouro(logradouro);
            }
            List<BairroLogradouro> bairroLogradouros = new ArrayList<>();
            bairroLogradouros = bairroLogradouro.obterBairroPorLogradouro(bairro.getIdBairro(), logradouro.getLogradouroPK().getIdLogradouro());
            if (!bairroLogradouros.isEmpty()) {
                bl = bairroLogradouros.get(0);
            } else {
                bl = contribuinte.getImovel().getBairrologradouro();
            }
            bairroLogradouro.salvarBairroLogradouro(bl);

            //imovel
            List<Imovel> lstImovel = new ArrayList();
            lstImovel = imovelServico.obterImovel(bairro.getNome(), solicitacao.getEmpresa().getNumero(), solicitacao.getEmpresa().getComplemento(), 1);
            if (!lstImovel.isEmpty()) {
                i = lstImovel.get(0);
            } else {
                i = contribuinte.getImovel();
            }
            i.setComplemento(solicitacao.getEmpresa().getComplemento());
            i.setNrImovel(solicitacao.getEmpresa().getNumero());
            i.setPontoReferencia(solicitacao.getEmpresa().getReferencia());
            i.setLote(solicitacao.getEmpresa().getSetorQuadraLote().substring(solicitacao.getEmpresa().getSetorQuadraLote().indexOf("@") + 1).trim());
            i.setCep(solicitacao.getEmpresa().getCep());
            i.setBairrologradouro(bl);
            imovelServico.salvarImovel(i);
            contribuinte.setImovel(i);

            //iss
            contribuinte.setCep(solicitacao.getEmpresa().getCep());
            contribuinte.setAreaEstabelecimento(solicitacao.getEmpresa().getAreaEstabelecimento().doubleValue());
            contribuinte.setAreaImovel(solicitacao.getEmpresa().getAreaImovel().doubleValue());
            contribuinte.setNrJuntaComercial(solicitacao.getEmpresa().getNire().substring(solicitacao.getEmpresa().getSetorQuadraLote().indexOf("@") + 1).trim());
            contribuinte.setCep(solicitacao.getEmpresa().getCep());
            contribuinte.setCnpjCpf(solicitacao.getEmpresa().getCpnj().replace(".", "").replace("-", "").replace("/", "").trim());
            contribuinte.setSenha(StringMD5.md5(Util.extractStr(123)));
            contribuinte.setIdExercicio(global.getExercicio().getAno());
            contribuinte.setMei(solicitacao.getEmpresa().isMei());
            contribuinte.setPendente(true);
            contribuinte.setInativo(false);
            contribuinte.setEncerrado(false);

            //pessoa
            Pessoa p = new Pessoa();
            List<Pessoa> lstPessoa = new ArrayList();
            lstPessoa = pessoaServico.getPessoasPorCpfCnpj(contribuinte.getCnpjCpf());
            if (!lstPessoa.isEmpty()) {
                p = lstPessoa.get(0);
            }
            p.setUf(contribuinte.getImovel().getBairrologradouro().getCidade().getUf());
            p.setCidade(contribuinte.getImovel().getBairrologradouro().getCidade().getNome());
            p.setBairro(contribuinte.getImovel().getBairrologradouro().getBairro().getNome());
            p.setLogradouro(contribuinte.getImovel().getBairrologradouro().getLogradouro().getNome());
            p.setCpfCnpj(((solicitacao.getEmpresa().getCpnj().replace(".", "")).replace("-", "")).replace("/", "").trim());
            p.setNome(solicitacao.getEmpresa().getNomeEmpresa());
            p.setEmail(solicitacao.getEmpresa().getEmail().trim());
            p.setTelefone(solicitacao.getEmpresa().getDDD1() + solicitacao.getEmpresa().getTelefone1());
            p.setCelular(solicitacao.getEmpresa().getDDD2() + solicitacao.getEmpresa().getTelefone2());
            p.setNumero(Util.isNumeric(contribuinte.getImovel().getNrImovel()) ? Util.extractInt(contribuinte.getImovel().getNrImovel()) : null);
            p.setSituacao(Situacao.ATIVO);
            contribuinte.setPessoa(pessoaServico.adicionarPessoa(p));
            servico.salvarIssqn(contribuinte);

            String inscricao = (contribuinte.getCnpjCpf().length() >= 6 ? contribuinte.getCnpjCpf().substring(0, 6) : "") + contribuinte.getIdExercicio() + contribuinte.getId();
            contribuinte.setInscricao(inscricao);
            contribuinte.getPessoa().setInscMunicipal(inscricao);
            if (!contribuinte.getPessoa().getCpfCnpj().isEmpty()) {
                if (contribuinte.getPessoa().getCpfCnpj().length() > 11) {
                    contribuinte.getPessoa().setTpPessoa(TipoPessoa.JURIDICA);
                    if (solicitacao.getStatusSolicitacao().getStatus().toUpperCase().contains("CONCLUÍD")) {
                        for (Licenca l : solicitacao.getLicencas().getLicenca()) {
                            if (l.getOrgao().getNome().toUpperCase().contains("PREFEITURA") && l.getSituacaoLicenca().getDescricao().toUpperCase().contains("CONCLUÍD")) {
                                if (l.getDataEmissao() != null && l.getDataValidade() != null && !l.getNumero().isEmpty()) {
                                    contribuinte.setDtAlvara(l.getDataEmissao().getValue().toGregorianCalendar().getTime());
                                    contribuinte.setDtValidadeAlvara(l.getDataValidade().getValue().toGregorianCalendar().getTime());
                                    contribuinte.setNumAlvara(Util.extractStr(l.getNumero().trim()));
                                }
                            }
                        }
                    }
                } else {
                    contribuinte.getPessoa().setTpPessoa(TipoPessoa.FISICA);
                    contribuinte.setNumAlvara(contribuinte.getCnpjCpf().substring(10, 11) + contribuinte.getCnpjCpf().substring(7, 9) + contribuinte.getCnpjCpf().substring(4, 6));
                }
            }

            if (solicitacao.getStatusSolicitacao().getStatus().toUpperCase().contains("CONCLUÍD")) {
                //alvaras
                for (Licenca l : solicitacao.getLicencas().getLicenca()) {
                    if (!l.getNumero().isEmpty()) {
                        if (l.getOrgao().getNome().toUpperCase().contains("PREFEITURA") && l.getSituacaoLicenca().getDescricao().toUpperCase().contains("CONCLUÍD")) {
                            contribuinte.setDtAlvaraFunc(l.getDataValidade() != null ? l.getDataValidade().getValue().toGregorianCalendar().getTime() : null);
                            contribuinte.setNumAlvaraFunc(Util.extractStr(l.getNumero().trim()));
                        } else if (l.getOrgao().getNome().toUpperCase().contains("VIGIL") && l.getSituacaoLicenca().getDescricao().toUpperCase().contains("CONCLUÍD")) {
                            contribuinte.setDtAlvaraSanitaria(l.getDataValidade() != null ? l.getDataValidade().getValue().toGregorianCalendar().getTime() : null);
                            contribuinte.setNumAlvaraSanitaria(Util.extractStr(l.getNumero().trim()));
                        } else if (l.getOrgao().getNome().toUpperCase().contains("BOMBEIRO") && l.getSituacaoLicenca().getDescricao().toUpperCase().contains("CONCLUÍD")) {
                            contribuinte.setDtAlvaraBombeiro(l.getDataValidade() != null ? l.getDataValidade().getValue().toGregorianCalendar().getTime() : null);
                            contribuinte.setNumAlvaraBombeiro(Util.extractStr(l.getNumero().trim()));
                        } else if (l.getOrgao().getNome().toUpperCase().contains("CETESB") && l.getSituacaoLicenca().getDescricao().toUpperCase().contains("CONCLUÍD")) {
                            contribuinte.setDtAlvaraCetesb(l.getDataValidade() != null ? l.getDataValidade().getValue().toGregorianCalendar().getTime() : null);
                            contribuinte.setNumAlvaraCetesb(Util.extractStr(l.getNumero().trim()));
                        }
                    }
                }
            }
            contribuinte.setEventual(false);
            pessoaServico.adicionarPessoa(contribuinte.getPessoa());
            contribuinte = servico.salvarIssqn(contribuinte);

            if (solicitacao != null && !solicitacao.getEmpresa().getSocios().getResponsavelEmpresa().isEmpty()) {
                for (ResponsavelEmpresa re : solicitacao.getEmpresa().getSocios().getResponsavelEmpresa()) {
                    List<Pessoa> ps = new ArrayList();
                    IssqnSocio is = new IssqnSocio();
                    ps = pessoaServico.getPessoasPorCpfCnpj(re.getCPFResponsavel().replace(".", "").replace("-", "").replace("/", "").trim());
                    if (ps.isEmpty()) {
                        if (re.getCPFResponsavel().equals(solicitacao.getEmpresa().getCpfResponsavel())) {
                            is.setIsResponsavel(true);
                        }
                        is.setPessoa(new Pessoa());
                        is.getPessoa().setCpfCnpj(re.getCPFResponsavel());
                        is.getPessoa().setNome(re.getNomeResponsavel());
                        is.setPessoa(pessoaServico.adicionarPessoa(is.getPessoa()));
                        is.setIss(contribuinte);
                        is.setImovel(contribuinte.getImovel());
                    } else {
                        if (re.getCPFResponsavel().equals(solicitacao.getEmpresa().getCpfResponsavel())) {
                            is.setIsResponsavel(true);
                        }
                        is.setPessoa(pessoaServico.adicionarPessoa(is.getPessoa()));
                        is.setIss(contribuinte);
                        is.setImovel(contribuinte.getImovel());
                    }
                    socioServico.salvarSocio(is);
                }
            }

            if (!solicitacao.getEmpresa().getAtividadesCNAE().getAtividadeCNAE().isEmpty()) {
                for (AtividadeCNAE it : solicitacao.getEmpresa().getAtividadesCNAE().getAtividadeCNAE()) {
                    IssqnCnaeIss ici = new IssqnCnaeIss();
                    ici.setAtividadePrimaria(false);
                    ici.setAtividadeSecundaria(false);
                    ici.setCnae(cnaeServico.obterCnaePorNome(it.getDescricao(), 1).get(0));
                    ici.setIss(contribuinte);
                    servico.salvarCnaeIss(ici);
                }
            }

            Funcao.notificacaoSucesso("Contribuinte salvo com sucesso!");
        } catch (Exception ex) {
            try {
                if (contribuinte.getId() != null) {
                    servico.removerIssqn(contribuinte.getId());
                }
                if (i.getId() != null) {
                    imovelServico.removerImovel(i.getId());
                }
                if (bl.getId() != null) {
                    bairroLogradouro.removerImovel(bl.getId());
                }
            } catch (Exception ex1) {
                Logger.getLogger(ConsultarEmpresasBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Funcao.avisoErro(ex.getMessage());
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

    public List<Solicitacao> getListaEmpresas() {
        return listaEmpresas;
    }

    public void setListaEmpresas(List<Solicitacao> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

}
