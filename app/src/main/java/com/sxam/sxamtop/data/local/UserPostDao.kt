package com.sxam.sxamtop.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPostDao {
    @Query("SELECT * FROM user_posts ORDER BY createdAt DESC")
    fun getAllUserPosts(): Flow<List<UserPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPost(post: UserPostEntity)

    @Delete
    suspend fun deleteUserPost(post: UserPostEntity)

    @Query("DELETE FROM user_posts")
    suspend fun clearAll()
}
