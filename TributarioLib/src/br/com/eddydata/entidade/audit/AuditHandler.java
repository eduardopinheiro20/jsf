/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.audit;

import br.com.eddydata.entidade.admin.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.descriptors.DescriptorEventAdapter;
import org.eclipse.persistence.internal.sessions.DirectToFieldChangeRecord;
import org.eclipse.persistence.queries.InsertObjectQuery;
import org.eclipse.persistence.queries.WriteObjectQuery;

/**
 *
 * @author eddylucas
 */
public class AuditHandler extends DescriptorEventAdapter implements
        DescriptorCustomizer {

    private static final Log log = LogFactory.getLog(AuditHandler.class);

    /**
     * This method "customizes" the DescriptionEventManager, adding this
     * listener to be invoked for events
     */
    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        classDescriptor.getDescriptorEventManager().addListener(this);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * oracle.toplink.essentials.descriptors.DescriptorEventAdapter#postDelete(oracle.toplink.essentials.descriptors.DescriptorEvent)
     */
    @Override
    public void postDelete(DescriptorEvent event) {
        AuditEntry entry = new AuditEntry();
        entry.setOperation(AuditEntry.DELETE_OPERATION);
        entry.setOperationTime(new Date());
        entry.setBaseEntityId(event.getSource().hashCode());
        InsertObjectQuery insertQuery = new InsertObjectQuery(entry);
        event.getSession().executeQuery(insertQuery);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * oracle.toplink.essentials.descriptors.DescriptorEventAdapter#postInsert(oracle.toplink.essentials.descriptors.DescriptorEvent)
     */
    @Override
    public void postInsert(DescriptorEvent event) {
        processWriteEvent(event);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * oracle.toplink.essentials.descriptors.DescriptorEventAdapter#postUpdate(oracle.toplink.essentials.descriptors.DescriptorEvent)
     */
    @Override
    public void postUpdate(DescriptorEvent event) {
        processWriteEvent(event);
    }

    /**
     * Common method to handle both Update and Insert events. Fortunately, the
     * Toplink libs are ammenable to this.
     *
     * @param event The DescriptorEvent to process
     */
    protected void processWriteEvent(DescriptorEvent event) {
        AuditEntry entry = new AuditEntry();
        entry.setOperation(event.getEventCode() == 7 ? AuditEntry.UPDATE_OPERATION
                : AuditEntry.INSERT_OPERATION);
        entry.setOperationTime(new Date());
        String source = event.getSource().toString().replaceAll(" ", "");
        Integer a = source.indexOf("=")+1;
        Integer b = source.indexOf("}");
        if (b <= 0) {
            b = source.indexOf("]");
        }
        if (a <= 0 || b <= 0) {
            entry.setBaseEntityId(event.getSource().hashCode());
        } else {
            entry.setBaseEntityId(Integer.parseInt(source.substring(a, b)));
        }
        try {
            Integer a1 = source.indexOf("[") + 1;
            if (a1 <= 0) {
                a1 = source.indexOf("{") + 1;
            }
            Integer a2 = source.indexOf("=");
            String nome_campo = "";
            if (a1 > 0 && a2 > 0) {
                nome_campo = source.substring(a1, a2).trim();
            }
            entry.setBaseFieldName(nome_campo);
        } catch (Exception e) {
            System.out.println("Erro ao salvar nome do campo do novo registro em auditoria\n" + e.getCause().getMessage());
        }
        entry.setTableName(event.getSource().getClass().getName());
        if (FacesContext.getCurrentInstance() == null) {
            return;
        }
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
        entry.setUser(usuario);

        Collection fields = new LinkedList();
        WriteObjectQuery query = (WriteObjectQuery) event.getQuery();
        List changes = query.getObjectChangeSet().getChanges();
        for (int i = 0; i < changes.size(); i++) {
            if (changes.get(i) instanceof DirectToFieldChangeRecord) {
                DirectToFieldChangeRecord fieldChange = (DirectToFieldChangeRecord) changes.get(i);
                AuditField field = new AuditField();
                field.setAuditEntry(entry);
                field.setFieldName(fieldChange.getAttribute());
                if (fieldChange.getOldValue() instanceof java.util.Date) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    field.setFieldValue(df.format(((Date) fieldChange.getOldValue())));
                } else {
                    field.setFieldValue((fieldChange.getOldValue() == null ? "null" : fieldChange.getOldValue().toString()));
                }
                fields.add(field);
            }
        }
        entry.setFields(fields);

        InsertObjectQuery insertQuery = new InsertObjectQuery(entry);
        event.getSession().executeQuery(insertQuery);

        for (Object o : fields) {
            AuditField field = (AuditField) o;
            insertQuery = new InsertObjectQuery(field);
            event.getSession().executeQuery(insertQuery);
        }
    }
}
