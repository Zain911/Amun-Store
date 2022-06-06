package com.example.amunstore.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentLoginBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.example.amunstore.ui.favourites.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager

@AndroidEntryPoint
class SigninFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.loginLoginWithEmailBtn.setOnClickListener { showBottomSheetDialogFragment() }

        binding.loginSignupTxt.setOnClickListener {  findNavController().navigate(
            ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)
        ) }


//        gotoRegisterTxt.setOnClickListener {
//            findNavController().navigate(ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)) //for test purposal only
//        }
//
//        loginBtn.setOnClickListener {
//            loginBtn.visibility = View.INVISIBLE
//            check if there are strings or not
//            if (userNameEdt.text.toString().trim().isNullOrEmpty() || passwordEdt.text.toString()
//                    .trim().isNullOrEmpty()
//            ) {
//                tryAgain(getString(R.string.try_again))
//            } else
//                viewModel.getUserByEmail(email = userNameEdt.text.toString().trim())
//                    ?.observe(this@LoginFragment.viewLifecycleOwner, androidx.lifecycle.Observer
//                    {
//                        if (it != null) {
//
//                            if (it.isNullOrEmpty()) {
//                              tryAgain(getString(R.string.try_again))
//                            } else {
//                                if (it[0].password == passwordEdt.text.toString()) {
//                                    here password = list password so let him in the app
//                                    val intent = Intent(context, NewsActivity::class.java)
//                                   if(rememberMeCheckBox.isChecked){
//                                       SharedPreferenceUtil.setUserLoggedIn(requireContext())
//                                   }
//                                    startActivity(intent)
//                                } else {
//                                   tryAgain( getString(R.string.wrong_password))
//                                }
//                            }
//                        } else {
//                            tryAgain(getString(R.string.try_again))
//                        }
//
//                    })
//        }


        return root
    }

//    private fun tryAgain(message:String) {
//        Toast.makeText(context,message , Toast.LENGTH_LONG).show()
//        loginBtn.visibility = View.VISIBLE
//    }


    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = LoginBottomSheetDialogFragment(viewModel)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

}