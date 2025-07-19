/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.controllers;

import com.devcaotics.model.entidades.Acompanhamento;
import com.devcaotics.model.persistenceManager.ManagerDao;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ALUNO
 */
@ManagedBean
public class Controlador {
    
    public void inserir(){
        Acompanhamento a = new Acompanhamento();
        
        a.setAtivo(true);
        a.setNome("Gorgonzola");
        a.setPrecoAdicional(1);
        a.setDescricao("cada porção tem 0.01 grama de gorgonzola");
        
        ManagerDao.getCurrentInstance().insert(a);
        
    }
    
}
