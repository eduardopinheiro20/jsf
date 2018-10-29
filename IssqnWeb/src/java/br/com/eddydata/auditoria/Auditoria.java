/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.auditoria;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Thiago Martos
 */
@Entity
@Table(name = "auditoria_log")
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String UPDATE_OPERATION = "EDIÇÃO";
    public static final String INSERT_OPERATION = "INSERÇÃO";
    public static final String DELETE_OPERATION = "EXCLUSÃO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    protected Long id;

    @Column(name = "Operacao", length = 50)
    protected String operacao;

    @NotNull(message = "Informe o session_id para a auditoria")
    @Column(name = "session_id", nullable = false)
    protected Integer sessao;

    @NotNull(message = "Informe o login_name para a auditoria")
    @Column(name = "login_name", length = 128, nullable = false)
    protected String usuario;

    @Column(name = "hostname", length = 200)
    protected String host;

    @NotNull(message = "Informe a data para a auditoria")
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    protected Date data;

    @Column(name = "program_name", length = 128)
    protected String programName;

    @Column(name = "sistema", length = 50)
    protected String sistema;

    @Column(name = "Query", length = 5000)
    protected String query;

    @Column(name = "query_map", length = 5000)
    protected String queryMap;

    @Column(name = "tabela", length = 100)
    protected String tabela;

    @Column(name = "id")
    protected Long idRegistro;

    @Column(name = "id_orgao")
    protected String orgao;

    @Column(name = "id_exercicio")
    protected Integer exercicio;

    @Column(name = "tipo", length = 3)
    protected String tipo;

    public Auditoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public Integer getSessao() {
        return sessao;
    }

    public void setSessao(Integer sessao) {
        this.sessao = sessao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public Long getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Long idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(String queryMap) {
        this.queryMap = queryMap;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Auditoria) {
            if (Objects.equals(((Auditoria) obj).getId(), id)) {
                return true;
            }
        }
        return false;
    }
}
