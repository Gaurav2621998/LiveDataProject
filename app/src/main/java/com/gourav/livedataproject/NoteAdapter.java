package com.gourav.livedataproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {


    List<Note> notes=new ArrayList<>();


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_card,viewGroup,false);


        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {

        Note currentnote=notes.get(i);
        noteHolder.priority.setText(String.valueOf(currentnote.getPriority()));
        noteHolder.title.setText(currentnote.getTitle());
        noteHolder.desc.setText(currentnote.getDesc());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note>notes)
    {
        this.notes=notes;
        notifyDataSetChanged();
    }

    public Note getNote(int i)
    {
        return notes.get(i);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        private TextView title,desc,priority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            desc=(TextView)itemView.findViewById(R.id.desc);
            priority=(TextView)itemView.findViewById(R.id.priority);

        }
    }
}
