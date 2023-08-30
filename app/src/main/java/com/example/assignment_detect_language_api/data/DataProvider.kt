package com.example.assignment_detect_language_api.data

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.assignment_detect_language_api.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object DataProvider {

    lateinit var dao: RepoDao

    lateinit var value: String

    class Data {
        var detections: ArrayList<Detection>? = null
    }

    class Detection {
        var language: String? = null
        var isReliable = false
        var confidence = 0.0
    }

    class Root {
        var data: Data? = null
    }

    fun detectLanguage(context: Context, text: Any, viewModel: MainViewModel) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://ws.detectlanguage.com/0.2/detect"

        val requestBody = ""
        val stringReq: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->

                    val jsonString = response.toString()
                    val user = Gson().fromJson(jsonString, Root::class.java)
                    value = "language: ${user.data?.detections?.get(0)?.language}"
                    viewModel.getLanguage()
                    viewModel.addListItem(text.toString(), value)
                    CoroutineScope(Dispatchers.IO).launch {
                        dao.insert(Response(text.toString(), value))
                    }
                },
                Response.ErrorListener { error ->
                    value = "That didn't work!"
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers: MutableMap<String, String> = HashMap()
                    headers["Authorization"] = "Bearer 2db93ff4af241b91403e549d9aa73549"
                    return headers
                }
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    //Change with your post params
                    //params["q"] = "Hello+world"
                    params["q"] = text.toString()
                    return params
                }
            }
        queue.add(stringReq)
    }

}