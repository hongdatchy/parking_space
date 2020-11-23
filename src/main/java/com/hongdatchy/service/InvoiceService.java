package com.hongdatchy.service;

import com.hongdatchy.entities.data.Invoice;
import com.hongdatchy.entities.json.InvoiceJson;

import java.util.List;

public interface InvoiceService {

    InvoiceJson createAndUpdate(Invoice invoice);

    boolean delete(int id);

    List<InvoiceJson> findAll();

}
