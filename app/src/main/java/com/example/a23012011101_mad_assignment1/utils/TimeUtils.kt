package com.example.a23012011101_mad_assignment1.utils

import java.util.Calendar

object TimeUtils {

    fun defaultDoseTimes(perDay: Int): List<Long> {
        val result = mutableListOf<Long>()
        if (perDay <= 0) return result

        if (perDay == 1) {
            result.add(todayAtHourMinute(12, 0))
            return result
        }

        if (perDay == 2) {
            result.add(todayAtHourMinute(12, 0))
            result.add(todayAtHourMinute(19, 30))
            return result
        }

        val startHour = 8
        val endHour = 20
        val span = endHour - startHour
        val step = span.toDouble() / (perDay - 1)

        for (i in 0 until perDay) {
            val hour = (startHour + (i * step)).toInt()
            result.add(todayAtHourMinute(hour, 0))
        }

        return result
    }

    private fun todayAtHourMinute(hour: Int, minute: Int): Long {
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hour)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.timeInMillis
    }
}
