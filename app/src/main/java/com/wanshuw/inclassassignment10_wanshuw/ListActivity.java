package com.wanshuw.inclassassignment10_wanshuw;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    TextView display;

    FirebaseDatabase database;
    DatabaseReference postsRef;
    DatabaseReference postRef;
    ArrayList<BlogPost> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance();
        postRef = database.getReference("post");
        postsRef = database.getReference("posts");

        posts = new ArrayList<>();

        display = (TextView)findViewById(R.id.display);

        postsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BlogPost post = dataSnapshot.getValue(BlogPost.class);
                posts.add(post);

                String results = "";
                for (BlogPost p:posts){
                    results += p + "\n";
                }
                display.setText(results);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(ListActivity.this,dataSnapshot.getValue(BlogPost.class)+" has changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(ListActivity.this,dataSnapshot.getValue(BlogPost.class)+" is removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        display.setVisibility(View.VISIBLE);
    }
}