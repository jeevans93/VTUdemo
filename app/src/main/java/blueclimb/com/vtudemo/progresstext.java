package blueclimb.com.vtudemo;

/**
 * Created by 3lok on 25-Oct-17.
 */

public class progresstext {
    String subname;
    String attval;
    int progress,progressmax;
    progresstext(String sub,String att,int prog,int max)
    {
        this.subname=sub;
        this.attval=att;
        this.progress=prog;
        this.progressmax=max;
    }
    public String getSubname()
    {
        return subname;
    }
    public String getAttval()
    {
        return attval;
    }
    public int getProgress()
    {
        return progress;
    }

    public int getProgressmax() {
        return progressmax;
    }

    public void setProgressmax(int progressmax) {
        this.progressmax = progressmax;
    }

    public void setAttval(String attval) {
        this.attval = attval;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }
}
