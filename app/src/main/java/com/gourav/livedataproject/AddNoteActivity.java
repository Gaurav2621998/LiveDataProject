package com.gourav.livedataproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    TextView title,desc;
    NumberPicker numberPicker;


    public static final String EXTRA_TITLE="EXTRA_TITLE";
    public static final String EXTRA_DESC="EXTRA_DESC";
    public static final String EXTRA_PRIORITY="EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        numberPicker=(NumberPicker)findViewById(R.id.number_picker);
        title=(TextView)findViewById(R.id.titletext);
        desc=(TextView)findViewById(R.id.desctext);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Add Note");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_add,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save :
                savenote();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void savenote() {

        String t=title.getText().toString();
        String des=desc.getText().toString();
        int p=numberPicker.getValue();

        if(t.trim().isEmpty() || des.trim().isEmpty())
        {
            Toast.makeText(this, "Please Enter Title And Desc", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent=new Intent();
        intent.putExtra(EXTRA_TITLE,t);
        intent.putExtra(EXTRA_DESC,des);
        intent.putExtra(EXTRA_PRIORITY,p);
        setResult(RESULT_OK,intent);
        finish();



    }
}
