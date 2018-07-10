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

public class MainActivity extends AppCompatActivity {

    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String HTTP_JSON_URL = "https://barbersakh.ru/services.php";

    String id_services_json = "id";  //--------------------
    String name_services_json = "name";
    String img_services_json = "img";


    JsonArrayRequest RequestOfJSonArray;

    RequestQueue requestQueue;

    View view;

    int RecyclerViewItemPosition;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> NameCategoryArrListForClick;
    ArrayList<String> ImgCategoryArrListForClick;

    ArrayList<String> IdCategoryArrListForClick;  //--------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NameCategoryArrListForClick = new ArrayList<>();
        ImgCategoryArrListForClick = new ArrayList<>();

        IdCategoryArrListForClick = new ArrayList<>();  //----------------------

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP_CALL();

        // Реализация прослушивателя щелчков в RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

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

                    Log.i("MyLOG", "--------------------------------SERVICES---------------------------------------");
                    Log.i("MyLOG", "id_services: " + IdCategoryArrListForClick.get(RecyclerViewItemPosition));
                    Log.i("MyLOG", "name: " + NameCategoryArrListForClick.get(RecyclerViewItemPosition));

                    Intent intent = new Intent(MainActivity.this, CompanyActivity.class);
                    intent.putExtra("id_services", IdCategoryArrListForClick.get(RecyclerViewItemPosition));
                    intent.putExtra("name", NameCategoryArrListForClick.get(RecyclerViewItemPosition));
                    intent.putExtra("imgSe", ImgCategoryArrListForClick.get(RecyclerViewItemPosition));
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

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,

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

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitle(json.getString(name_services_json));

                GetDataAdapter2.setId(json.getString(id_services_json));

                // Добавление имени заголовка изображения в массив для отображения в событии click RecyclerView.
                NameCategoryArrListForClick.add(json.getString(name_services_json));

                ImgCategoryArrListForClick.add(json.getString(img_services_json));

                //Добавление id в массив для отображения в событии click
                IdCategoryArrListForClick.add(json.getString(id_services_json));

                GetDataAdapter2.setImageUrl(json.getString(img_services_json));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}