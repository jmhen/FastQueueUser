package fq.campus.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class baseActivity extends AppCompatActivity {
    protected static final String SERVER = "http://fastqueue.000webhostapp.com/";
    protected Activity asyncActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected ProgressDialog getProgressDialog(String msg) {
        ProgressDialog dialog = new ProgressDialog(asyncActivity);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }
}
