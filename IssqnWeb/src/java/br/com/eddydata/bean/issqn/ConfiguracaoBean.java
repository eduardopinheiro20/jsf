/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnHistoricoTaxa;
import br.com.eddydata.servico.admin.OrgaoServico;
import br.com.eddydata.servico.issqn.IssqnConfiguracaoServico;
import br.com.eddydata.servico.issqn.IssqnHistoricoTaxaServico;
import br.com.eddydata.suporte.BusinessViolation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;

@Named
@ViewScoped
public class ConfiguracaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnConfiguracaoServico servico;
    @EJB
    private OrgaoServico orgaoServico;
    @EJB
    private IssqnHistoricoTaxaServico historicoTaxaServico;

    @Inject
    private GlobalBean global;

    private IssqnConfiguracao selectedRegistro;
    private boolean transacao = false;

    private ContabilOrgao selectedOrgao;
    private String caminhoImagem;
    private IssqnHistoricoTaxa historicoUfm;
    private IssqnHistoricoTaxa historicoUfesp;
    private IssqnHistoricoTaxa historicoExpediente;
    private boolean usaBoleto = false;
    private boolean usaNumCertGeral = false;
    private boolean atribuicaoAutomaticaInscricao = false;
    private boolean inscricaoMunicipal = false;
    private boolean alvaraSequencial = false;
    private boolean trocarNomenclaturaAlvara = false;
    private boolean agruparBoleto = false;
    private List<String[]> listModCarne;
    private List<String[]> listModCertidao;
    private List<String[]> listModAlvara;
    private List<String[]> listModCarta;
    private Integer ano;

    @PostConstruct
    public void init() {
        try {
            selectedRegistro = servico.obterConfiguracao(global.getUsuarioLogado().getOrgao().getIdOrgao());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível buscar os parâmetros do sistema");
            System.out.println("Não foi possível buscar os parâmetros do sistema\n" + ex.getMessage());
        }

        if (selectedRegistro != null) {
            selectedOrgao = selectedRegistro.getOrgao();
            if (selectedOrgao == null) {
                selectedOrgao = global.getUsuarioLogado().getOrgao();
            }
            usaNumCertGeral = (selectedOrgao.getUsaNumCertGeral() != null && selectedOrgao.getUsaNumCertGeral() == 1);
            usaBoleto = (selectedRegistro.getBoleto() != null && selectedRegistro.getBoleto() == 1);
            atribuicaoAutomaticaInscricao = (selectedRegistro.getAtribuicaoAutomaticaInsc() != null && selectedRegistro.getAtribuicaoAutomaticaInsc() == 1);
            inscricaoMunicipal = (selectedRegistro.getUtilizaInscricaoMunicipal() != null && selectedRegistro.getUtilizaInscricaoMunicipal() == 1);
            alvaraSequencial = (selectedRegistro.getUtilizaAlvaraSequencial() != null && selectedRegistro.getUtilizaAlvaraSequencial() == 1);
            trocarNomenclaturaAlvara = (selectedRegistro.getTrocarNomenclaturaAlvara() != null && selectedRegistro.getTrocarNomenclaturaAlvara() == 1);
            agruparBoleto = (selectedRegistro.getAgruparBoleto() != null && selectedRegistro.getAgruparBoleto() == 1);
            historicoUfm = new IssqnHistoricoTaxa();
            historicoUfm.setValorAntigo(selectedRegistro.getUfm());
            historicoUfm.setValorAtual(selectedRegistro.getUfm());
            historicoUfesp = new IssqnHistoricoTaxa();
            historicoUfesp.setValorAntigo(selectedRegistro.getUfesp());
            historicoUfesp.setValorAtual(selectedRegistro.getUfesp());
            historicoExpediente = new IssqnHistoricoTaxa();
            historicoExpediente.setValorAntigo(selectedRegistro.getExpediente());
            historicoExpediente.setValorAtual(selectedRegistro.getExpediente());
        } else {
            usaBoleto = atribuicaoAutomaticaInscricao = inscricaoMunicipal = alvaraSequencial = trocarNomenclaturaAlvara = agruparBoleto = false;
        }
    }

    private void antesSalvar() {
        selectedOrgao.setUsaNumCertGeral(usaNumCertGeral ? 1 : 0);

        selectedRegistro.setBoleto(usaBoleto ? 1 : 0);
        selectedRegistro.setAtribuicaoAutomaticaInsc(atribuicaoAutomaticaInscricao ? 1 : 0);
        selectedRegistro.setUtilizaInscricaoMunicipal(inscricaoMunicipal ? 1 : 0);
        selectedRegistro.setUtilizaAlvaraSequencial(alvaraSequencial ? 1 : 0);
        selectedRegistro.setTrocarNomenclaturaAlvara(trocarNomenclaturaAlvara ? 1 : 0);
        selectedRegistro.setAgruparBoleto(agruparBoleto ? 1 : 0);
    }

    public synchronized void salvar() {
        preSalvar();
    }

    private boolean preSalvar() {
        boolean retorno = salvarRegistro();
        transacao = false;
        return retorno;
    }

    private void salvarOrgao() {
        try {
            selectedOrgao = orgaoServico.setOrgao(selectedOrgao);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar orgão");
            System.out.println("Erro ao salvar orgão\n" + ex.getMessage());
        }
    }

    private boolean salvarRegistro() {
        if (transacao) {
            Funcao.avisoErro("Já existe uma transação em andamento!");
            return false;
        }
        transacao = true;

        antesSalvar();

        try {
            salvarOrgao();
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar orgão");
            System.out.println("Erro ao salvar orgão\n" + ex.getMessage());
            return false;
        }

        try {
            selectedRegistro = servico.salvarConfiguracao(selectedRegistro);
            if (this.historicoUfm.getValorAntigo() != this.selectedRegistro.getUfm()) {
                IssqnHistoricoTaxa issqn = this.historicoTaxaServico.obterUltimoAdd("h2.taxa = 'UFM'");
                if (issqn != null) {
                    this.historicoUfm.setDataInicio(issqn.getDataAlteracao());
                } else {
                    this.historicoUfm.setDataInicio(new Date());
                }
                this.historicoUfm.setValorAtual(this.selectedRegistro.getUfm());
                this.historicoUfm.setDataAlteracao(new Date());
                this.historicoUfm.setTaxa("UFM");
                historicoTaxaServico.salvarHistoricoTaxa(historicoUfm);
                this.historicoUfm.setId(0);
                this.historicoUfm.setValorAntigo(this.selectedRegistro.getUfm());
            }
            if (this.historicoUfesp.getValorAntigo() != this.selectedRegistro.getUfesp()) {
                IssqnHistoricoTaxa issqn = this.historicoTaxaServico.obterUltimoAdd("h2.taxa = 'UFESP'");
                if (issqn != null) {
                    this.historicoUfesp.setDataInicio(issqn.getDataAlteracao());
                } else {
                    this.historicoUfesp.setDataInicio(new Date());
                }
                this.historicoUfesp.setValorAtual(this.selectedRegistro.getUfesp());
                this.historicoUfesp.setDataAlteracao(new Date());
                this.historicoUfesp.setTaxa("UFESP");
                historicoTaxaServico.salvarHistoricoTaxa(historicoUfesp);
                this.historicoUfesp.setId(0);
                this.historicoUfesp.setValorAntigo(this.selectedRegistro.getUfesp());
            }
            if (this.historicoExpediente.getValorAntigo() != this.selectedRegistro.getExpediente()) {
                IssqnHistoricoTaxa issqn = this.historicoTaxaServico.obterUltimoAdd("h2.taxa = 'EXPEDIENTE'");
                if (issqn != null) {
                    this.historicoExpediente.setDataInicio(issqn.getDataAlteracao());
                } else {
                    this.historicoExpediente.setDataInicio(new Date());
                }
                this.historicoExpediente.setValorAtual(this.selectedRegistro.getExpediente());
                this.historicoExpediente.setDataAlteracao(new Date());
                this.historicoExpediente.setTaxa("EXPEDIENTE");
                historicoTaxaServico.salvarHistoricoTaxa(historicoExpediente);
                this.historicoExpediente.setId(0);
                this.historicoExpediente.setValorAntigo(this.selectedRegistro.getExpediente());
            }
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return false;
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        }

        Funcao.notificacaoSucesso("Registro salvo com sucesso!");
        return true;
    }

    public void gerarExercicio() {
        try {
            if (ano != null || ano == 0) {
                servico.verificarExercicio(ano);
            } else {
                Funcao.avisoErro("Informar exercício!");
            }
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao gerar exercicio\n" + ex.getMessage());
        }
    }

    public String getCaminhoImagem(byte[] foto) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            String imageUsers = servletContext.getRealPath("/resources/upload");
            File dirImageUsers = new File(imageUsers);
            if (!dirImageUsers.exists()) {
                dirImageUsers.createNewFile();
            }

            if (foto != null) {
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(dirImageUsers, selectedOrgao.getIdOrgao() + ".png"));
                imageOutput.write(foto, 0, foto.length);
                imageOutput.flush();
                imageOutput.close();

                caminhoImagem = "resources/upload/" + selectedOrgao.getIdOrgao() + ".png";
            } else {
                caminhoImagem = "resources/img/128x128_user.png";
            }

        } catch (FileNotFoundException ex) {
            Funcao.avisoErro("Não foi possível buscar o brasão");
            System.out.println("Erro ao buscar brasão\n" + ex.getMessage());
        } catch (IOException ex) {
            Funcao.avisoErro("Não foi possível buscar o brasão");
            System.out.println("Erro ao buscar brasão\n" + ex.getMessage());
        }

        return caminhoImagem;
    }

    public void fazerUploadArquivo(FileUploadEvent event) {
        selectedOrgao.setBrasao(event.getFile().getContents());
    }

    //------------ getters e setters ---------------//
    public IssqnConfiguracao getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnConfiguracao selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public ContabilOrgao getSelectedOrgao() {
        return selectedOrgao;
    }

    public void setSelectedOrgao(ContabilOrgao selectedOrgao) {
        this.selectedOrgao = selectedOrgao;
    }

    public boolean isUsaNumCertGeral() {
        return usaNumCertGeral;
    }

    public void setUsaNumCertGeral(boolean usaNumCertGeral) {
        this.usaNumCertGeral = usaNumCertGeral;
    }

    public boolean isAtribuicaoAutomaticaInscricao() {
        return atribuicaoAutomaticaInscricao;
    }

    public void setAtribuicaoAutomaticaInscricao(boolean atribuicaoAutomaticaInscricao) {
        this.atribuicaoAutomaticaInscricao = atribuicaoAutomaticaInscricao;
    }

    public boolean isInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(boolean inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public boolean isAlvaraSequencial() {
        return alvaraSequencial;
    }

    public void setAlvaraSequencial(boolean alvaraSequencial) {
        this.alvaraSequencial = alvaraSequencial;
    }

    public boolean isTrocarNomenclaturaAlvara() {
        return trocarNomenclaturaAlvara;
    }

    public void setTrocarNomenclaturaAlvara(boolean trocarNomenclaturaAlvara) {
        this.trocarNomenclaturaAlvara = trocarNomenclaturaAlvara;
    }

    public boolean isAgruparBoleto() {
        return agruparBoleto;
    }

    public void setAgruparBoleto(boolean agruparBoleto) {
        this.agruparBoleto = agruparBoleto;
    }

    public boolean isUsaBoleto() {
        return usaBoleto;
    }

    public void setUsaBoleto(boolean usaBoleto) {
        this.usaBoleto = usaBoleto;
    }

    public List<String[]> getListModCarne() {
        if (listModCarne == null) {
            listModCarne = new ArrayList<>();

            listModCarne.add(new String[]{"0", "NORMAL"});
            listModCarne.add(new String[]{"1", "BOLETO COM ALVARÁ"});
        }
        return listModCarne;
    }

    public void setListModCarne(List<String[]> listModCarne) {
        this.listModCarne = listModCarne;
    }

    public List<String[]> getListModCertidao() {
        if (listModCertidao == null) {
            listModCertidao = new ArrayList<>();

            listModCertidao.add(new String[]{"0", "Modelo 1 - Timbre"});
            listModCertidao.add(new String[]{"1", "Modelo 1 - Sem Timbre"});
            listModCertidao.add(new String[]{"2", "Modelo 2 - Timbre"});
            listModCertidao.add(new String[]{"3", "Modelo 2 - Sem Timbre"});
        }
        return listModCertidao;
    }

    public void setListModCertidao(List<String[]> listModCertidao) {
        this.listModCertidao = listModCertidao;
    }

    public List<String[]> getListModAlvara() {
        if (listModAlvara == null) {
            listModAlvara = new ArrayList<>();

            listModAlvara.add(new String[]{"0", "Modelo 1"});
            listModAlvara.add(new String[]{"1", "Modelo 2"});
            listModAlvara.add(new String[]{"2", "Modelo 3"});
            listModAlvara.add(new String[]{"3", "Modelo 4"});
            listModAlvara.add(new String[]{"4", "Modelo 5"});
            listModAlvara.add(new String[]{"5", "Modelo 6"});
            listModAlvara.add(new String[]{"6", "Modelo 7"});
        }
        return listModAlvara;
    }

    public void setListModAlvara(List<String[]> listModAlvara) {
        this.listModAlvara = listModAlvara;
    }

    public List<String[]> getListModCarta() {
        if (listModCarta == null) {
            listModCarta = new ArrayList<>();

            listModCarta.add(new String[]{"0", "Modelo com descrição das parcelas"});
            listModCarta.add(new String[]{"1", "Modelo sem descrição das parcelas"});
        }
        return listModCarta;
    }

    public void setListModCarta(List<String[]> listModCarta) {
        this.listModCarta = listModCarta;
    }

    public Integer getAno() {
        if (ano == null) {
            ano = global.getExercicio().getAno() + 1;
        }
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

}
