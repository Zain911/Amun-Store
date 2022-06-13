package com.example.amunstore.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.amunstore.MainActivity
import com.example.amunstore.databinding.FragmentLoginBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.example.amunstore.ui.auth.register.SignupBottomSheetDialogFragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton: LoginButton
    private val viewModel: AuthViewModel by viewModels()
    companion object {
        private var EMAIL = "email"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loginButton = binding.loginLogWithFacebookBtn
        loginButton.fragment = this@SigninFragment
        loginButton.setPermissions(listOf(EMAIL))
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.v("TAGGY", "onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.v("TAGGY", error.message.toString())
                }

                override fun onSuccess(result: LoginResult) {
                    Log.v("TAGGY", result.toString())
                }
            })

        LoginManager.getInstance()
            .retrieveLoginStatus(requireContext(), object : LoginStatusCallback {
                override fun onCompleted(accessToken: AccessToken) {
                    Log.v("login statue", accessToken.userId)
                    // User was previously logged in, can log them in directly here.
                    // If this callback is called, a popup notification appears that says
                    // "Logged in as <User Name>"
                }

                override fun onFailure() {
                    Log.v("login statue", "onFailure")
                    // No access token could be retrieved for the user
                }

                override fun onError(exception: Exception) {
                    Log.v("login statue", exception.message.toString())
                    // An error occurred
                }
            })
        binding.loginLoginWithEmailBtn.setOnClickListener { showBottomSheetDialogFragment() }

        binding.loginSignupTxt.setOnClickListener { showSignUpSheet() }

        return root
    }

    private fun showSignUpSheet() {
        val bottomSheetFragment = SignupBottomSheetDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = SigninBottomSheetDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }


    override fun onResume() {
        super.onResume()
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

       if (isLoggedIn){
           startMainActivity()
       }
    }



//    private fun setFacebookData(loginResult: LoginResult) {
//        val request = GraphRequest.newMeRequest(
//            loginResult.accessToken,
//            object : GraphRequest.GraphJSONObjectCallback() {
//                override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
    // // // Application code
//                    try {
//                        Log.i("Response", response.toString())
//                        val email = response!!.getJSONObject()!!.getString("email")
//                        val firstName = response.getJSONObject()!!.getString("first_name")
//                        val lastName = response.getJSONObject()!!.getString("last_name")
//                        var profileURL = ""
//                        if (Profile.getCurrentProfile() != null) {
//                            profileURL = ImageRequest.getProfilePictureUri(
//                                Profile.getCurrentProfile()?.id, 400, 400
//                            ).toString()
//                        }
//
    // //TODO put your code here
//                    } catch (e: JSONException) {
//                        Toast.makeText(
//                            context,
//                            R.string.com_facebook_internet_permission_error_message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            })
//        val parameters = Bundle()
//        parameters.putString("fields", "id,email,first_name,last_name")
//        request.parameters = parameters
//        request.executeAsync()
//    }


//     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//    }

    private fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}