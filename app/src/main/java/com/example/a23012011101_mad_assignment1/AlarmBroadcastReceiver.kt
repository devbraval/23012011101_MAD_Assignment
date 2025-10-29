package com.example.a23012011101_mad_assignment1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.example.a23012011101_mad_assignment1.model.Medicine

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val medId = intent.getLongExtra("med_id", -1L)
        val medName = intent.getStringExtra("med_name") ?: "Medicine"

        Toast.makeText(context, "Time to take: $medName", Toast.LENGTH_LONG).show()

        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("med_id", medId)
            putExtra("med_name", medName)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }

        val med: Medicine? = MedicineManager.get(medId)
        med?.let {
            if (it.remainingTablets > 0) it.remainingTablets -= 1
            if (it.remainingTablets <= 0) {
                AlarmHelper.cancelAlarms(context, it.id)
                val after = Intent(context, AfterCourseActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(after)
            }
        }
    }
}
