package blueclimb.com.vtudemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jeevan on 24/10/17.
 */

public class InternalsStudent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internalsstudent);
        Intent intent = new Intent(InternalsStudent.this, unit.class);
        startActivity(intent);
        finish();
    }
}
