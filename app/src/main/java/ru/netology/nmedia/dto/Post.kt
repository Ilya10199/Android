package ru.netology.nmedia

import android.annotation.SuppressLint
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow


data class Post(
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        var likeCount: Int = 1099,
        var shareCount: Int = 11_999,
        var visibilityCount: Int = 1_200_000,
        var likedByMe: Boolean = false

)

fun countFormat(likesCount: Int): String {
        return when (likesCount) {
                in 1000..1099 ->"${roundNoDecimal(likesCount.toDouble()/1_000.0)}K"
                in 1100..9_999 ->"${roundWithDecimal(likesCount.toDouble()/1_000.0)}K"
                in 10_000..999_999 ->"${roundNoDecimal(likesCount.toDouble()/1_000.0)}K"
                in 1_000_000..1_099_999 ->"${roundNoDecimal(likesCount.toDouble()/1_000_000.0)}M"
                in 1_100_000..Int.MAX_VALUE ->"${roundWithDecimal(likesCount.toDouble()/1_000_000.0)}M"

                else-> likesCount.toString()
        }
}


fun roundWithDecimal(number: Double): String {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.DOWN
        return df.format(number)
}
fun roundNoDecimal(number: Double): String {
        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number)
}