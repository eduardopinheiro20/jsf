/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.bean.relatorio.RelatorioCartaCobrancaBean.SubRelatorio;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnAnexo;
import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.entidade.issqn.IssqnCnaeIss;
import br.com.eddydata.entidade.issqn.IssqnContribuinteTaxa;
import br.com.eddydata.entidade.issqn.IssqnTaxa;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.repositorio.issqn.IssqnRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.tempuri.AuthenticationHeader;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnRepositorio(em);
    }

    /**
     * metodo de validacao do contribuinte
     *
     * @param issqn
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarIssqn(Issqn issqn) throws BusinessViolation, Exception {
        if (issqn == null) {
            throw new Exception("Contribuinte não foi passada como parâmetro");
        } else if (issqn.getPessoa() == null) {
            throw new BusinessViolation("Informe o contribuinte");
        }
    }

    /**
     * método para incluir ou salvar contribuinte
     *
     * @param i
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public Issqn salvarIssqn(Issqn i) throws BusinessViolation, Exception {
        try {
            verificarIssqn(i);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.salvarIssqn(i);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover contribuinte
     *
     * @param issqnId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerIssqn(int issqnId) throws BusinessViolation, Exception {
        Issqn t = repositorio.obterIssqnPorId(issqnId);
        if (t == null) {
            throw new Exception("Contriuinte não encontrado para exclusão");
        }

        try {
            repositorio.removerIssqn(issqnId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar contribuinte pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public Issqn obterIssqnPorId(int id) throws BusinessViolation, Exception {
        Issqn t;

        try {
            t = repositorio.obterIssqnPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Contribuinte não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar os contribuintes
     *
     * @param filtro
     * @param limite
     * @param idExercicio
     * @param apenasPendentes
     * @return
     * @throws Exception
     */
    public List<Issqn> obterIssqns(String filtro, Integer limite, int idExercicio, String filter) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        //limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterIssqns(filtro, limite, idExercicio, filter);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<IssqnAnexo> obterAnexos(Integer issId) {
        if (issId == null) {
            return new ArrayList<>();
        }
        return repositorio.obterAnexos(issId);
    }

    public List<IssqnTaxa> carregarAliquotas(Integer id_iss) {
        return repositorio.carregarAliquotas(id_iss);
    }

    public IssqnContribuinteTaxa salvarContribuinteTaxa(IssqnContribuinteTaxa ict) {
        return repositorio.salvarContribuinteTaxa(ict);
    }

    public void removerContribuinteTaxa(int ict) {
        repositorio.removerContribuinteTaxa(ict);
    }

    public IssqnCnaeIss salvarCnaeIss(IssqnCnaeIss ici) {
        return repositorio.salvarCnaeIss(ici);
    }

    public Integer getNumLicenca(String orgaoId) {
        return repositorio.getNumLicenca(orgaoId);
    }

    public void atualizarLicencaSequencia(String orgaoId) {
        repositorio.atualizarLicencaSequencia(orgaoId);
    }

    /**
     * método para retornar o issqn por nome do contribuinte
     *
     * @param nome
     * @param ano
     * @param limite
     * @return
     * @throws BusinessViolation
     */
    public List<Issqn> obterIssqnPorNomePessoa(String nome, Integer ano, Integer limite) throws BusinessViolation {
        if (nome == null || nome.trim().equals("")) {
            throw new BusinessViolation("Informe o nome do contribuinte para busca");
        }
        nome = nome.trim().toUpperCase() + "%";
        limite = (limite == null ? 20 : limite);

        return repositorio.obterIssqnPorNomePessoa(nome, ano, limite);
    }

    public Issqn obterIssqnPorInscricao(String inscricao, Integer ano) throws BusinessViolation {
        if (inscricao == null || inscricao.trim().equals("")) {
            throw new BusinessViolation("Informe a inscricao do contribuinte para busca");
        }
        return repositorio.obterIssqnPorInscricao(inscricao, ano);
    }

    /**
     * metodo para retornar o tipo da parcela
     *
     * @param descricao
     * @param ano
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     */
    public IssqnTipoCobranca obterIssqnTipoCobranca(String descricao, Integer ano) throws BusinessViolation {
        if (descricao == null || descricao.trim().equals("")) {
            throw new BusinessViolation("Informe o tipo da cobranca");
        }
        return repositorio.obterIssqnTipoCobranca(descricao, ano);
    }

    /**
     * metodo para retornar o issqn por cpf/cpnj e senha
     *
     * @param cpf
     * @param senha
     * @return
     */
    public Issqn obterIssqnPorCPFESenha(String cpf, String senha) {
        return repositorio.obterIssqnPorCPFESenha(cpf, senha);
    }

    /**
     * relatório de contribuintes simplificado
     *
     * @param orgaoId
     * @param sem_cpf
     * @param filtro
     * @param ordem
     * @throws Exception
     */
    public void imprimirRelacaoContribuintes(String orgaoId, boolean sem_cpf, String filtro, String ordem, String usuario) throws Exception {
        repositorio.imprimirRelacaoContribuintes(orgaoId, sem_cpf, filtro, ordem, usuario);
    }

    /**
     *
     * @param orgaoId
     * @param contribuinte
     * @param filtro
     * @param nome_usuario
     * @param exercicio
     * @param numeroProcesso
     * @throws Exception
     */
    public void imprimirEncerramentoInscricao(String orgaoId, int exercicio, String nome_usuario, String pesquisa, Issqn contribuinte) throws Exception {
        repositorio.imprimirEncerramentoInscricao(orgaoId, exercicio, nome_usuario, pesquisa, contribuinte);
    }

    /**
     *
     * @param orgaoId
     * @param contribuinte
     * @param filtro
     * @param ordem
     * @param exercicio
     * @throws Exception
     */
    public void imprimirConferenciaParcelas(String orgaoId, String contribuinte, String dataMax, int exercicio,String ordem,Integer tipoCobranca,String tipoRelatorio) throws BusinessViolation,Exception {
        repositorio.imprimirConferenciaParcelas(orgaoId, contribuinte, dataMax, exercicio,ordem,tipoCobranca,tipoRelatorio);
    }

    /**
     *
     * @param orgaoId
     * @param contribuinte
     * @param filtro
     * @param ordem
     * @param exercicio
     * @throws Exception
     */
    public void imprimirInativacaoInscricao(String orgaoId, String usuario, Issqn inscricao, int exercicio) throws Exception {
        repositorio.imprimirInativacaoInscricao(orgaoId, usuario, inscricao, exercicio);
    }

    /**
     * relatório certidao Negativa
     *
     * @param orgaoId
     * @param filtro
     * @throws Exception
     */
    public void imprimirCertidaoNegativa(String orgaoId, String inscricao, Date validade, Integer exercicio, String contribuinte, int id_pessoa) throws Exception {
        repositorio.imprimirCertidaoNegativa(orgaoId, inscricao, validade, exercicio, contribuinte, id_pessoa);
    }

    /**
     * relatório contribuinte pendente documentação / fiscalização
     *
     * @param orgaoId
     * @throws Exception
     */
    public void imprimirContribuinteDocFiscalizacao(String orgaoId, Integer ano) throws Exception {
        if (orgaoId == null || orgaoId.trim().equals("")) {
            throw new Exception("Orgão não passado como parâmetro");
        }
        repositorio.imprimirContribuinteDocFiscalizacao(orgaoId, ano);
    }

    /**
     * relatório contribuinte pendente documentação / fiscalização
     *
     * @param orgaoId
     * @param exercicio
     * @param ordem
     * @param usuario
     * @throws Exception
     */
    public void imprimirContribuintePorSegmento(String orgaoId, Integer exercicio, String ordem, String usuario) throws Exception {
        if (orgaoId == null || orgaoId.trim().equals("")) {
            throw new Exception("Orgão não passado como parâmetro");
        }
        repositorio.imprimirContribuintePorSegmento(orgaoId, exercicio, ordem, usuario);
    }

    /**
     * gerar Febraban
     *
     * @param orgaoId
     * @param pesquisa
     * @param exercicio
     * @param ordem
     * @param tipoCobrancaBoleto
     * @param vencimento
     * @throws Exception
     */
    public void gerarCobranca(String orgaoId, String pesquisa, int exercicio, int ordem, Integer tipoCobrancaBoleto, Date vencimento) throws Exception {
        repositorio.gerarCobranca(orgaoId, pesquisa, exercicio, ordem, tipoCobrancaBoleto, vencimento);
    }
    
    public void gerarBoletoFebraban(String orgaoId, String pesquisa, int exercicio, int ordem, Integer tipoCobrancaBoleto, Date vencimento) throws Exception {
        repositorio.gerarBoletoFebraban(orgaoId, pesquisa, exercicio, ordem, tipoCobrancaBoleto, vencimento);
    }

    public void emitirCartaCobranca(String orgaoId, boolean periodo, String where, int exercicio,
            String order, String texto, boolean frente_verso, SubRelatorio sub) throws Exception {
        repositorio.emitirCartaCobranca(orgaoId, periodo, where, exercicio, order, texto, frente_verso, sub);
    }

    public void imprimirRelacaoContribuintesDevedores(String orgaoId, String filtro, String ordem, String ano, boolean expediente, String usuario) throws Exception {
        repositorio.imprimirRelacaoContribuintesDevedores(orgaoId, filtro, ordem, ano, expediente, usuario);
    }

    public void registrarContribuinte(Issqn iss, List<IssqnAnexo> anexos) throws BusinessViolation, Exception {
        if (iss == null) {
            throw new Exception("Contribuinte não passado para registro");
        }
        if (iss.getPessoa() == null) {
            throw new Exception("Pessoa não passada para registro");
        }
        if (iss.getPessoa().getNome() == null || iss.getPessoa().getNome().trim().isEmpty()) {
            throw new BusinessViolation("Informe o nome!");
        }

        repositorio.registrarContribuinte(iss, anexos);
    }

    public void removerAnexo(int anexoId) throws BusinessViolation, Exception {
        IssqnAnexo t = repositorio.obterAnexosPorId(anexoId);
        if (t == null) {
            return;
        }

        try {
            repositorio.removerAnexo(anexoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * metodo para retornar o issqn por cpf/cpnj
     *
     * @param cpf
     * @return
     */
    public Issqn obterIssqnPorCPF(String cpf) {
        return repositorio.obterIssqnPorCPF(cpf);
    }

    public void imprimirFichaCadastral(String orgaoId, String inscricao, Integer exercicio, String usuario, String tipo, AuthenticationHeader authenticationHeader) throws Exception {
        repositorio.imprimirFichaCadastral(orgaoId, inscricao, exercicio, usuario, (tipo == null ? "ficha_cadastral_simplificada" : tipo), authenticationHeader);
    }

    public void imprimirListagemDebitos(String orgaoId, String inscricao, Date validade, Integer exercicio, String usuario, String where) throws Exception {
        repositorio.imprimirListagemDebitos(orgaoId, inscricao, validade, exercicio, usuario, where);
    }

    public List<IssqnCnaeIss> carregarCnae(Integer id_iss, Integer tpAtividade) {
        return repositorio.carregarCnae(id_iss, tpAtividade);
    }

    public List<IssqnCnae> obterCnaePorNome(String nome, Integer tpAtividade) {
        return repositorio.obterCnaePorNome(nome, tpAtividade);
    }

    public List<IssqnCnae> obterCnaePorCodigo(String codigoCnae) {
        return repositorio.obterCnaePorCodigo(codigoCnae);
    }

    /**
     * método para remover Cnae
     *
     * @param atividadeId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerAtividade(Integer atividadeId) throws BusinessViolation, Exception {
        IssqnCnaeIss s = repositorio.obterIssqnCnaeIssPorId(atividadeId);
        if (s == null) {
            return;
        }

        try {
            repositorio.removerIssqnCnaeIss(atividadeId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void imprimirDocumentoAutenticado(String chave) throws BusinessViolation {
        if (chave == null || chave.isEmpty()) {
            throw new BusinessViolation("Digite a chave de autenticação");
        }
        repositorio.imprimirDocumentoAutenticado(chave);
    }

    public List<String> obterCidade(String cidade) {
        return repositorio.obterIssqnCidade(cidade, 10);
    }

    public List<String> obterBairro(String bairro) {
        return repositorio.obterIssqnBairro(bairro, 10);
    }

    public List<String> obterLogradouro(String logradouro) {
        return repositorio.obterIssqnLogradouro(logradouro, 10);
    }

}
