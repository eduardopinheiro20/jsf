/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnTermoFiscalizacao;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.Toolkit;
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
 * @author Thiago Martos
 */
public class TermoFiscalizacaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public TermoFiscalizacaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnTermoFiscalizacao salvar(IssqnTermoFiscalizacao ad) {
        if (ad.getId() == null) {
            return adicionarEntidade(IssqnTermoFiscalizacao.class, ad);
        } else {
            return setEntidade(IssqnTermoFiscalizacao.class, ad);
        }
    }

    public synchronized void remover(int id) {
        IssqnTermoFiscalizacao t = getEntidade(IssqnTermoFiscalizacao.class, id);
        removerEntidade(t);
    }

    public IssqnTermoFiscalizacao obterPorId(int id) {
        return getEntidade(IssqnTermoFiscalizacao.class, id);
    }

    public List<IssqnTermoFiscalizacao> listar(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            String filtroCpfCnpj = filtro.replace(".", "").replace("-", "").replace("/", "");
            if (Util.isNumeric(filtroCpfCnpj)) {
                where += "\n where i.inscricao like " + Util.quotarStr("%" + filtroCpfCnpj + "%") 
                        + " OR ad.cnpjCpf like " + Util.quotarStr("%" + filtroCpfCnpj + "%");
            } else {
                  where += "\n where UPPER(ad.estabelecimento) like " + Util.quotarStr(filtro + "%");
//                where += "\n where UPPER(ad.iss.pessoa.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnTermoFiscalizacao.class,
                "select ad from IssqnTermoFiscalizacao ad "
                + "join ad.iss i"
                + where
                + "\norder by ad.id", limite
        );

    }

    public void imprimirTermoFiscalizacao(Integer termoId, String orgaoId) throws Exception {

        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            IssqnConfiguracao config = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c where c.orgao.idOrgao = ?1", orgaoId
            );
            if (config == null) {
                throw new Exception("Informações das configurações não encontradas!");
            }
            ContabilOrgao orgao = config.getOrgao();
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
            p.put("brasao",logotipo.getImage());
            p.put("lei", config.getLeiTermoFiscalizacao());

            String endCeat = (config.getEndCeat() == null ? "" : config.getEndCeat().trim());
            IssqnTermoFiscalizacao termo = getEntidade(IssqnTermoFiscalizacao.class, termoId);

            if (termo != null) {
                HashMap field = new HashMap();

                field.put("end_ceat", (endCeat.length() > 0 ? "ENDEREÇO CEAT: " + endCeat.toUpperCase() : ""));
                field.put("tf", termoId.toString());
                field.put("data", (termo.getData() == null ? "" : Util.convertToBrDate(termo.getData())));
                field.put("estabelecimento", termo.getEstabelecimento());
                field.put("cnpj", termo.getCnpjCpf());
                field.put("proprietario", termo.getProprietario());
                field.put("proprietario_cpf", termo.getProprietarioCpf());
                field.put("proprietario_profissao", termo.getProprietarioProfissao());
                String endereco = "";
                String bairro = "";
                String cidade = "";
                String uf = "";
                if (termo.getBairrologradouro() != null) {
                    if (termo.getBairrologradouro().getLogradouro() != null) {
                        endereco = termo.getBairrologradouro().getLogradouro().getNome();
                    }
                    if (termo.getBairrologradouro().getBairro() != null) {
                        bairro = termo.getBairrologradouro().getBairro().getNome();
                    }
                    if (termo.getBairrologradouro().getCidade() != null) {
                        cidade = termo.getBairrologradouro().getCidade().getNome();
                        uf = termo.getBairrologradouro().getCidade().getUf();
                    }
                }
                field.put("endereco", endereco);
                field.put("numero", termo.getNrImovel().toString());
                field.put("bairro", bairro);
                field.put("cidade", cidade);
                field.put("uf", uf);
                field.put("telefone", termo.getFone());
                field.put("atividade", termo.getAtividade());
                field.put("escritorio_responsavel", termo.getNomeEscritorio());
                field.put("escritorio_telefone", termo.getFoneEscritorio());
                field.put("apurado", termo.getApuracao() + (termo.getObservacao() == null || termo.getObservacao().isEmpty() ? "" : "\n\n\nOBSERVAÇÕES:\n" + termo.getObservacao()));
                field.put("cep", "");

                lst.add(field);
            }
            rpt.jasperReport("termo_fiscalizacao", "application/pdf", lst, p, "termo_fiscalizacao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }
    public void imprimirTermoFiscalizacao(Date dtInicial,Date dtFinal, String orgaoId) throws Exception {

        try {
            Map p = new HashMap();
            ArrayList<HashMap> lst = new ArrayList<>();
            ReportGenerator rpt = new ReportGenerator();

            IssqnConfiguracao config = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c where c.orgao.idOrgao = ?1 ", orgaoId
            );
            if (config == null) {
                throw new Exception("Informações das configurações não encontradas!");
            }
            ContabilOrgao orgao = config.getOrgao();
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
            p.put("brasao",logotipo.getImage());
            p.put("lei", config.getLeiTermoFiscalizacao());

            String endCeat = (config.getEndCeat() == null ? "" : config.getEndCeat().trim());
            
            
               List<IssqnTermoFiscalizacao> lista = getListaPura(IssqnTermoFiscalizacao.class,
                "select i from IssqnTermoFiscalizacao i"
                + "\nwhere i.data between ?1 and ?2"
                + "\norder by i.data ", dtInicial,dtFinal);
            


            for (IssqnTermoFiscalizacao termo : lista) {
                HashMap field = new HashMap();

                field.put("end_ceat", (endCeat.length() > 0 ? "ENDEREÇO CEAT: " + endCeat.toUpperCase() : ""));
                field.put("tf", termo.getId().toString());
                field.put("data", (termo.getData() == null ? "" : Util.convertToBrDate(termo.getData())));
                field.put("estabelecimento", termo.getEstabelecimento());
                field.put("cnpj", termo.getCnpjCpf());
                field.put("proprietario", termo.getProprietario());
                field.put("proprietario_cpf", termo.getProprietarioCpf());
                field.put("proprietario_profissao", termo.getProprietarioProfissao());
                String endereco = "";
                String bairro = "";
                String cidade = "";
                String uf = "";
                if (termo.getBairrologradouro() != null) {
                    if (termo.getBairrologradouro().getLogradouro() != null) {
                        endereco = termo.getBairrologradouro().getLogradouro().getNome();
                    }
                    if (termo.getBairrologradouro().getBairro() != null) {
                        bairro = termo.getBairrologradouro().getBairro().getNome();
                    }
                    if (termo.getBairrologradouro().getCidade() != null) {
                        cidade = termo.getBairrologradouro().getCidade().getNome();
                        uf = termo.getBairrologradouro().getCidade().getUf();
                    }
                }
                field.put("endereco", endereco);
                field.put("numero", termo.getNrImovel() == null ? "" : termo.getNrImovel().toString());
                field.put("bairro", bairro);
                field.put("cidade", cidade);
                field.put("uf", uf);
                field.put("telefone", termo.getFone());
                field.put("atividade", termo.getAtividade());
                field.put("escritorio_responsavel", termo.getNomeEscritorio());
                field.put("escritorio_telefone", termo.getFoneEscritorio());
                field.put("apurado", termo.getApuracao() + (termo.getObservacao() == null || termo.getObservacao().isEmpty() ? "" : "\n\n\nOBSERVAÇÕES:\n" + termo.getObservacao()));
                field.put("cep", "");

                lst.add(field);
            }
            rpt.jasperReport("termo_fiscalizacao", "application/pdf", lst, p, "termo_fiscalizacao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }

}
