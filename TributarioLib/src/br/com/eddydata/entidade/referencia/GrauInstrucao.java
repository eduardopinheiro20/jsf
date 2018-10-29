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
public enum GrauInstrucao {

    OUTRO(0, ""),
    ANALFABETO(14, "Analfabeto(a)"),
    FUNDAMENTAL1INCOMPLETO(1, "Até 4º série do 1º Grau Incompleto"),
    FUNDAMENTAL1COMPLETO(2, "Até 4º série do 1º Grau Completo"),
    FUNDAMENTAL2INCOMPLETO(3, "De 5º a 9º série do 1º Grau Incompleto"),
    FUNDAMENTAL2COMPLETO(4, "De 5º a 9º série do 1º Grau Completo"),
    MEDIOINCOMPLETO(5, "Ensino Médio Incompleto"),
    MEDIOCOMPLETO(6, "Ensino Médio Completo"),
    SUPERIORINCOMPLETO(7, "Superior Incompleto"),
    SUPERIORCOMPLETO(8, "Superior Completo"),
    POS(9, "Pós-Graduação Lato Sensu"),
    MESTRADO(10, "Mestrado"),
    DOUTORADO(11, "Doutorado"),
    POSDOUTORADO(12, "Pós-Doutorado"),
    LIVREDOCENTE(13, "Livre Docente");

    public Integer id;
    public String descricao;

    private GrauInstrucao(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }

    public static GrauInstrucao parse(Integer id) {
        GrauInstrucao grau = null;
        for (GrauInstrucao item : GrauInstrucao.values()) {
            if (Objects.equals(item.getId(), id)) {
                grau = item;
                break;
            }
        }
        return grau;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
