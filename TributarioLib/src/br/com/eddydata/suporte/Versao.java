/*
 * Versao.java
 *
 * Created on 6 de Setembro de 2006, 14:20
 *
 */

package br.com.eddydata.suporte;

public class Versao {
    private int ano;
    private int mes;
    private int build;
    
    public Versao(int ano, int mes, int build) {
        this.ano = ano;
        this.mes = mes;
        this.build = build;
    }
    
    public String getVersao() {
        return Integer.toString(getAno())+"."+Integer.toString(getMes())+"."+
            Integer.toString(getBuild());
    }
    
    public int getIntValue() {
        return Integer.parseInt(getAno() + "" + Util.formatarDecimal("00",getMes()) + "" + 
                Util.formatarDecimal("00",getBuild()));
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }
}
