package com.example.notesbook_tabyldievawin_1_22.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import com.example.notesbook_tabyldievawin_1_22.models.Student;


@Dao
public interface StudentDao {
    @Query("SELECT * FROM students")
    List<Student> getAll();
    @Insert
    void insert(Student student);
    @Delete
    void delete(Student student);

    @Update
    void update(Student student);

    @Query("SELECT * FROM students ORDER BY name_surname ASC")
    List<Student> sortAll();
}
