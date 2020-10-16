package hu.bme.photoapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLoginLogin.setOnClickListener {
            var valid = true
            if (isEmailValid(etLoginEmail.text.toString()) != true) {
                etLoginEmail.error = "Invalid email adress!"
                valid = false
            }
            if(TextUtils.isEmpty(etLoginPassword.text)) {
                etLoginPassword.error = "Cannot be empty!"
                valid = false
            }
            if(valid) {
                val action = LoginFragmentDirections.actionLoginSuccessful()
                findNavController().navigate(action)
                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}