package com.example.oliverecipe.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.oliverecipe.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Update
    fun updateUser(user:User)

    //선택한 User를 지웁니다.
    @Delete
    fun deleteUser(user:User)

    //모두지웁니다.
    @Query("DELETE FROM user_table")
    fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>
}