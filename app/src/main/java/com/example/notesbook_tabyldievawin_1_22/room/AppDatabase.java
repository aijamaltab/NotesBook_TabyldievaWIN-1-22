package com.example.notesbook_tabyldievawin_1_22.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notesbook_tabyldievawin_1_22.models.Student;


@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();

}
