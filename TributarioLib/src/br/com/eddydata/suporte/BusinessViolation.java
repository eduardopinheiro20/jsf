/**
 * Copyright (C) 2012, Eddydata ltda.
 * 
 * @author Eddydata
 * @version 1.0
 * 01 de agosto de 2012
 */
package br.com.eddydata.suporte;

public class BusinessViolation extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessViolation(String msg){
        super(msg);
    }

}
