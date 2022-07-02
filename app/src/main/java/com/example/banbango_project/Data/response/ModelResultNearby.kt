package com.example.banbango_project.data.model.response


import com.example.banbango_project.data.model.nearby.ModelResults
import com.google.gson.annotations.SerializedName

class ModelResultNearby {
    @SerializedName("results")
    lateinit var modelResults: List<ModelResults>
}