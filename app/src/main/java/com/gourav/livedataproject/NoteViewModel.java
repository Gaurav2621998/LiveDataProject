package com.gourav.livedataproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {


    private NoteRepository noteRepository;
    private LiveData<List<Note>> allnotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteRepository=new NoteRepository(application);
        allnotes=noteRepository.getAllNotes();
    }

    public void insert(Note note)
    {
        noteRepository.insert(note);
    }
    public void update(Note note)
    {
        noteRepository.update(note);
    }
    public void delete(Note note)
    {
        noteRepository.delete(note);
    }
    public void deleteall()
    {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllnotes(){
        return allnotes;
    }
}
