package com.example.a23012011101_mad_assignment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a23012011101_mad_assignment1.model.Medicine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MedicineAdapter(
    private val onDeleteClick: (Long) -> Unit
) : ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(DiffCallback()) {

    class MedicineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvItemName)
        val tvDetails: TextView = view.findViewById(R.id.tvItemDetails)
        val tvNextTime: TextView = view.findViewById(R.id.tvNextRing)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = getItem(position)
        holder.tvName.text = medicine.tableName
        holder.tvDetails.text =
            "Type: ${medicine.type} | Per Day: ${medicine.perDay} | Remaining: ${medicine.remainingTablets}"

        val nextTime = medicine.nextRingTime()
        if (nextTime > 0) {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            holder.tvNextTime.text = "Next: ${sdf.format(Date(nextTime))}"
        } else {
            holder.tvNextTime.text = "Next: --"
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(medicine.id)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine) = oldItem == newItem
    }
}
