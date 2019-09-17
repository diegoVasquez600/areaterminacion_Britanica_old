package com.example.areaterminacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etpedido, etcliente, etreferencia, etcolor, ettipotalla, etcantidaentra;
    Button btnguardar, btnbuscar, btnactualizar, btneliminar, btnLista;

    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etpedido=findViewById(R.id.etpedido);
        etcliente=findViewById(R.id.etcliente);
        etreferencia=findViewById(R.id.etreferencia);
        etcolor=findViewById(R.id.etcolor);
        ettipotalla=findViewById(R.id.ettipotalla);
        etcantidaentra=findViewById(R.id.etcantidaentra);
        btnguardar=findViewById(R.id.btnguardar);
        btnLista = findViewById(R.id.btnLista);

       /* btnbuscar=findViewById(R.id.btnbuscar);
        btnactualizar=findViewById(R.id.btnactualizar);
        btneliminar=findViewById(R.id.btneliminar);*/

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarservicio("http://192.168.1.97:8080/terminacion/insertar.php");

            }
        });
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: intent to list
            }
        });

       /* btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar("http://192.168.1.97:8080/terminacion/buscar.php?pedido="+etpedido.getText()+"");
            }
        });*/

       /* btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarservicio("http://192.168.1.97:8080/terminacion/update.php");
            }
        });

        */
      /*  btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar("http://192.168.1.97:8080/terminacion/eliminar.php");
            }
        });
       */

    }

    private void ejecutarservicio(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "CAMBIOS REALIZADOS", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<>();
                parametros.put("pedido",etpedido.getText().toString());
                parametros.put("cliente",etcliente.getText().toString());
                parametros.put("referencia",etreferencia.getText().toString());
                parametros.put("color",etcolor.getText().toString());
                parametros.put("tipotalla",ettipotalla.getText().toString());
                parametros.put("canentra",etcantidaentra.getText().toString());
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void buscar(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        etcliente.setText(jsonObject.getString("cliente"));
                        etreferencia.setText(jsonObject.getString("referencia"));
                        etcolor.setText(jsonObject.getString("color"));
                        ettipotalla.setText(jsonObject.getString("tipotalla"));
                        etcantidaentra.setText(jsonObject.getString("canentra"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"NO SE ENCUENTRAN DATOS ALMACENADOS",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void eliminar(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "SE ELIMINO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                limpiarformulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<>();
                parametros.put("pedido",etpedido.getText().toString());
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void limpiarformulario(){
        etpedido.setText("");
        etcliente.setText("");
        etreferencia.setText("");
        etcolor.setText("");
        ettipotalla.setText("");
        etcantidaentra.setText("");

    }
}
