/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.JSFUtils;
import br.com.eddydata.bean.UtilBean;
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
import br.com.eddydata.entidade.issqn.IssqnRamoAtuacao;
import br.com.eddydata.entidade.referencia.Cor;
import br.com.eddydata.entidade.referencia.EstadoCivil;
import br.com.eddydata.entidade.referencia.GrauInstrucao;
import br.com.eddydata.entidade.referencia.OrgaoExpedidor;
import br.com.eddydata.entidade.referencia.Sexo;
import br.com.eddydata.entidade.referencia.Situacao;
import br.com.eddydata.entidade.referencia.TipoPessoa;
import br.com.eddydata.entidade.referencia.TipoSanguineo;
import br.com.eddydata.entidade.referencia.UfRg;
import br.com.eddydata.servico.admin.BairroServico;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.LogradouroServico;
import br.com.eddydata.servico.admin.PessoaServico;
import br.com.eddydata.servico.issqn.BairroLogradouroServico;
import br.com.eddydata.servico.issqn.ImovelServico;
import br.com.eddydata.servico.issqn.IssqnCategoriaServico;
import br.com.eddydata.servico.issqn.IssqnHistoricoRegistroServico;
import br.com.eddydata.servico.issqn.IssqnRamoAtuacaoServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.StringMD5;
import br.com.eddydata.suporte.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.tempuri.Empresa;
import org.tempuri.Solicitacao;

