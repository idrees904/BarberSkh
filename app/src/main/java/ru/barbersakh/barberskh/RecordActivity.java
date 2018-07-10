package ru.barbersakh.barberskh;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class RecordActivity extends AppCompatActivity {
    EditText editText;
    Button btnRecord;
    Spinner spinTime;
    HttpResponse httpResponseDate, httpResponseTime;
    JSONObject jsonObjectDate = null;
    String StringHolderDate = "", StringHolderTime = "";
    ProgressBar progressBar;
    // Adding HTTP Server URL to string variable.
    String urlDate = "https://barbersakh.ru/fdatesdate.php";
    String urlTime = "https://barbersakh.ru/time.php";
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Spinner sp;

    EditText etfio, etphone, etdata; //Для insert
    Spinner sptime;
    Button btninsert; //Для insert
    com.android.volley.RequestQueue requestQueue; //Для insert
    String insertUrl = "https://barbersakh.ru/add_record.php"; //Для insert


    String id_specialist, name_specialist, id_services, name_services, id_company,
            name_company, address_company, img_service, img_company, img_specialist;

    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        etfio = (EditText) findViewById(R.id.etFIO); //Для insert
        etphone = (EditText) findViewById(R.id.etPhone); //Для insert
        etdata = (EditText) findViewById(R.id.etData); //Для insert
        sptime = (Spinner) findViewById(R.id.spinTime); //Для insert
        btninsert = (Button) findViewById(R.id.btnRecord); //Для insert

        requestQueue = Volley.newRequestQueue(getApplicationContext()); //Для insert

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("client_fio", etfio.getText().toString());
                        parameters.put("client_phone", etphone.getText().toString());
                        parameters.put("client_date", etdata.getText().toString());
                        parameters.put("client_time", sptime.getSelectedItem().toString());

                        parameters.put("id_company", id_company);
                        parameters.put("id_specialist", id_specialist);
                        parameters.put("id_services", id_services);

                        return parameters;
                    }
                };
                requestQueue.add(request);


                AlertDialog alertDialog = new AlertDialog.Builder(RecordActivity.this).create();

                // Указываем Title
                alertDialog.setTitle("Информационое сообщение");

                // Указываем текст сообщение
                alertDialog.setMessage("Заявка отправлена.");

                // задаем иконку
                alertDialog.setIcon(R.drawable.message_accept);

                // Обработчик на нажатие OK
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Код который выполнится после закрытия окна
                        //Возврат к началу
                        Intent intent = new Intent(RecordActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                // показываем Alert
                alertDialog.show();
            }
        });

        Intent intent = getIntent();
        String id_sp = intent.getStringExtra("id_specialist");
        String name_sp = intent.getStringExtra("nameSp");
        String id_se = intent.getStringExtra("id_services");
        String name_se = intent.getStringExtra("nameSe");
        String id_co = intent.getStringExtra("id_company");
        String name_co = intent.getStringExtra("nameCo");
        String address_co = intent.getStringExtra("addressCo");
        String img_se = intent.getStringExtra("imgSe");
        String img_co = intent.getStringExtra("imgCo");
        String img_sp = intent.getStringExtra("imgSp");

        id_specialist = id_sp;
        id_services = id_se;
        id_company = id_co;
        name_specialist = name_sp;
        name_services = name_se;
        name_company = name_co;
        address_company = address_co;
        img_service = img_se;
        img_company = img_co;
        img_specialist = img_sp;

        TextView nameSpec = (TextView) findViewById(R.id.tvNameSpecialist);
        TextView nameServ = (TextView) findViewById(R.id.tvNameUslugi);
        TextView nameCpmp = (TextView) findViewById(R.id.tvNameCompany);
        TextView AddressComp = (TextView) findViewById(R.id.tvAddressCompany);

        nameSpec.setText(name_specialist);
        nameServ.setText(name_services);
        nameCpmp.setText(name_company);
        AddressComp.setText(address_company);

        Log.i("MyLOG", "--------------------------------RECORD---------------------------------------");
        Log.i("MyLOG", "id_services:" + id_services);
        Log.i("MyLOG", "name_services:" + name_services);
        Log.i("MyLOG", "img_service:" + img_service);
        Log.i("MyLOG", "id_company:" + id_company);
        Log.i("MyLOG", "name_company:" + name_company);
        Log.i("MyLOG", "address_company:" + address_company);
        Log.i("MyLOG", "img_company:" + img_company);
        Log.i("MyLOG", "id_specialist:" + id_specialist);
        Log.i("MyLOG", "name_specialist:" + name_specialist);
        Log.i("MyLOG", "img_specialist:" + img_specialist);

        new DownloadImageTask((ImageView) findViewById(R.id.ImgUslugi)).execute(img_service);
        new DownloadImageTask((ImageView) findViewById(R.id.ImgCompany)).execute(img_company);
        new DownloadImageTask((ImageView) findViewById(R.id.ImgSpecialist)).execute(img_specialist);

        editText = (EditText) findViewById(R.id.etData);
        spinTime = (Spinner) findViewById(R.id.spinTime);

        btnRecord = (Button) findViewById(R.id.btnRecord);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        progressBar.setVisibility(View.GONE);

        // Выбор даты и времени
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing progress bar on button click.
                progressBar.setVisibility(View.VISIBLE);

                //Загрузка даты
                new GetDate(RecordActivity.this).execute();
            }
        });
    }

    // Declaring GetDataFromServer method with AsyncTask.
    public class GetDate extends AsyncTask<Void, Void, Void> {
        // Declaring CONTEXT.
        public Context context;
        private String minYear = "", minMonth = "", minDay = "";
        private String maxYear = "", maxMonth = "", maxDay = "";

        public GetDate(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient httpClient = new DefaultHttpClient();

            // Adding HttpURL to my HttpPost oject.
            HttpPost httpPost = new HttpPost(urlDate);

            try {
                httpResponseDate = httpClient.execute(httpPost);

                StringHolderDate = EntityUtils.toString(httpResponseDate.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                // Passing string holder variable to JSONArray.
                JSONArray jsonArray = new JSONArray(StringHolderDate);
                jsonObjectDate = jsonArray.getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            try {
                // Adding JSOn string to textview after done loading.
                minYear = jsonObjectDate.getString("minYear");
                minMonth = jsonObjectDate.getString("minMonth");
                minDay = jsonObjectDate.getString("minDay");
                maxYear = jsonObjectDate.getString("maxYear");
                maxMonth = jsonObjectDate.getString("maxMonth");
                maxDay = jsonObjectDate.getString("maxDay");

                //etDate.setText(jsonObject.getString("minYear"));
                Log.i("MyLOG", "GetDate.onPostExecute(): MinDate: " + minYear + "." + minMonth + "." + minDay);
                Log.i("MyLOG", "GetDate.onPostExecute(): MaxDate: " + maxYear + "." + maxMonth + "." + maxDay);

                /*Начало открытия календаря*/
                SelectDate datePickerDialog = new SelectDate(RecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Мутим что-то с датой
                        EditText eTDate = (EditText) findViewById(R.id.etData);

                        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                        String maxDate = df.format(calendar.getTime());

                        eTDate.setText(maxDate);

                        new GetTime(RecordActivity.this).execute();
                    }
                }, year, month, day);   // Значения года, месяца, дня, это будет открыто по умолчанию в диалоговом окне

                datePickerDialog.setMinDate(Integer.parseInt(minYear), Integer.parseInt(minMonth), Integer.parseInt(minDay)); // год, месяц, дата (для установки пользовательской минимальной даты)
                datePickerDialog.setMaxDate(Integer.parseInt(maxYear), Integer.parseInt(maxMonth), Integer.parseInt(maxDay));  // год, месяц, дата (для установки пользовательской максимальной даты)

                datePickerDialog.show();
                /*Конец открытия календаря*/


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

            //Hiding progress bar after done loading TextView.
            progressBar.setVisibility(View.GONE);

        }
    }

    // Declaring GetDataFromServer method with AsyncTask.
    public class GetTime extends AsyncTask<Void, Void, Void> {

        public Context context;
        ArrayList<String> list;

        public GetTime(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient httpClient = new DefaultHttpClient();

            //HttpPost httpPost = new HttpPost(urlTime + editText.getText());
            //http://10.220.3.49/time.php?id=1&idate=
            HttpPost httpPost = new HttpPost(urlTime + "?id=" + id_specialist + "&idate=" + editText.getText());

            Log.i("MyLOG", "HttpPost: " + urlTime + "?id=" + id_specialist + "&idate=" + editText.getText());


            try {
                httpResponseTime = httpClient.execute(httpPost);

                StringHolderTime = EntityUtils.toString(httpResponseTime.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONArray jArray = new JSONArray(StringHolderTime);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    list.add(jsonObject.getString("time"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            listItems.addAll(list);

            sp = (Spinner) findViewById(R.id.spinTime);
            adapter = new ArrayAdapter<String>(RecordActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
            sp.setAdapter(adapter);


            progressBar.setVisibility(View.GONE);

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}