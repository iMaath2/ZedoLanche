package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Cliente;
import com.devcaotics.model.persistenceManager.ManagerDao;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "registroClienteBean")
@ViewScoped
public class RegistroClienteBean {
    private Cliente novoCliente = new Cliente();

    public String registrar() {
        try {
            ManagerDao.getCurrentInstance().insert(novoCliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Cadastro realizado. Faça o login."));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Ocorreu um erro no cadastro."));
            return null;
        }
    }

    public Cliente getNovoCliente() { return novoCliente; }
    public void setNovoCliente(Cliente novoCliente) { this.novoCliente = novoCliente; }
}