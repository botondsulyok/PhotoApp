package hu.bme.photoapp.competitions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
import hu.bme.photoapp.categories.CategoryFragmentDirections
import hu.bme.photoapp.home.HomeFragment
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

        (activity as AppCompatActivity?)?.supportActionBar?.title = getString(R.string.title_competitions)

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
        if(!competition.currentVisibility) {
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle(getString(R.string.txt_somethingwentwrong))
                    .setMessage(getString(R.string.error_vipcompetition))
                    .setNeutralButton(getString(R.string.btn_ok), null)
                    .show()
            }
        }
        else {
            val action =
                CompetitionFragmentDirections.actionCompetitionSelected(competition._id, HomeFragment.COMPETITION_IMAGES, competition.name)
            findNavController().navigate(action)
        }
    }

}