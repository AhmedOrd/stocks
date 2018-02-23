package com.payconiq.stocksdemo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.payconiq.stocksdemo.repo.StockRepository;
import com.payconiq.stocksdemo.domain.Stock;
import com.payconiq.stocksdemo.exception.DuplicateStockException;
import com.payconiq.stocksdemo.exception.StockNotFoundException;
import com.payconiq.stocksdemo.model.StockViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile("regular")
public class StockServiceImpl implements StockService{

    private StockRepository repository;

    @Autowired
    public StockServiceImpl(StockRepository repository) {
        this.repository = repository;
    }

    public List<StockViewModel> getAllStocks() {
        List<Stock> stocks = repository.findAll();
        if (stocks != null) {
            log.debug("All stock retrieved successfully, {} stocks have been found", stocks.size());
            return stocks.stream()
                         .map(StockViewModel::toModel)
                         .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Optional<Stock> retrieveStock(String name) {
        return repository.findStockByName(name).stream().findFirst();
    }

    public StockViewModel getStock (String name) {
        Stock stock = retrieveStock(name).orElseThrow(StockNotFoundException::new);
        return StockViewModel.toModel(stock);
    }

    public boolean updateStockPrice(StockViewModel model) {
        Stock stock = retrieveStock(model.getName()).orElseThrow(StockNotFoundException::new);
        stock.setPrice(model.getAmount());
        log.debug("Stock has been found, stock name {}", model.getName());
        return repository.save(stock) != null;
    }

    public boolean createNewStock(StockViewModel viewModel) {
        Optional<Stock> existingStock = retrieveStock(viewModel.getName());
        if (existingStock.isPresent()) {
            throw new DuplicateStockException("Duplicate: Found Stock with the same name");
        }
        Stock newStock = viewModel.toDomain();
        return repository.save(newStock) != null;
    }
}
