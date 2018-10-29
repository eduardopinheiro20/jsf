/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.entidade.issqn.IssqnMovimento;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcelaItem;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Lucas
 */
public abstract class CalculoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public CalculoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    protected List<IssqnTipoCobranca> carregarTiposCobranca(int id_exercicio, Integer tipoCobranca) throws RuntimeException {
        String condicao = "";
        if (tipoCobranca != 0) {
            condicao += " and tc.tpCobranca = " + tipoCobranca;
        }

        return getListaPura(IssqnTipoCobranca.class, "select tc from IssqnTipoCobranca tc "
                + "where tc.idExercicio = ?1 " + condicao + " order by tc.qtdParcela", id_exercicio);
    }

    protected Long gerarNossoNumero() {
        try {
            Number nossoNumero = getEntidadePura(Number.class, "select coalesce(max(p.nossoNumero), 0) + 1 from IssqnMovimentoParcela p");
            return nossoNumero.longValue();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected IssqnMovimento registrarIssqn_movimento(Issqn id_iss, double vl_total,
            double vl_desconto, int cancelado_movimento, Date dt_cancelamento,
            String motivo, String isento, int tp_movimento, String usuario, int id_exercicio) throws RuntimeException {

        IssqnMovimento im = new IssqnMovimento();
        im.setIss(id_iss);
        im.setIdExercicio(id_exercicio);
        im.setIsento(isento);
        im.setVlTotal(vl_total);
        im.setVlDesconto(vl_desconto);
        im.setCanceladoMovimento(cancelado_movimento);
        im.setDtCancelado(dt_cancelamento);
        im.setMotivo(motivo);
        im.setTpMovimento(tp_movimento);
        im.setUsuario(usuario);
        im.setDtCalculo(new Date());

        return adicionarEntidade(IssqnMovimento.class, im);
    }

    public Taxa carregarHorarioEspecial(double ufm, double ufesp, double valor) {
        List<Object[]> taxasFixas = getListaPura(Object[].class, "select t.id,t.valor,t.tpValor,t.nome,0 as parente,t.tipo,t.banco.id from IssqnTaxa t where t.nome like '%HORÁRIO ESPECIAL%'");
        for (Object[] taxaFixa : taxasFixas) {
            Taxa t = new Taxa(ufm, ufesp);
            t.id_taxa = Util.extractInt(taxaFixa[0]);
            t.nome = Util.extractStr(taxaFixa[3]);
            t.id_parente = 0;
            t.valor = valor;
            String tpValor = Util.extractStr(taxaFixa[2]);
            t.tipo = Taxa.TipoValor.rs;
            t.nome_taxa = Util.extractStr(taxaFixa[3]);
            t.estimado = false;
            t.cobranca_unica = Util.extractInt(taxaFixa[5]);
            t.id_banco = Util.extractInt(taxaFixa[6]) == 0 ? null : Util.extractInt(taxaFixa[6]);
            return t;
        }
        return null;
    }

    public LinkedList<Taxa> carregarTaxas(int id_iss, double ufm, double ufesp, Integer tipoCobranca) {
        LinkedList<Taxa> saida = new LinkedList();

        List<Object[]> taxasFixas = getListaPura(Object[].class, "select t.id,t.valor,t.tpValor,t.nome,0 as parente,t.tipo,t.banco.id from IssqnTaxa t where t.taxaFixa = true");
        for (Object[] taxaFixa : taxasFixas) {
            Taxa t = new Taxa(ufm, ufesp);
            t.id_taxa = Util.extractInt(taxaFixa[0]);
            t.nome = Util.extractStr(taxaFixa[3]);
            t.id_parente = 0;
            t.valor = Util.extractDouble(taxaFixa[1]);
            String tpValor = Util.extractStr(taxaFixa[2]);
            if (tpValor == null || tpValor.toUpperCase().equals("UFM")) {
                t.tipo = Taxa.TipoValor.ufm;
            } else {
                t.tipo = Taxa.TipoValor.rs;
            }
            t.nome_taxa = Util.extractStr(taxaFixa[3]);
            t.estimado = false;
            t.cobranca_unica = Util.extractInt(taxaFixa[5]);
            t.id_banco = Util.extractInt(taxaFixa[6]) == 0 ? null : Util.extractInt(taxaFixa[6]);
            saida.addLast(t);
        }

        List<Object[]> taxas = getListaPura(Object[].class,
                " select t.id,i.vlTaxa,t.tpValor,t.nome,0 as parente,t.tipo,b.id,t.tipo from IssqnContribuinteTaxa i inner join i.taxa t left join t.banco b"
                + " where i.iss.id = ?1 and i.vlTaxa is not null and t.taxaFixa = false", id_iss);
        for (Object[] taxa : taxas) {
            Taxa t = new Taxa(ufm, ufesp);
            if (tipoCobranca != 3 && !(Util.extractStr(taxa[3]).contains("EVENTUAL")
                    || Util.extractStr(taxa[3]).contains("DIVERSÕES PÚBLICAS")
                    || Util.extractStr(taxa[3]).contains("PUBLICIDADE"))) {
                if (Util.extractInt(taxa[7]) == 1) { // Cobrar uma unica vez na vida
                    Issqn i = getEntidade(Issqn.class, id_iss);
                    List<Number> existeCobranca = getListaPura(Number.class,
                            "select count(mpi) from IssqnMovimentoParcelaItem mpi join mpi.parcela mp join mp.iss i \n"
                            + "where mpi.idTaxa = ?1 and i.inscricao = ?2", Util.extractInt(taxa[0]), i.getInscricao());
                    if (existeCobranca.get(0).intValue() > 0) {
                        continue; //Caso tenha algum calculo pra esta taxa nos exercicios anteriores não deve cobrar novamente
                    }
                }
                t.id_taxa = Util.extractInt(taxa[0]);
                t.nome = Util.extractStr(taxa[3]);
                t.id_parente = 0;
                t.valor = Util.extractDouble(taxa[1]);
                String tpValor = Util.extractStr(taxa[2]);
                if (tpValor == null || tpValor.toUpperCase().equals("UFM")) {
                    t.tipo = Taxa.TipoValor.ufm;
                } else if (tpValor.toUpperCase().equals("UFESP")) {
                    t.tipo = Taxa.TipoValor.ufesp;
                } else {
                    t.tipo = Taxa.TipoValor.rs;
                }
                t.nome_taxa = Util.extractStr(taxa[3]);
                t.estimado = false;
                t.cobranca_unica = Util.extractInt(taxa[5]);
                t.id_banco = Util.extractInt(taxa[6]) == 0 ? null : Util.extractInt(taxa[6]);
                saida.add(t);
            } else if (tipoCobranca == 3 && (Util.extractStr(taxa[3]).contains("EVENTUAL")
                    || Util.extractStr(taxa[3]).contains("DIVERSÕES PÚBLICAS")
                    || Util.extractStr(taxa[3]).contains("PUBLICIDADE"))) { //eventual
//                if (!entrou) {
//                    saida.clear();
//                    entrou = true;
//                }

                t.id_taxa = Util.extractInt(taxa[0]);
                t.nome = Util.extractStr(taxa[3]);
                t.id_parente = 0;
                t.valor = Util.extractDouble(taxa[1]);
                String tpValor = Util.extractStr(taxa[2]);
                if (tpValor == null || tpValor.toUpperCase().equals("UFM")) {
                    t.tipo = Taxa.TipoValor.ufm;
                } else if (tpValor.toUpperCase().equals("UFESP")) {
                    t.tipo = Taxa.TipoValor.ufesp;
                } else {
                    t.tipo = Taxa.TipoValor.rs;
                }
                t.nome_taxa = Util.extractStr(taxa[3]);
                t.estimado = false;
                t.cobranca_unica = Util.extractInt(taxa[5]);
                t.id_banco = Util.extractInt(taxa[6]) == 0 ? null : Util.extractInt(taxa[6]);
                saida.add(t);
            }
        }

        return saida;
    }

    public static int getNumMesesEntreDatas(java.util.Date dt1, java.util.Date dt2) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dt1);
        int ano1 = gc.get(Calendar.YEAR);
        int mes1 = gc.get(Calendar.MONTH);

        gc.setTime(dt2);
        int ano2 = gc.get(Calendar.YEAR);
        int mes2 = gc.get(Calendar.MONTH);

        int diffAno = (ano2 - ano1) * 12;
        return diffAno + mes2 - mes1 + 1;
    }

    public abstract void calcular(int id_iss, boolean proporcional, int id_exercicio, String usuario, Integer tipoCobranca, List<Taxa> lstBoleto2, boolean geral) throws RuntimeException, BusinessViolation, Exception;

    public abstract void calculoGeral(int id_exercicio, String usuario, boolean proporcional, Date dataAberturaMaxima, Integer tipoCobranca) throws RuntimeException, BusinessViolation, Exception;

    public abstract void calculoContribuinte(Issqn issqn, String usuario, boolean proporcional, IssqnMotivoCancel moticoCancelamento, Integer tipoCobranca) throws RuntimeException, BusinessViolation, Exception;

    protected IssqnMovimentoParcela registrarIssqn_movimento_parcela(IssqnMovimento id_movimento,
            IssqnTipoCobranca id_tipo_cobranca, int parcela, double vl_parcela, double vl_desconto,
            Date dt_vencimento, String dt_pagamento, String dt_baixa, String dt_cancelado_pagamento,
            Integer id_motivo_cancel, Issqn id_iss, double vl_total, Integer id_banco,
            Integer lote, String agencia, Integer arrecadado, Integer cancelado_pagamento,
            long nosso_numero, double vl_expediente, int id_exercicio) throws RuntimeException, Exception {

        IssqnMovimentoParcela mp = new IssqnMovimentoParcela();
        mp.setMovimento(id_movimento);
        mp.setIss(id_iss);
        mp.setVlTotal(vl_total);
        mp.setVlDesconto(vl_desconto);
        mp.setParcela(parcela);
        mp.setVlParcela(vl_parcela);
        mp.setIdBanco(id_banco);
        mp.setAgencia(agencia);
        mp.setDtVencimento(dt_vencimento);
        mp.setDtPagamento(dt_pagamento == null ? null : Util.parseBrStrToDate(dt_pagamento));
        mp.setDtBaixa(dt_baixa == null ? null : Util.parseBrStrToDate(dt_baixa));
        mp.setLote(lote);
        mp.setArrecadado(arrecadado);
        mp.setCanceladoPagamento(cancelado_pagamento);
        mp.setDtCanceladoPagamento(dt_cancelado_pagamento == null ? null : Util.parseBrStrToDate(dt_cancelado_pagamento));
        mp.setIdMotivoCancel(id_motivo_cancel);
        mp.setIdTipoCobranca(id_tipo_cobranca);
        mp.setNossoNumero(nosso_numero);
        mp.setIdExercicio(id_exercicio);
        mp.setVlExpediente(vl_expediente);

        return adicionarEntidade(IssqnMovimentoParcela.class, mp);
    }

    protected void registrarIssqn_movimento_parcela_item(IssqnMovimentoParcela id_movimento_parcela,
            Integer id_taxa, double vl_taxa, double vl_desconto) throws RuntimeException {
        IssqnMovimentoParcelaItem mpi = new IssqnMovimentoParcelaItem();
        mpi.setParcela(id_movimento_parcela);
        mpi.setIdTaxa(id_taxa);
        mpi.setVlTaxa(vl_taxa);
        mpi.setVlDesconto(vl_desconto);
        adicionarEntidade(IssqnMovimentoParcelaItem.class, mpi);
    }

    public void cancelarMovimento(Issqn issqn, IssqnMotivoCancel motivo, String processo, Integer tipoCobranca) throws BusinessViolation {
        if (!existePagamento(issqn)) {
            excluirMovimento(issqn.getIdExercicio(), issqn.getId(), tipoCobranca);
            return;
        }

        if (tipoCobranca == 0) {
            List<IssqnMovimento> lm = getListaPura(IssqnMovimento.class, "select m from IssqnMovimento m "
                    + " inner join m.iss i where i.id = ?1 and m.dtCancelado is null", issqn.getId());
            for (IssqnMovimento m : lm) {
                if (!issqn.getEncerrado() || !issqn.getInativo()) {
                    m.setCanceladoMovimento(1);
                    m.setDtCancelado(new Date());
                    m.setMotivo(motivo.getDescricao());
                    m.setNumeroProcesso(processo);
                    setEntidade(IssqnMovimento.class, m);
                }
                Date dataAtualAux = issqn.getInativo() ? issqn.getDtInatividade() : issqn.getDtEncerramento();
                if (dataAtualAux == null) {
                    dataAtualAux = new Date();
                    dataAtualAux = Util.dateToday();
                }
                Calendar dataEncerradoInativo = new GregorianCalendar();
                dataEncerradoInativo.setTimeInMillis(dataAtualAux.getTime());
                List<IssqnMovimentoParcela> lp = getListaPura(IssqnMovimentoParcela.class, "select m from IssqnMovimentoParcela m "
                        + " inner join m.movimento i where i.id = ?1 and m.dtCanceladoPagamento is null", m.getId());
                for (IssqnMovimentoParcela mp : lp) {
                    if (issqn.getEncerrado() || issqn.getInativo()) {
                        Calendar vencimento = new GregorianCalendar();
                        vencimento.setTime(mp.getDtVencimento());
                        if (mp.getIdTipoCobranca().getQtdParcela() == 1 && mp.getIdTipoCobranca().getIdExercicio().intValue() == issqn.getIdExercicio().intValue()) {
                            mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                            mp.setObsCancel(motivo.getDescricao());
                            mp.setCanceladoPagamento(1);
                            mp.setStatus(2);
                            setEntidade(IssqnMovimentoParcela.class, mp);
                        }
                        if (dataEncerradoInativo.before(vencimento)) {
                            if (dataEncerradoInativo.get(Calendar.DAY_OF_MONTH) < 15) {
                                mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                                mp.setObsCancel(motivo.getDescricao());
                                mp.setIdMotivoCancel(motivo.getId());
                                mp.setCanceladoPagamento(1);
                                mp.setStatus(2);
                                setEntidade(IssqnMovimentoParcela.class, mp);
                            } else {
                                if (dataEncerradoInativo.get(Calendar.MONTH) + 1 == vencimento.get(Calendar.MONTH)) {
                                    continue;
                                }
                                mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                                mp.setObsCancel(motivo.getDescricao());
                                mp.setIdMotivoCancel(motivo.getId());
                                mp.setCanceladoPagamento(1);
                                mp.setStatus(2);
                                setEntidade(IssqnMovimentoParcela.class, mp);
                            }
                        }
                    } else {
                        mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                        mp.setObsCancel(motivo.getDescricao());
                        mp.setIdMotivoCancel(motivo.getId());
                        mp.setCanceladoPagamento(1);
                        mp.setStatus(2);
                        setEntidade(IssqnMovimentoParcela.class, mp);
                    }
                }
            }
        } else {
            List<IssqnMovimentoParcela> lp = getListaPura(IssqnMovimentoParcela.class, "select mp from IssqnMovimentoParcela mp "
                    + " inner join mp.movimento m where mp.iss.id = ?1 and m.dtCancelado is null and mp.idTipoCobranca.tpCobranca = ?2", issqn.getId(), tipoCobranca);

            Date dataAtualAux = issqn.getInativo() ? issqn.getDtInatividade() : issqn.getDtEncerramento();
            if (dataAtualAux == null) {
                dataAtualAux = new Date();
                dataAtualAux = Util.dateToday();
            }
            Calendar dataEncerradoInativo = new GregorianCalendar();
            dataEncerradoInativo.setTimeInMillis(dataAtualAux.getTime());
            for (IssqnMovimentoParcela mp : lp) {
                if (issqn.getEncerrado() || issqn.getInativo()) {
                    Calendar vencimento = new GregorianCalendar();
                    vencimento.setTime(mp.getDtVencimento());
                    if (mp.getIdTipoCobranca().getQtdParcela() == 1 && mp.getIdTipoCobranca().getIdExercicio().intValue() == issqn.getIdExercicio().intValue()) {
                        mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                        mp.setObsCancel(motivo.getDescricao());
                        mp.setCanceladoPagamento(1);
                        mp.setStatus(2);
                        setEntidade(IssqnMovimentoParcela.class, mp);
                    }
                    if (dataEncerradoInativo.before(vencimento)) {
                        if (dataEncerradoInativo.get(Calendar.DAY_OF_MONTH) < 15) {
                            mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                            mp.setObsCancel(motivo.getDescricao());
                            mp.setIdMotivoCancel(motivo.getId());
                            mp.setCanceladoPagamento(1);
                            mp.setStatus(2);
                            setEntidade(IssqnMovimentoParcela.class, mp);
                        } else {
                            if (dataEncerradoInativo.get(Calendar.MONTH) + 1 == vencimento.get(Calendar.MONTH)) {
                                continue;
                            }
                            mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                            mp.setObsCancel(motivo.getDescricao());
                            mp.setIdMotivoCancel(motivo.getId());
                            mp.setCanceladoPagamento(1);
                            mp.setStatus(2);
                            setEntidade(IssqnMovimentoParcela.class, mp);
                        }
                    }
                } else {
                    mp.setDtCanceladoPagamento(dataEncerradoInativo.getTime());
                    mp.setObsCancel(motivo.getDescricao());
                    mp.setIdMotivoCancel(motivo.getId());
                    mp.setCanceladoPagamento(1);
                    mp.setStatus(2);
                    setEntidade(IssqnMovimentoParcela.class, mp);
                }
            }
        }
    }

    public void cancelarEventual(Issqn issqn, IssqnMotivoCancel motivo, Integer parcela) {
        List<IssqnMovimento> lm = getListaPura(IssqnMovimento.class, "select i from IssqnMovimentoParcela m "
                + " inner join m.movimento i where m.id = ?1 and i.dtCancelado is null", parcela);

        for (IssqnMovimento m : lm) {
            m.setCanceladoMovimento(1);
            m.setDtCancelado(new Date());
            m.setMotivo(motivo.getDescricao());
            setEntidade(IssqnMovimento.class, m);

            List<IssqnMovimentoParcela> lp = getListaPura(IssqnMovimentoParcela.class, "select m from IssqnMovimentoParcela m "
                    + " inner join m.movimento i where i.id = ?1 and m.id = ?2", m.getId(), parcela);
            for (IssqnMovimentoParcela mp : lp) {
                mp.setStatus(2);
                mp.setDtCanceladoPagamento(new Date());
                mp.setObsCancel(motivo.getDescricao());
                mp.setIdMotivoCancel(motivo.getId());
                setEntidade(IssqnMovimentoParcela.class, mp);
            }

        }

    }

    public Boolean existePagamento(Issqn issqn) {

        List<IssqnMovimentoParcela> lp = getListaPura(IssqnMovimentoParcela.class,
                "select m from IssqnMovimentoParcela m "
                + " inner join m.movimento i where i.iss.id = ?1 and i.dtCancelado is null and m.dtPagamento is not null",
                issqn.getId());
        return (!lp.isEmpty());
    }

    public static int diffMeses(java.util.Date dt1, java.util.Date dt2) {
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(dt1);
        cal2.setTime(dt2);
        int qtdMesesIni = (cal.get(Calendar.YEAR) * 12) + cal.get(Calendar.MONTH);
        int qtdMesesFim = (cal2.get(Calendar.YEAR) * 12) + cal2.get(Calendar.MONTH);
        return qtdMesesIni - qtdMesesFim;
    }

    public void excluirMovimento(int id_exercicio, Integer id_iss, int tipoCobranca) throws RuntimeException, BusinessViolation {
        if (existePagamentoExercicio(id_exercicio, id_iss, tipoCobranca)) {
            throw new BusinessViolation("Há pagamento(s) no exercício. Cálculo cancelado.");
        } else {
            String condicaoSql = " and m.id_exercicio = " + id_exercicio;
            String condicaoHql = " where m.idExercicio = " + id_exercicio;
            if (id_iss != null) {
                condicaoSql += " and m.id_iss = " + id_iss;
                condicaoHql += " and m.iss.id = " + id_iss;
            }
            if (tipoCobranca != 0) {
                condicaoSql += "  and tc.tp_cobranca in (" + (tipoCobranca == 1 ? "1,2" : "3") + ")";
                condicaoHql += " and mp.idTipoCobranca.tpCobranca = " + tipoCobranca;
            }
            List<IssqnMovimento> movimentos = new ArrayList<>();
            movimentos = getListaPura(IssqnMovimento.class,
                    "select distinct m from IssqnMovimentoParcela mp "
                    + " inner join mp.movimento m " + condicaoHql);

            executeCommand("delete from issqn_movimento_parcela_item mpi using "
                    + " issqn_movimento_parcela mp "
                    + " inner join issqn_movimento m on m.id_movimento = mp.id_movimento "
                    + " INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = MP.ID_TIPO_COBRANCA"
                    + " where mpi.id_movimento_parcela = mp.id_movimento_parcela " + condicaoSql);

            executeCommand("delete from issqn_movimento_parcela mp using "
                    + " issqn_movimento m "
                    + " INNER JOIN ISSQN_MOVIMENTO_PARCELA IMP ON IMP.ID_MOVIMENTO = M.ID_MOVIMENTO "
                    + " INNER JOIN ISSQN_TIPO_COBRANCA TC on TC.ID_TIPO_COBRANCA = IMP.ID_TIPO_COBRANCA"
                    + " where IMP.ID_MOVIMENTO_PARCELA = MP.ID_MOVIMENTO_PARCELA AND mp.id_movimento = m.id_movimento " + condicaoSql);
            for (IssqnMovimento m : movimentos) {
                executeCommand("delete from issqn_movimento "
                        + " where id_exercicio = ?1 and id_movimento = ?2" + (id_iss == null ? " " : " and id_iss = " + id_iss), id_exercicio, m.getId());
            }
        }
    }

    protected boolean existePagamentoExercicio(int id_exercicio, Integer id_iss, Integer tipoCobranca) {
        Object[] lo;
        if (id_iss != null) {
            lo = new Object[]{id_exercicio, id_iss};
        } else {
            lo = new Object[]{id_exercicio};

        }
        String condicaoHql = "";
        if (tipoCobranca != 0) {
            condicaoHql += " and p.idTipoCobranca.tpCobranca = " + tipoCobranca;
        }
        Number existe = getEntidadePura(Number.class,
                "select coalesce(count(p.id), 0) from IssqnMovimentoParcela p "
                + "where p.movimento.idExercicio = ?1 and p.dtPagamento is not null and (p.canceladoPagamento = 0 or p.canceladoPagamento is null) " + (id_iss == null ? "" : " and p.movimento.iss.id = ?2 " + condicaoHql), lo);
        return existe.intValue() > 0;
    }

    protected boolean existeCalculoExercicio(int id_exercicio, Integer id_iss) {
        Object[] lo;
        if (id_iss != null) {
            lo = new Object[]{id_exercicio, id_iss};
        } else {
            lo = new Object[]{id_exercicio};

        }
        Number existe = getEntidadePura(Number.class,
                "select coalesce(count(p.id), 0) from IssqnMovimentoParcela p "
                + "where p.movimento.idExercicio = ?1 " + (id_iss == null ? "" : " and p.movimento.iss.id = ?2 "), lo);
        return existe.intValue() > 0;

    }

    public boolean existeCalculoTipoCobranca(int id_exercicio, Integer id_iss, Integer tipoCobranca) {

        Number existe = getEntidadePura(Number.class,
                "select coalesce(count(p.id), 0) from IssqnMovimentoParcela p "
                + "where p.movimento.idExercicio = ?1 and p.movimento.iss.id = ?2" + (tipoCobranca == 0 ? "" : " and p.idTipoCobranca.tpCobranca = " + tipoCobranca)
                + " and p.movimento.dtCancelado is null", id_exercicio, id_iss);
        return existe.intValue() > 0;

    }

    public static class Taxa {

        public TipoValor getTipo() {
            return tipo;
        }

        /**
         * @return the estimado
         */
        public boolean isEstimado() {
            return estimado;
        }

        /**
         * @param estimado the estimado to set
         */
        public void setEstimado(boolean estimado) {
            this.estimado = estimado;
        }

        /**
         * @return the nome
         */
        public String getNome() {
            return nome;
        }

        public String getNome_taxa() {
            return nome_taxa;
        }

        public void setNome_taxa(String nome_taxa) {
            this.nome_taxa = nome_taxa;
        }

        public Integer getId_parente() {
            return id_parente;
        }

        public void setId_parente(Integer id_parente) {
            this.id_parente = id_parente;
        }

        public static enum TipoValor {

            ufm, rs, ufesp
        };
        private Integer id_taxa;
        private Integer id_banco;
        private double valor;
        private TipoValor tipo;
        private double ufm;
        private double ufesp;
        private boolean estimado;
        private String nome;
        private int cobranca_unica;
        private String nome_taxa;
        private Integer id_parente;

        public double getValorCalculado() {
            switch (getTipo()) {
                case rs:
                    return valor;

                case ufm:
                    return ufm * valor;

                case ufesp:
                    return ufesp * valor;

                default:
                    throw new IllegalArgumentException("Tipo de valor desconhecido da benfeitoria.");
            }
        }

        Taxa(double ufm, double ufesp) {
            this.ufm = ufm;
            this.ufesp = ufesp;
        }

        Taxa() {

        }

        public Integer getId_Taxa() {
            return id_taxa;
        }

        public double getValor() {
            return valor;
        }

        public int getCobranca_unica() {
            return cobranca_unica;
        }

        public void setCobranca_unica(int cobranca_unica) {
            this.cobranca_unica = cobranca_unica;
        }

        public Integer getId_banco() {
            return id_banco;
        }

        public void setId_banco(Integer id_banco) {
            this.id_banco = id_banco;
        }

    }
}
