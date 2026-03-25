package ru.fedotov.myfirstapp.adapter

import ru.fedotov.myfirstapp.dto.Post

interface OnPostInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onAvatarClick(post: Post) {}
    fun putExtra(extraText: Any, content: String)
}
