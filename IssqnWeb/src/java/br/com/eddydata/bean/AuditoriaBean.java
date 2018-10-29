/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean;

import br.com.eddydata.auditoria.Auditoria;
import br.com.eddydata.entidade.admin.Usuario;
import br.com.eddydata.servico.admin.UsuarioServico;
import br.com.eddydata.servico.auditoria.AuditoriaServico;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AuditoriaBean implements Serializable {

    @EJB
    private UsuarioServico usuarioServico;
    @EJB
    private AuditoriaServico auditoriaServico;

    @Inject
    private GlobalBean global;

    private final SimpleDateFormat fSqlDate = new SimpleDateFormat("yyyy-MM-dd");

    //filtros
    private List<String[]> listTipoOperacao = new ArrayList<>();
    private String tipoOperacao = "*";
    private Boolean inclusao = true;
    private Boolean alteracao = true;
    private Boolean exclusao = true;
    private Boolean todosSistemas = true;
    private Boolean mostrarComando = false;
    private List<String[]> listPeriodo = new ArrayList<>();
    private String periodo = "I";
    private Date data_ini = null;
    private Date data_fim = new Date();
    private String hora_ini = "";
    private String hora_fim = "";
    private List<Usuario> listUsuario = new ArrayList<>();
    private String usuario = "";
    private String historico = "";
    private Integer idRegistro;
    private Integer n_reg = 1000;
    private String display = "block";

    private List<Auditoria> lstAuditoria = new ArrayList<>();

    @PostConstruct
    public void init() {
        doPreencheTipoOperacao();
        doPreenchePeriodo();
    }

    public void doToggleDisplay() {
        if (periodo.equals("I")) {
            display = "block";
        } else {
            display = "none";
        }
    }

    public void doBuscarAuditorias() {
        if (!inclusao && !alteracao && !exclusao) {
            Funcao.avisoAtencao("Informe pelo menos uma operação para a auditoria");
            return;
        }
        if (periodo.equals("I") && (data_ini == null || data_ini == null)) {
            Funcao.avisoAtencao("Informe as datas do intervalo");
            return;
        }
        try {
            String filtro = "";
            Integer limit = 1000;

            filtro += montarCriteriaTipoOperacao();
            filtro += montarCriteriaOperacao();
            filtro += montarCriteriaPeriodo();

            //AVANÇADO
            if (usuario != null && !usuario.equals("")) {
                filtro += "\n and UPPER(a.usuario) like " + Util.quotarStr("%" + usuario.toUpperCase() + "%");
            }
            if (historico != null && !historico.equals("")) {
                filtro += "\n and UPPER(a.queryMap) like " + Util.quotarStr("%" + historico.toUpperCase() + "%");
            }
            if (idRegistro != null) {
                filtro += "\n and a.idRegistro = " + Util.quotarStr(idRegistro);
            }

            if (n_reg != null && n_reg > 0) {
                limit = n_reg;
            }

            filtro = "".equals(filtro) ? filtro : "\nwhere " + filtro.substring(filtro.indexOf("and ") + 4, filtro.length());

            lstAuditoria = auditoriaServico.obterAuditoriasPorCriteria((todosSistemas ? null : "ISSQN"), filtro, limit);

        } catch (Exception e) {
            Funcao.avisoErro("Erro ao buscar auditorias\n" + e.getMessage());
        }
    }

    public void doPreencheTipoOperacao() {
        String[] str;

        str = new String[2];
        str[0] = "*";
        str[1] = "Todo o Sistema";
        listTipoOperacao.add(str);

        str = new String[2];
        str[0] = "usu";
        str[1] = "Usuários e Perfis";
        listTipoOperacao.add(str);

        str = new String[2];
        str[0] = "param";
        str[1] = "Parâmetros";
        listTipoOperacao.add(str);

    }

    public void doPreenchePeriodo() {
        String[] str;

        str = new String[2];
        str[0] = "*";
        str[1] = "Todo o período";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "I";
        str[1] = "Intervalo entre datas";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "h";
        str[1] = "Hoje";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "o";
        str[1] = "Ontem";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "7";
        str[1] = "Últimos 7 dias";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "m";
        str[1] = "Este mês";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "ult_m";
        str[1] = "Último mês";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "3";
        str[1] = "Últimos 3 meses";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "6";
        str[1] = "Últimos 6 meses";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "12";
        str[1] = "Últimos 12 meses";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "18";
        str[1] = "Últimos 18 meses";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "24";
        str[1] = "Últimos 24 meses";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "a";
        str[1] = "Este ano";
        listPeriodo.add(str);

        str = new String[2];
        str[0] = "a_pas";
        str[1] = "Ano passado";
        listPeriodo.add(str);

    }

    public String montarCriteriaTipoOperacao() {
        if (!tipoOperacao.equals("*")) {
            switch (tipoOperacao) {
                case "usu":
                    return "\n and UPPER(a.tabela) IN("
                            + "'USUARIO', "
                            + "'USUARIOPERFIL'"
                            + ") ";
                case "param":
                    return "\n and UPPER(a.tabela) = 'ISSQNPARAMETRO'";
                default:
                    return "";
            }
        }
        return "";
    }

    private String montarCriteriaOperacao() {
        String _op = "";
        if (inclusao && alteracao && exclusao) {
            return "";
        }
        _op += (inclusao ? "'INSERÇÃO'," : "");
        _op += (alteracao ? "'EDIÇÃO'," : "");
        _op += (exclusao ? "'EXCLUSÃO'," : "");
        if (_op.length() > 0) {
            return "\n and a.operacao IN(" + _op.substring(0, _op.length() - 1) + ") ";
        } else {
            return "";
        }
    }

    private String montarCriteriaPeriodo() {
        hora_ini = hora_ini.replace("_", "0");
        hora_fim = hora_fim.replace("_", "0");
        hora_ini = " " + (hora_ini.equals("") ? "00:00:00" : hora_ini + ":00");
        hora_fim = " " + (hora_fim.equals("") ? "23:59:59" : hora_fim + ":59");

        if (!periodo.equals("*")) {
            GregorianCalendar cal_data = new GregorianCalendar();
            Date dt_tmp;
            switch (periodo) {
                case "I": //intervalo entre datas
                    return "\n and a.data between '" + fSqlDate.format(data_ini) + hora_ini + "' and '" + fSqlDate.format(data_fim) + hora_fim + "' ";
                case "h": //hoje
                    if (hora_ini.length() > 0) {
                        return "\n and a.data between '" + fSqlDate.format(new Date()) + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                    } else {
                        return "\n and a.data = '" + fSqlDate.format(new Date()) + "' ";
                    }
                case "o": //ontem
                    cal_data.setTime(new Date());
                    cal_data.add(Calendar.DAY_OF_MONTH, -1);//ontem
                    dt_tmp = cal_data.getTime();
                    if (hora_ini.length() > 0) {
                        return "\n and a.data between '" + fSqlDate.format(dt_tmp) + hora_ini + "' and '" + fSqlDate.format(dt_tmp) + hora_fim + "' ";
                    } else {
                        return "\n and a.data = '" + fSqlDate.format(dt_tmp) + "' ";
                    }
                case "7": //ultimos 7 dias
                    Calendar cal_data7 = Calendar.getInstance();
                    cal_data7.setTime(new Date());
                    cal_data7.add(Calendar.DAY_OF_MONTH, -7);
                    dt_tmp = cal_data7.getTime();
                    return "\n and a.data between '" + fSqlDate.format(dt_tmp) + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "m": //este mes
                    String data_mes = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.get(Calendar.MONTH) + 1) + "-01";
                    return "\n and a.data between '" + data_mes + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "ult_m": //ultimo mes
                    String data_ult_mes1 = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.get(Calendar.MONTH)) + "-01";
                    String data_ult_mes2 = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.get(Calendar.MONTH)) + "-31";
                    return "\n and a.data between '" + data_ult_mes1 + hora_ini + "' and '" + data_ult_mes2 + hora_fim + "' ";
                case "3": //ultimos 3 meses
                    cal_data.add(Calendar.MONTH, -3);
                    String data_mes3 = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.getTime().getMonth() + 1) + "-01";
                    return "\n and a.data between '" + data_mes3 + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "6": //ultimos 6 meses
                    cal_data.add(Calendar.MONTH, -6);
                    String data_mes6 = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.getTime().getMonth() + 1) + "-01";
                    return "\n and a.data between '" + data_mes6 + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "12": //ultimos 12 meses
                    cal_data.add(Calendar.MONTH, -12);
                    String data_mes12 = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.getTime().getMonth() + 1) + "-01";
                    return "\n and a.data between '" + data_mes12 + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "18": //ultimos 18 meses
                    cal_data.add(Calendar.MONTH, -18);
                    String data_mes18 = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.getTime().getMonth() + 1) + "-01";
                    return "\n and a.data between '" + data_mes18 + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "24": //ultimos 24 meses
                    cal_data.add(Calendar.MONTH, -24);
                    String data_mes24 = cal_data.get(Calendar.YEAR) + "-" + Util.formatDecimal("00", cal_data.getTime().getMonth() + 1) + "-01";
                    return "\n and a.data between '" + data_mes24 + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "a": //este ano
                    String data_ano = cal_data.get(Calendar.YEAR) + "-01-01";
                    return "\n and a.data between '" + data_ano + hora_ini + "' and '" + fSqlDate.format(new Date()) + hora_fim + "' ";
                case "a_pas": //ano passado
                    String data_ano_p1 = (cal_data.get(Calendar.YEAR) - 1) + "-01-01";
                    String data_ano_p2 = (cal_data.get(Calendar.YEAR) - 1) + "-12-31";
                    return "\n and a.data between '" + data_ano_p1 + hora_ini + "' and '" + data_ano_p2 + hora_fim + "' ";
            }
        } else if (!hora_ini.equals(" ") && !hora_fim.equals(" ")) {
            return "\n and a.data between '1900-01-01" + hora_ini + "' and '2099-12-31" + hora_fim + "' ";
        }
        return "";
    }

    public List<String[]> getListTipoOperacao() {
        return listTipoOperacao;
    }

    public void setListTipoOperacao(List<String[]> listTipoOperacao) {
        this.listTipoOperacao = listTipoOperacao;
    }

    public Boolean getInclusao() {
        return inclusao;
    }

    public void setInclusao(Boolean inclusao) {
        this.inclusao = inclusao;
    }

    public Boolean getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Boolean alteracao) {
        this.alteracao = alteracao;
    }

    public Boolean getExclusao() {
        return exclusao;
    }

    public void setExclusao(Boolean exclusao) {
        this.exclusao = exclusao;
    }

    public List<String[]> getListPeriodo() {
        return listPeriodo;
    }

    public void setListPeriodo(List<String[]> listPeriodo) {
        this.listPeriodo = listPeriodo;
    }

    public Date getData_ini() {
        return data_ini;
    }

    public void setData_ini(Date data_ini) {
        this.data_ini = data_ini;
    }

    public Date getData_fim() {
        return data_fim;
    }

    public void setData_fim(Date data_fim) {
        this.data_fim = data_fim;
    }

    public List<Usuario> getListUsuario() {
        if (listUsuario != null && listUsuario.isEmpty()) {
            listUsuario = usuarioServico.getUsuarios(false);
        }
        return listUsuario;
    }

    public String getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(String hora_ini) {
        this.hora_ini = hora_ini;
    }

    public String getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(String hora_fim) {
        this.hora_fim = hora_fim;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Auditoria> getLstAuditoria() {
        return lstAuditoria;
    }

    public void setLstAuditoria(List<Auditoria> lstAuditoria) {
        this.lstAuditoria = lstAuditoria;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Integer getN_reg() {
        return n_reg;
    }

    public void setN_reg(Integer n_reg) {
        this.n_reg = n_reg;
    }

    public Boolean getTodosSistemas() {
        return todosSistemas;
    }

    public void setTodosSistemas(Boolean todosSistemas) {
        this.todosSistemas = todosSistemas;
    }

    public Boolean getMostrarComando() {
        return mostrarComando;
    }

    public void setMostrarComando(Boolean mostrarComando) {
        this.mostrarComando = mostrarComando;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

}
