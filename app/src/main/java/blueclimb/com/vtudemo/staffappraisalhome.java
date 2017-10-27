package blueclimb.com.vtudemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by 3lok on 26-Oct-17.
 */

public class staffappraisalhome extends AppCompatActivity {
    String USN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffappraisalhome);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            USN =(String) b.get("usn");
        }
        getsubs();
    }

    public void getsubs() {

    }
}
