package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Estabelecimento;
import com.devcaotics.model.entidades.Pedido;
import com.devcaotics.model.persistenceManager.ManagerDao;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class GerenciarPedidosBean {
    private Estabelecimento estabelecimentoLogado;
    private List<Pedido> pedidosEmAberto;
    private List<Pedido> todosOsPedidos;

    @PostConstruct
    public void init() {
        LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance()
            .getExternalContext().getSessionMap().get("loginBean");
        this.estabelecimentoLogado = loginBean.getEstabelecimentoLogado();

        this.pedidosEmAberto = ManagerDao.getCurrentInstance().read(
            "select p from Pedido p where p.estabelecimento.codigo = " + estabelecimentoLogado.getCodigo() + " and p.status = 'em aberto'", Pedido.class);
            
        this.todosOsPedidos = ManagerDao.getCurrentInstance().read(
            "select p from Pedido p where p.estabelecimento.codigo = " + estabelecimentoLogado.getCodigo(), Pedido.class);
    }

    public void confirmarPedido(Pedido pedido) {
        pedido.setStatus("confirmado");
        ManagerDao.getCurrentInstance().update(pedido);

        this.pedidosEmAberto = ManagerDao.getCurrentInstance().read(
            "select p from Pedido p where p.estabelecimento.codigo = " + estabelecimentoLogado.getCodigo() + " and p.status = 'em aberto'", Pedido.class);
            
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Pedido #" + pedido.getCodigo() + " confirmado."));
    }

    public List<Pedido> getPedidosEmAberto() { return pedidosEmAberto; }
    public List<Pedido> getTodosOsPedidos() { return todosOsPedidos; }
}