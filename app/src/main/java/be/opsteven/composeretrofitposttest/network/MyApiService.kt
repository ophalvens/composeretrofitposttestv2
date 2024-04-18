package be.opsteven.composeretrofitposttest.network

import be.opsteven.composeretrofitposttest.data.LoginResponse
import be.opsteven.composeretrofitposttest.data.ProductenResponse
import be.opsteven.composeretrofitposttest.data.User
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Vervang dit endpoint door het endpoint van jouw api.
 * In dit geval moet deze URI eindigen met een /
 */
private const val baseUrl = "https://stevenop.be/wm/api2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .build()

interface MyApiService {

    /**
     * Voor een get request is er niet veel speciaal aan te passen, hier volgen we
     * de cursus.
     * Let wel op : deze api is publiek, studenten kunnen producten verwijderen dus
     * het is mogelijk dat als je dit test, dat je een lege lijst krijgt. Is dat het
     * geval, voeg dan een paar producten toe via de test-app die je eerder al gebruikte.
     */
    @GET("products/")
    suspend fun getProducten() : ProductenResponse


    /**
     * Let bij de parameters vooral op wat je wil meegeven :
     * - in dit geval verwacht de php api in de body van de Request 2 velden : "name" en "password"
     * - daarom is er een User object meegegeven (met "name" en "password" als eigenschappen)
     */
    @POST("login/")
    suspend fun login(
        @Body body: User
    ) : LoginResponse
}

object MyApi {
    val retroFitService : MyApiService by lazy {
        retrofit.create(MyApiService::class.java)
    }
}

