package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditAndNewPostBinding


class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditAndNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.editText) {
            requestFocus()
            val text = intent.getStringExtra("post text input")
            setText(text)
        }
        binding.ok.setOnClickListener{
            val intent = Intent()
            if (binding.editText.text.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    this.getString(R.string.error_empty_content),
                    Toast.LENGTH_SHORT)
                    .show()
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.editText.text.toString()
                intent.putExtra("post text output", content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
        binding.cancelButton.setOnClickListener{
            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}
class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditAndNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editText.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.editText.text.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    this.getString(R.string.error_empty_content),
                    Toast.LENGTH_SHORT)
                    .show()
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.editText.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
        binding.cancelButton.setOnClickListener{
            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }

}