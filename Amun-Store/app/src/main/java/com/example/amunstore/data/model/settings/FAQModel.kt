package com.example.amunstore.data.model.settings

data class FAQModel(
    val question: String,
    val answer: String,
)

var listFQA = listOf(
    FAQModel("Is this app available on in Egypt?", "Yes, it's exclusive in Egypt."),
    FAQModel("What is limit to make order?", "there is no limit."),
    FAQModel("What are the payment methods?", "You can pay either online or on delivery."),
    FAQModel(
        "What is the return policy?",
        "Based on vendor policy you can check in product details."
    )
)