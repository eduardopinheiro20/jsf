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
public enum Natureza {

    OUTRO(0, ""),
    ESTRANGEIRO(7, "Estrangeira"),
    MISTA(1, "Mista"),
    ONG(2, "ONG"),
    PRIVADA(3, "Privada"),
    PUBLICAMUN(4, "Pública Municipal"),
    PUBLICAEST(5, "Pública Estadual"),
    PUBLICAFED(6, "Pública Federal"),
    RELIGIOSO(8, "Templo Religioso"),
    OUTROS(9, "Outros");

    public Integer id;
    public String descricao;

    private Natureza(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }

    public static Natureza parse(Integer id) {
        Natureza natureza = null;
        for (Natureza item : Natureza.values()) {
            if (Objects.equals(item.getId(), id)) {
                natureza = item;
                break;
            }
        }
        return natureza;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
