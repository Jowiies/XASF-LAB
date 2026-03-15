package edu.upc.xasf.xasf_lab.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import edu.upc.xasf.xasf_lab.data.Municipi

class MunicipisRepository(context: Context) {

    private val requestQueue = VolleyProvider.getRequestQueue(context)

    fun obtenirMunicipis(
        url: String,
        onSuccess: (List<Municipi>) -> Unit,
        onError: (String) -> Unit
    ) {

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->

                try {
                    val municipis = mutableListOf<Municipi>()

                    val elements = response.getJSONArray("elements")

                    for (i in 0 until elements.length()) {

                        val item = elements.getJSONObject(i)

                        val nom = item.optString("municipi_nom", "Sense nom")
                        val ajuntament = item.optJSONObject("grup_ajuntament")
                        val codiPostal = ajuntament?.optString("codi_postal", "Sense codi") ?: "Sense codi"

                        municipis.add(
                            Municipi(
                                nom = nom,
                                codiPostal = codiPostal
                            )
                        )
                    }

                    onSuccess(municipis)

                } catch (e: Exception) {
                    onError("Error parsejant JSON: ${e.message}")
                }

            },
            { error ->
                onError("Error de xarxa: ${error.message ?: "desconegut"}")
            }
        )

        requestQueue.add(request)
    }
}