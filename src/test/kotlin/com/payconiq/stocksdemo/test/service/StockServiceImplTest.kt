package com.payconiq.stocksdemo.test.service

import com.payconiq.stocksdemo.repo.StockRepository
import com.payconiq.stocksdemo.service.StockServiceImpl
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
class StockServiceImplTest {

    private lateinit var service: StockServiceImpl

    @Mock
    private lateinit var repository: StockRepository

    @Before
    fun setUp() {
        service = StockServiceImpl(repository)
    }

}