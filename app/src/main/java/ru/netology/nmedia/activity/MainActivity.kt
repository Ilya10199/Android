package ru.netology.nmedia.activity


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.countFormat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                numberLikes.text = countFormat(post.likeCount)
                numberShare.text = countFormat(post.shareCount)
                numberVisibility.text = countFormat(post.visibilityCount)
                like.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )
                like.setOnClickListener {
                    viewModel.likeClicked()
                }
                share.setOnClickListener {
                    viewModel.shareClicked()
                }
            }
        }
    }
}
