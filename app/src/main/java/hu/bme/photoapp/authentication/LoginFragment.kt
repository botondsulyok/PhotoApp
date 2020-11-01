package hu.bme.photoapp.authentication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.photoapp.R
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
            view?.let {
                hideKeyboard()
                var valid = true
                if (isEmailValid(etLoginEmail.text.toString()) != true) {
                    etLoginEmail.error = "Invalid email adress!"
                    valid = false
                }
                if (TextUtils.isEmpty(etLoginPassword.text)) {
                    etLoginPassword.error = "Cannot be empty!"
                    valid = false
                }
                valid=true //TODO
                if (valid) {
                    //TODO etLoginEmail.text.toString() UserData osztály szerverhez belépés kérése
                    val action =
                        LoginFragmentDirections.actionLoginSuccessful()
                    findNavController().navigate(action)
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}