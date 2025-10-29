package com.example.a23012011101_mad_assignment1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object AlarmHelper {

    fun scheduleDailyAlarms(context: Context, med: com.example.a23012011101_mad_assignment1.models.Medicine) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        med.doseTimes.forEachIndexed { index, timeMillis ->
            val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
                putExtra("med_id", med.id)
                putExtra("med_name", med.tableName)
            }
            val req = (med.id * 100 + index).toInt()
            val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            val pi = PendingIntent.getBroadcast(context, req, intent, flags)

            // If time is past for today schedule for tomorrow
            val triggerAt = if (timeMillis <= System.currentTimeMillis()) timeMillis + AlarmManager.INTERVAL_DAY else timeMillis

            // setExactAndAllowWhileIdle is better for alarms that must fire roughly at time
            try {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAt, AlarmManager.INTERVAL_DAY, pi)
            } catch (e: Exception) {
                // fallback
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, pi)
            }
        }
    }

    fun cancelAlarms(context: Context, medId: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (i in 0..23) {
            val req = (medId * 100 + i).toInt()
            val intent = Intent(context, AlarmBroadcastReceiver::class.java)
            val pi = PendingIntent.getBroadcast(context, req, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
            if (pi != null) {
                try {
                    alarmManager.cancel(pi)
                    pi.cancel()
                } catch (_: Exception) { }
            }
        }
    }
}
