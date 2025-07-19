package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Alimento;
import com.devcaotics.model.persistenceManager.ManagerDao;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "alimentoBean")
@ViewScoped
public class AlimentoBean {
    private Alimento alimento;
    private List<Alimento> alimentos;

    @PostConstruct
    public void init() {
        this.alimento = new Alimento();
        this.alimentos = ManagerDao.getCurrentInstance().read("select a from Alimento a", Alimento.class);
    }

    public void salvar() {
        try {
            if (this.alimento.getCodigo() == 0) {
                ManagerDao.getCurrentInstance().insert(this.alimento);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Alimento cadastrado."));
            } else {
                ManagerDao.getCurrentInstance().update(this.alimento);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Alimento atualizado."));
            }
            this.alimento = new Alimento();
            this.alimentos = ManagerDao.getCurrentInstance().read("select a from Alimento a", Alimento.class);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro!", "Ocorreu um erro ao salvar o alimento."));
        }
    }

    public void remover() {
        try {
            ManagerDao.getCurrentInstance().delete(this.alimento);
            this.alimentos = ManagerDao.getCurrentInstance().read("select a from Alimento a", Alimento.class);
            this.alimento = new Alimento();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Alimento removido."));
        } catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro!", "Ocorreu um erro ao remover."));
        }
    }

    public Alimento getAlimento() { return alimento; }
    public void setAlimento(Alimento alimento) { this.alimento = alimento; }
    public List<Alimento> getAlimentos() { return alimentos; }
    public void setAlimentos(List<Alimento> alimentos) { this.alimentos = alimentos; }
}