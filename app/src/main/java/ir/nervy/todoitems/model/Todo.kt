package ir.nervy.todoitems.model

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("completed")
    val completed: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)