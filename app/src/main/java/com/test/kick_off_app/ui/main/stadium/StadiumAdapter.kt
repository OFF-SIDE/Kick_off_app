package com.test.kick_off_app.ui.main.stadium

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.databinding.RvStadiumItemBinding

class StadiumAdapter(val context: Context, val onClick: (Int?)->(Unit)) : RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder>() {
    private var items = listOf<Stadium>()

    inner class StadiumViewHolder(private val binding: RvStadiumItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(stadium: Stadium){
            binding.nameStadium.setText(stadium.name)
            binding.addressStadium.setText(stadium.location)
            binding.priceStadium.setText(stadium.price)
            binding.ratingStadium.setText(stadium.totalRating.toString())
            /*
            val filename = stadium.name + "downloadImage.jpg"
            CoroutineScope(Dispatchers.Main).launch {
                downloadImage(context, stadium.image!!, filename)
                val myImageFile = File(context.getFilesDir(), filename)
                if(myImageFile.exists()){
                    val myBitmap = BitmapFactory.decodeFile(myImageFile.absolutePath)
                    binding.imageView.setImageBitmap(myBitmap)
                }
            }
            */
            /*
            Glide.with(binding.imageView)
                //.load(stadium.image)
                .load(stadium.image)
                .error(R.drawable.baseline_error_24)
                .into(binding.imageView)

             */
            binding.root.setOnClickListener {
                // rv click event
                onClick(stadium.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StadiumViewHolder {
        return StadiumViewHolder(RvStadiumItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: StadiumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(Stadiums: List<Stadium>) {
        items = Stadiums
    }
}