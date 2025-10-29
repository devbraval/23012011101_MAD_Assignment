package com.example.a23012011101_mad_assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardActivity : AppCompatActivity() {

    // make adapter accessible from dialog refresh
    lateinit var adapter: MedicineAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerViewMedicines)
        val addBtn = findViewById<FloatingActionButton>(R.id.fabAddMedicine)

        adapter = MedicineAdapter { medId ->
            AlarmHelper.cancelAlarms(this, medId)
            MedicineManager.remove(medId)
            adapter.submitList(MedicineManager.all().toList())
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.submitList(MedicineManager.all().toList())

        addBtn.setOnClickListener {
            AddMedicineDialog().show(supportFragmentManager, "AddMedicineDialog")
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(MedicineManager.all().toList())
    }
}
