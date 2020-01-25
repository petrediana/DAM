package com.diana.bilet_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.diana.bilet_monitor.Clase.Graficcc;
import com.diana.bilet_monitor.Clase.Monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RaportActivity extends AppCompatActivity {
    private static final String TAG = "RaportActivity";
    private Intent intent;
    private List<Monitor> monitors = new ArrayList<>();
    private HashMap<String, Integer> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_raport);

        intent = getIntent();
        if (intent.hasExtra(MainActivity.TRIMITE_LISTA_MONITOARE_KEY)) {
            monitors = (List<Monitor>) intent.getSerializableExtra(MainActivity.TRIMITE_LISTA_MONITOARE_KEY);
            Log.i(TAG, monitors.toString());
        }

        puneDateInHashMap();
        Log.i(TAG, hashMap.toString());

        if (hashMap != null) {
            View myView = new Graficcc(this, hashMap);
            setContentView(myView);
        }
    }

    private void puneDateInHashMap() {
        if (monitors != null && monitors.size() > 0) {
            for (Monitor monitor : monitors) {
                if (hashMap.containsKey("Diag:" + monitor.getDiagonala())) {
                    hashMap.put("Diag:" + monitor.getDiagonala(), hashMap.get("Diag:" + monitor.getDiagonala()) + 1);
                } else {
                    hashMap.put("Diag:" + monitor.getDiagonala(), 1);
                }
            }
        }
    }
}
