/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.JSFUtils;
import br.com.eddydata.bean.UtilBean;
import br.com.eddydata.dto.issqn.CancelamentoDTO;
import br.com.eddydata.dto.issqn.ParcelaDTO;
import br.com.eddydata.dto.issqn.RetornoBuscaParcelaDTO;
import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.entidade.geo.Logradouro;
import br.com.eddydata.entidade.geo.LogradouroPK;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnAnexo;
import br.com.eddydata.entidade.issqn.IssqnCategoria;
import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.entidade.issqn.IssqnCnaeIss;
import br.com.eddydata.entidade.issqn.IssqnContribuinteTaxa;
import br.com.eddydata.entidade.issqn.IssqnHistRegTribContribuinte;
import br.com.eddydata.entidade.issqn.IssqnHistorico;
import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.entidade.issqn.IssqnMovimento;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.entidade.issqn.IssqnNotificacao;
import br.com.eddydata.entidade.issqn.IssqnRamoAtuacao;
import br.com.eddydata.entidade.issqn.IssqnSegmento;
import br.com.eddydata.entidade.issqn.IssqnSocio;
import br.com.eddydata.entidade.issqn.IssqnTaxa;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.entidade.issqn.IssqnUnidadeCalculo;
import br.com.eddydata.entidade.referencia.Situacao;
import br.com.eddydata.entidade.referencia.TipoPessoa;
import br.com.eddydata.servico.admin.BairroServico;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.EstadoServico;
import br.com.eddydata.servico.admin.LogradouroServico;
import br.com.eddydata.servico.admin.PessoaServico;
import br.com.eddydata.servico.issqn.BairroLogradouroServico;
import br.com.eddydata.servico.issqn.CalculoServico;
import br.com.eddydata.servico.issqn.ImovelServico;
import br.com.eddydata.servico.issqn.IssqnAtividadeServico;
import br.com.eddydata.servico.issqn.IssqnCategoriaServico;
import br.com.eddydata.servico.issqn.IssqnHistRegTribContribuinteServico;
import br.com.eddydata.servico.issqn.IssqnHistoricoServico;
import br.com.eddydata.servico.issqn.IssqnMovimentoParcelaServico;
import br.com.eddydata.servico.issqn.IssqnMovimentoServico;
import br.com.eddydata.servico.issqn.IssqnNotificacaoServico;
import br.com.eddydata.servico.issqn.IssqnRamoAtuacaoServico;
import br.com.eddydata.servico.issqn.IssqnSegmentoServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.IssqnSocioServico;
import br.com.eddydata.servico.issqn.IssqnUnidadeCalculoServico;
import br.com.eddydata.servico.issqn.ParcelasServico;
import br.com.eddydata.servico.issqn.TipoCobrancaServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.StringMD5;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.tempuri.ArrayOfString;
import org.tempuri.ConsultarEmpresaPorCNPJRequest;
import org.tempuri.ConsultarEmpresaPorCNPJResponse;
import org.tempuri.ConsultarProtocoloPorCNPJRequest;
import org.tempuri.ConsultarProtocoloPorCNPJResponse;
import org.tempuri.Licenca;
import org.tempuri.Operacionais;
import org.tempuri.Solicitacao;

