package com.example.amunstore.data.model.settings

data class DeveloperModel(
    val name: String,
    val github: String,
    val LinkedIn: String,
)

var develperList = listOf(
    DeveloperModel("walid Zain",
        "https://github.com/Zain911",
        "https://www.linkedin.com/in/walid-zain/"),
    DeveloperModel("Eslam Esmael ",
        "https://github.com/EslamEsmael", "https://www.linkedin.com/in/eslamesmael/"
    ),
    DeveloperModel("Abdalla badr",
        "https://github.com/AbdallaBadreldin",
        "https://www.linkedin.com/in/abdalla-badreldin/")
)