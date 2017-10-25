package blueclimb.com.vtudemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
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
    private int subcount = 0;
    ListView lv;
    progresstext[] ptlist;
    progressAdapter pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            USN = (String) b.get("usn");
        }
        getdata();
    }

    public void getdata() {
        class getddataAsync extends AsyncTask<Void, Void, String> {
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
                lv=(ListView)findViewById(R.id.attendancestudentlist);
                ptlist = new progresstext[subcount];
                for(int i=0;i<subcount;i++)
                {
                    ptlist[i]=new progresstext("Subject "+String.valueOf(i),String.valueOf(prcount.get(i))+"/"+String.valueOf(tocount.get(i)),prcount.get(i),tocount.get(i));
                }
                pa = new progressAdapter(Attendance.this,ptlist);
                lv.setAdapter(pa);
                lv.invalidate();
                loadingDialog.dismiss();
            }
        }
        getddataAsync g = new getddataAsync();
        g.execute();
    }
}