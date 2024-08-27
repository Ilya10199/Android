package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likeCount = 0,
        shareCount = 0,
        visibilityCount = 10,
        likedByMe = false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun likeClicked() {
        val currentPost = checkNotNull(data.value) {
            "Not null value"
        }
        val modifiedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likeCount = currentPost.likeCount + if (!currentPost.likedByMe) 1 else -1
        )
        data.value = modifiedPost
    }

    override fun shareClicked() {
        val currentPost = checkNotNull(data.value) {
            "Not null value"
        }

        data.value = currentPost.copy(
            shareCount = currentPost.shareCount + 1
        )
    }
}