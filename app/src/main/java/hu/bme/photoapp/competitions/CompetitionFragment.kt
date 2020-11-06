package hu.bme.photoapp.competitions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.fragment_competitions.*


class CompetitionFragment : Fragment(), CompetitionRecyclerViewAdapter.CompetitionItemClickListener {

    private lateinit var recyclerViewAdapter: CompetitionRecyclerViewAdapter

    private lateinit var competitionViewModel: CompetitionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_competitions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        competitionViewModel = ViewModelProvider(this).get(CompetitionViewModel::class.java)
        competitionViewModel.allCompetitions.observe(viewLifecycleOwner) { competitions ->
            recyclerViewAdapter.addAll(competitions)
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = CompetitionRecyclerViewAdapter()
        recyclerViewAdapter.itemClickListener = this
        rvCompetitionList.adapter = recyclerViewAdapter
    }

    override fun onItemClick(competition: Competition) {
        //TODO megfelelő kategórialista megnyitása
    }


}