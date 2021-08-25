package gian.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.gian.getcooked.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;



    public LoadingDialog(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custim_dialog,null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
