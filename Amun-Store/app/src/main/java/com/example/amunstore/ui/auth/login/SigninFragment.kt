package com.example.amunstore.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentSigninBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.example.amunstore.ui.details.ProductDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    lateinit var loginBtn: Button
    lateinit var userNameEdt: EditText
    lateinit var passwordEdt: EditText
    lateinit var gotoRegisterTxt: TextView
    lateinit var rememberMeCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loginBtn = binding.signinLogin
        loginBtn.setOnClickListener {  setFragment() ;
            Log.v("F_U" ,"button pressed") }

//        userNameEdt = binding.loginEditUserName
//        passwordEdt = binding.loginEditPassword
//        loginBtn = binding.loginButtonLogin
//        gotoRegisterTxt = binding.loginTextCreateNewAccount
//        rememberMeCheckBox=binding.loginCheckerTextRememberMe
//
//        viewModel = ViewModelProvider(
//            this, AuthModelFactory(
//                UserRepositoryImp(LocalSource( NewsDatabase.getInstance(requireContext()).userDao()))
//            )
//        ).get(AuthViewModel::class.java)
//
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



    fun setFragment() {
        val productID  = 7782820643045
        // Create new fragment and transaction
        val newFragment: Fragment = ProductDetailsFragment(productID)
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment, and add the transaction to the back stack
        transaction.replace(R.id.nav_auth_fragment, newFragment)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }


}