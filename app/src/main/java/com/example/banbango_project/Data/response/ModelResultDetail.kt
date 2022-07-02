package com.example.banbango_project.data.model.response


import com.example.banbango_project.data.model.details.ModelDetail
import com.google.gson.annotations.SerializedName

class ModelResultDetail {
    @SerializedName("result")
    lateinit var modelDetail: ModelDetail
}