@Named
@ViewScoped
public class IssqnBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;

    @EJB
    private IssqnHistoricoRegistroServico historicoServico;

    @EJB
    private PessoaServico pessoaServico;
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
    private IssqnCategoriaServico categoriaServico;
    @EJB
    private IssqnRamoAtuacaoServico ramoAtuacaoServico;

    private Issqn selectedRegistro;
    private TipoPessoa[] listTipoPessoa;
    private OrgaoExpedidor[] listOrgaoExpedidor;
    private GrauInstrucao[] listGrauInstrucao;
    private TipoSanguineo[] listTipoSanguineo;
    private Sexo[] listSexo;
    private EstadoCivil[] listEstadoCivil;
    private Cor[] listCor;
    private UfRg[] listUfRg;
    private Situacao[] listSituacao;
    private boolean alteracao = false;
    private List<IssqnAnexo> listAnexos = new ArrayList<>();
    private IssqnAnexo selectedAnexo = new IssqnAnexo();
    private Solicitacao solicitacao = null;
    private boolean jucesp = true;
    private List<IssqnCategoria> listCategorias;
    private List<IssqnRamoAtuacao> listRamoAtuacao;

    @Inject
    private GlobalBean global;
    @Inject
    private UtilBean util;

    @PostConstruct
    public void init() {
        if (global.getIssqnLogado() != null) {
            selectedRegistro = global.getIssqnLogado();
        } else {
            if (JSFUtils.flashScope().get("solicitacao") != null) {
                solicitacao = (Solicitacao) JSFUtils.flashScope().get("solicitacao");
            } else {
                String cnpj_pessoa = (String) JSFUtils.flashScope().get("cnpjPessoa");                
                solicitacao = new Solicitacao();
                Empresa emp = new Empresa();
                emp.setCpnj(cnpj_pessoa);
                solicitacao.setEmpresa(emp);
                jucesp = false;
                Funcao.avisoAtencao("Contribuinte não encontrado nos dados da jucesp!!");
            }
            adicionar();
        }
    }

    public void adicionar() {
        selectedRegistro = new Issqn();
        Pessoa p = new Pessoa();
        if (solicitacao != null) {
            Empresa e = solicitacao.getEmpresa();

            p.setEmail(Funcao.abreviarString(solicitacao.getEmpresa().getEmail(), 70));
            p.setNome(Funcao.abreviarString(solicitacao.getNomeSolicitante(), 300));
            p.setNomeFantasia(Funcao.abreviarString(e.getNomeEmpresa(), 300));
            p.setCpfCnpj(Funcao.abreviarString(e.getCpnj(), 20));
            p.setTelefone(Funcao.abreviarString(e.getTelefone1(), 20));
            p.setTel1(Funcao.abreviarString(e.getTelefone1(), 50));
            p.setTel2(Funcao.abreviarString(e.getTelefone2(), 50));
            if (solicitacao.getEmpresa().getNaturezaJuridica() == null) {
                if (solicitacao.getEmpresa().getCpnj().length() > 11) {
                    p.setTpPessoa(TipoPessoa.JURIDICA);
                } else {
                    p.setTpPessoa(TipoPessoa.FISICA);
                }
            }else{
                if (solicitacao.getEmpresa().getNaturezaJuridica().getTipoNaturezaJuridica().getNomeNatJuridica().contains("EMPRESARIAIS")) {
                    p.setTpPessoa(TipoPessoa.JURIDICA);
                } else {
                    p.setTpPessoa(TipoPessoa.FISICA);
                }
            }

            selectedRegistro.setCnpjCpf(Funcao.abreviarString(e.getCpnj(), 20));
        }
        selectedRegistro.setPessoa(p);
        selectedRegistro.setPendente(true);
        selectedRegistro.setImovel(new Imovel());
        selectedRegistro.getImovel().setBairrologradouro(new BairroLogradouro());
        selectedRegistro.getImovel().getBairrologradouro().setBairro(new Bairro());
        selectedRegistro.getImovel().getBairrologradouro().setCidade(new Cidade());
        selectedRegistro.getImovel().getBairrologradouro().setLogradouro(new Logradouro());
        selectedRegistro.getImovel().getBairrologradouro().getLogradouro().setLogradouroPK(new LogradouroPK());
        selectedRegistro.getImovel().getBairrologradouro().getCidade().setEstado(new Estado());
    }

    public void salvar() {
        try {
            if (!jucesp) {
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
                        selectedRegistro.setSenha(StringMD5.md5(selectedRegistro.getSenha()));
                    } else {
                        selectedRegistro.setSenha(StringMD5.md5(Util.extractStr(123)));
                    }
                } else if (selectedRegistro.getSenha() != null || !selectedRegistro.getSenha().isEmpty()) {
                    selectedRegistro.setSenha(StringMD5.md5(selectedRegistro.getSenha()));
                }

                if (selectedRegistro.getDtInicio() == null) {
                    selectedRegistro.setDtInicio(Util.dateToday());
                }

                selectedRegistro.getImovel().setNrImovel(selectedRegistro.getImovel().getNrImovel().replace(".", "").replace(",", "").replace("-", "").trim());
                if (!Util.isNumeric(selectedRegistro.getImovel().getNrImovel())) {
                    selectedRegistro.getImovel().setNrImovel(null);
                }

                incluirEndereco();
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

                //String inscricao = "";
                String documento = "";
                selectedRegistro.setIdExercicio(Util.getYear(new Date()));
                selectedRegistro = servico.salvarIssqn(selectedRegistro);
                if (selectedRegistro.getInscricao() == null) {
                    if (selectedRegistro.getPessoa().getTpPessoa() == TipoPessoa.FISICA) {
                        documento = Util.unMask("###.###.###-##", selectedRegistro.getPessoa().getCpfCnpj());
                    } else {
                        documento = Util.unMask("##.###.###/####-##", selectedRegistro.getPessoa().getCpfCnpj());
                    }
                    /*
                        inscricao = (documento.length() >= 6 ? documento.substring(0, 6) : "") + selectedRegistro.getIdExercicio() + selectedRegistro.getId();
                        selectedRegistro.setInscricao(inscricao);
                        selectedRegistro.getPessoa().setInscMunicipal(inscricao);
                    */                    
                    
                    pessoaServico.adicionarPessoa(selectedRegistro.getPessoa());
                    selectedRegistro = servico.salvarIssqn(selectedRegistro);
                }
            } else {
                servico.registrarContribuinte(selectedRegistro, listAnexos);
            }
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return;
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível salvar o seu registro!");
            System.out.println("Erro ao salvar o registro de contribuinte\n" + ex.getMessage());
            return;
        }

        Funcao.avisoSucesso("Registro efetuado com sucesso!\nAguarde informações do orgão para o acesso as funcionalidades");        
    }

    private void incluirEndereco() {
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
        } catch (BusinessViolation bv) {
            Funcao.avisoErro(bv.getMessage());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
        }
    }

    public void alterar() {
        try {
            if (!selectedRegistro.getSenha().isEmpty()) {
                servico.registrarContribuinte(selectedRegistro, new ArrayList<IssqnAnexo>());
            }
            historicoServico.salvarHistoricoRegistro(selectedRegistro);
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return;
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível alterar o seu registro!");
            System.out.println("Erro ao alterar o registro de contribuinte\n" + ex.getMessage());
            return;
        }

        Funcao.avisoSucesso("Registro alterado com sucesso!\nAguarde a validação das alterações");
    }

    public void downloadAnexo() {
        if (selectedAnexo == null) {
            Funcao.avisoErro("Anexo não passado como parametro!");
            return;
        }
        if (selectedAnexo.getArquivo() == null) {
            Funcao.avisoErro("Arquivo do anexo não encontrado!");
            return;
        }
        if (selectedAnexo.getNomeRealArquivo() == null) {
            Funcao.avisoErro("Nome do arquivo não encontrado!");
            return;
        }

        byte[] arquivo = selectedAnexo.getArquivo();
        String nomeArquivo = selectedAnexo.getNomeRealArquivo();

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
            Funcao.avisoErro("Erro ao disponibilizar o arquivo para download !");
        }
    }

    /**
     * faz o upload do anexo
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        selectedAnexo.setNomeRealArquivo(event.getFile().getFileName());
        selectedAnexo.setArquivo(event.getFile().getContents());
        selectedAnexo.setTipo(event.getFile().getContentType());
    }

    /**
     * Pega os bytes do anexo no banco e transforma em um arquivo real
     */
    public void criarArquivo() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            String path = servletContext.getRealPath("/resources/anexos");
            File dir = new File(path);
            if (!dir.exists()) {
                dir.createNewFile();
            }

            if (selectedAnexo.getArquivo() != null) {
                byte[] bytes = selectedAnexo.getArquivo();
                FileOutputStream fileOutput = new FileOutputStream(new File(dir, selectedAnexo.getNome()));
                fileOutput.write(bytes, 0, bytes.length);
                fileOutput.flush();
                fileOutput.close();

            }
        } catch (Exception ex) {
        }
    }

    public void salvarAnexo() {
        selectedAnexo.setDataCadastro(new Date());
        selectedAnexo.setIss(selectedRegistro);

        listAnexos.add(selectedAnexo);

        selectedAnexo = new IssqnAnexo();
    }

    public void imprimirFichaCadastral() {
        try {
            servico.imprimirFichaCadastral(null, selectedRegistro.getInscricao(), global.getIssqnLogado().getIdExercicio(), null, "ficha_cadastral_simplificada", global.getAuthenticationHeader());

        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void imprimirBoleto() {
        StringBuilder pesquisa = new StringBuilder();

        pesquisa.append(" AND II.INSCRICAO = ").append(Util.quotarStr(selectedRegistro.getInscricao()));

        try {
            servico.gerarCobranca("1", pesquisa.toString(), selectedRegistro.getIdExercicio(), 2, 0, null);
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void changeEstado() {
        JSFUtils.criarObjetoDeSessao(selectedRegistro.getImovel().getBairrologradouro().getCidade().getEstado(), "estado");
    }

    public void changeCidade() {
        JSFUtils.criarObjetoDeSessao(selectedRegistro.getImovel().getBairrologradouro().getCidade(), "cidade");
    }

    // --------------- gets e sets -------------------------------------------------
    public Issqn getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(Issqn selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
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

    public Situacao[] getListSituacao() {
        if (listSituacao == null || listSituacao.length == 0) {
            listSituacao = Situacao.values();
        }
        return listSituacao;
    }

    public void setListSituacao(Situacao[] listSituacao) {
        this.listSituacao = listSituacao;
    }

    public OrgaoExpedidor[] getListOrgaoExpedidor() {
        if (listOrgaoExpedidor == null || listOrgaoExpedidor.length == 0) {
            listOrgaoExpedidor = OrgaoExpedidor.values();
        }
        return listOrgaoExpedidor;
    }

    public void setListOrgaoExpedidor(OrgaoExpedidor[] listOrgaoExpedidor) {
        this.listOrgaoExpedidor = listOrgaoExpedidor;
    }

    public UfRg[] getListUfRg() {
        if (listUfRg == null || listUfRg.length == 0) {
            listUfRg = UfRg.values();
        }
        return listUfRg;
    }

    public void setListUfRg(UfRg[] listUfRg) {
        this.listUfRg = listUfRg;
    }

    public Cor[] getListCor() {
        if (listCor == null || listCor.length == 0) {
            listCor = Cor.values();
        }
        return listCor;
    }

    public void setListCor(Cor[] listCor) {
        this.listCor = listCor;
    }

    public Sexo[] getListSexo() {
        if (listSexo == null || listSexo.length == 0) {
            listSexo = Sexo.values();
        }
        return listSexo;
    }

    public void setListSexo(Sexo[] listSexo) {
        this.listSexo = listSexo;
    }

    public GrauInstrucao[] getListGrauInstrucao() {
        if (listGrauInstrucao == null || listGrauInstrucao.length == 0) {
            listGrauInstrucao = GrauInstrucao.values();
        }
        return listGrauInstrucao;
    }

    public void setListGrauInstrucao(GrauInstrucao[] listGrauInstrucao) {
        this.listGrauInstrucao = listGrauInstrucao;
    }

    public TipoSanguineo[] getListTipoSanguineo() {
        if (listTipoSanguineo == null || listTipoSanguineo.length == 0) {
            listTipoSanguineo = TipoSanguineo.values();
        }
        return listTipoSanguineo;
    }

    public void setListTipoSanguineo(TipoSanguineo[] listTipoSanguineo) {
        this.listTipoSanguineo = listTipoSanguineo;
    }

    public EstadoCivil[] getListEstadoCivil() {
        if (listEstadoCivil == null || listEstadoCivil.length == 0) {
            listEstadoCivil = EstadoCivil.values();
        }
        return listEstadoCivil;
    }

    public void setListEstadoCivil(EstadoCivil[] listEstadoCivil) {
        this.listEstadoCivil = listEstadoCivil;
    }

    public boolean getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(boolean alteracao) {
        this.alteracao = alteracao;
    }

    public List<IssqnAnexo> getListAnexos() {
        return listAnexos;
    }

    public void setListAnexos(List<IssqnAnexo> listAnexos) {
        this.listAnexos = listAnexos;
    }

    public IssqnAnexo getSelectedAnexo() {
        return selectedAnexo;
    }

    public void setSelectedAnexo(IssqnAnexo selectedAnexo) {
        this.selectedAnexo = selectedAnexo;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public boolean getJucesp() {
        return jucesp;
    }

    public void setJucesp(boolean jucesp) {
        this.jucesp = jucesp;
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

}
