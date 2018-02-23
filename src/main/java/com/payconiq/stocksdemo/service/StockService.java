package com.payconiq.stocksdemo.service;

import java.util.List;

import com.payconiq.stocksdemo.model.StockViewModel;

public interface StockService {

    List<StockViewModel> getAllStocks();

    StockViewModel getStock (String name);

    boolean updateStockPrice(StockViewModel model);

    boolean createNewStock(StockViewModel viewModel);

}
