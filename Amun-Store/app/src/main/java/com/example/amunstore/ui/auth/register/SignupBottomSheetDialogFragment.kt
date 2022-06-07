package com.example.amunstore.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import com.example.amunstore.data.model.customer.Customer
import com.example.amunstore.databinding.DialogLoginWithEmailBinding
import com.example.amunstore.databinding.DialogSignupWithEmailBinding
import com.example.amunstore.ui.auth.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


class SignupBottomSheetDialogFragment(val viewModel: AuthViewModel) : BottomSheetDialogFragment() {
    private var _binding: DialogSignupWithEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var passwordConfirm:String
    private lateinit var first:String
    private lateinit var second:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSignupWithEmailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dialogSignupCloseImageView.setOnClickListener { FragmentManager.POP_BACK_STACK_INCLUSIVE } // to close dialog

//        binding.dialogSignupSignupBtn.setOnClickListener { viewModel.getUserByEmail(binding.dialogLoginEmailIdEdt.text.toString()) }





        viewModel.users.observe(viewLifecycleOwner){
            if (it){
                var intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish();
            }
            else {
                Toast.makeText(context,getString(R.string.login_failed), Toast.LENGTH_LONG).show()
            }
        }


        binding.dialogSignupSignupBtn.setOnClickListener {
            email = binding.dialogSignupEmailIdEdt.text.toString().trim().lowercase()
            passwordConfirm =binding.dialogSignupPasswordConfirmEdtIn.text.toString().trim()
            password =binding.dialogSignupPasswordEdtIn.text.toString().trim()

            first =binding.signupFirstNAmeEdt.text.toString().trim()
            second = binding.signupLastNameEdt.text.toString().trim()

        if (!viewModel.InputsIsEmpty(email, pass1 = password , pass2 = passwordConfirm)){
            Toast.makeText(context,getString(R.string.fields_are_empty),Toast.LENGTH_LONG).show()
        }
            else if (!viewModel.isPasswordConfirmed(pass2 = password , pass1 = passwordConfirm))
            {  Toast.makeText(context,getString(R.string.two_passwords_are_not_similar),Toast.LENGTH_LONG).show()}

            else if ( !viewModel.checkIfEmailIsGood(email = email))
            {
                Toast.makeText(context,getString(R.string.email_format_is_not_accepted),Toast.LENGTH_LONG).show()
            }
            else{

            var customer = Customer(email = email, first_name = first, last_name = second , note = password)
//                var customer = Customer(email = email , password = password, password_confirmation = passwordConfirm , first_name = first, last_name = second)
            viewModel.createUser(customer)
            showBottomSheetDialogFragment()
            }



        }








        return root
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = SignupSuccesfulBottomSheetDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        this@SignupBottomSheetDialogFragment.dismiss()
    }
}