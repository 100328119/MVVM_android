package com.example.mvvm_example;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1,exportSchema = false)
public abstract class NoteDB extends RoomDatabase {

    private static NoteDB instance;

    public abstract Note_DAO note_dao();

    public static synchronized NoteDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDB.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super .onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{
        private Note_DAO note_dao;

        private PopulateDBAsyncTask (NoteDB db){
            note_dao = db.note_dao();
        }
        @Override
        protected Void doInBackground(Void... voids){
            note_dao.insertNote(new Note("title 1", "description 1", 1));
            note_dao.insertNote(new Note("title 2", "description 2", 2));
            note_dao.insertNote(new Note("title 3", "description 3", 3));
            return null;
        }
    }

}
