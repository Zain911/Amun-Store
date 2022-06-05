package com.example.amunstore.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.amunstore.databinding.FragmentSignupBinding
import com.example.amunstore.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private lateinit var _binding: FragmentSignupBinding
    private val binding get() = _binding
    private lateinit var viewModel: AuthViewModel

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,32}" +  // 1 - 265
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,32}" +    // 0 - 64
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,32}" +   // 0 - 25
                ")+"
    )

    lateinit var userNameEdt: EditText
    lateinit var passWordEdt: EditText
    lateinit var passConfirmEdt: EditText
    lateinit var emailEdt: EditText
    lateinit var phoneEdt: EditText
    lateinit var registerBtn: Button
    lateinit var gotoLoginTxt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        viewModel = ViewModelProvider(
//            this, AuthModelFactory(
//                UserRepositoryImp(localSource = LocalSource(NewsDatabase.getInstance(requireContext()).userDao()))
//            )
//        ).get(AuthViewModel::class.java)

//        userNameEdt = binding.registerEditUserName
//        passWordEdt = binding.registerEditPassword
//        passConfirmEdt = binding.registerEditPasswordConfirm
//        emailEdt = binding.registerEditEmail
//        phoneEdt = binding.registerEditPhone
//        registerBtn = binding.registerButtonRegister
//        gotoLoginTxt = binding.registerTextLogin
//
//        gotoLoginTxt.setOnClickListener {
//            findNavController().navigate(ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment)) //for test purposal only
//        }
//
//        registerBtn.setOnClickListener {
//            registerBtn.visibility = View.INVISIBLE
//
//            if(userNameEdt.text.toString().trim().isNullOrEmpty() || passWordEdt.text.toString().trim().isNullOrEmpty() || passConfirmEdt.text.toString().trim().isNullOrEmpty()|| emailEdt.text.toString().trim().isNullOrEmpty()|| phoneEdt.text.toString().trim().isNullOrEmpty() )
//            {
//                tryAgain( getString(R.string.try_again))
//            }
//            else if ( passWordEdt.text.toString().trim() != passConfirmEdt.text.toString().trim())
//            {
//               tryAgain(getString(R.string.try_again))
//            }
//            else if (!isValidString(emailEdt.text.toString().trim()))
//            {
//             tryAgain( getString(R.string.wrong_email))
//            }
//            else{
//            viewModel.insertUser(User(userNameEdt.text.toString().trim().lowercase(), passWordEdt.text.toString().trim(),emailEdt.text.toString().trim().lowercase(),phoneEdt.text.toString().trim().lowercase()))
//
//                Toast.makeText(
//                    context,
//                    getString(R.string.registered_successfully),
//                    Toast.LENGTH_LONG
//                ).show()
//
//                findNavController().navigate(ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment)) //navigate auto after registeration
//            }}
//
        return root
    }
//
//    fun isValidString(str: String): Boolean{
//        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
//    }
//
//    private fun tryAgain(message:String) {
//        Toast.makeText(context, message , Toast.LENGTH_LONG).show()
//        registerBtn.visibility = View.VISIBLE
//    }
}