package com.example.alpha1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RTDBActivity extends AppCompatActivity {
    private EditText editText;
    private Button saveButton;
    private Button loadButton;
    private TextView displayText;
    private DatabaseReference databaseReference;
    private DatabaseReference historyReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtdbactivity);

        editText = findViewById(R.id.editText);
        displayText = findViewById(R.id.displayText);

        databaseReference = FirebaseDatabase.getInstance().getReference("textData");
        historyReference = FirebaseDatabase.getInstance().getReference("textHistory");

    }

    public void next(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void save(View view) {
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            databaseReference.push().setValue(text);
            historyReference.push().setValue(text);
            editText.getText().clear();
        }
    }

    public void load(View view) {
        databaseReference.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String text = snapshot.getValue(String.class);
                    if (text != null) {
                        displayText.append(text + "\n");
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                displayText.setText("Failed to load data");
            }
        });
    }
}