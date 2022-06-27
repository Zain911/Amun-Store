package com.example.amunstore.data.model.settings

data class FAQModel(
    val question: String,
    val answer: String,
)

var listFQA = listOf(
    FAQModel("Is this app available on in Egypt?", "Yes, it's exclusive in Egypt."),
    FAQModel("How much are the delivery fees?", "It's based on your location."),
    FAQModel("What are the payment methods?", "You can pay either online or on delivery."),
    FAQModel(
        "What is the return policy?",
        "You have 14 days to return the product but it needs to a brand-new, or 30 days if there is any defect in it."
    )
)