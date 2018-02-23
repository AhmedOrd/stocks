package com.payconiq.stocksdemo.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.payconiq.stocksdemo.model.ErrorResponse;
import com.payconiq.stocksdemo.model.JustContainerForValidtorToWork;
import com.payconiq.stocksdemo.model.StockViewModel;
import com.payconiq.stocksdemo.service.StockService;
import com.payconiq.stocksdemo.validtor.ExceptionBasedValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Stocks Service", //
        description = "Stocks Service", //
        tags = { "Stocks Service" } //
)
@Slf4j
@NoArgsConstructor // required constructor
@RequestMapping(path = "/api/stocks")
@RestController
public class StocksRestController {

    private StockService stockService;

    private ExceptionBasedValidator validator;

    @Autowired
    public StocksRestController(StockService stockService, ExceptionBasedValidator validator) {
        this.stockService = stockService;
        this.validator = validator;
    }


    @ApiOperation(value = "POST", notes = "Create new Stock", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(response = ErrorResponse.class, code = 404, message = ""),
            @ApiResponse( code = 201, message = "")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStock(@ApiParam(name = "model", value = "Requst body that contains all need value to create new stock", required = true)
                                                  @RequestBody(required = false) StockViewModel model) {
        log.debug("Handel request stock name : {}", model.getName());


        validator.assertObjectsAreValid(JustContainerForValidtorToWork.builder() //if you put @valid annotation on param, it wouldn't work, so i create this funny name class to let work,
                                                                      .viewModel(model)
                                                                      .build());

        boolean isCreated = stockService.createNewStock(model);

        return isCreated ? new ResponseEntity<>(HttpStatus.CREATED) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @ApiOperation(value = "PUT", notes = "Update Stock price", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(response = ErrorResponse.class, code = 404, message = ""),
            @ApiResponse( code = 201, message = "")
    })
    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStockPrice(@ApiParam(name = "model", value = "Requst body that contains all need value to create new stock", required = true)
                                                     @RequestBody(required = false) @Valid StockViewModel model) {

        validator.assertObjectsAreValid(model);

        boolean isUpdated = stockService.updateStockPrice(model);

        return isUpdated ? ResponseEntity.accepted().build() : new ResponseEntity<>(ErrorResponse.builder()
                                                                                                 .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                                                                 .informationDetails("Could not update price, technical error accrued")
                                                                                                 .build(),
                                                                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ApiOperation(value = "GET", notes = "Retrieve all Stocks", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(response = List.class, code = 200, message = "")} //
            )
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @ApiOperation(value = "GET", notes = "Get Stock by name", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(response = List.class, code = 200, message = ""),
            @ApiResponse(response = List.class, code = 200, message = "")
    })
    @GetMapping(path = "/{name}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStock(@ApiParam(name = "name", value = "Stock name that you would like to retrieve", required = true)
                                                   @PathVariable(value = "name", required = false)
                                                   @NotNull(message = "name.invalid.error.reason")
                                                   @Size(min = 1, message = "name.invalid.error.reason") String stockName) {

        validator.assertObjectsAreValid(stockName);

        StockViewModel stock = stockService.getStock(stockName);
        log.debug("Stock has been found successfully, {}", stockName);
        return ResponseEntity.ok(stock);
    }

}
