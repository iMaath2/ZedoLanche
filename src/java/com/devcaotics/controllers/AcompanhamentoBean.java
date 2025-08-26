package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Acompanhamento;
import com.devcaotics.model.persistenceManager.ManagerDao;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "acompBean")
@ViewScoped
public class AcompanhamentoBean {
    private Acompanhamento acompanhamento;
    private List<Acompanhamento> acompanhamentos;

    @PostConstruct
    public void init() {
        this.acompanhamento = new Acompanhamento();
        this.acompanhamentos = ManagerDao.getCurrentInstance().read("select a from Acompanhamento a", Acompanhamento.class);
    }

    public void salvar() {
        try {
            if (this.acompanhamento.getCodigo() == 0) {
                ManagerDao.getCurrentInstance().insert(this.acompanhamento);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Acompanhamento cadastrado."));
            } else {
                ManagerDao.getCurrentInstance().update(this.acompanhamento);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Acompanhamento atualizado."));
            }
            this.acompanhamento = new Acompanhamento();
            this.acompanhamentos = ManagerDao.getCurrentInstance().read("select a from Acompanhamento a", Acompanhamento.class);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro!", "Ocorreu uma falha ao salvar."));
        }
    }

    public void remover() {
        try {
            ManagerDao.getCurrentInstance().delete(this.acompanhamento);
            this.acompanhamentos = ManagerDao.getCurrentInstance().read("select a from Acompanhamento a", Acompanhamento.class);
            this.acompanhamento = new Acompanhamento();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Acompanhamento removido."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro!", "Ocorreu uma falha ao remover."));
        }
    }

    public Acompanhamento getAcompanhamento() { return acompanhamento; }
    public void setAcompanhamento(Acompanhamento acompanhamento) { this.acompanhamento = acompanhamento; }
    public List<Acompanhamento> getAcompanhamentos() { return acompanhamentos; }
    public void setAcompanhamentos(List<Acompanhamento> acompanhamentos) { this.acompanhamentos = acompanhamentos; }
}