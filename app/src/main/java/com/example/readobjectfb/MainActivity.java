package com.example.readobjectfb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvUser;
    private UserAdapter mUserAdapter;
    private ArrayList<User> mListUsers;
    EditText edtnhap;
    Button btnFind;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

       getListUserFromDatabase();

       btnFind.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String t;
               t= edtnhap.getText()+"";
               txt.setText(t);
               Find(t);
           }
       });

    }

    private void AnhXa(){
        txt=findViewById(R.id.textView2);
        rcvUser =findViewById(R.id.rcv_user);
        mListUsers =  new ArrayList<>();
        edtnhap = findViewById(R.id.editTextTextPersonName);
        btnFind= findViewById(R.id.button);

        mUserAdapter = new UserAdapter(mListUsers, this, new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                UpdateItem(user);
            }
        });
                rcvUser.setLayoutManager(new LinearLayoutManager(this));
        rcvUser.setAdapter(mUserAdapter);
    }

    private void getListUserFromDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
// cach1
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (mListUsers !=null){
//                    mListUsers.clear();
//                }
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    User user = dataSnapshot.getValue(User.class);
//                    mListUsers.add(user);
//                }
//                mUserAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Get list fail", Toast.LENGTH_SHORT).show();
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user =snapshot.getValue(User.class);
                if(user !=null){
                    mListUsers.add(user);
                    mUserAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user =snapshot.getValue(User.class);
                if(mListUsers == null ||user ==null || mListUsers.isEmpty()){
                    return;
                }
                for(int i=0; i<mListUsers.size();i++){
                    if(user.getId() == mListUsers.get(i).getId()){
                        mListUsers.set(i,user);
                    }
                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void Find(String keyword) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        //Query query= myRef.limitToFirst(2);
       // Query query= myRef.orderByChild("name").equalTo("Kien");
        //query.addChildEventListener(new ChildEventListener() {
        myRef.limitToFirst(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(user !=null){
                    if(user.getName().contains(keyword)){
                        mListUsers.add(user);
                    }
                    mUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void UpdateItem(User user) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_user);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtUpdateName = dialog.findViewById(R.id.edtName);
        Button btnCancel = dialog.findViewById(R.id.btnCan);
        Button btnUpdate = dialog.findViewById(R.id.btnUp);

        edtUpdateName.setText(user.getName());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");

                String newname = edtUpdateName.getText().toString().trim();
                user.setName(newname);
                myRef.child(String.valueOf(user.getId())).updateChildren(user.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(MainActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }



    }