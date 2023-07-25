package com.raywenderlich.android.punchline

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeServiceTestUsingMockWebServer {
    @get:Rule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            // 1
            .baseUrl(mockWebServer.url("/"))
            // 2
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            // 3
            .addConverterFactory(GsonConverterFactory.create())
            // 4
            .build()
    }

//    Notice the use of triple quotes to create raw strings. By using raw strings for your
//    JSON Strings, you don’t need to worry about escaping characters such as the quotes
//    around the JSON properties.

    private val testJson = """{ "id": 1, "joke": "joke" }"""


    private val jokeService by lazy {
        retrofit.create(JokeService::class.java)
    }

    @Test
    fun getRandomJokeEmitJoke(){
        // 1
        mockWebServer.enqueue(
            // 2
            MockResponse()
                // 3
                .setBody(testJson)
                // 4
                .setResponseCode(200))
        // 1
        val testObserver = jokeService.getRandomJoke().test()
// 2
        testObserver.assertValue(Joke("1", "joke"))

    }
}