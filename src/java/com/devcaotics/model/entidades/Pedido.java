/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.model.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ALUNO
 */
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Lob
    private String observacao;
    private String status;
    private String pagamento;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Estabelecimento estabelecimento;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
    
    public double preco(){
        double precoTotal = 0;

        if (itens == null) {
            return 0.0; 
        }

        for(ItemPedido i : itens){
            if (i != null && i.getAlimento() != null) {
                precoTotal += i.getAlimento().getPreco();

                if (i.getAcompanhamentos() != null) { // Verifica se a lista de acompanhamentos não é nula
                    for(Acompanhamento a : i.getAcompanhamentos()){
                        if (a != null) {
                            precoTotal += a.getPrecoAdicional();
                        }
                    }
                }
            }
        }
        return precoTotal;
    }
    
}
