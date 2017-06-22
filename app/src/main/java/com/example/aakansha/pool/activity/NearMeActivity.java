package com.example.aakansha.pool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aakansha.pool.R;
import com.example.aakansha.pool.app.AppConfig;
import com.example.aakansha.pool.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NearMeActivity extends AppCompatActivity {

    public static final String TAG="ANSWER";
    ArrayList<String> names= new ArrayList<>();
    ArrayList<nearMeItem> nearMeItemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);

        Intent intent1= new Intent();
        String employee_id = intent1.getStringExtra("employee_id");

        NearMe(employee_id, new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<nearMeItem> result) {
                for(int i=0; i<nearMeItemArrayList.size(); i++) {
                    names.add(nearMeItemArrayList.get(i).getName());
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(NearMeActivity.this, R.layout.near_me_lv, names);
                ListView listView = (ListView) findViewById(R.id.list_nearMe);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name=nearMeItemArrayList.get(position).getName();
                        String employee_id=nearMeItemArrayList.get(position).getEmployee_id();
                        String designation=nearMeItemArrayList.get(position).getDesignation();
                        String address=nearMeItemArrayList.get(position).getAddress();

                        Intent i= new Intent(NearMeActivity.this,Information.class);
                        i.putExtra("name", name);
                        i.putExtra("employee_id", employee_id);
                        i.putExtra("designation", designation);
                        i.putExtra("address", address);
                        startActivity(i);
                    }
                });
}
        });
    }


    private void NearMe(final String employee_id, final VolleyCallback volleyCallback) {
        // Tag used to cancel the request

        String tag_string_req = "req_near_me";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_NEARME, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Near Me Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONObject user = jObj.getJSONObject("user");
                        String employee_id = user.getString("employee_id");
                        String name = user.getString("name");
                        String designation = user.getString("designation");
                        String address = user.getString("address");
                        nearMeItemArrayList.add(new nearMeItem(name,employee_id,designation,address));
                        volleyCallback.onSuccess(nearMeItemArrayList);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Near Me Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("employee_id", employee_id);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public interface VolleyCallback{
        void onSuccess(ArrayList<nearMeItem> result);
    }
}
