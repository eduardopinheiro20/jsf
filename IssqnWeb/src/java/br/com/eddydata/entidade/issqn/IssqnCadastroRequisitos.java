/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author d.morais
 */
@Entity
@Table(name = "issqn_cadastro_requisitos")
public class IssqnCadastroRequisitos implements Serializable {

    private static final long serialVersionUID = 1L;

    public IssqnCadastroRequisitos() {
        this.ativo = true;
    }
    private IssqnCadastroRequisitos repositorio;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nome", length = 255)
    private String nome;
    
    @Column(name = "caracteristica", length = 5000)
    private String caracteristica;
    
    @Column(name = "classe",length = 255)
    private String classe;
    
    @Column(name = "nivel_impacto",length = 255)
    private String nivelImpacto;
    
    @Column(name = "ativo")
    private Boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNivelImpacto() {
        return nivelImpacto;
    }

    public void setNivelImpacto(String nivelImpacto) {
        this.nivelImpacto = nivelImpacto;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.nome);
        hash = 37 * hash + Objects.hashCode(this.caracteristica);
        hash = 37 * hash + Objects.hashCode(this.classe);
        hash = 37 * hash + Objects.hashCode(this.nivelImpacto);
        hash = 37 * hash + Objects.hashCode(this.ativo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IssqnCadastroRequisitos other = (IssqnCadastroRequisitos) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.caracteristica, other.caracteristica)) {
            return false;
        }
        if (!Objects.equals(this.classe, other.classe)) {
            return false;
        }
        if (!Objects.equals(this.nivelImpacto, other.nivelImpacto)) {
            return false;
        }
        if (!Objects.equals(this.ativo, other.ativo)) {
            return false;
        }
        return true;
    }
        public List<IssqnZonaMunicipio> obterCadastroRequisitos(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterCadastroRequisitos(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    

}
