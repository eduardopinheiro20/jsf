/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.auditoria;

import java.util.Date;
import br.com.eddydata.entidade.admin.Usuario;
import com.ocpsoft.shade.org.apache.commons.beanutils.PropertyUtils;
import java.lang.reflect.InvocationTargetException;
import javax.faces.context.FacesContext;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.descriptors.DescriptorEventAdapter;
import org.eclipse.persistence.queries.DeleteObjectQuery;
import org.eclipse.persistence.queries.InsertObjectQuery;
import org.eclipse.persistence.queries.UpdateObjectQuery;

/**
 *
 * @author Thiago Martos
 */
public class AuditoriaHandler extends DescriptorEventAdapter implements DescriptorCustomizer {

    /**
     * This method "customizes" the DescriptionEventManager, adding this
     * listener to be invoked for events
     *
     * @param classDescriptor
     * @throws java.lang.Exception
     */
    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        classDescriptor.getDescriptorEventManager().addListener(this);
    }

    /**
     * (non-Javadoc)
     *
     * @param event
     * @see
     * oracle.toplink.essentials.descriptors.DescriptorEventAdapter#postDelete(oracle.toplink.essentials.descriptors.DescriptorEvent)
     */
    @Override
    public void postDelete(DescriptorEvent event) {
        gerarLogAuditoria(event);
    }

    /**
     * (non-Javadoc)
     *
     * @param event
     * @see
     * oracle.toplink.essentials.descriptors.DescriptorEventAdapter#postInsert(oracle.toplink.essentials.descriptors.DescriptorEvent)
     */
    @Override
    public void postInsert(DescriptorEvent event) {
        gerarLogAuditoria(event);
    }

    /**
     * (non-Javadoc)
     *
     * @param event
     * @see
     * oracle.toplink.essentials.descriptors.DescriptorEventAdapter#postUpdate(oracle.toplink.essentials.descriptors.DescriptorEvent)
     */
    @Override
    public void postUpdate(DescriptorEvent event) {
        gerarLogAuditoria(event);
    }

    /**
     * Método para gerar auditoria das tabelas mapeadas no persistence
     *
     * @param event parametro do tipo DescriptorEvent
     */
    protected void gerarLogAuditoria(DescriptorEvent event) {

        String operacao;
        String usuario;
        String sistema;
        String tabela;
        String query;
        String queryMap = "";
        Object id = null;

        //obtem operacao
        if (event.getEventCode() == 5) {
            operacao = Auditoria.INSERT_OPERATION;
        } else if (event.getEventCode() == 7) {
            operacao = Auditoria.UPDATE_OPERATION;
        } else {
            operacao = Auditoria.DELETE_OPERATION;
        }

        //obtem usuário
        if (FacesContext.getCurrentInstance() == null) {
            usuario = "";
        } else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado") == null) {
            usuario = "";
        } else {
            Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
            usuario = (u == null ? "" : u.getNome());
        }

        //obtem sistema
        sistema = event.getDescriptor().getJavaClassName();
        sistema = sistema.substring(0, sistema.lastIndexOf("."));
        int index = sistema.lastIndexOf(".") + 1;
        sistema = sistema.substring(index, sistema.length());

        //obtem tabela
        tabela = event.getDescriptor().getAlias();

        //obtem SQL gerado
        query = event.getQuery().getTranslatedSQLString(event.getSession(), event.getQuery().getTranslationRow());

        //obtem alteração simplificada
        if (event.getQuery() instanceof UpdateObjectQuery) {
            UpdateObjectQuery qry = (UpdateObjectQuery) event.getQuery();
            for (org.eclipse.persistence.sessions.changesets.ChangeRecord cr : qry.getObjectChangeSet().getChanges()) {
                try {
                    id = qry.getObjectChangeSet().getId();
                    Object valorAnterior = cr.getOldValue();
                    Object valorAtual = PropertyUtils.getProperty(qry.getObject(), cr.getAttribute());

                    queryMap += "<b>Campo:</b> " + cr.getAttribute()
                            + "<br />"
                            + "<b>Anterior:</b> " + valorAnterior
                            + " | <b>Atual:</b> " + valorAtual
                            + "<hr />";
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    System.out.println("Erro ao buscar alteração simplificada na auditoria\n" + ex.getMessage());
                }
            }
        } else if (event.getQuery() instanceof InsertObjectQuery) {
            InsertObjectQuery qry = (InsertObjectQuery) event.getQuery();
            id = qry.getObjectChangeSet().getId();
            String mapa = event.getQuery().getTranslationRow().toString();
            mapa = mapa.replace("DatabaseRecord(", "").replace("\t", "").replace("\n", "");
            for (String s : mapa.split(qry.getDescriptor().getDefaultTable().getName() + ".")) {
                String[] row = s.split("=>");
                if (row.length == 2) {
                    String campo = row[0].replaceAll("\\s+$", ""); //RTRIM
                    String valor = row[1].replaceAll("^\\s+", ""); //LTRIM
                    if ("null".equals(valor)) {
                        continue;
                    }
                    queryMap += "<b>Campo:</b> " + campo
                            + " | "
                            + "<b>Valor:</b> " + valor
                            + "<hr />";
                }
            }
        } else if (event.getQuery() instanceof DeleteObjectQuery) {
//            DeleteObjectQuery qry = (DeleteObjectQuery) event.getQuery();
            String mapa = event.getQuery().getTranslationRow().toString();
            id = mapa.substring(mapa.lastIndexOf("=>") + 3).replace(")", "");
            mapa = mapa.replace("DatabaseRecord(", "").replace("\t", "").replace("\n", "");
            queryMap = mapa;
        } else {
            id = "0";
            queryMap = event.getQuery().getTranslationRow().toString();
        }

        Auditoria entry = new Auditoria();

        entry.setOperacao(operacao);
        entry.setUsuario(usuario == null ? "" : usuario);
        entry.setSistema(sistema.toUpperCase());
        entry.setTabela(tabela.toUpperCase());
        entry.setQuery(query);
        entry.setQueryMap(queryMap);
        entry.setData(new Date());
        entry.setIdRegistro((id == null ? null : Long.parseLong(id.toString())));

        entry.setSessao(0);
        entry.setHost(null);
        entry.setProgramName(null);
        entry.setOrgao(null);
        entry.setExercicio(null);

        try {
            InsertObjectQuery insertQuery = new InsertObjectQuery(entry);
            event.getSession().executeQuery(insertQuery);
        } catch (Exception e) {
            System.out.println("Erro ao salvar auditoria:\n" + e.getMessage());
        }
    }
}
