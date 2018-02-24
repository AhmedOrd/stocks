package com.payconiq.stocksdemo.test.endpoint

import org.intellij.lang.annotations.Language
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ActiveProfiles("dev","regular")
@SpringBootTest
open class StockEndpointIT {
    companion object {
        @Language("JSON")
        private const val VALID_REQUEST_TENSOR = "{\"name\":\"TENSOR\", \"amount\":\"10.21\", \"dateTime\": \"2018-02-23T16:54:47.416\"}"
        @Language("JSON")
        private const val VALID_REQUEST_APPLE = "{\"name\":\"Apple\", \"amount\":\"12.21\", \"dateTime\": \"2018-02-23T16:54:47.416\"}"
        @Language("JSON")
        private const val VALID_REQUEST_AMAZON_UPDATE_PRICE = "{\"name\":\"Amazon\", \"amount\":\"15.21\", \"dateTime\": \"2018-02-23T16:54:47.416\"}"
        @Language("JSON")
        private const val INVALID_REQUEST_AMOUNT = "{\"name\":\"Google\", \"amount\":\"\", \"dateTime\": \"2018-02-23T16:54:47.416\"}"
        private const val INVALID_REQUEST_FORMAT = "adfasdfs"

    }

    @Autowired
    private lateinit var wac: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @Before
    fun setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }


    @Test
    @Throws(Exception::class)
    fun when_request_is_valid_than_successful_response() {

        val response = mockMvc.perform(MockMvcRequestBuilders //
                .post("/api/stocks") //
                .contentType(APPLICATION_JSON) //
                .content(VALID_REQUEST_TENSOR) //
        )

        response.andExpect(status().is2xxSuccessful)
    }

    @Test
    @Throws(Exception::class)
    fun when_amount_is_not_valid_than_badRequest() {

        val response = mockMvc.perform(MockMvcRequestBuilders //
                .post("/api/stocks") //
                .contentType(APPLICATION_JSON) //
                .content(INVALID_REQUEST_AMOUNT) //
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(jsonPath("$.informationDetails").value("viewModel.amount: Could not verify stock price, no price found."))
    }

    @Test
    @Throws(Exception::class)
    fun when_request_format_is_not_valid_than_badRequest() {

        val response = mockMvc.perform(MockMvcRequestBuilders //
                .post("/api/stocks") //
                .contentType(APPLICATION_JSON) //
                .content(INVALID_REQUEST_FORMAT) //
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(jsonPath("$.informationDetails").value("We could not verify the request, request format or field is not valid."))
    }

    @Test
    @Throws(Exception::class)
    fun when_stock_is_created_than_successful_response_with_stock_data() {


        val stockResponse = mockMvc.perform(MockMvcRequestBuilders //
                .get("/api/stocks/Microsoft") //
                .contentType(APPLICATION_JSON) //
        )

        stockResponse.andExpect(status().is2xxSuccessful)
        stockResponse.andExpect(jsonPath("$.name").value("Microsoft"))
        stockResponse.andExpect(jsonPath("$.amount").value("98.87"))

    }


    @Test
    @Throws(Exception::class)
    fun when_stock_is_created_than_duplicate_stock_is_not_allowed() {

        val duplicateResponse = mockMvc.perform(MockMvcRequestBuilders //
                .post("/api/stocks") //
                .contentType(APPLICATION_JSON) //
                .content(VALID_REQUEST_APPLE) //
        )

        duplicateResponse.andExpect(status().isBadRequest)
        duplicateResponse.andExpect(jsonPath("$.informationDetails").value("Cloud not Create stock, stock name already exist"))

    }

    @Test
    @Throws(Exception::class)
    fun when_stock_is_not_exist_than_not_found_response() {

        val stockResponse = mockMvc.perform(MockMvcRequestBuilders //
                .get("/api/stocks/sdfgd") //
                .contentType(APPLICATION_JSON) //
        )

        stockResponse.andExpect(status().isNotFound)
    }

    @Test
    @Throws(Exception::class)
    fun when_stock_price_is_updated_than_accepted_response() {

        val updateResponse = mockMvc.perform(MockMvcRequestBuilders //
                .put("/api/stocks") //
                .contentType(APPLICATION_JSON) //
                .content(VALID_REQUEST_AMAZON_UPDATE_PRICE) //
        )

        updateResponse.andExpect(status().is2xxSuccessful)

    }

    @Test
    @Throws(Exception::class)
    fun getAllStockst() {

        mockMvc.perform(MockMvcRequestBuilders //
                .get("/api/stocks") //
                .contentType(APPLICATION_JSON) //
        ).andExpect(status().is2xxSuccessful)

    }

}