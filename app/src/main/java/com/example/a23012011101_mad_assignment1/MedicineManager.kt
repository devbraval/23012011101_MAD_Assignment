package com.example.a23012011101_mad_assignment1

import com.example.a23012011101_mad_assignment1.models.Medicine
import java.util.concurrent.atomic.AtomicLong

object MedicineManager {
    private val idGen = AtomicLong(1)
    private val medicines = mutableListOf<Medicine>()

    fun nextId(): Long = idGen.getAndIncrement()
    fun nextTheId(id: Long): String = "MED-$id"

    fun add(medicine: Medicine) {
        medicines.add(medicine)
    }

    fun all(): List<Medicine> = medicines.toList()

    fun get(id: Long): Medicine? = medicines.find { it.id == id }

    fun remove(id: Long) {
        medicines.removeAll { it.id == id }
    }

    fun clearAll() {
        medicines.clear()
    }
}
