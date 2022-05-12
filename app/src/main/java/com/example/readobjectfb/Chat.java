package com.example.readobjectfb;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private EditText edtMessage;
    private Button btnSend;
    private RecyclerView rcvMessage;
    private MessageAdapter messageAdapter;
    private List<Message> mListMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        edtMessage = findViewById(R.id.edt_message);
        btnSend=findViewById(R.id.btn_send);
        rcvMessage =findViewById(R.id.rcv_message);

        mListMessage =  new ArrayList<>();
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(mListMessage);
        rcvMessage.setAdapter(messageAdapter);
        rcvMessage.setLayoutManager(new LinearLayoutManager(this ));

        getListUserFromDatabase();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }

        });

        edtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkKeyBoard();
            }
        });
    }

    private void getListUserFromDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Message Message = dataSnapshot.getValue(Message.class);
                    mListMessage.add(Message);
                }
                //messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Chat.this, "Get list fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        String strMessage = edtMessage.getText().toString().trim();
        if(TextUtils.isEmpty(strMessage)){
            return;
        }
        mListMessage.add(new Message(strMessage));
        myRef.push().setValue(strMessage);
        messageAdapter.notifyDataSetChanged();
        rcvMessage.scrollToPosition(mListMessage.size()-1);

        edtMessage.setText("");
    }

    private void checkKeyBoard(){
        final View activityRootView = findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r =new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if(heightDiff > 0.25*activityRootView.getRootView().getHeight()){
                    if(mListMessage.size()>0){
                        rcvMessage.scrollToPosition(mListMessage.size()-1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}
