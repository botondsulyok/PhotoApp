package hu.bme.photoapp.photo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.row_comment.view.*

class CommentRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {

    private val commentList = mutableListOf<Comment>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]
        holder.tvUser.text = comment.user.email
        holder.tvCommentText.text = comment.text
        holder.comment = comment
    }

    override fun getItemCount(): Int = commentList.size

    fun addAll(comment: List<Comment>) {
        commentList.clear()
        commentList.addAll(comment)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUser: TextView = itemView.tv_commentUser
        val tvCommentText: TextView = itemView.tv_commentText
        var comment: Comment? = null
    }

}