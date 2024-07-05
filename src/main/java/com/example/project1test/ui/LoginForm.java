package com.example.project1test.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginForm extends AppCompatActivity {
    TextView user_email,password;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    Button login;
    String nickname, email,pword;
    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }*/

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);
        this.user_email=(TextView) findViewById(R.id.editTextText);
        this.password=(TextView)findViewById(R.id.editTextTextPassword);
        this.login= (Button) findViewById(R.id.button6);
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
            mAuth.signInWithEmailAndPassword(user_email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginForm.this, "Authentication Successful!.",
                                        Toast.LENGTH_SHORT).show();
                                User.email=email;
                                email=cleanupEmail(email);
                                getNicknameValue(email);
                                // TODO: 2024-06-20 -rend la variable nom global et ajoute un textview dans la barre en haut! 
                                // TODO: 2024-06-20 -passe nickname a l'activite!
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginForm.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                clearInput();
                            }
                        }
                    });

        });
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    public void clearInput(){
        this.user_email.setText("");
        this.password.setText("");
    }
    public void Register(View V){
        Intent i=new Intent(getApplicationContext(),RegisterForm.class);
        startActivity(i);
        finish();
    }

    private void getNicknameValue(String email){
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("User").child(User.cleanupEmail(User.email));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    //Log.d("UserNom", "onDataChange: "+ds.child("Nickname").getValue(String.class));
                    Log.d("Nicks", "onDataChange: "+ds.child("Nickname"));
                    if(ds.exists()){
                        User.nickname=ds.child("Nickname").getValue(String.class);
                        Log.d("nickckkkk", "onDataChange: "+User.nickname);
                    }
                }
                Intent i=new Intent(getApplicationContext(), PickQuiz.class);
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

    private String cleanupEmail(String email){
        return email.replace('.',',').replace('#',',').replace('$',',').replace('[',',').replace(']',',').replace('/',',');
    }

}