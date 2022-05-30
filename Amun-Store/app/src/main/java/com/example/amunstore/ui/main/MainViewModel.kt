package com.example.amunstore.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
   fun printLog(){
       Log.d("MainViewModel", "printLog: ")
   }
}