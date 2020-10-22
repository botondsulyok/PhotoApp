package hu.bme.photoapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.photoapp.R
import hu.bme.photoapp.home.Image
import kotlinx.android.synthetic.main.row_image.view.*

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    private val imageList = mutableListOf<Image>()

    var itemClickListener: ImageItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageList[position]

        holder.image = image
        holder.tvImageName.text = image.name
    }

    override fun getItemCount(): Int = imageList.size

    fun addAll(images: List<Image>) {
        imageList.clear()
        imageList.addAll(images)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvImageName: TextView = itemView.tv_ImageName

        var image: Image? = null

        init {
            itemView.setOnClickListener {
                image?.let { image -> itemClickListener?.onItemClick(image) }
            }
        }
    }

    interface ImageItemClickListener {
        fun onItemClick(image: Image)
    }

}