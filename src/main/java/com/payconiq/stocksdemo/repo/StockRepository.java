package com.payconiq.stocksdemo.repo;

import com.payconiq.stocksdemo.domain.Stock;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {

     List<Stock> findStockByName(String name);

     List<Stock> findAll();
}
