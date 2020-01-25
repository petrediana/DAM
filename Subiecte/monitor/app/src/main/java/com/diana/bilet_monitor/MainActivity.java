package com.diana.bilet_monitor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.diana.bilet_monitor.Clase.JsonParse;
import com.diana.bilet_monitor.Clase.Monitor;
import com.diana.bilet_monitor.Network.HttpManager;
import com.diana.bilet_monitor.Network.HttpResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String URL = "https://raw.githubusercontent.com/petrediana/DAM/master/Subiecte/monitor.txt";

    public static final int ADAUGA_MONITOR_REQ_KEY = 200;
    public static final String TRIMITE_LISTA_MONITOARE_KEY = "listaCuMonitoareWooo";
    private Button despreBtn;
    private Button inregistrareMonitorBtn;
    private Button listaMonitoareBtn;
    private Button preluareRetreaBtn;
    private Button raportBtn;
    private List<Monitor> monitors = new ArrayList<>();
    private HttpResponse httpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        //aducereJsonRetea();
    }

    void initComponents() {
        despreBtn = findViewById(R.id.activity_main_despre_btn);
        despreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.main_activity_despre_btn_toast), Toast.LENGTH_SHORT).show();
            }
        });

        inregistrareMonitorBtn = findViewById(R.id.activity_main_inregistrare_btn);
        inregistrareMonitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InregistrareMonitorActivity.class);
                startActivityForResult(intent, ADAUGA_MONITOR_REQ_KEY);
            }
        });

        listaMonitoareBtn = findViewById(R.id.activity_main_lista_monitoare_btn);
        listaMonitoareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListaMonitoareActivity.class);
                intent.putExtra(TRIMITE_LISTA_MONITOARE_KEY, (Serializable) monitors);
                Log.i(TAG, monitors.toString());
                startActivity(intent);
            }
        });

        preluareRetreaBtn = findViewById(R.id.activity_main_preluare_btn);
        preluareRetreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Click aducere din retea: ");
                aducereJsonRetea();

                if (httpResponse != null) {
                    Toast.makeText(MainActivity.this, getString(R.string.preluat), Toast.LENGTH_SHORT).show();
                    monitors.addAll(httpResponse.getMonitors());
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.mai_da_un_click), Toast.LENGTH_SHORT).show();
                }
            }
        });

        raportBtn = findViewById(R.id.activity_main_raport_btn);
        raportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RaportActivity.class);
                intent.putExtra(TRIMITE_LISTA_MONITOARE_KEY, (Serializable) monitors);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADAUGA_MONITOR_REQ_KEY && resultCode == RESULT_OK && data != null) {
            Monitor monitor = (Monitor)data.getSerializableExtra(InregistrareMonitorActivity.ADAUGA_MONITOR_KEY);
            Toast.makeText(getApplicationContext(), monitor.toString(), Toast.LENGTH_LONG).show();
            monitors.add(monitor);
        }
    }

    private void aducereJsonRetea() {
        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.i(TAG, s);
                httpResponse = JsonParse.parsareJson(s);
                if (httpResponse != null) {
                    Log.i(TAG, httpResponse.getMonitors().toString());
                }
            }
        }.execute(URL);
    }
}
