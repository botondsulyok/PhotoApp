package hu.bme.photoapp.authentication

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
import hu.bme.photoapp.RegisterFragmentDirections
import kotlinx.android.synthetic.main.fragment_register.*



class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegisterRegister.setOnClickListener {
            var valid = true
            if (isEmailValid(etRegisterEmail.text.toString()) != true) {
                etRegisterEmail.error = "Invalid email adress!"
                valid = false
            }
            //TODO check passwords match
            /*if(etRegisterPassword.text?.equals(etRegisterPasswordAgain.text) == false) {
                etRegisterPassword.error = "Passwords don't match !"
                etRegisterPasswordAgain.error = "Passwords don't match !"
                valid = false
            }*/
            if(TextUtils.isEmpty(etRegisterPassword.text)) {
                etRegisterPassword.error = "Cannot be empty!"
                valid = false
            }
            if(valid) {
                val action =
                    RegisterFragmentDirections.actionRegisterSuccessful()
                findNavController().navigate(action)
                Toast.makeText(activity, "Registered, please login", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}