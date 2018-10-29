/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

/**
 *
 * @author David
 */
public class ArquivoCobrancaFebrabanRepositorio extends Repositorio {

    private File file;
    private PrintWriter p;

    public ArquivoCobrancaFebrabanRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public void gerarArquivo(int exercicio, String order, String orgaoId, String tipo, String opcao_cobranca)
            throws RuntimeException {
        try {

            IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c "
                    + "\n where c.orgao.idOrgao = ?1 "
                    + "\norder by c.id desc", orgaoId
            );
            if (conf == null) {
                throw new Exception("Informações do parâmetros não encontradas!");
            }

            String desc_arq = "FEBRABAN_TCF_" + exercicio;
            if (tipo.equals(" AND TC.TP_COBRANCA = 2")) {
                desc_arq = "FEBRABAN_TX_VIGILANCIA_" + exercicio;
            }

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            ServletContext servletContext = (ServletContext) context.getContext();

            file = new File(servletContext.getRealPath("") + "/resources/" + desc_arq + ".txt");
            p = new PrintWriter(file);
            String l = "";

            List<Object[]> lstObj = createNativeQuery("SELECT DISTINCT M.ID_EXERCICIO, I.INSCRICAO, M.ID_MOVIMENTO, "
                    + "P.NOME AS CONTRIBUINTE,\n"
                    + "L.NOME AS LOG, IM.NR_IMOVEL, IM.COMPLEMENTO,\n"
                    + "B.NOME AS BAIRRO, LC.NOME AS LOG_C, IC.NR_IMOVEL AS NR_IMOVEL_C,\n"
                    + "IC.COMPLEMENTO AS COMP_C, BC.NOME AS BAIRRO_C, CC.NOME AS CIDADE_C,\n"
                    + "CC.UF AS UF_C, IC.CEP AS CEP_C, M.VL_TOTAL, I.ID_ISS, P.CPF_CNPJ\n"
                    + "FROM ISSQN_MOVIMENTO M\n"
                    + "INNER JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = M.ID_MOVIMENTO\n"
                    + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA\n"
                    + "INNER JOIN ISSQN I ON I.ID_ISS = M.ID_ISS\n"
                    + "INNER JOIN PESSOA P ON P.ID_PESSOA = I.ID_PESSOA\n"
                    + "INNER JOIN IMOVEL IM ON IM.ID_IMOVEL = I.ID_IMOVEL\n"
                    + "INNER JOIN BAIRRO_LOGRADOURO BL ON BL.ID_BAIRROLOGRADOURO = IM.ID_BAIRROLOGRADOURO\n"
                    + "INNER JOIN BAIRRO B ON B.ID_BAIRRO = BL.ID_BAIRRO AND B.ID_CIDADE = BL.ID_CIDADE\n"
                    + "INNER JOIN CIDADE C ON C.ID_CIDADE = B.ID_CIDADE\n"
                    + "INNER JOIN LOGRADOURO L ON L.ID_LOGRADOURO = BL.ID_LOGRADOURO AND L.ID_CIDADE = BL.ID_CIDADE\n"
                    + "LEFT  JOIN ABREVIATURA A1 ON A1.ID_ABREVIATURA = L.ID_ABREVIATURA\n"
                    + "LEFT  JOIN ABREVIATURA A2  ON A2.ID_ABREVIATURA = B.ID_ABREVIATURA\n"
                    + "INNER JOIN IMOVEL IC ON IC.ID_IMOVEL = coalesce(I.ID_IMOVEL_COBRANCA, I.ID_IMOVEL)\n"
                    + "INNER JOIN BAIRRO_LOGRADOURO BLC ON BLC.ID_BAIRROLOGRADOURO = IC.ID_BAIRROLOGRADOURO\n"
                    + "INNER JOIN BAIRRO BC ON BC.ID_BAIRRO = BLC.ID_BAIRRO AND BC.ID_CIDADE = BLC.ID_CIDADE\n"
                    + "INNER JOIN CIDADE CC ON CC.ID_CIDADE = BC.ID_CIDADE\n"
                    + "INNER JOIN LOGRADOURO LC ON LC.ID_LOGRADOURO = BLC.ID_LOGRADOURO AND LC.ID_CIDADE = BLC.ID_CIDADE\n"
                    + "LEFT  JOIN ABREVIATURA A3 ON A3.ID_ABREVIATURA = LC.ID_ABREVIATURA\n"
                    + "LEFT  JOIN ABREVIATURA A4 ON A4.ID_ABREVIATURA = BC.ID_ABREVIATURA\n"
                    + "WHERE M.ID_EXERCICIO =  " + exercicio + tipo + "\n"
                    + "AND M.CANCELADO_MOVIMENTO = 0 AND (MP.STATUS = 0 OR MP.STATUS IS NULL)"
                    + "\n" + order);
            for (Object[] obj : lstObj) {

                double vlTotal = Util.extractDouble(obj[15]);

                l = "";
                l += "C";
                l += Util.Texto.alinharEsquerda(Util.extractDouble(obj[0]), 4);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[1]), 20);
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000000", Util.extractInt(obj[2])), 7);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[3]), 45);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[4]), 85);
                if (Util.isNumeric(Util.extractStr(obj[5]))) {
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000", Util.extractInt(obj[5])), 4);
                } else {
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000", 0), 4);
                }
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[6]), 20);
                l += Util.Texto.alinharEsquerda(null, 10);
                l += Util.Texto.alinharEsquerda(null, 20);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[7]), 55);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[8]), 85);
                if (Util.isNumeric(Util.extractStr(obj[5]))) {
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000", Util.extractInt(obj[9])), 4);
                } else {
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000", 0), 4);
                }
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[10]), 20);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[11]), 55);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[12]), 35);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[13]), 2);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[14]), 8);
                l += "1a VIA";
                l += Util.Texto.alinharEsquerda(Util.desmascarar("/", Util.hoje()), 8); // data atual - emissão
                l += Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("0000000.00", vlTotal)), 9);
                l += Util.Texto.alinharEsquerda(null, 40);
                l += Util.Texto.alinharEsquerda(buscaAtividades(Util.extractInt(obj[16])), 40);
                l += Util.Texto.alinharEsquerda(Util.extractStr(obj[17]), 20);

                List<Object[]> lstObjBenf = createNativeQuery("SELECT T.NOME as DESCRICAO, SUM(PI.VL_TAXA) as VALOR\n"
                        + "FROM ISSQN_MOVIMENTO_PARCELA_ITEM PI\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA P ON P.ID_MOVIMENTO_PARCELA = PI.ID_MOVIMENTO_PARCELA\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = P.ID_TIPO_COBRANCA\n"
                        + "INNER JOIN ISSQN_TAXA T ON T.ID_TAXA = PI.ID_TAXA\n"
                        + "WHERE P.ID_MOVIMENTO = " + Util.extractInt(obj[2]) + "\n"
                        + "AND T.NOME <> 'TAXA DE EXPEDIENTE'\n" // Taxa de Expediente pega somente o valor unico do movimento, não pega a soma das parcelas
                        + "AND ((TC.QTD_PARCELA > 1) or ((SELECT COUNT(ID_MOVIMENTO) FROM ISSQN_MOVIMENTO_PARCELA WHERE ID_MOVIMENTO = P.ID_MOVIMENTO) = 1))\n"
                        + "GROUP BY 1\n"
                        + "UNION\n"
                        + "SELECT T.NOME as DESCRICAO, SUM(PI.VL_TAXA) as VALOR\n"
                        + "FROM ISSQN_MOVIMENTO_PARCELA_ITEM PI\n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA P ON P.ID_MOVIMENTO_PARCELA = PI.ID_MOVIMENTO_PARCELA\n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = P.ID_TIPO_COBRANCA\n"
                        + "INNER JOIN ISSQN_TAXA T ON T.ID_TAXA = PI.ID_TAXA\n"
                        + "WHERE P.ID_MOVIMENTO = " + Util.extractInt(obj[2]) + "\n"
                        + "AND T.NOME = 'TAXA DE EXPEDIENTE'\n"
                        + "AND (TC.QTD_PARCELA = 1)"
                        + "GROUP BY 1");

                String taxas_valores = "";
                for (Object[] objbenf : lstObjBenf) {
                    double vlTaxa = Util.extractDouble(objbenf[1]);
                    taxas_valores = taxas_valores + Util.Texto.alinharEsquerda(Util.extractStr(objbenf[0]), 50);
                    taxas_valores = taxas_valores + Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("0000000.00", vlTaxa)), 9);
                }
                l += taxas_valores;
                l += "\n";

                int conta_parcela = 0;
                String linha = "";

                List<Object[]> lstObjParc = createNativeQuery("SELECT DISTINCT TI.DT_VENCIMENTO, TC.QTD_PARCELA, TC.DESCRICAO || ': ' AS DESC, TI.ID_TIPO_COBRANCA, TI.PARCELA \n"
                        + "FROM ISSQN_TIPO_COBRANCA_ITEM TI \n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC ON TC.ID_TIPO_COBRANCA = TI.ID_TIPO_COBRANCA \n"
                        + "INNER JOIN ISSQN_MOVIMENTO_PARCELA IMP ON TC.ID_TIPO_COBRANCA = IMP.ID_TIPO_COBRANCA \n"
                        + "INNER JOIN ISSQN_MOVIMENTO IM ON IM.ID_MOVIMENTO = IMP.ID_MOVIMENTO \n"
                        + "WHERE TI.ID_EXERCICIO = " + exercicio + tipo + "\n"
                        + "AND IMP.ID_MOVIMENTO = " + Util.extractInt(obj[2]) + "\n"
                        + "ORDER BY TI.ID_TIPO_COBRANCA, TI.PARCELA");

                for (Object[] objParc : lstObjParc) {
                    conta_parcela++;
                    String dia = Util.parseSqlToBrDate(Util.extractStr(objParc[0])).substring(0, 2);
                    String mes = Util.getNomeMes((byte) Integer.parseInt((Util.parseSqlToBrDate(Util.extractStr(objParc[0]))).substring(3, 5)));

                    linha += dia + "/" + mes.substring(0, 3) + " - ";

                    if (Util.extractInt(objParc[1]) == conta_parcela) {
                        l += Util.Texto.alinharEsquerda("V" + Util.extractStr(objParc[2]) + linha.substring(0, linha.length() - 2), 62) + "\n";
                        linha = "";
                        conta_parcela = 0;
                    }
                }

                List<Object[]> lstObjParcelas = createNativeQuery("SELECT TC.DESCRICAO, MP.PARCELA, MP.DT_VENCIMENTO, MP.VL_PARCELA, MP.VL_DESCONTO, \n"
                        + "(CASE WHEN (QTD_PARCELA = 1) THEN 'FALSE' ELSE 'TRUE' END) AS STATUS, MP.NOSSO_NUMERO, \n"
                        + "TC.QTD_PARCELA, TC.MENSAGEM, MP.ID_TIPO_COBRANCA, IS_FIXO, TC.BLOQUEIO\n"
                        + "FROM ISSQN_MOVIMENTO_PARCELA MP \n"
                        + "INNER JOIN ISSQN_TIPO_COBRANCA TC ON TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA \n"
                        + " WHERE MP.ID_MOVIMENTO = " + Util.extractInt(obj[2]) + " AND (MP.STATUS = 0 or MP.STATUS IS NULL)\n"
                        + "ORDER BY 2,8,10");

                for (Object[] objParcelas : lstObjParcelas) {

                    l += Util.Texto.alinharEsquerda("P", 1);
                    l += Util.Texto.alinharEsquerda("REAL", 4);
                    l += Util.Texto.alinharEsquerda(Util.extractStr(objParcelas[0]), 40);
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00", Util.extractInt(objParcelas[1])) + "/" + Util.formatarDecimal("00", Util.extractInt(objParcelas[7])), 5);
                    l += Util.Texto.alinharEsquerda(Util.desmascarar("/", Util.parseSqlToBrDate(Util.extractStr(objParcelas[2]))), 8);
                    l += Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("0000000.00", Util.extractDouble(objParcelas[3]))), 9);
                    l += Util.Texto.alinharEsquerda(Util.extractStr(objParcelas[8]).replaceAll("\n", " "), 255);
                    l += Util.Texto.alinharEsquerda(Util.extractStr(objParcelas[5]), 5);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String dataVencimento = sdf.format(Util.extractDate(objParcelas[2]));

                    String formaDigito
                            = "8"
                            + "1"
                            + ((objParcelas[11] == null ? true : !(boolean)objParcelas[11]) ? "7" : "6")
                            + Util.desmascarar(",", Util.formatarDecimal("000000000.00", Util.extractDouble(objParcelas[3])))
                            + Util.formatarDecimal("0000", conf.getFebraban())
                            + dataVencimento
                            + Util.Texto.alinharDireita(Util.extractStr(objParcelas[6]), 14).replaceAll(" ", "0")
                            + "030";
                    int digitoGeral = calcModulo10(Util.desmascarar("-/.'", formaDigito));

                    String barcode
                            = "8"
                            + "1"
                            + ((objParcelas[11] == null ? true : !(boolean)objParcelas[11])  ? "7" : "6")
                            + digitoGeral
                            + Util.desmascarar(",", Util.formatarDecimal("000000000.00", Util.extractDouble(objParcelas[3])))
                            + Util.formatarDecimal("0000", conf.getFebraban())
                            + dataVencimento
                            + Util.Texto.alinharDireita(Util.extractStr(objParcelas[6]), 14).replaceAll(" ", "0")
                            + "030";

                    l += Util.Texto.alinharEsquerda(Util.desmascarar("-/.'", barcode), 44);
                    l += "\n";
                }
                p.print(l);
            }
            p.close();
            Util.downloadFile((desc_arq + ".txt"), ("/resources/" + desc_arq + ".txt"));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar arquivo\n" + e.getMessage());
        }
    }

    private String buscaAtividades(int id_iss) {
        String atv_desc = "";
        String sql_atv
                = "SELECT IA.NOME FROM ISSQN_CNAE_ISS ICA \n"
                + "INNER JOIN ISSQN_CNAE IA ON ICA.ID_CNAE = IA.ID_CNAE \n"
                + "WHERE ID_ISS = " + id_iss + " AND ATIVIDADE_PRIMARIA = TRUE";
        try {
            List<Object> lstObj = createNativeQuery(sql_atv);
            for (Object obj : lstObj) {
                atv_desc = obj.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return atv_desc;
    }

    private int calcModulo10(String seq0) {
        // converter String em vetor de interios
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
        result = 10 - (result % 10);// pego o resto da divisão do valor calculado acima por 10 e subtrai de 10
        if (result == 10) {
            result = 0;
        }

        return result;
    }

}
