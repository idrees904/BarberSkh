package ru.barbersakh.barberskh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CompanyInfoActivity extends AppCompatActivity {

    String id_com;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        Intent intent = getIntent();
        String id_comp = intent.getStringExtra("id_comp");
        id_com = id_comp;

        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(id_com);
    }

}
