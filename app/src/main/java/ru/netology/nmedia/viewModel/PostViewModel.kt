package ru.netology.nmedia.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

//private val empty = Post(
//    id = 0,
//    content = "content",
//    author = "me",
//    likedByMe = false,
//   published = "now"
//)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()
    val edited = MutableLiveData<Post?>(null)

    fun save(content: String) {
        if (content.isBlank()) return

        val post = edited.value?.copy(
            content = content
        ) ?: Post(
            id = 0,
            author = "Me",
            content = content,
            published = "Now"
        )
        repository.save(post)
        edited.value = null
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun onCloseEditClicked() {
        edited.value = null

    }


    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

}