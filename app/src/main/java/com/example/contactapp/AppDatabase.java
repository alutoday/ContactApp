package com.example.contactapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// Database class with version 2 (change this if necessary)
@Database(entities = Contact.class, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactDAO contactDAO();

    // Singleton instance
    private static AppDatabase instance;

    // Method to get the database instance
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "contacts")
                    // Uncomment one of the following lines based on your preference:

                    // Option 1: If you want to allow destructive migration (losing data during version changes)
                    .fallbackToDestructiveMigration()

                    // Option 2: If you want to add specific migration strategies (without data loss)
                    // .addMigrations(MIGRATION_1_2)  // Uncomment if you provide migrations
                    .build();
        }
        return instance;
    }

    // Example of a migration from version 1 to version 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Perform any necessary schema changes here
            // Example: Add a new column to the contacts table
            database.execSQL("ALTER TABLE contacts ADD COLUMN new_column TEXT");
            // Add more schema changes here as needed
        }
    };
}
