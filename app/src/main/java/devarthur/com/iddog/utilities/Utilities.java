package devarthur.com.iddog.utilities;


import android.content.Context;
import android.support.v7.app.AlertDialog;

public class Utilities {

    public Utilities()
    {

    }

    public static void showMessageDialog(String message, Context context){
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
