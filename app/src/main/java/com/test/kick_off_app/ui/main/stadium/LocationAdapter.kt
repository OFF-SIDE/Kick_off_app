package com.test.kick_off_app.ui.main.stadium

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.databinding.RvLocationBinding

class LocationAdapter(private val onClick: (String) -> Unit) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private var Locations = listOf<String>()

    inner class LocationViewHolder(private val binding: RvLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(Location: String) {
            binding.nameLocation.text = Location
            binding.root.setOnClickListener {
                // RecyclerView item click event
                onClick(Location)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(RvLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(Locations[position])
    }

    override fun getItemCount(): Int {
        return Locations.size
    }

    fun getSelectedlocations(){

    }

    fun setLocations(LocationList: List<String>) {
        Locations = LocationList
    }
}