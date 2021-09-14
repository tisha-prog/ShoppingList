package com.workshop.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText item;
    ListView list;
    ImageView img;
    //arraylist is used to store items of the shopping list
    List<String> l = new ArrayList<>();
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = findViewById(R.id.item_input);
        list = findViewById(R.id.list);
        img = findViewById(R.id.button);

        db = FirebaseDatabase.getInstance().getReference();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, l);

        db.child("GroceryList").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                l.clear();
                l.clear();
                for (DataSnapshot shot : snapshot.getChildren()) {
                    l.add(shot.getValue(String.class));
                }

                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }

    public void add(View view) {
        //code which works after clicking the button
        //DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("GroceryList")
                .child("item_" + l.size())
                .setValue(item.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        l.clear();
    }
}
