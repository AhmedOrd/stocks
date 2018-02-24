package com.payconiq.stocksdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "Error Response model")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {

    @ApiModelProperty(example = "400", value = "Http status code")
    private int errorCode;

    @ApiModelProperty(example = "Cloud not verify stock price", value = "More information details")
    private String informationDetails;
}
