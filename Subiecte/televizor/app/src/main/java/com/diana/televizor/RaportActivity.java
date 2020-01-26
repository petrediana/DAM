package com.diana.televizor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.diana.televizor.Clase.Graficcc;
import com.diana.televizor.Clase.Televizor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RaportActivity extends AppCompatActivity {
    private static final String TAG = "RaportActivity";
    private Intent intent;
    private List<Televizor> televizorList = new ArrayList<>();
    private HashMap<String, Integer> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_raport);

        intent = getIntent();
        if (intent.hasExtra(MainActivity.TRIMITE_LISTA_TV_KEY)) {
            televizorList = (List<Televizor>) intent.getSerializableExtra(MainActivity.TRIMITE_LISTA_TV_KEY);
        }

        puneInHashMap();
        Log.i(TAG, hashMap.toString());

        List<String> extraInfo = new ArrayList<>(hashMap.keySet());
        List<Integer> dateDiag = new ArrayList<>();
        for (String str : extraInfo) {
            dateDiag.add(hashMap.get(str));
        }

        if (extraInfo != null && dateDiag != null) {
            View myView = new Graficcc(getApplicationContext(), dateDiag, extraInfo);
            setContentView(myView);
        }
    }

    private void puneInHashMap() {
        if (televizorList != null && televizorList.size() > 0) {
            for (Televizor televizor : televizorList) {
                if (hashMap.containsKey("Diag: " + televizor.getDiagonala())) {
                    hashMap.put("Diag: " + televizor.getDiagonala(), hashMap.get("Diag: " + televizor.getDiagonala()) + 1);
                } else {
                    hashMap.put("Diag: " + televizor.getDiagonala(), 1);
                }
            }
        }
    }
}
