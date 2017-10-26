package blueclimb.com.vtudemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by 3lok on 26-Oct-17.
 */

public class pieAdapter extends ArrayAdapter<internalpie> {
    internalpie[] ip =null;
    Context context;
    ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
    public pieAdapter(Context c,internalpie[] ips)
    {
        super(c,R.layout.internalspie,ips);
        this.context=c;
        this.ip=ips;
    }
    public View getView(final int position, View Convertedview, ViewGroup pareent)
    {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        Convertedview = inflater.inflate(R.layout.internalspie,pareent,false);
        Button b = (Button)Convertedview.findViewById(R.id.internalsubbtn);
        final ExpandableLayout el = (ExpandableLayout)Convertedview.findViewById(R.id.expandableLayout1);
        TextView in1 = (TextView)Convertedview.findViewById(R.id.inter1);
        TextView in2 = (TextView)Convertedview.findViewById(R.id.inter2);
        TextView in3 = (TextView)Convertedview.findViewById(R.id.inter3);
        PieChart pp = (PieChart)Convertedview.findViewById(R.id.interchart);
        TextView avgm = (TextView)Convertedview.findViewById(R.id.avgm);
        b.setText(ip[position].getInternalsubbutton());
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                el.toggle();
            }
        });
        in1.setText(in1.getText()+ip[position].getIa1());
        in2.setText(in2.getText()+ip[position].getIa2());
        in3.setText(in3.getText()+ip[position].getIa3());
        avgm.setText(avgm.getText()+ip[position].getAvg());
        yvalues.add(new PieEntry(Integer.parseInt(ip[position].getAvg()),ip[position].getAvg()+" Marks"));
        yvalues.add(new PieEntry(Integer.parseInt(ip[position].getRemmrks()),"Out of 25"));
        PieDataSet dataSet = new PieDataSet(yvalues, "Subject Average");
        dataSet.setSliceSpace(3);
        PieData data = new PieData(dataSet);
        pp.setData(data);
        pp.notifyDataSetChanged();
        Description d = new Description();
        d.setText("");
        pp.setDescription(d);
        pp.setContentDescription("");
        pp.setDrawHoleEnabled(true);
        pp.setCenterText("Average Chart");
        pp.setHoleRadius(170f);
        pp.getLegend().setTextSize(11f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.DKGRAY);
        pp.animateXY(140, 140);
        pp.getLegend().setEnabled(false);
        pp.invalidate();
        yvalues.clear();
        return Convertedview;
    }
}
