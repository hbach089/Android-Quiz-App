package com.example.project1test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

import org.w3c.dom.Text;

import java.util.Objects;

public class LoginForm extends AppCompatActivity {
    TextView user_email,password;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    Button login;
    String email,pword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);
        this.user_email= findViewById(R.id.editTextText);
        this.password=findViewById(R.id.editTextTextPassword);
        this.login= findViewById(R.id.button6);

        //Login button onClick event listener
        this.login.setOnClickListener(v -> {
            this.email=user_email.getText().toString();
            this.pword=password.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(LoginForm.this,"Enter username!",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(pword)){
                Toast.makeText(LoginForm.this,"Enter password!",Toast.LENGTH_SHORT).show();
                return;
            }

            //authenticate the user credentials in firebase
            mAuth.signInWithEmailAndPassword(user_email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginForm.this, "Authentication Successful!.",
                                    Toast.LENGTH_SHORT).show();
                            User.email=email;
                            getNicknameValue(User.email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginForm.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            clearInput();
                        }
                    });

        });
    }

    private void clearInput(){
        this.user_email.setText("");
        this.password.setText("");
    }
    public void Register(View V){
        //If user does not exist, click to register
        Intent i=new Intent(getApplicationContext(),RegisterForm.class);
        startActivity(i);
        finish();
    }

    private void getNicknameValue(String email){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        assert currentFirebaseUser != null;
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("Users").child(currentFirebaseUser.getUid()).child("UserCredentials");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Search for nickname corresponding to the current user
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(Objects.equals(ds.getKey(), "nickname")){
                        //Toast.makeText(getApplicationContext(), (CharSequence) ds.getValue(),Toast.LENGTH_SHORT).show();
                        User.nickname= Objects.requireNonNull(ds.getValue()).toString();
                    }
                }
                //onClick, go to the next activity
                Intent i=new Intent(getApplicationContext(),PickQuiz.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}

