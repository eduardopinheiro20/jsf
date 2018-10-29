/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnTermoApreensao;
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
public class TermoApreensaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public TermoApreensaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnTermoApreensao salvar(IssqnTermoApreensao ad) {
        if (ad.getId() == null) {
            return adicionarEntidade(IssqnTermoApreensao.class, ad);
        } else {
            return setEntidade(IssqnTermoApreensao.class, ad);
        }
    }

    public synchronized void remover(int id) {
        IssqnTermoApreensao t = getEntidade(IssqnTermoApreensao.class, id);
        removerEntidade(t);
    }

    public IssqnTermoApreensao obterPorId(int id) {
        return getEntidade(IssqnTermoApreensao.class, id);
    }

    public List<IssqnTermoApreensao> listar(String filtro, Integer limite) {
        String where = "";
        
        if (!filtro.equals("")) {
            String filtroCpfCnpj = filtro.replace(".", "").replace("-", "").replace("/", "");
            
            if (Util.isNumeric(filtroCpfCnpj)) {
                where += "\n where i.inscricao like " + Util.quotarStr("%" + filtroCpfCnpj + "%") 
                        + " OR ad.cnpjCpf like " + Util.quotarStr("%" + filtroCpfCnpj + "%");
            } else {
                where += "\n where UPPER(ad.contribuinte) like " + Util.quotarStr(filtro + "%");
//                where += "\n where UPPER(ad.iss.pessoa.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnTermoApreensao.class,
                "select ad from IssqnTermoApreensao ad "
                +"\njoin ad.iss i"
                + where
                + "\norder by ad.id", limite
        );

    }

    public void imprimirTermoApreensao(Integer termoId, String orgaoId) throws Exception {

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

            String endCeat = (config.getEndCeat() == null ? "" : config.getEndCeat().trim());
            IssqnTermoApreensao termo = getEntidade(IssqnTermoApreensao.class, termoId);

            if (termo != null) {
                HashMap field = new HashMap();

                field.put("end_ceat", (endCeat.length() > 0 ? "ENDEREÇO CEAT: " + endCeat.toUpperCase() : ""));
                field.put("ta", termoId.toString());
                field.put("data", (termo.getDataEmissao()== null ? "" : Util.convertToBrDate(termo.getDataEmissao())));
                field.put("deacordo", termo.getAcordoCom());
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
                
                if (Util.extractStr(termo.getIss()) != "") {
                    field.put("telefone", Util.extractStr(termo.getIss().getPessoa().getTel1()));
                } else {
                    field.put("telefone", "");
                }
                
                field.put("dadosservico", termo.getDescricaoServico());

                lst.add(field);
            }
            
            rpt.jasperReport("termo_apreensao", "application/pdf", lst, p, "termo_apreensao");
        
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }
    public void imprimirTermoApreensao(Date dtInicial,Date dtFinal, String orgaoId) throws Exception {

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

            String endCeat = (config.getEndCeat() == null ? "" : config.getEndCeat().trim());
            
            
               List<IssqnTermoApreensao> lista = getListaPura(IssqnTermoApreensao.class,
                "select i from IssqnTermoApreensao i"
                + "\nwhere i.data between ?1 and ?2"
                + "\norder by i.data ", dtInicial,dtFinal);
            


            for (IssqnTermoApreensao termo : lista) {
                HashMap field = new HashMap();

                field.put("end_ceat", (endCeat.length() > 0 ? "ENDEREÇO CEAT: " + endCeat.toUpperCase() : ""));
                field.put("ta", termo.getId().toString());
                field.put("data", (termo.getDataEmissao()== null ? "" : Util.convertToBrDate(termo.getDataEmissao())));
                field.put("deacordo", termo.getAcordoCom());
                field.put("cpfcnpj", termo.getCnpjCpf());
                field.put("contribuinte", termo.getContribuinte());
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
                field.put("numero", termo.getNrImovel() == null ? "" : termo.getNrImovel().toString());
                field.put("bairro", bairro);
                field.put("telefone", Util.extractStr(termo.getIss().getPessoa().getTel1()));
                field.put("dadosservico", termo.getDescricaoServico());

                lst.add(field);
            }
            
            rpt.jasperReport("termo_apreensao", "application/pdf", lst, p, "termo_apreensao");
        
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

    }

}
