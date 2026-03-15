package edu.upc.xasf.xasf_lab.network
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
object VolleyProvider {
    private var requestQueue: RequestQueue? = null

    fun getRequestQueue(context: Context): RequestQueue {
        return requestQueue ?: synchronized(this) {
            requestQueue ?: Volley.newRequestQueue(context.applicationContext).also {
                requestQueue = it
            }
        }
    }
}