/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.relatorio.RelatorioCartaCobrancaBean.SubRelatorio;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author tales
 */
@Named
@ViewScoped
public class RelatorioCartaCobrancaBean implements Serializable {

    @EJB
    private IssqnServico servico;

    @Inject
    private GlobalBean global;

    private Issqn contribuinte;

    private static final long serialVersionUID = 1L;
    private Date dataInicial = null;
    private Date dataFinal = null;
    private String inscricaoInicial = null;
    private String inscricaoFinal = null;
    private String where = null;
    private Integer order = 0;
    private String filtro = null;
    private boolean frente_verso = false;
    private boolean chkPeriodo = false;
    private boolean chkInscricao = false;
    private String cnpj = null;
    private String enderecoEntrega = null;
    private String enderecoImovel = null;
    private Integer exercicio = null;
    private String texto = null;
    private String ordem = "";
    private String cnpjCpf;

    @PostConstruct
    public void init() {

    }

    public void limpar() {
        dataInicial = null;
        dataFinal = null;
        inscricaoInicial = null;
        inscricaoFinal = null;
    }

    public void imprimir() {
        where = construirWhere();
        try {
            SubRelatorio sub = new SubRelatorio();
            servico.emitirCartaCobranca(global.getUsuarioLogado().getOrgao().getIdOrgao(), chkPeriodo, where, exercicio, ordem, texto, frente_verso, sub);
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public String construirWhere() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (inscricaoInicial != null && inscricaoInicial.length() != 0) {
            if (sb.length() != 0) {
                sb.append("\nAND ");
            } else {
                sb.append(" WHERE ");
            }
            if (exercicio != 0) {
                sb.append(" II.ID_EXERCICIO = " + exercicio);
            } else {
                exercicio = global.getExercicio().getAno();
                sb.append(" II.ID_EXERCICIO = " + exercicio);
            }
            sb.append("\n AND II.INSCRICAO = " + Util.quotarStr(inscricaoInicial));

        } else if (dataInicial != null || dataFinal != null) {
            if (dataInicial != null && dataInicial.toString().trim().length() != 0) {
                if (sb.length() != 0) {
                    sb.append("\nAND ");
                } else {
                    sb.append(" WHERE ");
                }
                sb.append(" CI.DT_VENCIMENTO >= " + Util.quotarStr(sdf.format(dataInicial)));
            }
            if (dataFinal != null && dataInicial.toString().trim().length() != 0) {
                if (sb.length() != 0) {
                    sb.append("\nAND ");
                } else {
                    sb.append(" WHERE ");
                }
                sb.append(" CI.DT_VENCIMENTO <= " + Util.quotarStr(sdf.format(dataFinal)));
            }
        } else {
            if (sb.length() != 0) {
                sb.append("\nAND ");
            } else {
                sb.append(" WHERE ");
            }
            if (exercicio != 0) {
                sb.append(" II.ID_EXERCICIO = " + exercicio);
            } else {
                exercicio = global.getExercicio().getAno();
                sb.append(" II.ID_EXERCICIO = " + exercicio);
            }
        }

        if (order == 0) {
            ordem = "\nORDER BY LOGRA_COBRANCA";
        }
        if (order == 1) {
            ordem = "\nORDER BY LOGRA_IMOVEL";
        }
        if (order == 2) {
            ordem = "\nORDER BY II.INSCRICAO";
        }
        if (order == 3) {
            ordem = "\nORDER BY II.CNPJ_CPF";
        }
        return sb.toString();
    }

    public void selecionarIssqn() {
        if (contribuinte == null) {
            Funcao.avisoAtencao("Contribuinte não selecionado");
            return;
        }
        inscricaoInicial = contribuinte.getInscricao();
        cnpjCpf = contribuinte.getCnpjCpf();
    }

    public void buscaPorInscricao() {
        if (inscricaoInicial == null || inscricaoInicial.trim().equals("")) {
            limpar();
            return;
        }
        try {
            contribuinte = servico.obterIssqnPorInscricao(inscricaoInicial, global.getExercicio().getAno());
            cnpjCpf = contribuinte.getCnpjCpf();
        } catch (BusinessViolation ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar inscrição\n" + ex.getMessage());
        }
        selecionarIssqn();
    }

    public void buscaPorCnpj() {
        if (cnpjCpf == null || cnpjCpf.trim().equals("")) {
            return;
        }
        contribuinte = servico.obterIssqnPorCPF(Util.removerMascara(cnpjCpf));
        selecionarIssqn();
    }

    public static class SubRelatorio {

        private String logradouro_ent;
        private String cidade_ent;
        private String bairro_ent;
        private String complemento_ent;
        private String numero_ent;
        private String uf_ent;
        private String cep_ent;

        public String getLogradouro_ent() {
            return logradouro_ent;
        }

        public void setLogradouro_ent(String logradouro_ent) {
            this.logradouro_ent = logradouro_ent;
        }

        public String getCidade_ent() {
            return cidade_ent;
        }

        public void setCidade_ent(String cidade_ent) {
            this.cidade_ent = cidade_ent;
        }

        public String getBairro_ent() {
            return bairro_ent;
        }

        public void setBairro_ent(String bairro_ent) {
            this.bairro_ent = bairro_ent;
        }

        public String getComplemento_ent() {
            return complemento_ent;
        }

        public void setComplemento_ent(String complemento_ent) {
            this.complemento_ent = complemento_ent;
        }

        public String getNumero_ent() {
            return numero_ent;
        }

        public void setNumero_ent(String numero_ent) {
            this.numero_ent = numero_ent;
        }

        public String getUf_ent() {
            return uf_ent;
        }

        public void setUf_ent(String uf_ent) {
            this.uf_ent = uf_ent;
        }

        public String getCep_ent() {
            return cep_ent;
        }

        public void setCep_ent(String cep_ent) {
            this.cep_ent = cep_ent;
        }

    }

    public IssqnServico getServico() {
        return servico;
    }

    public void setServico(IssqnServico servico) {
        this.servico = servico;
    }

    public GlobalBean getGlobal() {
        return global;
    }

    public void setGlobal(GlobalBean global) {
        this.global = global;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getInscricao() {
        return inscricaoInicial;
    }

    public void setInscricao(String inscricao) {
        this.inscricaoInicial = inscricao;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isChkPeriodo() {
        return chkPeriodo;
    }

    public void setChkPeriodo(boolean periodo) {
        this.chkPeriodo = periodo;
    }

    public Integer getExercicio() {
        if (exercicio == null) {
            exercicio = global.getExercicio().getAno();
        }
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    public String getInscricaoInicial() {
        return inscricaoInicial;
    }

    public void setInscricaoInicial(String inscricaoInicial) {
        this.inscricaoInicial = inscricaoInicial;
    }

    public String getInscricaoFinal() {
        return inscricaoFinal;
    }

    public void setInscricaoFinal(String inscricaoFinal) {
        this.inscricaoFinal = inscricaoFinal;
    }

    public boolean isChkInscricao() {
        return chkInscricao;
    }

    public void setChkInscricao(boolean chkInscricao) {
        this.chkInscricao = chkInscricao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getEnderecoImovel() {
        return enderecoImovel;
    }

    public void setEnderecoImovel(String enderecoImovel) {
        this.enderecoImovel = enderecoImovel;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public boolean isFrente_verso() {
        return frente_verso;
    }

    public void setFrente_verso(boolean frente_verso) {
        this.frente_verso = frente_verso;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

}
