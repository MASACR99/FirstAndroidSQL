package com.example.firstsql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CommentDatabase db;
    private CommentDao cmTb;
    private int selectedId = 0;
    private String selectedTitle = null;
    private Spinner spin;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),CommentDatabase.class, "database").allowMainThreadQueries().build();
        cmTb = db.commentDao();
        spin = findViewById(R.id.spinner);
        List<Comment> comms = cmTb.getAll();
        String[] titles = new String[comms.size()];
        if(!comms.isEmpty()){
            for(int i = 0; i < comms.size(); i++){
                titles[i] = comms.get(i).title;
            }
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, titles);
        updateSpinner();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTitle = spin.getSelectedItem().toString();
                selectedId = cmTb.getByTitle(selectedTitle).get(0).uid;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateSpinner(){
        spin.setAdapter(adapter);
    }

    private void updateSpinner(String item){
        String[] newAdapter = new String[adapter.getCount()+1];
        for(int i = 0; i < adapter.getCount(); i++){
            newAdapter[i] = adapter.getItem(i);
        }
        newAdapter[newAdapter.length-1] = item;
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, newAdapter);
        spin.setAdapter(adapter);
    }

    private void deleteSpinner(String item){
        String[] newAdapter = new String[adapter.getCount()-1];
        boolean foundRepeated = false;
        for(int i = 0; i < adapter.getCount(); i++){
            if(!adapter.getItem(i).equals(item)){
                if(!foundRepeated) {
                    newAdapter[i] = adapter.getItem(i);
                }else{
                    newAdapter[i-1] = adapter.getItem(i);
                }
            }else{
                foundRepeated = true;
            }
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, newAdapter);
        spin.setAdapter(adapter);
    }

    public void onViewButtonPress(View v){
        TextView contentViewer = findViewById(R.id.commentContentViewer);
        try{
            contentViewer.setText(cmTb.getById(selectedId).get(0).content);
            Log.d("oh yeah","See that comment???");
        }catch(Exception ex){
            Log.d("ohOH","Couldn't load the comment");
        }
    }

    public void onYeetButtonPress(View v){
        try{
            cmTb.delete(cmTb.getById(selectedId).get(0));
            deleteSpinner(selectedTitle);
            Log.d("oh yeah","YEETED the comment");
        }catch(Exception ex){
            Log.d("ohOH","Couldn't yeet the comment");
        }
    }

    public void onCreateButtonPress(View v){
        TextView title = findViewById(R.id.commentTitle);
        TextView content = findViewById(R.id.commentContent);
        try{
            if(!title.getText().toString().equals("") && !content.getText().toString().equals("")){
                cmTb.insertOne(new Comment(title.getText().toString(),content.getText().toString()));
                updateSpinner(title.getText().toString());
                title.setText("");
                content.setText("");
                Log.d("oh yeah","Put it in");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            Log.d("ohOH","Couldn't save the comment");
        }
    }
}