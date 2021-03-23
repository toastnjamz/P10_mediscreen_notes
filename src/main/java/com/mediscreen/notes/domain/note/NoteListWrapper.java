package com.mediscreen.notes.domain.note;

import java.util.List;

public class NoteListWrapper {

    private List<Note> noteList;

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }
}
