/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean.issqn;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author tales
 */
@Named
@ViewScoped
public class CartaCobrancaBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init() {

    }
}
