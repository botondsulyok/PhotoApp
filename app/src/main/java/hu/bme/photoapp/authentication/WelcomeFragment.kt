package hu.bme.photoapp.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setBackgroundDrawableResource(R.drawable.welcome_background)
        
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            val action =
                WelcomeFragmentDirections.actionLoginChosen()
            findNavController().navigate(action)
        }

        btnRegister.setOnClickListener {
            val action =
                WelcomeFragmentDirections.actionRegisterChosen2()
            findNavController().navigate(action)
        }

        activity?.toolbar_main?.visibility = View.GONE
        activity?.drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        activity?.nav_view?.setCheckedItem(R.id.nav_home)

    }


}
