package com.example.postrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postrequest.databinding.RecyclerviewCellBinding

class RecyclerviewAdapter(val people : ArrayList<Person>) : RecyclerView.Adapter<RecyclerviewAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder(val binding: RecyclerviewCellBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            RecyclerviewCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val personObject = people[position]
        holder.binding.apply {
            nameTv.text = personObject.name
            locationTv.text = personObject.location
        }
    }

    override fun getItemCount(): Int = people.size
}