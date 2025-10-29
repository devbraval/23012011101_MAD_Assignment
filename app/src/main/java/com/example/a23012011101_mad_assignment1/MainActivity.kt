package com.example.a23012011101_mad_assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Initialize RecyclerView
        recyclerView = findViewById(R.id.medicineRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Adapter setup with delete action
        adapter = MedicineAdapter { medId ->
            AlarmHelper.cancelAlarms(this, medId)
            MedicineManager.remove(medId)
            adapter.submitList(MedicineManager.all().toList())
        }

        recyclerView.adapter = adapter
        adapter.submitList(MedicineManager.all().toList())

        // ✅ Floating Action Button to add new medicine
        val fab: FloatingActionButton = findViewById(R.id.fabAddMedicine)
        fab.setOnClickListener {
            val dialog = AddMedicineDialog()
            dialog.show(supportFragmentManager, "AddMedicineDialog")
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(MedicineManager.all().toList())
    }
}
