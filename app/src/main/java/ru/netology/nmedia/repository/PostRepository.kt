package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    
    fun getAllAsync(callback: Callback<List<Post>>)
    fun likeById(id: Long, likedByMe: Boolean, callback: Callback<Post>)
    fun saveAsync(post: Post, callback: Callback<Post>)
    fun removeById(id: Long, callback: CallbackUnit<Unit>)
    fun getById(id : Long, callback: Callback<Post>)

    interface Callback<T> {
        fun onSuccess(data: T)
        fun onError (e: Throwable)

    }
    interface CallbackUnit<T> {
        fun onError(e : Exception)
        fun onSuccess()
    }
}