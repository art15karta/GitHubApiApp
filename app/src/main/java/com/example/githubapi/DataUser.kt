package com.example.githubapi

data class DataUser(

    val login: String,
//    val id: Int,
//    val node_id: String,
    val avatar_url: String,
//  val gravatar_id: String,
//    val url: String,
//    val html_url: String,
//    val followers_url: String,
//    val following_url: String,
//    val gists_url: String,
//    val starred_url: String,
//    val subscriptions_url: String,
//    val organizations_url: String,
//    val repos_url: String,
//    val events_url: String,
//    val received_events_url: String,
//    val type: String,
//    val site_admin: Boolean,
//    val company: String,
//    val blog: String,
//    val location: String,
//    val bio: String,
//    val hireable: String,
//    val twitter: String,
    val public_repos: Int = 1,
//    val public_gists: Int,
//    val followers: Int,
//    val following: Int,
//    val created_at: String,
//    val updated_at: String,
    val name: String,
    val email: String
)