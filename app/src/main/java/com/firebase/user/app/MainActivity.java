package com.firebase.user.app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends Activity implements View.OnClickListener{

    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private EditText mEdtName;
    private EditText mEdtPhoneNumber;
    private Button mBtnSubmit;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String name;
    private String phone_number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        init();
        setListener();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("users");

    }

    private void init(){
        mEdtEmail = findViewById(R.id.edit_text_email);
        mEdtPassword = findViewById(R.id.edit_text_password);
        mBtnSubmit = findViewById(R.id.button_submit);
        mEdtName = findViewById(R.id.edit_text_name);
        mEdtPhoneNumber = findViewById(R.id.edit_text_phone_number);
    }

    private void setListener(){
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                String email = mEdtEmail.getText().toString().trim();
                final String pass = mEdtPassword.getText().toString().trim();
                name = mEdtName.getText().toString().trim();
                phone_number = mEdtPhoneNumber.getText().toString().trim();


                    mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.e("uid",user.getUid()+" ");
                                    mDatabaseRef.child(user.getUid()).setValue(new User(user.getUid(),name,Long.parseLong(phone_number)));
                                    Toast.makeText(getApplicationContext(),"User Added successfully",Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(),task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                break;
        }

    }

/*    private void writeNewUserIfNeeded(final String userId, final String name, final long phone_number) {

        mDatabaseRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabaseRef.child(userId).setValue(new User(userId,name,phone_number));
                Toast.makeText(getApplicationContext(),"User inserted",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error",databaseError+" ");


            }
        });
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            mAuth.signOut();
        }

    }
}
