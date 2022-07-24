package com.example.unsplashtestapp.data.network

import com.example.unsplashtestapp.domain.entitites.photo.PhotoItem
import com.example.unsplashtestapp.domain.entitites.topic.TopicItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {
    @GET("/topics")
    suspend fun getTopics(@Query("page") page: Int = 1,
                          @Query("client_id") client_id: String = CLIENT_ID): List<TopicItem>

    @GET("/topics/{id}")
    suspend fun getTopicPhotos(@Path("id") id: Int,
                               @Query("page") page: Int = 1,
                               @Query("client_id") client_id: String = CLIENT_ID): List<PhotoItem>


    companion object {
        //to do hide key
        const val CLIENT_ID = "kbdLo5fTjxscbcPFhGoKZSve1Jry7YK69UhT-KVDKFY"
    }
}