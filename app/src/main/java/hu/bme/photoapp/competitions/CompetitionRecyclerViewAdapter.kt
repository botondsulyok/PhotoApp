package hu.bme.photoapp.competitions

import android.media.Image
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.row_competition.view.*

class CompetitionRecyclerViewAdapter : RecyclerView.Adapter<CompetitionRecyclerViewAdapter.ViewHolder>() {

    private val competitionList = mutableListOf<Competition>()

    var itemClickListener: CompetitionItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_competition, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val competition = competitionList[position]

        holder.competition = competition
        holder.tvCompetitionName.text = competition.name
        holder.tvCompetitionDate.text = competition.date
        if(competition.vip) {
            holder.ivVip.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = competitionList.size

    fun addAll(competitions: List<Competition>) {
        competitionList.clear()
        competitionList.addAll(competitions)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCompetitionName: TextView = itemView.tv_competitionName
        val tvCompetitionDate: TextView = itemView.tv_competitionDate
        val ivVip: ImageView = itemView.iv_vip
        var competition: Competition? = null

        init {
            itemView.setOnClickListener {
                competition?.let { competition -> itemClickListener?.onItemClick(competition) }
            }
        }
    }

    interface CompetitionItemClickListener {
        fun onItemClick(competition: Competition)
    }

}