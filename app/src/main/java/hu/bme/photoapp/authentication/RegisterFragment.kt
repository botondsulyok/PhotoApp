package hu.bme.photoapp.authentication

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
import hu.bme.photoapp.categories.CategoryViewModel
import hu.bme.photoapp.model.MainActivityViewModel
import hu.bme.photoapp.model.RegisterUser
import hu.bme.photoapp.model.User
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

        etRegisterEmail.requestFocus()

        btnRegisterRegister.setOnClickListener {
            var valid = true
            if (!isEmailValid(etRegisterEmail.text.toString())) {
                etRegisterEmail.error = getString(R.string.error_invalidemail)
                valid = false
            }

            if(TextUtils.isEmpty(etRegisterPassword.text)) {
                etRegisterPassword.error = getString(R.string.error_empty)
                valid = false
            }
            if(valid) {
                val registerUser = RegisterUser(
                    etRegisterEmail.text.toString(),
                    etRegisterPassword.text.toString()
                )
                val mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
                mainActivityViewModel.registerUser(
                    registerUser,
                    this::onSuccess,
                    this::showError)
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun onSuccess() {
        val action =
            RegisterFragmentDirections.actionRegisterSuccessful()
        findNavController().navigate(action)
        Toast.makeText(activity, getString(R.string.txt_registeredplslogin), Toast.LENGTH_LONG).show()
    }

    private fun showError(t: Throwable) {
        Toast.makeText(activity, t.message.toString(), Toast.LENGTH_LONG).show()
    }

}