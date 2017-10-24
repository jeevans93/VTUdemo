package blueclimb.com.vtudemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by jeevan on 17/10/17.
 */
public class Attendance extends AppCompatActivity {
    String USN;
    ArrayList<Integer> abcount = new ArrayList<Integer>();
    ArrayList<Integer> prcount = new ArrayList<Integer>();
    ArrayList<Integer> tocount = new ArrayList<Integer>();
    private android.widget.ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    private int subcount=0;
    RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            USN =(String) b.get("usn");
        }
        rl = (RelativeLayout)findViewById(R.id.attendrel);
        getdata();
    }

    public void getdata() {
        class getddataAsync extends AsyncTask<Void,Void,String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Attendance.this, "Please wait", "Updating");
            }


            @Override
            protected String doInBackground(Void... params) {
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("usn", USN));
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String web = getResources().getString(R.string.web);
                    HttpPost httpPost = new HttpPost(web + "/attendance/attendance_view.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("log res", result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    JSONObject jsonResponse = new JSONObject(result);

                    JSONArray slist = jsonResponse.getJSONArray("attendence");

                    for (int j = 0; j < slist.length(); j++) {
                        JSONObject sub = slist.getJSONObject(j);
                        subcount++;
                        abcount.add(Integer.parseInt(sub.getString("ac")));
                        prcount.add(Integer.parseInt(sub.getString("pc")));
                        tocount.add((Integer.parseInt(sub.getString("ac"))) + (Integer.parseInt(sub.getString("pc"))));
                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                loadingDialog.dismiss();

                ProgressBar pp = new ProgressBar(getApplicationContext(),null,android.R.attr.progressBarStyleHorizontal);
                TextView sub = new TextView(getApplicationContext());
                TextView total = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                RelativeLayout.LayoutParams belsub = (RelativeLayout.LayoutParams)sub.getLayoutParams();
                RelativeLayout.LayoutParams belprog = (RelativeLayout.LayoutParams)sub.getLayoutParams();
                RelativeLayout.LayoutParams beltot = (RelativeLayout.LayoutParams)pp.getLayoutParams();
                belsub.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                belprog.addRule(RelativeLayout.BELOW);
                beltot.addRule(RelativeLayout.BELOW);
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                pp.setLayoutParams(lp);
                sub.setLayoutParams(lp);
                total.setLayoutParams(lp);
                for(int i =0;i<subcount;i++)
                {
                    sub.setText("Subject "+ String.valueOf(i));
                    total.setText(String.valueOf(prcount.get(i)+"/"+String.valueOf(tocount.get(i))));
                    progressBar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                    progressBar.setScaleY(3f);
                    pp.setMax(tocount.get(i));
                    rl.removeAllViews();
                    rl.addView(sub);
                    rl.addView(pp);
                    rl.addView(total);
                }
            }
        }
        getddataAsync g = new getddataAsync();
        g.execute();
    }
//    void disp()
//    {
//        progressBar = (android.widget.ProgressBar) findViewById(R.id.progressBar);
//        progressBar.getProgressDrawable().setColorFilter(
//                Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
//        progressBar.setScaleY(3f);
//        textView = (TextView) findViewById(R.id.textView);
//        // Start long running operation in a background thread
//        new Thread(new Runnable() {
//            public void run() {
//                while (progressStatus < 100) {
//                    progressStatus += 1;
//                    // Update the progress bar and display the
//                    //current value in the text view
//                    handler.post(new Runnable() {
//                        public void run() {
//                            progressBar.setProgress(progressStatus);
//                            textView.setText(progressStatus+"/"+progressBar.getMax());
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }

}
