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
public enum Sociedade {

    OUTROS(0, ""),
    ASSOCIACAO(9, "Associação"),
    CONDOMINIO(1, "Condomínio"),
    COOPERATIVA(2, "Cooperativa"),
    FUNDACAO(3, "Fundação"),
    MICROEMPRESA(4, "Micro-empresa"),
    PUBLICO(5, "Orgão/Entidade Pública"),
    SA(6, "Sociedade Anônima"),
    SC(7, "S/C Ltda."),
    CIVIL(8, "Sociedade Civil"),
    TEMPLO(10, "Templo Religioso");

    public Integer id;
    public String descricao;

    private Sociedade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }

    public static Sociedade parse(Integer id) {
        Sociedade sociedade = null;
        for (Sociedade item : Sociedade.values()) {
            if (Objects.equals(item.getId(), id)) {
                sociedade = item;
                break;
            }
        }
        return sociedade;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
