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

public class CompanyActivity extends AppCompatActivity {

    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String id_se, nameServices,imgServices;

    String HTTP_JSON_URL = "https://barbersakh.ru/company.php";
    String id_comp_json = "id";
    String name_comp_json = "name";
    String addres_comp_json = "address";
    String img_comp_json = "img";
    String id_services_json = "id_services";

    JsonArrayRequest RequestOfJSonArray;

    RequestQueue requestQueue;

    View view;

    int RecyclerViewItemPosition;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> ImageCompanyArrListForClick;
    ArrayList<String> IdCategoryArrListForClick;
    ArrayList<String> IdCompanyArrListForClick;
    ArrayList<String> NameCompanyArrListForClick;
    ArrayList<String> AddressCompanyArrListForClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id_services = intent.getStringExtra("id_services");
        String name_se = intent.getStringExtra("name");
        String img_se = intent.getStringExtra("imgSe");
        id_se = id_services;
        nameServices = name_se;
        imgServices = img_se;

        ImageCompanyArrListForClick = new ArrayList<>();

        IdCategoryArrListForClick = new ArrayList<>();
        IdCompanyArrListForClick = new ArrayList<>();
        NameCompanyArrListForClick = new ArrayList<>();
        AddressCompanyArrListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP_CALL();

        // Реализация прослушивателя щелчков в RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(CompanyActivity.this, new GestureDetector.SimpleOnGestureListener() {

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

                    // Отображение RecyclerView Щелкнув значение элемента с помощью Toast.
                    //Toast.makeText(CompanyActivity.this, ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_SHORT).show();

                    Log.i("MyLOG", "--------------------------------COMPANY---------------------------------------");
                    Log.i("MyLOG", "id_services: " + IdCategoryArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "nameSe: " + nameServices);
                    Log.i("MyLOG", "id_company: " + IdCompanyArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "nameCo: " + NameCompanyArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "address: " + AddressCompanyArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "imgCompany: " + ImageCompanyArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "imgServices: " + imgServices);


                    Intent intent = new Intent(CompanyActivity.this, SpecialistActivity.class);
                    intent.putExtra("id_services", IdCategoryArrListForClick.get(RecyclerViewItemPosition)); //Передать id услуги
                    intent.putExtra("nameSe", nameServices); //Передать имя услуги
                    intent.putExtra("id_company", IdCompanyArrListForClick.get(RecyclerViewItemPosition)); //Передать id компании
                    intent.putExtra("nameCo", NameCompanyArrListForClick.get(RecyclerViewItemPosition)); //Передать имя компании
                    intent.putExtra("address", AddressCompanyArrListForClick.get(RecyclerViewItemPosition)); //Передать адресс компании
                    intent.putExtra("imgCo", ImageCompanyArrListForClick.get(RecyclerViewItemPosition)); //Передать адресс компании
                    intent.putExtra("imgSe",imgServices);
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

//id_c - id категории

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL + "?" + "id=" + id_se,

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

        requestQueue = Volley.newRequestQueue(CompanyActivity.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitle(json.getString(name_comp_json));
                GetDataAdapter2.setAddressCompany(json.getString(addres_comp_json));
                GetDataAdapter2.setId(json.getString(id_comp_json));

                // Добавление имени заголовка изображения в массив для отображения в событии click RecyclerView.
                ImageCompanyArrListForClick.add(json.getString(img_comp_json));
                //ImageTitleNameArrayListForClick.add(json.getString(Addres_company_JSON));

                //Добавление id в массив для отображения в событии click
                IdCategoryArrListForClick.add(json.getString(id_services_json));
                IdCompanyArrListForClick.add(json.getString(id_comp_json));
                NameCompanyArrListForClick.add(json.getString(name_comp_json));
                AddressCompanyArrListForClick.add(json.getString(addres_comp_json));

                GetDataAdapter2.setImageUrl(json.getString(img_comp_json));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapterCompany(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}