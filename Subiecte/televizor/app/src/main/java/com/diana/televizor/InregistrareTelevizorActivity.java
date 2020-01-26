package com.diana.televizor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.diana.televizor.Clase.Televizor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InregistrareTelevizorActivity extends AppCompatActivity {
    private static final String TAG = "InregistrareTvActivity";
    public static final String DATA_FORMAT = "dd-MM-yyyy";

    public static final String ADAUGA_MODIFICA_TELEVIZOR_KEY = "faCevaCuTv-u";

    private EditText nrInvEt;
    private EditText producatorEt;
    private Spinner diagonalaSpn;
    private EditText proprietarEt;
    private EditText dataIntrariiEt;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inregistrare_televizor);
        initComponents();

        intent = getIntent();
        if (intent.hasExtra(ADAUGA_MODIFICA_TELEVIZOR_KEY)) {
            Televizor televizor = (Televizor) intent.getSerializableExtra(ADAUGA_MODIFICA_TELEVIZOR_KEY);
            puneTelevizorInComponenteVizuale(televizor);
        }

    }

    private void puneTelevizorInComponenteVizuale(Televizor televizor) {
        if (televizor != null) {
            nrInvEt.setText(String.valueOf(televizor.getNrInventar()));
            producatorEt.setText(televizor.getProducator());
            proprietarEt.setText(televizor.getProprietar());
            dataIntrariiEt.setText(new SimpleDateFormat(DATA_FORMAT, Locale.US).format(televizor.getDataIntrarii()));

            int diagonala = televizor.getDiagonala();

            ArrayAdapter<String> adapter = (ArrayAdapter<String>) diagonalaSpn.getAdapter();
            for (int i = 0; i < adapter.getCount(); ++i) {
                if (String.valueOf(diagonala).equals(adapter.getItem(i))) {
                    //Log.i(TAG, "sunt unde trebuie");
                    diagonalaSpn.setSelection(i);
                    break;
                }
            }
        }
    }

    private void initComponents() {
        nrInvEt = findViewById(R.id.inregistrare_monitor_nr_inventar_et);
        producatorEt = findViewById(R.id.inregistrare_monitor_producator_et);
        proprietarEt = findViewById(R.id.inregistrare_monitor_proprietar_et);
        dataIntrariiEt = findViewById(R.id.inregistrare_monitor_data_intrarii_et);

        diagonalaSpn = findViewById(R.id.diagonala_spn);
        List<String> values = new ArrayList<>();
        for (int i = 15; i < 55; ++i) {
            values.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, values);
        diagonalaSpn.setAdapter(adapter);

        Button adaugaBtn = findViewById(R.id.inregistrare_monitor_adauga_btn);
        adaugaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificaDateInput()) {
                    Televizor televizor = creareTelevizor();
                    Log.i(TAG, televizor.toString());

                    intent.putExtra(ADAUGA_MODIFICA_TELEVIZOR_KEY, (Serializable) televizor);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean verificaDateInput() {
        if (nrInvEt.getText() == null || nrInvEt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "nr inv nu e ok", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (producatorEt.getText() == null || producatorEt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "producator nu e ok", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (proprietarEt.getText() == null || proprietarEt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "proprietar nu e ok", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dataIntrariiEt.getText() == null || dataIntrariiEt.getText().toString().trim().isEmpty()
                || !validareData(dataIntrariiEt.getText().toString())) {
            Toast.makeText(this, "data nu e ok; format: dd-MM-YYYY", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validareData(String data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_FORMAT, Locale.US);

        try {
            simpleDateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Televizor creareTelevizor() {
        int nrInv = Integer.parseInt(nrInvEt.getText().toString());
        String producator = producatorEt.getText().toString();
        String proprietar = proprietarEt.getText().toString();
        String dataStr = dataIntrariiEt.getText().toString();
        int diagonala = Integer.parseInt(diagonalaSpn.getSelectedItem().toString());

        Date data_inregistraii = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_FORMAT, Locale.US);
            data_inregistraii = simpleDateFormat.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Televizor(nrInv, producator, diagonala, proprietar, data_inregistraii);
    }
}
