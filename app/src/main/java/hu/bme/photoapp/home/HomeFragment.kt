package hu.bme.photoapp.home

import android.os.Bundle
import android.view.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.window?.setBackgroundDrawableResource(R.color.colorBackground)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            //TODO
        }
        //activity?.tvEmail?.text = HomeFragmentArgs.fromBundle(requireArguments()).email
        //activity?.toolbar_main?.visibility = View.VISIBLE
        activity?.toolbar_main?.visibility = View.VISIBLE
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_toolbar, menu)
    }

    //TODO
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }




}