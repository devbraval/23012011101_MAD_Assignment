package com.example.a23012011101_mad_assignment1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import com.example.a23012011101_mad_assignment1.model.Medicine

object AlarmHelper {

    fun scheduleDailyAlarms(context: Context, med: Medicine) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(
                    context,
                    " Please allow exact alarm permission in settings.",
                    Toast.LENGTH_LONG
                ).show()


                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
                return
            }
        }

        med.doseTimes.forEachIndexed { index, timeMillis ->
            val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
                putExtra("med_id", med.id)
                putExtra("med_name", med.tableName)
            }

            val req = (med.id * 100 + index).toInt()
            val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            val pi = PendingIntent.getBroadcast(context, req, intent, flags)


            val triggerAt = if (timeMillis <= System.currentTimeMillis())
                timeMillis + AlarmManager.INTERVAL_DAY
            else
                timeMillis

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi)
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pi)
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
                Toast.makeText(context, "Alarm permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun cancelAlarms(context: Context, medId: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (i in 0..23) {
            val req = (medId * 100 + i).toInt()
            val intent = Intent(context, AlarmBroadcastReceiver::class.java)
            val pi = PendingIntent.getBroadcast(
                context,
                req,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            pi?.let {
                try {
                    alarmManager.cancel(it)
                    it.cancel()
                } catch (_: Exception) { }
            }
        }
    }
}
