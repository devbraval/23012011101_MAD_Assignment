package com.example.a23012011101_mad_assignment1

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.example.a23012011101_mad_assignment1.model.Medicine
import com.example.a23012011101_mad_assignment1.utils.TimeUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddMedicineDialog : BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_add_medicine, null)
        dialog.setContentView(view)

        val etName = view.findViewById<EditText>(R.id.etMedicineName)
        val etTotal = view.findViewById<EditText>(R.id.etTotalTablets)
        val etPerDay = view.findViewById<EditText>(R.id.etPerDay)
        val timePicker = view.findViewById<TimePicker>(R.id.timePickerMedicine)
        val btnSave = view.findViewById<Button>(R.id.btnSaveMedicine)
        val cbEmpty = view.findViewById<CheckBox>(R.id.cbEmptyStomach)

        timePicker.setIs24HourView(true)

        if (etPerDay.text.isNullOrBlank()) etPerDay.setText("2")

        btnSave.setOnClickListener {
            val name = etName.text.toString().ifBlank { "Medicine ${System.currentTimeMillis()}" }
            val total = etTotal.text.toString().toIntOrNull() ?: 0
            val perDay = etPerDay.text.toString().toIntOrNull() ?: 2
            val empty = cbEmpty.isChecked

            if (total <= 0) {
                Toast.makeText(requireContext(), "Enter medicine quantity!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hour = timePicker.hour
            val minute = timePicker.minute

            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }

            val times = TimeUtils.defaultDoseTimes(perDay).toMutableList()
            if (times.isNotEmpty()) {
                times[0] = cal.timeInMillis
            } else {
                times.add(cal.timeInMillis)
            }

            val id = MedicineManager.nextId()
            val med = Medicine(
                id = id,
                theId = MedicineManager.nextTheId(id),
                tableName = name,
                type = "Tablet",
                totalTablets = total,
                perDay = perDay,
                remainingTablets = total,
                emptyStomach = empty,
                doseTimes = times
            )

            MedicineManager.add(med)
            AlarmHelper.scheduleDailyAlarms(requireContext(), med)

            Toast.makeText(requireContext(), "Medicine saved & Alarm set!", Toast.LENGTH_SHORT).show()

            (activity as? DashboardActivity)?.let {
                it.runOnUiThread {
                    it.adapter.submitList(MedicineManager.all().toList())
                }
            }

            dismiss()
        }

        return dialog
    }
}
