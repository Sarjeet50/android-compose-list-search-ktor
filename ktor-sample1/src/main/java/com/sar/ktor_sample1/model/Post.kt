package com.sar.ktor_sample1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//{
//    "userId": 1,
//    "id": 3,
//    "title": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
//    "body": "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
//}

@Serializable
data class Post(@SerialName("userId") val userId : Long,
    @SerialName("id") val id : Long,
    @SerialName ("title") val title : String,
    @SerialName("body") val body : String)