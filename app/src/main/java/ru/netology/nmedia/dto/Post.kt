package ru.netology.nmedia

import android.annotation.SuppressLint



data class Post(
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        var likeCount: Int = 999,
        var shareCount: Int = 11_999,
        var visibilityCount: Int = 1_200_000,
        var likedByMe: Boolean = false

)
@SuppressLint("DefaultLocale")
fun countFormat (count: Int): String {
        val formatCount = when {
                count in 1..999 -> {
                        String.format(count.toString())
                }
                count in 1000..9999 -> {
                        String.format("%.1fK", count / 1_000.0)
                }
                count in 10000..999999 -> {
                        String.format("%dK", count / 1_000)
                }
                count >= 1000000 -> {
                        String.format("%.1fM", count / 1_000_000.0)
                }

                else -> {
                        count.toString()
                }
        }
        return formatCount
}