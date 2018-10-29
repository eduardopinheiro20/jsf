/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.referencia;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Leandro
 */

public enum UfRg {
    
    
    AC("AC"), AL("AL"), AM("AM"), AP("AP"), BA("BA"), CE("CE"), DF("DF"), ES("ES"), GO("GO"), MA("MA"), MG("MG"), MS("MS"), 
    MT("MT"), PA("PA"), PB("PB"), PE("PE"), PI("PI"), PR("PR"), RJ("RJ"), RN("RN"), RO("RO"), RR("RR"), RS("RS"), SC("SC"), 
    SE("SE"), SP("SP"), TO("TO");
    
    public String descricao;

    private UfRg(String descricao) {
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
