/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.relatorio.RelatorioCartaCobrancaBean.SubRelatorio;
import br.com.eddydata.boleto.BancoBrasil;
import br.com.eddydata.boleto.BancoReal;
import br.com.eddydata.boleto.Bancoob;
import br.com.eddydata.boleto.BoletoBean;
import br.com.eddydata.boleto.BoletoBeanJasper;
import br.com.eddydata.boleto.Bradesco;
import br.com.eddydata.boleto.CaixaEconomicaNovo;
import br.com.eddydata.boleto.Hsbc;
import br.com.eddydata.boleto.Itau;
import br.com.eddydata.boleto.NossaCaixa;
import br.com.eddydata.boleto.SantanderBanespa;
import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.entidade.geral.LogEmissao;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnAnexo;
import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.entidade.issqn.IssqnCnaeIss;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnContribuinteTaxa;
import br.com.eddydata.entidade.issqn.IssqnTaxa;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.StringMD5;
import br.com.eddydata.suporte.Util;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.tempuri.AuthenticationHeader;
import org.tempuri.ConsultarNumeroProtocolosPorCNPJRequest;
import org.tempuri.ConsultarNumeroProtocolosPorCNPJResponse;
import org.tempuri.ConsultarProtocoloRequest;
import org.tempuri.Licenca;
import org.tempuri.Operacionais;
import org.tempuri.Solicitacao;

/**
 *
 * @author David
 */
