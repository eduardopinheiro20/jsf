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


public enum Cor {
    
    /**
     *
     */
   
    AMARELO_A(2, "Amarelo(a)"),
    BRANCO_A(6, "Branco(a)"),
    INDIGENA(4, "Indígena"),
    NEGRO_A(1, "Negro(a)"),
    PARDO_A(3, "Pardo(a)"),
    SEM_DECLARAÇÃO(5, "Sem Declaracão");
    
    
    public Integer id;
    
    public String descricao;

    
    private Cor(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
 
  

    
    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }

    
    
    public static Cor parse(Integer id){
        Cor cor = null;
        for(Cor item: Cor.values()){
            if(Objects.equals(item.getId(), id)){
                cor = item;
                break;
            }
           
        }
        return cor;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }
    
    
}
