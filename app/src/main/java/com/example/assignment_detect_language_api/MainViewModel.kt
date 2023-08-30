package com.example.assignment_detect_language_api

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment_detect_language_api.data.DataProvider
import com.example.assignment_detect_language_api.data.Response


class MainViewModel : ViewModel() {

    val languageLiveData: LiveData<String>
        get() = language

    private val language = MutableLiveData<String>()

    fun getLanguage() {
        language.value = DataProvider.value
    }

    fun setLanguage(test: String) {
        language.value = test
    }

    val textLiveData: LiveData<String>
        get() = text

    private val text = MutableLiveData<String>()

    fun setText(test: String) {
        text.value = test
    }

    val responseListLiveData: MutableLiveData<SnapshotStateList<Response>>
        get() = responseList

    private val responseList = MutableLiveData<SnapshotStateList<Response>>()

    var responseListItems = SnapshotStateList<Response>()

    fun setItems(test: List<Response?>?) {
        if (responseListItems.isEmpty())
            test?.forEach { item ->
                if (item != null) {
                    responseListItems.add(item)
                }
            }
    }

    fun setList() {
        responseList.value = responseListItems
    }

    fun addListItem(title: Any, description: String) {
        responseListItems.add(Response(title.toString(), description))
        responseList.value = responseListItems
    }

    /*fun test(){
        viewModelScope.launch(Dispatchers.IO) {
            val endPoint = URL("https://ws.detectlanguage.com/0.2/detect")
            val myConnection = endPoint.openConnection() as HttpsURLConnection
            myConnection.setRequestProperty("Authorization", "Bearer 2db93ff4af241b91403e549d9aa73549")
            val myData = "q=Hello+world"
            // Enable writing
            myConnection.doOutput = true
            // Write the data
            myConnection.outputStream.write(myData.toByteArray())
            //myConnection.requestMethod = "GET";
            val responseBody = myConnection.inputStream
            val responseBodyReader = InputStreamReader(responseBody, "UTF-8")
            val jsonReader = JsonReader(responseBodyReader)
            jsonReader.beginObject() // Start processing the JSON object

            while (jsonReader.hasNext()) { // Loop through all keys
                val key = jsonReader.nextName() // Fetch the next key
                if (key == "language") { // Check if desired key
                    // Fetch the value as a String
                    val value = jsonReader.nextString()
                    counter.postValue(value)
                    break // Break out of the loop
                } else {
                    jsonReader.skipValue() // Skip values of other keys
                }
            }
            jsonReader.close();
            myConnection.disconnect();
        }
    }*/

}