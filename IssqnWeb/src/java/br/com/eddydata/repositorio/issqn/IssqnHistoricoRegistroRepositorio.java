/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnHistoricoRegistro;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public class IssqnHistoricoRegistroRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnHistoricoRegistroRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnHistoricoRegistro salvarHistoricoRegistro(Issqn i) {
        IssqnHistoricoRegistro ihr = new IssqnHistoricoRegistro();
        Issqn issOld = getEntidade(Issqn.class, i.getId());
        ihr.setAtivo(true);
        ihr.setDataCadastro(new Date());
        ihr.setIss(i);
        int alterados = 0;
        if (i.getPessoa().getNome() != null && !i.getPessoa().getNome().trim().isEmpty()
                && !i.getPessoa().getNome().equals(issOld.getPessoa().getNome())) {
            ihr.setNome(i.getPessoa().getNome());
            alterados++;
        }
        if (i.getPessoa().getNomeFantasia() != null && !i.getPessoa().getNomeFantasia().trim().isEmpty()
                && !i.getPessoa().getNomeFantasia().equals(issOld.getPessoa().getNomeFantasia())) {
            ihr.setNomeFantasia(i.getPessoa().getNomeFantasia());
            alterados++;
        }
        if (i.getPessoa().getTpPessoa() != null && !i.getPessoa().getTpPessoa().equals(issOld.getPessoa().getTpPessoa())) {
            ihr.setTpPessoa(i.getPessoa().getTpPessoa());
            alterados++;
        }
        if (i.getPessoa().getCpfCnpj() != null && !i.getPessoa().getCpfCnpj().trim().isEmpty()
                && !i.getPessoa().getCpfCnpj().equals(issOld.getPessoa().getCpfCnpj())) {
            ihr.setCpfCnpj(i.getPessoa().getCpfCnpj());
            alterados++;
        }
        if (i.getPessoa().getCartaoCidadao() != null && !i.getPessoa().getCartaoCidadao().equals(issOld.getPessoa().getCartaoCidadao())) {
            ihr.setCartaoCidadao(i.getPessoa().getCartaoCidadao());
            alterados++;
        }

        String telefone = i.getPessoa().getTelefone().replace("(", "").replace(")", "").replace(".", "").replace("-", "");
        String telefoneOld = issOld.getPessoa().getTelefone().replace("(", "").replace(")", "").replace(".", "").replace("-", "");
        if (telefone != null && !telefone.trim().isEmpty()
                && !telefone.equals(telefoneOld)) {
            ihr.setTelefone(i.getPessoa().getTelefone());
            alterados++;
        }

        String celular = i.getPessoa().getCelular().replace("(", "").replace(")", "").replace(".", "").replace("-", "");
        String celularOld = issOld.getPessoa().getCelular().replace("(", "").replace(")", "").replace(".", "").replace("-", "");
        if (celular != null && !celular.trim().isEmpty()
                && !celular.equals(celularOld)) {
            ihr.setCelular(i.getPessoa().getCelular());
            alterados++;
        }
        if (i.getPessoa().getTel1() != null && !i.getPessoa().getTel1().trim().isEmpty()
                && !i.getPessoa().getTel1().equals(issOld.getPessoa().getTel1())) {
            ihr.setTel1(i.getPessoa().getTel1());
            alterados++;
        }
        if (i.getPessoa().getTel2() != null && !i.getPessoa().getTel2().trim().isEmpty()
                && !i.getPessoa().getTel2().equals(issOld.getPessoa().getTel2())) {
            ihr.setTel2(i.getPessoa().getTel2());
            alterados++;
        }
        if (i.getPessoa().getDataNascimento() != null && !i.getPessoa().getDataNascimento().equals(issOld.getPessoa().getDataNascimento())) {
            ihr.setDataNascimento(i.getPessoa().getDataNascimento());
            alterados++;
        }
        if (i.getPessoa().getRg() != null && !i.getPessoa().getRg().trim().isEmpty()
                && !i.getPessoa().getRg().equals(issOld.getPessoa().getRg())) {
            ihr.setRg(i.getPessoa().getRg());
            alterados++;
        }
        if (i.getPessoa().getDtEmissaoRg() != null && !i.getPessoa().getDtEmissaoRg().equals(issOld.getPessoa().getDtEmissaoRg())) {
            ihr.setDtEmissaoRg(i.getPessoa().getDtEmissaoRg());
            alterados++;
        }
        if (i.getPessoa().getEmail() != null && !i.getPessoa().getEmail().trim().isEmpty()
                && !i.getPessoa().getEmail().equals(issOld.getPessoa().getEmail())) {
            ihr.setEmail(i.getPessoa().getEmail());
            alterados++;
        }
        if (i.getPessoa().getOrgaoExpRg() != null && !i.getPessoa().getOrgaoExpRg().equals(issOld.getPessoa().getOrgaoExpRg())) {
            ihr.setOrgaoExpRg(i.getPessoa().getOrgaoExpRg());
            alterados++;
        }
        if (i.getPessoa().getCertReservista() != null && !i.getPessoa().getCertReservista().trim().isEmpty()
                && !i.getPessoa().getCertReservista().equals(issOld.getPessoa().getCertReservista())) {
            ihr.setCertReservista(i.getPessoa().getCertReservista());
            alterados++;
        }
        if (i.getPessoa().getGrauInstrucao() != null && !i.getPessoa().getGrauInstrucao().equals(issOld.getPessoa().getGrauInstrucao())) {
            ihr.setGrauInstrucao(i.getPessoa().getGrauInstrucao());
            alterados++;
        }
        if (i.getPessoa().getEstCivil() != null && !i.getPessoa().getEstCivil().equals(issOld.getPessoa().getEstCivil())) {
            ihr.setEstCivil(i.getPessoa().getEstCivil());
            alterados++;
        }
        if (i.getPessoa().getCor() != null && !i.getPessoa().getCor().equals(issOld.getPessoa().getCor())) {
            ihr.setCor(i.getPessoa().getCor());
            alterados++;
        }
        if (i.getPessoa().getUfRg() != null && !i.getPessoa().getUfRg().equals(issOld.getPessoa().getUfRg())) {
            ihr.setUfRg(i.getPessoa().getUfRg());
            alterados++;
        }
        if (i.getPessoa().getSexo() != null && !i.getPessoa().getSexo().equals(issOld.getPessoa().getSexo())) {
            ihr.setSexo(i.getPessoa().getSexo());
            alterados++;
        }
        if (i.getPessoa().getTpSanguineo() != null && !i.getPessoa().getTpSanguineo().equals(issOld.getPessoa().getTpSanguineo())) {
            ihr.setTpSanguineo(i.getPessoa().getTpSanguineo());
            alterados++;
        }
        if (i.getPessoa().getInscEstadual() != null && !i.getPessoa().getInscEstadual().trim().isEmpty()
                && !i.getPessoa().getInscEstadual().equals(issOld.getPessoa().getInscEstadual())) {
            ihr.setInscEstadual(i.getPessoa().getInscEstadual());
            alterados++;
        }
        if (i.getPessoa().getInscMunicipal() != null && !i.getPessoa().getInscMunicipal().trim().isEmpty()
                && !i.getPessoa().getInscMunicipal().equals(issOld.getPessoa().getInscMunicipal())) {
            ihr.setInscMunicipal(i.getPessoa().getInscMunicipal());
            alterados++;
        }

        if (alterados > 0) {
            return setEntidade(IssqnHistoricoRegistro.class, ihr);
        }

        return ihr;
    }

    public synchronized boolean alteracaoRegistro(IssqnHistoricoRegistro ihr) {
        Pessoa pessoa = getEntidade(Pessoa.class, ihr.getIss().getPessoa().getId());
        pessoa.setNome(ihr.getIss().getPessoa().getNome());
        pessoa.setNomeFantasia(ihr.getNomeFantasia());
        pessoa.setTpPessoa(ihr.getIss().getPessoa().getTpPessoa());
        pessoa.setCartaoCidadao(ihr.getCartaoCidadao());
        pessoa.setTelefone(ihr.getTelefone());
        pessoa.setCelular(ihr.getCelular());
        pessoa.setTel1(ihr.getTel1());
        pessoa.setTel2(ihr.getTel2());
        pessoa.setDataNascimento(ihr.getDataNascimento());
        pessoa.setRg(ihr.getRg());
        pessoa.setDtEmissaoRg(ihr.getDtEmissaoRg());
        pessoa.setEmail(ihr.getEmail());
        pessoa.setOrgaoExpRg(ihr.getOrgaoExpRg());
        pessoa.setCertReservista(ihr.getCertReservista());
        pessoa.setGrauInstrucao(ihr.getGrauInstrucao());
        pessoa.setEstCivil(ihr.getEstCivil());
        pessoa.setCor(ihr.getCor());
        pessoa.setUfRg(ihr.getUfRg());
        pessoa.setSexo(ihr.getSexo());
        pessoa.setTpSanguineo(ihr.getTpSanguineo());
        pessoa.setInscEstadual(ihr.getInscEstadual());
        pessoa.setInscMunicipal(ihr.getInscMunicipal());

        setEntidade(Pessoa.class, pessoa);

        ihr.setAtivo(false);
        setEntidade(IssqnHistoricoRegistro.class, ihr);

        return true;

    }

    public synchronized void removerHistoricoRegistro(int historicoId) {
        IssqnHistoricoRegistro h = getEntidade(IssqnHistoricoRegistro.class, historicoId);
        removerEntidade(h);
    }

    public IssqnHistoricoRegistro obterHistoricoRegistroPorId(int id) {
        return getEntidade(IssqnHistoricoRegistro.class, id);
    }

    public List<IssqnHistoricoRegistro> obterHistoricos(Integer limite) {
        return getListaPuraLimite(IssqnHistoricoRegistro.class,
                "select h from IssqnHistoricoRegistro h"
                + "\nwhere h.ativo = ?1", limite, true);
    }

    public void imprimirHistoricoRegistro(String orgaoId, String nomePessoa, int idPessoa, String emailUsuario) throws Exception {
        {
            try {
                Map p = new HashMap();
                ArrayList<HashMap> lst = new ArrayList<>();
                ReportGenerator rpt = new ReportGenerator();

                ContabilOrgao orgao = getEntidade(ContabilOrgao.class, orgaoId);
                if (orgao == null) {
                    throw new Exception("Informações do orgão não encontradas!");
                }
                byte[] logotipo_bytes = orgao.getLogotipo();
                ImageIcon logotipo = new ImageIcon();
                if (logotipo_bytes != null) {
                    logotipo.setImage(Toolkit.getDefaultToolkit().createImage(logotipo_bytes));
                }

                //-- PARAMETROS
                p.put("orgao", orgao.getNome().toUpperCase());
                p.put("nomeContribuinte", nomePessoa);
                p.put("brasao", logotipo.getImage());

                String dataGeracao = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                p.put("assinatura", "Documento assinado por: " + emailUsuario + ", em " + dataGeracao);

                List<IssqnHistoricoRegistro> lista = getListaPura(IssqnHistoricoRegistro.class,
                        "select h from IssqnHistoricoRegistro h"
                        + "\njoin h.iss i"
                        + "\njoin i.pessoa p"
                        + "\nwhere p.id = ?1"
                        + "\norder by h.dataCadastro desc", idPessoa);

                for (IssqnHistoricoRegistro ihr : lista) {
                    HashMap field = new HashMap();
                    field.put("data", Util.parseSqlToBrDate(ihr.getDataCadastro()));

                    StringBuilder sb = new StringBuilder();
                    if (ihr.getNome() != null) {
                        sb.append("\nNome:");
                        sb.append(ihr.getNome());
                    }
                    if (ihr.getNomeFantasia() != null) {
                        sb.append("\nNome Fantasia:");
                        sb.append(ihr.getNomeFantasia());
                    }
                    if (ihr.getTpPessoa() != null) {
                        sb.append("\nTipo Pessoa:");
                        sb.append(ihr.getTpPessoa().getDescricao());
                    }
                    if (ihr.getCartaoCidadao() != null) {
                        sb.append("\nCartão Cidadão:");
                        sb.append(ihr.getCartaoCidadao());
                    }
                    if (ihr.getTelefone() != null) {
                        sb.append("\nTel. Res:");
                        sb.append(ihr.getTelefone());
                    }
                    if (ihr.getCelular() != null) {
                        sb.append("\nCelular:");
                        sb.append(ihr.getCelular());
                    }
                    if (ihr.getTel1() != null) {
                        sb.append("\nTel. Com:");
                        sb.append(ihr.getTel1());
                    }
                    if (ihr.getTel2() != null) {
                        sb.append("\nTel. Recados:");
                        sb.append(ihr.getTel2());
                    }
                    if (ihr.getDataNascimento() != null) {
                        sb.append("\nNascimento:");
                        sb.append(Util.parseSqlToBrDate(ihr.getDataNascimento()));
                    }
                    if (ihr.getRg() != null) {
                        sb.append("\nNº. RG:");
                        sb.append(ihr.getRg());
                    }
                    if (ihr.getDtEmissaoRg() != null) {
                        sb.append("\nDt. RG:");
                        sb.append(Util.parseSqlToBrDate(ihr.getDtEmissaoRg()));
                    }
                    if (ihr.getEmail() != null) {
                        sb.append("\nEmail:");
                        sb.append(ihr.getEmail());
                    }
                    if (ihr.getOrgaoExpRg() != null) {
                        sb.append("\nOrgão Exp:");
                        sb.append(ihr.getOrgaoExpRg().getDescricao());
                    }
                    if (ihr.getCertReservista() != null) {
                        sb.append("\nReservista:");
                        sb.append(ihr.getCertReservista());
                    }
                    if (ihr.getGrauInstrucao() != null) {
                        sb.append("\nEscolaridade:");
                        sb.append(ihr.getGrauInstrucao());
                    }
                    if (ihr.getEstCivil() != null) {
                        sb.append("\nEst. Civil:");
                        sb.append(ihr.getEstCivil().getDescricao());
                    }
                    if (ihr.getCor() != null) {
                        sb.append("\nCor:");
                        sb.append(ihr.getCor().getDescricao());
                    }
                    if (ihr.getUfRg() != null) {
                        sb.append("\nUF:");
                        sb.append(ihr.getUfRg().getDescricao());
                    }
                    if (ihr.getSexo() != null) {
                        sb.append("\nSexo:");
                        sb.append(ihr.getSexo().getDescricao());
                    }
                    if (ihr.getTpSanguineo() != null) {
                        sb.append("\nTp. Sanguineo:");
                        sb.append(ihr.getSexo().getDescricao());
                    }
                    if (ihr.getInscEstadual() != null) {
                        sb.append("\nInsc. Estadual:");
                        sb.append(ihr.getInscEstadual());
                    }
                    if (ihr.getInscMunicipal() != null) {
                        sb.append("\nInsc. Municipal:");
                        sb.append(ihr.getInscMunicipal());
                    }
                    field.put("alteracoes", sb.toString());
                    lst.add(field);
                }
                rpt.jasperReport("historico_registro", "application/pdf", lst, p, "historico");
            } catch (NoResultException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
