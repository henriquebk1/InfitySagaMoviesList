package com.henriquebecker.infinitysaga.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.io.IOException
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class CalendarDeserializer: JsonDeserializer<Calendar> {
    private val formatter: SimpleDateFormat = SimpleDateFormat("dd MMM YYYY", Locale.US)


    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Calendar {
        val dateAsString: String = json!!.asString

        return try {
            val date = formatter.parse(dateAsString)
            val calendar = Calendar.getInstance().apply {
                time = date!!
            }
            calendar
        } catch (e: Exception) {
            throw IOException(e)
        }
    }

}