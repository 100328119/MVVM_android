package com.example.mvvm_example;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private Note_DAO note_dao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDB db = NoteDB.getInstance(application);
        note_dao = db.note_dao();
        allNotes = note_dao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(note_dao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(note_dao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(note_dao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(note_dao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    //Insert database in the background
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private Note_DAO note_dao;

        private InsertNoteAsyncTask(Note_DAO note_dao){
            this.note_dao = note_dao;
        }


        @Override
        protected Void doInBackground(Note... notes){
            note_dao.insertNote(notes[0]);
            return null;
        }
    }

    //update database in the background
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private Note_DAO note_dao;

        private UpdateNoteAsyncTask(Note_DAO note_dao){
            this.note_dao = note_dao;
        }


        @Override
        protected Void doInBackground(Note... notes){
            note_dao.updateNote(notes[0]);
            return null;
        }
    }

    //Delete database in the background
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private Note_DAO note_dao;

        private DeleteNoteAsyncTask(Note_DAO note_dao){
            this.note_dao = note_dao;
        }


        @Override
        protected Void doInBackground(Note... notes){
            note_dao.deleteNote(notes[0]);
            return null;
        }
    }

    //Delete all note in  background
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void>{
        private Note_DAO note_dao;

        private DeleteAllNoteAsyncTask(Note_DAO note_dao){
            this.note_dao = note_dao;
        }


        @Override
        protected Void doInBackground(Void... voids){
            note_dao.deleteAllNotes();
            return null;
        }
    }
}
