package com.devcaotics.controllers;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/restrito/*")
public class ControleAcessoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        LoginBean loginBean = (LoginBean) req.getSession().getAttribute("loginBean");

        if (loginBean == null || (!loginBean.getUsuarioClienteLogado() && !loginBean.getUsuarioEstabelecimentoLogado())) {
            resp.sendRedirect(req.getContextPath() + "/login.xhtml");    
        } else if (req.getRequestURI().contains("/restrito/cliente/") && !loginBean.getUsuarioClienteLogado()) {
            resp.sendRedirect(req.getContextPath() + "/restrito/estabelecimento/home.xhtml");
        } else if (req.getRequestURI().contains("/restrito/estabelecimento/") && !loginBean.getUsuarioEstabelecimentoLogado()) {
            resp.sendRedirect(req.getContextPath() + "/restrito/cliente/home.xhtml");      
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}