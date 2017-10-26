package blueclimb.com.vtudemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

public class studentInternals extends AppCompatActivity {
    ListView lv;
    internalpie[] ips;
    String USN;
    ArrayList<String> iamarks = new ArrayList<String>();
    private int subcount=0;
    pieAdapter pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentinternal);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            USN = (String) b.get("usn");
        }
        lv=(ListView)findViewById(R.id.internalslist);
        getmarks();
    }

    public void getmarks() {
        class getpieAsync extends AsyncTask<Void, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(studentInternals.this, "Please wait", "Updating");
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
                    HttpPost httpPost = new HttpPost(web + "/internals/internal_view.php");
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
                    JSONArray slist = jsonResponse.getJSONArray("internals");
                    for (int j = 0; j < slist.length(); j++) {
                        JSONObject sub = slist.getJSONObject(j);
                        subcount++;
                        iamarks.add(String.valueOf(j));
                        iamarks.add(sub.getString("ia1"));
                        iamarks.add(sub.getString("ia2"));
                        iamarks.add(sub.getString("ia3"));
                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
//                    Toast.makeText(attendencesubmit.this, "JsonArray fail", Toast.LENGTH_SHORT).show();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                loadingDialog.dismiss();
                int avg[]= new int[subcount];
                int rem[]= new int[subcount];
                int ims[][]= new int[subcount][3];
                for(int l=0;l<subcount;l++)
                {
                    for(int k=0;k<4;k++)
                    {
                        if(k==0) {
                            continue;
                        }
                        if(k!=0)
                        {
                            ims[l][k-1] = Integer.parseInt(iamarks.get((l*4)+k));
                        }
                    }
                }
                for(int i=0;i<subcount;i++)
                {
                    int h1,h2;
                    h1=Math.max(ims[i][0],h2=Math.max(ims[i][1],ims[i][2]));
                    h2=Math.min(Math.max(Math.min(ims[i][1],ims[i][2]),ims[i][0]),h2);
                    avg[i]=h1+h2/2;
                    rem[i]=25-avg[i];
                }

                ips = new internalpie[subcount];
                for(int i=0;i<subcount;i++)
                {
                    ips[i]=new internalpie("Subject "+i,String.valueOf(ims[i][0]),String.valueOf(ims[i][1]),String.valueOf(ims[i][2]),String.valueOf(avg[i]),String.valueOf(rem[i]));
                }
                pa= new pieAdapter(studentInternals.this,ips);
                lv.setAdapter(pa);
                lv.invalidate();
            }
        }
        getpieAsync gpa = new getpieAsync();
        gpa.execute();
    }
}
