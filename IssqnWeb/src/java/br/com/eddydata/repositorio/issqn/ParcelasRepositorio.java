/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.dto.issqn.CancelamentoDTO;
import br.com.eddydata.dto.issqn.ParcelaDTO;
import br.com.eddydata.dto.issqn.RetornoBuscaParcelaDTO;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class ParcelasRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public ParcelasRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public List<ParcelaDTO> obterParcelas(Integer exercicio, String filtro) {
        List<ParcelaDTO> retorno = new ArrayList<>();
        List<Object[]> lst = createNativeQuery(
                "SELECT I.ID_ISS, I.INSCRICAO, CI.ID_MOVIMENTO_PARCELA,\n"
                + "T.QTD_PARCELA, T.DESCRICAO, CI.PARCELA, CI.DT_VENCIMENTO, CI.VL_PARCELA,\n"
                + "CI.DT_PAGAMENTO, CI.VL_PAGO, CI.LOTE,\n"
                + "B.NOME AS BANCO, CI.DT_BAIXA,\n"
                + "(select sum(MP.VL_PARCELA) from ISSQN_MOVIMENTO_PARCELA MP where MP."
                + "ID_MOVIMENTO = MI.ID_MOVIMENTO and MP.ID_TIPO_COBRANCA = CI.ID_TIPO_COBRANCA and "
                + "(MP.CANCELADO_PAGAMENTO = 0 or MP.CANCELADO_PAGAMENTO is null)) as VL_TOTAL, CI.ID_MOVIMENTO_PARCELA,\n"
                + "CI.ID_TIPO_COBRANCA, CASE WHEN MI.TP_MOVIMENTO = 2 THEN 'ISS'  WHEN MI.TP_MOVIMENTO = 1 THEN 'LICENÇA DE FUNCIONAMENTO'\n"
                + "WHEN MI.TP_MOVIMENTO = 3 THEN 'ESTIMADO' ELSE 'ISSQN E TAXA DE FUNCIONAMENTO'END AS TIPO_MOVIMENTO, CI.NOSSO_NUMERO\n"
                + "FROM ISSQN I\n"
                + "INNER JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = I.ID_ISS\n"
                + "INNER JOIN ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO = MI.ID_MOVIMENTO\n"
                + "LEFT JOIN BANCO B ON B.ID_BANCO = CI.ID_BANCO\n"
                + "INNER JOIN ISSQN_TIPO_COBRANCA T ON T.ID_TIPO_COBRANCA = CI.ID_TIPO_COBRANCA\n"
                + "WHERE T.ID_EXERCICIO = " + exercicio + "\n"
                + "AND (MI.CANCELADO_MOVIMENTO IS NULL OR MI.CANCELADO_MOVIMENTO = 0)\n"
                + "AND (CI.CANCELADO_PAGAMENTO IS NULL OR CI.CANCELADO_PAGAMENTO = 0)\n"
                + filtro
                + " ORDER BY CI.NOSSO_NUMERO"
        );

        for (Object[] obj : lst) {
            ParcelaDTO p = new ParcelaDTO();

            p.setTipoTaxa(Util.extractStr(obj[16]));
            p.setFormaPagto(Util.extractStr(obj[4]));
            p.setParc(Util.extractInt(obj[5]));
            p.setValor(Util.extractDouble(obj[7]));
            p.setDtVenc((obj[6] == null ? null : Util.extractDate(obj[6])));
            p.setDtPagto((obj[8] == null ? null : Util.extractDate(obj[8])));
            p.setVlPago(Util.extractDouble(obj[9]));
            p.setBanco(Util.extractStr(obj[11]));
            p.setLote(Util.extractStr(obj[10]));
            p.setNossoNumero(Util.extractStr(obj[17]));
            p.setVlTotal(Util.extractDouble(obj[13]));

            retorno.add(p);
        }

        return retorno;
    }

    public List<CancelamentoDTO> obterCancelamentos(Integer exercicio, String filtro) {
        List<CancelamentoDTO> retorno = new ArrayList<>();
        List<Object[]> lst = createNativeQuery(
                "SELECT I.ID_ISS, I.INSCRICAO, CI.ID_MOVIMENTO_PARCELA,\n"
                + "T.QTD_PARCELA, T.DESCRICAO, CI.PARCELA, CI.DT_VENCIMENTO, CI.VL_PARCELA,\n"
                + "CI.DT_CANCELADO_PAGAMENTO, CI.OBS_CANCEL, CI.NOSSO_NUMERO\n "
                + "FROM ISSQN I\n"
                + "INNER JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = I.ID_ISS\n"
                + "INNER JOIN ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO = MI.ID_MOVIMENTO\n"
                + "LEFT JOIN BANCO B ON B.ID_BANCO = CI.ID_BANCO\n"
                + "INNER JOIN ISSQN_TIPO_COBRANCA T ON T.ID_TIPO_COBRANCA = CI.ID_TIPO_COBRANCA\n"
                + "WHERE T.ID_EXERCICIO = " + exercicio + "\n"
                + "AND (MI.CANCELADO_MOVIMENTO IS NOT NULL OR MI.CANCELADO_MOVIMENTO = 1)\n"
                + "AND (CI.STATUS = 2 OR CI.OBS_CANCEL IS NOT NULL)"
                + filtro
                + "ORDER BY CI.NOSSO_NUMERO"
        );

        for (Object[] obj : lst) {
            CancelamentoDTO c = new CancelamentoDTO();

            c.setFormaPagto(Util.extractStr(obj[4]));
            c.setParc(Util.extractInt(obj[5]));
            c.setValor(Util.extractDouble(obj[7]));
            c.setDtCancelamento((obj[8] == null ? null : Util.extractDate(obj[8])));
            c.setDescricaoParc(Util.extractStr(obj[5]));
            c.setDetalheCancelamento(Util.extractStr(obj[9]).trim());
            c.setNossoNumero(Util.extractStr(obj[10]).trim());

            retorno.add(c);
        }

        return retorno;
    }

    public List<Object[]> obterTaxas(int movimentoId,int movimentoParcelaId,double vlParcela,int nossoNumero) {
        List<Object[]> retorno = new ArrayList<>();
        List<Object[]> lst = createNativeQuery(
                " SELECT T.NOME as DESCRICAO, PI.VL_TAXA AS SOMA"
                + " \nFROM ISSQN_MOVIMENTO_PARCELA_ITEM PI "
                + " \nINNER JOIN ISSQN_TAXA T ON T.ID_TAXA = PI.ID_TAXA::integer "
                + " \nINNER JOIN ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO_PARCELA = PI.ID_MOVIMENTO_PARCELA  "
                + " \nINNER JOIN ISSQN_MOVIMENTO M ON  CI.ID_MOVIMENTO = M.ID_MOVIMENTO "
                + " \nWHERE CI.PARCELA = " + movimentoParcelaId
                + " \nAND M.ID_MOVIMENTO = " + movimentoId
                + " \nAND CI.VL_PARCELA = " + vlParcela
                + " \nAND CI.NOSSO_NUMERO = " + nossoNumero
                + " \nAND PI.VL_TAXA <> 0 "
        );

        for (Object[] obj : lst) {
            Object[] o = new Object[2];

            if (obj[0] == null) {
                o[0] = "ISS: VL_ESTIMADO";
            } else {
                o[0] = Util.extractStr(obj[0]);
            }
            o[1] = Util.extractDouble(obj[1]);

            retorno.add(o);
        }

        return retorno;
    }

    public Double[] obterTotais(Integer exercicio, Integer issId) {
        Double[] retorno = new Double[5];
        List<Object[]> lst = createNativeQuery(
                "select sum(MP.VL_DESCONTO) as VL_DESCONTO, sum(MP.VL_JUROS) as VL_JUROS, "
                + "sum(MP.VL_MULTA) as VL_MULTA, sum(MP.VL_PAGO) as VL_PAGO , m.VL_TOTAL\n"
                + "from ISSQN_MOVIMENTO_PARCELA MP\n"
                + "inner join ISSQN_MOVIMENTO M on M.ID_MOVIMENTO = MP.ID_MOVIMENTO and "
                + "M.ID_EXERCICIO = " + exercicio + "\n"
                + "where M.ID_ISS = " + issId + " and "
                + "(M.CANCELADO_MOVIMENTO = 0 or M.CANCELADO_MOVIMENTO is null) and (MP.CANCELADO_PAGAMENTO = 0 or "
                + "MP.CANCELADO_PAGAMENTO is null) GROUP BY M.VL_TOTAL"
        );

        for (Object[] obj : lst) {
            retorno[0] = Util.extractDouble(obj[0]);
            retorno[1] = Util.extractDouble(obj[1]);
            retorno[2] = Util.extractDouble(obj[2]);
            retorno[3] = Util.extractDouble(obj[3]);
            retorno[4] = Util.extractDouble(obj[4]);
        }

        return retorno;
    }

    public List<RetornoBuscaParcelaDTO> buscarParcela(Integer exercicio, String filtro) {
        List<RetornoBuscaParcelaDTO> retorno = new ArrayList<>();
        List<Object[]> lst = createNativeQuery(
                "SELECT DISTINCT P.NOME, (ALE.NOME ||' '|| LE.NOME) AS LOG_ENTREGA, "
                + "\nCASE WHEN BE.ID_ABREVIATURA IS NOT NULL THEN ABE.NOME ||' '|| BE.NOME ELSE BE.NOME END, "
                + "\nIE.NR_IMOVEL, CE.NOME AS CID_ENTREGA, CE.UF, IE.CEP, IE.COMPLEMENTO, "
                + "\nII.INSCRICAO, IM.ID_IMOVEL AS IMOVEL, (ALI.NOME ||' '|| LI.NOME) AS LOG_IMOVEL, "
                + "\nCASE WHEN BI.ID_ABREVIATURA IS NOT NULL THEN ABI.NOME ||' '|| BI.NOME ELSE BI.NOME END, "
                + "\nIM.NR_IMOVEL, CI.NOME AS CID_IMOVEL, CI.UF, "
                + "\nCASE WHEN ALI.NOME IS NOT NULL THEN ALI.NOME ||' '|| P.LOGRADOURO ||', '|| IM.NR_IMOVEL ELSE P.LOGRADOURO ||', '|| IM.NR_IMOVEL  END AS ENDERECO, "
                + "\nIM.CEP, IM.COMPLEMENTO, II.ID_ISS, MI.ID_MOVIMENTO, "
                + "\nCASE WHEN MI.TP_MOVIMENTO = 2 THEN 'ISS'  WHEN MI.TP_MOVIMENTO = 1 THEN 'LICENÇA DE FUNCIONAMENTO'  "
                + " WHEN MI.TP_MOVIMENTO = 3 THEN 'ESTIMADO' ELSE 'ISSQN E TAXA DE LICENÇA' END AS TIPO_MOVIMENTO, MI.TP_MOVIMENTO, II.NUM_ALVARA, P.ID_PESSOA "
                + "\nFROM pessoa p"
                + "\nINNER JOIN issqn ii ON ii.id_pessoa = p.id_pessoa"
                + "\nINNER JOIN imovel im ON im.id_imovel = ii.id_imovel"
                + "\nINNER JOIN bairro_logradouro bl ON bl.id_bairrologradouro = im.id_bairrologradouro"
                + "\nINNER JOIN cidade ci ON ci.id_cidade = bl.id_cidade"
                + "\nINNER JOIN bairro bi ON bi.id_bairro = bl.id_bairro AND bi.id_cidade = bl.id_cidade"
                + "\nINNER JOIN logradouro li ON li.id_logradouro = bl.id_logradouro AND li.id_cidade = bl.id_cidade"
                + "\nLEFT JOIN ABREVIATURA ALI ON ALI.ID_ABREVIATURA = LI.ID_ABREVIATURA"
                + "\nLEFT JOIN ABREVIATURA ABI ON ABI.ID_ABREVIATURA = BI.ID_ABREVIATURA"
                + "\nLEFT JOIN imovel ie ON ie.id_imovel = ii.id_imovel_cobranca"
                + "\nLEFT JOIN bairro_logradouro ble ON ble.id_bairrologradouro = ie.id_bairrologradouro"
                + "\nLEFT JOIN cidade ce ON ce.id_cidade = ble.id_cidade"
                + "\nLEFT JOIN bairro be ON be.id_bairro = ble.id_bairro AND be.id_cidade = ble.id_cidade"
                + "\nLEFT JOIN logradouro le ON le.id_logradouro = ble.id_logradouro AND le.id_cidade = ble.id_cidade"
                + "\nLEFT JOIN ABREVIATURA ALE ON ALE.ID_ABREVIATURA = LE.ID_ABREVIATURA"
                + "\nLEFT JOIN ABREVIATURA ABE ON ABE.ID_ABREVIATURA = BE.ID_ABREVIATURA"
                + "\nLEFT JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = II.ID_ISS"
                + "\nLEFT JOIN ISSQN_MOVIMENTO_PARCELA MP ON MP.ID_MOVIMENTO = MI.ID_MOVIMENTO"
                + "\nWHERE mi.dt_cancelado is null and mi.id_exercicio=" + exercicio
                + filtro
                + "\n ORDER BY P.NOME"
        );
        for (Object[] obj : lst) {
            RetornoBuscaParcelaDTO r = new RetornoBuscaParcelaDTO();

            r.setNome(Util.extractStr(obj[0]));
            r.setInscricao(Util.extractStr(obj[8]));
            r.setEndereco(Util.extractStr(obj[15]));
            r.setIdIss(Util.extractInt(obj[18]));
            r.setIdMovimento(Util.extractInt(obj[19]));

            retorno.add(r);
        }

        return retorno;
    }

}
