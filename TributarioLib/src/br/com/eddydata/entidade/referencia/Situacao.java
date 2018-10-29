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
public enum Situacao {
    
    ATIVO(4, "Ativo"),
    EXCLUÍDO(1, "Excluído"),
    FALECIDO(3, "Falecido"),
    SUSPENSO(2, "Suspenso");
   
    
    
    public Integer id;
    public String descricao;

    private Situacao(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
  
    
    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }
    
    public static Situacao parse(Integer id){
        Situacao situacao = null;
        for(Situacao item : Situacao.values()){
            if(Objects.equals(item.getId(), id)){
                situacao = item;
                break;
            } 
        }
        return situacao;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }
    
    
    
}
