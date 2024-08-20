package ru.netology.nmedia


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import ru.netology.nmedia.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            numberLikes.text = countFormat(post.likeCount)
            numberShare.text = countFormat(post.shareCount)
            numberVisibility.text = countFormat(post.visibilityCount)

            if (post.likedByMe) {
                like?.setImageResource(R.drawable.baseline_favorite_24)
            }


            like?.setOnClickListener {

                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )
                if (post.likedByMe) post.likeCount++ else post.likeCount--
                numberLikes?.text = countFormat(post.likeCount)
            }
            share?.setOnClickListener {
                post.shareCount++
                numberShare?.text = countFormat(post.shareCount)
            }
        }
    }
}
