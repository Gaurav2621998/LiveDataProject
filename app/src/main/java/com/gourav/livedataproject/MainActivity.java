package com.gourav.livedataproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    RecyclerView recyclerView;
    FloatingActionButton fab;

     private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,1 );

            }
        });


//        Intent contentIntent = new Intent(this, AddNoteActivity.class);
//        PendingIntent pendingContentIntent = PendingIntent.getActivity(this, 0,
//                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//
//        NotificationManager mNotifyManager = (NotificationManager)
//                getSystemService(NOTIFICATION_SERVICE);
//
//
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel =
//                    new NotificationChannel("1", "Mascot Notification",
//                            NotificationManager.IMPORTANCE_DEFAULT);
//        }
//
//
//
//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_save_black_24dp);
//
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
//                .setSmallIcon(R.drawable.ic_add)
//                .setContentTitle("LiveDataNotification")
//                .setContentText("Live Data Value is Changed ")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setSmallIcon(R.drawable.ic_add)
//                .setContentIntent(pendingContentIntent)
//                .setDefaults(NotificationCompat.DEFAULT_ALL);
//
//
//
//        mBuilder.setContentIntent(pendingContentIntent);
//
//        //Action Button
//        mBuilder.addAction(R.drawable.ic_add, "Get Directions", pendingContentIntent);
//
//        mNotifyManager.notify(1,mBuilder.build());



//        NotificationCompat notif = new NotificationCompat.Builder(this, "1")
//                .setContentTitle("New photo from ")
//                .setContentText("hello")
//                .setSmallIcon(R.drawable.ic_add)
//                .setLargeIcon(bitmap)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(bitmap)
//                        .setBigContentTitle("Large Notification Title"))
//                .build();













        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final NoteAdapter noteAdapter=new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);


        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllnotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //Recycler Update

                noteAdapter.setNotes(notes);
                Toast.makeText(MainActivity.this, "Changed", Toast.LENGTH_SHORT ).show();


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK)
        {
            String title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String desc=data.getStringExtra(AddNoteActivity.EXTRA_DESC);
            int priority=data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

            Note note=new Note(title,desc,priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.deleteAllNotes:
                noteViewModel.deleteall();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.notify:
                startActivity(new Intent(this,NotificationBuilder.class));
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }


}
