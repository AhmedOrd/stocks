package com.payconiq.stocksdemo.test.mapper

import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import java.util.*

class MappedMessageSource(val messageMap:Map<String,String>) : MessageSource {
    override fun getMessage(code: String, args: Array<out Any>?, defaultMessage: String?, locale: Locale?): String {
        return messageMap[code]?:code
    }

    override fun getMessage(code: String, args: Array<out Any>?, locale: Locale?): String {
        return messageMap[code]?:code
    }

    override fun getMessage(resolvable: MessageSourceResolvable?, locale: Locale?): String {
        throw NotImplementedException()
    }
}