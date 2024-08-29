package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {


    fun getAll(): LiveData<List<Post>>
    fun likeClicked(id: Long)
    fun shareClicked(id: Long)

}