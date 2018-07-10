package ru.barbersakh.barberskh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpecialistActivity extends AppCompatActivity {

    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String id_services, id_company, name_company, address_company, name_services,img_company,img_services;

    String HTTP_JSON_URL = "https://barbersakh.ru/specialist.php";

    String id_specialist_json = "id";  //ID специалиста
    String name_specialist_json = "name"; //ФИО специалиста
    String img_specialist_json = "img"; //Фотография специалиста


    JsonArrayRequest RequestOfJSonArray;

    RequestQueue requestQueue;

    View view;

    int RecyclerViewItemPosition;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapterSpecialist;

    ArrayList<String> FIOSpecialistArrListForClick;
    ArrayList<String> ImgSpecialistArrListForClick;
    ArrayList<String> IdSpecialistCategoryArrListForClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id_se = intent.getStringExtra("id_services");
        String name_se = intent.getStringExtra("nameSe");
        String id_co = intent.getStringExtra("id_company");
        String name_co = intent.getStringExtra("nameCo");
        String address_co = intent.getStringExtra("address");
        String img_co = intent.getStringExtra("imgCo");
        String img_se = intent.getStringExtra("imgSe");
        id_services = id_se;
        id_company = id_co;
        name_company = name_co;
        address_company = address_co;
        name_services = name_se;
        img_company = img_co;
        img_services = img_se;

        FIOSpecialistArrListForClick = new ArrayList<>();
        ImgSpecialistArrListForClick = new ArrayList<>();
        IdSpecialistCategoryArrListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP_CALL();

        // Реализация прослушивателя щелчков в RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(SpecialistActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    // Получение значения свойства RecyclerView.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);

                    Log.i("MyLOG", "--------------------------------SPECIALIST---------------------------------------");
                    Log.i("MyLOG", "id_services: " + id_services);
                    Log.i("MyLOG", "nameSe: " + name_services);
                    Log.i("MyLOG", "id_company: " + id_company);
                    Log.i("MyLOG", "nameCo: " + name_company);
                    Log.i("MyLOG", "addressCo: " + address_company);
                    Log.i("MyLOG", "id_specialist: " + IdSpecialistCategoryArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "nameSp: " + FIOSpecialistArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "nameSp: " + ImgSpecialistArrListForClick.get(RecyclerViewItemPosition));

                    Intent intent = new Intent(SpecialistActivity.this, RecordActivity.class);
                    intent.putExtra("id_services", id_services); //Передать id услуги
                    intent.putExtra("nameSe", name_services); //Передать id услуги
                    intent.putExtra("id_company", id_company); //Передать id компании
                    intent.putExtra("nameCo", name_company); //Передать имя компании
                    intent.putExtra("addressCo", address_company); //Передать адресс компании
                    intent.putExtra("id_specialist", IdSpecialistCategoryArrListForClick.get(RecyclerViewItemPosition)); //Передать id специалиста
                    intent.putExtra("nameSp", FIOSpecialistArrListForClick.get(RecyclerViewItemPosition)); //Передать имя специалиста

                    intent.putExtra("imgSp", ImgSpecialistArrListForClick.get(RecyclerViewItemPosition)); //Передать имя специалиста
                    intent.putExtra("imgCo", img_company); //Передать имя специалиста
                    intent.putExtra("imgSe", img_services); //Передать имя специалиста

                    startActivity(intent);

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    public void JSON_HTTP_CALL() {

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL + "?" + "id_se=" + id_services + "&" + "id_co=" + id_company,
                //RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL + "?" + "id_cat=2&id_com=2",

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(SpecialistActivity.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setFIOSpecialist(json.getString(name_specialist_json));

                GetDataAdapter2.setIdSpecialist(json.getString(id_specialist_json));

                // Добавление имени заголовка изображения в массив для отображения в событии click RecyclerView.
                FIOSpecialistArrListForClick.add(json.getString(name_specialist_json));

                ImgSpecialistArrListForClick.add(json.getString(img_specialist_json));

                //Добавление id в массив для отображения в событии click
                IdSpecialistCategoryArrListForClick.add(json.getString(id_specialist_json));

                GetDataAdapter2.setPhotoSpecialist(json.getString(img_specialist_json));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapterSpecialist = new RecyclerViewAdapterSpecialist(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapterSpecialist);
    }
}