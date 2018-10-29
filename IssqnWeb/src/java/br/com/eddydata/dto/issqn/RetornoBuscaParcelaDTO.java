/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.dto.issqn;

/**
 *
 * @author eddydata
 */
public class RetornoBuscaParcelaDTO {

    private int idIss;
    private int idMovimento;
    private String nome;
    private String inscricao;
    private String endereco;
    private String tpMovimento;

    public RetornoBuscaParcelaDTO() {
    }

    public int getIdIss() {
        return idIss;
    }

    public void setIdIss(int idIss) {
        this.idIss = idIss;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTpMovimento() {
        return tpMovimento;
    }

    public void setTpMovimento(String tpMovimento) {
        this.tpMovimento = tpMovimento;
    }

}
