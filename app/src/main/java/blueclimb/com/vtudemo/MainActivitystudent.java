package blueclimb.com.vtudemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivitystudent extends AppCompatActivity {

    String USN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainstudent);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            USN =(String) b.get("usn");
            if(b.get("pid")!=null&&!b.get("pid").toString().isEmpty())
            {
                USN=b.getString("pid").substring(0,10);
                Log.e("usnmain",USN);
            }
        }
    }

    public void attendance(View view){
        Intent intent = new Intent(MainActivitystudent.this,Attendance.class);
        intent.putExtra("usn",USN);
        startActivity(intent);
    }

    public void internals(View view){
        Intent intent = new Intent(MainActivitystudent.this,studentInternals.class);
        intent.putExtra("usn",USN);
        startActivity(intent);
    }

    public void syllabus(View view){
        Intent intent = new Intent(MainActivitystudent.this,syllabus.class);
        startActivity(intent);
    }

    public void feepayment(View view){
//        Intent intent = new Intent(MainActivity.this,feepayment.class);
//        startActivity(intent);
    }

    public void staffappraisal(View view){
        Intent intent = new Intent(MainActivitystudent.this,staffappraisalhome.class);
        intent.putExtra("usn",USN);
        startActivity(intent);
    }

    public void notes(View view){
        Intent intent = new Intent(MainActivitystudent.this,Notes.class);
        startActivity(intent);
    }
    public void notification(View view){
//        Intent intent = new Intent(MainActivity.this,notification.class);
//        startActivity(intent);
    }
    public void coe(View view){
        Intent intent = new Intent(MainActivitystudent.this,calendarofevents.class);
        startActivity(intent);
    }

}