package com.example.readobjectfb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ReadWriteObject extends AppCompatActivity {
    Button btnPush, btnGet, btnDel,btnUp;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readwrite_object);


        btnPush=findViewById(R.id.button5);
        btnGet=findViewById(R.id.button6);
        btnDel=findViewById(R.id.button7);
        btnUp=findViewById(R.id.button8);
        tv=findViewById(R.id.textView);

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPushData();
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGetData();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelData();
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateDataC32();
            }
        });
    }

    //Update cach 1
    private void onClickUpdateDataC1(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info");

        User user=new User(2,"Duy", new Job(2,"job2"));
        user.setAddress("TPHCM");
        myRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ReadWriteObject.this, "Update success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Update cach 2
    private void onClickUpdateDataC2(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("User_info");
//        myRef.child("name").setValue("Test12", new DatabaseReference.CompletionListener() {

        DatabaseReference myRef = database.getReference("User_info/name");
        myRef.setValue("Test12", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ReadWriteObject.this, "Update success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Update cach 3
    private void onClickUpdateDataC3(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info");

        Map<String, Object> result = new HashMap<>();
        result.put("address", "SaiGon");
        result.put("name", "abc");
        result.put("job/name", "job3");

        myRef.updateChildren(result, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ReadWriteObject.this, "Update success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Update cach 3.2
    private void onClickUpdateDataC32(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info");

        User user = new User("ss","Hai Phong");
        myRef.updateChildren(user.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ReadWriteObject.this, "Update success", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onClickDelData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test");
        myRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ReadWriteObject.this, "Del Success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onClickPushData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info");

        User user=new User(1,"AN", new Job(1,"job1"));
        user.setAddress("Hà Nội");
        myRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ReadWriteObject.this, "Push success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onClickGetData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                tv.setText(user.toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
}
