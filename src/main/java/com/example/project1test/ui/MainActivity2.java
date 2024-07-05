package com.example.project1test.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project1test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    int nextq,prevq;
    int qdisp,score=0;
    int nextB,prevB,ans1B,ans2B,ans3B,ans4B;
    static int cnt=0;
    TextView t;
    Button buttonNext,buttonPrev,button1ans,button2ans,button3ans,button4ans,submit;
    TextView topnickname;

    Question q[]=new Question[]{
            new Question("What element does 'O' represent on the periodic table?","Oxygen", new String[]{"OBlock","Oxygen","OxyClean","Oh Woww"}),
            new Question("What is your body's largest organ?","Skin",new String[]{"Heart","Brain","Stomach","Skin"}),
            new Question("Who is attributed with discovering gravity?","Newton",new String[]{"Newton","Galileo","Einstein","Darwin"}),
            new Question("What is the largest land animal","African elephant",new String[]{"Lion","Rhino","African elephant","Hippo"}),
            new Question("How many stripes are there on the US flag","13",new String[]{"51","13","14","19"})
    };
    public String[] answers=new String[q.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_geography_quiz);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.nextq=R.id.nextb;
        this.prevq=R.id.prevb;

        this.qdisp=R.id.textView2;

        this.nextB=R.id.nextb;
        this.prevB=R.id.prevb;

        this.ans1B=R.id.op1;
        this.ans2B=R.id.op2;
        this.ans3B=R.id.op3;
        this.ans4B=R.id.op4;

        this.t=(TextView) findViewById(this.qdisp);
        this.buttonNext=(Button)findViewById(this.nextB);
        this.buttonPrev=(Button)findViewById(this.prevB);
        this.buttonPrev.setBackgroundColor(Color.MAGENTA);
        this.buttonNext.setBackgroundColor(Color.MAGENTA);

        this.button1ans= findViewById(this.ans1B);
        this.button2ans= findViewById(this.ans2B);
        this.button3ans= findViewById(this.ans3B);
        this.button4ans= findViewById(this.ans4B);

        this.submit= findViewById(R.id.submitbutton);

        this.button1ans.setText((this.q[0].getOptions())[0]);
        this.button2ans.setText((this.q[0].getOptions())[1]);
        this.button3ans.setText((this.q[0].getOptions())[2]);
        this.button4ans.setText((this.q[0].getOptions())[3]);
        this.button1ans.setBackgroundColor(Color.MAGENTA);
        this.button2ans.setBackgroundColor(Color.MAGENTA);
        this.button3ans.setBackgroundColor(Color.MAGENTA);
        this.button4ans.setBackgroundColor(Color.MAGENTA);

        this.buttonPrev.setEnabled(false);
        this.t.setText(this.q[0].getQs());
        this.topnickname=(TextView) findViewById(R.id.textView5);
        this.topnickname.setText(User.nickname);
    }
    public void GotoNextQ(View V){
        cnt+=1;
        this.buttonPrev.setEnabled(true);


//+this.answers[cnt].equals(this.q[cnt].getOptions()[1])

        this.button1ans.setBackgroundColor(Color.MAGENTA);
        this.button2ans.setBackgroundColor(Color.MAGENTA);
        this.button3ans.setBackgroundColor(Color.MAGENTA);
        this.button4ans.setBackgroundColor(Color.MAGENTA);
        if(this.answers[cnt]!=null){
            if (this.answers[cnt].equals(this.q[cnt].getOptions()[0])) {
                this.button1ans.setBackgroundColor(Color.CYAN);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[1])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.CYAN);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[2])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.CYAN);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[3])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.CYAN);
            }
        }



        this.button1ans.setText((this.q[cnt].getOptions())[0]);
        this.button2ans.setText((this.q[cnt].getOptions())[1]);
        this.button3ans.setText((this.q[cnt].getOptions())[2]);
        this.button4ans.setText((this.q[cnt].getOptions())[3]);

        if(this.cnt>=this.q.length-1){
            cnt=this.q.length-1;
            this.t.setText(this.q[cnt].getQs());
            V.setEnabled(false);
        }
        else{
            this.t.setText(this.q[cnt].getQs());
        }
    }
    public void GotoPrevQ(View V){
        cnt-=1;

        //this.button1ans.getText().toString()
        //this.q[cnt].getOptions()[3].length()

//+this.answers[cnt].equals(this.q[cnt].getOptions()[1])
        this.button1ans.setBackgroundColor(Color.MAGENTA);
        this.button2ans.setBackgroundColor(Color.MAGENTA);
        this.button3ans.setBackgroundColor(Color.MAGENTA);
        this.button4ans.setBackgroundColor(Color.MAGENTA);

        if(this.answers[cnt]!=null){
            if (this.answers[cnt].equals(this.q[cnt].getOptions()[0])) {
                this.button1ans.setBackgroundColor(Color.CYAN);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[1])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.CYAN);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[2])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.CYAN);
                this.button4ans.setBackgroundColor(Color.MAGENTA);
            } else if (this.answers[cnt].equals(this.q[cnt].getOptions()[3])) {
                this.button1ans.setBackgroundColor(Color.MAGENTA);
                this.button2ans.setBackgroundColor(Color.MAGENTA);
                this.button3ans.setBackgroundColor(Color.MAGENTA);
                this.button4ans.setBackgroundColor(Color.CYAN);
            }
        }

        this.buttonNext.setEnabled(true);
        this.button1ans.setText((this.q[cnt].getOptions())[0]);
        this.button2ans.setText((this.q[cnt].getOptions())[1]);
        this.button3ans.setText((this.q[cnt].getOptions())[2]);
        this.button4ans.setText((this.q[cnt].getOptions())[3]);
        if(cnt<=0){
            cnt=0;
            this.t.setText(this.q[cnt].getQs());
            V.setEnabled(false);
        }
        else{
            V.setEnabled(true);
            this.t.setText(this.q[cnt].getQs());
        }
    }

    public void AnsweredClicked(View V){
        if(V.getId()==this.ans1B) {
            this.answers[cnt] = this.button1ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.CYAN);
            this.button2ans.setBackgroundColor(Color.MAGENTA);
            this.button3ans.setBackgroundColor(Color.MAGENTA);
            this.button4ans.setBackgroundColor(Color.MAGENTA);

        }
        else if(this.ans2B==V.getId()) {
            this.answers[cnt] = this.button2ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.MAGENTA);
            this.button2ans.setBackgroundColor(Color.CYAN);
            this.button3ans.setBackgroundColor(Color.MAGENTA);
            this.button4ans.setBackgroundColor(Color.MAGENTA);
        }
        else if (V.getId()==this.ans3B) {
            this.answers[cnt] = this.button3ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.MAGENTA);
            this.button2ans.setBackgroundColor(Color.MAGENTA);
            this.button3ans.setBackgroundColor(Color.CYAN);
            this.button4ans.setBackgroundColor(Color.MAGENTA);
        }
        else if (V.getId()==this.ans4B) {
            this.answers[cnt] = this.button4ans.getText().toString();
            this.button1ans.setBackgroundColor(Color.MAGENTA);
            this.button2ans.setBackgroundColor(Color.MAGENTA);
            this.button3ans.setBackgroundColor(Color.MAGENTA);
            this.button4ans.setBackgroundColor(Color.CYAN);
        }
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
            //todo ajoute le logout de firebase!!!!!!!!!!!!!!!!
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(getApplicationContext(), LoginForm.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void SubmitQ(View V){
        for(int j=0;j<this.answers.length;j++){
            if(Objects.equals(this.answers[j], this.q[j].getQa())){
                this.score+=1;
            }
        }
        Intent i=new Intent(getApplicationContext(), MainActivity3.class);
        i.putExtra("Score",score);
        startActivity(i);
        finish();
        // todo avec la moyenne
    }
}
