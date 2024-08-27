package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

interface PostRepository {


    fun get(): LiveData<Post>
    fun likeClicked()
    fun shareClicked()

}