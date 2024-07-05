package com.example.project1test.ui;

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

import java.util.ArrayList;
import java.util.List;

public class RegisterForm extends AppCompatActivity {
    TextView user_email,password,nickname,goBackToLogin;
    FirebaseAuth mAuth;
    Button reg;
    List<String> userslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_form);
        mAuth= FirebaseAuth.getInstance();

        this.user_email=findViewById(R.id.editTextText);
        this.password=findViewById(R.id.editTextTextPassword);
        this.nickname=findViewById(R.id.editNickName);
        this.goBackToLogin=(TextView)findViewById(R.id.textView13);

        reg=findViewById(R.id.button6);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pword1,name;

                email=(user_email.getText().toString());
                pword1=(password.getText().toString());
                name=(nickname.getText().toString());
                User user=new User(email,name);
                DatabaseReference myRef = database.getReference("User");

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterForm.this,"Enter username!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pword1)){
                    Toast.makeText(RegisterForm.this,"Enter password!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterForm.this,"What should we call you?",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pword1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    insertNickName(user,myRef,name);
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
                            }
                        });
            }
        });

        this.goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),LoginForm.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void insertNickName(User user,DatabaseReference myRef,String name){
        String clean_email=User.cleanupEmail(user.getEmail());
        Log.d("CLEAN", "insertNickName: "+clean_email);
        myRef.child(clean_email).child("Nickname").setValue(user.getNickname());
        //myRef=FirebaseDatabase.getInstance().getReference().child("User").child(clean_email);
        userslist=new ArrayList<>();
        //userslist.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("DSDAWG", "onDataChange: ");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.exists()){
                        Log.d("DSDAWG", "onDataChange: "+ds.child("Nickname").getValue());
                        User.nickname =(ds.child("Nickname").getValue(String.class));
                    }
                   /*
                    for (DataSnapshot child : ds.getChildren()) {
                        Log.d("TAG", "Child Key: " + child.getKey());
                        Log.d("TAG", "Child Value: " + child.child("Nickname").getValue());
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
        Toast.makeText(getApplicationContext(),"New user in Firebase DB!",Toast.LENGTH_SHORT).show();
    }

    private String cleanupEmail(String email){
        return email.replace('.',',').replace('#',',').replace('$',',').replace('[',',').replace(']',',').replace('/',',');
    }
    private void clearInput(){
        this.user_email.setText("");
        this.password.setText("");
    }

}