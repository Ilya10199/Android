package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.*
import ru.netology.nmedia.utils.SingleLiveEvent


private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    likes = 0,
    published = "",
    authorAvatar = ""
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

    private val _singleError = SingleLiveEvent<Unit>()
    val singleError: LiveData<Unit>
        get() = _singleError

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(
            object : PostRepository.Callback<List<Post>> {
                override fun onSuccess(data: List<Post>) {
                    _data.postValue(FeedModel(posts = data, empty = data.isEmpty()))
                }

                override fun onError(e: Throwable) {
                    _data.postValue(FeedModel(error = true))
                }

            }
        )

    }

    fun save() {
        edited.value?.let {
            repository.saveAsync(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(data: Post) {
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Throwable) {
                    _singleError.postValue(Unit)

                }

            }

            )
        }
        edited.value = empty
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


    fun likeById(id: Long, likedByMe: Boolean) {
        val old = _data.value?.posts.orEmpty()
        repository.likeById(id, likedByMe, object : PostRepository.Callback<Post> {
            override fun onSuccess(value: Post) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map {
                            if (it.id != id) it else value
                        }
                    )
                )
            }
            override fun onError(e: Throwable) {
                _singleError.postValue(Unit)
                _data.postValue(_data.value?.copy(posts = old))
            }
        })
    }


    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()

        repository.removeById(id, object : PostRepository.CallbackUnit<Unit> {
            override fun onSuccess() {

                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id })
                )


            }

            override fun onError(e: Exception) {
                _singleError.postValue(Unit)
                _data.postValue(_data.value?.copy(posts = old))
            }
        })
    }

    fun shareById(id: Long) {

    }


}