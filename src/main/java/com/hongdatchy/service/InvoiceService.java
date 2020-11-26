package com.hongdatchy.service;

import com.hongdatchy.entities.data.Invoice;
import com.hongdatchy.entities.json.InvoiceJson;
import com.hongdatchy.entities.payload.InvoicePayload;

import java.util.List;

public interface InvoiceService {

    InvoiceJson createAndUpdate(InvoicePayload invoicePayload);

    boolean delete(int id);

    List<InvoiceJson> findAll();

}
