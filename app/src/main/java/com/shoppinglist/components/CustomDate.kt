package com.shoppinglist.components

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN)


data class CustomDate(
    private var dateTime: String = format.format(Date(System.currentTimeMillis()))
) {
    constructor(timeAsLong: Long) : this() {
        dateTime = format.format(Date(timeAsLong))
    }

    val day: Int
        get() = dateTime.split(".")[0].toInt()

    val month: Int
        get() = dateTime.split(".")[1].toInt()

    val year: Int
        get() = dateTime.split(".")[2].toInt()

    val hour: Int
        get() = dateTime.split(" ")[1].split(":")[0].toInt()

    val minute: Int
        get() = dateTime.split(" ")[1].split(":")[1].toInt()

    val getDateTime: String
        get() = dateTime

    private fun setDateTime(dt: String) {
        dateTime = dt
    }

    private fun CharSequence.countDigits(): Int = count { it.isDigit() }

    private fun intToString(value: Int): String {
        return if (value.toString().countDigits() == 1) {
            "0$value"
        } else {
            value.toString()
        }
    }

    fun toLong(): Long {
        return dateTime.toLong()
    }

    fun setTimeToMidnight(): CustomDate {
        val tempCustomDate = CustomDate(dateTime)

        tempCustomDate.setDateTime(
            tempCustomDate.getDateTime.replace(
                intToString(tempCustomDate.hour),
                "00"
            )
        )
        tempCustomDate.setDateTime(
            tempCustomDate.getDateTime.replace(
                intToString(tempCustomDate.minute),
                "00"
            )
        )

        return tempCustomDate
    }

    fun changeTime(time: Long): CustomDate {
        val tempCustomDateTime = CustomDate(time)

        tempCustomDateTime.getDateTime.replace(
            intToString(this.hour),
            intToString(tempCustomDateTime.hour)
        )
        tempCustomDateTime.getDateTime.replace(
            intToString(this.minute),
            intToString(tempCustomDateTime.minute)
        )

        return tempCustomDateTime
    }
}
