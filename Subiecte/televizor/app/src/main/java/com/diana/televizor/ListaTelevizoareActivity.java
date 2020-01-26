package com.diana.televizor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.diana.televizor.Clase.Televizor;
import com.diana.televizor.Database.TelevizorService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaTelevizoareActivity extends AppCompatActivity {
    private static final String TAG = "ListaTVActivity";
    private static final int MODIFICA_TV_REQUEST_KEY = 208;

    private Intent intent;
    private ListView listView;
    private List<Televizor> televizorList = new ArrayList<>();
    private int clickedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_televizoare);

        intent = getIntent();
        if (intent.hasExtra(MainActivity.TRIMITE_LISTA_TV_KEY)) {
            televizorList = (List<Televizor>) intent.getSerializableExtra(MainActivity.TRIMITE_LISTA_TV_KEY);
            Log.i(TAG, televizorList.toString());
        }

        initComp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MODIFICA_TV_REQUEST_KEY && resultCode == RESULT_OK && data != null) {
            Televizor televizor = (Televizor) data.getSerializableExtra(InregistrareTelevizorActivity.ADAUGA_MODIFICA_TELEVIZOR_KEY);
            Log.i(TAG, televizor.toString());
            modificaTv(televizor);

            updateLv();
            ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) listView.getAdapter();
            arrayAdapter.notifyDataSetChanged();

        }
    }

    private void modificaTv(Televizor televizor) {
        televizorList.get(clickedItem).setNrInventar(televizor.getNrInventar());
        televizorList.get(clickedItem).setProducator(televizor.getProducator());
        televizorList.get(clickedItem).setDiagonala(televizor.getDiagonala());
        televizorList.get(clickedItem).setProprietar(televizor.getProprietar());
        televizorList.get(clickedItem).setDataIntrarii(televizor.getDataIntrarii());
    }

    private void initComp() {
        listView = findViewById(R.id.lista_tv_lv);
        updateLv();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedItem = i;
                Log.i(TAG, "Clicked on: " + i);

                Televizor televizor = televizorList.get(i);
                Intent intent = new Intent(getApplicationContext(), InregistrareTelevizorActivity.class);
                intent.putExtra(InregistrareTelevizorActivity.ADAUGA_MODIFICA_TELEVIZOR_KEY, (Serializable) televizor);
                startActivityForResult(intent, MODIFICA_TV_REQUEST_KEY);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(ListaTelevizoareActivity.this, "clicked: " + i, Toast.LENGTH_SHORT).show();
                construiesteAlertDialog(i);
                return true;
            }
        });
    }

    private void updateLv() {
        List<String> values = new ArrayList<>();
        for (Televizor tv : televizorList) {
            values.add(tv.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
    }

    private void construiesteAlertDialog(final int index) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Titlu")
                .setMessage("Sigur vrei?" + index)
                .setPositiveButton("da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // Toast.makeText(ListaTelevizoareActivity.this, "da", Toast.LENGTH_SHORT).show();
                        StergeTvDinDb(televizorList.get(index));
                    }
                })
                .setNegativeButton("nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ListaTelevizoareActivity.this, "nu", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        alertDialog.show();
    }

    private void StergeTvDinDb(final Televizor televizor) {
        new TelevizorService.DeleteTelevizor(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer integer) {
                if (integer == 1) {
                    Toast.makeText(ListaTelevizoareActivity.this, "s-a sters", Toast.LENGTH_SHORT).show();
                    televizorList.remove(televizor);
                    updateLv();

                } else {
                    Toast.makeText(ListaTelevizoareActivity.this, "nu s-a sters", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(televizor);
    }
}
