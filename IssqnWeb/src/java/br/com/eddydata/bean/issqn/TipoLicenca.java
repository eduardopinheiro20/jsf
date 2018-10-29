/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean.issqn;

public enum TipoLicenca {
    AUTONOMO_1("Licenciamento para Autônomo"),
    AUTONOMO_2("Licenciamento para Autônomo Domicílio Fiscal"),
    DIVERSOES("Licenciamento para Diversões Públicas"),
    PROVISORIO("Licenciamento Provisório"),
    FUNCIONAMENTO("Licenciamento de Funcionamento - MEI");

    private final String descricao;

    private TipoLicenca(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
