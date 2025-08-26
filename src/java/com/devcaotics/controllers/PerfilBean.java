package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Cliente;
import com.devcaotics.model.persistenceManager.ManagerDao;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class PerfilBean {
    private Cliente cliente;
    private String senhaAntiga;
    private String novaSenha;
    private String confirmaNovaSenha;

    @PostConstruct
    public void init() {
        LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
        this.cliente = loginBean.getClienteLogado();
    }

    public void alterarDados() {
        try {
            ManagerDao.getCurrentInstance().update(this.cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Dados alterados."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Falha ao alterar dados."));
        }
    }

    public void alterarSenha() {
        if (!cliente.getSenha().equals(senhaAntiga)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "Senha antiga não confere."));
            return;
        }
        if (!novaSenha.equals(confirmaNovaSenha)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "A nova senha e a confirmação não são iguais."));
            return;
        }
        
        this.cliente.setSenha(novaSenha);
        ManagerDao.getCurrentInstance().update(this.cliente);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Senha alterada."));
    }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public String getSenhaAntiga() { return senhaAntiga; }
    public void setSenhaAntiga(String senhaAntiga) { this.senhaAntiga = senhaAntiga; }
    public String getNovaSenha() { return novaSenha; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
    public String getConfirmaNovaSenha() { return confirmaNovaSenha; }
    public void setConfirmaNovaSenha(String confirmaNovaSenha) { this.confirmaNovaSenha = confirmaNovaSenha; }
}