package com.payconiq.stocksdemo.exception;

public class DuplicateStockException extends RuntimeException{

    /**
     * Constructs a <code>StockNotFoundException</code> with the specified message.
     *
     * @param msg the detail message.
     */
    public DuplicateStockException(String msg) {
        super(msg);
    }

}
