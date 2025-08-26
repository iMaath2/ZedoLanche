package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Cliente;
import com.devcaotics.model.entidades.Estabelecimento;
import com.devcaotics.model.persistenceManager.ManagerDao;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
    private String email;
    private String senha;
    private Cliente clienteLogado;
    private Estabelecimento estabelecimentoLogado;

    public String login() {
        List<Cliente> clientes = ManagerDao.getCurrentInstance().buscarClientePorLogin(this.email, this.senha);
        
        if (!clientes.isEmpty()) {
            this.clienteLogado = clientes.get(0);
            this.estabelecimentoLogado = null;
            return "/restrito/cliente/home.xhtml?faces-redirect=true";
        }
        
        List<Estabelecimento> estabelecimentos = ManagerDao.getCurrentInstance().buscarEstabelecimentoPorLogin(this.email, this.senha);
        
        if (!estabelecimentos.isEmpty()) {
            this.estabelecimentoLogado = estabelecimentos.get(0);
            this.clienteLogado = null;
            return "/restrito/estabelecimento/home.xhtml?faces-redirect=true";
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Email ou senha inválidos."));
        return null;
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean getUsuarioClienteLogado() {
        return this.clienteLogado != null;
    }

    public boolean getUsuarioEstabelecimentoLogado() {
        return this.estabelecimentoLogado != null;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Cliente getClienteLogado() { return clienteLogado; }
    public Estabelecimento getEstabelecimentoLogado() { return estabelecimentoLogado; }
}