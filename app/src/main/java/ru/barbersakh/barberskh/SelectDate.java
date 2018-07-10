package ru.barbersakh.barberskh;

/*import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;





public class DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Dialog picker = new DatePickerDialog(getActivity(),this,year,month,day);
        picker.setTitle(getResources().getString(R.string.choose_date));

        return picker;
    }

    @Override
    public void onStart(){
        super.onStart();
        Button nButton = ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.ready));

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        TextView tv = (TextView) getActivity().findViewById(R.id.tv);
        tv.setText(day+"."+month+"."+year);


    }
}*/

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by Manohar on 14/08/2017.
 */

public class SelectDate extends DatePickerDialog {


    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SelectDate(@NonNull Context context) {
        super(context);
        datePickerDialog = new DatePickerDialog(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SelectDate(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        datePickerDialog = new DatePickerDialog(context, themeResId);
    }

    public SelectDate(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
        datePickerDialog = new DatePickerDialog(context, listener, year, month, dayOfMonth);

    }

    public SelectDate(@NonNull Context context, int themeResId, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
        datePickerDialog = new DatePickerDialog(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }

    public void show() {
        datePickerDialog.show();
    }


    public void setMinDate(int year, int month, int day) {

        //String minDate = "" + day + "-" + month + "-" + year;

        Log.i("MyLOG", "SelectDate.setMinDate(): year, month, day: " + year + month + day);
        month = month - 1;
        Calendar calendar = new GregorianCalendar(year, month, day);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String minDate = df.format(calendar.getTime());

        Log.i("MyLOG", "SelectDate.setMinDate(): MinDate: " + minDate);

        try {
            datePickerDialog.getDatePicker().setMinDate(dateFormat.parse(minDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void setMaxDate(int year, int month, int day) {

        //String maxDate = "" + day + "-" + month + "-" + year;

        Log.i("MyLOG", "SelectDate.setMinDate(): year, month, day: " + year + month + day);
        month = month - 1;
        Calendar calendar = new GregorianCalendar(year, month, day);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String maxDate = df.format(calendar.getTime());

        Log.i("MyLOG", "SelectDate.setMaxDate(): MaxDate: " + maxDate);

        try {
            datePickerDialog.getDatePicker().setMaxDate(dateFormat.parse(maxDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void setTodayAsMaxDate() {

        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime());

    }

    public void setTodayAsMinDate() {

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());

    }


    private String getmonth(int month) {

        switch (month) {
            case 1:
                return "01";

            case 2:
                return "02";

            case 3:
                return "03";

            case 4:
                return "04";

            case 5:
                return "05";

            case 6:
                return "06";

            case 7:
                return "07";

            case 8:
                return "08";

            case 9:
                return "09";

            case 10:
                return "10";

            case 11:
                return "11";

            case 12:
                return "12";


        }
        return "01";

    }

}
