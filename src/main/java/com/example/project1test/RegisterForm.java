package com.example.project1test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterForm extends AppCompatActivity {
    TextView user_email,password,nickname,goBackToLogin;
    FirebaseAuth mAuth;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_form);
        mAuth= FirebaseAuth.getInstance();

        this.user_email=findViewById(R.id.editTextText);
        this.password=findViewById(R.id.editTextTextPassword);
        this.nickname=findViewById(R.id.editNickName);
        this.goBackToLogin=findViewById(R.id.textView13);
        this.register=findViewById(R.id.button6);

        //onClick listener for registering user
        this.register.setOnClickListener(v -> {
            String pword1;

            User.email=(user_email.getText().toString());
            pword1=(password.getText().toString());
            User.nickname=(nickname.getText().toString());

            //Check if any of the fields are empty
            if(TextUtils.isEmpty(User.email)){
                Toast.makeText(RegisterForm.this,"Enter username!",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(pword1)){
                Toast.makeText(RegisterForm.this,"Enter password!",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(User.nickname)){
                Toast.makeText(RegisterForm.this,"What should we call you?",Toast.LENGTH_SHORT).show();
                return;
            }

            //Authenticate registered user
            mAuth.createUserWithEmailAndPassword(User.email, pword1)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            insertNickName();
                            Toast.makeText(RegisterForm.this, "Account created!.",
                                    Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(), PickQuiz.class);
                            startActivity(i);
                            //todo envoie le nom a l'activite!!!!!!!!!
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterForm.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            clearInput();
                        }
                    });
        });

        //onClick listener to go back to login page
        this.goBackToLogin.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(),LoginForm.class);
            startActivity(i);
            finish();
        });
    }

    private void clearInput(){
        this.user_email.setText("");
        this.password.setText("");
    }

    private void insertNickName(){
        //Insert nickname in realtime database for newly registered user
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        assert currentFirebaseUser != null;

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child(currentFirebaseUser.getUid()).child("UserCredentials");
        //Create User object for future use
        User user=new User(User.email,User.nickname);

        myRef.setValue(user);

    }
}