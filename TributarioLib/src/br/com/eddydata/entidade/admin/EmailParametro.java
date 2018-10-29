/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "email_parametro")
public class EmailParametro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Informe o endereço de email")
    @Column(name = "address_email", length = 255, nullable = false)
    private String addressEmail;

    @NotNull(message = "Informe se utiliza autenticação")
    @Column(name = "email_authenticated", nullable = false)
    private Boolean emailAuthenticated;

    @NotNull(message = "Informe o email de acesso")
    @Column(name = "email_email", length = 255, nullable = false)
    private String emailEmail;

    @NotNull(message = "Informe o host")
    @Column(name = "email_hostname", length = 255, nullable = false)
    private String emailHostname;

    @NotNull(message = "Informe a senha de acesso")
    @Column(name = "email_password", length = 255, nullable = false)
    private String emailPassword;

    @NotNull(message = "Informe a porta")
    @Column(name = "email_port", length = 255, nullable = false)
    private Integer emailPort;

    @NotNull(message = "Informe o email de solicitação")
    @Column(name = "email_solicitacao", length = 255, nullable = false)
    private String emailSolicitacao;

    @NotNull(message = "Informe se utiliza TLS")
    @Column(name = "email_starttls", nullable = false)
    private Boolean emailStarttls;

    public EmailParametro() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public Boolean getEmailAuthenticated() {
        return emailAuthenticated;
    }

    public void setEmailAuthenticated(Boolean emailAuthenticated) {
        this.emailAuthenticated = emailAuthenticated;
    }

    public String getEmailEmail() {
        return emailEmail;
    }

    public void setEmailEmail(String emailEmail) {
        this.emailEmail = emailEmail;
    }

    public String getEmailHostname() {
        return emailHostname;
    }

    public void setEmailHostname(String emailHostname) {
        this.emailHostname = emailHostname;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public Integer getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(Integer emailPort) {
        this.emailPort = emailPort;
    }

    public String getEmailSolicitacao() {
        return emailSolicitacao;
    }

    public void setEmailSolicitacao(String emailSolicitacao) {
        this.emailSolicitacao = emailSolicitacao;
    }

    public Boolean getEmailStarttls() {
        return emailStarttls;
    }

    public void setEmailStarttls(Boolean emailStarttls) {
        this.emailStarttls = emailStarttls;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailParametro)) {
            return false;
        }
        EmailParametro other = (EmailParametro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmailParametro{" + "id=" + id + '}';
    }

}
