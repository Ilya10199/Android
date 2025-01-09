package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.*

import ru.netology.nmedia.utils.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread


private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    likes = 0,
    published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    private val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.postValue(FeedModel(loading = true))
        repository.getAllAsync(
            object : PostRepository.NMediaCallback<List<Post>> {
                override fun onSuccess(data: List<Post>) {
                    _data.postValue(FeedModel(posts = data, empty = data.isEmpty()))
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }

            }
        )

    }

    fun save() {
        edited.value?.let {
            repository.saveAsync(it, object : PostRepository.NMediaCallback<Post> {
                override fun onSuccess(data: Post) {
                    _data.postValue(FeedModel())
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }

            }

            )
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        repository.likeById(id, object : PostRepository.NMediaCallback<Post> {
            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }

            override fun onSuccess(post: Post) {
                _data.postValue(
                    FeedModel(posts =
                    _data.value!!.posts.map {
                        if (post.id == it.id)
                        {post.copy(likedByMe = post.likedByMe, likes = post.likes) }
                        else {
                            it
                        }
                    })
                )
            }

        })
    }
    fun unlikeById(id : Long) {
        repository.unlikeById(id, object : PostRepository.NMediaCallback<Post> {
            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }

            override fun onSuccess(post: Post) {
                _data.postValue(
                    FeedModel(posts =
                    _data.value!!.posts.map {
                        if (post.id == it.id)
                        {post.copy(likedByMe = post.likedByMe, likes = post.likes) }
                        else {
                            it
                        }
                    })
                )
            }

        })
    }

    fun like(id : Long) {

        repository.getById(id, object : PostRepository.NMediaCallback<Post> {
            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }

            override fun onSuccess(post: Post) {
                if (post.likedByMe) unlikeById(id) else likeById(id)
            }
        })



    }



    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()

        repository.removeById(id, object : PostRepository.NMediaCallback<Unit> {
            override fun onSuccess(data: Unit) {
                try {
                    _data.postValue(
                        _data.value?.copy(posts = _data.value?.posts.orEmpty()
                            .filter { it.id != id })
                    )

                } catch (e: Exception) {
                    _data.postValue(_data.value?.copy(posts = old))
                }
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun shareById(id: Long) {

    }


}