package com.payconiq.stocksdemo.service;

import com.payconiq.stocksdemo.model.StockViewModel;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Profile("sandbox")
@Primary
public class StupStockService implements StockService {
    @Override
    public List<StockViewModel> getAllStocks() {
        //handel sandbox request, for example producing internal Server error.
        return Collections.emptyList();
    }

    @Override
    public StockViewModel getStock(String name) {
        return new StockViewModel();
    }

    @Override
    public boolean updateStockPrice(StockViewModel model) {
        return true;
    }

    @Override
    public boolean createNewStock(StockViewModel viewModel) {
        return true;
    }
}
