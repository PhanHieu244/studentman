package com.example.studentmanagement.database

import androidx.room.*
import com.example.studentmanagement.model.Student

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Student): Long

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>

    @Update
    suspend fun updateStudent(student: Student): Int

    @Delete
    suspend fun deleteStudent(student: Student): Int
}
