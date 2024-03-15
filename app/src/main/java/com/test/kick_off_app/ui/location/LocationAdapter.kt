package com.test.kick_off_app.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.R
import com.test.kick_off_app.data.LocationEnum
import com.test.kick_off_app.data.getLocation
import com.test.kick_off_app.databinding.RvLocationItemBinding

class LocationAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private var locations = arrayOf<Boolean>()

    inner class LocationViewHolder(private val binding: RvLocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.textLocation.text = getLocation(LocationEnum.values()[position])
            if (locations[position]){
                // 활성
                binding.buttonLocation.setBackgroundResource(R.drawable.border_location_selected_button)
            }
            else{
                // 비활성
                binding.buttonLocation.setBackgroundResource(R.drawable.border_location_button)
            }
            binding.root.setOnClickListener {
                // RecyclerView item click event
                onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(RvLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    fun setLocations(locationList: Array<Boolean>) {
        locations = locationList
    }
}