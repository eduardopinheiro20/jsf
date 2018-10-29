/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnAlvaraDiversoesPublicas;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Thiago Martos
 */
public class IssqnAlvaraDiversoesRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnAlvaraDiversoesRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnAlvaraDiversoesPublicas salvarAlvaraDiversoes(IssqnAlvaraDiversoesPublicas ad) {
        if (ad.getId() == null) {
            return adicionarEntidade(IssqnAlvaraDiversoesPublicas.class, ad);
        } else {
            return setEntidade(IssqnAlvaraDiversoesPublicas.class, ad);
        }
    }

    public synchronized void removerAlvaraDiversoes(int categoriaId) {
        IssqnAlvaraDiversoesPublicas t = getEntidade(IssqnAlvaraDiversoesPublicas.class, categoriaId);
        removerEntidade(t);
    }

    public IssqnAlvaraDiversoesPublicas obterAlvaraDiversoesPorId(int id) {
        return getEntidade(IssqnAlvaraDiversoesPublicas.class, id);
    }

    public List<IssqnAlvaraDiversoesPublicas> obterAlvarasDiversoes(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where i.inscricao like " + Util.quotarStr("%" + filtro + "%")
                + "\n or ad.cnpjCpf like " + Util.quotarStr("%" + filtro + "%");
            } else {
                where += "\n where UPPER(ad.contribuinte) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnAlvaraDiversoesPublicas.class,
                "select ad from IssqnAlvaraDiversoesPublicas ad "
                + "\njoin ad.iss i "
                + where
                + "\norder by ad.id", limite
        );

    }

    public void imprimirAlvaraDiversao(Integer alvaraId, String orgaoId) throws Exception {

        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            List<Object[]> orgao = createNativeQuery(
                    "SELECT O.NOME, C.NOME, I.NOME_1, I.CARGO_1, I.NOME_2, I.CARGO_2, I.observacao_diversao, I.end_ceat "
                    + "\nFROM CONTABIL_ORGAO O"
                    + "\nINNER JOIN ISSQN_CONFIGURACAO I ON I.ID_ORGAO = O.ID_ORGAO"
                    + "\nLEFT JOIN CIDADE C ON C.ID_CIDADE = O.ID_CIDADE WHERE O.ID_ORGAO = "
                    + Util.quotarStr(orgaoId)
            );
            if (orgao.isEmpty()) {
                throw new Exception("Informações do orgão não encontradas!");
            }

            //-- PARAMETROS
            p.put("data", orgao.get(0)[1] + ", " + Util.dateExtended(new Date(), false));
            p.put("nome1", orgao.get(0)[2]);
            p.put("cargo1", orgao.get(0)[3]);
            p.put("nome2", orgao.get(0)[4]);
            p.put("cargo2", orgao.get(0)[5]);
            p.put("observacao", (orgao.get(0)[6] == null ? "" : "Observações: \n\n" + orgao.get(0)[6]));

            String endCeat = (orgao.get(0)[6] == null ? "" : orgao.get(0)[6].toString().trim());
            List<Object[]> listRelatorio = createNativeQuery(
                    "SELECT T.contribuinte, T.CNPJ_CPF, L.NOME, B.NOME, C.NOME, T.PROCESSO, T.DATA, T.TEXTO, T.TIPO_ALVARA\n"
                    + "FROM issqn_alvara_diversoes_publicas T\n"
                    + "LEFT JOIN BAIRRO_LOGRADOURO BL ON BL.ID_BAIRROLOGRADOURO = T.ID_BAIRROLOGRADOURO\n"
                    + "LEFT JOIN LOGRADOURO L ON L.ID_LOGRADOURO = BL.ID_LOGRADOURO AND L.ID_CIDADE = BL.ID_CIDADE\n"
                    + "LEFT JOIN BAIRRO B ON B.ID_BAIRRO = BL.ID_BAIRRO AND B.ID_CIDADE = BL.ID_CIDADE\n"
                    + "LEFT JOIN CIDADE C ON B.ID_CIDADE = C.ID_CIDADE\n"
                    + "WHERE T.id_alvara_diversoes_publicas = " + alvaraId
            );

            for (Object[] obj : listRelatorio) {
                HashMap field = new HashMap();

                String texto = "A " + orgao.get(0)[0] + ", por seu Departamento competente, no uso de suas "
                        + "atribuições, e de conformidade com as disposições contidas na Legislação "
                        + "Municipal vigente, dando prosseguimento ao que requereu " + obj[0]
                        + " - CPF/CNPJ: " + obj[1] + ", localizado na " + obj[2]
                        + ", " + obj[3] + " - " + obj[4]
                        + ", através do processo Nº " + obj[5] + ", guichê Nº 0000000 de "
                        + (obj[6] == null ? "" : Util.parseSqlToBrDate(obj[6]))
                        + ", concede o presente Alvará de Diversões Públicas para: \n\n";
                texto += obj[7] + "\n\nFicando sujeito a fiscalização dos agentes desta municipalidade.";

                field.put("end_ceat", (endCeat.length() > 0 ? "ENDEREÇO CEAT: " + endCeat.toUpperCase() : ""));
                field.put("tipo", obj[8]);
                field.put("alvara", ("Alvará Nº: <b>" + Util.Texto.strZero(alvaraId, 6) + "</b>"));
                field.put("texto", texto);

                lst.add(field);
            }
            rpt.jasperReport("alvara_diversao", "application/pdf", lst, p, "alvara_diversao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }

}
