package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Cliente;
import com.devcaotics.model.persistenceManager.ManagerDao;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "clienteBean")
@ViewScoped
public class ClienteBean {
    private Cliente cliente;
    private List<Cliente> clientes;

    @PostConstruct
    public void init() {
        this.cliente = new Cliente();
        this.clientes = ManagerDao.getCurrentInstance().read("select c from Cliente c", Cliente.class);
    }

    public void salvar() {
        try {
            if (this.cliente.getCodigo() == 0) {
                ManagerDao.getCurrentInstance().insert(this.cliente);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Cliente cadastrado."));
            } else {
                ManagerDao.getCurrentInstance().update(this.cliente);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Cliente atualizado."));
            }
            this.cliente = new Cliente();
            this.clientes = ManagerDao.getCurrentInstance().read("select c from Cliente c", Cliente.class);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro!", "Ocorreu um erro ao salvar o cliente."));
        }
    }
    
    public void remover() {
        try {
            ManagerDao.getCurrentInstance().delete(this.cliente);
            this.clientes = ManagerDao.getCurrentInstance().read("select c from Cliente c", Cliente.class);
            this.cliente = new Cliente();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!", "Cliente removido."));
        } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro!", "Ocorreu um erro ao remover o cliente."));
        }
    }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }
}