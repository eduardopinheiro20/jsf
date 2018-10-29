/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.referencia;

import java.util.Objects;

/**
 *
 * @author Thiago Martos
 */
public enum TipoSanguineo {

    A(1, "A+"),
    a(2, "A-"),
    B(3, "B+"),
    b(4, "B-"),
    AB(5, "AB+"),
    ab(6, "AB-"),
    O(7, "O+"),
    o(8, "O-");

    public Integer id;
    public String descricao;

    private TipoSanguineo(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }

    public static TipoSanguineo parse(Integer id) {
        TipoSanguineo tipoSanguineo = null;
        for (TipoSanguineo item : TipoSanguineo.values()) {
            if (Objects.equals(item.getId(), id)) {
                tipoSanguineo = item;
                break;
            }
        }
        return tipoSanguineo;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
