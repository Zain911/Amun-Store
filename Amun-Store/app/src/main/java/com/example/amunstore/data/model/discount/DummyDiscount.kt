package com.example.amunstore.data.model.discount

import com.example.amunstore.R
import com.example.example.PriceRules

fun getDisCount() =
    mutableListOf(

        PriceRules(12121, "type Value", value = "50", title = "discount 50", startsAt = "20-2-2020"),
        PriceRules(12121, "type Value", value = "10", title = "10 OFF ", startsAt = "20-2-2020"),
        PriceRules(12121, "type Value", value = "50", title = "Summer 50", startsAt = "20-2-2020"),
        PriceRules(12121, "type Value", value = "20", title = "Shose 20", startsAt = "20-2-2020")
    )