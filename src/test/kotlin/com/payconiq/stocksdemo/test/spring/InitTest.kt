package com.payconiq.stocksdemo.test.spring

import com.payconiq.stocksdemo.endpoint.StocksRestController
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class InitTest {
    @Autowired
    private lateinit var wac: WebApplicationContext

    @Test
    fun canScanComponents(){
        val ctx = AnnotationConfigWebApplicationContext()
        ctx.servletContext = wac.servletContext
        ctx.environment.setActiveProfiles("dev", "regular")
        ctx.scan("com.payconiq.stocksdemo")
        try {
            ctx.refresh()
        }catch(e:java.lang.Exception){
            e.printStackTrace()
            Assert.fail("Exception thrown")
        }
        ctx.getBean(StocksRestController::class.java)
    }
}