package com.github.calo001.fondo.model

import java.io.Serializable

data class Photo (
    val categories: List<Any>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: LinksPhoto,
    val sponsored: Boolean,
    val sponsored_by: Any,
    val sponsored_impressions_id: Any,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
) : Serializable

data class User(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val links: Links,
    val location: String,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImage,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String,
    val updated_at: String,
    val username: String
) : Serializable

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : Serializable

data class Links(
    val followers: String,
    val following: String,
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
) : Serializable

data class LinksPhoto(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
) : Serializable

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Serializable

data class Result (val results: List<Photo>, val total: Int)