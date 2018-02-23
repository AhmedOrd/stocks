package com.payconiq.stocksdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JustContainerForValidtorToWork {

    @Valid
    @NotNull
    private StockViewModel viewModel;
}
