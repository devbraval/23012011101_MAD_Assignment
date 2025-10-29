package com.example.a23012011101_mad_assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AfterCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_course)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val bpInput = findViewById<EditText>(R.id.bpInput)
        val oxygenInput = findViewById<EditText>(R.id.oxygenInput)
        val saveBtn = findViewById<Button>(R.id.saveAfterBtn)

        saveBtn.setOnClickListener {
            val w = weightInput.text.toString()
            val bp = bpInput.text.toString()
            val o = oxygenInput.text.toString()

            if (w.isBlank() && bp.isBlank() && o.isBlank()) {
                Toast.makeText(this, "Enter at least one value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            PostCourseStore.weight = w.ifBlank { null }
            PostCourseStore.bp = bp.ifBlank { null }
            PostCourseStore.oxygen = o.ifBlank { null }

            Toast.makeText(this, "Saved post-course values", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SummaryActivity::class.java))
            finish()
        }
    }
}
