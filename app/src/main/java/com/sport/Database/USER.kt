package com.sport.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class USER(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean = false,
    val profileImageUri: String?
)