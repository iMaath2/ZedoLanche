package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Estabelecimento;
import com.devcaotics.model.persistenceManager.ManagerDao;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class PerfilEstabelecimentoBean {
    private Estabelecimento estabelecimento;
    private String senhaAntiga;
    private String novaSenha;
    private String confirmaNovaSenha;

    @PostConstruct
    public void init() {
        LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
        this.estabelecimento = loginBean.getEstabelecimentoLogado();
    }

    public void alterarDados() {
        try {
            ManagerDao.getCurrentInstance().update(this.estabelecimento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Dados alterados."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Falha ao alterar dados."));
        }
    }

    public void alterarSenha() {
        if (!estabelecimento.getSenha().equals(senhaAntiga)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "Senha antiga não confere."));
            return;
        }
        if (!novaSenha.equals(confirmaNovaSenha)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "A nova senha e a confirmação não são iguais."));
            return;
        }
        
        this.estabelecimento.setSenha(novaSenha);
        ManagerDao.getCurrentInstance().update(this.estabelecimento);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Senha alterada."));
    }

    public Estabelecimento getEstabelecimento() { return estabelecimento; }
    public void setEstabelecimento(Estabelecimento est) { this.estabelecimento = est; }
    public String getSenhaAntiga() { return senhaAntiga; }
    public void setSenhaAntiga(String senhaAntiga) { this.senhaAntiga = senhaAntiga; }
    public String getNovaSenha() { return novaSenha; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
    public String getConfirmaNovaSenha() { return confirmaNovaSenha; }
    public void setConfirmaNovaSenha(String confirmaNovaSenha) { this.confirmaNovaSenha = confirmaNovaSenha; }
}