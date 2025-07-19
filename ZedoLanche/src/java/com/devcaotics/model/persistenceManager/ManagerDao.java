package com.devcaotics.model.persistenceManager;

import com.devcaotics.model.entidades.Cliente;
import com.devcaotics.model.entidades.Estabelecimento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManagerDao {
    
    private static ManagerDao myself = null;
    
    public static ManagerDao getCurrentInstance(){
        if(myself == null)
            myself = new ManagerDao();
        
        return myself;
    }
    
    private EntityManagerFactory emf = null;
            
    private ManagerDao(){
        this.emf = Persistence.createEntityManagerFactory("ZedoLanchePU");
    } 
    
    public void insert(Object o){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }
    
    public void update(Object o){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
    }
    
    public List read(String query,Class c){
        EntityManager em = emf.createEntityManager();
        List returnedList = em.createQuery(query,c).getResultList();
        em.close();
        return returnedList;
    }
    
    public void delete(Object o){
        EntityManager em = emf.createEntityManager();
        Object oDelete = o;
        if(!em.contains(o)){
            oDelete = em.merge(o);
        }
        em.getTransaction().begin();
        em.remove(oDelete);
        em.getTransaction().commit();
        em.close();
    }

    public List<Cliente> buscarClientePorLogin(String email, String senha) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select c from Cliente c where c.email = :email and c.senha = :senha", Cliente.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Estabelecimento> buscarEstabelecimentoPorLogin(String email, String senha) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select e from Estabelecimento e where e.email = :email and e.senha = :senha", Estabelecimento.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}