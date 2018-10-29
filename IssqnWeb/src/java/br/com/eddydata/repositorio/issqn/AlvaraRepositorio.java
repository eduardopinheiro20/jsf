/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.geral.LogEmissao;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnAlvaraEmissao;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public class AlvaraRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;
    private boolean parc_alvara = false;
    private boolean parc_iss = false;

    public AlvaraRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public void imprimirAlvara(String orgaoId, String ordem, String pesquisa,
            Issqn contribuinte, String data_formatada, Integer exercicio, Date validade, String tipo, String usuario, String observacao)
            throws RuntimeException {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c "
                    + "\n where c.orgao.idOrgao = ?1 "
                    + "\norder by c.id desc", orgaoId
            );
            if (conf == null) {
                throw new Exception("Informações do parâmetros não encontradas!");
            }

            int n_protocolo = 0;
            String protocoloManual = "";
            if (contribuinte.getProtocoloAutonomo() == null && contribuinte.getProtocoloAutonomoDomicilioFiscal() == null
                    && contribuinte.getProtocoloDiversoes() == null) {
                n_protocolo = Util.extractInt(conf.getSequenciaNProtocolo());
            } else if (tipo.equals("Licenciamento para Autônomo") && !contribuinte.getProtocoloAutonomo().isEmpty()) {
                protocoloManual = contribuinte.getProtocoloAutonomo();
            } else if (tipo.equals("Licenciamento para Autônomo Domicílio Fiscal") && !contribuinte.getProtocoloAutonomoDomicilioFiscal().isEmpty()) {
                protocoloManual = contribuinte.getProtocoloAutonomoDomicilioFiscal();
            } else if (tipo.equals("Licenciamento para Diversões Públicas") && !contribuinte.getProtocoloDiversoes().isEmpty()) {
                protocoloManual = contribuinte.getProtocoloDiversoes();
            } else if (tipo.equals("Licenciamento de Funcionamento") && !contribuinte.getProtocoloFuncionamento().isEmpty()) {
                protocoloManual = contribuinte.getProtocoloFuncionamento();
            } else if (tipo.equals("Licenciamento Provisório") && !contribuinte.getProtocoloProvisorio().isEmpty()) {
                protocoloManual = contribuinte.getProtocoloProvisorio();
            } else {
                n_protocolo = Util.extractInt(conf.getSequenciaNProtocolo());
            }

            int numero_alvara = (Util.extractInt(conf.getSequenciaNAlvara()));

            byte[] logotipo_bytes = orgao.getLogotipo();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
            }
            byte[] brasao_bytes = orgao.getBrasao();
            ImageIcon brasao = new ImageIcon();
            if (brasao_bytes != null) {
                brasao.setImage(Toolkit.getDefaultToolkit().createImage(brasao_bytes));
            }
            String linha1 = "CNPJ: " + (orgao.getCnpj() == null ? "" : Util.mask("##-###-###/####-##", orgao.getCnpj()));
            String linha2 = orgao.getEndereco() + " - " + orgao.getBairro() + " - CEP "
                    + (orgao.getCep() == null ? "" : Util.mask("##.###-###", orgao.getCep()));
            String linha3 = "Fones/Contato: " + (orgao.getTelefone() == null ? "" : Util.mask("(##) ####-####", orgao.getTelefone()));
            String linha4 = "Site: " + orgao.getEmail();

            Date data = new java.util.Date();
            String data_emissao = null;
            String data_validade = null;

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            try {
                data_emissao = f.format(data);
                data_validade = f.format(validade);
            } catch (Exception ex) {
                throw new Exception("Não foi possível formatar a data nos parametros do relatório");
            }

            String protocolo;
            if (conf.getFebraban() == 3841) {
                if (n_protocolo == 0 && !protocoloManual.isEmpty()) {
                    protocolo = "Conforme despacho exarado no processo protocolado sob nº " + protocoloManual;
                } else {
                    protocolo = "Conforme despacho exarado no processo protocolado sob nº " + Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio;

                }
            } else if (n_protocolo == 0 && !protocoloManual.isEmpty()) {
                protocolo = "Com situação regular de acordo com as vistorias procedidas pelas repartições\n"
                        + "competentes desta Prefeitura conforme consta no pedido de Alvará protocolado\n"
                        + "sob o nº " + protocoloManual;
            } else {
                protocolo = "Com situação regular de acordo com as vistorias procedidas pelas repartições\n"
                        + "competentes desta Prefeitura conforme consta no pedido de Alvará protocolado\n"
                        + "sob o nº " + Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio;
            }

            String chave = null;
            try {
//                chave = Util.geraCertificado(Util.extractInt(conf.getSequenciaNCertidao())); // validação antiga
                chave = validaChaveAutenticao(Funcao.chaveAutenticacao()); // validação padrão nota fiscal
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            String modelo = "";
            String msg_rodape = "";
            Date dataAtual = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
            String dataExtenso = formatador.format(dataAtual);
            int index = dataExtenso.indexOf(",");
            int lenght = dataExtenso.length();

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase().toUpperCase());
            p.put("logotipo", logotipo.getImage());
            p.put("brasao", brasao.getImage());
            p.put("marcaDagua", adicionaMarcaDagua(brasao_bytes));
            p.put("logradouro", orgao.getEndereco());
            p.put("numero", orgao.getNumero());
            p.put("bairro", orgao.getBairro());
            p.put("cep", Util.mask("#####-###", orgao.getCep()));
            p.put("cidade", orgao.getCidade());
            p.put("uf", orgao.getUf());
            p.put("estado", orgao.getEstado().toUpperCase());
            p.put("data", data_emissao);
            p.put("validade", data_validade);
            p.put("prefeito_nome", orgao.getPrefeito());
            p.put("prefeito_titulo", orgao.getTituloPrefeito());
            p.put("protocolo", protocolo);
            p.put("codigoValidacao", chave);
            p.put("linha1", linha1);
            p.put("linha2", linha2);
            p.put("linha3", linha3);
            p.put("linha4", linha4);
            p.put("chefe", Util.extractStr(orgao.getChefeEmpreendedorismo()).trim().toUpperCase());
            p.put("fiscal", Util.extractStr(orgao.getFiscalRendas()).trim().toUpperCase());
            p.put("dataAtual", orgao.getCidade() + "," + dataExtenso.substring(++index, lenght));

            List<Object[]> lstObj = createNativeQuery(
                    "SELECT DISTINCT I.INSCRICAO as INSCRICAO, P.ID_PESSOA, P.INSC_MUNICIPAL as INSCRICAO_MUNICIPAL, I.ID_ISS, \n"
                    + " P.NOME, L.NOME ||', '|| IM.NR_IMOVEL ||', '|| B.NOME AS LOGRADOURO, IM.COMPLEMENTO, coalesce(AB.NOME || ' ', '') || B.NOME as BAIRRO, \n"
                    + " COALESCE(I.CNPJ_CPF, P.CPF_CNPJ) CNPJ_CPF, I.INSCR_ESTADUAL, I.DT_ABERTURA, I.FANTASIA, I.DT_ABERTURA, IRA.NOME AS RAMO_ATUACAO, I.NUM_ALVARA, I.OBSERVACAO, \n"
                    + " E.NOME_FANTASIA, IC1.NOME PRIMARIA, '' AS SECUNDARIA, C.NOME AS CIDADE, P.RG, P.TEL1, \n"
                    + " '' AS NOME_SOCIO, '' AS LOGRADOURO_SOCIO, '' as BAIRRO_SOCIO, \n"
                    + " '' AS CIDADE_SOCIO, '' AS RG_SOCIO, \n"
                    + " '', '' AS TEL_SOCIO, I.PROCESSO_N, I.FONE, IM.CEP, I.NUM_ALVARA\n"
                    + " FROM ISSQN I\n"
                    + " INNER JOIN IMOVEL IM ON IM.ID_IMOVEL = I.ID_IMOVEL\n"
                    + " INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA\n"
                    + " INNER JOIN BAIRRO_LOGRADOURO BL ON BL.ID_BAIRROLOGRADOURO = IM.ID_BAIRROLOGRADOURO\n"
                    + " INNER JOIN LOGRADOURO L ON L.ID_LOGRADOURO = BL.ID_LOGRADOURO AND L.ID_CIDADE = BL.ID_CIDADE\n"
                    + " INNER JOIN CIDADE C ON C.ID_CIDADE = BL.ID_CIDADE\n"
                    + " INNER JOIN BAIRRO B ON B.ID_BAIRRO = BL.ID_BAIRRO AND B.ID_CIDADE = BL.ID_CIDADE\n"
                    + " LEFT JOIN ISSQN_CNAE_ISS IA ON IA.ID_ISS = I.ID_ISS AND IA.ATIVIDADE_PRIMARIA = TRUE \n"
                    + " LEFT JOIN ISSQN_CNAE IC1 ON IC1.ID_CNAE = IA.ID_CNAE\n"
                    //                        + " LEFT JOIN ISSQN_CNAE_ISS IA2 ON IA2.ID_ISS = I.ID_ISS AND IA2.ATIVIDADE_SECUNDARIA = TRUE\n"
                    //                        + " LEFT JOIN ISSQN_CNAE IC2 ON IC2.ID_CNAE = IA2.ID_CNAE\n"
                    + " LEFT JOIN ISSQN_CATEGORIA IC ON IC.ID_CATEGORIA = I.ID_CATEGORIA\n"
                    + " LEFT JOIN ISSQN_ESCRITORIO E ON E.ID_ESCRITORIO = I.ID_ESCRITORIO\n"
                    + " LEFT JOIN ABREVIATURA AB on AB.ID_ABREVIATURA = B.ID_ABREVIATURA\n"
                    + " LEFT JOIN ABREVIATURA ABL on ABL.ID_ABREVIATURA = L.ID_ABREVIATURA\n"
                    + " LEFT JOIN ISSQN_SOCIO ISC ON ISC.ID_ISS = I.ID_ISS\n"
                    + " LEFT JOIN PESSOA PS ON PS.ID_PESSOA = ISC.ID_PESSOA\n"
                    + " LEFT JOIN IMOVEL IMS ON IMS.ID_IMOVEL = ISC.ID_IMOVEL\n"
                    + " LEFT JOIN BAIRRO_LOGRADOURO BLS ON BLS.ID_BAIRROLOGRADOURO = IMS.ID_BAIRROLOGRADOURO\n"
                    + " LEFT JOIN LOGRADOURO LS ON LS.ID_LOGRADOURO = BLS.ID_LOGRADOURO AND LS.ID_CIDADE = BLS.ID_CIDADE\n"
                    + " LEFT JOIN CIDADE CS ON CS.ID_CIDADE = BLS.ID_CIDADE\n"
                    + " LEFT JOIN BAIRRO BS ON BS.ID_BAIRRO = BLS.ID_BAIRRO AND BS.ID_CIDADE = BLS.ID_CIDADE\n"
                    + " LEFT JOIN ABREVIATURA ABS on ABS.ID_ABREVIATURA = BS.ID_ABREVIATURA\n"
                    + " LEFT JOIN ABREVIATURA ABLS on ABLS.ID_ABREVIATURA = LS.ID_ABREVIATURA\n"
                    + " LEFT JOIN ISSQN_CATEGORIA IRA ON IRA.ID_CATEGORIA = I.ID_CATEGORIA\n"
                    + " WHERE I.ID_EXERCICIO = " + exercicio
                    + " " + pesquisa
                    + " " + ordem
            );

            for (Object[] obj : lstObj) {
                HashMap field = new HashMap();
                numero_alvara++;
                n_protocolo++;

                switch (tipo) {
                    case "Licenciamento para Diversões Públicas":
                        modelo = "certidao_diversoes_publicas";
                        msg_rodape = ("Certificado de Licenciamento para Diversões Públicas emitido para " + Util.extractStr(obj[4]).toUpperCase()
                                + " : " + Util.extractStr(obj[8]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                                + " , garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                                + "autenticidade " + chave + ", " + dataExtenso + ".");
                        break;
                    case "Licenciamento para Autônomo":
                        modelo = "certidao_licenca_funcionamento_autonomo_modelo_1";
                        msg_rodape = ("Certificado de Licenciamento para Autônomo emitido para " + Util.extractStr(obj[4]).toUpperCase()
                                + " : " + Util.extractStr(obj[8]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                                + " , garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                                + "autenticidade " + chave + ", " + dataExtenso + ".");
                        break;
                    case "Licenciamento para Autônomo Domicílio Fiscal":
                        modelo = "certidao_licenca_funcionamento_autonomo_modelo_2";
                        msg_rodape = ("Certificado de Licenciamento para Autônomo Domicílio Fiscal emitido para " + Util.extractStr(obj[4]).toUpperCase()
                                + " : " + Util.extractStr(obj[8]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                                + " , garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                                + "autenticidade " + chave + ", " + dataExtenso + ".");
                        break;
                    case "Licenciamento de Funcionamento - MEI":
                        modelo = "alvara_funcionamento";
                        msg_rodape = ("Certificado de Licenciamento para Funcionamento MEI emitido para " + Util.extractStr(obj[4]).toUpperCase()
                                + " : " + Util.extractStr(obj[8]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                                + " , garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                                + "autenticidade " + chave + ", " + dataExtenso + ".");
                        break;
                    case "Licenciamento Provisório":
                        modelo = "alvara_provisorio";
                        msg_rodape = ("Certificado de Licenciamento Provisório emitido para " + Util.extractStr(obj[4]).toUpperCase()
                                + " : " + Util.extractStr(obj[8]) + ". Documento certificado por " + conf.getOrgao().getNome().toUpperCase() + ". A " + conf.getOrgao().getNome().toUpperCase()
                                + " , garante a autenticidade deste documento quando visualizado diretamente  no portal http://sistemas.americobrasiliense.sp.gov.br:8080/issqn/login sob o número de "
                                + "autenticidade " + chave + ", " + dataExtenso + ".");
                        break;
                }

                field.put("cnae", buscaAtividades(Util.extractInt(obj[3])).toUpperCase());
                if (conf.getUtilizaInscricaoMunicipal() == 1) {
                    field.put("inscricao", (obj[2] == null || obj[2].equals("")) ? Util.extractStr(obj[0]) : Util.extractStr(obj[2]));
                } else {
                    field.put("inscricao", Util.extractStr(obj[0]));
                }
                field.put("nome", Util.extractStr(obj[4]));
                field.put("logradouro", Util.extractStr(obj[5]));
                field.put("complemento", Util.extractStr(obj[6]));
                field.put("atividade_primaria", Util.extractStr(obj[17]).toUpperCase());
                field.put("atividade_secundaria", Util.extractStr(obj[18]).toUpperCase());
                switch (Util.extractStr(obj[8]).length()) {
                    case 0:
                        field.put("cnpj", Util.extractStr(obj[3]));
                        break;
                    case 11:
                        field.put("cnpj", Util.mask("###.###.###-##", Util.extractStr(obj[8])));
                        break;
                    default:
                        field.put("cnpj", Util.mask("##.###.###/####-##", Util.extractStr(obj[8])));
                        break;
                }
                field.put("insc", Util.extractStr(obj[9]));
                field.put("id_exercicio", exercicio);
                field.put("categoria", Util.extractStr(obj[13]).toUpperCase());
                field.put("bairro", Util.extractStr(obj[7]));
                field.put("nome_fantasia", Util.extractStr(obj[11]));
                field.put("dt_abertura", Util.extractDate(obj[12]));
                field.put("chefe_setor", conf.getNome1());
                field.put("cargo", conf.getCargo1());
                field.put("chefe_setor_2", conf.getNome2());
                field.put("cargo_2", conf.getCargo2());
                field.put("fone", Util.extractStr(obj[30]));
                field.put("cidade", Util.extractStr(obj[19]));
                field.put("licenca", Util.extractStr(obj[32]));
                field.put("naturezaAtividade", buscaAtividades(Integer.parseInt(obj[3].toString())).toUpperCase());
                field.put("cep", obj[31].toString());
                field.put("endereco", Util.extractStr(obj[5]));
                field.put("observacao", observacao);
                if (conf.getUtilizaAlvaraSequencial() == 1) {
                    field.put("num_alvara", Util.formatarDecimal("00000", numero_alvara) + "/" + exercicio);
                } else {
                    field.put("num_alvara", Util.extractStr(obj[14]));
                }
                field.put("observacoes", Util.extractStr(obj[15]).replace("\n", "").replace("\r", ""));
                field.put("texto_alvara", "");//Util.extractStr(obj[17]));
                field.put("escritorio_responsavel", Util.extractStr(obj[16]));
                field.put("nome_socio", Util.extractStr(obj[22]).equals("") ? Util.extractStr(obj[4]) : Util.extractStr(obj[22]));
                field.put("cpf_socio", Util.extractStr(obj[9]).equals("") ? Util.extractStr(obj[27]) : Util.extractStr(obj[9]));
                field.put("rg_socio", Util.extractStr(obj[26]).equals("") ? Util.extractStr(obj[20]) : Util.extractStr(obj[26]));
                field.put("fone_socio", Util.extractStr(obj[28]).equals("") ? Util.extractStr(obj[21]) : Util.extractStr(obj[28]));
                field.put("logradouro_socio", Util.extractStr(obj[23]).equals("") ? Util.extractStr(obj[5]) : Util.extractStr(obj[23]));
                field.put("cidade_socio", Util.extractStr(obj[25]).equals("") ? Util.extractStr(obj[19]) : Util.extractStr(obj[25]));
                field.put("bairro_socio", Util.extractStr(obj[24]).equals("") ? Util.extractStr(obj[7]) : Util.extractStr(obj[24]));
                if (!protocoloManual.isEmpty()) {
                    field.put("n_protocolo", protocoloManual);
                } else {
                    field.put("n_protocolo", Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio);
                }
                field.put("mensagemUsuario", msg_rodape);
                field.put("data", data_emissao);
                field.put("validade", data_validade);

                lst.add(field);

            }

            if (conf.getUtilizaAlvaraSequencial() == 1) {
                try {
                    conf.setSequenciaNAlvara(numero_alvara);
                    setEntidade(IssqnConfiguracao.class, conf);
                } catch (Exception ex) {
                    throw new RuntimeException("Erro ao salvar sequencial do alvará nas configurações\n" + ex.getMessage());
                }
            }

            if (n_protocolo == 1) {
                setEntidade(Issqn.class, contribuinte);
            } else {
                conf.setSequenciaNProtocolo(n_protocolo);
                setEntidade(IssqnConfiguracao.class, conf);
                switch (tipo) {
                    case "Licenciamento para Autônomo":
                        contribuinte.setProtocoloAutonomo(Util.extractStr(Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio));
                        break;
                    case "Licenciamento para Autônomo Domicílio Fiscal":
                        contribuinte.setProtocoloAutonomoDomicilioFiscal(Util.extractStr(Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio));
                        break;
                    case "Licenciamento para Diversões Públicas":
                        contribuinte.setProtocoloDiversoes(Util.extractStr(Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio));
                        break;
                    case "Licenciamento de Funcionamento - MEI":
                        contribuinte.setProtocoloFuncionamento(Util.extractStr(Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio));
                        break;
                    case "Licenciamento Provisório":
                        contribuinte.setProtocoloProvisorio(Util.extractStr(Util.formatarDecimal("00000", n_protocolo) + "/" + exercicio));
                        break;
                    default:
                        break;
                }
                setEntidade(Issqn.class, contribuinte);
            }

            if (lst.size() > 0) {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                String ip = request.getHeader("x-forwarded-for");
                if (ip == null) {
                    ip = request.getRemoteAddr();
                }

                LogEmissao logEmissao = new LogEmissao();
                logEmissao.setArquivo(rpt.byteArray(modelo, "application/pdf", lst, p, modelo));
                logEmissao.setChave(chave);
                logEmissao.setData(new Date());
                logEmissao.setCertidao(Util.extractStr(conf.getSequenciaNCertidao()));
                logEmissao.setIp(ip);
                logEmissao.setTipo("ALVARÁ");
                setEntidade(LogEmissao.class, logEmissao);
                conf.setSequenciaNCertidao(Util.extractInt(conf.getSequenciaNCertidao()) + 1);
                setEntidade(IssqnConfiguracao.class, conf);
            }

            rpt.jasperReport(modelo, "application/pdf", lst, p, modelo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao emitir alvará\n" + e.getMessage());
        }
    }

    private String buscaAtividades(int id_iss) {
        String atv_desc = "";
        String condicao = "";
        if (parc_iss) {
            condicao = " AND ICA.ATIVIDADE_SECUNDARIA = TRUE ";
        }
        String sql_atv
                = "SELECT IA.NOME FROM ISSQN_CNAE_ISS ICA \n"
                + "INNER JOIN ISSQN_CNAE IA ON ICA.ID_CNAE = IA.ID_CNAE\n"
                + "WHERE ID_ISS = " + id_iss
                + condicao + "ORDER BY IA.NOME";
        try {
            List<Object> lstObj = createNativeQuery(sql_atv);
            for (Object obj : lstObj) {
                if (!atv_desc.isEmpty()) {
                    atv_desc += "\n" + obj.toString();
                } else {
                    atv_desc = obj.toString();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return atv_desc;
    }

    public void imprimirCertidaoNegativa(String orgaoId, String inscricao, Date validade, String contribuinte, int id_pessoa) throws Exception {
        String mensagem = "";
        int id_iss = 0;
        int num_certidao = 0;
        boolean isento = false;

        // Parametros que deverá ser pego do sistema
        String chefe = "DIRCE LAUTO GUIMARÃES OLIVEIRA";
        String setor = "I.S.S.Q.N E TAXA DE LICENÇA";
        String titulo_chefe = "CHEFE DO CEAT";
        String cidade = "AMÉRICO BRASILIENSE";

        Map p = new HashMap();
        ArrayList<HashMap> lst = new ArrayList<>();
        ReportGenerator rpt = new ReportGenerator();

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

        List<Object[]> lstObj = createNativeQuery(" SELECT DISTINCT p.nome AS proprietario, COALESCE((al.nome || ' ' || l.nome || ' ' || ', ' || i.nr_imovel),l.nome || ' ' || ', ' || i.nr_imovel) AS LOG,\n"
                + "COALESCE((ab.nome || ' ' ||b.nome), b.nome) AS BAIRRO, i.cep, i.complemento,\n"
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
                + "AND II.ID_EXERCICIO =" + 2017);

        for (Object[] obj : lstObj) {

            if (obj[5].toString().equals("S")) {
                isento = true;
            }

            HashMap field = new HashMap();

            field.put("proprietario", obj[0].toString());
            field.put("endereco", obj[1].toString());
            field.put("bairro", obj[2].toString());
            field.put("cep", obj[3].toString());
            field.put("complemento", obj[4].toString());
            field.put("cnpj", obj[9].toString());
            field.put("inscricao", obj[5].toString());
            field.put("inscricao_estadual", obj[11].toString());
            field.put("atividade", buscaAtividades(Integer.parseInt(obj[7].toString())));
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
        p.put("id_exercicio", 2017);
        p.put("num_certidao", 2000001037);

        rpt.jasperReport("certidao_negativa_com_timbre", "application/pdf", lst, p, "certidao_negativa_com_timbre");

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

    public void imprimirLicencasVencidas(String orgaoId, String filtro, String ordem) {
        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
            if (orgao == null) {
                throw new Exception("Informações do orgão não encontradas!");
            }
            byte[] logotipo_bytes = orgao.getLogotipo();
            ImageIcon logotipo = new ImageIcon();
            if (logotipo_bytes != null) {
                logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));        // Lista com beans
            }

            //-- PARAMETROS
            p.put("orgao", orgao.getNome().toUpperCase());
            p.put("brasao", logotipo.getImage());

            List<IssqnAlvaraEmissao> lstEmissao = getListaPura(IssqnAlvaraEmissao.class,
                    "select e from IssqnAlvaraEmissao e"
                    + "\nwhere e.orgao.idOrgao = ?1"
                    + "\nand e.dataValidade < ?2 "
                    + filtro
                    + "\norder by " + ordem, orgaoId, new Date()
            );

            for (IssqnAlvaraEmissao e : lstEmissao) {
                HashMap field = new HashMap();

                field.put("inscricao", e.getIss().getInscricao());
                field.put("contribuinte", e.getIss().getPessoa().getNome());
                field.put("atividade", buscaAtividades(e.getIss().getId()));
                field.put("tp_pessoa", e.getIss().getPessoa().getTpPessoa().descricao);
                field.put("num_alvara", e.getNrAlvara().toString());
                field.put("validade", e.getDataValidade());

                lst.add(field);
            }

            rpt.jasperReport("licencas_vencidas", "application/pdf", lst, p, "licencas_vencidas");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}
