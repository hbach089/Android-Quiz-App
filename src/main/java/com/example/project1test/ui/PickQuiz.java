package com.example.project1test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project1test.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PickQuiz extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser fbuser;
    TextView topnickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //String nom=getIntent().getStringExtra("Nom");
        //Log.d("nommmmm", "onCreate: "+User.nom);
        topnickname=(TextView)findViewById(R.id.textView8);
        topnickname.setText(User.nickname);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Quizzies");
        //toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        auth=FirebaseAuth.getInstance();
        /*
        if(fbuser==null){
            Intent i=new Intent(getApplicationContext(), LoginForm.class);
            startActivity(i);
            finish();
        }*/
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

    }
    public void startQ(View V){
        //fbuser=auth.getCurrentUser();
        User user=new User();
        Intent i=new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(i);
        finishAffinity();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.navigation_home){
            Intent i=new Intent(getApplicationContext(), PickQuiz.class);
            startActivity(i);
            finish();
        }
        else if (item.getItemId()==R.id.navigation_dashboard) {
            Intent i=new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(i);
            finish();
        } else if (item.getItemId()==R.id.navigation_logout) {
            //ajoute le logout de firebase!!!!!!!!!!!!!!!!
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(getApplicationContext(), LoginForm.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}