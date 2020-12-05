package hu.bme.photoapp.authentication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import hu.bme.photoapp.R
import hu.bme.photoapp.model.MainActivityViewModel
import hu.bme.photoapp.model.RegisterUser
import hu.bme.photoapp.model.User
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etLoginEmail.requestFocus()

        btnLoginLogin.setOnClickListener {
            hideKeyboard()
            var valid = true
            if (!isEmailValid(etLoginEmail.text.toString())) {
                etLoginEmail.error = getString(R.string.error_invalidemail)
                valid = false
            }

            if (TextUtils.isEmpty(etLoginPassword.text)) {
                etLoginPassword.error = getString(R.string.error_empty)
                valid = false
            }

            if (valid) {
                val registerUser = RegisterUser(
                    etLoginEmail.text.toString(),
                    etLoginPassword.text.toString()
                )
                val mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
                mainActivityViewModel.loginUser(
                    registerUser,
                    this::onSuccess,
                    this::showError)
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun onSuccess(user: User) {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
        MainActivityViewModel.user = user
        val navigationView = activity?.findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        headerView.tvEmail.text = etLoginEmail.text
        val action =
            LoginFragmentDirections.actionLoginSuccessful()
        findNavController().navigate(action)
        Toast.makeText(activity, getString(R.string.txt_success), Toast.LENGTH_SHORT).show()
    }

    private fun showError(t: Throwable) {
        Toast.makeText(activity, t.message.toString(), Toast.LENGTH_LONG).show()
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