package com.test.kick_off_app.ui.main.stadium

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.kick_off_app.R
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.data.StadiumDetail
import com.test.kick_off_app.data.StadiumInfoList
import com.test.kick_off_app.databinding.RvStadiumInfoItemBinding
import com.test.kick_off_app.databinding.RvStadiumItemBinding

class StadiumInfoAdapter(val onClick: (String?)->(Unit)) : RecyclerView.Adapter<StadiumInfoAdapter.StadiumViewHolder>() {
    private var items = listOf<StadiumInfoList>()

    inner class StadiumViewHolder(private val binding: RvStadiumInfoItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(stadium: StadiumInfoList){
            binding.nameStadium.setText(stadium.subName)

            binding.root.setOnClickListener {
                // rv click event
                onClick(stadium.link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StadiumViewHolder {
        return StadiumViewHolder(RvStadiumInfoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: StadiumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(Stadiums: List<StadiumInfoList>) {
        items = Stadiums
    }
}