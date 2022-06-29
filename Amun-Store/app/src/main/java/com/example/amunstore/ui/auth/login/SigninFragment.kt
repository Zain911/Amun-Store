package com.example.amunstore.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.amunstore.MainActivity
import com.example.amunstore.databinding.FragmentLoginBinding
import com.example.amunstore.domain.util.InternetConnectivity
import com.example.amunstore.ui.auth.AuthViewModel
import com.example.amunstore.ui.auth.register.SignupBottomSheetDialogFragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SigninFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton: LoginButton
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var connectionLiveData: InternetConnectivity

    companion object {
        private var EMAIL = "email"
    }


    lateinit var bottomSignUpFragment: SignupBottomSheetDialogFragment
    lateinit var bottomSignInFragment: SigninBottomSheetDialogFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loginButton = binding.loginLogWithFacebookBtn
        loginButton.setFragment(this@SigninFragment)
        loginButton.setPermissions(listOf(EMAIL))
        callbackManager = CallbackManager.Factory.create()

        bottomSignUpFragment = SignupBottomSheetDialogFragment()
        bottomSignInFragment = SigninBottomSheetDialogFragment()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                }

                override fun onError(error: FacebookException) {
                }

                override fun onSuccess(result: LoginResult) {
                    viewModel.getUserProfile()
                    startMainActivity()
                }
            })

        LoginManager.getInstance()
            .retrieveLoginStatus(requireContext(), object : LoginStatusCallback {
                override fun onCompleted(accessToken: AccessToken) {
                    viewModel.getUserProfile()
                    startMainActivity()
                    // User was previously logged in, can log them in directly here.
                    // If this callback is called, a popup notification appears that says
                    // "Logged in as <User Name>"
                }

                override fun onFailure() {
                    // No access token could be retrieved for the user
                }

                override fun onError(exception: Exception) {
                    // An error occurred
                }
            })

        binding.loginLoginWithEmailBtn.setOnClickListener {
            connectionLiveData = InternetConnectivity(context!!)
            connectionLiveData.observe(viewLifecycleOwner) {
                if (it)
                    showBottomSheetDialogFragment()
                else
                    Toast.makeText(context,"check Internet Connection",Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginSignupTxt.setOnClickListener {
            showSignUpSheet()
        }

        return root
    }

    private fun showSignUpSheet() {
        if (!bottomSignUpFragment.isVisible)
            bottomSignUpFragment.show(childFragmentManager, bottomSignUpFragment.tag)
    }

    private fun showBottomSheetDialogFragment() {
        if (!bottomSignInFragment.isVisible)
            bottomSignInFragment.show(childFragmentManager, bottomSignInFragment.tag)
    }

    override fun onResume() {
        super.onResume()
        LoginManager.getInstance().logOut()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}