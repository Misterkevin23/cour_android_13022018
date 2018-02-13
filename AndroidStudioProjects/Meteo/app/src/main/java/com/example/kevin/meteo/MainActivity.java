package com.example.kevin.meteo;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kevin.meteo.models.OpenWeatherMap;
import com.example.kevin.meteo.utils.Constant;
import com.example.kevin.meteo.utils.FastDialog;
import com.example.kevin.meteo.utils.Network;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="Main" ;
    private EditText editTextCity;
    private Button buttonSubmit;
    private TextView textViewTemperature, textViewCity;
    private ImageView imageViewIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = (EditText) findViewById(R.id.editTextCity);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        textViewCity = (TextView) findViewById(R.id.textViewCity);
        textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
        imageViewIcon = (ImageView) findViewById(R.id.imageViewIcon);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextCity.getText().toString().length() > 0){
                    //TODO : verification d'une connexion Internet

                    if(Network.isNetworkAvailable(MainActivity.this)) {
                        //TODO : requête au web service
                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        String url = String.format(Constant.URL, editTextCity.getText().toString());

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e(TAG, "json: "+response);

                                        getData(response);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String response = new String(error.networkResponse.data);

                                getData(response);

                            }
                        });
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    } else {
                        FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, "Vous devez être connecté");
                    }
                } else {
                    FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, "Vous devez renseigner une ville");
                }
            }
        });

    }

    private void getData(String json) {
        Gson myGson = new Gson();
        OpenWeatherMap owm = myGson.fromJson(json, OpenWeatherMap.class);

        if(owm.getCod().equals("200")) {
            textViewCity.setText(owm.getName());
            //TODO : afficher la temperature
            textViewTemperature.setText(owm.getMain().getTemp());
            //ToDO: afficher l'image du temps avec Picasso
            //insert le nom de l'image dans l'url (remplace le %s)
            String url_image = String.format(Constant.URL_IMAGE, owm.getWeather().get(0).getIcon()) ;

            //picasso permet de charger l'image
            Picasso.with(MainActivity.this).load(url_image).into(imageViewIcon);

        } else {
            FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, owm.getMessage());
        }
    }
}
