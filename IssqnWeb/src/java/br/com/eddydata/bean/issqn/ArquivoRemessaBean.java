/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.servico.admin.BancoServico;
import br.com.eddydata.servico.issqn.ArquivoRemessaServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ArquivoRemessaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Banco banco;
    private List<Banco> listBanco;

    @EJB
    private ArquivoRemessaServico ArquivoRemessaServico;
    
    @EJB
    private BancoServico bancoServico;
    
    @Inject
    private GlobalBean global;

    public void gerarRemessa() {

        try {
            ArquivoRemessaServico.gerarArquivo(global.getExercicio().getAno(),
                    global.getUsuarioLogado().getOrgao().getIdOrgao(), getBanco());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível gerar o arquivo!");
            System.out.println(ex.getMessage());
        }
    }


    public List<Banco> getListBanco() {
        if (listBanco == null) {
            try {
                listBanco = bancoServico.obterBancos("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar bancos");
                System.out.println("Erro ao buscar bancos\n" + ex.getMessage());
            }
        }
        return listBanco;
    }

    public void setListBanco(List<Banco> listBanco) {
        this.listBanco = listBanco;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
}
