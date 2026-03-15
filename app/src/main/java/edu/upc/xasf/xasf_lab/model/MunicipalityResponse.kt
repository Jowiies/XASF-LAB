package edu.upc.xasf.xasf_lab.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MunicipalityResponse(
    val elements: List<Municipality>
)

@Serializable
data class Municipality(
    @SerialName("municipi_nom") val name: String,
    @SerialName("grup_ajuntament") val info: AjuntamentInfo
)

@Serializable
data class AjuntamentInfo(
    @SerialName("codi_postal") val postalCode: String
)