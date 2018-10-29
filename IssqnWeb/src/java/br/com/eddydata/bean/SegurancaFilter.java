/*
 * Sistema Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Eddydata
 */
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.Acesso;
import br.com.eddydata.entidade.referencia.Pagina;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SegurancaFilter implements Serializable, Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.addHeader("Pragma", "no-cache");
        resp.addHeader("Cache-Control", "no-cache");
        resp.addHeader("Cache-Control", "no-store");
        resp.addHeader("Cache-Control", "must-revalidate");
        resp.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");

        Object user = httpRequest.getSession().getAttribute("usuarioLogado");

        Logon logon = (Logon) ((HttpServletRequest) request).getSession().getAttribute("acesso");

        if (logon == null) {
            resp.sendRedirect("/issqn/login.xhtml");
            return;
        } else if (logon.getPermissoes() != null) {
            if (logon.getPermissoes().size() > 0) {
                for (Acesso access : logon.getPermissoes().get(0).getUsuarioPerfil().getAcessos()) {
                    Pagina pag = access.getPagina();
                    if (httpRequest.getRequestURI().contains(pag.getPagina())) {
                        if (access.getAtivo() == null) {
                            httpRequest.getSession().setAttribute("somenteConsulta", true);
                        } else if (!access.getAtivo()) {
                            httpRequest.getSession().setAttribute("somenteConsulta", false);
                            resp.sendRedirect(httpRequest.getRequestURI().substring(0, httpRequest.getRequestURI().indexOf("app/")) + "acesso-negado.xhtml");
                        } else {
                            httpRequest.getSession().setAttribute("somenteConsulta", false);
                        }
                    }
                }
            }
        }

        // The user has been logged !!!!
        if (user != null || httpRequest.getRequestURL().toString().endsWith("login")) {
            chain.doFilter(request, response);
        } else {
            //User is not logged !!!!
            resp.sendRedirect("/issqn/login.xhtml");
        }
    }

    @Override
    public void destroy() {
    }

}
