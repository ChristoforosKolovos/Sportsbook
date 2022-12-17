package christoforos.api

import retrofit2.Response
import retrofit2.http.GET

interface SportsApi {

    @GET("sports")
    suspend fun getSports() : Response<List<SportsDto>>
}