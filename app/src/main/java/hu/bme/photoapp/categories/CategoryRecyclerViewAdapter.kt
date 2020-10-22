package hu.bme.photoapp.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.row_category.view.*

class CategoryRecyclerViewAdapter : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    private val categoryList = mutableListOf<Category>()

    var itemClickListener: CategoryItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]

        holder.category = category
        holder.tvCategory.text = category.name
    }

    override fun getItemCount(): Int = categoryList.size

    fun addAll(categories: List<Category>) {
        categoryList.clear()
        categoryList.addAll(categories)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.tv_category

        var category: Category? = null

        init {
            itemView.setOnClickListener {
                category?.let { category -> itemClickListener?.onItemClick(category) }
            }
        }
    }

    interface CategoryItemClickListener {
        fun onItemClick(category: Category)
    }

}