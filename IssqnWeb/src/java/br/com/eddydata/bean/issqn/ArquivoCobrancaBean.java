/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.servico.issqn.ArquivoCobrancaServico;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ArquivoCobrancaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String opcaoOrdem;
    private String opcaoCarne;
    private String opcaoCobranca;

    @EJB
    private ArquivoCobrancaServico ArquivoCobrancaServico;

    @Inject
    private GlobalBean global;

    @PostConstruct
    public void init() {
        setOpcaoOrdem("I");
        setOpcaoCarne("TCF");
        setOpcaoCobranca("B");
    }

    public void gerarArquivo() {
        String order = "";
        String tipo = "";

        switch (opcaoOrdem) {
            case "I":
                order = "ORDER BY 2";
                break;
            case "C":
                order = "ORDER BY 4";
                break;
            case "EI":
                order = "ORDER BY 5";
                break;
            case "EC":
                order = "ORDER BY 9";
                break;
            default:
                order = "ORDER BY 2";
                break;
        }

        switch (opcaoCarne) {
            case "TCF":
                tipo = " AND TC.TP_COBRANCA = 1";
                break;
            case "TV":
                tipo = " AND TC.TP_COBRANCA = 2";
                break;
            default:
                tipo = " AND TC.TP_COBRANCA = 1";
                break;
        }

        try {
            ArquivoCobrancaServico.gerarArquivo(global.getExercicio().getAno(), order, 
                    global.getUsuarioLogado().getOrgao().getIdOrgao(), tipo, opcaoCobranca);
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível gerar o arquivo!");
            System.out.println(ex.getMessage());
        }
    }

    public String getOpcaoOrdem() {
        return opcaoOrdem;
    }

    public void setOpcaoOrdem(String opcaoOrdem) {
        this.opcaoOrdem = opcaoOrdem;
    }

    public String getOpcaoCarne() {
        return opcaoCarne;
    }

    public void setOpcaoCarne(String opcaoCarne) {
        this.opcaoCarne = opcaoCarne;
    }

    public String getOpcaoCobranca() {
        return opcaoCobranca;
    }

    public void setOpcaoCobranca(String opcaoCobranca) {
        this.opcaoCobranca = opcaoCobranca;
    }

}
