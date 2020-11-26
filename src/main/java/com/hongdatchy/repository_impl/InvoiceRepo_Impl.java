package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Invoice;
import com.hongdatchy.repository.InvoiceRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class InvoiceRepo_Impl implements InvoiceRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Invoice createAndUpdate(Invoice invoice) {
        return entityManager.merge(invoice);
    }

    @Override
    public boolean delete(int id) {
        Invoice invoice = entityManager.find(Invoice.class, id);
        if(invoice != null){
            entityManager.createQuery("delete from Contract x where x.invoiceId =:id")
            .setParameter("id", id).executeUpdate();
            entityManager.remove(invoice);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Invoice> findAll() {
        return entityManager.createQuery("select x from Invoice x").getResultList();
    }
}
