package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Acompanhamento;
import com.devcaotics.model.entidades.Alimento;
import com.devcaotics.model.entidades.Estabelecimento;
import com.devcaotics.model.persistenceManager.ManagerDao;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CardapioBean {
    private Estabelecimento estabelecimentoLogado;
    private List<Alimento> todosAlimentos; // Alimentos do sistema para adicionar
    private List<Acompanhamento> todosAcompanhamentos; // Acompanhamentos do sistema

    @PostConstruct
    public void init() {
        LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance()
            .getExternalContext().getSessionMap().get("loginBean");
        this.estabelecimentoLogado = loginBean.getEstabelecimentoLogado();

        this.todosAlimentos = ManagerDao.getCurrentInstance().read("select a from Alimento a", Alimento.class);
        this.todosAcompanhamentos = ManagerDao.getCurrentInstance().read("select a from Acompanhamento a", Acompanhamento.class);
    
        if (this.estabelecimentoLogado.getLanches() == null) {
            this.estabelecimentoLogado.setLanches(new ArrayList<>());
        }
        if (this.estabelecimentoLogado.getAcompanhamento() == null) {
            this.estabelecimentoLogado.setAcompanhamento(new ArrayList<>());
        }
    }

    public void adicionarAlimento(Alimento alimento) {
        if (!this.estabelecimentoLogado.getLanches().contains(alimento)) {
            this.estabelecimentoLogado.getLanches().add(alimento);
            ManagerDao.getCurrentInstance().update(this.estabelecimentoLogado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", alimento.getNome() + " adicionado ao cardápio."));
        }
    }

    public void adicionarAcompanhamento(Acompanhamento acomp) {
        if (!this.estabelecimentoLogado.getAcompanhamento().contains(acomp)) {
            this.estabelecimentoLogado.getAcompanhamento().add(acomp);
            ManagerDao.getCurrentInstance().update(this.estabelecimentoLogado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", acomp.getNome() + " adicionado aos acompanhamentos."));
        }
    }

    public Estabelecimento getEstabelecimentoLogado() { return estabelecimentoLogado; }
    public List<Alimento> getTodosAlimentos() { return todosAlimentos; }
    public List<Acompanhamento> getTodosAcompanhamentos() { return todosAcompanhamentos; }
}