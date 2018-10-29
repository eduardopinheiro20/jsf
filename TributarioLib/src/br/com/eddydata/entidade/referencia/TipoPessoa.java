/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.referencia;

/**
 *
 * @author Leandro
 */
public enum TipoPessoa {

    FISICA("Física"),
    JURIDICA("Jurídica");

    public String descricao;

    private TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
