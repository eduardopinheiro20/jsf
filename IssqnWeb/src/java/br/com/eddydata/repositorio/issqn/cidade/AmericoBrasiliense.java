/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn.cidade;

import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.entidade.issqn.IssqnMovimento;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.entidade.issqn.IssqnTipoCobrancaItem;
import br.com.eddydata.repositorio.issqn.CalculoRepositorio;
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
 * @author lucas
 */
public class AmericoBrasiliense extends CalculoRepositorio {

    public AmericoBrasiliense(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void calcular(int id_iss, boolean proporcional, int id_exercicio, String usuario, Integer tipoCobranca, List<Taxa> lstBoleto2, boolean geral) throws RuntimeException, BusinessViolation, Exception {

        Issqn issqn = getEntidadePura(Issqn.class, "select i from Issqn i where i.id = ?1 and i.idExercicio = ?2", id_iss, id_exercicio);
        Calendar dtAbertura = Calendar.getInstance();
        dtAbertura.setTime(issqn.getDtAbertura());
        int diffMesAbertura = 0;
        if (dtAbertura.get(Calendar.YEAR) >= id_exercicio) { // proporcional gerar somente parcela unica quando a data de abertura for o mesmo ano do exercicio
            diffMesAbertura = 1;
        } else {
            diffMesAbertura = 13;
        }

        if (issqn.getInativo() != null && issqn.getInativo().booleanValue()) {
            if (geral) {
                return;
            } else {
                throw new BusinessViolation("Contribuinte Inativo! Não é possível efetuar cálculo");
            }
        }
        if (issqn.getEncerrado() != null && issqn.getEncerrado().booleanValue()) {
            if (geral) {
                return;
            } else {
                throw new BusinessViolation("Contribuinte Encerrado! Não é possível efetuar cálculo");
            }
        }
        if (issqn.getBloqueado() != null && issqn.getBloqueado().booleanValue()) {
            if (geral) {
                return;
            } else {
                throw new BusinessViolation("Contribuinte Bloqueado! Não é possível efetuar cálculo");
            }
        }
        if (issqn.getIsento() != null && !issqn.getIsento().equals("false")) { // lei do municipio, calculo da categoria "EVENTUAL" não será realizado
            if (geral) {
                return;
            } else {
                throw new BusinessViolation("Contribuinte Isento! Não é possível efetuar cálculo");
            }
        }
        if (issqn.getMei() != null && issqn.getMei().booleanValue()) {
            if (geral) {
                return;
            } else {
                throw new BusinessViolation("Contribuinte Mei! Não é possível efetuar cálculo");
            }
        }

        double vl_taxas = 0.0;
        double vl_taxas_rs = 0.0;
        double vl_total = 0.0;
        double vl_desc = 0.0;
        double vl_expediente = 0.0;
        int num_parcela = 1;

        IssqnConfiguracao parametro = getEntidadePura(IssqnConfiguracao.class, "select p from IssqnConfiguracao p");
        vl_expediente = parametro.getExpediente();
        if (parametro.getUfm() == null || parametro.getUfesp() == null) {
            throw new BusinessViolation("Parametrizar valores da UFM e UFESP");
        }

        //Carregar as taxas com valor do contribuiente selecionado
        LinkedList<Taxa> taxas = carregarTaxas(id_iss, parametro.getUfm(), parametro.getUfesp(), tipoCobranca);

        if (!lstBoleto2.isEmpty()) {
            taxas.clear();
            taxas.addAll(lstBoleto2);
        }

        boolean estimado = false;
        Integer id_banco = null;
        List<Taxa> taxaBoleto2 = new ArrayList<>();
        LinkedList<Taxa> taxasAux = (LinkedList<Taxa>) taxas.clone();
        boolean bilheteria = true;
        double horario_especial = 0.0;
        for (Taxa t : taxas) {
            if ((t.getNome().contains("EXPEDIENTE") || t.getNome().contains("IMPOSTO SOBRE SERVIÇOS DE QUALQUER NATUREZA")) && taxas.size() == 2) {
                return;
            }

            if (id_banco != null && id_banco != t.getId_banco()) { //Boletos com bancos diferentes
                taxaBoleto2.add(t);
                taxasAux.remove(t);
                continue;
            } else {
                id_banco = t.getId_banco();
            }

            if (tipoCobranca == 3) {
                if (t.getNome().contains("EVENTUAL") || t.getNome_taxa().contains("EVENTUAL")) {
                    vl_taxas += t.getValorCalculado();
                    estimado |= t.isEstimado();
                    continue;
                }
                if (t.getNome().contains("DIVERSÕES PÚBLICAS") || t.getNome_taxa().contains("DIVERSÕES PÚBLICAS")) {
                    vl_taxas += t.getValorCalculado();
                    estimado |= t.isEstimado();
                    continue;
                }
                if (t.getNome().contains("PUBLICIDADE") || t.getNome_taxa().contains("PUBLICIDADE")) {
                    vl_taxas += t.getValorCalculado();
                    estimado |= t.isEstimado();
                    continue;
                }
                if (issqn.getVlBilheteria() != null && bilheteria) {
                    vl_taxas += Util.truncarValor((issqn.getVlBilheteria() * 0.03), 2);
                    bilheteria = false;
                }
            } else if (t.getNome().contains("EVENTUAL") || t.getNome_taxa().contains("EVENTUAL")
                    || t.getNome().contains("PUBLICIDADE") || t.getNome_taxa().contains("PUBLICIDADE")
                    || t.getNome().contains("DIVERSÕES PÚBLICAS") || t.getNome_taxa().contains("DIVERSÕES PÚBLICAS")) {
                continue;
            }

            if (t.getNome().contains("EXPEDIENTE") || t.getNome_taxa().contains("EXPEDIENTE")) {
                vl_expediente = parametro.getExpediente();
                vl_taxas += vl_expediente;
                vl_taxas_rs += vl_expediente;
                estimado |= t.isEstimado();

            } else if (issqn.getHorarioEspecial() && t.getNome().contains("TAXA DE CONTROLE E FISCAL")) {
                vl_taxas += Util.truncarValor(t.getValorCalculado() + (t.getValorCalculado() * 0.3) + 0.005, 2);
                horario_especial = Util.truncarValor((t.getValorCalculado() * 0.3) + 0.005, 2);
            } else {
                vl_taxas += t.getValorCalculado();
                estimado |= t.isEstimado();
            }
            if (t.getTipo() == Taxa.TipoValor.rs) {
                vl_taxas_rs += t.getValor();
            }

        }
        if (horario_especial > 0.0) {
            taxasAux.add(carregarHorarioEspecial(parametro.getUfm(), parametro.getUfesp(), horario_especial));
        }

        taxas.clear();
        taxas.addAll(taxasAux);

        if (carregarTiposCobranca(id_exercicio, tipoCobranca).isEmpty()) { //Valida se existe o cadastro de vencimentos
            throw new BusinessViolation("Não há taxas ou vencimentos cadastrados, efetuar cadastro!");
        }

        if (vl_taxas == 0.0 || vl_taxas == 0) {
            throw new BusinessViolation("Não há valor cadastrado para nenhuma taxa!");
        }

        if (proporcional && tipoCobranca != 3) {
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(issqn.getDtAbertura());
            cal2.setTime(Util.parseBrStrToDate("31/12/" + id_exercicio));
            int diffMes = Util.mesesEntre(cal, cal2) + 1;
            if (diffMes < 0) {
                throw new RuntimeException("Data de abertura do ISS id. " + id_iss
                        + " incompatível com o exercício.");
            }

            vl_taxas -= vl_taxas_rs; // Taxas do tipo RS não pode ser proporcional
            vl_desc = vl_taxas;
            double fator = vl_taxas / 12;
            vl_taxas = fator * diffMes;
            vl_desc = vl_desc - vl_taxas;
            vl_taxas += vl_taxas_rs;
        }

        int tp_movimento;
        if (estimado) {
            tp_movimento = 3;
        } else {
            tp_movimento = 2;
        }

        vl_total = Util.truncarValor(vl_taxas + 0.005, 2);

        IssqnMovimento movimento = registrarIssqn_movimento(issqn, vl_total, 0, 0,
                null, null, null, tp_movimento, usuario, id_exercicio);

        for (IssqnTipoCobranca tc : carregarTiposCobranca(id_exercicio, tipoCobranca)) { // Fazendo calculo pelo tipo de cobrança
            int qtde_parcelas = tc.getQtdParcela();
            if ((tc.getDescricao().contains("EVENTUAL") && tipoCobranca != 3) || (!tc.getDescricao().contains("EVENTUAL") && tipoCobranca == 3)) {
                continue;
            }
            if (qtde_parcelas > 1 && tipoCobranca == 3) { // caso seja eventual será feita só uma parcela unica
                continue;
            }
            if ((qtde_parcelas > 1) && (diffMesAbertura <= 12)) { // Empresas com inscrição aberta em até 12 meses, deverá emitir somente a parcela única 
                continue;
            }

            double vl_parcelado = 0;

            double vl_desconto_parcelas = 0;
            if (tipoCobranca != 3) {
                vl_desconto_parcelas = tc.getVlDesconto();
            }
            IssqnTipoCobranca tipo_cobranca = tc;

            if (tipoCobranca != 3) {
                if (qtde_parcelas > 0) {
                    vl_total = (vl_total - vl_expediente) + (vl_expediente * qtde_parcelas);
                }
            }
            double[] taxa_proporcao = new double[taxas.size()];

            int i = 0;
            for (Taxa t : taxas) {
                if (t.getNome().contains("EXPEDIENTE") || t.getNome_taxa().contains("EXPEDIENTE")) {
                    taxa_proporcao[i++] = Util.truncarValor(((vl_expediente * qtde_parcelas) / (vl_total + vl_desc)) + 0.000005, 5);
                } else if (t.getNome().contains("EVENTUAL") || t.getNome_taxa().contains("EVENTUAL")) {
                    taxa_proporcao[i++] += Util.truncarValor(((t.getValorCalculado()) / ((vl_total + vl_desc) - vl_expediente)) + 0.000005, 5);
                } else {
                    if (taxas.size() == 1) {
                        taxa_proporcao[i++] = 1;
                        continue;
                    }
                    if(qtde_parcelas > 1){
                        taxa_proporcao[i++] += Util.truncarValor((t.getValorCalculado() / ((vl_total + vl_desc) - (vl_expediente * 3))) + 0.000005, 5);
                    }else{                        
                        taxa_proporcao[i++] += Util.truncarValor((t.getValorCalculado() / ((vl_total + vl_desc) - vl_expediente)) + 0.000005, 5);
                    }
                }
            }

            List<IssqnTipoCobrancaItem> itens = getListaPura(IssqnTipoCobrancaItem.class, "select i from IssqnTipoCobrancaItem i where i.tipoCobranca.id = ?1 order by i.parcela asc", tc.getId());
            for (IssqnTipoCobrancaItem itm : itens) {
                if (issqn.getDtEventual() != null && tipoCobranca == 3) {
                    itm.setDtVencimento(issqn.getDtEventual());
                }
                if ((proporcional) && (tc.getQtdParcela() > 1)) {
                    if (itm.getDtVencimento().before(issqn.getDtAbertura())) {
                        qtde_parcelas--;
                        continue;
                    }
                }

                //Inserção nas tabelas de parcela e parcela_item
                double vl_desconto_tipo_pagamento = vl_desconto_parcelas / 100;
                double vl_parcela = Util.truncarValor(vl_total / qtde_parcelas - ((vl_total / qtde_parcelas) * vl_desconto_tipo_pagamento) + 0.005, 2);

                vl_parcelado += vl_parcela;

                num_parcela = itm.getParcela();

                if (!proporcional) {
                    if (num_parcela == qtde_parcelas && qtde_parcelas != 1) {
                        vl_parcela += Util.truncarValor((vl_total - vl_parcelado) + 0.005, 2);
                    }
                } else if (num_parcela == tc.getQtdParcela()) {
                    vl_parcelado = Util.truncarValor(vl_parcelado + 0.005, 2);
                    vl_parcela += vl_total - vl_parcelado;
                }

                Date dt_vencimento = itm.getDtVencimento();
                
                /*
                if ((proporcional) && (qtde_parcelas == 1) && tipoCobranca != 3) { // proporcional adiciona um mês no vencimento
                    Calendar c = new GregorianCalendar();
                    c.setTime(Util.dateToday());
                    c.add(Calendar.MONTH, 1);
                    c.set(Calendar.DAY_OF_MONTH, 10);
                    dt_vencimento = c.getTime();

                }*/
                long nosso_numero = 0;

                nosso_numero = gerarNossoNumero();
                if (nosso_numero == 1) {
                    nosso_numero = 340000001;
                }

                IssqnMovimentoParcela parcela = registrarIssqn_movimento_parcela(
                        movimento, tipo_cobranca, num_parcela, vl_parcela,
                        vl_desconto_parcelas, dt_vencimento, null, null, null, null, issqn, vl_total,
                        id_banco, null, null, null, null, nosso_numero, vl_expediente, id_exercicio);
                i = 0;
                double vl_restante = 0.0;
                for (Taxa t : taxas) {
                    double prop = (taxa_proporcao[i++]);
                    double vl_taxas_item = 0;

                    if (qtde_parcelas == 1) {
                        if (t.getNome().contains("EXPEDIENTE")) {
                            vl_taxas_item = vl_expediente;
                            vl_restante = Util.truncarValor(vl_restante + vl_taxas_item + 0.005, 2);
                        } else if ((t.getNome().contains("IMPOSTO SOBRE SERVIÇOS DE QUALQUER NATUREZA")
                                || t.getNome_taxa().contains("IMPOSTO SOBRE SERVIÇOS DE QUALQUER NATUREZA"))
                                && tipoCobranca == 3 && issqn.getVlBilheteria() != null) {
                            vl_taxas_item = Util.truncarValor((issqn.getVlBilheteria() * 0.03), 2);
                            vl_restante = Util.truncarValor(vl_restante + vl_taxas_item + 0.005, 2);
                        } else if (taxas.size() == 1) { // quando é eventual e tem valor de bilheteria incluso
                            vl_taxas_item = vl_total;
                        } else if (t.getNome().contains("EVENTUAL") || t.getNome_taxa().contains("EVENTUAL")) {
                            vl_taxas_item = Util.truncarValor(t.getValorCalculado(), 2);
                            vl_restante = Util.truncarValor(vl_restante + vl_taxas_item + 0.005, 2);
                        } else if (proporcional) {
                            vl_taxas_item = Util.truncarValor(((vl_parcela - vl_expediente) * prop) + 0.005, 2);
                            vl_restante = Util.truncarValor(vl_restante + vl_taxas_item + 0.005, 2);
                            if (i == taxas.size()) {
                                vl_taxas_item = Util.truncarValor(vl_taxas_item + (vl_parcela - vl_restante) + 0.005, 2);
                            }
                        } else {
                            vl_taxas_item = Util.truncarValor(t.getValorCalculado(), 2);
                            vl_restante = Util.truncarValor(vl_restante + vl_taxas_item + 0.005, 2);
                        }
                    } else {
                        if (t.getNome().contains("EXPEDIENTE")) {
                            vl_taxas_item = vl_expediente;
                        } else {
                            vl_taxas_item = Util.truncarValor(((vl_parcela - vl_expediente) * prop) + 0.005, 2);
                        }
                        vl_restante = Util.truncarValor(vl_restante + vl_taxas_item + 0.005, 2);
                        if (i == taxas.size()) {
                            vl_taxas_item = Util.truncarValor(vl_taxas_item + (vl_parcela - vl_restante) + 0.005, 2);
                        }
                    }
                    if (vl_taxas_item > 0) {
                        registrarIssqn_movimento_parcela_item(parcela, t.getId_Taxa(), vl_taxas_item, vl_desconto_tipo_pagamento * prop);
                    }
                }
            }
        }
        if (!taxaBoleto2.isEmpty()) {
            calcular(id_iss, proporcional, id_exercicio, usuario, tipoCobranca, taxaBoleto2, geral);
        }
    }

    @Override
    public void calculoGeral(int id_exercicio, String usuario,
            boolean proporcional, Date dataAberturaMaxima,
            Integer tipoCobranca) throws RuntimeException, BusinessViolation, Exception {
        if (existePagamentoExercicio(id_exercicio, null, tipoCobranca)) {
            throw new BusinessViolation("Há pagamento(s) no exercício. Cálculo cancelado.");
        } else if (existeCalculoExercicio(id_exercicio, null)) {
            excluirMovimento(id_exercicio, null, tipoCobranca);
        }
        if (dataAberturaMaxima != null) {
            Calendar c = new GregorianCalendar();
            c.setTime(dataAberturaMaxima);
            c.add(Calendar.DATE, 1);
            dataAberturaMaxima = c.getTime();
        } else {
            dataAberturaMaxima = new Date();
        }
        List<Issqn> contribuintes = getListaPura(Issqn.class,
                "select distinct ica from Issqn ica "
                + "where ica.idExercicio = ?1 and ica.dtAbertura < ?2 and ica.dtInatividade is null and ica.dtEncerramento is null "
                + "and ica.autoLancado = false and ica.dtBloqueado is null and coalesce(ica.isento, 'false') = 'false'",
                id_exercicio, dataAberturaMaxima);
        for (Issqn contribuinte : contribuintes) {
            Calendar dtAbertura = Calendar.getInstance();
            dtAbertura.setTime(contribuinte.getDtAbertura());
            proporcional = dtAbertura.get(Calendar.YEAR) >= id_exercicio;
            try {
                calcular(contribuinte.getId(), proporcional, id_exercicio, usuario, tipoCobranca, new ArrayList(), true);
            } catch (BusinessViolation ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void calculoContribuinte(Issqn issqn, String usuario,
            boolean proporcional, IssqnMotivoCancel motivoCancelamento,
            Integer tipoCobranca) throws RuntimeException, BusinessViolation, Exception {
        if (existePagamentoExercicio(issqn.getIdExercicio(), issqn.getId(), tipoCobranca)) {
            throw new BusinessViolation("Há pagamento(s) no exercício. Cálculo cancelado.");
        } else if (existeCalculoExercicio(issqn.getIdExercicio(), issqn.getId())) {
            excluirMovimento(issqn.getIdExercicio(), issqn.getId(), tipoCobranca);
        }
        calcular(issqn.getId(), proporcional, issqn.getIdExercicio(), usuario, tipoCobranca, new ArrayList(), false);
    }
}