@Named
@ViewScoped
public class ContribuinteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;
    @EJB
    private IssqnRamoAtuacaoServico ramoAtuacaoServico;
    @EJB
    private IssqnSegmentoServico segmentoServico;
    @EJB
    private IssqnHistoricoServico historicoServico;
    @EJB
    private IssqnSocioServico socioServico;
    @EJB
    private IssqnCategoriaServico categoriaServico;
    @EJB
    private IssqnHistRegTribContribuinteServico historicoRegTribContribServico;
    @EJB
    private IssqnNotificacaoServico notificacaoServico;
    @EJB
    private IssqnUnidadeCalculoServico unidadeCalculoServico;
    @EJB
    private IssqnAtividadeServico atividadeServico;
    @EJB
    private EstadoServico estadoServico;
    @EJB
    private CidadeServico cidadeServico;
    @EJB
    private BairroLogradouroServico bairroLogradouroServico;
    @EJB
    private BairroServico bairroServico;
    @EJB
    private LogradouroServico logradouroServico;
    @EJB
    private ImovelServico imovelServico;
    @EJB
    private CalculoServico calculoServico;
    @EJB
    private ParcelasServico parcelaServico;
    @EJB
    private IssqnMovimentoServico issqnMovimentoServico;
    @EJB
    private IssqnMovimentoParcelaServico issqnMovimentoParcelaServico;
    @EJB
    private TipoCobrancaServico cobrancaServico;

    @Inject
    private GlobalBean global;

    @Inject
    private UtilBean util;

    @Inject
    private AlvaraBean alvaraBean;

    @EJB
    private PessoaServico pessoaServico;

    private Issqn selectedRegistro;
    private List<Issqn> listRegistro;
    private List<IssqnRamoAtuacao> listRamoAtuacao;
    private List<IssqnSegmento> listSegmento;
    private List<IssqnHistorico> listHistoricos;
    private IssqnHistorico histInEdit = new IssqnHistorico();
    private List<IssqnSocio> listSocios;
    private List<IssqnCategoria> listCategorias;
    private String textoFiltro;
    private String filter = "";
    private boolean transacao = false;
    private String assuntoHistorico = "";
    private String observacaoHistorico = "";
    private String dataHistorico = "";
    private String dataProcesso = "";
    private String processo = "";
    private IssqnSocio socio;
    private TipoPessoa[] listTipoPessoa;
    private Integer tipoSocio = null;
    private String nomeAtividade = "Atividade";//CNAE";
    private String razaoSocialAnterior = "";
    private Date dtSimplesNacional = null;
    private boolean simplesNacional;
    private String atividadeAnterior = "";
    private IssqnMotivoCancel motivo;
    private Imovel imovelCobranca = new Imovel();
    private boolean mesmoEndereco = false;
    private Integer[] enderecoExistente;
    private boolean solicitacaoExiste = false;
    private Date dtVencimento;

    // Utilizado para aba de historico de enquadramento Reg. Trib.
    private List<IssqnHistRegTribContribuinte> listHistRegTrib;
    private IssqnHistRegTribContribuinte histTribInEdit = new IssqnHistRegTribContribuinte();
    // ---------------------------------------------------------

    // Utilizado para aba de notificação
    private List<IssqnNotificacao> listNotificacao;
    private IssqnNotificacao notificacaoInEdit = new IssqnNotificacao();
    // -----------------------------------------------------------

    // Utilizado para aba de Unidade de Calculo.
    private List<IssqnUnidadeCalculo> listUnidadeCalculo;
    private IssqnUnidadeCalculo unidadeCalculoInEdit = new IssqnUnidadeCalculo();
    // ---------------------------------------------------------

    // Utilizado para aba de Taxas
    private List<IssqnContribuinteTaxa> contribuinteTaxas = new ArrayList<>();
    private List<IssqnTaxa> taxas;
    // ---------------------------------------------------------

    // Utilizado para aba de Taxas
    private List<IssqnCnaeIss> cnaes;
    // ---------------------------------------------------------

    private List<IssqnAnexo> listAnexos = new ArrayList<>();
    private IssqnAnexo anexos = new IssqnAnexo();

    // Utilizado para aba de Atividade
    private IssqnCnae selectedAtividade = new IssqnCnae();

    //calculo ou relatório
    private Boolean relatorioCalculo = false;
    private List<ParcelaDTO> lstParcela = new ArrayList<>();
    private List<Object[]> listTaxa;
    private List<CancelamentoDTO> lstCancelamento;
    private ParcelaDTO selectedParcelaDTO;
    private CancelamentoDTO selectedParcelaCanceladaDTO;

    private Integer parcelaInicial;
    private Integer parcelaFinal;
    private List<IssqnTipoCobranca> listTipoCobranca = new ArrayList<>();
    private Integer tipoCobranca = 1;
    private Integer tipoCobrancaBoleto = 0;

    //Integração Jucesp
    private Operacionais ws;
    private List<Solicitacao> listaJucesp = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
        enderecoExistente = new Integer[1];
    }

    public void changeEstado() {
        JSFUtils.criarObjetoDeSessao(selectedRegistro.getImovel().getBairrologradouro().getCidade().getEstado(), "estado");
    }

    public void changeCidade() {
        JSFUtils.criarObjetoDeSessao(selectedRegistro.getImovel().getBairrologradouro().getCidade(), "cidade");
    }

    private void limpar() {
        selectedRegistro = new Issqn();
        selectedRegistro.setEventual(false);
        selectedRegistro.setInativo(false);
        selectedRegistro.setEncerrado(false);
        listHistoricos = new ArrayList<>();
        listHistRegTrib = new ArrayList<>();
        listNotificacao = new ArrayList<>();
        listSocios = new ArrayList<>();
        listAnexos = new ArrayList<>();
        socio = new IssqnSocio();
        socio.setPessoa(new Pessoa());
        listUnidadeCalculo = new ArrayList<>();
        cnaes = new ArrayList<>();
        taxas = null;
        selectedRegistro.setPessoa(new Pessoa());
        selectedRegistro.getPessoa().setTpPessoa(TipoPessoa.JURIDICA);
        selectedRegistro.setImovel(new Imovel());
        selectedRegistro.getImovel().setBairrologradouro(new BairroLogradouro());
        selectedRegistro.getImovel().getBairrologradouro().setBairro(new Bairro());
        selectedRegistro.getImovel().getBairrologradouro().setCidade(new Cidade());
        selectedRegistro.getImovel().getBairrologradouro().setLogradouro(new Logradouro());
        selectedRegistro.getImovel().getBairrologradouro().getLogradouro().setLogradouroPK(new LogradouroPK());
        selectedRegistro.getImovel().getBairrologradouro().getCidade().setEstado(new Estado());

        imovelCobranca = new Imovel();
        imovelCobranca.setBairrologradouro(new BairroLogradouro());
        imovelCobranca.getBairrologradouro().setBairro(new Bairro());
        imovelCobranca.getBairrologradouro().setCidade(new Cidade());
        imovelCobranca.getBairrologradouro().setLogradouro(new Logradouro());
        imovelCobranca.getBairrologradouro().getLogradouro().setLogradouroPK(new LogradouroPK());
        imovelCobranca.getBairrologradouro().getCidade().setEstado(new Estado());

        List<Estado> estado = estadoServico.getEstadoPorNome(global.getUsuarioLogado().getOrgao().getEstado());
        List<Cidade> cidade = cidadeServico.getCidadePorNome(global.getUsuarioLogado().getOrgao().getCidade());
        if (!estado.isEmpty() && !cidade.isEmpty()) {
            selectedRegistro.getImovel().getBairrologradouro().setCidade(cidade.get(0));
            selectedRegistro.getImovel().getBairrologradouro().getCidade().setEstado(estado.get(0));
            changeEstado();
            changeCidade();
            imovelCobranca.getBairrologradouro().setCidade(cidade.get(0));
            imovelCobranca.getBairrologradouro().getCidade().setEstado(estado.get(0));
        }
        relatorioCalculo = false;
        lstParcela = new ArrayList<>();
        listTaxa = new ArrayList<>();
        lstCancelamento = new ArrayList<>();
        tipoSocio = null;
        transacao = false;
        parcelaInicial = null;
        parcelaFinal = null;
        listTipoCobranca = cobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
        razaoSocialAnterior = "";
        dtSimplesNacional = null;
        atividadeAnterior = "";
        assuntoHistorico = "";
        dataHistorico = "";
        observacaoHistorico = "";
        dataProcesso = "";
        processo = "";
        motivo = new IssqnMotivoCancel();
        contribuinteTaxas.clear();
        tipoCobranca = 1;
        enderecoExistente = new Integer[1];
        solicitacaoExiste = false;
        selectedParcelaDTO = null;
    }

    public void filtrar() {
        textoFiltro = textoFiltro.replace("-", "").replace(".", "").replace("/", "").trim();
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            textoFiltro = util.retiraAcento(textoFiltro);
            try {
                listRegistro = servico.obterIssqns(textoFiltro, 100, global.getExercicio().getAno(), filter);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
        textoFiltro = "";
        selectedRegistro = null;
    }

    public void adicionar() {
        limpar();
    }

    public void alterar(Issqn registroSelecionado) {
        selectedRegistro = registroSelecionado;
        changeEstado();
        changeCidade();
        if (selectedRegistro.getIdImovelCobranca() != null) {
            try {
                imovelCobranca = imovelServico.obterImovelPorId(selectedRegistro.getIdImovelCobranca());
                if (imovelCobranca.getId().equals(selectedRegistro.getImovel().getId())) {
                    imovelCobranca = selectedRegistro.getImovel();
                    enderecoExistente = new Integer[]{1};
                } else {
                    enderecoExistente = new Integer[]{};
                }
            } catch (Exception ex) {
                Logger.getLogger(ContribuinteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            imovelCobranca = new Imovel();
            imovelCobranca.setBairrologradouro(new BairroLogradouro());
            imovelCobranca.getBairrologradouro().setBairro(new Bairro());
            imovelCobranca.getBairrologradouro().setCidade(new Cidade());
            imovelCobranca.getBairrologradouro().setLogradouro(new Logradouro());
            imovelCobranca.getBairrologradouro().getLogradouro().setLogradouroPK(new LogradouroPK());
            imovelCobranca.getBairrologradouro().getCidade().setEstado(new Estado());
            enderecoExistente = new Integer[]{};

        }

        listHistoricos = new ArrayList<>();
        listHistRegTrib = new ArrayList<>();
        listNotificacao = new ArrayList<>();
        listSocios = new ArrayList<>();
        listUnidadeCalculo = new ArrayList<>();
        listAnexos = new ArrayList<>();
        lstParcela = new ArrayList<>();
        listTaxa = new ArrayList<>();
        buscarDados();
        cnaes = servico.carregarCnae(selectedRegistro.getId(), selectedRegistro.getPessoa().getTpPessoa().ordinal());
        socio = new IssqnSocio();
        socio.setPessoa(new Pessoa());
        taxas = null;
        carregarListagemHistorico();
        carregarListagemHistRegTrib();
        carregarListagemNotificacao();
        carregarListagemSocio();
        carregarListagemUnidadeCalculo();
        carregarListagemAnexos();
        alvaraBean.setInscricao(registroSelecionado.getInscricao());
        alvaraBean.setContribuinte(selectedRegistro);
        parcelaInicial = null;
        parcelaFinal = null;
        listTipoCobranca = cobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
        razaoSocialAnterior = selectedRegistro.getPessoa().getNome();
        dtSimplesNacional = selectedRegistro.getDtSimples();
        simplesNacional = selectedRegistro.getSimplesNacional();
        if (!cnaes.isEmpty()) {
            for (IssqnCnaeIss ici : cnaes) {
                if (ici.getAtividadePrimaria()) {
                    atividadeAnterior = ici.getCnae().getNome();
                }
            }
        }
        motivo = new IssqnMotivoCancel();
        if (selectedRegistro.getEventual()) {
            tipoCobranca = 3;
        } else {
            tipoCobranca = 1;
        }
    }

    public void validaNovoCalculo() {

        if (selectedRegistro.getEventual()) {
            if (selectedRegistro.getDtEventual() == null) {
                Funcao.executarJavaScript("swal({title: 'Atenção!', text: 'Para efetuar cálculo eventual é preciso informar data eventual', type: 'info'});");
                return;
            }
            if (selectedRegistro.getDtEventual().before(Util.dateToday())) {
                Funcao.executarJavaScript("swal({title: 'Atenção!', text: 'Data eventual menor que data atual! Não é possível efetuar cálculo', type: 'info'});");
                return;
            }
        }
        if (motivo.getId() == null && existeCalculo() && !selectedRegistro.getEventual()) {
            Funcao.executarJavaScript("swal({title: 'Atenção!', text: 'Para efetuar novo cálculo é preciso informar motivo do cancelamento', type: 'info'});");
            return;
        }

        calculo();
    }

    public void cancelar() {
        selectedRegistro = null;
        textoFiltro = "";
        carregarListagem();
    }

    public synchronized void salvar() {
        try {
            salvarPessoaContribuinte();
            preSalvar();
        } catch (Exception ex) {
            Funcao.avisoErro(ex.getMessage() == null ? "Erro ao salvar registro" : ex.getMessage());
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
        }
    }

    public synchronized void salvarIncluir() {
        if (preSalvar()) {
            adicionar();
        }
    }

    private boolean preSalvar() {
        return salvarRegistro();
    }

    public void atualizaCamposSocio() throws Exception {
        if (socio != null && socio.getPessoa() != null) {
            String nome = socio.getPessoa().getNome();
            nome = (nome == null ? "" : nome.trim().toUpperCase());

            if (socio.getId() != null && socio.getId() > 0) {
                IssqnSocio socioAlterado = new IssqnSocio();
                socioAlterado = socioServico.obterSocioPorId(socio.getId());
                socio.setPessoa(socioAlterado.getPessoa());
            }
            socio.getPessoa().setNome(nome);
        }
    }

    public void preencherCamposSocio(IssqnSocio is) {
        if (is != null && is.getPessoa() != null) {
            Pessoa p = is.getPessoa();
            socio.setPessoa(p);
        }
    }

    public void adicionarHistorico() {
        if (histInEdit.getId() != null) {
            for (IssqnHistorico h : listHistoricos) {
                if (h.getId() == histInEdit.getId()) {
                    h.setHistoricoAssunto(assuntoHistorico);
                    h.setHistoricoData(Util.extractDate(dataHistorico));
                    h.setHistoricoObs(observacaoHistorico);
                    if (!dataProcesso.isEmpty()) {
                        h.setProcessoData(Util.extractDate(dataProcesso));
                    }
                    h.setProcesso(processo);
                    if (selectedRegistro.getId() != null) {
                        h.setIss(selectedRegistro);
                        try {
                            historicoServico.salvarHistorico(h);
                        } catch (Exception ex) {
                            Logger.getLogger(ContribuinteBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            histInEdit = null;
            assuntoHistorico = "";
            dataHistorico = "";
            observacaoHistorico = "";
            dataProcesso = "";
            processo = "";
            Funcao.executarJavaScript("$('#dataHistorico').mask('99/99/9999')");
            return;
        }

        IssqnHistorico ih = new IssqnHistorico();
        if (assuntoHistorico.isEmpty() || dataHistorico == null || dataHistorico.equals("__/__/____") || observacaoHistorico.isEmpty()) {
            Funcao.mensagemAtencao("Atenção!", "Data Histórico / Assunto ou Observação não estão preenchidos!");
            return;
        }
        ih.setHistoricoAssunto(assuntoHistorico);
        ih.setHistoricoData(Util.extractDate(dataHistorico));
        ih.setHistoricoObs(observacaoHistorico);
        ih.setProcessoData(Util.extractDate(dataProcesso));
        ih.setProcesso(processo);
        if (selectedRegistro.getId() != null) {
            ih.setIss(selectedRegistro);
            try {
                historicoServico.salvarHistorico(ih);
            } catch (Exception ex) {
                Logger.getLogger(ContribuinteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        listHistoricos.add(ih);
        assuntoHistorico = "";
        dataHistorico = "";
        observacaoHistorico = "";
        dataProcesso = "";
        processo = "";
        Funcao.executarJavaScript("$('#dataHistorico').mask('99/99/9999')");

    }

    public void adicionarHistoricoAlteracao(String assunto, Date dthistorico, String observacao) throws Exception {
        IssqnHistorico ih = new IssqnHistorico();
        ih.setIss(selectedRegistro);
        ih.setHistoricoAssunto(assunto);
        ih.setHistoricoData(dthistorico);
        ih.setHistoricoObs(observacao);

        listHistoricos.add(ih);
        historicoServico.salvarHistorico(ih);
    }

    public void removerHistorico(IssqnHistorico ih) {
        if (ih.getId() != null) {
            try {
                historicoServico.removerHistorico(ih.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }

        listHistoricos.remove(ih);
    }

    public void adicionarHistoricoRegTrib() {
        if (listHistRegTrib == null) {
            listHistRegTrib = new ArrayList<IssqnHistRegTribContribuinte>();
        }
        if (histTribInEdit.getRegime() != null && !histTribInEdit.getRegime().isEmpty()) {
            listHistRegTrib.add(histTribInEdit);
            histTribInEdit = new IssqnHistRegTribContribuinte();
        }

        Funcao.executarJavaScript("$('#dtInicio1').mask('99/99/9999')");
        Funcao.executarJavaScript("$('#dtTermino').mask('99/99/9999')");

    }

    public void editarHistoricoRegTrib(IssqnHistRegTribContribuinte ih) {
        listHistRegTrib.remove(ih);
        histTribInEdit = ih;
    }

    public void removerHistoricoRegTrib(IssqnHistRegTribContribuinte ih) {
        if (ih.getId() != null) {
            try {
                historicoRegTribContribServico.removerHistorico(ih.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }

        listHistRegTrib.remove(ih);
    }

    public void salvarHistRegTrib() throws Exception {
        if (selectedRegistro.getId() == null) {
            return;
        }

        if (histTribInEdit != null && histTribInEdit.getRegime() != null
                && !histTribInEdit.getRegime().isEmpty()) {
            listHistRegTrib.add(histTribInEdit);
        }

        if (listHistRegTrib != null && !listHistRegTrib.isEmpty()) {
            for (IssqnHistRegTribContribuinte h : listHistRegTrib) {
                h.setIss(selectedRegistro);
                historicoRegTribContribServico.salvarHistorico(h);
            }
        }
    }

    public void adicionarNotificacao() {
        if (listNotificacao == null) {
            listNotificacao = new ArrayList<IssqnNotificacao>();
        }
        if (notificacaoInEdit.getNotificacao() != null && !notificacaoInEdit.getNotificacao().isEmpty()) {
            listNotificacao.add(notificacaoInEdit);
            notificacaoInEdit = new IssqnNotificacao();
        }
        Funcao.executarJavaScript("$('#dtNotificacao').mask('99/99/9999')");

    }

    public void editarNotificacao(IssqnNotificacao n) {
        listNotificacao.remove(n);
        notificacaoInEdit = n;
    }

    public void removerNotificacao(IssqnNotificacao n) {
        if (n.getId() != null) {
            try {
                notificacaoServico.removerNotificacao(n.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }

        listNotificacao.remove(n);
    }

    public void salvarNotificacao() throws Exception {
        if (selectedRegistro.getId() == null) {
            return;
        }

        if (notificacaoInEdit != null && notificacaoInEdit.getNotificacao() != null
                && !notificacaoInEdit.getNotificacao().isEmpty()) {
            listNotificacao.add(notificacaoInEdit);
        }

        if (listNotificacao != null && !listNotificacao.isEmpty()) {
            for (IssqnNotificacao n : listNotificacao) {
                n.setIss(selectedRegistro);
                notificacaoServico.salvarNotificacao(n);
            }
        }
    }

    public void removerAnexo(IssqnAnexo n) {
        if (n.getId() != null) {
            try {
                servico.removerAnexo(n.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }

        listAnexos.remove(n);
    }

    public void adicionarSocio() throws Exception {

        if (socio.getPessoa().getNome() == null) {
            Funcao.avisoErro("Informar o novo sócio!");
            return;
        }
        if (socio.getId() == null) {
            if (socio.getPessoa().getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
                socio.getPessoa().setTpPessoa(TipoPessoa.FISICA);
            } else {
                socio.getPessoa().setTpPessoa(TipoPessoa.JURIDICA);
            }
            socio.getPessoa().setSituacao(Situacao.ATIVO);
            socio.getPessoa().setCpfCnpj(socio.getPessoa().getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim());
            pessoaServico.adicionarPessoa(socio.getPessoa());
        } else {
            try {
                socio.getPessoa().setNome(util.retiraAcento(socio.getPessoa().getNome().trim().toUpperCase()));
                pessoaServico.adicionarPessoa(socio.getPessoa());
            } catch (Exception ex) {
                Logger.getLogger(ContribuinteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Boolean existe = false;
        for (IssqnSocio is : listSocios) {
            if (is.getPessoa().getCpfCnpj().trim().equals(socio.getPessoa().getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim())) {
                existe = true;
            }
        }
        if (existe == false) {
            listSocios.add(socio);
        } else {
            Funcao.executarJavaScript("swal({title: 'Atenção!', text: 'Sócio já foi adicionado', type: 'error'});");
        }
        socio = null;
        tipoSocio = null;

    }

    public void limparSocio() {
        socio = null;
        tipoSocio = null;
    }

    public void removerSocio(IssqnSocio is) {
        if (is.getId() != null) {
            try {
                socioServico.removerSocio(is.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }

        listSocios.remove(is);
    }

    public boolean listaEventual(ParcelaDTO d) {
        boolean exibe = false;
        if (!d.getFormaPagto().isEmpty()) {
            if (d.getFormaPagto().contains("EVENTUAL") && d.getDtPagto() == null) {
                exibe = true;
            } else {
                exibe = false;
            }
        }
        return exibe;
    }

    public void salvarUnidadeCalculo() throws Exception {
        if (selectedRegistro.getId() == null) {
            return;
        }

        if (unidadeCalculoInEdit != null && unidadeCalculoInEdit.getDescricao() != null
                && !unidadeCalculoInEdit.getDescricao().isEmpty()) {
            listUnidadeCalculo.add(unidadeCalculoInEdit);
        }

        if (listUnidadeCalculo != null && !listUnidadeCalculo.isEmpty()) {
            for (IssqnUnidadeCalculo u : listUnidadeCalculo) {
                u.setIss(selectedRegistro);
                unidadeCalculoServico.salvarUnidadeCalculo(u);
            }
        }
    }

    public void adicionarUnidadeCalculo() {
        if (listUnidadeCalculo == null) {
            listUnidadeCalculo = new ArrayList<IssqnUnidadeCalculo>();
        }
        if (unidadeCalculoInEdit.getDescricao() != null && !unidadeCalculoInEdit.getDescricao().isEmpty()) {
            listUnidadeCalculo.add(unidadeCalculoInEdit);
            unidadeCalculoInEdit = new IssqnUnidadeCalculo();
        }

        Funcao.executarJavaScript("$('#dtInicioUnCalculo').mask('99/99/9999')");
        Funcao.executarJavaScript("$('#dtTerminoUnCalculo').mask('99/99/9999')");

    }

    public void editarUnidadeCalculo(IssqnUnidadeCalculo u) {
        listUnidadeCalculo.remove(u);
        unidadeCalculoInEdit = u;
    }

    public void removerUnidadeCalculo(IssqnUnidadeCalculo u) {
        if (u.getId() != null) {
            try {
                unidadeCalculoServico.removerUnidadeCalculo(u.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }

        listUnidadeCalculo.remove(u);
    }

    public List<IssqnCnae> buscarAtividade(String nome) {
        List<IssqnCnae> atividadeLista = new ArrayList();
        try {
            if (selectedRegistro.getPessoa() == null) {
                Funcao.avisoErro("Preencha o contribuinte antes de vincular atividade");
                return atividadeLista;
            }
            Integer tpPessoa = (selectedRegistro.getPessoa().getTpPessoa() == null ? null
                    : selectedRegistro.getPessoa().getTpPessoa().ordinal());
            atividadeLista = atividadeServico.obterAtividades(nome.trim().toUpperCase(), tpPessoa, 10000);
        } catch (Exception e) {
            Funcao.avisoErro("Não foi possível buscar atividades");
            System.out.println("Não foi possivel preencher a lista de atividades " + e.getMessage());
        }
        return atividadeLista;
    }

    private boolean salvarRegistro() {
        if (transacao) {
            Funcao.avisoErro("Já existe uma transação em andamento!");
            return false;
        }
        transacao = true;
        try {
            String inscricao = "";
            String documento = "";
            selectedRegistro.setIdExercicio(global.getExercicio().getAno());
            selectedRegistro.setPendente(false);
            if (selectedRegistro.getPessoa().getTpPessoa() == TipoPessoa.FISICA && (selectedRegistro.getNumAlvara() == null || selectedRegistro.getNumAlvara().isEmpty())) {
                selectedRegistro.setNumAlvara(selectedRegistro.getCnpjCpf().substring(10, 11) + selectedRegistro.getCnpjCpf().substring(7, 9) + selectedRegistro.getCnpjCpf().substring(4, 6));
                if (selectedRegistro.getDtAlvara() == null || selectedRegistro.getDtAlvara().equals("")) {
                    selectedRegistro.setDtAlvara(Util.dateToday());
                }
            } else {
                if (selectedRegistro.getNumAlvara() == null || selectedRegistro.getNumAlvara().isEmpty()) {
                    dadosJucesp();
                    if ((selectedRegistro.getNumAlvara() == null || selectedRegistro.getNumAlvara().isEmpty()) && !solicitacaoExiste) {
                        selectedRegistro.setNumAlvara(selectedRegistro.getCnpjCpf().substring(10, 11) + selectedRegistro.getCnpjCpf().substring(7, 9) + selectedRegistro.getCnpjCpf().substring(4, 6));
                        if (selectedRegistro.getDtAlvara() == null || selectedRegistro.getDtAlvara().equals("")) {
                            selectedRegistro.setDtAlvara(Util.dateToday());
                        }
                    }
                }
            }
            selectedRegistro = servico.salvarIssqn(selectedRegistro);
            if (selectedRegistro.getInscricao() == null) {
                if (selectedRegistro.getPessoa().getTpPessoa() == TipoPessoa.FISICA) {
                    documento = Util.unMask("###.###.###-##", selectedRegistro.getPessoa().getCpfCnpj());
                } else {
                    documento = Util.unMask("##.###.###/####-##", selectedRegistro.getPessoa().getCpfCnpj());
                }
                inscricao = (documento.length() >= 6 ? documento.substring(0, 6) : "") + selectedRegistro.getIdExercicio() + selectedRegistro.getId();
                selectedRegistro.setInscricao(inscricao);

                selectedRegistro.getPessoa().setInscMunicipal(inscricao);
                pessoaServico.adicionarPessoa(selectedRegistro.getPessoa());
                selectedRegistro = servico.salvarIssqn(selectedRegistro);
            }

            if (listHistoricos != null && !listHistoricos.isEmpty()) {
                for (IssqnHistorico ih : listHistoricos) {
                    ih.setIss(selectedRegistro);
                    historicoServico.salvarHistorico(ih);
                }
            }

            if (listSocios != null && !listSocios.isEmpty()) {
                for (IssqnSocio is : listSocios) {
                    if (is.getPessoa().getId() == 0) {
                        is.getPessoa().setId(null);
                        is.setPessoa(pessoaServico.adicionarPessoa(is.getPessoa()));
                    }
                    is.setIss(selectedRegistro);
                    is.setImovel(selectedRegistro.getImovel());
                    socioServico.salvarSocio(is);
                }
            }

            if (taxas != null && !taxas.isEmpty()) {
                contribuinteTaxas.clear();
                for (IssqnTaxa it : taxas) {
                    if (it.getContribuinteTaxa().getId() == null) {
                        it.getContribuinteTaxa().setIss(selectedRegistro);
                    }
                    if (it.getContribuinteTaxa().getVlTaxa() != null) {
                        contribuinteTaxas.add(servico.salvarContribuinteTaxa(it.getContribuinteTaxa()));
                    } else if (it.getContribuinteTaxa().getId() != null) {
                        servico.removerContribuinteTaxa(it.getContribuinteTaxa().getId());
                        contribuinteTaxas.remove(it.getContribuinteTaxa());
                    }
                }
            }

            if (cnaes != null && !cnaes.isEmpty()) {
                for (IssqnCnaeIss it : cnaes) {
                    if (!it.getCnae().getNome().equals(atividadeAnterior) && it.getAtividadePrimaria() && !atividadeAnterior.isEmpty()) {
                        adicionarHistoricoAlteracao("Atividade/CNAE alterada", Util.dateToday(), "A atividade primária foi alterada de: " + atividadeAnterior.toUpperCase() + " para: " + it.getCnae().getNome().toUpperCase().toString());
                    }
                    if (it.getId() == null) {
                        it.setIss(selectedRegistro);
                    }
                    servico.salvarCnaeIss(it);
                }
            }

            SimpleDateFormat fSqlDate = new SimpleDateFormat("dd/MM/yyyy");
            if (!selectedRegistro.getPessoa().getNome().equals(razaoSocialAnterior) && !razaoSocialAnterior.isEmpty()) {
                adicionarHistoricoAlteracao("Razão Social alterada", Util.dateToday(), "A razão social foi alterada de: " + razaoSocialAnterior.toUpperCase() + " para: " + selectedRegistro.getPessoa().getNome().toUpperCase().toString() + ".");
            }
            if (selectedRegistro.getSimplesNacional() != simplesNacional) {
                if (selectedRegistro.getSimplesNacional() && selectedRegistro.getDtSimples() != null) {
                    adicionarHistoricoAlteracao("Simples Nacional ativo", Util.dateToday(), "Simples nacional ativo. Data simples nacional: " + fSqlDate.format(selectedRegistro.getDtSimples()));
                }
                if (!selectedRegistro.getSimplesNacional()) {
                    adicionarHistoricoAlteracao("Simples Nacional inativo", Util.dateToday(), "Simples nacional inativo.");
                }
            }
            if (selectedRegistro.getSimplesNacional() && !selectedRegistro.getDtSimples().equals(dtSimplesNacional) && dtSimplesNacional != null) {
                adicionarHistoricoAlteracao("Data Simples Nacional alterada", Util.dateToday(), "A data de simples nacional foi alterada de: " + fSqlDate.format(dtSimplesNacional) + " para: " + fSqlDate.format(selectedRegistro.getDtSimples()) + ".");
            }

            salvarNotificacao();
            salvarHistRegTrib();
            salvarUnidadeCalculo();
            salvarAnexos();
            encerrarContribuinte();

        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return false;
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        } finally {
            transacao = false;
        }

        if (relatorioCalculo) {
            return false;
        }
        Funcao.notificacaoSucesso("Registro salvo com sucesso!");

        carregarListagem();
        limpar();
        selectedRegistro = null;
        return true;
    }

    private void encerrarContribuinte() {
        try {
            if (selectedRegistro.getInativo() || selectedRegistro.getEncerrado()) {
                if (!selectedRegistro.getEventual()) {
                    Date dataAtualAux = selectedRegistro.getInativo() ? selectedRegistro.getDtInatividade() : selectedRegistro.getDtEncerramento();
                    if (dataAtualAux == null) {
                        dataAtualAux = Util.dateToday();
                    }
                    Calendar dataEncerradoInativo = new GregorianCalendar();
                    dataEncerradoInativo.setTimeInMillis(dataAtualAux.getTime());
                    List<IssqnMovimento> movimento = new ArrayList<IssqnMovimento>();
                    movimento = issqnMovimentoServico.buscaMovimentoPorIss(selectedRegistro.getId(), global.getExercicio().getAno());
                    if (!movimento.isEmpty()) {
                        for (IssqnMovimento im : movimento) {
                            List<IssqnMovimentoParcela> parcela = new ArrayList<IssqnMovimentoParcela>();
                            parcela = issqnMovimentoParcelaServico.buscarMovimentoPorIdMovimento(im.getId());
                            for (IssqnMovimentoParcela imp : parcela) {
                                Calendar vencimento = new GregorianCalendar();
                                vencimento.setTime(imp.getDtVencimento());
                                if (imp.getIdTipoCobranca().getQtdParcela() == 1 && imp.getIdTipoCobranca().getIdExercicio().intValue() == global.getExercicio().getAno().intValue()) {
                                    imp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                                    imp.setObsCancel("ENCERRAMENTO DE INSCRIÇÃO");
                                    imp.setCanceladoPagamento(1);
                                    imp.setStatus(2);
                                    issqnMovimentoParcelaServico.salvarMovimento(imp);
                                }
                                if (dataEncerradoInativo.before(vencimento)) {
                                    if (dataEncerradoInativo.get(Calendar.DAY_OF_MONTH) < 15) {
                                        imp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                                        imp.setObsCancel("ENCERRAMENTO DE INSCRIÇÃO");
                                        imp.setCanceladoPagamento(1);
                                        imp.setStatus(2);
                                        issqnMovimentoParcelaServico.salvarMovimento(imp);
                                    } else {
                                        if (dataEncerradoInativo.get(Calendar.MONTH) + 1 == vencimento.get(Calendar.MONTH)) {
                                            continue;
                                        }
                                        imp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                                        imp.setObsCancel("ENCERRAMENTO DE INSCRIÇÃO");
                                        imp.setCanceladoPagamento(1);
                                        imp.setStatus(2);
                                        issqnMovimentoParcelaServico.salvarMovimento(imp);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            buscarDados();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Funcao.avisoErro("Não foi possível gerar movimentação");
        }

    }

    public void removerAtividade(IssqnCnaeIss is) {
        if (is.getId() != null) {
            try {
                servico.removerAtividade(is.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }
        cnaes.remove(is);
        try {
            adicionarHistoricoAlteracao("Atividade/CNAE removida", Util.dateToday(), "A atividade " + is.getCnae().getNome().toUpperCase().toString() + " foi removida");
        } catch (Exception ex) {
            Util.messageAlert(ex.getMessage());
        }
    }

    public void remover() {
        try {
            servico.removerIssqn(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Não é possível remover!\nExiste atividade ,sócio ou movimento vinculado ao registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
            return;
        }
        Funcao.notificacaoSucesso("Removido com sucesso");
        selectedRegistro = null;
        carregarListagem();
    }

    public void alteraValorTaxa(IssqnTaxa it) {

        for (IssqnTaxa t : taxas) {
            if (t.getContribuinteTaxa().getVlTaxa() != null) {
                if (!t.getContribuinteTaxa().getVlTaxa().equals(it.getContribuinteTaxa().getVlTaxa()) && t.getContribuinteTaxa().getId().equals(it.getContribuinteTaxa().getId())) {
                    t.getContribuinteTaxa().setVlTaxa(it.getContribuinteTaxa().getVlTaxa());
                }
            }
        }
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterIssqns("", null, global.getExercicio().getAno(), filter);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void carregarListagemHistorico() {
        try {
            listHistoricos = historicoServico.obterHistoricoPorContribuinte(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void selecionarHistorico(IssqnHistorico ih) {
        SimpleDateFormat fSqlDate = new SimpleDateFormat("dd/MM/yyyy");
        dataHistorico = fSqlDate.format(ih.getHistoricoData());
        assuntoHistorico = ih.getHistoricoAssunto();
        observacaoHistorico = ih.getHistoricoObs();
        if (ih.getProcessoData() != null) {
            dataProcesso = fSqlDate.format(ih.getProcessoData());
        }
        processo = ih.getProcesso();
        histInEdit = ih;
    }

    public void carregarListagemHistRegTrib() {
        try {
            listHistRegTrib = historicoRegTribContribServico.obterHistoricoPorContribuinte(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void carregarListagemNotificacao() {
        try {
            listNotificacao = notificacaoServico.obterNotificacaoPorContribuinte(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void carregarListagemSocio() {
        try {
            listSocios = socioServico.obterSocioPorContribuinte(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void carregarListagemUnidadeCalculo() {
        try {
            listUnidadeCalculo = unidadeCalculoServico.obterUnidadeCalculoPorContribuinte(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void carregarListagemAnexos() {
        try {
            listAnexos = servico.obterAnexos(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void cnaePrimaria(IssqnCnaeIss selecionado) {
        if (cnaes != null && selecionado.getCnae() != null) {
            for (IssqnCnaeIss a : cnaes) {
                if (!a.getCnae().equals(selecionado.getCnae())) { //demais
                    a.setAtividadePrimaria(false);
                    a.setAtividadeSecundaria(true);
                } else if (a.getAtividadePrimaria()) { //primaria for selecionada
                    a.setAtividadePrimaria(true);
                    a.setAtividadeSecundaria(false);
                } else { //primaria desmarcada
                    a.setAtividadePrimaria(false);
                    a.setAtividadeSecundaria(false);
                }
            }
        }
    }

    public void cnaeSecundaria(IssqnCnaeIss selecionado) {
        if (cnaes != null && selecionado.getCnae() != null) {
            for (IssqnCnaeIss a : cnaes) {
                if (a.getCnae().equals(selecionado.getCnae())) {
                    a.setAtividadePrimaria(false);
                }
            }
        }
    }

    private void buscarDados() {
        StringBuilder pesquisa = new StringBuilder();
        pesquisa.append("AND I.INSCRICAO = ").append(Util.quotarStr(selectedRegistro.getInscricao()));

        //PARCELAS
        try {
            lstParcela = parcelaServico.obterParcelas(global.getExercicio().getAno(), pesquisa.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            Funcao.avisoErro("Não foi possível buscar as parcelas do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

        //CANCELAMENTOS
        try {
            lstCancelamento = parcelaServico.obterCancelamentos(global.getExercicio().getAno(), pesquisa.toString());
        } catch (Exception ex) {
            Funcao.avisoAtencao("Não foi possível buscar os cancelamentos do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }
    }

    public void onParcelaSelected(SelectEvent event) {
        selectedParcelaDTO = (ParcelaDTO) event.getObject();
        if (selectedParcelaDTO == null) {
            return;
        }
        StringBuilder pesquisa = new StringBuilder();
        pesquisa.append(" AND II.INSCRICAO = ").append(Util.quotarStr(selectedRegistro.getInscricao()));
        pesquisa.append(" AND MP.NOSSO_NUMERO = ").append(Util.quotarStr(selectedParcelaDTO.getNossoNumero()));
        Integer movimentoId;
        try {
            List<RetornoBuscaParcelaDTO> lstBuscaParcela = parcelaServico.buscarParcela(global.getExercicio().getAno(), pesquisa.toString());
            if (lstBuscaParcela.size() == 1) {
                movimentoId = lstBuscaParcela.get(0).getIdMovimento();
            } else {
                movimentoId = lstBuscaParcela.get(lstBuscaParcela.size() - 1).getIdMovimento();
            }

            listTaxa = parcelaServico.obterTaxas(movimentoId, selectedParcelaDTO.getParc(), selectedParcelaDTO.getValor(), Util.extractInt(selectedParcelaDTO.getNossoNumero()));
        } catch (Exception ex) {
            Funcao.avisoAtencao("Não foi possível buscar as taxas do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

    }

    public void cancelaParcelaSelecionada(ParcelaDTO selectedParcelaDTO) {
        if (selectedParcelaDTO == null) {
            return;
        }
        try {
            IssqnMovimentoParcela i = issqnMovimentoParcelaServico.buscarMovimentoPorNossoNumero(Util.extractInt(selectedParcelaDTO.getNossoNumero()));
            i.setDtCanceladoPagamento(Util.dateToday());
            if (motivo == null) {
                i.setObsCancel("ENCERRAMENTO DE INSCRIÇÃO");
            } else {
                if (motivo.getDescricao() != null) {
                    i.setObsCancel(motivo.getDescricao());
                } else {
                    i.setObsCancel("ENCERRAMENTO DE INSCRIÇÃO");
                }
            }
            i.setCanceladoPagamento(1);
            i.setStatus(2);
            i = issqnMovimentoParcelaServico.salvarMovimento(i);

            buscarDados();
        } catch (Exception ex) {
            Funcao.avisoAtencao("Não foi possível cancelar parcelas do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

    }

    public void estornaParcelaSelecionada(CancelamentoDTO selectedParcelaDTO) {
        if (selectedParcelaDTO == null) {
            return;
        }
        try {
            IssqnMovimentoParcela i = issqnMovimentoParcelaServico.buscarMovimentoPorNossoNumero(Util.extractInt(selectedParcelaDTO.getNossoNumero()));
            i.setDtCanceladoPagamento(null);
            i.setObsCancel(null);
            i.setCanceladoPagamento(null);
            i.setStatus(null);
            i = issqnMovimentoParcelaServico.salvarMovimento(i);

            buscarDados();
        } catch (Exception ex) {
            Funcao.avisoAtencao("Não foi possível cancelar parcelas do contribuinte");
            System.out.println(ex.getMessage());
            return;
        }

    }

    public void imprimirBoleto() {
        if (lstParcela.isEmpty()) {
            Funcao.avisoErro("Não foi efetuado o cálculo para este contribuinte!");
            return;
        }
        StringBuilder pesquisa = new StringBuilder();

        pesquisa.append(" AND II.INSCRICAO = ").append(Util.quotarStr(selectedRegistro.getInscricao()));

        if (parcelaInicial != null) {
            pesquisa.append(" AND MP.PARCELA >= ").append(parcelaInicial);
        }
        if (parcelaFinal != null) {
            pesquisa.append(" AND MP.PARCELA <= ").append(parcelaFinal);
        }

        if (tipoCobrancaBoleto != 0) {
            pesquisa.append(" AND TC.TP_COBRANCA = ").append(tipoCobrancaBoleto);
        }

        try {
            servico.gerarCobranca(global.getUsuarioLogado().getOrgao().getIdOrgao(), pesquisa.toString(), global.getExercicio().getAno(), 2, tipoCobrancaBoleto, getDtVencimento());
        } catch (Exception ex) {
            Funcao.avisoErro(ex.getMessage() == null ? "Não foi possível fazer a impressão!" : ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public void imprimirFebraban() {
        if (lstParcela.isEmpty()) {
            Funcao.avisoErro("Não foi efetuado o cálculo para este contribuinte!");
            return;
        }
        StringBuilder pesquisa = new StringBuilder();

        pesquisa.append(" AND II.INSCRICAO = ").append(Util.quotarStr(selectedRegistro.getInscricao()));

        if (parcelaInicial != null) {
            pesquisa.append(" AND MP.PARCELA >= ").append(parcelaInicial);
        }
        if (parcelaFinal != null) {
            pesquisa.append(" AND MP.PARCELA <= ").append(parcelaFinal);
        }

        if (tipoCobrancaBoleto != 0) {
            pesquisa.append(" AND TC.TP_COBRANCA = ").append(tipoCobrancaBoleto);
        }

        try {
            servico.gerarBoletoFebraban(global.getUsuarioLogado().getOrgao().getIdOrgao(), pesquisa.toString(), global.getExercicio().getAno(), 2, tipoCobrancaBoleto, getDtVencimento());
        } catch (Exception ex) {
            Funcao.avisoErro(ex.getMessage() == null ? "Não foi possível fazer a impressão!" : ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

//------------ getters e setters ---------------//
    public Issqn getSelectedRegistro() {

        if (selectedRegistro != null && selectedRegistro.getPessoa() == null) {
            selectedRegistro.setPessoa(new Pessoa());
        }

        return selectedRegistro;
    }

    public void setSelectedRegistro(Issqn selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<Issqn> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<Issqn> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public List<IssqnRamoAtuacao> getListRamoAtuacao() {
        if (listRamoAtuacao == null && selectedRegistro != null) {
            try {
                listRamoAtuacao = ramoAtuacaoServico.obterRamosAtuacao("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar ramos de atuacao");
                System.out.println("Erro ao buscar ramos de atuacao\n" + ex.getMessage());
            }
        }
        return listRamoAtuacao;
    }

    public void setListRamoAtuacao(List<IssqnRamoAtuacao> listRamoAtuacao) {
        this.listRamoAtuacao = listRamoAtuacao;
    }

    public List<IssqnSegmento> getListSegmento() {
        if (listSegmento == null && selectedRegistro != null) {
            try {
                listSegmento = segmentoServico.obterSegmento("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar segmento");
                System.out.println("Erro ao buscar segmento\n" + ex.getMessage());
            }
        }
        return listSegmento;
    }

    public void setListSegmento(List<IssqnSegmento> listSegmento) {
        this.listSegmento = listSegmento;
    }

    public List<IssqnCategoria> getListCategorias() {
        if (listCategorias == null && selectedRegistro != null) {
            try {
                listCategorias = categoriaServico.obterCategorias("", 10000);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar categorias");
                System.out.println("Erro ao buscar categorias\n" + ex.getMessage());
            }
        }
        return listCategorias;
    }

    public void imprimirContribuinteDocFiscalizacao() {
        try {
            servico.imprimirContribuinteDocFiscalizacao(global.getUsuarioLogado().getOrgao().getIdOrgao(), global.getExercicio().getAno());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível gerar o relatório");
            System.out.println(ex.getMessage());
        }
    }

    public void setListCategorias(List<IssqnCategoria> listCategorias) {
        this.listCategorias = listCategorias;
    }

    public String getAssuntoHistorico() {
        return assuntoHistorico;
    }

    public void setAssuntoHistorico(String assuntoHistorico) {
        this.assuntoHistorico = assuntoHistorico;
    }

    public String getObservacaoHistorico() {
        return observacaoHistorico;
    }

    public void setObservacaoHistorico(String observacaoHistorico) {
        this.observacaoHistorico = observacaoHistorico;
    }

    public String getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(String dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public String getDataProcesso() {
        return dataProcesso;
    }

    public void setDataProcesso(String dataProcesso) {
        this.dataProcesso = dataProcesso;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public List<IssqnHistorico> getListHistoricos() {
        return listHistoricos;
    }

    public void setListHistoricos(List<IssqnHistorico> listHistoricos) {
        this.listHistoricos = listHistoricos;
    }

    public IssqnSocio getSocio() {
        if (socio == null) {
            socio = new IssqnSocio();
            socio.setPessoa(new Pessoa());
        }
        return socio;
    }

    public void setSocio(IssqnSocio socio) {
        this.socio = socio;
    }

    public List<IssqnSocio> getListSocios() {
        return listSocios;
    }

    public void setListSocios(List<IssqnSocio> listSocios) {
        this.listSocios = listSocios;
    }

    public List<IssqnHistRegTribContribuinte> getListHistRegTrib() {
        return listHistRegTrib;
    }

    public void setListHistRegTrib(List<IssqnHistRegTribContribuinte> listHistRegTrib) {
        this.listHistRegTrib = listHistRegTrib;
    }

    public List<IssqnNotificacao> getListNotificacao() {
        return listNotificacao;
    }

    public void setListNotificacao(List<IssqnNotificacao> listNotificacao) {
        this.listNotificacao = listNotificacao;
    }

    public List<IssqnUnidadeCalculo> getListUnidadeCalculo() {
        return listUnidadeCalculo;
    }

    public void setListUnidadeCalculo(List<IssqnUnidadeCalculo> listUnidadeCalculo) {
        this.listUnidadeCalculo = listUnidadeCalculo;
    }

    public IssqnUnidadeCalculoServico getUnidadeCalculoServico() {
        return unidadeCalculoServico;
    }

    public void setUnidadeCalculoServico(IssqnUnidadeCalculoServico unidadeCalculoServico) {
        this.unidadeCalculoServico = unidadeCalculoServico;
    }

    public IssqnUnidadeCalculo getUnidadeCalculoInEdit() {
        return unidadeCalculoInEdit;
    }

    public void setUnidadeCalculoInEdit(IssqnUnidadeCalculo unidadeCalculoInEdit) {
        this.unidadeCalculoInEdit = unidadeCalculoInEdit;
    }

    public List<IssqnAnexo> getListAnexos() {
        if (listAnexos == null && selectedRegistro != null) {
            listAnexos = servico.obterAnexos(selectedRegistro.getId());
        }
        return listAnexos;
    }

    public void setListAnexos(List<IssqnAnexo> listAnexos) {
        this.listAnexos = listAnexos;
    }

    public IssqnHistRegTribContribuinte getHistTribInEdit() {
        return histTribInEdit;
    }

    public void setHistTribInEdit(IssqnHistRegTribContribuinte histTribInEdit) {
        this.histTribInEdit = histTribInEdit;
    }

    public IssqnNotificacao getNotificacaoInEdit() {
        return notificacaoInEdit;
    }

    public void setNotificacaoInEdit(IssqnNotificacao notificacaoInEdit) {
        this.notificacaoInEdit = notificacaoInEdit;
    }

    public List<IssqnContribuinteTaxa> getContribuinteTaxas() {
        return contribuinteTaxas;
    }

    public void setContribuinteTaxas(List<IssqnContribuinteTaxa> contribuinteTaxas) {
        this.contribuinteTaxas = contribuinteTaxas;
    }

    public List<IssqnTaxa> getTaxas() {
        if (selectedRegistro != null) {
            taxas = servico.carregarAliquotas(selectedRegistro.getId());
        }
        return taxas;
    }

    public void setTaxas(List<IssqnTaxa> taxas) {
        this.taxas = taxas;
    }

    public List<IssqnCnaeIss> getCnaes() {
        if (cnaes == null && selectedRegistro != null && selectedRegistro.getPessoa() != null) {
            Integer tpPessoa = (selectedRegistro.getPessoa().getTpPessoa() == null ? null
                    : selectedRegistro.getPessoa().getTpPessoa().ordinal());
            if (tpPessoa != null) {
                cnaes = servico.carregarCnae(selectedRegistro.getId(), tpPessoa);
            }
        }
        return cnaes;
    }

    public void setCnaes(List<IssqnCnaeIss> cnaes) {
        this.cnaes = cnaes;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public IssqnCnae getSelectedAtividade() {
        return selectedAtividade;
    }

    public void setSelectedAtividade(IssqnCnae selectedAtividade) {
        this.selectedAtividade = selectedAtividade;
    }

    public void doAdicionaAtividade(SelectEvent event) {
        IssqnCnae ps = (IssqnCnae) event.getObject();
        selectedAtividade = null;
        for (IssqnCnaeIss pse : cnaes) {
            if (pse.getCnae().getId() == ps.getId().intValue()) {
                Funcao.avisoErro("Esta atividade já está vinculada!");
                return;
            }
        }
        IssqnCnaeIss cnaeIss = new IssqnCnaeIss();
        cnaeIss.setCnae(ps);
        cnaeIss.setIss(selectedRegistro);
        cnaes.add(cnaeIss);
    }

    public ArrayList<IssqnCnae> doFiltroAtividade(String nome) {
        List<IssqnCnae> listar;
        ArrayList<IssqnCnae> listaNome = new ArrayList<>();
        nome = nome.replace("-", "").replace("/", "").replace(".", "");
        try {
            if (Util.isNumeric(nome)) {
                listar = servico.obterCnaePorCodigo(nome + "%");
            } else {
                nome = util.retiraAcento(nome);
                Integer tpPessoa = (selectedRegistro.getPessoa().getTpPessoa() == null ? null
                        : selectedRegistro.getPessoa().getTpPessoa().ordinal());

                listar = servico.obterCnaePorNome(nome, tpPessoa);
            }
            for (IssqnCnae p : listar) {
                listaNome.add(p);
            }
        } catch (Exception e) {
            System.out.println("Não foi possivel preencher lista para consulta " + e.getMessage());
        }
        return listaNome;
    }

    public void salvarPessoaContribuinte() throws Exception {
        if (transacao) {
            Funcao.avisoErro("Já existe uma transação em andamento!");
            return;
        }

        if (selectedRegistro.getDtAbertura() == null) {
            Funcao.avisoAtencao("Informar Data Abertura");
            return;
        }

        transacao = true;

        try {
            if (selectedRegistro.getPessoa().getSituacao() == null) {
                selectedRegistro.getPessoa().setSituacao(Situacao.ATIVO);
            }

            if (selectedRegistro.getPessoa().getId() != null) {
                if (selectedRegistro.getPessoa().getId() == 0) {
                    selectedRegistro.getPessoa().setId(null);
                }
            }

            selectedRegistro.getPessoa().setTpPessoa(selectedRegistro.getPessoa().getTpPessoa());
            selectedRegistro.getPessoa().setCpfCnpj(((selectedRegistro.getPessoa().getCpfCnpj().replace(".", "")).replace("-", "")).replace("/", ""));
            selectedRegistro.getPessoa().setNome(util.retiraAcento(selectedRegistro.getPessoa().getNome().toUpperCase().trim()));
            selectedRegistro.setFantasia(selectedRegistro.getFantasia() != null ? selectedRegistro.getFantasia().toUpperCase().trim() : "");

            if (selectedRegistro.getId() == null) {
                if (selectedRegistro.getSenha() != null) {
                    selectedRegistro.setSenha(StringMD5.md5(selectedRegistro.getSenhaBase()));
                } else {
                    selectedRegistro.setSenhaBase(Util.extractStr(123));
                    selectedRegistro.setSenha(StringMD5.md5(Util.extractStr(123)));
                }
            } else {
                if (selectedRegistro.getSenha() != null || !selectedRegistro.getSenha().isEmpty()) {
                    selectedRegistro.setSenha(StringMD5.md5(selectedRegistro.getSenhaBase()));
                }
            }

            if (selectedRegistro.getDtInicio() == null) {
                selectedRegistro.setDtInicio(Util.dateToday());
            }

            selectedRegistro.getImovel().setNrImovel(selectedRegistro.getImovel().getNrImovel().replace(".", "").replace(",", "").replace("-", "").trim());
            if (!Util.isNumeric(selectedRegistro.getImovel().getNrImovel())) {
                selectedRegistro.getImovel().setNrImovel(null);
            }

            if (!incluirEndereco()) {
                imovelCobranca = new Imovel();
                imovelCobranca.setBairrologradouro(new BairroLogradouro());
                imovelCobranca.getBairrologradouro().setBairro(new Bairro());
                imovelCobranca.getBairrologradouro().setCidade(new Cidade());
                imovelCobranca.getBairrologradouro().setLogradouro(new Logradouro());
                imovelCobranca.getBairrologradouro().getLogradouro().setLogradouroPK(new LogradouroPK());
                imovelCobranca.getBairrologradouro().getCidade().setEstado(new Estado());
                throw new Exception("Preencher os dados de endereço de cobrança");
            }

            selectedRegistro.getPessoa().setUf(selectedRegistro.getImovel().getBairrologradouro().getCidade().getUf());
            selectedRegistro.getPessoa().setCidade(selectedRegistro.getImovel().getBairrologradouro().getCidade().getNome());
            selectedRegistro.getPessoa().setBairro(selectedRegistro.getImovel().getBairrologradouro().getBairro().getNome());
            selectedRegistro.getPessoa().setLogradouro(selectedRegistro.getImovel().getBairrologradouro().getLogradouro().getNome());
            selectedRegistro.getPessoa().setNumero(Util.isNumeric(selectedRegistro.getImovel().getNrImovel()) ? Util.extractInt(selectedRegistro.getImovel().getNrImovel()) : null);
            selectedRegistro.getPessoa().setNomeFantasia(selectedRegistro.getFantasia());
            selectedRegistro.setPessoa(pessoaServico.adicionarPessoa(selectedRegistro.getPessoa()));
            selectedRegistro.setInscrEstadual(selectedRegistro.getPessoa().getInscEstadual());
            selectedRegistro.setCep(selectedRegistro.getImovel().getCep());
            selectedRegistro.setCnpjCpf(selectedRegistro.getPessoa().getCpfCnpj());

        } finally {
            transacao = false;
        }

    }

    private boolean incluirEndereco() {
        try {
            if (selectedRegistro.getImovel().getBairrologradouro().getCidade().getIdCidade() != null) {
                if (selectedRegistro.getImovel().getBairrologradouro().getCidade().getIdCidade() == 0) {
                    selectedRegistro.getImovel().getBairrologradouro().getCidade().setIdCidade(null);
                }
            }

            if (selectedRegistro.getImovel().getBairrologradouro().getBairro().getIdBairro() != null) {
                if (selectedRegistro.getImovel().getBairrologradouro().getBairro().getIdBairro() == 0) {
                    selectedRegistro.getImovel().getBairrologradouro().getBairro().setIdBairro(null);
                }
            }

            if (selectedRegistro.getImovel().getBairrologradouro().getLogradouro().getLogradouroPK().getIdLogradouro() != null) {
                if (selectedRegistro.getImovel().getBairrologradouro().getLogradouro().getLogradouroPK().getIdLogradouro() == 0) {
                    selectedRegistro.getImovel().getBairrologradouro().getLogradouro().getLogradouroPK().setIdLogradouro(null);
                }
            }

            cidadeServico.adicionarCidade(selectedRegistro.getImovel().getBairrologradouro().getCidade());
            selectedRegistro.getImovel().getBairrologradouro().getBairro().setCidade(selectedRegistro.getImovel().getBairrologradouro().getCidade());
            bairroServico.salvarBairro(selectedRegistro.getImovel().getBairrologradouro().getBairro());
            selectedRegistro.getImovel().getBairrologradouro().getLogradouro().getLogradouroPK().setIdCidade(selectedRegistro.getImovel().getBairrologradouro().getCidade().getIdCidade());
            logradouroServico.adicionarLogradouro(selectedRegistro.getImovel().getBairrologradouro().getLogradouro());

            bairroLogradouroServico.salvarBairroLogradouro(selectedRegistro.getImovel().getBairrologradouro());
            imovelServico.salvarImovel(selectedRegistro.getImovel());

            if (enderecoExistente.length > 0) {
                imovelCobranca = selectedRegistro.getImovel();
            } else {
                if (imovelCobranca.getBairrologradouro().getBairro() == null || imovelCobranca.getBairrologradouro().getCidade() == null
                        || imovelCobranca.getBairrologradouro().getLogradouro() == null) {
                    return false;
                }

                if (imovelCobranca.getBairrologradouro().getCidade().getIdCidade() != null) {
                    if (imovelCobranca.getBairrologradouro().getCidade().getIdCidade() == 0) {
                        imovelCobranca.getBairrologradouro().getCidade().setIdCidade(null);
                    }
                }

                if (imovelCobranca.getBairrologradouro().getBairro().getIdBairro() != null) {
                    if (imovelCobranca.getBairrologradouro().getBairro().getIdBairro() == 0) {
                        imovelCobranca.getBairrologradouro().getBairro().setIdBairro(null);
                    }
                }

                if (imovelCobranca.getBairrologradouro().getLogradouro().getLogradouroPK().getIdLogradouro() != null) {
                    if (imovelCobranca.getBairrologradouro().getLogradouro().getLogradouroPK().getIdLogradouro() == 0) {
                        imovelCobranca.getBairrologradouro().getLogradouro().getLogradouroPK().setIdLogradouro(null);
                    }
                }

                cidadeServico.adicionarCidade(imovelCobranca.getBairrologradouro().getCidade());
                imovelCobranca.getBairrologradouro().getBairro().setCidade(imovelCobranca.getBairrologradouro().getCidade());
                bairroServico.salvarBairro(imovelCobranca.getBairrologradouro().getBairro());
                selectedRegistro.getImovel().getBairrologradouro().getLogradouro().getLogradouroPK().setIdCidade(imovelCobranca.getBairrologradouro().getCidade().getIdCidade());
                imovelCobranca.getBairrologradouro().getLogradouro().getLogradouroPK().setIdCidade(imovelCobranca.getBairrologradouro().getCidade().getIdCidade());
                logradouroServico.adicionarLogradouro(imovelCobranca.getBairrologradouro().getLogradouro());

                bairroLogradouroServico.salvarBairroLogradouro(imovelCobranca.getBairrologradouro());
                imovelServico.salvarImovel(imovelCobranca);
            }
            selectedRegistro.setIdImovelCobranca(imovelCobranca.getId());

            return true;

        } catch (BusinessViolation bv) {
            Funcao.avisoErro(bv.getMessage());
            return false;
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        } finally {
            transacao = false;
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        anexos = new IssqnAnexo();
        anexos.setNome(event.getFile().getFileName());
        anexos.setArquivo(event.getFile().getContents());
        anexos.setTipo(event.getFile().getContentType());
        anexos.setNomeRealArquivo(event.getFile().getFileName().toUpperCase());
        listAnexos.add(anexos);
    }

    public void salvarAnexos() {

        try {
            servico.registrarContribuinte(selectedRegistro, listAnexos);
        } catch (Exception ex) {
            Util.messageErro(ex.getMessage());
            return;
        }

    }

    public void changeEventual() {

        try {
            if (selectedRegistro.getEventual()) {
                tipoCobranca = 3;
            } else {
                tipoCobranca = 1;
            }
        } catch (Exception ex) {
            Util.messageErro(ex.getMessage());
            return;
        }

    }

    public boolean existeCalculo() {
//        return calculoServico.existeCalculoTipoCobranca(global.getExercicio().getAno(), selectedRegistro.getId(), tipoCobranca);
        return calculoServico.existePagamento(selectedRegistro);
    }

    public void downloadAnexo(IssqnAnexo anexos) {
        this.anexos = anexos;
        if (anexos == null) {
            Util.messageAlert("Arquivo não passado como parametro!");
            return;
        }
        if (anexos.getArquivo() == null) {
            Util.messageErro("Arquivo não encontrado!");
            return;
        }

        byte[] arquivo = anexos.getArquivo();
        String nomeArquivo = anexos.getNome();

        HttpServletResponse response;
        try {
            response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/download");
            response.addHeader("Content-Disposition", "attachment; filename=" + nomeArquivo);
            response.setContentLength(arquivo.length);
            response.getOutputStream().write(arquivo);
            response.getOutputStream().flush();
            response.getOutputStream().close();

            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            Util.messageErro("Não foi possível disponibilizar o arquivo para download !");
            System.out.println(e.getMessage());
        }
    }

    public void imprimirFichaCadastral() {
        try {
            if (selectedRegistro.getId() == null) {
                selectedRegistro.getPessoa().setId(0);
                relatorioCalculo = true;
                salvar();
            } else {
                relatorioCalculo = true;
                salvar();
            }
            servico.imprimirFichaCadastral(global.getUsuarioLogado().getOrgao().getIdOrgao(), selectedRegistro.getInscricao(), global.getExercicio().getAno(),
                    global.getUsuarioLogado().getLogin(), "ficha_cadastral_simplificada", global.getAuthenticationHeader());

            relatorioCalculo = false;
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
            relatorioCalculo = false;
        }
    }

    public void calculo() {
        Boolean proporcional = false;
        try {

            if (selectedRegistro.getId() == null) {
                selectedRegistro.getPessoa().setId(0);

            }
            relatorioCalculo = true;
            salvar();

            if (selectedRegistro.getInativo() != null && selectedRegistro.getInativo().booleanValue()) {
                throw new BusinessViolation("Contribuinte Inativo! Não é possível efetuar cálculo");
            }
            if (selectedRegistro.getEncerrado() != null && selectedRegistro.getEncerrado().booleanValue()) {
                throw new BusinessViolation("Contribuinte Encerrado! Não é possível efetuar cálculo");
            }
            if (selectedRegistro.getBloqueado() != null && selectedRegistro.getBloqueado().booleanValue()) {
                throw new BusinessViolation("Contribuinte Bloqueado! Não é possível efetuar cálculo");
            }
            if (selectedRegistro.getIsento() != null && !selectedRegistro.getIsento().equals("false")) { // lei do municipio, calculo da categoria "EVENTUAL" não será realizado
                throw new BusinessViolation("Contribuinte Isento! Não é possível efetuar cálculo");
            }
            if (selectedRegistro.getMei() != null && selectedRegistro.getMei().booleanValue()) {
                throw new BusinessViolation("Contribuinte Mei! Não é possível efetuar cálculo");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedRegistro.getDtAbertura());
            if (calendar.get(Calendar.YEAR) == global.getExercicio().getAno()) {
                proporcional = true;
            }

            calculoServico.calculoContribuinte(selectedRegistro, global.getUsuarioLogado().getNome(), proporcional, motivo, tipoCobranca);
            buscarDados();
            motivo = new IssqnMotivoCancel();
            relatorioCalculo = false;
            if (lstParcela.isEmpty()) {
                throw new BusinessViolation("Nenhuma taxa foi informada! Não é possível efetuar cálculo");
            }
            Funcao.notificacaoSucesso("Cálculo realizado com sucesso");
        } catch (BusinessViolation bv) {
            relatorioCalculo = false;
            Funcao.mensagemAtencao("Atenção!", bv.getMessage());
        } catch (Exception e) {
            relatorioCalculo = false;
            Funcao.aviso("Não foi possível realizar o cálculo");
        }
    }

    public int retornaTipoPessoa() {
        if (selectedRegistro.getPessoa().getTpPessoa() == null) {
            return 0;
        }
        return selectedRegistro.getPessoa().getTpPessoa().ordinal();
    }

    public void validaCpfCnpj() {

        if (selectedRegistro.getPessoa().getCpfCnpj() == null || selectedRegistro.getPessoa().getCpfCnpj().equals("___.___.___-__")) {
            return;
        }

        if (selectedRegistro.getPessoa().getTpPessoa() == TipoPessoa.FISICA) {
            if (!util.validaCpf(selectedRegistro.getPessoa().getCpfCnpj())) {
                Funcao.avisoErro("CPF Inválido");
                selectedRegistro.getPessoa().setCpfCnpj(null);
                return;
            }
        } else if (!util.validaCNPJ(selectedRegistro.getPessoa().getCpfCnpj())) {
            Funcao.avisoErro("CNPJ Inválido");
            selectedRegistro.getPessoa().setCpfCnpj(null);
            return;
        }

        if (selectedRegistro.getId() == null) {
            String cpf = selectedRegistro.getPessoa().getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim();
            Issqn existente = new Issqn();
            existente = servico.obterIssqnPorCPF(cpf);
            if (existente != null) {
                Funcao.executarJavaScript("swal({title: 'Atenção!', text: 'Já possui um contribuinte cadastrado com este CPF/CNPJ', type: 'info'});");
            } else if (selectedRegistro.getId() != null) {
                selectedRegistro.setPessoa(new Pessoa());
            }
        }
    }

    public void validaCpfCnpjSocio() {
        if (socio.getPessoa().getCpfCnpj() != null || !socio.getPessoa().getCpfCnpj().isEmpty()) {
            String socioAtualizado = socio.getPessoa().getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim();
            if (socioAtualizado.length() < 11) {
                return;
            } else if (socioAtualizado.length() == 11) {
                if (!util.validaCpf(socio.getPessoa().getCpfCnpj())) {
                    Funcao.avisoErro("CPF Inválido");
                    socio.getPessoa().setCpfCnpj(null);
                    return;
                }
                tipoSocio = 0;
            } else if (socioAtualizado.length() > 11) {
                if (!util.validaCNPJ(socio.getPessoa().getCpfCnpj())) {
                    Funcao.avisoErro("CNPJ Inválido");
                    socio.getPessoa().setCpfCnpj(null);
                    return;
                }
                tipoSocio = 1;
            }

        }
        String socioAtualizado = socio.getPessoa().getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim();
        List<Pessoa> listPessoa = new ArrayList<>();
        listPessoa = pessoaServico.getPessoasPorCpfCnpj(socioAtualizado);
        if (!listPessoa.isEmpty()) {
            socio.setPessoa(listPessoa.get(0));
        }
    }

    public void imprimirHistorico() {
        try {
            historicoServico.imprimirHistoricoContribuinte(global.getUsuarioLogado().getOrgao().getIdOrgao(),
                    selectedRegistro, global.getUsuarioLogado().getEmail());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void copiarEndereco() {
        if (enderecoExistente.length > 0) {
            if (selectedRegistro.getImovel().getId() == null) {
                incluirEndereco();
            }
            imovelCobranca = selectedRegistro.getImovel();
            selectedRegistro.setIdImovelCobranca(imovelCobranca.getId());
        } else {
            imovelCobranca = new Imovel();
            imovelCobranca.setBairrologradouro(new BairroLogradouro());
            imovelCobranca.getBairrologradouro().setBairro(new Bairro());
            imovelCobranca.getBairrologradouro().setCidade(new Cidade());
            imovelCobranca.getBairrologradouro().setLogradouro(new Logradouro());
            imovelCobranca.getBairrologradouro().getLogradouro().setLogradouroPK(new LogradouroPK());
            imovelCobranca.getBairrologradouro().getCidade().setEstado(new Estado());
        }
    }

    public void dadosJucesp() {

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ws_sil", "qaws123!".toCharArray());
            }
        });
        SSLContext ctx;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new ContribuinteBean.DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            ws = new Operacionais();
            ConsultarEmpresaPorCNPJRequest parametros = new ConsultarEmpresaPorCNPJRequest();
            parametros.setCnpj(selectedRegistro.getPessoa().getCpfCnpj());
            ConsultarEmpresaPorCNPJResponse response = ws.getBasicHttpBindingIOperacionais().consultarEmpresaPorCNPJ(parametros, global.getAuthenticationHeader());

            ConsultarProtocoloPorCNPJRequest parametros2 = new ConsultarProtocoloPorCNPJRequest();
            ArrayOfString a = new ArrayOfString();
            a.getString().add(selectedRegistro.getPessoa().getCpfCnpj());
            parametros2.setCNPJs(a);
            ConsultarProtocoloPorCNPJResponse response1 = ws.getBasicHttpBindingIOperacionais().consultarProtocoloPorCNPJ(parametros2, global.getAuthenticationHeader());

            if (response != null) {
                for (Solicitacao s : response1.getSolicitacoes().getSolicitacao()) {
                    solicitacaoExiste = true;
                    if (s.getStatusSolicitacao().getStatus().toUpperCase().contains("CONCLUÍD")) {
                        for (Licenca l : response.getEmpresa().getLicencas().getLicenca()) {
                            if (!l.getNumero().isEmpty() && l.getSituacaoLicenca().getDescricao().toUpperCase().contains("CONCLUÍD")) {
                                if (l.getOrgao().getNome().toUpperCase().contains("PREFEITURA")) {
                                    selectedRegistro.setNumAlvara(Util.extractStr(l.getNumero().trim()));
                                    selectedRegistro.setDtAlvara(l.getDataEmissao().getValue().toGregorianCalendar().getTime());
                                    selectedRegistro.setDtValidadeAlvara(l.getDataValidade().getValue().toGregorianCalendar().getTime());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public TipoPessoa[] getListTipoPessoa() {
        if (listTipoPessoa == null || listTipoPessoa.length == 0) {
            listTipoPessoa = TipoPessoa.values();
        }
        return listTipoPessoa;
    }

    public void setListTipoPessoa(TipoPessoa[] listTipoPessoa) {
        this.listTipoPessoa = listTipoPessoa;
    }

    public IssqnAnexo getAnexos() {
        return anexos;
    }

    public void setAnexos(IssqnAnexo anexos) {
        this.anexos = anexos;
    }

    public List<ParcelaDTO> getLstParcela() {
        return lstParcela;
    }

    public void setLstParcela(List<ParcelaDTO> lstParcela) {
        this.lstParcela = lstParcela;
    }

    public Integer getTipoSocio() {
        return tipoSocio;
    }

    public void setTipoSocio(Integer tipoSocio) {
        this.tipoSocio = tipoSocio;
    }

    public Integer getParcelaInicial() {
        return parcelaInicial;
    }

    public void setParcelaInicial(Integer parcelaInicial) {
        this.parcelaInicial = parcelaInicial;
    }

    public Integer getParcelaFinal() {
        return parcelaFinal;
    }

    public void setParcelaFinal(Integer parcelaFinal) {
        this.parcelaFinal = parcelaFinal;
    }

    public List<IssqnTipoCobranca> getListTipoCobranca() {
        return listTipoCobranca;
    }

    public void setListTipoCobranca(List<IssqnTipoCobranca> listTipoCobranca) {
        this.listTipoCobranca = listTipoCobranca;
    }

    public Integer getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(Integer tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public IssqnMotivoCancel getMotivo() {
        return motivo;
    }

    public void setMotivo(IssqnMotivoCancel motivo) {
        if (motivo == null) {
            motivo = new IssqnMotivoCancel();
        }
        this.motivo = motivo;
    }

    public List<CancelamentoDTO> getLstCancelamento() {
        return lstCancelamento;
    }

    public void setLstCancelamento(List<CancelamentoDTO> lstCancelamento) {
        this.lstCancelamento = lstCancelamento;
    }

    public List<Object[]> getListTaxa() {
        return listTaxa;
    }

    public void setListTaxa(List<Object[]> listTaxa) {
        this.listTaxa = listTaxa;
    }

    public ParcelaDTO getSelectedParcelaDTO() {
        return selectedParcelaDTO;
    }

    public void setSelectedParcelaDTO(ParcelaDTO selectedParcelaDTO) {
        this.selectedParcelaDTO = selectedParcelaDTO;
    }

    public Integer getTipoCobrancaBoleto() {
        return tipoCobrancaBoleto;
    }

    public void setTipoCobrancaBoleto(Integer tipoCobrancaBoleto) {
        this.tipoCobrancaBoleto = tipoCobrancaBoleto;
    }

    public Imovel getImovelCobranca() {
        return imovelCobranca;
    }

    public void setImovelCobranca(Imovel imovelCobranca) {
        this.imovelCobranca = imovelCobranca;
    }

    public boolean isMesmoEndereco() {
        return mesmoEndereco;
    }

    public void setMesmoEndereco(boolean mesmoEndereco) {
        this.mesmoEndereco = mesmoEndereco;
    }

    public Integer[] getEnderecoExistente() {
        return enderecoExistente;
    }

    public void setEnderecoExistente(Integer[] enderecoExistente) {
        this.enderecoExistente = enderecoExistente;
    }

    public CancelamentoDTO getSelectedParcelaCanceladaDTO() {
        return selectedParcelaCanceladaDTO;
    }

    public void setSelectedParcelaCanceladaDTO(CancelamentoDTO selectedParcelaCanceladaDTO) {
        this.selectedParcelaCanceladaDTO = selectedParcelaCanceladaDTO;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
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
}
