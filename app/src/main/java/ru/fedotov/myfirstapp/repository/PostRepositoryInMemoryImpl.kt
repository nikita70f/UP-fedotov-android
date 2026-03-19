package ru.fedotov.myfirstapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.fedotov.myfirstapp.dto.Post

class PostRepositoryInMemoryImpl:PostRepository {

    // Теперь это список, а не один пост
    private var posts = listOf(
        Post(
            id = 1,
            author = "ШУБЫ!",
            content = "✨ Новая коллекция шуб уже в нашем магазине! ✨\n" +
                    "Представляем вам свежие модели, которые станут настоящим украшением вашего зимнего гардероба. В этом сезоне мы сделали ставку на:\n" +
                    "благородные оттенки (шоколад, графит, айвори);\n" +
                    "лаконичные силуэты;\n" +
                    "премиальное качество меха.\n" +
                    "Ждём вас в магазине",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 999,
            views = 5700
        ),
        Post(
            id = 2,
            author = "ШУБЫ!",
            content = "Скидки до 42%!!!.",
            published = "22 мая в 10:15",
            likedByMe = false,
            likes = 342,
            shares = 89,
            views = 2300
        ),
        Post(
            id = 3,
            author = "ШУБЫ!",
            content = "Новый привоз норочек!!!.",
            published = "23 мая в 09:42",
            likedByMe = true,
            likes = 1250,
            shares = 420,
            views = 8900
        ),
        Post(
            id = 4,
            author = "ШУБЫ!",
            content = "ЛИКВИДАЦИЯ!!!!!!!!! ТОЛЬКО СЕГОДНЯ!!!",
            published = "20 мая в 20:00",
            likedByMe = false,
            likes = 5678,
            shares = 1234,
            views = 45000
        )
    )

    private val _data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }
}


