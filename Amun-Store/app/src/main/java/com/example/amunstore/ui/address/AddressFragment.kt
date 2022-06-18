package com.example.amunstore.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.amunstore.data.model.address.AddAddress
import com.example.amunstore.data.model.address.AddAddressRequestModel
import com.example.amunstore.databinding.FragmentAddAddressBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private var _binding: FragmentAddAddressBinding? = null
    private val binding get() = _binding!!


    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)


//FixMe for testing
        binding.firstNameEdt.setText("Eslam")
        binding.lastNameEdt.setText("Eslam")
        binding.phoneEdt.setText("0102114578")
        binding.cityEdt.setText("Eslam")
        binding.stateEdt.setText("Eslam")
        binding.postCodeEdt.setText("G1R 4P5")
        binding.addressEdt.setText("Eslam1")
        binding.address2Edt.setText("Eslam")
        binding.countryEdt.setText("Egypt")

        intiFocusListener()
        binding.saveAddressButton.setOnClickListener { submitForm() }
        return binding.root

    }

    private fun intiFocusListener() {
        firstNameFocusListener()
        secondNameFocusListener()
        phoneFocusListener()
        cityFocusListener()
        stateFocusListener()
        zipFocusListener()
        countryFocusListener()
    }

    private fun submitForm() {
        binding.firstNameInputText.helperText = validateFirstName()
        binding.lastNameInputText.helperText = validateLastName()
        binding.phoneTextInput.helperText = validatePhone()
        binding.cityTextInput.helperText = validateCity()
        binding.stateTextInput.helperText = validateState()
        binding.postCodeTextInput.helperText = validateZipCode()
        binding.countryTextInput.helperText = validateCountry()

        val validFirstName = binding.firstNameInputText.helperText == null
        val validLastName = binding.lastNameInputText.helperText == null
        val validPhone = binding.phoneTextInput.helperText == null
        val validCity = binding.cityTextInput.helperText == null
        val validState = binding.stateTextInput.helperText == null
        val validPostCode = binding.postCodeTextInput.helperText == null
        val validCountry = binding.countryTextInput.helperText == null

        viewLifecycleOwner.lifecycleScope.launch {
            if (validFirstName && validLastName && validPhone && validCity && validState && validPostCode && validCountry)
                viewModel.addNewAddress(
                    AddAddressRequestModel(
                        address = AddAddress(
                            firstName = binding.firstNameEdt.text.toString(),
                            lastName = binding.lastNameEdt.text.toString(),
                            city = binding.cityEdt.text.toString(),
                            zip = binding.postCodeEdt.text.toString(),
                            address1 = binding.addressEdt.text.toString(),
                            address2 = binding.address2Edt.text.toString(),
                            country = binding.countryEdt.text.toString(),
                            phone = binding.phoneEdt.text.toString()
                        )
                    )

                )
            else
                Toast.makeText(context, "Invalid Address", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firstNameFocusListener() {
        binding.firstNameEdt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.firstNameInputText.helperText = validateFirstName()
            }
        }
    }

    private fun validateFirstName(): String? {
        val firstName = binding.firstNameEdt.text

        if (firstName.length < 3)
            return "Too short Name"
        return null
    }

    private fun secondNameFocusListener() {
        binding.lastNameEdt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.lastNameInputText.helperText = validateLastName()
            }
        }
    }

    private fun validateLastName(): String? {
        val lastName = binding.firstNameEdt.text

        if (lastName.length < 3)
            return "Too short Name"
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneEdt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneTextInput.helperText = validatePhone()
            }
        }
    }

    private fun validatePhone(): String? {
        val phone = binding.phoneEdt.text
        if (!phone.matches(".*[0-9].*".toRegex())) {
            return "Must be all Digits"
        }
        if (phone.length != 10) {
            return "Must be 10 Digits"
        }
        return null
    }

    private fun cityFocusListener() {
        binding.cityEdt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.cityTextInput.helperText = validateCity()
            }
        }
    }

    private fun validateCity(): String? {
        val city = binding.cityEdt.text

        if (city.length < 4)
            return "Enter valid city name"
        return null
    }

    private fun stateFocusListener() {
        binding.stateEdt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.stateTextInput.helperText = validateState()
            }
        }
    }

    private fun validateState(): String? {
        val state = binding.stateEdt.text
        if (state.length < 4)
            return "Enter valid state name"
        return null
    }

    private fun zipFocusListener() {
        binding.postCodeEdt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.postCodeTextInput.helperText = validateZipCode()
            }
        }
    }

    private fun validateZipCode(): String? {
        val zipCode = binding.postCodeEdt.text
        if (zipCode.length < 4)
            return "Enter valid ZIP coce"
        return null
    }

    private fun countryFocusListener() {
        binding.countryEdt.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.countryTextInput.helperText = validateCountry()
            }
        }
    }

    private fun validateCountry(): String? {
        val addressOne = binding.countryEdt.text
        if (addressOne.length < 4)
            return "Enter valid name"
        return null
    }

}