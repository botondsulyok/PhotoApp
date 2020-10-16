package hu.bme.photoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
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
            val action = WelcomeFragmentDirections.actionLoginChosen()
            findNavController().navigate(action)
        }

        btnRegister.setOnClickListener {
            val action = WelcomeFragmentDirections.actionRegisterChosen2()
            findNavController().navigate(action)
        }

    }


}
