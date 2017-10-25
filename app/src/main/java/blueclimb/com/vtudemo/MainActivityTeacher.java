package blueclimb.com.vtudemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jeevan on 17/10/17.
 */

public class MainActivityTeacher extends AppCompatActivity {
    private String tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            tid =(String) b.get("tid");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainteacher);
    }
    public void attendance(View view){
        Intent intent = new Intent(MainActivityTeacher.this,attendanceteacher.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void internals(View view){
        Intent intent = new Intent(MainActivityTeacher.this,teacherinternalsselect.class);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void syllabus(View view){
        Intent intent = new Intent(MainActivityTeacher.this,syllabus.class);
        startActivity(intent);
    }

    public void staffappraisal(View view){
//        Intent intent = new Intent(MainActivityTeacher.this,staffappraisal.class);
//        startActivity(intent);
    }

    public void notes(View view){
        Intent intent = new Intent(MainActivityTeacher.this,teachernotes.class);
        startActivity(intent);
    }
    public void notification(View view){
//        Intent intent = new Intent(MainActivityTeacher.this,notification.class);
//        startActivity(intent);
    }
    public void coe(View view){
        Intent intent = new Intent(MainActivityTeacher.this,calendarofevents.class);
        startActivity(intent);
    }
}
