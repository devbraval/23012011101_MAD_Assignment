package com.example.a23012011101_mad_assignment1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a23012011101_mad_assignment1.models.Medicine

class AddMedicineActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var totalTabletsInput: EditText
    private lateinit var perDayInput: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)

        // Bind views
        nameInput = findViewById(R.id.medicineName)
        totalTabletsInput = findViewById(R.id.totalTablets)
        perDayInput = findViewById(R.id.dosesPerDay)
        saveButton = findViewById(R.id.saveMedicineBtn)

        saveButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val totalTablets = totalTabletsInput.text.toString().trim().toIntOrNull()
            val perDay = perDayInput.text.toString().trim().toIntOrNull()

            if (name.isEmpty() || totalTablets == null || perDay == null) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Generate unique ID and theId using MedicineManager
            val newId = MedicineManager.nextId()
            val newTheId = MedicineManager.nextTheId(newId)

            val medicine = Medicine(
                id = newId,
                theId = newTheId,
                tableName = name,
                type = "Tablet", // You can modify or use spinner later
                totalTablets = totalTablets,
                perDay = perDay,
                remainingTablets = totalTablets,
                emptyStomach = false,
                doseTimes = listOf() // Empty for now
            )

            // Add medicine to manager
            MedicineManager.add(medicine)

            Toast.makeText(this, "Medicine Saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
