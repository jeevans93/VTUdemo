package blueclimb.com.vtudemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by 3lok on 25-Oct-17.
 */

public class progressAdapter extends ArrayAdapter<progresstext> {
    progresstext[] progressitems = null;
    Context context;
    public progressAdapter(Context c,progresstext[] prs)
    {
        super(c,R.layout.progresstext,prs);
        this.context=c;
        this.progressitems = prs;
    }
    public View getView(final int position, View Convertedview, ViewGroup pareent)
    {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        Convertedview = inflater.inflate(R.layout.progresstext,pareent,false);
        TextView subn=(TextView)Convertedview.findViewById(R.id.progresssub);
        TextView progval =(TextView)Convertedview.findViewById(R.id.progressval);
        ProgressBar pb = (ProgressBar)Convertedview.findViewById(R.id.attprogressBar);
        pb.setScaleY(4f);
        subn.setText(progressitems[position].getSubname());
        progval.setText(progressitems[position].getAttval());
        pb.setMax(progressitems[position].getProgressmax());
        pb.setProgress(progressitems[position].getProgress());
        if((((float)progressitems[position].getProgress()/progressitems[position].getProgressmax())*100)<65)
        {
            pb.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }
        if((((float)progressitems[position].getProgress()/progressitems[position].getProgressmax())*100)>65&&(((float)progressitems[position].getProgress()/progressitems[position].getProgressmax())*100)<75)
        {
            pb.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        }
        if((((float)progressitems[position].getProgress()/progressitems[position].getProgressmax())*100)>75)
        {
            pb.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        return Convertedview;
    }

}
