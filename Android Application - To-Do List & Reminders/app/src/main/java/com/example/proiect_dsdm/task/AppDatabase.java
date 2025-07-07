package com.example.proiect_dsdm.task;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.example.proiect_dsdm.budget.category.Category;
import com.example.proiect_dsdm.budget.category.CategoryDao;
import com.example.proiect_dsdm.budget.transaction.Transaction;
import com.example.proiect_dsdm.budget.transaction.TransactionDao;

@Database(entities = {TaskEntity.class, Category.class, Transaction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TaskDao taskDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "task_database")
                    //.addMigrations(MIGRATION_3_4)
                    .fallbackToDestructiveMigration(true)
                    .build();
        }
        return instance;
    }
}


