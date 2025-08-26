package com.devcaotics.controllers;

import com.devcaotics.model.entidades.*;
import com.devcaotics.model.persistenceManager.ManagerDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class FazerPedidoBean {

    private String termoBusca;
    private List<Estabelecimento> estabelecimentosEncontrados;
    private Estabelecimento estabelecimentoSelecionado;

    private Alimento lancheAtual;
    private List<Acompanhamento> acompanhamentosSelecionados;
    private Pedido carrinho;
    private Cliente clienteLogado;

    @PostConstruct
    public void init() {
        this.carrinho = new Pedido();
        this.carrinho.setItens(new ArrayList<>());
        
        LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance()
            .getExternalContext().getSessionMap().get("loginBean");
        this.clienteLogado = loginBean.getClienteLogado();
    }

    public void buscarEstabelecimentos() {
        this.estabelecimentosEncontrados = ManagerDao.getCurrentInstance().read(
            "select e from Estabelecimento e where e.nome like '%" + termoBusca + "%'", Estabelecimento.class);
    }

    public void selecionarEstabelecimento(Estabelecimento e) {
        this.estabelecimentoSelecionado = e;
        this.carrinho = new Pedido();
        this.carrinho.setItens(new ArrayList<>());
    }
    
    public void prepararAdicionarLanche(Alimento lanche) {
        System.out.println("Preparando para adicionar o lanche: " + lanche.getNome());
        this.lancheAtual = lanche;
        this.acompanhamentosSelecionados = new ArrayList<>();
    }

    public void adicionarLancheAoCarrinho() {
        System.out.println("--- DENTRO DE adicionarLancheAoCarrinho ---");
        
        if (lancheAtual == null) {
            System.out.println("ERRO: O lancheAtual é nulo!");
            return;
        }
        
        System.out.println("Lanche a ser adicionado: " + lancheAtual.getNome());
        
        if (acompanhamentosSelecionados != null) {
            System.out.println("Acompanhamentos selecionados: " + acompanhamentosSelecionados.size());
            for(Acompanhamento a : acompanhamentosSelecionados) {
                System.out.println("  - " + a.getNome());
            }
        } else {
            System.out.println("ERRO: A lista de acompanhamentosSelecionados é nula!");
        }

        ItemPedido item = new ItemPedido();
        item.setAlimento(lancheAtual);
        item.setAcompanhamentos(new ArrayList<>(acompanhamentosSelecionados));
        
        this.carrinho.getItens().add(item);
        
        System.out.println("Tamanho atual do carrinho: " + this.carrinho.getItens().size());
        System.out.println("Preço calculado: " + this.carrinho.preco());
        System.out.println("----------------------------------------");
        
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Sucesso!", lancheAtual.getNome() + " adicionado ao carrinho."));
    }

    public String finalizarPedido() {
        this.carrinho.setCliente(this.clienteLogado);
        this.carrinho.setEstabelecimento(this.estabelecimentoSelecionado);
        this.carrinho.setDataHora(new Date());
        this.carrinho.setStatus("em aberto");

        ManagerDao.getCurrentInstance().insert(carrinho);
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Seu pedido foi realizado!"));
        
        return "/restrito/cliente/home.xhtml?faces-redirect=true";
    }
    
    public String getTermoBusca() { return termoBusca; }
    public void setTermoBusca(String termoBusca) { this.termoBusca = termoBusca; }
    public List<Estabelecimento> getEstabelecimentosEncontrados() { return estabelecimentosEncontrados; }
    public Estabelecimento getEstabelecimentoSelecionado() { return estabelecimentoSelecionado; }
    public Alimento getLancheAtual() { return lancheAtual; }
    public void setLancheAtual(Alimento lancheAtual) { this.lancheAtual = lancheAtual; }
    public List<Acompanhamento> getAcompanhamentosSelecionados() { return acompanhamentosSelecionados; }
    public void setAcompanhamentosSelecionados(List<Acompanhamento> acompanhamentos) { this.acompanhamentosSelecionados = acompanhamentos; }
    public Pedido getCarrinho() { return carrinho; }
}