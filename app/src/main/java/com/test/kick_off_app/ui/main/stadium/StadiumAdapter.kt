package com.test.kick_off_app.ui.main.stadium

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.data.StadiumInfo
import com.test.kick_off_app.databinding.RvStadiumBinding

class StadiumAdapter(val onClick: (Int?)->(Unit)) : RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder>() {
    private var items = listOf<StadiumInfo>()

    inner class StadiumViewHolder(private val binding: RvStadiumBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(stadium: StadiumInfo){
            binding.nameStadium.setText(stadium.name)
            binding.addressStadium.setText(stadium.address)
            binding.priceStadium.setText(stadium.price.toString())
            binding.ratingStadium.setText(stadium.rating.toString())
            binding.root.setOnClickListener {
                // rv click event
                onClick(stadium.stadiumId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StadiumViewHolder {
        return StadiumViewHolder(RvStadiumBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: StadiumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(Stadiums: List<StadiumInfo>) {
        items = Stadiums
    }
}