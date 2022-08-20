package com.example.mvvmtest.model.remote

import com.example.mvvmtest.model.CocktailDetails
import com.example.mvvmtest.model.CocktailSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
1.- Retrofit dependencies.
2.- Create Retrofit Interface (Service)
3.- In the service create the HTTP verbs
4.- Create the Retrofit object. (singleton)
 */
interface Service {
    @GET(ENDPOINT_SEARCH)
    fun queryCocktailByName(
        @Query(ARG_SEARCH) searchInput: String
    ) : Call<CocktailSearch>

    @GET(ENDPOINT_DETAIL)
    fun queryCocktailDetails(
        @Query(ARG_DETAIL) cocktailID: String
    ) : Call<CocktailDetails>
}