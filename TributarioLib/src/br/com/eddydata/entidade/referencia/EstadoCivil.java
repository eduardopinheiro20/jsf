/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.referencia;

import java.util.Objects;

/**
 *
 * @author Leandro
 */
public enum EstadoCivil {
    
    CASADO_A(1, "Casado(a)"),
    DESQUITADO_A(3, "Desquitado(a)"),
    DIVORCIADO_A(4, "Divorciado(a)"),
    OUTRO(6, "Outro"),
    SOLTEIRO(7, "Solteiro"),
    UNIÃO_ESTÁVEL(5, "União Estável"),
    VIÚVO_A(2, "Viúvo(a)");
    
    
    
    
    public Integer id;
    public String descricao;

    private EstadoCivil(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }   

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }
    
    public static EstadoCivil parse(Integer id){
        EstadoCivil estadoCivil = null;
        for(EstadoCivil item: EstadoCivil.values()){
            if(Objects.equals(item.getId(), id)){
                estadoCivil = item;
                break;
            }
        }
        return estadoCivil;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }
    
    
    
}
