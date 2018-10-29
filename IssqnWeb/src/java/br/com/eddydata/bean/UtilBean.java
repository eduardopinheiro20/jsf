/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David
 **/
package br.com.eddydata.bean;

import br.com.eddydata.entidade.geo.Abreviatura;
import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.entidade.geo.Logradouro;
import br.com.eddydata.entidade.geo.LogradouroPK;
import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.entidade.issqn.IssqnConselhoRegional;
import br.com.eddydata.entidade.issqn.IssqnEscritorio;
import br.com.eddydata.entidade.issqn.IssqnSocio;
import br.com.eddydata.entidade.issqn.IssqnTaxa;
import br.com.eddydata.servico.admin.AbreviaturaServico;
import br.com.eddydata.servico.admin.BairroServico;
import br.com.eddydata.servico.admin.BancoServico;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.EstadoServico;
import br.com.eddydata.servico.admin.LogradouroServico;
import br.com.eddydata.servico.admin.PessoaServico;
import br.com.eddydata.servico.issqn.IssqnAtividadeServico;
import br.com.eddydata.servico.issqn.IssqnConselhoRegionalServico;
import br.com.eddydata.servico.issqn.IssqnEscritorioServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.IssqnSocioServico;
import br.com.eddydata.servico.issqn.IssqnTaxaServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.XMLGregorianCalendar;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class UtilBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private BairroServico bairroServico;
    @EJB
    private LogradouroServico logradouroServico;
    @EJB
    private CidadeServico cidadeServico;
    @EJB
    private EstadoServico estadoServico;
    @EJB
    private IssqnTaxaServico taxaServico;
    @EJB
    private BancoServico bancoServico;
    @EJB
    private PessoaServico pessoaServico;
    @EJB
    private IssqnSocioServico socioServico;
    @EJB
    private IssqnEscritorioServico escritorioServico;
    @EJB
    private IssqnConselhoRegionalServico conselhoServico;
    @EJB
    private IssqnAtividadeServico atividadeServico;
    @EJB
    private IssqnServico issqnServico;
    @EJB
    private AbreviaturaServico abreviaturaServico;

    @Inject
    private GlobalBean global;

    private String textoFiltro;

    private void mostrarMsgErro(String msg) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", msg);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public List<Bairro> buscarBairro(String nome) {
        List<Bairro> bairroList = new ArrayList();
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Integer idCidade = (Integer) UIComponent.getCurrentComponent(context).getAttributes().get("cidade");
            if (idCidade != null && idCidade != 0) {
                bairroList = bairroServico.obterBairrosPorNome(idCidade, retiraAcento(nome.trim().toUpperCase()) + "%");
            }
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar bairros");
            System.out.println("Não foi possivel preencher a lista de bairros " + e.getMessage());
        }
        return bairroList;
    }

    public List<Logradouro> buscarLogradouro(String nome) {
        List<Logradouro> logradouroList = new ArrayList();
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Integer idCidade = (Integer) UIComponent.getCurrentComponent(context).getAttributes().get("cidade");
            if (idCidade != null && idCidade != 0) {
                logradouroList = logradouroServico.obterLogradourosPorNome(idCidade, retiraAcento(nome.trim().toUpperCase()) + "%");
            }
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar logradouros");
            System.out.println("Não foi possivel preencher a lista de logradouros " + e.getMessage());
        }
        return logradouroList;
    }

    public List<Cidade> buscarCidade(String nome) {
        List<Cidade> cidadeList = new ArrayList();
        try {
            cidadeList = cidadeServico.getCidadePorNome(retiraAcento(nome.trim().toUpperCase()) + "%");
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar cidades");
            System.out.println("Não foi possivel preencher a lista de cidades " + e.getMessage());
        }
        return cidadeList;
    }

    public List<IssqnTaxa> buscarTaxa(String nome) {
        List<IssqnTaxa> taxaLista = new ArrayList();
        try {
            taxaLista = taxaServico.obterTaxas(retiraAcento(nome.trim().toUpperCase()), 100);
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar taxas");
            System.out.println("Não foi possivel preencher a lista de taxas " + e.getMessage());
        }
        return taxaLista;
    }

    public List<IssqnCnae> buscarAtividade(String nome) {
        List<IssqnCnae> atividadeLista = new ArrayList();
        try {
            atividadeLista = atividadeServico.obterAtividades(retiraAcento(nome.trim().toUpperCase()), null, 10000);
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar atividades");
            System.out.println("Não foi possivel preencher a lista de atividades " + e.getMessage());
        }
        return atividadeLista;
    }

    public List<Pessoa> buscarPessoa(String nome) {
        List<Pessoa> pessoaList = new ArrayList();
        try {
            pessoaList = pessoaServico.getPessoasPorNomeLimite(retiraAcento(nome.trim().toUpperCase()), 20);
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar pessoas");
            System.out.println("Não foi possivel preencher a lista de pessoas" + e.getMessage());
        }
        Pessoa nova = new Pessoa();
        nova.setId(0);
        nova.setNome(retiraAcento(nome.trim().toUpperCase()));
        pessoaList.add(nova);
        return pessoaList;
    }

    public List<IssqnSocio> buscarSocio(String nome) {
        List<IssqnSocio> socioList = new ArrayList();
        try {
            socioList = socioServico.getSocioPorNome(retiraAcento(nome.trim().toUpperCase()));
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar socio");
            System.out.println("Não foi possivel preencher a lista de socio" + e.getMessage());
        }
        IssqnSocio nova = new IssqnSocio();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(retiraAcento(nome.trim().toUpperCase()));

        nova.setId(0);
        nova.setPessoa(pessoa);

        socioList.add(nova);
        return socioList;
    }

    public List<Issqn> buscarIssqnNomePessoa(String nome) {
        List<Issqn> issqnList = new ArrayList();
        if (nome.trim().isEmpty()) {
            return issqnList;
        }
        try {
            issqnList = issqnServico.obterIssqnPorNomePessoa(retiraAcento(nome.trim().toUpperCase()), global.getExercicio().getAno(), 20);
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar contribuinte\n");
            System.out.println("Não foi possivel preencher a lista de issqn por pessoa" + e.getMessage());
        }
        return issqnList;
    }

    public Issqn buscarIssqnInscricao(String inscricao) {
        try {
            return issqnServico.obterIssqnPorInscricao(inscricao, global.getExercicio().getAno());
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar contribuinte");
            System.out.println("Não foi possivel buscar o contribuinte por inscrição" + e.getMessage());
        }
        return null;
    }

    public List<IssqnEscritorio> buscarEscritorio(String nome) {
        List<IssqnEscritorio> escritorioList = new ArrayList();
        try {
            escritorioList = escritorioServico.obterEscritorios(retiraAcento(nome.trim().toUpperCase()), 100);
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar escritorios");
            System.out.println("Não foi possivel preencher a lista de escritorios" + e.getMessage());
        }
        return escritorioList;
    }

    public List<IssqnConselhoRegional> buscarConselho(String nome) {
        List<IssqnConselhoRegional> conselhoList = new ArrayList();
        try {
            conselhoList = conselhoServico.obterConselhosRegionais(retiraAcento(nome.trim().toUpperCase()), 100);
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar conselhos regionais");
            System.out.println("Não foi possivel preencher a lista de conselhos regionais" + e.getMessage());
        }
        return conselhoList;
    }

    public List<Banco> listBancos() {
        List<Banco> bancoList = new ArrayList();
        try {
            bancoList = bancoServico.obterBancos("", null);
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar bancos");
            System.out.println("Não foi possivel preencher a lista de bancos " + e.getMessage());
        }
        return bancoList;
    }

    public Date convertXmlGregorianCalendarToDate(XMLGregorianCalendar xmlData) {
        if (xmlData == null) {
            return null;
        }
        return xmlData.toGregorianCalendar().getTime();
    }

    public List<Estado> buscarIssqnEstado() {
        List<Estado> estadoList = new ArrayList();
        try {
            estadoList = estadoServico.getEstados();
            if (estadoList.isEmpty()) {
                Map<String, String> estados = new HashMap<String, String>();

                estados.put("AC", new String("ACRE"));
                estados.put("AL", new String("ALAGOAS"));
                estados.put("AM", new String("AMAZONAS"));
                estados.put("AP", new String("AMAPA"));
                estados.put("BA", new String("BAHIA"));
                estados.put("CE", new String("CEARA"));
                estados.put("DF", new String("DISTRITO FEDERAL"));
                estados.put("ES", new String("ESPIRITO SANTO"));
                estados.put("GO", new String("GOIÁS"));
                estados.put("MA", new String("MARANHAO"));
                estados.put("MT", new String("MATO GROSSO"));
                estados.put("MS", new String("MATO GROSSO DO SUL"));
                estados.put("MG", new String("MINAS GERAIS"));
                estados.put("PA", new String("PARA"));
                estados.put("PB", new String("PARAIBA"));
                estados.put("PR", new String("PARANA"));
                estados.put("PE", new String("PERNAMBUCO"));
                estados.put("PI", new String("PIAUA"));
                estados.put("RJ", new String("RIO DE JANEIRO"));
                estados.put("RN", new String("RIO GRANDE DO NORTE"));
                estados.put("RO", new String("RONDONIA"));
                estados.put("RS", new String("RIO GRANDE DO SUL"));
                estados.put("RR", new String("RORAIMA"));
                estados.put("SC", new String("SANTA CATARINA"));
                estados.put("SE", new String("SERGIPE"));
                estados.put("SP", new String("SAO PAULO"));
                estados.put("TO", new String("TOCANTINS"));

                SortedSet<String> keys = new TreeSet<>(estados.keySet());
                int estado = 1;
                for (String key : keys) {
                    Estado e = new Estado();
                    e.setId(estado);
                    e.setNome(estados.get(key));
                    e.setUf(key);
                    estadoServico.adicionarEstado(e);
                    estadoList.add(e);
                    estado++;
                }
            }
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar bairro");
            System.out.println("Não foi possivel preencher a lista de bairro " + e.getMessage());
        }

        return estadoList;
    }

    public List<String> buscarCidadeSocio(String nome) {
        List<Cidade> cidade = buscarIssqnCidade(nome);
        List<String> cidadeSocio = new ArrayList<>();
        for (Cidade c : cidade) {
            cidadeSocio.add(c.getNome());
        }

        return cidadeSocio;
    }

    public List<Cidade> buscarIssqnCidade(String nome) {
        Estado estadoRegistro = new Estado();
        if (JSFUtils.pegarObjetoDaSessao("estado") != null) {
            estadoRegistro = (Estado) JSFUtils.pegarObjetoDaSessao("estado");
            JSFUtils.flashScope().clear();
        }
        nome = retiraAcento(nome.trim().toUpperCase());
        List<Cidade> cidadeList = new ArrayList();
        try {
            cidadeList = cidadeServico.getCidadePorNome(nome + "%");
            if (cidadeList.isEmpty()) {
                Cidade c = new Cidade();
                c.setCodCidade(cidadeServico.getCidades().size() + 1);
                c.setUf(estadoRegistro.getUf());
                c.setNome(nome);
                c.setSiafi("");
                c.setUrl("");
                c.setEstado(estadoRegistro);
                c.setIdCidade(0);
                cidadeList.add(c);
            }
        } catch (Exception e) {
            Funcao.avisoErro("Não foi possível buscar cidade");
            System.out.println("Não foi possivel preencher a lista de cidade " + e.getMessage());
        }
        return cidadeList;
    }

    public List<String> buscarBairroSocio(String nome) {
        List<Bairro> bairro = buscarIssqnBairro(nome);
        List<String> bairroSocio = new ArrayList<>();
        for (Bairro c : bairro) {
            bairroSocio.add(c.getNome());
        }

        return bairroSocio;
    }

    public List<Bairro> buscarIssqnBairro(String nome) {
        nome = retiraAcento(nome.trim().toUpperCase());
        List<Bairro> bairroList = new ArrayList();
        Cidade cidadeRegistro = new Cidade();
        if (JSFUtils.pegarObjetoDaSessao("cidade") != null) {
            cidadeRegistro = (Cidade) JSFUtils.pegarObjetoDaSessao("cidade");
            JSFUtils.flashScope().clear();
        }
        try {
            bairroList = bairroServico.obterBairros(nome + "%", 10);
            if (bairroList.isEmpty()) {
                List<Abreviatura> listAbreviatura = new ArrayList();
                listAbreviatura = abreviaturaServico.obterAbreviaturas(nome.substring(0, 2), 1);
                if (listAbreviatura.isEmpty()) {
                    Abreviatura abreviatura = new Abreviatura();
                    abreviatura.setId(23);
                    abreviatura.setNome("");
                    abreviatura.setNomeExtenso("Bairro");
                    abreviatura.setTpAbrev("0");
                    listAbreviatura.add(abreviatura);
                }
                Bairro b = new Bairro();
                b.setAbreviatura(listAbreviatura.get(0));
                b.setNome(nome);
                b.setCidade(cidadeRegistro);
                b.setIdBairro(0);
                bairroList.add(b);
            }
        } catch (Exception e) {
            Funcao.avisoErro("Não foi possível buscar bairros");
            System.out.println("Não foi possivel preencher a lista de bairros " + e.getMessage());
        }
        return bairroList;
    }

    public List<Logradouro> buscarIssqnLogradouro(String nome) {
        nome = retiraAcento(nome.trim().toUpperCase());
        List<Logradouro> logradouroList = new ArrayList();
        Cidade cidadeRegistro = new Cidade();
        if (JSFUtils.pegarObjetoDaSessao("cidade") != null) {
            cidadeRegistro = (Cidade) JSFUtils.pegarObjetoDaSessao("cidade");
            JSFUtils.flashScope().clear();
        }
        try {
            logradouroList = logradouroServico.obterLogradourosPorNome(cidadeRegistro.getIdCidade(), retiraAcento(nome.trim().toUpperCase()) + "%");
            if (logradouroList.isEmpty()) {
                List<Abreviatura> listAbreviatura = new ArrayList();
                listAbreviatura = abreviaturaServico.obterAbreviaturas(nome.substring(0, 2), 1);
                if (listAbreviatura.isEmpty()) {
                    Abreviatura abreviatura = new Abreviatura();
                    abreviatura.setId(23);
                    abreviatura.setNome("RUA");
                    abreviatura.setNomeExtenso("Rua");
                    abreviatura.setTpAbrev("0");
                    listAbreviatura.add(abreviatura);
                }
                Logradouro logradouro = new Logradouro();
                logradouro.setAbreviatura(listAbreviatura.get(0));
                logradouro.setNome(nome);
                logradouro.setLogradouroPK(new LogradouroPK(0, null));
                logradouroList.add(logradouro);
            }
        } catch (Exception e) {
            mostrarMsgErro("Não foi possível buscar logradouro");
            System.out.println("Não foi possivel preencher a lista de logradouro " + e.getMessage());
        }
        return logradouroList;
    }

    public List<String> buscarLogradouroSocio(String nome) {
        List<Logradouro> logradouro = buscarIssqnLogradouro(nome);
        List<String> logradouroSocio = new ArrayList<>();
        for (Logradouro c : logradouro) {
            logradouroSocio.add(c.getNome());
        }

        return logradouroSocio;
    }

    public void subirCaixa(SelectEvent event) {
        if (event.getObject() instanceof Pessoa) {
            Pessoa p = (Pessoa) event.getObject();
            if (p != null) {
                String nome = p.getNome();
                nome = (nome == null ? "" : retiraAcento(nome.trim().toUpperCase()));
                p.setNome(nome);
            }
        }
    }

    public String retiraAcento(String str) {
        if (str != null) {
            str = Normalizer.normalize(str, Normalizer.Form.NFD);
            str = str.replaceAll("[^\\p{ASCII}]", "");
            return str;
        } else {
            return null;
        }
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            try {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                String ip = request.getHeader("x-forwarded-for");
                if (ip == null) {
                    ip = request.getRemoteAddr();
                }
//                textoFiltro = textoFiltro.replace(".", "").replace("-", "").replace("/", "").trim();
//                textoFiltro = Util.mask("######-######-#######-###",textoFiltro);
                issqnServico.imprimirDocumentoAutenticado(textoFiltro.trim());
            } catch (BusinessViolation ex) {
                Funcao.avisoErro    (ex.getMessage());
            } catch (Exception ex) {
                mostrarMsgErro("Erro ao realizar a consulta!");
            }
        }
        textoFiltro = "";
    }
    
    public boolean validaCpf(String cpf) {
        cpf = cpf.replace(".", "").replace("-", "").replace("/", "").trim();
        return Util.validateCPF(cpf);
    }

    public boolean validaCNPJ(String cnpj) {
        cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "").trim();
        return Util.validateCNPJ(cnpj);
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }
    
}
