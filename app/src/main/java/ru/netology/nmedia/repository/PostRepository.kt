package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    
    fun getAllAsync(callback: Callback<List<Post>>)
    fun likeById(id: Long, callback: Callback<Post>)
    fun unlikeById(id : Long, callback: Callback<Post>)
    fun shareById(id: Long)
    fun saveAsync(post: Post, callback: Callback<Post>)
    fun removeById(id: Long, callback: CallbackUnit<Unit>)
    fun getById(id : Long, callback: Callback<Post>)

    interface Callback<T> {
        fun onSuccess(data: T)
        fun onError (e: Exception)

    }
    interface CallbackUnit<T> {
        fun onError(e : Exception)
        fun onSuccess()
    }
}