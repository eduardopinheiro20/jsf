/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.entidade.issqn.IssqnHistoricoRegistro;
import br.com.eddydata.servico.issqn.IssqnHistoricoRegistroServico;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class HistoricoRegistroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnHistoricoRegistroServico historicoServico;

    private IssqnHistoricoRegistro selectedRegistro;
    private IssqnHistoricoRegistro selectedRegistroAnterior;
    private List<IssqnHistoricoRegistro> listRegistro;

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
    }

    public void cancelar() {
        selectedRegistro = null;
        carregarListagem();
    }

    public synchronized void salvar() {
        try {
            if (historicoServico.alteracaoRegistro(selectedRegistro)) {
                Funcao.notificacaoSucesso("Alteração liberada com sucesso!");
            }
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
        }

        carregarListagem();
        selectedRegistro = null;
    }

    public synchronized void remover() {
        try {
            historicoServico.removerHistorico(selectedRegistro.getId());
            Funcao.notificacaoSucesso("Negado com sucesso!");
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
        }

        carregarListagem();
        selectedRegistro = null;
    }

    public void alterar(IssqnHistoricoRegistro registroSelecionado) {
        selectedRegistro = registroSelecionado;
        selectedRegistroAnterior = registroSelecionado;
    }

    public String getAlteracoes() {
        if (selectedRegistro != null && !selectedRegistro.equals(selectedRegistroAnterior)) {
            StringBuilder sb = new StringBuilder();
            if (selectedRegistro.getDataCadastro() != null ) {
                sb.append("<br/><b>Data da Alteração:</b> ");
                sb.append(Util.parseSqlToBrDate(selectedRegistro.getDataCadastro()));
            }
            if (selectedRegistro.getNome() != null && !selectedRegistro.getNome().equals(selectedRegistroAnterior.getNome())) {
                sb.append("<br/><b>Nome:</b> ");
                sb.append(selectedRegistro.getNome());
            }
            if (selectedRegistro.getNomeFantasia() != null && !selectedRegistro.getNomeFantasia().equals(selectedRegistroAnterior.getNomeFantasia())) {
                sb.append("<br/><b>Nome Fantasia:</b> ");
                sb.append(selectedRegistro.getNomeFantasia());
            }
            if (selectedRegistro.getTpPessoa() != null && !selectedRegistro.getTpPessoa().equals(selectedRegistroAnterior.getTpPessoa())) {
                sb.append("<br/><b>Tipo Pessoa:</b> ");
                sb.append(selectedRegistro.getTpPessoa().getDescricao());
            }
            if (selectedRegistro.getCartaoCidadao() != null && !selectedRegistro.getCartaoCidadao().equals(selectedRegistroAnterior.getCartaoCidadao())) {
                sb.append("<br/><b>Cartão Cidadão:</b> ");
                sb.append(selectedRegistro.getCartaoCidadao());
            }
            if (selectedRegistro.getTelefone() != null && !selectedRegistro.getTelefone().equals(selectedRegistroAnterior.getTelefone())) {
                sb.append("<br/><b>Tel. Res:</b> ");
                sb.append(selectedRegistro.getTelefone());
            }
            if (selectedRegistro.getCelular() != null && !selectedRegistro.getCelular().equals(selectedRegistroAnterior.getCelular())) {
                sb.append("<br/><b>Celular:</b> ");
                sb.append(selectedRegistro.getCelular());
            }
            if (selectedRegistro.getTel1() != null && !selectedRegistro.getTel1().equals(selectedRegistroAnterior.getTel1())) {
                sb.append("<br/><b>Tel. Com:</b> ");
                sb.append(selectedRegistro.getTel1());
            }
            if (selectedRegistro.getTel2() != null && !selectedRegistro.getTel2().equals(selectedRegistroAnterior.getTel2())) {
                sb.append("<br/><b>Tel. Recados:</b> ");
                sb.append(selectedRegistro.getTel2());
            }
            if (selectedRegistro.getDataNascimento() != null && !selectedRegistro.getDataNascimento().equals(selectedRegistroAnterior.getDataNascimento())) {
                sb.append("<br/><b>Nascimento:</b> ");
                sb.append(Util.parseSqlToBrDate(selectedRegistro.getDataNascimento()));
            }
            if (selectedRegistro.getRg() != null && !selectedRegistro.getRg().equals(selectedRegistroAnterior.getRg())) {
                sb.append("<br/><b>Nº. RG:</b> ");
                sb.append(selectedRegistro.getRg());
            }
            if (selectedRegistro.getDtEmissaoRg() != null && !selectedRegistro.getDtEmissaoRg().equals(selectedRegistroAnterior.getDtEmissaoRg())) {
                sb.append("<br/><b>Dt. RG:</b> ");
                sb.append(Util.parseSqlToBrDate(selectedRegistro.getDtEmissaoRg()));
            }
            if (selectedRegistro.getEmail() != null && !selectedRegistro.getEmail().equals(selectedRegistroAnterior.getEmail())) {
                sb.append("<br/><b>Email:</b> ");
                sb.append(selectedRegistro.getEmail());
            }
            if (selectedRegistro.getOrgaoExpRg() != null && !selectedRegistro.getOrgaoExpRg().equals(selectedRegistroAnterior.getOrgaoExpRg())) {
                sb.append("<br/><b>Orgão Exp:</b> ");
                sb.append(selectedRegistro.getOrgaoExpRg().getDescricao());
            }
            if (selectedRegistro.getCertReservista() != null && !selectedRegistro.getCertReservista().equals(selectedRegistroAnterior.getCertReservista())) {
                sb.append("<br/><b>Reservista:</b> ");
                sb.append(selectedRegistro.getCertReservista());
            }
            if (selectedRegistro.getGrauInstrucao() != null && !selectedRegistro.getGrauInstrucao().equals(selectedRegistroAnterior.getGrauInstrucao())) {
                sb.append("<br/><b>Escolaridade:</b> ");
                sb.append(selectedRegistro.getGrauInstrucao());
            }
            if (selectedRegistro.getEstCivil() != null && !selectedRegistro.getEstCivil().equals(selectedRegistroAnterior.getEstCivil())) {
                sb.append("<br/><b>Est. Civil:</b> ");
                sb.append(selectedRegistro.getEstCivil().getDescricao());
            }
            if (selectedRegistro.getCor() != null && !selectedRegistro.getCor().equals(selectedRegistroAnterior.getCor())) {
                sb.append("<br/><b>Cor:</b> ");
                sb.append(selectedRegistro.getCor().getDescricao());
            }
            if (selectedRegistro.getUfRg() != null && !selectedRegistro.getUfRg().equals(selectedRegistroAnterior.getUfRg())) {
                sb.append("<br/><b>UF:</b> ");
                sb.append(selectedRegistro.getUfRg().getDescricao());
            }
            if (selectedRegistro.getSexo() != null && !selectedRegistro.getSexo().equals(selectedRegistroAnterior.getSexo())) {
                sb.append("<br/><b>Sexo:</b> ");
                sb.append(selectedRegistro.getSexo().getDescricao());
            }
            if (selectedRegistro.getTpSanguineo() != null && !selectedRegistro.getTpSanguineo().equals(selectedRegistroAnterior.getTpSanguineo())) {
                sb.append("<br/><b>Tp. Sanguineo:</b> ");
                sb.append(selectedRegistro.getTpSanguineo().getDescricao());
            }
            if (selectedRegistro.getInscEstadual() != null && !selectedRegistro.getInscEstadual().equals(selectedRegistroAnterior.getInscEstadual())) {
                sb.append("<br/><b>Insc. Estadual:</b> ");
                sb.append(selectedRegistro.getInscEstadual());
            }
            if (selectedRegistro.getInscMunicipal() != null && !selectedRegistro.getInscMunicipal().equals(selectedRegistroAnterior.getInscMunicipal())) {
                sb.append("<br/><b>Insc. Municipal:</b> ");
                sb.append(selectedRegistro.getInscMunicipal());
            }
            return sb.toString();
        }
        return "";
    }

    public void carregarListagem() {
        try {
            listRegistro = historicoServico.obterHistoricos(100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//
    public IssqnHistoricoRegistro getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnHistoricoRegistro selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnHistoricoRegistro> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnHistoricoRegistro> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public IssqnHistoricoRegistro getSelectedRegistroAnterior() {
        return selectedRegistroAnterior;
    }

    public void setSelectedRegistroAnterior(IssqnHistoricoRegistro selectedRegistroAnterior) {
        this.selectedRegistroAnterior = selectedRegistroAnterior;
    }
    
    

}
