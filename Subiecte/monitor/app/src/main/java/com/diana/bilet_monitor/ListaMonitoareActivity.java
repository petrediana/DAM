package com.diana.bilet_monitor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.diana.bilet_monitor.Clase.Monitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaMonitoareActivity extends AppCompatActivity {
    private static final int UPDATE_MONITOR_REQ_KEY = 205;
    private static final String TAG = "ListaMonitoareActivity";
    private ListView listView;
    private List<Monitor> monitors = new ArrayList<>();
    private Intent intent;
    private int clickedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_monitoare);

        intent = getIntent();
        if (intent.hasExtra(MainActivity.TRIMITE_LISTA_MONITOARE_KEY)) {
            monitors = (List<Monitor>) intent.getSerializableExtra(MainActivity.TRIMITE_LISTA_MONITOARE_KEY);
        }
        initComponents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_MONITOR_REQ_KEY && resultCode == RESULT_OK && data != null) {
            Monitor monitor = (Monitor) data.getSerializableExtra(InregistrareMonitorActivity.ADAUGA_MONITOR_KEY);
            Log.i(TAG, "updated monitor: " + monitor);
            Toast.makeText(this, monitor.toString(), Toast.LENGTH_SHORT).show();

            updateMonitor(monitor);
            updateLv();
            ArrayAdapter adapter = (ArrayAdapter<String>) listView.getAdapter();
            adapter.notifyDataSetChanged();

        }
    }

    private void updateMonitor(Monitor monitor) {
        monitors.get(clickedItem).setNrInventar(monitor.getNrInventar());
        monitors.get(clickedItem).setDataIntrarii(monitor.getDataIntrarii());
        monitors.get(clickedItem).setDiagonala(monitor.getDiagonala());
        monitors.get(clickedItem).setProducator(monitor.getProducator());
        monitors.get(clickedItem).setProprietar(monitor.getProprietar());
    }

    private void updateLv() {
        List<String> strs = new ArrayList<>();
        for (Monitor m : monitors) {
            strs.add(m.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, strs);
        listView.setAdapter(adapter);
    }

    private void initComponents() {
        listView = findViewById(R.id.lista_monitoare_lv);

        updateLv();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "clicked: " + monitors.get(i));
                clickedItem = i;
                Monitor monitor = monitors.get(i);

                Intent intent = new Intent(getApplicationContext(), InregistrareMonitorActivity.class);
                intent.putExtra(InregistrareMonitorActivity.ADAUGA_MONITOR_KEY, (Serializable) monitor);
                startActivityForResult(intent, UPDATE_MONITOR_REQ_KEY);
            }
        });
    }
}
