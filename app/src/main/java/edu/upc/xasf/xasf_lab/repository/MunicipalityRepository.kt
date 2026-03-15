package edu.upc.xasf.xasf_lab.repository
import edu.upc.xasf.xasf_lab.model.Municipality
import edu.upc.xasf.xasf_lab.model.MunicipalityResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class MunicipalityRepository {
    private val jsonParser = Json { ignoreUnknownKeys = true }

    suspend fun fetchMunicipalities(urlString: String): List<Municipality> {
        return withContext(Dispatchers.IO) {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            try {
                val content = connection.inputStream.bufferedReader().use { it.readText() }
                jsonParser.decodeFromString<MunicipalityResponse>(content).elements
            } finally {
                connection.disconnect()
            }
        }
    }
}