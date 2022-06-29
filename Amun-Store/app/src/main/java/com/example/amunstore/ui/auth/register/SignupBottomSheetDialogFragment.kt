package com.example.amunstore.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import com.example.amunstore.databinding.DialogSignupWithEmailBinding
import com.example.amunstore.domain.util.InternetConnectivity
import com.example.amunstore.ui.auth.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogSignupWithEmailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passwordConfirm: String
    private lateinit var first: String
    private lateinit var second: String
    private lateinit var connectionLiveData: InternetConnectivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSignupWithEmailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.dialogSignupCloseImageView.setOnClickListener { this@SignupBottomSheetDialogFragment.dismiss() } // to close dialog

        binding.dialogSignupSignupBtn.setOnClickListener {
            email = binding.dialogSignupEmailIdEdt.text.toString().trim().lowercase()
            passwordConfirm = binding.dialogSignupPasswordConfirmEdtIn.text.toString().trim()
            password = binding.dialogSignupPasswordEdtIn.text.toString().trim()

            first = binding.signupFirstNAmeEdt.text.toString().trim()
            second = binding.signupLastNameEdt.text.toString().trim()

            if (!viewModel.inputsIsEmpty(email, pass1 = password, pass2 = passwordConfirm)) {
                Toast.makeText(context, getString(R.string.fields_are_empty), Toast.LENGTH_LONG)
                    .show()
            } else if (!viewModel.validatePasswordConfirmation(
                    pass2 = password,
                    pass1 = passwordConfirm
                )
            ) {
                Toast.makeText(
                    context,
                    getString(R.string.two_passwords_are_not_similar),
                    Toast.LENGTH_LONG
                ).show()
            } else if (!viewModel.validateEmail(email = email)) {
                Toast.makeText(
                    context,
                    getString(R.string.email_format_is_not_accepted),
                    Toast.LENGTH_LONG
                ).show()
            } else if (!viewModel.validatePassword(password)) {

                Toast.makeText(
                    context,
                    getString(R.string.password_cannot_be_less_than_5_chars),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                connectionLiveData = InternetConnectivity(context!!)
                connectionLiveData.observe(viewLifecycleOwner) {
                    if (it) {
                        changeButtonsActivation(false)
                        viewModel.createUser(
                            email = email,
                            first_name = first,
                            second_name = second,
                            password = password,
                            password_confirmation = passwordConfirm
                        )

                    } else
                        Toast.makeText(context, "check Internet Connection", Toast.LENGTH_SHORT)
                            .show()
                }

            }
        }

        viewModel.isRegistered.observe(viewLifecycleOwner) {
            if (it) {
                showBottomSheetDialogFragment()
            } else {
                Toast.makeText(context, getString(R.string.signup_failed), Toast.LENGTH_LONG).show()
            }
            changeButtonsActivation(true)
        }

        viewModel.users.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(context, getString(R.string.login_failed), Toast.LENGTH_LONG).show()
            }
            changeButtonsActivation(true)
        }

        return root
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = SignupSuccesfulBottomSheetDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    private fun changeButtonsActivation(activeStatue: Boolean) {
        binding.dialogSignupSignupBtn.isEnabled = activeStatue
        binding.dialogSignupCloseImageView.isEnabled = activeStatue
    }
}