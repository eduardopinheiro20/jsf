/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.admin.Usuario;
import br.com.eddydata.entidade.admin.UsuarioSistema;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.StringMD5;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public UsuarioRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Usuario getUsuario(int id) {
        return getEntidade(Usuario.class, id);
    }

    public Usuario setUsuario(Usuario u) {
        return setEntidade(Usuario.class, u);
    }

    public Usuario adicionarUsuario(Usuario u) {
        u.setSenha(StringMD5.md5(u.getSenha()));
        adicionarEntidade(Usuario.class, u);
        return u;
    }

    public Usuario registrarUsuario(Usuario u, Integer idCidade) {
        ContabilOrgao o = new ContabilOrgao();
        switch (u.getTipo()) {
            case "C":
                // Camara
                o.setIdOrgao("010000");
                o.setNome("CÂMARA MUNICIPAL");
                break;
            case "P":
                // Prefeitura
                o.setIdOrgao("020000");
                o.setNome("PREFEITURA MUNICIPAL");
                break;
            case "A":
                // Autarquia
                o.setIdOrgao("030000");
                o.setNome("AUTARQUIA MUNICIPAL");
                break;
            case "F":
                // Fundacao
                o.setIdOrgao("040000");
                o.setNome("FUNDAÇÃO MUNICIPAL");
                break;
            case "R":
                // Previdencia
                o.setIdOrgao("050000");
                o.setNome("PREVIDÊNCIA MUNICIPAL");
                break;
            case "G":
                // Depto Agua
                o.setIdOrgao("060000");
                o.setNome("DEPARTAMENTO DE ÁGUA E ESGOTO");
                break;
            case "E":
                // Empresa publica
                o.setIdOrgao("070000");
                o.setNome("EMPRESA MUNICIPAL");
                break;
        }
        Cidade c = getCidadePorId(idCidade);
        ContabilOrgao newOrgao = getOrgaoPorCodigo(o.getIdOrgao(), c.getIdCidade());
        if (newOrgao == null) {
            o.setTipoOrgao(u.getTipo());
            o.setCnpj("-");
            o.setBairro("-");
            o.setCep("-");
            o.setEmail("-");
            o.setEndereco("-");
            o.setTelefone("-");
            o.setIdCidade(c);
            o.setIdTribunal(1);
            o.setNumero(0);
            adicionarEntidade(ContabilOrgao.class, o);
            u.setOrgao(o);
        } else {
            u.setOrgao(newOrgao);
        }

        u.setSenha(StringMD5.md5(u.getSenha()));
        adicionarEntidade(Usuario.class, u);
        return u;
    }

    public void removerUsuario(Usuario u) {
        removerEntidade(u);
    }

    public void setSenha(String novaSenha, int idUsuario) {
        String np = StringMD5.md5(novaSenha);
        Usuario usuario = getUsuario(idUsuario);
        usuario.setSenha(np);
        setUsuario(usuario);
    }

    public Usuario getUsuarioPorId(int id) {
        return getEntityManager().find(Usuario.class, id);
    }

    public Usuario getUsuarioPorLoginSenha(String login, String senha) {
        Usuario u = getEntidadePura(Usuario.class, "select u from Usuario u where u.email = ?1 and u.senha = ?2",
                login, StringMD5.md5(senha));
        return u;
    }
    
    public Usuario getUsuarioPorCPFESenha(String login, String senha) {
        Usuario u = getEntidadePura(Usuario.class, "select u from Usuario u where u.cpf = ?1 and u.senha = ?2",
                login, StringMD5.md5(senha));
        return u;
    }    
    
    private Cidade getCidadePorId(Integer cidadeId) {
        Cidade c = getEntidadePura(Cidade.class, "select c from Cidade c where c.id = ?1", cidadeId);
        return c;
    }

    private ContabilOrgao getOrgaoPorCodigo(String orgaoCodigo, Integer cidadeId) {
        try {
            ContabilOrgao o = getEntidadePura(ContabilOrgao.class, "select o from ContabilOrgao o where o.idOrgao = ?1 and o.idCidade.id = ?2", orgaoCodigo, cidadeId);
            return o;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Usuario> getUsuarios(boolean somenteAtivos) {
        return getListaPura(Usuario.class,
                "select u from Usuario u "
                + (somenteAtivos ? "\n where u.ativo = 1" : "")
                + "\n order by u.nome");
    }

    public List<UsuarioSistema> getUsuarioSistemaPorSistema(String sistema, boolean somenteAtivos) {
        return getListaPura(UsuarioSistema.class, "select us from UsuarioSistema us "
                + "\njoin us.usuarioPerfil up"
                + "\njoin us.usuario u"
                + "\nwhere up.sistema.nome = ?1"
                + (somenteAtivos ? " and u.ativo = 1" : "")
                + "\n order by u.nome", sistema);
    }

    public List<Usuario> getUsuarioPorSistema(String sistema, boolean somenteAtivos) {
        List<Integer> lstUsuarios = getListaPura(Integer.class,
                "select distinct u.idUsuario from UsuarioSistema us "
                + "\njoin us.usuarioPerfil up"
                + "\njoin us.usuario u"
                + "\nwhere up.sistema.nome = ?1"
                + (somenteAtivos ? " and u.ativo = 1" : "")
                + "\n order by u.nome", sistema);
        String _ids = "";
        for (Integer id_usuario : lstUsuarios) {
            if (!_ids.equals("")) {
                _ids += ", ";
            }
            _ids += id_usuario;
        }

        if (_ids.equals("")) {
            return null;
        } else {
            return getListaPura(Usuario.class, "select u from Usuario u where u.idUsuario in(" + _ids + ")");
        }
    }

    public List<Usuario> getUsuarioPorNome(String nome, String orgao, String sistema, boolean somenteAtivos) {
        nome = nome.toUpperCase();
        return getListaPura(Usuario.class, "select u from Usuario u "
                + "\njoin u.orgao o"
                + "\nwhere UPPER(u.nome) like ?1 and o.idOrgao = ?2 and u.sistema = ?3"
                + (somenteAtivos ? " and u.ativo = 1" : "")
                + "\norder by u.nome", nome + "%", orgao, sistema);
    }

    public List<Usuario> getUsuarioPorNome(String nome, String orgao, boolean somenteAtivos) {
        nome = nome.toUpperCase();
        return getListaPura(Usuario.class, "select u from Usuario u "
                + "\njoin u.orgao o"
                + "\nwhere UPPER(u.nome) like ?1 "
                + "\nand o.idOrgao = ?2 "
                + (somenteAtivos ? " and u.ativo = 1" : "")
                + "\norder by u.nome", nome + "%", orgao);
    }

    public Usuario getUsuarioPorEmail(String email) {
        Usuario u = getEntidadePura(Usuario.class, "select u from Usuario u where u.email = ?1 and u.ativo = true", email);
        if (u == null) {
            u = getEntidadePura(Usuario.class, "select u from Usuario u where u.email = ?1", email);
        }
        return u;
    }

}
