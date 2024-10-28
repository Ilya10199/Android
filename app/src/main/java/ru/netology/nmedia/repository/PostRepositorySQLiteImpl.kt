package ru.netology.nmedia.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
class PostRepositorySQLiteImpl(private val dao: PostDao) : PostRepository {
    private var posts = emptyList<Post>()
    private var data = MutableLiveData(posts)
    init {
        posts = dao.getAll()
        data.value = posts
    }
    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id : Long) {
        dao.likedById(id)
        posts = posts.map {
            if(it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likeCount = if (it.likedByMe) it.likeCount - 1 else it.likeCount + 1)
        }
        data.value = posts
    }
    override fun shareById(id : Long) {
        dao.shareById(id)
        posts = posts.map {
            if(it.id != id) it else it.copy(
                sharedByMe = !it.sharedByMe,
                shareCount = if (it.sharedByMe) it.shareCount - 1 else it.shareCount + 1)
        }
        data.value = posts
    }
    override fun watchById(id: Long) {
        dao.watchById(id)
        posts = posts.map {
            if(it.id != id) it else it.copy(
                visibilityCount = it.visibilityCount + 1)
        }
        data.value = posts
    }
    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }
    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}