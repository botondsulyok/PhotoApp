package hu.bme.photoapp.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.row_image.view.*

class HomeRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

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

        var imageUrl = image.ownImage

        imageUrl = imageUrl.replace("\\", "/")
        Glide.with(context).load(ImageAPI.BASE_URL+imageUrl).into(holder.ivImage)

        holder.tvImageName.text = image.title

        holder.ivImage.transitionName = imageUrl
    }

    override fun getItemCount(): Int = imageList.size

    fun addAll(images: List<Image>) {
        imageList.clear()
        imageList.addAll(images)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvImageName: TextView = itemView.tv_ImageName

        val ivImage: ImageView = itemView.iv_Image

        var image: Image? = null

        init {
            itemView.setOnClickListener {
                image?.let { image -> itemClickListener?.onItemClick(image, ivImage) }
            }
        }
    }

    interface ImageItemClickListener {
        fun onItemClick(image: Image, imageView: ImageView)
    }

}