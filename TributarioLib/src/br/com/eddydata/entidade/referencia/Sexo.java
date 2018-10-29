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
public enum Sexo {
    
    EMPRESA(2, "Empresa"),
    FEMININO(1, "Feminino"),
    MASCULINO(3, "Masculino");
    
    public Integer id;
    public String descricao;

    private Sexo(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }
    
    public static Sexo parse(Integer id){
        Sexo sexo = null;
        for(Sexo item : Sexo.values()){
            if(Objects.equals(item.getId(), id)){
                sexo = item;
                break;
            } 
           
        }
        return sexo;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }
    
    
    
}
