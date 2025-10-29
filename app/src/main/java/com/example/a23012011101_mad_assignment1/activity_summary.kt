package com.example.a23012011101_mad_assignment1

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.a23012011101_mad_assignment1.models.Medicine
import java.text.SimpleDateFormat
import java.util.*

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val container = findViewById<LinearLayout>(R.id.summaryContainer)
        container.removeAllViews()

        val medicines: List<Medicine> = MedicineManager.all()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // ✅ If no medicines are available
        if (medicines.isEmpty()) {
            addText(container, "No medicines added.")
        } else {
            // ✅ Display each medicine details
            medicines.forEach { medicine ->
                val doseTimes = if (medicine.doseTimes.isNotEmpty()) {
                    medicine.doseTimes.joinToString(", ") { timeFormat.format(Date(it)) }
                } else {
                    "Not Set"
                }

                val text = """
                    ID: ${medicine.theId}
                    Name: ${medicine.tableName}
                    Type: ${medicine.type}
                    Total Tablets: ${medicine.totalTablets}
                    Remaining: ${medicine.remainingTablets}
                    Doses/Day: ${medicine.perDay}
                    Times: $doseTimes
                    Empty Stomach: ${if (medicine.emptyStomach) "Yes" else "No"}
                """.trimIndent()

                addText(container, text)
            }
        }

        // ✅ Display Post-course Vitals
        if (PostCourseStore.weight != null || PostCourseStore.bp != null || PostCourseStore.oxygen != null) {
            addHeader(container, "Post-course Vitals")
            val vitals = """
                Weight: ${PostCourseStore.weight ?: "-"}
                BP: ${PostCourseStore.bp ?: "-"}
                Oxygen: ${PostCourseStore.oxygen ?: "-"}
            """.trimIndent()
            addText(container, vitals)
        }
    }

    // 🔹 Helper function to add normal text
    private fun addText(container: LinearLayout, text: String) {
        val tv = TextView(this)
        tv.text = text
        tv.setPadding(0, 12, 0, 12)
        container.addView(tv)
    }

    // 🔹 Helper function to add section headers
    private fun addHeader(container: LinearLayout, title: String) {
        val tv = TextView(this)
        tv.text = "\n$title"
        tv.textSize = 18f
        tv.setPadding(0, 12, 0, 8)
        container.addView(tv)
    }
}
