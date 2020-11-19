package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Invoice;

import java.util.List;

public interface InvoiceRepo {

    Invoice createAndUpdate(Invoice invoice);

    boolean delete(int id);

    List<Invoice> findAll();

}
