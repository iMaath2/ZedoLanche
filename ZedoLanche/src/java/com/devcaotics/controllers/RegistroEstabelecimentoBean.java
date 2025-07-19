package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Estabelecimento;
import com.devcaotics.model.persistenceManager.ManagerDao;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "registroEstabelecimentoBean")
@ViewScoped
public class RegistroEstabelecimentoBean {

    private Estabelecimento novoEstabelecimento;

    public RegistroEstabelecimentoBean() {
        this.novoEstabelecimento = new Estabelecimento();
    }

    public String registrar() {
        try {
            ManagerDao.getCurrentInstance().insert(novoEstabelecimento);
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Cadastro de estabelecimento realizado. Faça o login."));
            
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            return "/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Ocorreu um erro no cadastro do estabelecimento."));
            
            return null;
        }
    }

    public Estabelecimento getNovoEstabelecimento() {
        return novoEstabelecimento;
    }

    public void setNovoEstabelecimento(Estabelecimento novoEstabelecimento) {
        this.novoEstabelecimento = novoEstabelecimento;
    }
}