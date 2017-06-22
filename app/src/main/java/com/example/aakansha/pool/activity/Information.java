package com.example.aakansha.pool.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aakansha.pool.R;

public class Information extends AppCompatActivity {

    TextView tv_name=(TextView) findViewById(R.id.infoname);
    TextView tv_employee_id= (TextView)findViewById(R.id.infoemployeeid);
    TextView tv_designation=(TextView) findViewById(R.id.infodesignation);
    TextView tv_address= (TextView)findViewById(R.id.infoaddress);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Intent i = getIntent();
        String address = i.getStringExtra("address");
        String name = i.getStringExtra("name");
        String designation= i.getStringExtra("designation");
        String employee_id= i.getStringExtra("employee_id");

        tv_address.setText(address.toString());
        tv_employee_id.setText(employee_id.toString());
        tv_name.setText(name.toString());
        tv_designation.setText(designation.toString());
    }
}
