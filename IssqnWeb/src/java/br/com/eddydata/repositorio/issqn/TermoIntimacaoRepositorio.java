/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnTermoIntimacao;
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
public class TermoIntimacaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public TermoIntimacaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnTermoIntimacao salvar(IssqnTermoIntimacao ad) {
        if (ad.getId() == null) {
            return adicionarEntidade(IssqnTermoIntimacao.class, ad);
        } else {
            return setEntidade(IssqnTermoIntimacao.class, ad);
        }
    }

    public synchronized void remover(int id) {
        IssqnTermoIntimacao t = getEntidade(IssqnTermoIntimacao.class, id);
        removerEntidade(t);
    }

    public IssqnTermoIntimacao obterPorId(int id) {
        return getEntidade(IssqnTermoIntimacao.class, id);
    }

    public List<IssqnTermoIntimacao> listar(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            String filtroCpfCnpj = filtro.replace(".", "").replace("-", "").replace("/", "");
            if (Util.isNumeric(filtroCpfCnpj)) {
                where += "\n where i.inscricao like " + Util.quotarStr("%" + filtroCpfCnpj + "%") 
                        + " OR ad.cnpjCpf like " + Util.quotarStr("%" + filtroCpfCnpj + "%");
            } else {
                where += "\n where UPPER(ad.contribuinte) like " + Util.quotarStr(filtroCpfCnpj + "%");
//              where += "\n where UPPER(ad.iss.pessoa.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnTermoIntimacao.class,
                "select ad from IssqnTermoIntimacao ad "
                + "join ad.iss i"
                + where
                + "\norder by ad.id", limite
        );

    }

    public void imprimirTermoIntimacao(Integer termoId, String orgaoId) throws Exception {

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
            p.put("leimunicipal", config.getLeiTermoFiscalizacao());

            String endCeat = (config.getEndCeat() == null ? "" : config.getEndCeat().trim());
            IssqnTermoIntimacao termo = getEntidade(IssqnTermoIntimacao.class, termoId);

            if (termo != null) {
                HashMap field = new HashMap();

                field.put("end_ceat", (endCeat.length() > 0 ? "ENDEREÇO CEAT: " + endCeat.toUpperCase() : ""));
                field.put("ti", termoId.toString());
                field.put("intimacao", termo.getIntimacao());
                field.put("data", (termo.getData()== null ? "" : Util.convertToBrDate(termo.getData())));
                field.put("alvara", termo.getNumAlvara());
                field.put("notificacao", termo.getNotificacao());
                field.put("inscricao", termo.getIss().getInscricao());
                field.put("contribuinte", termo.getContribuinte());
                field.put("cpfcnpj", termo.getCnpjCpf());
                
                String endereco = "";
                String bairro = "";
                
                if (termo.getBairrologradouro() != null) {
                   
                    if (termo.getBairrologradouro().getLogradouro() != null) {
                        endereco = termo.getBairrologradouro().getLogradouro().getNome();
                    }
                   
                    if (termo.getBairrologradouro().getBairro() != null) {
                        bairro = termo.getBairrologradouro().getBairro().getNome();
                    }
                    
                }
                
                field.put("endereco", endereco);
                field.put("numero", termo.getNrImovel().toString());
                field.put("bairro", bairro);
                field.put("complemento", termo.getComplemento());
                field.put("telefone", termo.getIss().getPessoa().getTel1());
                field.put("atividade", termo.getAtividade());
                field.put("prazo", termo.getPrazoDe());
                field.put("observacao", termo.getObservacao() == null || termo.getObservacao().isEmpty() ? "" : "\n\n\nOBSERVAÇÕES:\n" + termo.getObservacao());
                field.put("historico", termo.getHistorico() == null || termo.getHistorico().isEmpty() ? "" : "\n\n\nHISTORICO:\n" + termo.getHistorico());

                lst.add(field);
            }
            rpt.jasperReport("termo_intimacao", "application/pdf", lst, p, "termo_intimacao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }
    public void imprimirTermoIntimacao(Date dtInicial,Date dtFinal, String orgaoId) throws Exception {

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
            p.put("leimunicipal", config.getLeiTermoFiscalizacao());

            String endCeat = (config.getEndCeat() == null ? "" : config.getEndCeat().trim());
            
            
               List<IssqnTermoIntimacao> lista = getListaPura(IssqnTermoIntimacao.class,
                "select i from IssqnTermoIntimacao i"
                + "\nwhere i.data between ?1 and ?2"
                + "\norder by i.data ", dtInicial,dtFinal);
            


            for (IssqnTermoIntimacao termo : lista) {
                HashMap field = new HashMap();

                field.put("end_ceat", (endCeat.length() > 0 ? "ENDEREÇO CEAT: " + endCeat.toUpperCase() : ""));
                field.put("ti", termo.getId());
                field.put("intimacao", termo.getIntimacao());
                field.put("data", (termo.getData()== null ? "" : Util.convertToBrDate(termo.getData())));
                field.put("alvara", termo.getNumAlvara());
                field.put("notificacao", termo.getNotificacao());
                field.put("inscricao", termo.getIss().getInscricao());
                field.put("contribuinte", termo.getContribuinte());
                field.put("cpfcnpj", termo.getCnpjCpf());
                
                String endereco = "";
                String bairro = "";
                
                if (termo.getBairrologradouro() != null) {
                    
                    if (termo.getBairrologradouro().getLogradouro() != null) {
                        endereco = termo.getBairrologradouro().getLogradouro().getNome();
                    }
                    
                    if (termo.getBairrologradouro().getBairro() != null) {
                        bairro = termo.getBairrologradouro().getBairro().getNome();
                    }
                    
                }
                
                field.put("endereco", endereco);
                field.put("numero", termo.getNrImovel().toString());
                field.put("bairro", bairro);
                field.put("complemento", termo.getComplemento());
                field.put("telefone", termo.getIss().getPessoa().getTel1());
                field.put("atividade", termo.getAtividade());
                field.put("prazo", termo.getPrazoDe());
                field.put("observacao", termo.getObservacao() == null || termo.getObservacao().isEmpty() ? "" : "\n\n\nOBSERVAÇÕES:\n" + termo.getObservacao());
                field.put("historico", termo.getHistorico() == null || termo.getHistorico().isEmpty() ? "" : "\n\n\nHISTORICO:\n" + termo.getHistorico());

                lst.add(field);
            }
            rpt.jasperReport("termo_intimacao", "application/pdf", lst, p, "termo_intimacao");
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }

}