public class IssqnRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;
    private final boolean parcelado = false;
    private boolean parc_alvara = false;
    private boolean parc_iss = false;
    private JRBeanCollectionDataSource subrpt;
    private Operacionais ws;
    private List<Solicitacao> listaEmpresas = new ArrayList<>();

    public IssqnRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized Issqn salvarIssqn(Issqn i) {
        if (i.getId() == null) {
            return adicionarEntidade(Issqn.class, i);
        } else {
            return setEntidade(Issqn.class, i);
        }
    }

    public synchronized void removerIssqn(int issqnId) {
        Issqn t = getEntidade(Issqn.class, issqnId);
        removerEntidade(t);
    }

    public Issqn obterIssqnPorId(int id) {
        return getEntidade(Issqn.class, id);
    }

    public List<Issqn> obterIssqns(String filtro, Integer limite, Integer idExercicio, String filter) {
        String where = "";
        if (!filtro.isEmpty()) {
            if (Util.isNumeric(filtro)) {
                where += "\n where (p.cpfCnpj = " + Util.quotarStr(filtro) + " or i.inscricao = " + Util.quotarStr(filtro) + ") ";
            } else {
                where += "\n where (UPPER(function('rem_acento', p.nome)) like " + Util.quotarStr(filtro + "%")
                        + " or l.nome like " + Util.quotarStr("%" + filtro + "%") + ")";
            }
            where += "\n and i.idExercicio = " + idExercicio;
        } else {
            where += "\n where i.idExercicio = " + idExercicio;
        }

        if (filter.equals("P")) {
            where += "\n and i.pendente = true";
        } else if (filter.equals("A")) {
            where += "\n and i.inativo = false and i.encerrado = false";
        } else if (filter.equals("I")) {
            where += "\n and (i.inativo = true)";
        } else if (filter.equals("S")) {
            where += "\n and i.bloqueado = true";
        } else if (filter.equals("E")) {
            where += "\n and i.encerrado = true";
        } else if (filter.equals("EV")) {
            where += "\n and i.eventualidade = true";
        }

        if (limite == null) {
            return getListaPura(Issqn.class,
                    "select i from Issqn i "
                    + "\njoin i.pessoa p"
                    + "\njoin i.imovel im"
                    + "\njoin im.bairroLogradouro bl"
                    + "\njoin bl.logradouro l"
                    + where
                    + "\norder by i.id");
        } else {
            return getListaPuraLimite(Issqn.class,
                    "select i from Issqn i "
                    + "\njoin i.pessoa p"
                    + "\njoin i.imovel im"
                    + "\njoin im.bairroLogradouro bl"
                    + "\njoin bl.logradouro l"
                    + where
                    + "\norder by i.id", limite);
        }

    }

    public List<Issqn> obterIssqnPorNomePessoa(String nome, Integer ano, Integer limite) {
        return getListaPuraLimite(Issqn.class,
                "select distinct i from Issqn i"
                + "\njoin i.pessoa p"
                + "\nwhere UPPER(p.nome) like ?1"
                + " and i.idExercicio = ?2"
                + "\norder by p.nome", limite, nome, ano
        );
    }

    public List<String> obterIssqnCidade(String nome, Integer limite) {
        return getListaPuraLimite(String.class,
                "select distinct i.cidade from Pessoa i"
                + "\nwhere UPPER(i.cidade) like ?1"
                + "\norder by i.cidade", limite, nome.toUpperCase() + "%"
        );
    }

    public List<String> obterIssqnBairro(String nome, Integer limite) {
        return getListaPuraLimite(String.class,
                "select distinct i.bairro from Pessoa i"
                + "\nwhere UPPER(i.bairro) like ?1"
                + "\norder by i.bairro", limite, nome.toUpperCase() + "%"
        );
    }

    public List<String> obterIssqnLogradouro(String nome, Integer limite) {
        return getListaPuraLimite(String.class,
                "select distinct i.logradouro from Pessoa i"
                + "\nwhere UPPER(i.logradouro) like ?1"
                + "\norder by i.logradouro", limite, nome.toUpperCase() + "%"
        );
    }

    public List<IssqnAnexo> obterAnexos(Integer issId) {
        return getListaPura(IssqnAnexo.class,
                "select a from IssqnAnexo a "
                + "where a.iss.id = ?1", issId
        );
    }

    public IssqnAnexo obterAnexosPorId(Integer anexoId) {

        List<IssqnAnexo> lst = getListaPura(IssqnAnexo.class,
                "select a from IssqnAnexo a"
                + " where a.id = ?1", anexoId
        );
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public Issqn obterIssqnPorInscricao(String inscricao, Integer ano) {
        List<Issqn> lst = getListaPura(Issqn.class,
                "select distinct i from Issqn i"
                + "\njoin i.pessoa p"
                + "\nwhere i.inscricao like ?1"
                + " and i.idExercicio = ?2"
                + "\norder by p.nome", inscricao, ano
        );
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public IssqnTipoCobranca obterIssqnTipoCobranca(String descricao, Integer ano) {
        List<IssqnTipoCobranca> lst = getListaPura(IssqnTipoCobranca.class,
                "select distinct t from IssqnTipoCobranca t"
                + "\nwhere t.descricao like ?1"
                + "\nand t.idExercicio = ?2"
                + "\norder by t.descricao", descricao, ano
        );
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public Issqn obterIssqnPorCPFESenha(String cpf, String senha) {
        List<Issqn> lst = getListaPura(Issqn.class,
                "select distinct i from Issqn i"
                + "\njoin i.pessoa p"
                + "\nwhere p.cpfCnpj = ?1"
                + "\nand i.senha = ?2", cpf, StringMD5.md5(senha));
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public Integer getNumLicenca(String orgaoId) {
        return getEntidadePura(Integer.class,
                "select c.sequenciaNLicenca from IssqnConfiguracao c "
                + "\n where c.orgao.idOrgao = ?1", orgaoId);
    }

    public synchronized void removerIssqnCnaeIss(int issqnId) {
        IssqnCnaeIss t = getEntidade(IssqnCnaeIss.class, issqnId);
        removerEntidade(t);
    }

    public IssqnCnaeIss obterIssqnCnaeIssPorId(int id) {
        return getEntidade(IssqnCnaeIss.class, id);
    }

    public void imprimirRelacaoContribuintes(String orgaoId, boolean sem_cpf, String filtro, String ordem, String usuario) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("InformaÃ§Ãµes do orgÃ£o nÃ£o encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("usuario", usuario);

            List<Object[]> lstObj = createNativeQuery(
                    " SELECT DISTINCT I.INSCRICAO, I.ID_ISS, P.NOME, P.CPF_CNPJ, I.DT_ABERTURA, C.NOME,"
                    + "\nI.NUM_ALVARA, P.RG, CASE WHEN P.TP_PESSOA = 0 THEN 'PESSOA FISICA' ELSE 'PESSOA JURIDICA' END AS TP_PESSOA,"
                    + "\nL.NOME as END, "
                    + "\nII.NR_IMOVEL, B.NOME, I.NUM_ALVARA, P.TELEFONE"
                    + "\nFROM ISSQN I"
                    + "\nINNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA"
                    + "\nLEFT JOIN ISSQN_CNAE_ISS ICI ON ICI.ID_ISS = I.ID_ISS"
                    + "\nLEFT JOIN ISSQN_CNAE ICN ON ICN.ID_CNAE = ICI.ID_CNAE"
                    + "\nINNER JOIN ISSQN_CATEGORIA IC ON IC.ID_CATEGORIA = I.ID_CATEGORIA "
                    + "\nINNER JOIN IMOVEL II ON II.ID_IMOVEL = I.ID_IMOVEL"
                    + "\nINNER JOIN BAIRRO_LOGRADOURO BL ON BL.ID_BAIRROLOGRADOURO = II.ID_BAIRROLOGRADOURO"
                    + "\nINNER JOIN LOGRADOURO L ON L.ID_LOGRADOURO = BL.ID_LOGRADOURO AND BL.ID_CIDADE = L.ID_CIDADE"
                    + "\nINNER JOIN BAIRRO B ON B.ID_BAIRRO = BL.ID_BAIRRO AND B.ID_CIDADE = BL.ID_CIDADE"
                    + "\nINNER JOIN CIDADE C ON C.ID_CIDADE = BL.ID_CIDADE"
                    + "\nLEFT JOIN ABREVIATURA A ON A.ID_ABREVIATURA = L.ID_ABREVIATURA"
                    + "\nLEFT JOIN ABREVIATURA AA ON AA.ID_ABREVIATURA = B.ID_ABREVIATURA"
                    + "\nLEFT JOIN ISSQN_MOVIMENTO IM ON IM.ID_ISS = I.ID_ISS"
                    + "\nWHERE 1=1 " + filtro
                    + "\nORDER BY " + ordem
            );

            for (Object[] obj : lstObj) {
                HashMap field = new HashMap();

                if (sem_cpf == true) {
                    if ((((Util.isValidCPF(Util.unMask(".-/\\", obj[3].toString()))))) || (((Util.isValidCNPJ(Util.unMask(".-/\\", obj[3].toString())))))) {
                        continue;
                    } else if ((((Util.isValidCNPJ(Util.unMask(".-/\\", obj[3].toString())))))) {
                        continue;
                    }
                }

                field.put("inscricao", Util.extractStr(obj[0]));
                field.put("contribuinte", Util.extractStr(obj[2]));
                field.put("atividade", buscaAtividades(Integer.parseInt(Util.extractStr(obj[1]))).toUpperCase());
                field.put("endImovel", Util.extractStr(obj[9]) + "," + Util.extractStr(obj[10]));
                field.put("cidade", Util.extractStr(obj[5]));
                field.put("bairroImovel", Util.extractStr(obj[11]));
                field.put("tp_pessoa", Util.extractStr(obj[8]));
                field.put("num_alvara", Util.extractStr(obj[12]).trim());
                field.put("dt_abertura", Util.extractDate(obj[4]));
                field.put("cpf_cnpj", Util.extractStr(obj[3]));
                field.put("total_contribuintes", lstObj.size());
                field.put("telefone", Util.extractStr(obj[13]));

                lst.add(field);
            }

            rpt.jasperReport("relacao_contribuinte", "application/pdf", lst, p, "relacao_contribuinte");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    private String buscaAtividadesCobranca(int id_iss) {
        String atv_desc = "";
        String condicao = "";
//        if (parc_iss) {
        condicao = " AND ICA.ATIVIDADE_SECUNDARIA = TRUE ";
//        }
        String sql_atv
                = "SELECT IA.CODIGO_CNAE FROM ISSQN_CNAE_ISS ICA \n"
                + "INNER JOIN ISSQN_CNAE IA ON ICA.ID_CNAE = IA.ID_CNAE\n"
                + "WHERE ID_ISS = " + id_iss
                + condicao + "ORDER BY IA.NOME";
        try {
            List<Object> lstObj = createNativeQuery(sql_atv);
            for (Object obj : lstObj) {
                if (!atv_desc.isEmpty()) {
                    atv_desc += "                   " + obj.toString();
                } else {
                    atv_desc = obj.toString();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return atv_desc.toUpperCase();
    }

    private String buscaAtividades(int id_iss) {
        String atv_desc = "";
        String condicao = "";
        String sql_atv
                = "SELECT IA.NOME FROM ISSQN_CNAE_ISS ICA \n"
                + "INNER JOIN ISSQN_CNAE IA ON ICA.ID_CNAE = IA.ID_CNAE\n"
                + "WHERE ID_ISS = " + id_iss
                + condicao + "ORDER BY IA.NOME";
        try {
            List<Object> lstObj = createNativeQuery(sql_atv);
            for (Object obj : lstObj) {
                if (!atv_desc.isEmpty()) {
                    atv_desc += "\n  " + obj.toString();
                } else {
                    atv_desc = "  " + obj.toString();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return atv_desc.toUpperCase();
    }

    private String buscaSocios(int id_iss) {
        String socios = "";
        List<String> lstObj = createNativeQuery("SELECT UPPER(PS.NOME) || ', NACIONALIDADE: BRASILEIRA, CPF: ' || coalesce(PS.CPF_CNPJ,'') || ' RG/RNE: ' || coalesce(PS.RG,'') || ', RESIDENTE À ' || coalesce(LOGRADOURO,'') || ', ' || coalesce(NUMERO,0) || ', BAIRRO ' \n"
                + "|| coalesce(BAIRRO,'') || ', CIDADE ' || coalesce(CIDADE,'') AS SOCIO"
                + " FROM ISSQN_SOCIO S \n"
                + " INNER JOIN PESSOA PS ON PS.ID_PESSOA = S.ID_PESSOA\n"
                + " WHERE ID_ISS = " + id_iss
                + " ORDER BY PS.NOME");
        try {
            for (String obj : lstObj) {
                if (!socios.isEmpty()) {
                    socios += "\n  " + obj;
                } else {
                    socios = "  " + obj;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return socios;
    }

    public void imprimirCertidaoNegativa(String orgaoId, String inscricao, Date validade, Integer exercicio, String contribuinte, int id_pessoa) throws Exception {
        String mensagem = "";
        int num_certidao = 0;

        // Parametros que deverá ser pego do sistema
        String chefe = "DIRCE LAUTO GUIMARÃES OLIVEIRA";
        String setor = "I.S.S.Q.N E TAXA DE LICENÇA";
        String titulo_chefe = "CHEFE DO CEAT";
        String cidade = "AMÉRICO BRASILIENSE";

        Map p = new HashMap();
        ArrayList<HashMap> lst = new ArrayList<>();
        ReportGenerator rpt = new ReportGenerator();

        IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                "select c from IssqnConfiguracao c "
                + "\n where c.orgao.idOrgao = ?1 "
                + "\norder by c.id desc", orgaoId
        );
        if (conf == null) {
            throw new Exception("Informações do parâmetros não encontradas!");
        }

        ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
        if (orgao == null) {
            throw new Exception("Informações do orgão não encontradas!");
        }
        byte[] logotipo_bytes = orgao.getBrasao();
        ImageIcon logotipo = new ImageIcon();
        if (logotipo_bytes != null) {
            logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans

        }

        String chave = null;
        try {
//            chave = Util.geraCertificado(conf.getSequenciaNCertidao() == null ? 0 : conf.getSequenciaNCertidao());// validação antiga
            chave = validaChaveAutenticao(Funcao.chaveAutenticacao()); // validação padrão nota fiscal
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        p.put("orgao", orgao.getNome().toUpperCase());
        p.put("orgao1", orgao.getNome().toUpperCase());
        p.put("brasao", logotipo.getImage());
        p.put("marcaDagua", adicionaMarcaDagua(logotipo_bytes));
        p.put("codigoValidacao", chave);

        List<Object[]> lstObj = createNativeQuery(" SELECT DISTINCT p.nome AS proprietario, l.nome || ', ' || i.nr_imovel AS LOG,\n"
                + "b.nome AS BAIRRO, i.cep, i.complemento,\n"
                + "ii.inscricao, p.insc_municipal as inscricao_municipal, ii.id_iss, II.isento,\n"
                + "COALESCE(ii.cnpj_cpf, p.cpf_cnpj) as cpf_cnpj, ii.isento, ii.inscr_estadual,\n"
                + "ii.dt_abertura, ii.dt_encerramento, c.nome as cidade \n"
                + "FROM issqn ii\n"
                + "INNER JOIN imovel i ON i.id_imovel = ii.id_imovel\n"
                + "INNER JOIN bairro_logradouro bl ON bl.id_bairrologradouro = i.id_bairrologradouro\n"
                + "INNER JOIN pessoa p ON p.id_pessoa = ii.id_pessoa\n"
                + "INNER JOIN cidade c ON c.id_cidade = bl.id_cidade\n"
                + "INNER JOIN bairro b ON b.id_bairro = bl.id_bairro AND b.id_cidade = bl.id_cidade\n"
                + "INNER JOIN logradouro l ON l.id_logradouro = bl.id_logradouro AND l.id_cidade = bl.id_cidade\n"
                + "LEFT JOIN ISSQN_CONTRIBUINTE_ATIVIDADE ICA ON II.ID_ISS = ICA.ID_ISS\n"
                + "LEFT JOIN issqn_atividade a ON a.id_atividade = ica.id_atividade\n"
                + "LEFT JOIN abreviatura al ON al.id_abreviatura = l.id_abreviatura\n"
                + "LEFT JOIN abreviatura ab ON ab.id_abreviatura = b.id_abreviatura\n"
                + "LEFT JOIN ISSQN_MOVIMENTO IM ON IM.ID_ISS = II.ID_ISS AND (IM.CANCELADO_MOVIMENTO = 0 OR IM.CANCELADO_MOVIMENTO IS NULL)\n"
                + "WHERE II.INSCRICAO = " + Util.quotarStr(inscricao) + "\n"
                + "AND II.ID_EXERCICIO =" + exercicio);

        for (Object[] obj : lstObj) {

            HashMap field = new HashMap();

            Date dataAtual = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
            String dataExtenso = formatador.format(dataAtual);

            field.put("proprietario", Util.extractStr(obj[0]));
            field.put("endereco", Util.extractStr(obj[1]));
            field.put("bairro", Util.extractStr(obj[2]));
            field.put("cep", Util.extractStr(obj[3]));
            field.put("complemento", Util.extractStr(obj[4]).toUpperCase());
            field.put("cnpj", Util.extractStr(obj[9]));
            field.put("inscricao", Util.extractStr(obj[5]));
            field.put("inscricao_estadual", Util.extractStr(obj[11]));
            field.put("atividade", obj[7] == null ? "" : buscaAtividades(Integer.parseInt(obj[7].toString())));
            field.put("mensagemUsuario", "Certificado de Certidão Negativa emitido para " + Util.extractStr(obj[0]).toUpperCase()
                    + " : " + Util.extractStr(obj[9]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                    + ", garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                    + "autenticidade " + chave + ", " + dataExtenso + ".");
            lst.add(field);
        }

        String tipo = "";
        String titulo = "";
        tipo = "SIM";
        titulo = "CERTIDÃO POSITIVA";

        if (tipo.equals("NÃO")) {
            mensagem = "CERTIFICO OUTROSSIM QUE O MESMO NÃO CONSTA DÉBITO ATÉ "
                    + "A PRESENTE DATA SOBRE OS SEGUINTES TRIBUTOS: TAXA DE LICENÇA E/ OU ISSQN. "
                    + " RESSALVO, PORÉM,  O DIREITO DE A FAZENDA MUNICIPAL COBRAR A DÍVIDA QUE POR VENTURA "
                    + "SEJA VERIFICADA POSTERIORMENTE.";
        } else if (tipo.equals("SIM")) {
            mensagem = "CERTIFICO OUTROSSIM QUE O MESMO CONSTA DÉBITO ATÉ "
                    + "A PRESENTE DATA SOBRE OS SEGUINTES TRIBUTOS: TAXA DE LICENÇA E/ OU ISSQN. "
                    + " RESSALVO, PORÉM,  O DIREITO DE A FAZENDA MUNICIPAL COBRAR A DÍVIDA QUE POR VENTURA "
                    + "SEJA VERIFICADA POSTERIORMENTE.";
        } else if (tipo.equals("SIM PARCELADO")) {
            mensagem = "CONSTA DÉBITO EM DÍVIDA ATIVA, PARCELAS EM ATRASO";
        } else if (tipo.equals("NÃO PARCELADO")) {
            mensagem = "CONSTA DÉBITO EM DÍVIDA ATIVA, PARCELAS EM DIA";
        }
        mensagem = "                     " + chefe + ", " + titulo_chefe + " da Prefeitura Municipal de " + cidade + ", a requerimento da pessoa interessada " + contribuinte + ", "
                + "CERTIFICA para os fins que se fizeram necessários, que a pessoa jurídica/física a seguir referenciada registra débitos de qualquer natureza, "
                + "para com os cofres públicos municipais até a presente data, tendo a presente CERTIDÃO validade até o dia " + validade + ", "
                + "ressalvando o direito da Fazenda Municipal de exigir o recolhimento de débitos, tributários ou não, constituído anteriormente a esta data mesmo durante a vigência desse prazo.";

        String date = null;
        SimpleDateFormat f = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy");
        try {
            date = f.format(new Date());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        p.put("chefe", chefe);
        p.put("titulo_chefe", titulo_chefe);
        p.put("setor", setor);
        p.put("cidade", cidade);
        p.put("data", date.toUpperCase());
        p.put("mensagem", mensagem);
        p.put("num_certidao", num_certidao);
        p.put("titulo", titulo += " DE DÉBITOS MUNICIPAIS");
        p.put("validade", validade);
        p.put("id_exercicio", exercicio);
//        p.put("num_certidao", 2000001037);
        p.put("num_certidao", conf.getSequenciaNCertidao());

        if (lst.size() > 0) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null) {
                ip = request.getRemoteAddr();
            }

            LogEmissao logEmissao = new LogEmissao();
            logEmissao.setArquivo(rpt.byteArray("certidao_negativa_com_timbre", "application/pdf", lst, p, "certidao_negativa_com_timbre"));
            logEmissao.setChave(chave);
            logEmissao.setData(new Date());
            logEmissao.setCertidao(conf.getSequenciaNCertidao().toString());
            logEmissao.setIp(ip);
            logEmissao.setTipo("CERTIDÃO NEGATIVA");
            setEntidade(LogEmissao.class, logEmissao);
            conf.setSequenciaNCertidao(conf.getSequenciaNCertidao() + 1);
            setEntidade(IssqnConfiguracao.class, conf);
        }

        rpt.jasperReport("certidao_negativa_com_timbre", "application/pdf", lst, p, "certidao_negativa_com_timbre");

    }

    public void emitirCartaCobranca(String orgaoId, boolean periodo, String where, int exercicio, String order, String texto, boolean frente_verso, SubRelatorio sub) throws Exception {

        Map p = new HashMap();
        ArrayList<HashMap> lista = new ArrayList<>();
        ReportGenerator rpt = new ReportGenerator();
        String inscricao = "";
        Integer id_movimento = null;

        ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
        if (orgao == null) {
            throw new Exception("Informações do orgão não encontradas!");
        }
        byte[] logotipo_bytes = null;
        ImageIcon logotipo = new ImageIcon();
        if (logotipo_bytes != null) {
            logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans

        }

        p.put("orgao", orgao.getNome().toUpperCase());
        p.put("orgao1", orgao.getNome().toUpperCase());
        p.put("brasao", logotipo.getImage());

        int qtd_pagos = 0;
        String descricao_parc = "";

        String sql_aux = " AND CI.ID_MOVIMENTO not in (SELECT distinct IMP2.ID_MOVIMENTO FROM ISSQN_MOVIMENTO_PARCELA IMP2\n"
                + " INNER JOIN ISSQN_TIPO_COBRANCA TC2 ON TC2.ID_TIPO_COBRANCA = IMP2.ID_TIPO_COBRANCA\n"
                + " WHERE IMP2.DT_PAGAMENTO IS NOT NULL AND\n"
                + " COALESCE(IMP2.CANCELADO_PAGAMENTO, 0) = 0 AND TC2.QTD_PARCELA = 1\n"
                + " AND TC2.TP_COBRANCA = 1 ) AND T.QTD_PARCELA > 1 \n";

        String sql
                = " select DISTINCT II.INSCRICAO, P.NOME, COALESCE((ALI.NOME ||' '|| LI.NOME), LI.NOME) as LOGRA_IMOVEL,  COALESCE(CEIC.NOME,CEI.NOME) AS CIDADE, CEI.NOME AS CIDADE_COBRANCA, EI.CEP, \n"
                + "\n IC.CEP AS CEP_COBRANCA, EI.COMPLEMENTO, COALESCE(IC.COMPLEMENTO,EI.COMPLEMENTO) AS COMPLEMENTO_COBRANCA, EI.NR_IMOVEL, COALESCE((ABI.NOME ||' '|| BI.NOME), BI.NOME) AS BAIRRO_COBRANCA,  \n"
                + "\n CEI.UF AS UF_COBRANCA, COALESCE(CEIC.UF,CEI.UF) UF,  COALESCE(COALESCE((ABC.NOME ||' '|| BIC.NOME), BIC.NOME),BI.NOME) AS BAIRRO_IMOVEL, MI.ID_MOVIMENTO,  COALESCE(COALESCE((ALI.NOME ||' '|| LIC.NOME), LIC.NOME),LI.NOME) as LOGRA_COBRANCA,\n"
                + "\n COALESCE(IC.NR_IMOVEL,EI.NR_IMOVEL) AS NR_COBRANCA, II.ID_ISS  "
                + "\n from ISSQN II "
                + "\n inner JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = II.ID_ISS "
                + "\n inner join ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO = MI.ID_MOVIMENTO"
                + "\n inner join ISSQN_TIPO_COBRANCA T ON T.ID_TIPO_COBRANCA = CI.ID_TIPO_COBRANCA"
                + "\n left join issqn_contribuinte_atividade ica on ii.id_iss = ica.id_iss"
                + "\n left join ISSQN_ATIVIDADE A ON A.ID_ATIVIDADE = ICA.ID_ATIVIDADE"
                + "\n inner join PESSOA P ON P.ID_PESSOA = II.ID_PESSOA "
                + "\n left join IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL "
                + "\n left join BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO "
                + "\n left join CIDADE CEI ON CEI.ID_CIDADE = BL.ID_CIDADE "
                + "\n left join LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE "
                + "\n left join BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE "
                + "\n left join IMOVEL IC ON IC.ID_IMOVEL = II.ID_IMOVEL_COBRANCA "
                + "\n left join BAIRRO_LOGRADOURO BLC on BLC.ID_BAIRROLOGRADOURO = IC.ID_BAIRROLOGRADOURO "
                + "\n left join BAIRRO BIC ON BIC.ID_BAIRRO = BLC.ID_BAIRRO AND BIC.ID_CIDADE = BLC.ID_CIDADE "
                + "\n left join CIDADE CEIC ON CEIC.ID_CIDADE = BLC.ID_CIDADE "
                + "\n left join LOGRADOURO LIC ON LIC.ID_LOGRADOURO = BLC.ID_LOGRADOURO AND LIC.ID_CIDADE = BLC.ID_CIDADE "
                + "\n left join ABREVIATURA ALI ON ALI.ID_ABREVIATURA = LI.ID_ABREVIATURA "
                + "\n left join ABREVIATURA ABI ON ABI.ID_ABREVIATURA = BI.ID_ABREVIATURA "
                + "\n left join ABREVIATURA ABC ON ABC.ID_ABREVIATURA = BIC.ID_ABREVIATURA "
                + where
                + "\n AND (MI.CANCELADO_MOVIMENTO = 0 or MI.CANCELADO_MOVIMENTO is null) "
                + "\n AND (CI.STATUS = 0 or CI.STATUS is null)"
                + "\n AND CI.DT_PAGAMENTO IS NULL"
                + sql_aux
                + "\n AND II.ID_EXERCICIO = " + exercicio
                + "\n AND II.DT_INATIVIDADE IS NULL "
                + "\n AND II.DT_ENCERRAMENTO IS NULL "
                + "\n AND II.DT_BLOQUEADO IS NULL "
                + order;

        try {
            List<Object[]> lstObj = createNativeQuery(sql);
            for (Object[] obj : lstObj) {
                inscricao = obj[0].toString();
                id_movimento = Util.extractInt(obj[14]);
                HashMap field = new HashMap();

                field.put("exercicio", exercicio);
                field.put("proprietario", obj[1] == null ? "" : obj[1].toString());
                field.put("inscricao", obj[0] == null ? "" : obj[0].toString());
                field.put("logradouro", obj[2] == null ? "" : obj[2].toString());
                field.put("numero", obj[9] == null ? "" : obj[9].toString());
                field.put("complemento", obj[7] == null ? "" : obj[7].toString());
                field.put("bairro", obj[13] == null ? "" : obj[13].toString());
                field.put("cidade", obj[3] == null ? "" : obj[3].toString());
                field.put("uf", obj[12] == null ? "" : obj[12].toString());
                field.put("cep", obj[5] == null ? "" : obj[5].toString());
                field.put("logra_cobranca", obj[15] == null ? "" : obj[15].toString());
                field.put("nr_cobranca", obj[16] == null ? "" : obj[16].toString());
                field.put("complemento_cobranca", obj[8] == null ? "" : obj[8].toString());
                field.put("bairro_cobranca", obj[10] == null ? "" : obj[10].toString());
                field.put("cidade_cobranca", obj[4] == null ? "" : obj[4].toString());
                field.put("uf_cobranca", obj[11] == null ? "" : obj[11].toString());
                field.put("cep_cobranca", obj[6] == null ? "" : obj[6].toString());
                HashMap fieldSub = new HashMap();

                ArrayList sub_rel = new ArrayList();
                fieldSub.put("bairro_ent", obj[10] == null ? "" : obj[10].toString());
                fieldSub.put("cep_ent", obj[6] == null ? "" : obj[6].toString());
                fieldSub.put("cidade_ent", obj[4] == null ? "" : obj[4].toString());
                fieldSub.put("complemento_ent", obj[8] == null ? "" : obj[8].toString());
                fieldSub.put("logradouro_ent", obj[15] == null ? "" : obj[15].toString());
                fieldSub.put("numero_ent", obj[16] == null ? "" : obj[16].toString());
                fieldSub.put("uf_ent", obj[11] == null ? "" : obj[11].toString());

                sub_rel.add(sub);
                field.put("", new JRBeanCollectionDataSource(sub_rel));

                field.put("texto", texto);

                sql
                        = "SELECT T.DESCRICAO, CI.PARCELA, CI.DT_VENCIMENTO, CI.VL_PARCELA, MI.TP_MOVIMENTO\n"
                        + "FROM ISSQN I\n"
                        + "INNER JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = I.ID_ISS\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO = MI.ID_MOVIMENTO\n"
                        + "LEFT JOIN BANCO B ON B.ID_BANCO = CI.ID_BANCO\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA T ON T.ID_TIPO_COBRANCA = CI.ID_TIPO_COBRANCA \n"
                        + "WHERE T.ID_EXERCICIO = " + exercicio + "\n"
                        + "AND (MI.CANCELADO_MOVIMENTO IS NULL OR MI.CANCELADO_MOVIMENTO = 0)\n"
                        + "AND (CI.CANCELADO_PAGAMENTO IS NULL OR CI.CANCELADO_PAGAMENTO = 0)\n"
                        + "AND (CI.STATUS = 0 or CI.STATUS is null)"
                        + "AND I.INSCRICAO = " + Util.quotarStr(inscricao) + " AND CI.DT_PAGAMENTO is null \n"
                        + "AND CI.ID_MOVIMENTO = " + id_movimento + "\n"
                        + sql_aux
                        + "ORDER BY T.ID_TIPO_COBRANCA, CI.PARCELA";

                List<Object[]> list = createNativeQuery(sql);
                HashMap fieldClone = (HashMap) field.clone();
                for (Object[] o : list) {
                    field = new HashMap();
                    field.putAll(fieldClone);
                    if (Util.extractInt(o[4]) == 1) {
                        descricao_parc = ("LICENÇA DE FUNCIONAMENTO");
                    } else {
                        descricao_parc = ("ISS " + exercicio);
                    }
                    field.put("forma_pagamento", descricao_parc);
                    field.put("parcelas", Util.extractInt(o[1].toString()));
                    field.put("dt_venc", new Date(((java.sql.Date) o[2]).getTime()));
                    field.put("vl_parcela", Util.extractDouble(o[3].toString()));
                    lista.add(field);
                }

            }
            if (frente_verso) {
                rpt.jasperReport("carta_cobranca_verso", "application/pdf", lista, p, "carta_cobranca_verso");
            } else {
                rpt.jasperReport("carta_cobranca", "application/pdf", lista, p, "carta_cobranca");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void verificaCalculo(int id_iss) {

        String sql_primeira_parc = "SELECT IMP.DT_VENCIMENTO FROM ISSQN_MOVIMENTO IM \n"
                + "INNER JOIN ISSQN_MOVIMENTO_PARCELA IMP ON (IM.ID_MOVIMENTO = IMP.ID_MOVIMENTO) WHERE \n"
                + "(IM.CANCELADO_MOVIMENTO = 0 OR IM.CANCELADO_MOVIMENTO IS NULL) \n"
                + "AND IM.ID_ISS = " + id_iss + " AND IMP.DT_VENCIMENTO < CURRENT_DATE \n"
                + "ORDER BY IMP.DT_VENCIMENTO ASC\n"
                + "LIMIT 1";
        try {
            List<Object> lstObj = createNativeQuery(sql_primeira_parc);
            if (lstObj != null && lstObj.size() > 0) {
                String sql_calculo = "SELECT IM.TP_MOVIMENTO FROM ISSQN_MOVIMENTO IM \n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA IMP ON (IM.ID_MOVIMENTO = IMP.ID_MOVIMENTO) WHERE \n"
                        + "(IM.CANCELADO_MOVIMENTO = 0 OR IM.CANCELADO_MOVIMENTO IS NULL) \n"
                        + " AND (IMP.STATUS <> 2 OR IMP.STATUS IS NULL) and coalesce(IMP.CANCELADO_PAGAMENTO, 0) = 0 \n"
                        + "AND IM.ID_ISS = " + id_iss;

                List<Object[]> lstCalculo = createNativeQuery(sql_calculo);
                for (Object[] obj : lstCalculo) {
                    if (Integer.parseInt(obj[0].toString()) == 1) {
                        parc_alvara = true;
                    } else {
                        parc_iss = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imprimirContribuinteDocFiscalizacao(String orgaoId, Integer ano) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("InformaÃ§Ãµes do orgÃ£o nÃ£o encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());

            List<Issqn> lista = getListaPura(Issqn.class,
                    "select i from Issqn i "
                    + "\njoin i.pessoa p where (i.pendenteFiscalizacao = true or i.pendenteDocumentacao = true) "
                    + "\nand i.idExercicio = ?1 "
                    + "\norder by p.nome", ano);

            for (Issqn i : lista) {
                HashMap field = new HashMap();

                field.put("inscricao", i.getInscricao());
                field.put("contribuinte", i.getPessoa().getNome());

                lst.add(field);
            }
            rpt.jasperReport("relacao_contribuinte_pendente", "application/pdf", lst, p, "relacao_contribuinte_pendente");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }

    public void imprimirContribuintePorSegmento(String orgaoId, Integer exercicio, String ordem, String usuario) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("usuario", usuario);

            String sql
                    = "SELECT DISTINCT II.INSCRICAO as INSCRICAO, P.NOME, LI.NOME || ', ' || NR_IMOVEL || ' - ' || BI.NOME AS ENDERECO,\n"
                    + "UPPER(IRA.NOME) AS RAMO_ATUACAO, II.FANTASIA, SEG.NOME\n"
                    + "FROM ISSQN II\n"
                    + "LEFT JOIN IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL\n"
                    + "INNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA\n"
                    + "INNER JOIN BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO\n"
                    + "INNER JOIN CIDADE CEI ON CEI.ID_CIDADE = BL.ID_CIDADE\n"
                    + "INNER JOIN LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE\n"
                    + "INNER JOIN BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE\n"
                    + "INNER JOIN ISSQN_SEGMENTO SEG ON SEG.ID_SEGMENTO = II.ID_SEGMENTO\n"
                    + "LEFT JOIN ISSQN_RAMO_ATUACAO IRA ON IRA.ID_RAMO_ATUACAO = II.ID_RAMO_ATUACAO\n"
                    + "WHERE II.ID_EXERCICIO = " + exercicio + "\n "
                    + "ORDER BY 6," + ordem;

            List<Object[]> lista = createNativeQuery(sql);

            for (Object[] obj : lista) {
                HashMap field = new HashMap();

                field.put("contribuinte", Util.extractStr(obj[1]));
                field.put("inscricao", Util.extractStr(obj[0]));
                field.put("nomeFantasia", Util.extractStr(obj[4]));
                field.put("ramoAtuacao", Util.extractStr(obj[3]));
                field.put("endereco", Util.extractStr(obj[2]));
                field.put("segmento", Util.extractStr(obj[5]));
                lst.add(field);
            }
            rpt.jasperReport("relacao_segmento", "application/pdf", lst, p, "relacao_segmento");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }

    public void imprimirRelacaoContribuintesDevedores(String orgaoId, String filtro, String ordem, String exercicio, boolean expediente, String usuario) throws Exception {
        try {
            String where = "";
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();
            int parcela_count = 0;

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);

            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }

            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();

            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("usuario", usuario);
            if (filtro.length() > 0) {
                where = filtro + " AND ";
            } else {
                where = "";
            }

            List<Object[]> lstObj = createNativeQuery(
                    " select DISTINCT II.INSCRICAO, P.NOME, COALESCE((ALI.NOME ||' '|| LI.NOME), LI.NOME) as LOGRA_IMOVEL, II.DT_ALVARA, "
                    + "\n EI.NR_IMOVEL, BI.NOME AS BAIRRO_IMOVEL, "
                    + "\n COALESCE((ABC.NOME ||' '|| BIC.NOME), BI.NOME) AS BAIRRO_COBRANCA, MI.ID_MOVIMENTO, II.ALVARA_PROVISORIO, "
                    + "\n COALESCE(COALESCE((ALI.NOME ||' '|| LIC.NOME), LIC.NOME),LI.NOME) as LOGRA_COBRANCA, COALESCE(IC.NR_IMOVEL,EI.NR_IMOVEL) AS NR_COBRANCA, "
                    + "\n II.ID_ISS, SUM(MP.vl_parcela) AS VALOR,"
                    + "\n COALESCE(P.TEL1,P.TELEFONE) TEL1, COALESCE(P.TEL2,P.CELULAR) TEL2, P.TEL3, P.TEL4, P.TEL5, P.TEL6,cnae.nome atividade,II.CNPJ_CPF "
                    + "\n from ISSQN II "
                    + "\n inner join ISSQN_MOVIMENTO MI ON MI.ID_ISS = II.ID_ISS "
                    + "\n inner join ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = MI.ID_MOVIMENTO"
                    + "\n inner join ISSQN_MOVIMENTO_PARCELA_ITEM MPI ON MPI.ID_MOVIMENTO_PARCELA = MP.ID_MOVIMENTO_PARCELA"
                    + "\ninner join ISSQN_TAXA IT ON IT.ID_TAXA = MPI.ID_TAXA"
                    + "\ninner join BANCO BAN ON BAN.ID_BANCO = IT.ID_BANCO"
                    + "\n inner join PESSOA P ON P.ID_PESSOA = II.ID_PESSOA "
                    + "\n inner join IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL "
                    + "\n inner join BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO "
                    + "\n inner join BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE"
                    + "\n inner join CIDADE CEI ON CEI.ID_CIDADE = BI.ID_CIDADE"
                    + "\n inner join LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE"
                    + "\n left join IMOVEL IC ON IC.ID_IMOVEL = II.ID_IMOVEL_COBRANCA "
                    + "\n left join BAIRRO_LOGRADOURO BLC on BLC.ID_BAIRROLOGRADOURO = IC.ID_BAIRROLOGRADOURO "
                    + "\n left join BAIRRO BIC ON BIC.ID_BAIRRO = BLC.ID_BAIRRO AND BIC.ID_CIDADE = BLC.ID_CIDADE"
                    + "\n left join CIDADE CEIC ON CEIC.ID_CIDADE = BIC.ID_CIDADE"
                    + "\n left join LOGRADOURO LIC ON LIC.ID_LOGRADOURO = BLC.ID_LOGRADOURO AND LIC.ID_CIDADE = BLC.ID_CIDADE"
                    + "\n left join ABREVIATURA ALI ON ALI.ID_ABREVIATURA = LIC.ID_ABREVIATURA "
                    + "\n left join ABREVIATURA ABI ON ABI.ID_ABREVIATURA = BI.ID_ABREVIATURA "
                    + "\n left join ABREVIATURA ABC ON ABC.ID_ABREVIATURA = BIC.ID_ABREVIATURA "
                    + "\n left join issqn_cnae_iss ici on ici.id_iss = ii.id_iss and ici.atividade_primaria = true"
                    + "\n left join issqn_cnae cnae on ici.id_cnae = cnae.id_cnae"
                    + "\n where " + where
                    + "\n MI.DT_CANCELADO IS NULL AND COALESCE(MP.CANCELADO_PAGAMENTO, 0) = 0 AND MP.DT_PAGAMENTO IS NULL "
                    + "\n AND II.ID_EXERCICIO = " + exercicio + " AND coalesce(MP.STATUS, 0) <> 2"
                    + "\n AND MI.ID_MOVIMENTO not in (SELECT distinct IMP2.ID_MOVIMENTO FROM ISSQN_MOVIMENTO_PARCELA IMP2"
                    + "\n INNER JOIN ISSQN_TIPO_COBRANCA TC2 ON TC2.ID_TIPO_COBRANCA = IMP2.ID_TIPO_COBRANCA"
                    + "\n WHERE IMP2.DT_PAGAMENTO IS NOT NULL AND"
                    + "\n COALESCE(IMP2.CANCELADO_PAGAMENTO, 0) = 0 AND TC2.QTD_PARCELA = 1"
                    + "\n AND TC2.TP_COBRANCA = 1 /*1 = ISS*/)"
                    + "\n GROUP BY II.INSCRICAO, P.NOME, ALI.NOME, LI.NOME, II.DT_ALVARA,  EI.NR_IMOVEL, ABI.NOME, BI.NOME, ABC.NOME, BIC.NOME, MI.ID_MOVIMENTO, "
                    + "\n II.ALVARA_PROVISORIO, ALI.NOME, LIC.NOME, IC.NR_IMOVEL, II.ID_ISS, "
                    + "\n COALESCE(P.TEL1,P.TELEFONE), COALESCE(P.TEL2,P.CELULAR), P.TEL3, P.TEL4, P.TEL5, P.TEL6,cnae.nome,II.CNPJ_CPF "
                    + "\norder by " + ordem);

            for (Object[] obj : lstObj) {

                double vl_expediente = verificaValorExpediente(obj[7].toString());

                String where_aux = "";
                double dia = 0;
                String desc_venc = "";

                if (obj[3] != null && obj[8].toString().equals("1")) {
                    dia = Util.diferencaEmDias(Util.parseBrStrToDate(obj[3].toString()), new Date());

                    if (dia > 59) {
                        desc_venc = "ALVARÁ VENCIDO HÁ " + dia + " DIA(S)";
                    }

                } else {
                    where_aux = "AND CI.DT_PAGAMENTO is null\n";
                }

                List<Object[]> lstSql = createNativeQuery("SELECT T.DESCRICAO, CI.PARCELA, CI.DT_VENCIMENTO, CI.VL_PARCELA,\n"
                        + "CI.DT_PAGAMENTO, CI.VL_PAGO, CI.VL_JUROS, CI.VL_MULTA \n"
                        + "FROM ISSQN I\n"
                        + "INNER JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = I.ID_ISS\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO = MI.ID_MOVIMENTO\n"
                        + "LEFT JOIN BANCO B ON B.ID_BANCO = CI.ID_BANCO\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA T ON T.ID_TIPO_COBRANCA = CI.ID_TIPO_COBRANCA \n"
                        + "WHERE T.ID_EXERCICIO = " + exercicio + "\n"
                        + "AND MI.DT_CANCELADO IS NULL AND COALESCE(CI.CANCELADO_PAGAMENTO, 0) = 0 AND CI.DT_PAGAMENTO IS NULL\n"
                        + "AND coalesce(CI.STATUS, 0) <> 2\n"
                        + "AND CI.VL_PARCELA > 0\n"
                        + "AND DT_VENCIMENTO < CURRENT_DATE\n"
                        + "AND I.INSCRICAO LIKE " + Util.quotarStr(obj[0].toString()) + " " + where_aux + "\n"
                        + "AND CI.ID_MOVIMENTO = " + obj[7].toString()
                        + "AND ((T.QTD_PARCELA > 1) or ((SELECT COUNT(ID_MOVIMENTO) FROM ISSQN_MOVIMENTO_PARCELA WHERE ID_MOVIMENTO = CI.ID_MOVIMENTO) = 1))"
                        + "ORDER BY T.ID_TIPO_COBRANCA, CI.PARCELA");

                for (Object[] o : lstSql) {
                    parcela_count++;
                    HashMap field = new HashMap();
                    field.put("inscricao", obj[0].toString());

                    field.put("nome", obj[1].toString());
                    field.put("endImovel", Util.extractStr(obj[5]) + ", " + Util.extractStr(obj[2]) + ", " + Util.extractStr(obj[4]));
                    field.put("endCobranca", Util.extractStr(obj[6]) + ", " + Util.extractStr(obj[9]) + ", " + Util.extractStr(obj[10]));
                    field.put("atividade", Util.extractStr(obj[19]));
                    field.put("tel1", Util.extractStr(obj[13]));
                    field.put("tel2", Util.extractStr(obj[14]));
                    field.put("tel3", Util.extractStr(obj[15]));
                    field.put("tel4", Util.extractStr(obj[16]));
                    field.put("tel5", Util.extractStr(obj[17]));
                    field.put("tel6", Util.extractStr(obj[18]));
                    field.put("cnpj_cpf", Util.extractStr(obj[20]));

                    field.put("forma_pagamento", o[0].toString());
                    field.put("parcela", Integer.parseInt(o[1].toString()));
                    field.put("vencimento", Util.parseSqlToBrDate(o[2].toString()));

                    if (expediente && parcela_count > 1) {
                        field.put("valor", Double.parseDouble(o[3].toString()) - vl_expediente);
                    } else {
                        field.put("valor", Double.parseDouble(o[3].toString()));
                    }

                    field.put("pagamento", o[4] == null ? null : Util.convertBrToDate(o[4].toString().replace("-", "/")));
                    field.put("valor_pago", Double.parseDouble(o[5] == null ? "0" : o[5].toString()));
                    field.put("juros", Double.parseDouble(o[6] == null ? "0" : o[6].toString()));
                    field.put("multa", Double.parseDouble(o[7] == null ? "0" : o[7].toString()));
                    field.put("correcao", Double.parseDouble("0"));

                    if (expediente && parcela_count > 1) {
                        field.put("valor_total", (Double.parseDouble(o[5] == null ? "0" : o[5].toString()) - vl_expediente) + Double.parseDouble(o[6] == null ? "0" : o[6].toString()) + Double.parseDouble(o[7] == null ? "0" : o[7].toString()));
                    } else {
                        field.put("valor_total", Double.parseDouble(o[5] == null ? "0" : o[5].toString()) + Double.parseDouble(o[6] == null ? "0" : o[6].toString()) + Double.parseDouble(o[7] == null ? "0" : o[7].toString()));
                    }

                    if (o[4] == null ? false : o[4].toString().length() > 0) {
                        field.put("pago", "Pago");
                    } else {
                        field.put("pago", "Não Pago");
                    }
                    Date hoje = new Date();

                    if (o[2] != null && Util.extractDate(o[2]).before(hoje)) {
                        dia = Util.diferencaEmDias(Util.extractDate(o[2]), new Date());
                    } else {
                        dia = 0.0;
                    }
                    field.put("diasatraso", dia);

                    lst.add(field);
                }

            }
            rpt.jasperReport("extrato", "application/pdf", lst, p, "extrato");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public double verificaValorExpediente(String id_movimento) {
        double valor = 0;
        String exp = "SELECT DISTINCT SUM(VL_TAXA)\n"
                + "FROM ISSQN_MOVIMENTO_PARCELA_ITEM IMPI\n"
                + "INNER JOIN ISSQN_MOVIMENTO_PARCELA IMP ON IMP.ID_MOVIMENTO_PARCELA = IMPI.ID_MOVIMENTO_PARCELA\n"
                + "INNER JOIN ISSQN_TIPO_COBRANCA TC ON TC.ID_TIPO_COBRANCA = IMP.ID_TIPO_COBRANCA\n"
                + "WHERE IMP.ID_MOVIMENTO = " + id_movimento + "\n"
                + "AND TC.QTD_PARCELA > 1\n";
        try {
            List<Double> qry = createNativeQuery(exp);

            for (Double o : qry) {
                valor += o == null ? 0 : o;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Funcao.mensagemAtencao("Erro", "NÃ£o foi possÃ­vel verificar o valor expediente!");
        }
        return valor;
    }

    public void registrarContribuinte(Issqn iss, List<IssqnAnexo> anexos) throws RuntimeException {
        try {
//            Pessoa p = iss.getPessoa();
//            p = setEntidade(Pessoa.class, p, true);
//
//            iss.setPessoa(p);
//            iss.setPendente(true);
//
//            iss = setEntidade(Issqn.class, iss, true);

            for (IssqnAnexo anexo : anexos) {
                if (anexo == null) {
                    continue;
                }
                anexo.setIss(iss);
                anexo.setDataCadastro(new Date());

                salvarAnexo(anexo);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void salvarAnexo(IssqnAnexo anexo) {
        try {
            if (anexo.getId() == null) {
                adicionarEntidade(IssqnAnexo.class, anexo);
            } else {
                setEntidade(IssqnAnexo.class, anexo);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void removerAnexo(int anexoId) {
        IssqnAnexo t = getEntidade(IssqnAnexo.class, anexoId);
        removerEntidade(t);
    }

    public Issqn obterIssqnPorCPF(String cpf) {
        List<Issqn> lst = getListaPura(Issqn.class,
                "select distinct i from Issqn i"
                + "\njoin i.pessoa p"
                + "\nwhere i.cnpjCpf = ?1", cpf);
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public void imprimirFichaCadastral(String orgaoId, String inscricao, Integer exercicio, String usuario, String tipo, AuthenticationHeader authenticationHeader) throws Exception {

        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c ");
            if (conf == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = conf.getOrgao().getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans
            }

            Date data = new java.util.Date();
            String date = null;

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = f.format(data);
            } catch (Exception ex) {
            }

            String chave = null;
            try {
                chave = validaChaveAutenticao(Funcao.chaveAutenticacao()); // validação padrão nota fiscal
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //-- PARAMETROS
            p.put("orgao", conf.getOrgao().getNome());
            p.put("cidade", conf.getOrgao().getCidade());
            p.put("brasao", logotipo.getImage());
            p.put("usuario", usuario);
            p.put("marcaDagua", adicionaMarcaDagua(logotipo_bytes));
            p.put("codigoValidacao", chave);

            String sql
                    = " \nSELECT DISTINCT II.INSCRICAO as INSCRICAO, P.NOME, II.DT_INICIO, II.CNPJ_CPF, LI.NOME AS LOGRA_IMOVEL,"
                    + " \nEI.CEP, UPPER(IRA.NOME) AS RAMO_ATUACAO, II.FANTASIA, BI.NOME AS BAIRRO, CEI.NOME AS CIDADE, EI.NR_IMOVEL, CEI.UF, EI.COMPLEMENTO, II.ID_ISS, "
                    + " \nII.INSCR_ESTADUAL, IA.NOME ATIVIDADE_PRIMARIA,II.DT_ABERTURA,II.NUM_ALVARA,P.INSC_MUNICIPAL,II.NR_JUNTA_COMERCIAL, "
                    + " \nIH.PROCESSO_DATA,IH.PROCESSO,IH.HISTORICO_ASSUNTO,IH.HISTORICO_DATA,IH.HISTORICO_OBS "
                    + " \nFROM ISSQN II "
                    + " \nINNER JOIN IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL "
                    + " \nINNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA "
                    + " \nINNER JOIN BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO "
                    + " \nINNER JOIN CIDADE CEI ON CEI.ID_CIDADE = BL.ID_CIDADE "
                    + " \nINNER JOIN LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE "
                    + " \nINNER JOIN BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE "
                    + " \nINNER JOIN ABREVIATURA ALI ON ALI.ID_ABREVIATURA = LI.ID_ABREVIATURA "
                    + " \nINNER JOIN ABREVIATURA ABI ON ABI.ID_ABREVIATURA = BI.ID_ABREVIATURA "
                    + " \nLEFT JOIN ISSQN_CNAE_ISS ICA ON II.ID_ISS = ICA.ID_ISS AND ICA.ATIVIDADE_PRIMARIA = TRUE"
                    + " \nLEFT JOIN ISSQN_CNAE IA ON IA.ID_CNAE = ICA.ID_CNAE "
                    + " \nLEFT JOIN ISSQN_CATEGORIA IRA ON IRA.ID_CATEGORIA = II.ID_CATEGORIA"
                    + " \nLEFT JOIN ISSQN_HISTORICO IH ON IH.ID_ISS = II.ID_ISS"
                    + " \nWHERE II.INSCRICAO = " + Util.quotarStr(inscricao)
                    + " \nAND II.ID_EXERCICIO = " + exercicio;

            List<Object[]> lista = createNativeQuery(sql);

            for (Object[] obj : lista) {
                HashMap field = new HashMap();

                String msg = "FIM DAS INFORMAÇÕES PARA INSCRIÇÃO: " + Util.extractStr(obj[0]) + "\n"
                        + "DATA DA ÚLTIMA ATUALIZAÇÃO DA BASE DE DADOS: " + date;

                field.put("nome", Util.extractStr(obj[1]));
                field.put("inscricaoMunicipal", Util.extractStr(obj[18]));
                field.put("dataInscricao", Util.extractDate(obj[2]));
                field.put("dataEmissao", date);
                switch (Util.extractStr(obj[3]).length()) {
                    case 0:
                        field.put("documento", Util.extractStr(obj[3]));
                        break;
                    case 11:
                        field.put("documento", Util.mask("###.###.###-##", Util.extractStr(obj[3])));
                        break;
                    default:
                        field.put("documento", Util.mask("##.###.###/####-##", Util.extractStr(obj[3])));
                        break;
                }

                Date dataAtual = new Date();
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
                String dataExtenso = formatador.format(dataAtual);
                buscarProtocolo(Util.extractStr(obj[3]), authenticationHeader);
                field.put("endereco", Util.extractStr(obj[4]));
                field.put("cep", Util.extractStr(obj[5]));
                field.put("mensagem", msg);
                field.put("tipo", Util.extractStr(obj[6]));
                field.put("nomeFantasia", Util.extractStr(obj[7]));
                field.put("bairro", Util.extractStr(obj[8]));
                field.put("municipio", Util.extractStr(obj[9]));
                field.put("numero", Util.extractStr(obj[10]));
                field.put("uf", Util.extractStr(obj[11]));
                field.put("complemento", Util.extractStr(obj[12]));
                field.put("nireEmpresarial", Util.extractStr(obj[19]));
                field.put("inscricaoEstadual", Util.extractStr(obj[14]));
                field.put("licencaValida", Util.extractStr(obj[17]));
                field.put("constituicao", Util.extractDate(obj[16]));
                field.put("naturezaAtividade", buscaAtividades(Integer.parseInt(obj[13].toString())).toUpperCase());
                field.put("titularSocios", Util.extractStr(buscaSocios(Util.extractInt(obj[13]))));
                field.put("mensagemUsuario", "Certificado de Ficha Cadastral " + (tipo.equals("ficha_cadastral_completa") ? "Completa" : "Simplificada") + " emitido para " + Util.extractStr(obj[1]).toUpperCase()
                        + " : " + Util.extractStr(obj[3]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                        + ", garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                        + "autenticidade " + chave + ", " + dataExtenso + ".");

                field.put("processo", Util.extractStr(obj[21]));
                field.put("data_processo", obj[20] == null ? null : Util.extractDate(obj[20]));
                field.put("assunto", Util.extractStr(obj[22]).toUpperCase());
                field.put("observacao_processo", Util.extractStr(obj[24]).toUpperCase());

                lst.add(field);

            }

            if (lst.size() > 0) {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                String ip = request.getHeader("x-forwarded-for");
                if (ip == null) {
                    ip = request.getRemoteAddr();
                }

                LogEmissao logEmissao = new LogEmissao();
                logEmissao.setArquivo(rpt.byteArray(tipo, "application/pdf", lst, p, tipo));
                logEmissao.setChave(chave);
                logEmissao.setData(new Date());
                logEmissao.setCertidao(conf.getSequenciaNCertidao().toString());
                logEmissao.setIp(ip);
                logEmissao.setTipo("FICHA CADASTRAL");
                setEntidade(LogEmissao.class, logEmissao);
                conf.setSequenciaNCertidao(conf.getSequenciaNCertidao() + 1);
                setEntidade(IssqnConfiguracao.class, conf);
            }

            rpt.jasperReport(tipo, "application/pdf", lst, p, tipo);
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarProtocolo(String cnpj, AuthenticationHeader authenticationHeader) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ws_sil", "qaws123!".toCharArray());
            }
        });
        SSLContext ctx;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new IssqnRepositorio.DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        } catch (Exception ex) {
        }

        try {
            ws = new Operacionais();
            ConsultarNumeroProtocolosPorCNPJRequest parametros = new ConsultarNumeroProtocolosPorCNPJRequest();
            parametros.setCnpj(cnpj);
            ConsultarNumeroProtocolosPorCNPJResponse response = ws.getBasicHttpBindingIOperacionais().consultarNumeroProtocolosPorCNPJ(parametros, authenticationHeader);
            ConsultarProtocoloRequest cpr = new ConsultarProtocoloRequest();
            cpr.setProtocolos(response.getSolicitacoes());

            listaEmpresas = ws.getBasicHttpBindingIOperacionais().consultarProtocolo(cpr, authenticationHeader).getSolicitacoes().getSolicitacao();

        } catch (Exception e) {
            System.out.println("");
        }
    }

    public List<IssqnTaxa> carregarAliquotas(Integer id_iss) {
        List<IssqnTaxa> lst = getListaPura(IssqnTaxa.class, "select t from IssqnTaxa t");
        for (IssqnTaxa taxa : lst) {
            IssqnContribuinteTaxa contribuinteTaxa = null;
            if (id_iss != null) {
                contribuinteTaxa = getEntidadePura(IssqnContribuinteTaxa.class,
                        "select icat from IssqnContribuinteTaxa icat where icat.iss.id = ?1 "
                        + " and icat.taxa.id = ?2 ", id_iss, taxa.getId());
            }
            if (contribuinteTaxa == null) {
                contribuinteTaxa = new IssqnContribuinteTaxa();
                contribuinteTaxa.setTaxa(taxa);
            }
            taxa.setContribuinteTaxa(contribuinteTaxa);
        }

        return lst;
    }

    public IssqnContribuinteTaxa salvarContribuinteTaxa(IssqnContribuinteTaxa ict) {
        if (ict.getId() == null) {
            return adicionarEntidade(IssqnContribuinteTaxa.class, ict);
        } else {
            return setEntidade(IssqnContribuinteTaxa.class, ict);
        }
    }

    public void removerContribuinteTaxa(int ict) {
        IssqnContribuinteTaxa t = getEntidade(IssqnContribuinteTaxa.class, ict);
        removerEntidade(t);
    }

    public IssqnCnaeIss salvarCnaeIss(IssqnCnaeIss ici) {
        if (ici.getId() == null) {
            return adicionarEntidade(IssqnCnaeIss.class, ici);
        } else {
            return setEntidade(IssqnCnaeIss.class, ici);
        }
    }

    public List<IssqnCnae> obterCnaePorNome(String nome, Integer tpAtividade) {
        if (nome != null) {
            nome = Normalizer.normalize(nome, Normalizer.Form.NFD);
            nome = nome.replaceAll("[^\\p{ASCII}]", "");
        }
        return getListaPura(IssqnCnae.class, "select c from IssqnCnae c where UPPER(function('rem_acento',c.nome)) like  ?1"
                + (tpAtividade == null ? "" : " and c.tpAtividade = " + tpAtividade), "%" + nome.toUpperCase() + "%");
    }

    public List<IssqnCnae> obterCnaePorCodigo(String codigoCnae) {
        return getListaPura(IssqnCnae.class, "select c from IssqnCnae c where function('rem_ponto',c.codigoCnae) like ?1", codigoCnae);
    }

    public List<IssqnCnae> obterCnaePorId(String codigoCnae) {
        return getListaPura(IssqnCnae.class, "select c from IssqnCnae c where c.codigoCnae = ?1", codigoCnae);
    }

    public List<IssqnCnaeIss> carregarCnae(Integer id_iss, Integer tpAtividade) {
        List<IssqnCnaeIss> lstPossui = null;
        if (id_iss != null) {
            lstPossui = getListaPura(IssqnCnaeIss.class, "select ici from IssqnCnaeIss ici where ici.iss.id = ?1", id_iss);
        }
        return lstPossui;
    }

    public void atualizarLicencaSequencia(String orgaoId) {
        IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                "select c from IssqnConfiguracao c "
                + "\n where c.orgao.idOrgao = ?1 "
                + "\norder by c.id desc", orgaoId
        );

        conf.setSequenciaNLicenca((conf.getSequenciaNLicenca() == null ? 0 : (conf.getSequenciaNLicenca() + 1)));
        setEntidade(IssqnConfiguracao.class, conf);
    }

    public void imprimirEncerramentoInscricao(String orgaoId, int exercicio, String usuario_logado, String pesquisa, Issqn contribuinte) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c "
                    + "\n where c.orgao.idOrgao = ?1 "
                    + "\norder by c.id desc", orgaoId
            );

            int numero_protocolo = (conf.getSequenciaNProtocolo() == null ? 0 : conf.getSequenciaNProtocolo());

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
            }

            String chave = null;
            try {
//            chave = Util.geraCertificado(conf.getSequenciaNCertidao() == null ? 0 : conf.getSequenciaNCertidao());// validação antiga
                chave = validaChaveAutenticao(Funcao.chaveAutenticacao()); // validação padrão nota fiscal
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("marcaDagua", adicionaMarcaDagua(logotipo_bytes));
            p.put("codigoValidacao", chave);

            List<Object[]> lstObj = createNativeQuery("SELECT P.CPF_CNPJ, I.INSCRICAO, P.NOME,\n"
                    + "COALESCE(I.DT_ENCERRAMENTO,CURRENT_DATE), I.PROCESSO_N, I.DT_ABERTURA, UPPER(IRA.NOME), UPPER(IC.NOME),\n"
                    + "P.LOGRADOURO || ',' || IM.NR_IMOVEL || ' - ' || P.BAIRRO AS ENDERECO, IM.CEP\n"
                    + "FROM ISSQN I\n"
                    + "INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA\n"
                    + "LEFT JOIN ISSQN_RAMO_ATUACAO IRA ON IRA.ID_RAMO_ATUACAO = I.ID_RAMO_ATUACAO\n"
                    + "INNER JOIN IMOVEL IM ON IM.ID_IMOVEL = I.ID_IMOVEL\n"
                    + "LEFT JOIN ISSQN_CNAE_ISS ICI ON ICI.ID_ISS = I.ID_ISS AND ATIVIDADE_PRIMARIA = TRUE\n"
                    + "LEFT JOIN ISSQN_CNAE IC ON  IC.ID_CNAE = ICI.ID_CNAE\n"
                    + "WHERE I.ENCERRADO = TRUE AND I.ID_EXERCICIO = " + exercicio + pesquisa);

            for (Object[] obj : lstObj) {

                HashMap field = new HashMap();
                numero_protocolo++;

                Date dataAtual = new Date();
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
                String dataExtenso = formatador.format(dataAtual);
                int index = dataExtenso.indexOf(",");
                int lenght = dataExtenso.length();

                field.put("nome", Util.extractStr(obj[2]));
                if (contribuinte.getProtocoloBaixaInscricao().isEmpty()) {
                    field.put("numeroProtocolo", Util.formatarDecimal("00000", numero_protocolo) + "/" + exercicio);
                } else {
                    field.put("numeroProtocolo", contribuinte.getProtocoloBaixaInscricao());
                }
                field.put("numeroInscrMunicipal", Util.extractStr(obj[1]));
                field.put("dataInicio", Util.extractDate(obj[5]));
                field.put("dataBaixa", Util.extractDate(obj[3]));
                field.put("categoria", Util.extractStr(obj[6]));
                field.put("documento", Util.extractStr(obj[0]));
                field.put("cnae", Util.extractStr(obj[7]));
                field.put("endereco", Util.extractStr(obj[8]));
                field.put("cep", Util.extractStr(obj[9]));
                field.put("mensagem", "PARECER DA PREFEITURA DO MUNICÍPIO DE " + orgao.getNome().toUpperCase().toUpperCase());
                field.put("mensagemUsuario", "Certificado de Baixa de Inscrição emitido para " + Util.extractStr(obj[2]).toUpperCase()
                        + " : " + Util.extractStr(obj[0]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                        + ", garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                        + "autenticidade " + chave + ", " + dataExtenso + ".");
                field.put("mensagemParecer", "Este documento confirma a Baixa da Inscrição Municipal no Cadastro Mobiliário da Prefeitura Municipal de " + orgao.getCidade() + " na data " + dataExtenso.substring(++index, lenght));
                lst.add(field);
            }

            if (!lstObj.isEmpty()) {
                try {

                    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    String ip = request.getHeader("x-forwarded-for");
                    if (ip == null) {
                        ip = request.getRemoteAddr();
                    }

                    LogEmissao logEmissao = new LogEmissao();
                    logEmissao.setArquivo(rpt.byteArray("certidao_baixa_inscricao", "application/pdf", lst, p, "certidao_baixa_inscricao"));
                    logEmissao.setChave(chave);
                    logEmissao.setData(new Date());
                    logEmissao.setCertidao(conf.getSequenciaNCertidao().toString());
                    logEmissao.setIp(ip);
                    logEmissao.setTipo("ENCERRAMENTO INSCRIÇÃO");
                    setEntidade(LogEmissao.class, logEmissao);
                    conf.setSequenciaNCertidao(conf.getSequenciaNCertidao() + 1);
                    setEntidade(IssqnConfiguracao.class, conf);

                    if (contribuinte.getProtocoloBaixaInscricao().isEmpty()) {
                        conf.setSequenciaNProtocolo(numero_protocolo);
                        setEntidade(IssqnConfiguracao.class, conf);
                        contribuinte.setProtocoloBaixaInscricao(Util.extractStr(Util.formatarDecimal("00000", numero_protocolo) + "/" + exercicio));
                        setEntidade(Issqn.class, contribuinte);
                    } else {
                        contribuinte.setProtocoloBaixaInscricao(contribuinte.getProtocoloBaixaInscricao());
                        setEntidade(Issqn.class, contribuinte);
                    }

                } catch (Exception ex) {
                    throw new RuntimeException("Erro ao salvar sequencial do protocolo\n" + ex.getMessage());
                }
            }
            rpt.jasperReport("certidao_baixa_inscricao", "application/pdf", lst, p, "certidao_baixa_inscricao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirInativacaoInscricao(String orgaoId, String usuario, Issqn contribuinte, int exercicio) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c "
                    + "\n where c.orgao.idOrgao = ?1 "
                    + "\norder by c.id desc", orgaoId
            );

            int numero_protocolo = (conf.getSequenciaNProtocolo() == null ? 0 : conf.getSequenciaNProtocolo());

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
            }

            String chave = null;
            try {
//            chave = Util.geraCertificado(conf.getSequenciaNCertidao() == null ? 0 : conf.getSequenciaNCertidao());// validação antiga
                chave = validaChaveAutenticao(Funcao.chaveAutenticacao()); // validação padrão nota fiscal
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("marcaDagua", adicionaMarcaDagua(logotipo_bytes));
            p.put("codigoValidacao", chave);

            List<Object[]> lstObj = createNativeQuery("SELECT P.CPF_CNPJ, I.INSCRICAO, P.NOME,\n"
                    + "COALESCE(I.DT_ENCERRAMENTO,CURRENT_DATE), I.PROCESSO_N, I.DT_ABERTURA, UPPER(IRA.NOME), UPPER(IC.NOME),\n"
                    + "P.LOGRADOURO || ',' || IM.NR_IMOVEL || ' - ' || P.BAIRRO AS ENDERECO, IM.CEP\n"
                    + "FROM ISSQN I\n"
                    + "INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA\n"
                    + "INNER JOIN ISSQN_CATEGORIA IRA ON IRA.ID_CATEGORIA = I.ID_CATEGORIA\n"
                    + "INNER JOIN IMOVEL IM ON IM.ID_IMOVEL = I.ID_IMOVEL\n"
                    + "LEFT JOIN ISSQN_CNAE_ISS ICI ON ICI.ID_ISS = I.ID_ISS AND ATIVIDADE_PRIMARIA = TRUE\n"
                    + "LEFT JOIN ISSQN_CNAE IC ON  IC.ID_CNAE = ICI.ID_CNAE\n"
                    + "WHERE I.INATIVO = TRUE AND I.ID_EXERCICIO = " + exercicio + " AND I.INSCRICAO = " + Util.quotarStr(contribuinte.getInscricao()));

            for (Object[] obj : lstObj) {

                HashMap field = new HashMap();

                Date dataAtual = new Date();
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
                String dataExtenso = formatador.format(dataAtual);
                int index = dataExtenso.indexOf(",");
                int lenght = dataExtenso.length();

                numero_protocolo++;

                field.put("nome", Util.extractStr(obj[2]));
                if (contribuinte.getProtocoloInativacao().isEmpty()) {
                    field.put("numeroProtocolo", Util.formatarDecimal("00000", numero_protocolo) + "/" + exercicio);
                } else {
                    field.put("numeroProtocolo", contribuinte.getProtocoloInativacao());
                }
                field.put("numeroInscrMunicipal", obj[1].toString());
                field.put("dataInicio", Util.extractDate(obj[5]));
                field.put("dataBaixa", Util.extractDate(obj[3]));
                field.put("categoria", Util.extractStr(obj[6]));
                field.put("documento", Util.extractStr(obj[0]));
                field.put("cnae", Util.extractStr(obj[7]));
                field.put("endereco", obj[8].toString());
                field.put("cep", Util.extractStr(obj[9]));
                field.put("mensagem", "PARECER DA " + orgao.getNome().toUpperCase());
                field.put("mensagemUsuario", "Certificado de Inativação de Inscrição emitido para " + Util.extractStr(obj[2]).toUpperCase()
                        + " : " + Util.extractStr(obj[0]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                        + ", garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                        + "autenticidade " + chave + ", " + dataExtenso + ".");
                field.put("mensagemParecer", "Este documento confirma a Baixa da Inscrição Municipal no Cadastro Mobiliário da Prefeitura Municipal de " + orgao.getCidade() + " na data " + dataExtenso.substring(++index, lenght));
                lst.add(field);
            }

            if (!lstObj.isEmpty()) {
                try {

                    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    String ip = request.getHeader("x-forwarded-for");
                    if (ip == null) {
                        ip = request.getRemoteAddr();
                    }

                    LogEmissao logEmissao = new LogEmissao();
                    logEmissao.setArquivo(rpt.byteArray("comprovante_inativacao", "application/pdf", lst, p, "comprovante_inativacao"));
                    logEmissao.setChave(chave);
                    logEmissao.setData(new Date());
                    logEmissao.setCertidao(conf.getSequenciaNCertidao().toString());
                    logEmissao.setIp(ip);
                    logEmissao.setTipo("INATIVAÇÃO INSCRIÇÃO");
                    setEntidade(LogEmissao.class, logEmissao);
                    conf.setSequenciaNCertidao(conf.getSequenciaNCertidao() + 1);
                    setEntidade(IssqnConfiguracao.class, conf);

                    if (contribuinte.getProtocoloInativacao().isEmpty()) {
                        conf.setSequenciaNProtocolo(numero_protocolo);
                        setEntidade(IssqnConfiguracao.class, conf);
                        contribuinte.setProtocoloInativacao(Util.extractStr(Util.formatarDecimal("00000", numero_protocolo) + "/" + exercicio));
                        setEntidade(Issqn.class, contribuinte);
                    } else {
                        setEntidade(Issqn.class, contribuinte);
                    }

                } catch (Exception ex) {
                    throw new RuntimeException("Erro ao salvar sequencial do protocolo\n" + ex.getMessage());
                }
            }

            rpt.jasperReport("comprovante_inativacao", "application/pdf", lst, p, "comprovante_inativacao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirConferenciaParcelas(String orgaoId, String contribuinte, String dataMax, int exercicio, String ordem, Integer tipoCobranca, String tipoRelatorio) throws BusinessViolation, Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();
            HashMap<String, Taxa> mapTaxas = new HashMap<String, Taxa>();
            DecimalFormat df = new DecimalFormat("#0.00");

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new BusinessViolation("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            String condicao = "";
            if (dataMax != null) {
                condicao = " AND II.DT_ABERTURA <= " + dataMax;
            }
            if (tipoCobranca != 0 && tipoCobranca == 1) {
                condicao += " AND (ITC.TP_COBRANCA = " + tipoCobranca + " OR ITC.TP_COBRANCA = 2)";
            } else if (tipoCobranca != 0) {
                condicao += " AND ITC.TP_COBRANCA = " + tipoCobranca;
            }
            if (!contribuinte.isEmpty()) {
                condicao += " AND II.INSCRICAO = " + Util.quotarStr(contribuinte);
            }
            List<Object[]> lstObj = new ArrayList<>();
            if (!tipoRelatorio.equals("C")) {
                lstObj = createNativeQuery(
                        "SELECT II.INSCRICAO, P.NOME, LI.NOME || (CASE WHEN EI.NR_IMOVEL IS NULL THEN '' ELSE ', ' || EI.NR_IMOVEL END) || ' - '  || BI.NOME as LOGRA_IMOVEL,\n"
                        + "M.ID_MOVIMENTO, SUM(MP.VL_PARCELA), COUNT(MP.ID_MOVIMENTO) AS QTD, II.ID_ISS,\n"
                        + "CAST(SUM(MP.VL_PARCELA)/count(MP.ID_MOVIMENTO) AS NUMERIC(15,2)) AS VL_PARCELA,ITC.TP_COBRANCA\n"
                        + "FROM ISSQN II \n"
                        + "INNER JOIN ISSQN_MOVIMENTO M ON M.ID_ISS = II.ID_ISS \n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA ITC ON ITC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                        + "INNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA \n"
                        + "INNER JOIN IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL \n"
                        + "INNER JOIN BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO \n"
                        + "INNER JOIN CIDADE CEI ON CEI.ID_CIDADE = BL.ID_CIDADE \n"
                        + "INNER JOIN LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE \n"
                        + "INNER JOIN BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE \n"
                        + "WHERE M.DT_CANCELADO IS NULL AND M.CANCELADO_MOVIMENTO = 0\n"
                        + "AND (MP.STATUS IS NULL OR MP.STATUS <> 2)\n"
                        + "AND ITC.QTD_PARCELA >= (SELECT MAX(TC2.QTD_PARCELA)\n"
                        + "FROM ISSQN_TIPO_COBRANCA TC2,ISSQN_MOVIMENTO_PARCELA MP2 \n"
                        + "WHERE TC2.ID_EXERCICIO = ITC.ID_EXERCICIO\n"
                        + "and TC2.ID_TIPO_COBRANCA = MP2.ID_TIPO_COBRANCA\n"
                        + "and MP2.id_movimento = M.ID_MOVIMENTO)\n"
                        + "AND II.ID_EXERCICIO = " + exercicio + "\n"
                        + condicao
                        + "\n GROUP BY II.INSCRICAO, P.NOME, LI.NOME, II.ID_ISS,"
                        + "\n EI.NR_IMOVEL, BI.NOME, M.ID_MOVIMENTO, ITC.TP_COBRANCA"
                        + " ORDER BY " + ordem);
            } else {
                lstObj = createNativeQuery(
                        "SELECT II.INSCRICAO, P.NOME, LI.NOME || (CASE WHEN EI.NR_IMOVEL IS NULL THEN '' ELSE ', ' || EI.NR_IMOVEL END) || ' - '  || BI.NOME as LOGRA_IMOVEL,\n"
                        + "M.ID_MOVIMENTO, SUM(MP.VL_PARCELA), COUNT(1) AS QTD, II.ID_ISS,\n"
                        + "CAST(SUM(MP.VL_PARCELA)/count(MP.ID_MOVIMENTO) AS NUMERIC(15,2)) AS VL_PARCELA,ITC.TP_COBRANCA,\n"
                        + "SUM(CASE WHEN MP.STATUS = 2 THEN 1 ELSE 0 END) QTD_CANCELADO,SUM(CASE WHEN MP.STATUS = 2 THEN MP.VL_PARCELA ELSE 0 END) AS VL_CANCELADO\n"
                        + "FROM ISSQN II \n"
                        + "INNER JOIN ISSQN_MOVIMENTO M ON M.ID_ISS = II.ID_ISS \n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA ITC ON ITC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                        + "INNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA \n"
                        + "INNER JOIN IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL \n"
                        + "INNER JOIN BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO \n"
                        + "INNER JOIN CIDADE CEI ON CEI.ID_CIDADE = BL.ID_CIDADE \n"
                        + "INNER JOIN LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE \n"
                        + "INNER JOIN BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE \n"
                        + "WHERE M.DT_CANCELADO IS NULL AND M.CANCELADO_MOVIMENTO = 0\n"
                        + "AND ITC.QTD_PARCELA >= (SELECT MAX(TC2.QTD_PARCELA)\n"
                        + "FROM ISSQN_TIPO_COBRANCA TC2,ISSQN_MOVIMENTO_PARCELA MP2 \n"
                        + "WHERE TC2.ID_EXERCICIO = ITC.ID_EXERCICIO\n"
                        + "and TC2.ID_TIPO_COBRANCA = MP2.ID_TIPO_COBRANCA\n"
                        + "and MP2.id_movimento = M.ID_MOVIMENTO)\n"
                        + "AND II.ID_EXERCICIO = " + exercicio + "\n"
                        + " AND  II.INSCRICAO IN ( SELECT II2.INSCRICAO FROM ISSQN  II2\n"
                        + "INNER JOIN ISSQN_MOVIMENTO M2 ON M2.ID_ISS = II2.ID_ISS \n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA MP2 ON MP2.ID_MOVIMENTO = M2.ID_MOVIMENTO\n"
                        + "WHERE MP2.STATUS = 2)"
                        + condicao
                        + "\n GROUP BY II.INSCRICAO, P.NOME, LI.NOME, II.ID_ISS,"
                        + "\n EI.NR_IMOVEL, BI.NOME, M.ID_MOVIMENTO, ITC.TP_COBRANCA"
                        + " ORDER BY " + ordem);
            }
            if (lstObj.isEmpty()) {
                throw new BusinessViolation("Não possui parcelas para o filtro informado!");
            }
            double vl_unica = 0;
            for (Object[] obj : lstObj) {
                HashMap field = new HashMap();
                field.put("inscricao", obj[0].toString());
                field.put("contribuinte", obj[1] == null ? "" : obj[1].toString());
                field.put("endereco", obj[2] == null ? "" : obj[2].toString());
                field.put("qtd_parc", obj[5] == null ? "" : Util.extractInt(obj[5]));
                field.put("vl_total", Util.extractDouble(obj[4]));
                field.put("vl_parc", Util.truncarValor(Util.extractDouble(obj[7]), 2));
                field.put("atividade", buscaAtividades(Util.extractInt(obj[6].toString())));
                if (tipoRelatorio.equals("C")) {
                    field.put("qtd_cancelada", obj[9] == null ? "" : Util.extractInt(obj[9]));
                    field.put("vl_cancelada", obj[10] == null ? "" : Util.extractDouble(obj[10]));
                }
                List<Number> unica = createNativeQuery("select vl_total \n"
                        + "from issqn_movimento_parcela mp \n"
                        + "inner join issqn_tipo_cobranca tc on tc.id_tipo_cobranca = mp.id_tipo_cobranca \n"
                        + "where id_movimento = " + Util.extractInt(obj[3]) + " and tc.qtd_parcela = 1  \n"
                        + "order by 1");

                if (!unica.isEmpty()) {
                    vl_unica += unica.get(0).doubleValue();
                    field.put("vl_unica", Util.truncarValor(Util.extractDouble(unica.get(0)), 2));
                }
                if(tipoRelatorio.equals("C")){
                    
                }

                List<Object[]> taxas = createNativeQuery("SELECT UPPER(coalesce(T1.NOME, T.NOME)) AS NOME, \n"
                        + "SUM(MI.VL_TAXA) AS VL_TAXA, COALESCE(T1.ID_TAXA, T.ID_TAXA) AS ID_TAXA\n"
                        + "FROM ISSQN_MOVIMENTO M\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA_ITEM MI ON MI.ID_MOVIMENTO_PARCELA = MP.ID_MOVIMENTO_PARCELA\n"
                        + "INNER JOIN ISSQN_TAXA T ON T.ID_TAXA = MI.ID_TAXA\n"
                        + "LEFT JOIN ISSQN_TAXA T1 ON T1.ID_TAXA = T.ID_PARENTE\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA ITC ON ITC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                        + "INNER JOIN ISSQN I ON I.ID_ISS = M.ID_ISS\n"
                        //                        + "AND ITC.TP_COBRANCA >= 1\n"
                        + "AND ITC.QTD_PARCELA >= (SELECT MAX(TC2.QTD_PARCELA)\n"
                        + "FROM ISSQN_TIPO_COBRANCA TC2,ISSQN_MOVIMENTO_PARCELA MP2 \n"
                        + "WHERE TC2.ID_EXERCICIO = ITC.ID_EXERCICIO\n"
                        + "and TC2.ID_TIPO_COBRANCA = MP2.ID_TIPO_COBRANCA\n"
                        + "and MP2.ID_MOVIMENTO = M.ID_MOVIMENTO)\n"
                        + "WHERE M.ID_MOVIMENTO = " + Util.extractInt(obj[3]) + "\n"
                        + "AND ITC.TP_COBRANCA = " + obj[8] + "\n"
                        + "GROUP BY 1, 3");

                String texto_taxas = "";
                for (Object[] t : taxas) {
                    if (!texto_taxas.isEmpty()) {
                        texto_taxas += "     ";
                    }
                    texto_taxas += Util.extractStr(t[0]) + ": " + df.format(Util.extractDouble(t[1]));
                    Taxa taxa = mapTaxas.get(Util.extractStr(t[2]));
                    if (taxa == null) {
                        taxa = new Taxa();
                        taxa.setId_taxa(Util.extractStr(t[2]));
                        taxa.setNome(Util.extractStr(t[0]));
                        taxa.setVl_taxa(Util.extractDouble(t[1]));
                        mapTaxas.put(taxa.getId_taxa(), taxa);
                    } else {
                        taxa.setVl_taxa(taxa.getVl_taxa() + Util.extractDouble(t[1]));
                        mapTaxas.put(Util.extractStr(t[2]), taxa);
                    }
                }
                field.put("descricao_taxas", texto_taxas);
                lst.add(field);
            }
            String resumo_taxas = "";
            for (Taxa t : mapTaxas.values()) {
                if (!resumo_taxas.isEmpty()) {
                    resumo_taxas += "\n";
                }
                resumo_taxas += Util.Texto.alinharEsquerda(t.nome, 80);
                resumo_taxas += Util.Texto.alinharDireita(df.format(t.vl_taxa), 15);
            }
            p.put("resumo_taxas", resumo_taxas);
            if (tipoRelatorio.equals("S")) {
                rpt.jasperReport("parcelas", "application/pdf", lst, p, "parcelas");
            } else if (tipoRelatorio.equals("D")) {
                rpt.jasperReport("parcelas_detalhado", "application/pdf", lst, p, "parcelas_detalhado");
            } else if (tipoRelatorio.equals("C")) {
                rpt.jasperReport("parcelas_canceladas", "application/pdf", lst, p, "parcelas_canceladas");
            }

        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirConferenciaParcelas_bkp(String orgaoId, String contribuinte, String dataMax, int exercicio, String ordem) throws Exception {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            String condicao = "";
            if (dataMax != null) {
                condicao = " AND II.DT_ABERTURA <= " + dataMax;
            }
            List<Object[]> lstObj = createNativeQuery(
                    "SELECT DISTINCT II.INSCRICAO, P.NOME, COALESCE((ALI.NOME ||' '|| LI.NOME), LI.NOME) || ', ' || \n"
                    + "EI.NR_IMOVEL || ' - '  || COALESCE((ABI.NOME ||' '|| BI.NOME), BI.NOME) as LOGRA_IMOVEL, \n"
                    + "MI.ID_MOVIMENTO, MI.VL_TOTAL, COUNT(MP.ID_MOVIMENTO) AS QTD, II.ID_ISS, \n"
                    + "(SELECT VL_PARCELA FROM ISSQN_MOVIMENTO_PARCELA WHERE ID_MOVIMENTO = MI.ID_MOVIMENTO ORDER BY PARCELA ASC LIMIT 1) AS VL_PARCELA\n"
                    + "FROM ISSQN II \n"
                    + "INNER JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = II.ID_ISS \n"
                    + "INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = MI.ID_MOVIMENTO\n"
                    + "INNER JOIN ISSQN_TIPO_COBRANCA ITC ON ITC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                    + "INNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA \n"
                    + "LEFT JOIN IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL \n"
                    + "LEFT JOIN BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO \n"
                    + "LEFT JOIN CIDADE CEI ON CEI.ID_CIDADE = BL.ID_CIDADE \n"
                    + "LEFT JOIN LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE \n"
                    + "LEFT JOIN BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE \n"
                    + "LEFT JOIN ABREVIATURA ALI ON ALI.ID_ABREVIATURA = LI.ID_ABREVIATURA \n"
                    + "LEFT JOIN ABREVIATURA ABI ON ABI.ID_ABREVIATURA = BI.ID_ABREVIATURA \n"
                    + "WHERE MI.DT_CANCELADO IS NULL AND MI.CANCELADO_MOVIMENTO = 0\n"
                    + " AND ITC.QTD_PARCELA = 1 "
                    + "AND II.ID_EXERCICIO = " + exercicio + "\n"
                    + "--AND MP.ID_TIPO_COBRANCA = (SELECT ID_TIPO_COBRANCA FROM ISSQN_MOVIMENTO_PARCELA WHERE ID_MOVIMENTO = MI.ID_MOVIMENTO ORDER BY PARCELA DESC LIMIT 1)\n" // verificar regra depois
                    + "AND II.INSCRICAO = " + Util.quotarStr(contribuinte) + (condicao.isEmpty() ? "" : condicao)
                    + "\n GROUP BY II.INSCRICAO, P.NOME, ALI.NOME, LI.NOME, LI.NOME, II.ID_ISS,"
                    + "\n EI.NR_IMOVEL, ABI.NOME, BI.NOME, MI.ID_MOVIMENTO, MI.VL_TOTAL"
                    + " ORDER BY " + ordem);

            if (lstObj.size() == 0) {
                return;
            }
            for (Object[] obj : lstObj) {

                HashMap field = new HashMap();
                field.put("inscricao", obj[0].toString());
                field.put("contribuinte", obj[1] == null ? "" : obj[1].toString());
                field.put("endereco", obj[2] == null ? "" : obj[2].toString());
                field.put("qtd_parc", obj[5] == null ? "" : Util.extractInt(obj[5]));
                field.put("vl_total", Util.extractDouble(obj[4]));
                field.put("vl_parc", Util.extractDouble(obj[7]));
                field.put("atividade", buscaAtividades(Util.extractInt(obj[6].toString())));

                lst.add(field);
            }
            rpt.jasperReport("parcelas", "application/pdf", lst, p, "parcelas");

        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirListagemDebitos(String orgaoId, String inscricao, Date validade, Integer exercicio, String usuario, String where) throws Exception {

        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getBrasao();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));  // Lista com beans
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());
            p.put("usuario", usuario);

            /**
             * Foi colocado para imprimir somente o registro 001000001895 pois a
             * quantidade de registro é muito grande.
             */
            String sql
                    = "\n select DISTINCT II.INSCRICAO, P.NOME, COALESCE((ALI.NOME ||' '|| LI.NOME), LI.NOME) as LOGRA_IMOVEL, II.DT_ALVARA, "
                    + "\n EI.NR_IMOVEL, COALESCE((ABI.NOME ||' '|| BI.NOME), BI.NOME) AS BAIRRO_IMOVEL, "
                    + "\n COALESCE((ABC.NOME ||' '|| BIC.NOME), BIC.NOME) AS BAIRRO_COBRANCA, II.ALVARA_PROVISORIO, "
                    + "\n COALESCE((ALI.NOME ||' '|| LIC.NOME), LIC.NOME) as LOGRA_COBRANCA, IC.NR_IMOVEL AS NR_COBRANCA, II.ID_ISS, "
                    + "\n cnae.nome atividade"
                    + "\n from ISSQN II "
                    + "\n inner join ISSQN_MOVIMENTO MI ON MI.ID_ISS = II.ID_ISS "
                    + "\n inner join ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = MI.ID_MOVIMENTO"
                    + "\n inner join PESSOA P ON P.ID_PESSOA = II.ID_PESSOA "
                    + "\n inner join IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL "
                    + "\n inner join BAIRRO_LOGRADOURO BL on BL.ID_BAIRROLOGRADOURO = EI.ID_BAIRROLOGRADOURO "
                    + "\n inner join BAIRRO BI ON BI.ID_BAIRRO = BL.ID_BAIRRO AND BI.ID_CIDADE = BL.ID_CIDADE"
                    + "\n inner join CIDADE CEI ON CEI.ID_CIDADE = BI.ID_CIDADE"
                    + "\n inner join LOGRADOURO LI ON LI.ID_LOGRADOURO = BL.ID_LOGRADOURO AND LI.ID_CIDADE = BL.ID_CIDADE"
                    + "\n left join IMOVEL IC ON IC.ID_IMOVEL = II.ID_IMOVEL_COBRANCA "
                    + "\n left join BAIRRO_LOGRADOURO BLC on BLC.ID_BAIRROLOGRADOURO = IC.ID_BAIRROLOGRADOURO "
                    + "\n left join BAIRRO BIC ON BIC.ID_BAIRRO = BLC.ID_BAIRRO AND BIC.ID_CIDADE = BLC.ID_CIDADE"
                    + "\n left join CIDADE CEIC ON CEIC.ID_CIDADE = BIC.ID_CIDADE"
                    + "\n left join LOGRADOURO LIC ON LIC.ID_LOGRADOURO = BLC.ID_LOGRADOURO AND LIC.ID_CIDADE = BLC.ID_CIDADE"
                    + "\n left join ABREVIATURA ALI ON ALI.ID_ABREVIATURA = LIC.ID_ABREVIATURA "
                    + "\n left join ABREVIATURA ABI ON ABI.ID_ABREVIATURA = BI.ID_ABREVIATURA "
                    + "\n left join ABREVIATURA ABC ON ABC.ID_ABREVIATURA = BIC.ID_ABREVIATURA"
                    + "\n left join issqn_cnae_iss icss on icss.id_iss = ii.id_iss and icss.atividade_primaria = true "
                    + "\n inner join issqn_cnae cnae on cnae.id_cnae = icss.id_cnae "
                    + "\n where  "
                    + (inscricao != null && inscricao.length() != 0 ? "ii.inscricao=" + Util.quotarStr(inscricao) + " AND " : "")
                    + "\n MI.DT_CANCELADO IS NULL AND COALESCE(MP.CANCELADO_PAGAMENTO, 0) = 0 AND MP.DT_PAGAMENTO IS NULL "
                    + "\n AND II.ID_EXERCICIO = " + exercicio + " AND coalesce(MP.STATUS, 0) <> 2"
                    + "\n AND MI.ID_MOVIMENTO not in (SELECT distinct IMP2.ID_MOVIMENTO FROM ISSQN_MOVIMENTO_PARCELA IMP2"
                    + "\n INNER JOIN ISSQN_TIPO_COBRANCA TC2 ON TC2.ID_TIPO_COBRANCA = IMP2.ID_TIPO_COBRANCA"
                    + "\n WHERE IMP2.DT_PAGAMENTO IS NOT NULL AND"
                    + "\n COALESCE(IMP2.CANCELADO_PAGAMENTO, 0) = 0 AND TC2.QTD_PARCELA = 1"
                    + "\n AND TC2.TP_COBRANCA = 1 /*1 = ISS*/)"
                    + where
                    + "\n GROUP BY II.INSCRICAO, P.NOME, ALI.NOME, LI.NOME, II.DT_ALVARA,  EI.NR_IMOVEL, ABI.NOME, BI.NOME, ABC.NOME, BIC.NOME, MI.ID_MOVIMENTO, "
                    + "\n II.ALVARA_PROVISORIO, ALI.NOME, LIC.NOME, IC.NR_IMOVEL, II.ID_ISS,cnae.nome ";

            List<Object[]> lista = createNativeQuery(sql);

            for (Object[] o : lista) {
                HashMap field = new HashMap();

                String sql_2 = "SELECT T.DESCRICAO, CI.PARCELA, CI.DT_VENCIMENTO, CI.VL_PARCELA,"
                        + "\n CI.DT_PAGAMENTO, CI.VL_PAGO, CI.VL_JUROS, CI.VL_MULTA,CI.VL_TOTAL, "
                        + "\n CASE WHEN COALESCE(CI.STATUS,0) = 0 THEN 'ABERTO' WHEN COALESCE(CI.STATUS,0) = 2 THEN 'CANCELADO' ELSE 'PAGO' END SITUACAO"
                        + "\n FROM ISSQN I"
                        + "\n INNER JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = I.ID_ISS"
                        + "\n INNER JOIN ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO = MI.ID_MOVIMENTO"
                        + "\n LEFT JOIN BANCO B ON B.ID_BANCO = CI.ID_BANCO"
                        + "\n INNER JOIN ISSQN_TIPO_COBRANCA T ON T.ID_TIPO_COBRANCA = CI.ID_TIPO_COBRANCA "
                        + "\n WHERE T.ID_EXERCICIO = " + exercicio + " AND I.INSCRICAO =" + Util.quotarStr(o[0])
                        + "\n AND MI.DT_CANCELADO IS NULL AND COALESCE(CI.CANCELADO_PAGAMENTO, 0) = 0 AND CI.DT_PAGAMENTO IS NULL"
                        + "\n AND coalesce(CI.STATUS, 0) <> 2"
                        + "\n AND CI.VL_PARCELA > 0 ";

                List<Object[]> lista_2 = createNativeQuery(sql_2);

                for (Object[] obj : lista_2) {
                    field = new HashMap();
                    field.put("inscricao", o[0].toString());
                    field.put("nome", o[1].toString());//
                    field.put("endImovel", o[2] == null ? "" : o[2].toString());
                    field.put("dt_alvara", o[3] == null ? "" : (Date) o[3]);
                    field.put("bairro_imovel", o[5] == null ? "" : o[5].toString());
                    field.put("bairro_cobranca", o[6] == null ? "" : o[6].toString());
                    field.put("endCobranca", o[8] == null ? "" : o[8].toString());
                    field.put("id_iss", Integer.parseInt(o[10].toString()));
                    field.put("atividade", o[11].toString());
                    field.put("forma_pagamento", obj[0].toString());
                    field.put("parcela", Integer.parseInt(obj[1].toString()));
                    field.put("vencimento", Util.extractDate(obj[2]));
                    field.put("correcao", 0.0);//
                    field.put("valor", Util.extractDouble(obj[3].toString()));
                    field.put("pagamento", obj[4] == null ? null : Util.extractDate(obj[4]));
                    field.put("valor_pago", obj[5] == null ? 0 : Util.extractDouble(obj[5].toString()));
                    field.put("juros", obj[6] == null ? 0 : Util.extractDouble(obj[6].toString()));
                    field.put("multa", obj[7] == null ? 0 : Util.extractDouble(obj[7].toString()));
                    field.put("valor_total", obj[8] == null ? 0 : Util.extractDouble(obj[8].toString()));
                    field.put("pago", obj[9] == null ? "" : obj[9].toString());
                    lst.add(field);

                }
            }
            rpt.jasperReport("extratodebitos", "application/pdf", lst, p, "extratodebitos");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public void gerarCobranca(String orgaoId, String pesquisa, int exercicio, int ordem, Integer tipoCobrancaBoleto, Date vencimento) throws Exception {
        IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class, "select c from IssqnConfiguracao c ");
        if ((conf.getBoleto() != null && conf.getBoleto() == 1) || tipoCobrancaBoleto == 2) {
            if (tipoCobrancaBoleto != 0) {
                pesquisa += " AND TC.TP_COBRANCA = " + tipoCobrancaBoleto;
            }
            gerarBoleto(orgaoId, pesquisa, exercicio, ordem, vencimento);
        } else {
            if (tipoCobrancaBoleto == 0) {
                pesquisa += " AND TC.TP_COBRANCA <> 2";
            } else {
                pesquisa += " AND TC.TP_COBRANCA = " + tipoCobrancaBoleto;
            }
            gerarFebraban(orgaoId, pesquisa, exercicio, ordem, vencimento);
        }
    }

    public void gerarBoletoFebraban(String orgaoId, String pesquisa, int exercicio, int ordem, Integer tipoCobrancaBoleto, Date vencimento) throws Exception {
        if (tipoCobrancaBoleto != 0) {
            pesquisa += " AND TC.TP_COBRANCA = " + tipoCobrancaBoleto;
        }
        gerarFebraban(orgaoId, pesquisa, exercicio, ordem, vencimento);
    }

    private void gerarFebraban(String orgaoId, String pesquisa, int exercicio, int ordem, Date dt_vencimento) throws Exception {

        Map p = new HashMap();
        ArrayList<HashMap> lista = new ArrayList<>();
        ReportGenerator rpt = new ReportGenerator();
        Integer id_movimento = null;

        ContabilOrgao orgao = getEntidadePura(ContabilOrgao.class, "select c from ContabilOrgao c ");
        if (orgao == null) {
            throw new Exception("Informações do orgão não encontradas!");
        }
        byte[] logotipo_bytes = orgao.getBrasao();
        ImageIcon logotipo = new ImageIcon();
        if (logotipo_bytes != null) {
            logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans

        }

        IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class, "select c from IssqnConfiguracao c ");

        p.put("orgao", orgao.getNome().toUpperCase());
        p.put("brasao", logotipo.getImage());
        p.put("locaisPgto", orgao.getFebrabanLocal().toUpperCase());
        p.put("beneficiario", orgao.getNome().toUpperCase() + " - DEP. DESENV. ECONÔMICO," + orgao.getCnpj() + " \n" + orgao.getEndereco().toUpperCase() + " - "
                + orgao.getBairro().toUpperCase() + " - " + orgao.getCep() + " \n" + orgao.getCidade().toUpperCase() + " - " + orgao.getUf().toUpperCase());
        p.put("beneficiarioRodape", orgao.getNome().toUpperCase() + " - " + orgao.getCnpj() + "\n"
                + orgao.getEndereco().toUpperCase() + " - " + orgao.getBairro().toUpperCase() + " - " + orgao.getCep()
                + " - " + orgao.getCidade().toUpperCase() + " - " + orgao.getUf().toUpperCase());
        p.put("endereco_orgao", orgao.getEndereco() + " - " + orgao.getBairro());

        String sql = "SELECT DISTINCT II.ID_IMOVEL, II.ID_IMOVEL_COBRANCA, II.ID_PESSOA, II.INSCRICAO,\n"
                + "M.ID_MOVIMENTO, TP_MOVIMENTO, LC.NOME AS ENDERECO_IMOVEL, IMC.NR_IMOVEL AS NUMERO_IMOVEL, P.NOME, CC.NOME AS CIDADE_IMOVEL, BC.NOME AS BAIRRO_IMOVEL,\n"
                + "LE.NOME AS ENDERECO_ENTREGA, IE.NR_IMOVEL AS NUMERO_ENTREGA, CE.NOME AS CIDADE_ENTREGA, BE.NOME AS BAIRRO_ENTREGA,\n"
                + "IE.CEP AS CEP_ENTREGA, IE.COMPLEMENTO AS COMPLEMENTO_ENTREGA, CE.UF AS UF_ENTREGA, IMC.CEP AS CEP_IMOVEL,  CC.UF AS UF_IMOVEL,\n"
                + "II.ID_ISS,IMC.COMPLEMENTO, coalesce(B.ID_BANCO,0) as ID_BANCO, P.CPF_CNPJ,TC.TP_COBRANCA,IC1.NOME AS ATIVIDADE_PRIMARIA\n"
                + "FROM ISSQN_MOVIMENTO_PARCELA MP\n"
                + "INNER JOIN ISSQN_MOVIMENTO M on M.ID_MOVIMENTO = MP.ID_MOVIMENTO\n"
                + "INNER JOIN ISSQN_MOVIMENTO_PARCELA_ITEM MPI on MPI.ID_MOVIMENTO_PARCELA = MP.ID_MOVIMENTO_PARCELA\n"
                + "INNER JOIN ISSQN_TAXA T on T.ID_TAXA = MPI.ID_TAXA\n"
                + "left join BANCO B on B.ID_BANCO = T.ID_BANCO\n"
                + "INNER JOIN ISSQN II on II.ID_ISS = M.ID_ISS\n"
                + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                + "LEFT JOIN ISSQN_CNAE_ISS IA ON IA.ID_ISS = II.ID_ISS AND IA.ATIVIDADE_PRIMARIA = TRUE \n"
                + "LEFT JOIN ISSQN_CNAE IC1 ON IC1.ID_CNAE = IA.ID_CNAE\n"
                + "INNER JOIN IMOVEL IMC ON IMC.ID_IMOVEL = II.ID_IMOVEL\n"
                + "INNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA\n"
                + "INNER JOIN BAIRRO_LOGRADOURO BLC ON BLC.ID_BAIRROLOGRADOURO = IMC.ID_BAIRROLOGRADOURO\n"
                + "INNER JOIN BAIRRO BC ON BC.ID_BAIRRO = BLC.ID_BAIRRO AND BC.ID_CIDADE = BLC.ID_CIDADE\n"
                + "INNER JOIN LOGRADOURO LC ON LC.ID_LOGRADOURO = BLC.ID_LOGRADOURO AND LC.ID_CIDADE = BLC.ID_CIDADE\n"
                + "INNER JOIN CIDADE CC ON CC.ID_CIDADE = BLC.ID_CIDADE\n"
                + "LEFT JOIN IMOVEL IE ON IE.ID_IMOVEL = II.ID_IMOVEL_COBRANCA\n"
                + "LEFT JOIN BAIRRO_LOGRADOURO BLE ON BLE.ID_BAIRROLOGRADOURO = IE.ID_BAIRROLOGRADOURO\n"
                + "LEFT JOIN CIDADE CE ON CE.ID_CIDADE = BLE.ID_CIDADE\n"
                + "LEFT JOIN BAIRRO BE ON BE.ID_BAIRRO = BLE.ID_BAIRRO AND BE.ID_CIDADE = BLE.ID_CIDADE\n"
                + "LEFT JOIN LOGRADOURO LE ON LE.ID_LOGRADOURO = BLE.ID_LOGRADOURO AND LE.ID_CIDADE = BLE.ID_CIDADE\n"
                + "LEFT JOIN ABREVIATURA ALE ON ALE.ID_ABREVIATURA = LE.ID_ABREVIATURA\n"
                + "LEFT JOIN ABREVIATURA ABE ON ABE.ID_ABREVIATURA = BE.ID_ABREVIATURA\n"
                + "left join issqn_cnae_iss ICI on ICI.id_iss = II.id_iss\n"
                + "left join issqn_cnae IC on IC.id_cnae = ICI.id_cnae\n"
                + "WHERE MP.DT_PAGAMENTO IS NULL\n"
                + "AND II.DT_ENCERRAMENTO IS NULL AND II.DT_BLOQUEADO IS NULL AND II.DT_INATIVIDADE IS NULL\n"
                + "AND M.CANCELADO_MOVIMENTO = 0 and ICI.ATIVIDADE_PRIMARIA = TRUE\n"
                + "AND T.ID_BANCO IS NOT NULL\n"
                + "AND II.ID_EXERCICIO = " + exercicio + "\n"
                + pesquisa
                + "\n ORDER BY " + ordem;

        try {
            List<Object[]> lstObj = createNativeQuery(sql);
            for (Object[] obj : lstObj) {
                id_movimento = Util.extractInt(obj[4]);
                HashMap field = new HashMap();

                field.put("exercicio", exercicio);
                field.put("atividadePrimaria", Util.extractStr(obj[22]).toUpperCase());
                field.put("atividades", buscaAtividadesCobranca(Util.extractInt(obj[20])));
                field.put("inscricao", Util.extractStr(obj[3]));
                field.put("contribuinte", Util.extractStr(obj[8]));
                field.put("endereco_entrega", obj[11] == null ? Util.extractStr(obj[6]) : obj[11].toString());
                field.put("bairro_entrega", obj[14] == null ? Util.extractStr(obj[10]) : obj[14].toString());
                field.put("cidade_entrega", obj[13] == null ? Util.extractStr(obj[9]) : obj[13].toString());
                field.put("cep_entrega", obj[15] == null ? Util.extractStr(obj[18]) : obj[15].toString());
                field.put("complemento_entrega", obj[16] == null ? Util.extractStr(obj[21]) : obj[16].toString());
                field.put("uf_entrega", obj[17] == null ? Util.extractStr(obj[19]) : obj[17].toString());
                field.put("numero_entrega", obj[12] == null ? Util.extractStr(obj[7]) : obj[12].toString());
                field.put("endereco_imovel", Util.extractStr(obj[6]));
                field.put("bairro_imovel", Util.extractStr(obj[10]));
                field.put("numero_imovel", Util.extractStr(obj[7]));
                field.put("cidade_imovel", Util.extractStr(obj[9]));
                field.put("cep_imovel", Util.extractStr(obj[18]));
                field.put("uf_imovel", Util.extractStr(obj[19]));
                field.put("pagador", Util.extractStr(obj[8]));
                field.put("dataAtual", Util.dateToday());

                sql
                        = "select DISTINCT MP.PARCELA, MP.VL_PARCELA, MP.DT_VENCIMENTO, MP.NOSSO_NUMERO,\n"
                        + "II.ID_IMOVEL, II.ID_IMOVEL_COBRANCA, II.ID_PESSOA, II.INSCRICAO, TC.MENSAGEM,\n"
                        + "M.ID_MOVIMENTO, P.NOME AS PESSOA, TC.QTD_PARCELA, TC.BLOQUEIO\n"
                        + "FROM  ISSQN_MOVIMENTO_PARCELA MP\n"
                        + "INNER JOIN ISSQN_MOVIMENTO M on M.ID_MOVIMENTO = MP.ID_MOVIMENTO\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA_ITEM MPI on MPI.ID_MOVIMENTO_PARCELA = MP.ID_MOVIMENTO_PARCELA\n"
                        + "INNER JOIN ISSQN_TAXA T on T.ID_TAXA = MPI.ID_TAXA\n"
                        + "INNER JOIN ISSQN II on II.ID_ISS = M.ID_ISS\n"
                        + "INNER JOIN PESSOA P on P.ID_PESSOA = II.ID_PESSOA\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                        + "WHERE MP.DT_PAGAMENTO IS NULL "
                        + "AND M.ID_MOVIMENTO = " + id_movimento + pesquisa + "\n"
                        + "AND MP.DT_CANCELADO_PAGAMENTO IS NULL\n"
                        + "AND II.DT_ENCERRAMENTO IS NULL AND II.DT_BLOQUEADO IS NULL AND II.DT_INATIVIDADE IS NULL\n"
                        + "ORDER BY MP.PARCELA, TC.QTD_PARCELA, MP.DT_VENCIMENTO";

                List<Object[]> list = createNativeQuery(sql);
                String sql_parcelas_ativas = "select count(1) from ISSQN_MOVIMENTO_PARCELA MP inner join issqn ii on mp.id_iss = ii.id_iss "
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA "
                        + "where 1=1 AND ID_MOVIMENTO = " + id_movimento + pesquisa + " and mp.dt_cancelado_pagamento is null and tc.qtd_parcela > 1";
                List<Number> parcelas = createNativeQuery(sql_parcelas_ativas);

                HashMap fieldClone = (HashMap) field.clone();

                String valor_taxas_unica = "";
                double vl_total_unica = 0;
                String primeiro_vencimento = "";
                String ultimo_vencimento = "";
                for (Object[] o : list) {
                    //Preenche o boleto
                    String codigoBarras = montarCodigoBarras( Util.extractDouble(o[1].toString()), conf.getFebraban(), o[3].toString(), new Date(((java.sql.Date) o[2]).getTime()), o[12] != null ? (boolean) o[12] : false);
                    String inscricao = Util.extractStr(obj[3]);
                    Integer qtdParcela = Util.extractInt(o[11]);
                    field = new HashMap();
                    field.putAll(fieldClone);
                    field.put("numeroDocumento", Util.formatarDecimal("000000000000", Util.extractInt(o[3])));
                    field.put("codigoBaixa", Util.formatarDecimal("00000000000000000", id_movimento));
                    field.put("nomeTributo", "I.S.S.Q.N. E TAXAS");
                    field.put("formaPagamento", Integer.parseInt(o[11].toString()) > 1 ? "Parcelas" : "Parcela Única");
                    if (Util.extractInt(o[11]) == 1) { //Parcela Unica
                        field.put("parcela", Util.formatarDecimal("00", o[0]) + "/" + Util.formatarDecimal("00", Util.extractInt(o[11])));
                    } else { //Parcelado
                        field.put("parcela", Util.formatarDecimal("00", o[0]) + "/" + Util.formatarDecimal("00", parcelas.get(0).intValue() == 0 ? Util.extractInt(o[11]) : parcelas.get(0).intValue()));
                    }
                    field.put("vencimento", new Date(((java.sql.Date) o[2]).getTime()));
                    field.put("valorDocumento", Util.extractDouble(o[1]));
                    field.put("valorCobrado", Util.extractDouble(o[1]));
                    field.put("multa", null);
                    field.put("juros", null);
                    field.put("correcao", null);

                    if (dt_vencimento != null) {
                        double[] calculo_multa_juros = calculo(Util.extractDouble(o[1]), new Date(((java.sql.Date) o[2]).getTime()), dt_vencimento);
                        field.put("vencimento", dt_vencimento);
                        field.put("valorDocumento", calculo_multa_juros[0]);
                        field.put("valorCobrado", calculo_multa_juros[0]);
                    }
                    field.put("bar1", codigoBarras.substring(0, 11) + "-" + calcModulo10(codigoBarras.substring(0, 11)));
                    field.put("bar2", codigoBarras.substring(11, 22) + "-" + calcModulo10(codigoBarras.substring(11, 22)));
                    field.put("bar3", codigoBarras.substring(22, 33) + "-" + calcModulo10(codigoBarras.substring(22, 33)));
                    field.put("bar4", codigoBarras.substring(33, 44) + "-" + calcModulo10(codigoBarras.substring(33, 44)));
                    field.put("codigoBarras", codigoBarras);
                    field.put("especie", "Real");
                    field.put("linhas", o[8].toString());
                    lista.add(field);

                    //Preenche a capa
                    String endereco = obj[11] == null ? (Util.extractStr(obj[6]) + (obj[7] == null ? "" : ", " + Util.extractStr(obj[7])))
                            : (Util.extractStr(obj[11]) + (obj[12] == null ? "" : ", " + Util.extractStr(obj[12])));
                    if (endereco.equals(", ")) {
                        endereco = "";
                    }
                    String cep = Util.extractStr(obj[18]);
                    String bairro = Util.extractStr(obj[10]);
                    String cidade = Util.extractStr(obj[9]);
                    String estado = Util.extractStr(obj[19]);
                    String enderecoEntrega = obj[11] == null ? Util.extractStr(obj[6]) : Util.extractStr(obj[11]);
                    String numeroEntrega = obj[12] == null ? Util.extractStr(obj[7]) : Util.extractStr(obj[12]);
                    String complementoEntrega = Util.extractStr(obj[16]);
                    String cepEntrega = obj[15] == null ? Util.extractStr(obj[18]) : Util.extractStr(obj[15]);
                    String bairroEntrega = obj[14] == null ? Util.extractStr(obj[10]) : Util.extractStr(obj[14]);
                    String cidadeEntrega = obj[13] == null ? Util.extractStr(obj[9]) : Util.extractStr(obj[13]);
                    String ufEntrega = obj[17] == null ? Util.extractStr(obj[19]) : Util.extractStr(obj[17]);
                    p.put("endereco_entrega", enderecoEntrega);
                    p.put("numero_entrega", numeroEntrega);
                    p.put("complemento_entrega", complementoEntrega);
                    p.put("bairro_entrega", bairroEntrega);
                    p.put("cidade_entrega", cidadeEntrega);
                    p.put("uf_entrega", ufEntrega);
                    p.put("cep_entrega", cepEntrega);
                    p.put("corpo_capa", endereco + "  " + bairro + "  " + cidade + "/" + estado + "  CEP: " + cep);
                    p.put("capa_endereco", enderecoEntrega + "," + numeroEntrega + "  " + bairroEntrega + "  " + cidadeEntrega + "/" + ufEntrega + "  CEP: " + cepEntrega);
                    p.put("atividade_primaria", Util.extractStr(obj[25]).toUpperCase());
                    p.put("atividade", buscaAtividadesCobranca(Util.extractInt(obj[20])));
                    sql = "select t.nome,mpi.vl_taxa from issqn_movimento_parcela mp\n"
                            + "inner join issqn_movimento_parcela_item mpi on mp.id_movimento_parcela = mpi.id_movimento_parcela \n"
                            + "inner join issqn_taxa t on mpi.id_taxa = t.id_taxa\n"
                            + "where mp.nosso_numero = " + Util.extractStr(o[3]) + " order by 1";
                    List<Object[]> taxas = createNativeQuery(sql);
                    String taxa = "";
                    String vlTaxa = "";
                    String inscricao_taxa = "";
                    String exercicio_taxa = "";
                    double vl_total_parc = 0;

                    for (Object[] t : taxas) {
                        if (qtdParcela == 1) {
                            valor_taxas_unica += Util.formatarDecimal("#0.00", Util.extractDouble(t[1])) + "\n";
                            vl_total_unica += Util.extractDouble(t[1]);
                            primeiro_vencimento = Util.parseSqlToBrDate(new Date(((java.sql.Date) o[2]).getTime()));
                        }
                        taxa += Util.extractStr(t[0]) + "\n";
                        vlTaxa += Util.formatarDecimal("#0.00", Util.extractDouble(t[1])) + "\n";
                        inscricao_taxa += inscricao + "\n";
                        exercicio_taxa += exercicio + "\n";
                        vl_total_parc += Util.extractDouble(t[1]);
                    }

                    ultimo_vencimento = Util.parseSqlToBrDate(new Date(((java.sql.Date) o[2]).getTime()));
                    p.put("taxas", taxa);
                    p.put("valor_taxas", vlTaxa);
                    p.put("valor_taxas_unica", valor_taxas_unica);
                    p.put("inscricao_taxa", inscricao_taxa);
                    p.put("exercicio_taxa", exercicio_taxa);
                    p.put("primeiro_vencimento", primeiro_vencimento);
                    p.put("ultimo_vencimento", ultimo_vencimento);
                    p.put("qtd_parcelas", (parcelas.get(0).intValue() == 0 ? Util.extractInt(o[11]) : parcelas.get(0).intValue()));
                    p.put("vl_total_unica", vl_total_unica);
                    p.put("vl_total_parc", vl_total_parc);
                    p.put("dt_doc", Util.parseSqlToBrDate(new Date()));

                }

            }
            rpt.jasperReport("carne_febraban_novo", "application/pdf", lista, p, "carne_febraban_novo");

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao emitir boleto\n" + ex.getMessage());
        }
    }

    private void gerarBoleto(String orgaoId, String pesquisa, int exercicio, int ordem, Date dt_vencimento) throws Exception {

        ReportGenerator rpt = new ReportGenerator();
        Integer id_movimento = null;

        ContabilOrgao orgao = getEntidadePura(ContabilOrgao.class, "select c from ContabilOrgao c ");
        if (orgao == null) {
            throw new Exception("Informações do orgão não encontradas!");
        }
        byte[] logotipo_bytes = orgao.getBrasao();
        ImageIcon logotipo = new ImageIcon();
        if (logotipo_bytes != null) {
            logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans

        }

        IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class, "select c from IssqnConfiguracao c ");
        Map parametros = new HashMap();
        List<BoletoBeanJasper> lstBoletos = new ArrayList<BoletoBeanJasper>();

        String sql = "SELECT DISTINCT II.ID_IMOVEL, II.ID_IMOVEL_COBRANCA, II.ID_PESSOA, II.INSCRICAO,\n"
                + "M.ID_MOVIMENTO, TP_MOVIMENTO, LC.NOME AS ENDERECO_IMOVEL, IMC.NR_IMOVEL AS NUMERO_IMOVEL, P.NOME, CC.NOME AS CIDADE_IMOVEL, BC.NOME AS BAIRRO_IMOVEL,\n"
                + "LE.NOME AS ENDERECO_ENTREGA, IE.NR_IMOVEL AS NUMERO_ENTREGA, CE.NOME AS CIDADE_ENTREGA, BE.NOME AS BAIRRO_ENTREGA,\n"
                + "IE.CEP AS CEP_ENTREGA, IE.COMPLEMENTO AS COMPLEMENTO_ENTREGA, CE.UF AS UF_ENTREGA, IMC.CEP AS CEP_IMOVEL,  CC.UF AS UF_IMOVEL,\n"
                + "II.ID_ISS,IMC.COMPLEMENTO, coalesce(B.ID_BANCO,0) as ID_BANCO, P.CPF_CNPJ,TC.TP_COBRANCA,IC1.NOME AS ATIVIDADE_PRIMARIA\n"
                + "FROM ISSQN_MOVIMENTO_PARCELA MP\n"
                + "INNER JOIN ISSQN_MOVIMENTO M on M.ID_MOVIMENTO = MP.ID_MOVIMENTO\n"
                + "INNER JOIN ISSQN_MOVIMENTO_PARCELA_ITEM MPI on MPI.ID_MOVIMENTO_PARCELA = MP.ID_MOVIMENTO_PARCELA\n"
                + "INNER JOIN ISSQN_TAXA T on T.ID_TAXA = MPI.ID_TAXA\n"
                + "left join BANCO B on B.ID_BANCO = T.ID_BANCO\n"
                + "INNER JOIN ISSQN II on II.ID_ISS = M.ID_ISS\n"
                + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                + "LEFT JOIN ISSQN_CNAE_ISS IA ON IA.ID_ISS = II.ID_ISS AND IA.ATIVIDADE_PRIMARIA = TRUE \n"
                + "LEFT JOIN ISSQN_CNAE IC1 ON IC1.ID_CNAE = IA.ID_CNAE\n"
                + "INNER JOIN IMOVEL IMC ON IMC.ID_IMOVEL = II.ID_IMOVEL\n"
                + "INNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA\n"
                + "INNER JOIN BAIRRO_LOGRADOURO BLC ON BLC.ID_BAIRROLOGRADOURO = IMC.ID_BAIRROLOGRADOURO\n"
                + "INNER JOIN BAIRRO BC ON BC.ID_BAIRRO = BLC.ID_BAIRRO AND BC.ID_CIDADE = BLC.ID_CIDADE\n"
                + "INNER JOIN LOGRADOURO LC ON LC.ID_LOGRADOURO = BLC.ID_LOGRADOURO AND LC.ID_CIDADE = BLC.ID_CIDADE\n"
                + "INNER JOIN CIDADE CC ON CC.ID_CIDADE = BLC.ID_CIDADE\n"
                + "LEFT JOIN IMOVEL IE ON IE.ID_IMOVEL = II.ID_IMOVEL_COBRANCA\n"
                + "LEFT JOIN BAIRRO_LOGRADOURO BLE ON BLE.ID_BAIRROLOGRADOURO = IE.ID_BAIRROLOGRADOURO\n"
                + "LEFT JOIN CIDADE CE ON CE.ID_CIDADE = BLE.ID_CIDADE\n"
                + "LEFT JOIN BAIRRO BE ON BE.ID_BAIRRO = BLE.ID_BAIRRO AND BE.ID_CIDADE = BLE.ID_CIDADE\n"
                + "LEFT JOIN LOGRADOURO LE ON LE.ID_LOGRADOURO = BLE.ID_LOGRADOURO AND LE.ID_CIDADE = BLE.ID_CIDADE\n"
                + "LEFT JOIN ABREVIATURA ALE ON ALE.ID_ABREVIATURA = LE.ID_ABREVIATURA\n"
                + "LEFT JOIN ABREVIATURA ABE ON ABE.ID_ABREVIATURA = BE.ID_ABREVIATURA\n"
                + "WHERE MP.DT_PAGAMENTO IS NULL\n"
                + "AND T.NOME NOT LIKE '%EXPEDIENTE%'\n"
                + "AND II.DT_ENCERRAMENTO IS NULL AND II.DT_BLOQUEADO IS NULL AND II.DT_INATIVIDADE IS NULL\n"
                + "AND M.CANCELADO_MOVIMENTO = 0\n"
                + "AND T.ID_BANCO IS NOT NULL\n"
                + "AND II.ID_EXERCICIO = " + exercicio + "\n"
                + pesquisa
                + "\nORDER BY " + ordem;

        try {
            List<Object[]> lstObj = createNativeQuery(sql);
            for (Object[] obj : lstObj) {
                if (Util.extractInt(obj[22]) == 0 && conf.getBancoBoleto() == null) {
                    continue;
                }
                Banco banco2 = (Util.extractInt(obj[22]) == 0 ? conf.getBancoBoleto() : getEntidade(Banco.class, Util.extractInt(obj[22])));
                String agencia = banco2.getAgencia();
                if (agencia.length() > 4) {
                    agencia = Util.formatarDecimal("0000", new Integer(agencia.trim()));
                }
                br.com.eddydata.boleto.Banco banco = null;
                // <editor-fold defaultstate="collapsed" desc="procura de banco">
                switch (Integer.parseInt(banco2.getNumero())) {
                    case 1:
                        banco = new BancoBrasil();
                        break;
                    case 356:
                        banco = new BancoReal();
                        break;
                    case 756:
                        banco = new Bancoob();
                        break;
                    case 237:
                        banco = new Bradesco();
                        break;
                    case 104:
                        banco = new CaixaEconomicaNovo();
                        break;
                    case 399:
                        banco = new Hsbc();
                        break;
                    case 341:
                        banco = new Itau();
                        break;
                    case 151:
                        banco = new NossaCaixa();
                        break;
                    case 33:
                        banco = new SantanderBanespa();
                        break;
                    //            case 409:
                    //               new Unibanco();
                    //                break;
                }
                // </editor-fold>
                br.com.eddydata.boleto.Relatorio relatorio = new br.com.eddydata.boleto.Relatorio(banco, 2);

                String dvagencia = banco2.getDvAgencia();
                String contacorrente = banco2.getConta();
                String dvcontacorrente = banco2.getDvConta();
                String numero = banco2.getNumero();
                String numero_convenio = banco2.getNumeroConvenio();
                String finalidade = "";
                switch (Util.extractInt(obj[24])) {
                    case 1:
                        finalidade = "TCF";
                        break;
                    case 2:
                        finalidade = "VIGILANCIA";
                        break;
                    default:
                        finalidade = "EVENTUAL";
                        break;
                }
//                String corpo_boleto = "FINALIDADE: " + finalidade + "\n" + banco2.getCorpoBoleto();
                String corpo_boleto = banco2.getCorpoBoleto() + "\n";
                String local_pagto1 = banco2.getBoletoLocalPagto1();
                String local_pagto2 = banco2.getBoletoLocalPagto2();
                String carteira = banco2.getBoletoCarteira();
                String especie_documento = banco2.getBoletoEspecieDocumento();
                id_movimento = Util.extractInt(obj[4]);

                sql
                        = "select DISTINCT MP.PARCELA, MP.VL_PARCELA, MP.DT_VENCIMENTO, MP.NOSSO_NUMERO,\n"
                        + "II.ID_IMOVEL, II.ID_IMOVEL_COBRANCA, II.ID_PESSOA, II.INSCRICAO, TC.MENSAGEM,\n"
                        + "M.ID_MOVIMENTO, P.NOME AS PESSOA, TC.QTD_PARCELA\n"
                        + "FROM  ISSQN_MOVIMENTO_PARCELA MP\n"
                        + "INNER JOIN ISSQN_MOVIMENTO M on M.ID_MOVIMENTO = MP.ID_MOVIMENTO\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA_ITEM MPI on MPI.ID_MOVIMENTO_PARCELA = MP.ID_MOVIMENTO_PARCELA\n"
                        + "INNER JOIN ISSQN_TAXA T on T.ID_TAXA = MPI.ID_TAXA\n"
                        + "INNER JOIN ISSQN II on II.ID_ISS = M.ID_ISS\n"
                        + "INNER JOIN PESSOA P on P.ID_PESSOA = II.ID_PESSOA\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                        + "WHERE MP.DT_PAGAMENTO IS NULL "
                        + "AND M.ID_MOVIMENTO = " + id_movimento + pesquisa + "\n"
                        + "AND MP.DT_CANCELADO_PAGAMENTO IS NULL\n"
                        + "AND II.DT_ENCERRAMENTO IS NULL AND II.DT_BLOQUEADO IS NULL AND II.DT_INATIVIDADE IS NULL\n"
                        + "ORDER BY MP.PARCELA, TC.QTD_PARCELA, MP.DT_VENCIMENTO";

                List<Object[]> list = createNativeQuery(sql);
                String sql_parcelas_ativas = "select count(1) from ISSQN_MOVIMENTO_PARCELA MP inner join issqn ii on mp.id_iss = ii.id_iss "
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA "
                        + "where 1=1 AND ID_MOVIMENTO = " + id_movimento + pesquisa + " and mp.dt_cancelado_pagamento is null and tc.qtd_parcela > 1";
                List<Number> parcelas = createNativeQuery(sql_parcelas_ativas);

                String valor_taxas_unica = "";
                double vl_total_unica = 0;
                String primeiro_vencimento = "";
                String ultimo_vencimento = "";
                for (Object[] o : list) {
                    String cliente_nome = Util.extractStr(obj[8]);
                    String inscricao = Util.extractStr(obj[3]);
                    String endereco = obj[11] == null ? (Util.extractStr(obj[6]) + (obj[7] == null ? "" : ", " + Util.extractStr(obj[7])))
                            : (Util.extractStr(obj[11]) + (obj[12] == null ? "" : ", " + Util.extractStr(obj[12])));
                    String enderecoEntrega = obj[11] == null ? Util.extractStr(obj[6]) : Util.extractStr(obj[11]);
                    String numeroEntrega = obj[12] == null ? Util.extractStr(obj[7]) : Util.extractStr(obj[12]);
                    if (endereco.equals(", ")) {
                        endereco = "";
                    }
                    String cep = Util.extractStr(obj[18]);
                    String bairro = Util.extractStr(obj[10]);
                    String cidade = Util.extractStr(obj[9]);
                    String estado = Util.extractStr(obj[19]);
                    String complementoEntrega = Util.extractStr(obj[16]);
                    String cepEntrega = obj[15] == null ? Util.extractStr(obj[18]) : Util.extractStr(obj[15]);
                    String bairroEntrega = obj[14] == null ? Util.extractStr(obj[10]) : Util.extractStr(obj[14]);
                    String cidadeEntrega = obj[13] == null ? Util.extractStr(obj[9]) : Util.extractStr(obj[13]);
                    String ufEntrega = obj[17] == null ? Util.extractStr(obj[19]) : Util.extractStr(obj[17]);
                    parametros.put("endereco_entrega", enderecoEntrega);
                    parametros.put("numero_entrega", numeroEntrega);
                    parametros.put("complemento_entrega", complementoEntrega);
                    parametros.put("bairro_entrega", bairroEntrega);
                    parametros.put("cidade_entrega", cidadeEntrega);
                    parametros.put("uf_entrega", ufEntrega);
                    parametros.put("cep_entrega", cepEntrega);
                    String cpf_cnpj = Util.extractStr(obj[23]);
                    Integer qtdParcela = Util.extractInt(o[11]);

                    BoletoBean boleto = new BoletoBean();
                    boleto.setAgencia(agencia); // sem digito
                    boleto.setDvAgencia(dvagencia.trim());
                    boleto.setExercicio(exercicio);
                    boleto.setInscricao(Util.extractStr(obj[3]));
                    boleto.setCorpo_capa(endereco + "  " + bairro + "  " + cidade + "/" + estado + "  CEP: " + cep);
                    parametros.put("capa_endereco", enderecoEntrega + "," + numeroEntrega + "  " + bairroEntrega + "  " + cidadeEntrega + "/" + ufEntrega + "  CEP: " + cepEntrega);
                    boleto.setAtividade_primaria(Util.extractStr(obj[25]).toUpperCase());
                    boleto.setAtividade(buscaAtividadesCobranca(Util.extractInt(obj[20])));
                    boleto.setContribuinte(Util.extractStr(obj[8]));

                    boleto.setContaCorrente(/*contacorrente.substring(0, 2) + " " +*/contacorrente); // modalidade(2) + conta(6)
                    boleto.setDvContaCorrente(dvcontacorrente); // digito
                    boleto.setMoeda("9"); // 9 = real
                    boleto.setCedente(conf.getOrgao().getNome() + " " + conf.getOrgao().getCnpj());
                    boleto.setNumConvenio(numero_convenio);
                    boleto.setCarteira(carteira);// exigencia do banco
                    boleto.setLocalPagamento(local_pagto1);
                    boleto.setLocalPagamento2(local_pagto2);

                    corpo_boleto = corpo_boleto + "\n" + Util.extractStr(o[8]);
                    sql = "select t.nome,mpi.vl_taxa from issqn_movimento_parcela mp\n"
                            + "inner join issqn_movimento_parcela_item mpi on mp.id_movimento_parcela = mpi.id_movimento_parcela \n"
                            + "inner join issqn_taxa t on mpi.id_taxa = t.id_taxa\n"
                            + "where mp.nosso_numero = " + Util.extractStr(o[3]) + " order by 1";
                    List<Object[]> taxas = createNativeQuery(sql);
                    String taxa = "";
                    String vlTaxa = "";
                    String inscricao_taxa = "";
                    String exercicio_taxa = "";
                    double vl_total_parc = 0;
                    corpo_boleto = banco2.getCorpoBoleto() + "\n";
                    for (Object[] t : taxas) {
                        if (qtdParcela == 1) {
                            valor_taxas_unica += Util.formatarDecimal("#0.00", Util.extractDouble(t[1])) + "\n";
                            vl_total_unica += Util.extractDouble(t[1]);
                            primeiro_vencimento = Util.parseSqlToBrDate(new Date(((java.sql.Date) o[2]).getTime()));
                        }
                        taxa += Util.extractStr(t[0]) + "\n";
                        vlTaxa += Util.formatarDecimal("#0.00", Util.extractDouble(t[1])) + "\n";
                        inscricao_taxa += inscricao + "\n";
                        exercicio_taxa += exercicio + "\n";
                        vl_total_parc += Util.extractDouble(t[1]);
                    }

                    ultimo_vencimento = Util.parseSqlToBrDate(new Date(((java.sql.Date) o[2]).getTime()));
                    parametros.put("taxas", taxa);
                    parametros.put("valor_taxas", vlTaxa);
                    parametros.put("valor_taxas_unica", valor_taxas_unica);
                    parametros.put("inscricao_taxa", inscricao_taxa);
                    parametros.put("exercicio_taxa", exercicio_taxa);
                    parametros.put("primeiro_vencimento", primeiro_vencimento);
                    parametros.put("ultimo_vencimento", ultimo_vencimento);
                    parametros.put("qtd_parcelas", (parcelas.get(0).intValue() == 0 ? Util.extractInt(o[11]) : parcelas.get(0).intValue()));
                    parametros.put("vl_total_unica", vl_total_unica);
                    parametros.put("vl_total_parc", vl_total_parc);
                    boleto.setInstrucao1(corpo_boleto);

                    boleto.setEspecieDocumento(especie_documento);

                    boleto.setDataDocumento(Util.parseSqlToBrDate(new Date()));
                    boleto.setDataProcessamento(Util.parseSqlToBrDate(new Date()));
                    boleto.setDataVencimento(Util.parseSqlToBrDate(new Date(((java.sql.Date) o[2]).getTime())));
                    String nossonumero = Util.extractInt(o[3]) + "";

                    int tamanho;
                    if (banco instanceof CaixaEconomicaNovo) {
                        tamanho = 15;
                    } else if (banco instanceof Hsbc) {
                        tamanho = 13;
                    } else if (banco instanceof BancoBrasil) {
                        tamanho = 10;
                    } else if (banco instanceof Bradesco) {
                        tamanho = 11;
                    } else {
                        tamanho = 7;
                    }

                    boleto.setNossoNumero(nossonumero, tamanho);

                    boleto.setNomeSacado(cliente_nome);
                    boleto.setEnderecoSacado(endereco);
                    boleto.setBairroSacado(bairro);
                    boleto.setCidadeSacado(cidade);
                    boleto.setUfSacado(estado);
                    boleto.setCepSacado(cep);
                    boleto.setCpfSacado(cpf_cnpj);
                    boleto.setDocumento(inscricao + "-" + Util.extractStr(obj[10]) + "/" + (parcelas.get(0).intValue() == 0 ? Util.extractInt(o[11]) : parcelas.get(0).intValue()));
                    boleto.setParcela(Integer.parseInt(o[11].toString()) == 1 ? "UNICA" : Util.extractStr(o[0]) + "/" + (parcelas.get(0).intValue() == 0 ? Util.extractInt(o[11]) : parcelas.get(0).intValue()));
                    boleto.setAcrescimo(0.0 + "");
                    boleto.setValorBoleto(Util.extractDouble(o[1]));

                    if (dt_vencimento != null) {
                        double[] calculo_multa_juros = calculo(Util.extractDouble(o[1]), new Date(((java.sql.Date) o[2]).getTime()), dt_vencimento);
                        boleto.setDataVencimento(Util.parseSqlToBrDate(dt_vencimento));
                        boleto.setValorBoleto(calculo_multa_juros[0]);
                    }

                    relatorio.addBoleto(boleto);
                    //-- PARAMETROS
                    parametros.put("orgao", orgao.getNome());
                    parametros.put("estado", "");
                    parametros.put("titulo", "Recibo de Lançamentos");
                    parametros.put("endereco_orgao", orgao.getEndereco() + " - " + orgao.getBairro());
                    try {
                        byte[] brasao_bytes = orgao.getBrasao();
                        ImageIcon brasao = new ImageIcon();
                        if (brasao_bytes != null) {
                            brasao.setImage(Toolkit.getDefaultToolkit().createImage(brasao_bytes));
                        }
                        parametros.put("brasao", brasao.getImage());
                    } catch (Exception e) {

                    }
                }

                lstBoletos.addAll(relatorio.getBoletos());
            }
            rpt.jasperReport("boleto2", "application/pdf", lstBoletos, parametros, "boleto2");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Dados bancários inválidos");
        }
    }

    private double[] calculo(double vl_parcela, Date dt_vencimento_parc, Date dt_vencimento) {
        double[] valor_calculo = new double[4];
        double valor_multa = 0.0;
        double valor_juro = 0.0;
        double valor_correcao = 0.0;
        Long dia = Funcao.diferencaDatas(Util.parseSqlToBrDate(dt_vencimento_parc), (Util.parseSqlToBrDate(dt_vencimento)));

        IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class, "select c from IssqnConfiguracao c ");

        try {
            if (dia > 0) {
                int mes;
                double multa = conf.getVlMulta();
                double juro = conf.getVlJuros();

                valor_multa = Util.truncarValor((vl_parcela * multa) / 100 + 0.005, 2);
                mes = (int) Funcao.arredondar((dia / 30.0), 0, 0);

                double vl_juro;
                if (mes == 1) {
                    vl_juro = (juro / 30) * dia;
                } else {
                    vl_juro = (mes * juro);
                }

                valor_juro = Util.truncarValor((vl_parcela * vl_juro) / 100 + 0.005, 2);
                valor_correcao = 0;
                vl_parcela += (valor_juro > 0 ? valor_juro : 0) + valor_multa + valor_correcao;
            }
        } catch (Exception e) {
            Util.erro("Falha ao realizar cálculo.", e);
        }

        valor_calculo[0] = vl_parcela;
        valor_calculo[1] = valor_multa;
        valor_calculo[2] = valor_juro;
        valor_calculo[3] = valor_correcao;

        return valor_calculo;

    }

    private String montarCodigoBarras(double valor, int febraban, String nosso_numero, Date vencimento, boolean bloqueio) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        String valorFebraban = df.format(valor).replace(",", "").replace(".", "");
        valorFebraban = Util.Texto.alinharDireita(valorFebraban, 11).replaceAll(" ", "0");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataVencimento = sdf.format(vencimento);

        nosso_numero = Util.Texto.alinharDireita(nosso_numero, 14).replaceAll(" ", "0");

        String codigobarras = "8" + "1" + (!bloqueio? "7" : "6") + valorFebraban + Util.formatarDecimal("0000", febraban) + dataVencimento + nosso_numero + "020";
        codigobarras = "8" + "1" + (!bloqueio ? "7" : "6") + calcModulo10(codigobarras) + valorFebraban + Util.formatarDecimal("0000", febraban) + dataVencimento + nosso_numero + "020";

        return codigobarras;
    }

    private int calcModulo10(String seq0) {
        char[] charCodigoDeBarras = seq0.toCharArray();
        int[] seq1 = new int[charCodigoDeBarras.length];
        for (int i = 0; i < charCodigoDeBarras.length; i++) {
            seq1[i] = Integer.parseInt(String.valueOf(charCodigoDeBarras[i]));
        }

        int[] seq2 = new int[seq1.length];
        int result = 0;
        boolean flag = false;

        for (int i = seq1.length - 1; i >= 0; i--) {// for para preencher o valor da sequencia 2 conforme o tamanho passado
            if (flag) {
                seq2[i] = 1;
            } else {
                seq2[i] = 2;
            }
            flag = !flag;
        }
        for (int i = 0; i < seq1.length; i++) {// multiplicado um vetor pelo outro
            int temp = seq1[i] * seq2[i];
            if (temp >= 10) {// se for maior que 10 tem que quebrar
                result += temp / 10 + temp % 10;
            } else {
                result += temp;
            }
        }
        result = 10 - (result % 10);// pego o resto da divisï¿½o do valor calculado acima por 10 e subtrai de 10
        if (result == 10) {
            result = 0;
        }

        return result;
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

    /**
     *
     * Método que faz o contraste
     *
     * @param paramContraste
     * @return
     */
    public BufferedImage doContrast(ImageIcon imagem) {

        Image image = imagem.getImage();
        BufferedImage buffered = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffered.createGraphics();
        g.drawImage(image, 0, 0, null);
        BufferedImage index = convertType(buffered, BufferedImage.TYPE_BYTE_INDEXED);
        float scaleFactor = 0.5f;
        float offset = 1;
        return rescale(index, scaleFactor, offset);
    }

    /**
     *
     * Cria um objeto IndexColorModel
     *
     * @param indexed
     * @param scaleFactor
     * @param offset
     * @return
     */
    public static BufferedImage rescale(BufferedImage indexed, float scaleFactor, float offset) {
        IndexColorModel icm = (IndexColorModel) indexed.getColorModel();
        return new BufferedImage(rescale(icm, scaleFactor, offset), indexed.getRaster(), false, null);
    }

    /**
     *
     * Separa por cores verde, vermelha e azul
     *
     * @param icm
     * @param scaleFactor
     * @param offset
     * @return
     */
    public static IndexColorModel rescale(IndexColorModel icm, float scaleFactor, float offset) {
        int size = icm.getMapSize();
        byte[] reds = new byte[size], greens = new byte[size], blues = new byte[size], alphas = new byte[size];
        icm.getReds(reds);
        icm.getGreens(greens);
        icm.getBlues(blues);
        icm.getAlphas(alphas);
        rescale(reds, scaleFactor, offset);
        rescale(greens, scaleFactor, offset);
        rescale(blues, scaleFactor, offset);
        return new IndexColorModel(8, size, reds, greens, blues, alphas);
    }

    /**
     *
     * Executa a reescala de cores
     *
     * @param comps
     * @param scaleFactor
     * @param offset
     */
    public static void rescale(byte[] comps, float scaleFactor, float offset) {
        for (int i = 0; i < comps.length; ++i) {
            int comp = 0xff & comps[i];
            int newComp = Math.round(comp * scaleFactor + offset);
            if (newComp < 0) {
                newComp = 0;
            } else if (newComp > 255) {
                newComp = 255;
            }
            comps[i] = (byte) newComp;
        }
    }

    /**
     *
     * Converte o tipo da imagem
     *
     * @param image
     * @param type
     * @return
     */
    public static BufferedImage convertType(BufferedImage image, int type) {
        if (image.getType() == type) {
            return image;
        }
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), type);
        Graphics2D g = result.createGraphics();
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g.setComposite(alphaChannel);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private Image adicionaMarcaDagua(byte[] imagem) throws IOException {

        File imagemAlterada = File.createTempFile("marcaDagua", ".png");

        ByteArrayInputStream bais2 = new ByteArrayInputStream(imagem);
        // Lê a imagem e redimensiona
        BufferedImage marcaDagua = resize(ImageIO.read(bais2), 150, 150);

        // Tipo de imagem PNG
        int imageType = BufferedImage.TYPE_INT_ARGB;
        BufferedImage watermarked = new BufferedImage(marcaDagua.getWidth(), marcaDagua.getHeight(), imageType);

        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        // Determina o nível de transparencia
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
        w.setComposite(alphaChannel);

        w.drawImage(marcaDagua, 0, 0, null);
        w.dispose();
        ImageIO.write(watermarked, "png", imagemAlterada);
        Image retorno = ImageIO.read(imagemAlterada);
        imagemAlterada.delete();

        return retorno;
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void imprimirDocumentoAutenticado(String chave) throws BusinessViolation {
        try {
            FacesContext fcontext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fcontext.getExternalContext().getResponse();
            LogEmissao logEmissao = getEntidadePura(LogEmissao.class, "select l from LogEmissao l where l.chave = ?1", chave);
            if (logEmissao == null) {
                throw new BusinessViolation("Documento não encontrado no sistema");
            }

            response.setContentType("application/pdf");
            ServletOutputStream out = response.getOutputStream();

            fcontext.responseComplete();

            response.setHeader(
                    "content-disposition",
                    "inline; name=\"" + logEmissao.getChave() + "\"; "
                    + "filename=\"" + logEmissao.getChave() + ".pdf\"");
            response.setHeader(
                    "Cache-Control", "no-cache");
            out.write(logEmissao.getArquivo());
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(IssqnRepositorio.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String validaChaveAutenticao(String chave) {
        String sql_chave
                = "SELECT count(CHAVE) FROM LOG_EMISSAO  \n"
                + "WHERE CHAVE = " + Util.quotarStr(chave);
        List<Number> lstObj = createNativeQuery(sql_chave);
        if (lstObj.get(0).intValue() >= 1) {
            chave = Funcao.chaveAutenticacao();
            validaChaveAutenticao(chave);
        }
        return chave;
    }

    public class Taxa {

        private String id_taxa = "";
        private String nome = "";
        private double vl_taxa = 0.0;

        public String getId_taxa() {
            return id_taxa;
        }

        public void setId_taxa(String id_taxa) {
            this.id_taxa = id_taxa;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public double getVl_taxa() {
            return vl_taxa;
        }

        public void setVl_taxa(double vl_taxa) {
            this.vl_taxa = vl_taxa;
        }
    }
}
