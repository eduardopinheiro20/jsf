/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.admin.EmailParametro;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.servico.admin.EmailParametroServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class NotificacaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;
    @EJB
    private EmailParametroServico servicoEmail;

    @Inject
    private GlobalBean global;

    private List<Issqn> listIssqn;
    private String filterValue;
    private String tipoFiltro = "*";
    private Boolean chkChange = true;
    private String subject;
    private String emailCopy;
    private String message;
    private UploadedFile anexo = null;
    private List<listEmails> dataItem = new ArrayList<>();
    private final Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public class listEmails {

        private Boolean checked = true;
        private Issqn iss;

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }

        public Issqn getIss() {
            return iss;
        }

        public void setIss(Issqn iss) {
            this.iss = iss;
        }
    }

    @PostConstruct
    public void init() {
        carregarContribuintes();
    }

    public void carregarContribuintes() {
        if (listIssqn == null) {
            try {
                listIssqn = servico.obterIssqns("", null, global.getExercicio().getAno(), "");
            } catch (Exception ex) {
                System.out.println("Erro ao buscar os contribuintes\n" + ex.getMessage());
                return;
            }

            dataItem = new ArrayList<>();
            for (Issqn iss : listIssqn) {
                listEmails item = new listEmails();
                item.setIss(iss);

                dataItem.add(item);
            }
        }
    }

    public void dispararEmail() {
        if (dataItem.isEmpty()) {
            Funcao.avisoAtencao("Setores não encontrados");
            return;
        }
        if (subject == null || subject.equals("")) {
            Funcao.avisoAtencao("Informe o assunto do e-mail");
            return;
        }
        if (message == null || message.equals("")) {
            Funcao.avisoAtencao("Informe a mensagem do e-mail");
            return;
        }
        if (emailCopy != null && !emailCopy.equals("")) {
            Matcher matcher = patternEmail.matcher(emailCopy);

            if (!matcher.matches()) {
                Funcao.avisoAtencao("E-mail de cópia inválido");
                return;
            }
        }

        try {

            Issqn iss;
            for (listEmails lstEmail : dataItem) {
                if (lstEmail.checked) {
                    iss = lstEmail.getIss();

                    if (iss.getPessoa() != null && iss.getPessoa().getEmail() != null && !iss.getPessoa().getEmail().equals("")) {

                        //conecta email
                        EmailParametro p = servicoEmail.obterEmailParametro();

                        String[] destinos = new String[]{iss.getPessoa().getEmail()};
                        if (emailCopy != null && !emailCopy.equals("")) {
                            destinos = new String[]{iss.getPessoa().getEmail(), emailCopy};
                        }

                        for (String para : destinos) {
                            if (anexo != null) {
                                Util.dispararEmail(p, subject, message, para, anexo.getContents(), "anexo", anexo.getContentType());
                            } else {
                                Util.dispararEmail(p, subject, message, para);
                            }
                        }
                    }
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "E-mails enviados com sucesso ", "Informação"));

        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao enviar e-mails\n" + ex.getMessage());
        }
    }

    public void doChangeAllChecks(Boolean value) {
        for (listEmails item : dataItem) {
            item.setChecked(value);
        }
        chkChange = value;
    }

    //getts and setts
    public GlobalBean getGlobal() {
        return global;
    }

    public void setGlobal(GlobalBean global) {
        this.global = global;
    }

    public List<Issqn> getListIssqn() {
        return listIssqn;
    }

    public void setListIssqn(List<Issqn> listIssqn) {
        this.listIssqn = listIssqn;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    public Boolean getChkChange() {
        return chkChange;
    }

    public void setChkChange(Boolean chkChange) {
        this.chkChange = chkChange;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailCopy() {
        return emailCopy;
    }

    public void setEmailCopy(String emailCopy) {
        this.emailCopy = emailCopy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UploadedFile getAnexo() {
        return anexo;
    }

    public void setAnexo(UploadedFile anexo) {
        this.anexo = anexo;
    }

    public List<listEmails> getDataItem() {
        return dataItem;
    }

    public void setDataItem(List<listEmails> dataItem) {
        this.dataItem = dataItem;
    }

}
