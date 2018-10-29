/**
 * Copyright (C) 2009, Eddydata ltda.
 * 
 */
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.Acesso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Logon implements Serializable {

    private int usuarioId;
    private String entidadeId;
    private boolean authenticated;
    private String logo;
    private List<Acesso> permissoes = new ArrayList<>();

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(String entidadeId) {
        this.entidadeId = entidadeId;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Acesso> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Acesso> permissoes) {
        this.permissoes = permissoes;
    }

}
