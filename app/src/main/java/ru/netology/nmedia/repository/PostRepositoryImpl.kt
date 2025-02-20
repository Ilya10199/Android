package ru.netology.nmedia.repository


import retrofit2.Call
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.net.ConnectException


class PostRepositoryImpl : PostRepository {


    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(
                call: retrofit2.Call<List<Post>>,
                response: retrofit2.Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }
        })

    }


    override fun likeById(id: Long, likedByMe: Boolean, callback: PostRepository.Callback<Post>) {
        if (!likedByMe) {
            PostsApi.retrofitService.likeById(id).enqueue(object :
                retrofit2.Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        callback.onSuccess(
                            response.body() ?: throw java.lang.RuntimeException("body is null")
                        )
                    } else {
                        callback.onError(RuntimeException("Bad code received"))
                    }
                }

                override fun onFailure(call: Call<Post>, e: Throwable) {
                    callback.onError(e)
                }
            })
        } else {
            PostsApi.retrofitService.dislikeById(id).enqueue(object :
                retrofit2.Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        callback.onSuccess(
                            response.body() ?: throw java.lang.RuntimeException("body is null")
                        )
                    } else {
                        callback.onError(RuntimeException("Bad code received"))
                    }
                }

                override fun onFailure(call: Call<Post>, e: Throwable) {
                    callback.onError(e)
                }
            })
        }

    }


    override fun getById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.getById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()}\n${response.code()}"))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }
        })
    }


    override fun saveAsync(post: Post, callback: PostRepository.Callback<Post>) {

        PostsApi.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()} \n${response.code()}"))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }
        })
    }


    override fun removeById(id: Long, callback: PostRepository.CallbackUnit<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(
                call: retrofit2.Call<Unit>,
                response: retrofit2.Response<Unit>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()}\n${response.code()}"))
                    return
                }
                callback.onSuccess()
            }

            override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }
        })
    }
}