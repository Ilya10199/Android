package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    
    fun getAllAsync(callback: NMediaCallback<List<Post>>)
    fun likeById(id: Long, callback: NMediaCallback<Post>)
    fun unlikeById(id : Long, callback: NMediaCallback<Post>)
    fun shareById(id: Long)
    fun saveAsync(post: Post, callback: NMediaCallback<Post>)
    fun removeById(id: Long, callback: NMediaCallback<Post>)
    fun getById(id : Long, callback: NMediaCallback<Post>)

    interface NMediaCallback<T> {
        fun onSuccess(data: T)
        fun onError (e: Exception)
    }
}