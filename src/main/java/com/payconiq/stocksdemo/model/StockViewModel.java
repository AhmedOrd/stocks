package com.payconiq.stocksdemo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.payconiq.stocksdemo.domain.Stock;

import com.payconiq.stocksdemo.validtor.constraint.DateConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "Stock model information")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class StockViewModel implements Serializable {

    private static final long serialVersionUID = 3297423984732894L;

    @ApiModelProperty(example = "Google", value = "The stock name")
    @NotNull(message = "name.invalid.error.reason")
    @Size(min = 1, message = "name.invalid.error.reason")
    private String name;

    @ApiModelProperty(example = "100.90", value = "The Stock price")
    @NotNull(message = "amount.invalid.error.reason")
    private BigDecimal amount;

    @ApiModelProperty(example = "2018-02-23T16:54:47.416", value = "The Time")
    @NotNull(message = "date.invalid.error.reason")
    @DateConstraint
    private LocalDateTime dateTime;

    public static StockViewModel toModel(Stock stock) {
        StockViewModel stockViewModel = new StockViewModel();
        stockViewModel.name = stock.getName();
        stockViewModel.amount = stock.getPrice();
        stockViewModel.dateTime = stock.getDateTime();
        return stockViewModel;
    }

    public Stock toDomain() {
        Stock stock = new Stock();
        stock.setName(name);
        stock.setPrice(amount);
        stock.setDateTime(dateTime);
        return stock;
    }

}
