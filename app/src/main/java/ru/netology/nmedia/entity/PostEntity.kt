package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likeCount: Int = 0 ,
    val shareCount: Int = 0,
    val visibilityCount: Int = 0,
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val videoUrl: String = ""
){
    fun toDto() = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likes = likeCount,
        shareCount = shareCount,
        visibilityCount = visibilityCount,
        likedByMe = likedByMe,
        sharedByMe = sharedByMe,
        videoUrl = videoUrl
    )

    companion object {
        fun fromDto(post: Post) :PostEntity = with(post){
            PostEntity(
                id = id,
                author = author,
                content = content,
                published = published,
                likeCount = likes,
                shareCount = shareCount,
                visibilityCount = visibilityCount,
                likedByMe = likedByMe,
                sharedByMe = sharedByMe,
                videoUrl = videoUrl
            )
        }

    }
}

