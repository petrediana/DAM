package com.diana.bilet_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diana.bilet_monitor.Clase.Monitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InregistrareMonitorActivity extends AppCompatActivity {
    private static final String TAG = "InregistrareMonitorAc";
    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String ADAUGA_MONITOR_KEY = "adaugaMonitor";

    private EditText nrInvEt;
    private EditText producatorEt;
    private EditText diagonalaEt;
    private EditText proprietarEt;
    private EditText dataIntrEt;
    private Button adaugaBtn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inregistrare_monitor);
        Log.i(TAG, "InregistrareMonitorActivity started");

        initComponents();
        intent = getIntent();

        if (intent.hasExtra(ADAUGA_MONITOR_KEY)) {
            Monitor monitor = (Monitor) intent.getSerializableExtra(ADAUGA_MONITOR_KEY);
            updateComponenteVizuale(monitor);
        }
    }

    private void updateComponenteVizuale(Monitor monitor) {
        nrInvEt.setText(String.valueOf(monitor.getNrInventar()));
        producatorEt.setText(monitor.getProducator());
        diagonalaEt.setText(String.valueOf(monitor.getDiagonala()));
        proprietarEt.setText(monitor.getProprietar());
        dataIntrEt.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(monitor.getDataIntrarii()));
    }

    private void initComponents() {
        adaugaBtn = findViewById(R.id.inregistrare_monitor_adauga_btn);
        nrInvEt = findViewById(R.id.inregistrare_monitor_nr_inventar_et);
        producatorEt = findViewById(R.id.inregistrare_monitor_producator_et);
        diagonalaEt = findViewById(R.id.inregistrare_monitor_diagonala_et);
        proprietarEt = findViewById(R.id.inregistrare_monitor_proprietar_et);
        dataIntrEt = findViewById(R.id.inregistrare_monitor_data_intrarii_et);

        adaugaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trimiteMonitorLaMain();
            }
        });
    }

    private boolean validareDate() {
        if (nrInvEt.getText().toString().trim().isEmpty() || nrInvEt.getText() == null) {
            Toast.makeText(this, getString(R.string.inregistrare_nr_inregistare_error), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (producatorEt.getText().toString().trim().isEmpty() || producatorEt.getText() == null) {
            Toast.makeText(this, getString(R.string.producator_nu_ok_error), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (diagonalaEt.getText().toString().trim().isEmpty() || diagonalaEt.getText() == null) {
            Toast.makeText(this, getString(R.string.diagonala_nu_ok), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Integer.parseInt(diagonalaEt.getText().toString()) < 14 || Integer.parseInt(diagonalaEt.getText().toString()) > 55) {
            Toast.makeText(this, getString(R.string.diagonala_intre_14_si_55_pls), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (proprietarEt.getText().toString().trim().isEmpty() || proprietarEt.getText() == null) {
            Toast.makeText(this, getString(R.string.proprietar_nu_ok), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dataIntrEt.getText().toString().trim().isEmpty() || dataIntrEt.getText() == null || !validateDate(dataIntrEt.getText().toString())) {
            Toast.makeText(this, getString(R.string.data_intrarii_nu_ok), Toast.LENGTH_SHORT).show();
            Log.i(TAG, dataIntrEt.getText().toString());
            return false;
        }

        return true;
    }

    private boolean validateDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        Log.i(TAG, strDate + " validateDate method()");

        try {
            simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Monitor construireMonitor() {
        int nrInventar = Integer.parseInt(nrInvEt.getText().toString());
        String producator = producatorEt.getText().toString();
        int diag = Integer.parseInt(diagonalaEt.getText().toString());
        String proprietar = proprietarEt.getText().toString();
        Date dataIntr = null;

        try {
            dataIntr = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(dataIntrEt.getText().toString());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new Monitor(nrInventar, producator, diag, proprietar, dataIntr);
    }

    private void trimiteMonitorLaMain() {
        if (validareDate()) {
            Monitor monitor = construireMonitor();
            Log.i(TAG, monitor.toString());
            intent.putExtra(ADAUGA_MONITOR_KEY, monitor);
            setResult(RESULT_OK, intent);

            //Toast.makeText(this, monitor.toString(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
